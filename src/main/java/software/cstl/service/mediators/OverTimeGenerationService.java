package software.cstl.service.mediators;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.AttendanceSummary;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.OverTime;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.repository.AttendanceSummaryRepository;
import software.cstl.repository.EmployeeSalaryRepository;
import software.cstl.repository.OverTimeRepository;
import software.cstl.repository.extended.EmployeeExtRepository;
import software.cstl.security.SecurityUtils;
import software.cstl.service.AttendanceSummaryService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Transactional
@AllArgsConstructor
@Slf4j
public class OverTimeGenerationService {

    Map<Long, List<AttendanceSummary>> employeeMapAttendanceSummaries;

    private final EmployeeExtRepository employeeExtRepository;
    private final AttendanceSummaryService attendanceSummaryService;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final OverTimeRepository overTimeRepository;
    private final AttendanceSummaryRepository attendanceSummaryRepository;

    public List<OverTime> generateOverTime(Integer year, MonthType monthType){
        List<OverTime> overTimes = new ArrayList<>();
        List<Employee> employees = employeeExtRepository.findAllByCategoryAndStatus(EmployeeCategory.WORKER, EmployeeStatus.ACTIVE);

        YearMonth yearMonth = YearMonth.of(year, monthType.ordinal()+1);

        initializeEmployeeSummaries(year, yearMonth);

        for(Employee employee: employees){
            OverTime overTime = new OverTime();
            overTime
                .employee(employee)
                .designation(employee.getDesignation())
                .year(year)
                .month(monthType)
                .executedBy(SecurityUtils.getCurrentUserLogin().get())
                .executedOn(Instant.now());
            calculateEmployeeOverTime(overTime);
            if(overTime.getTotalOverTime()!=null)
                overTimes.add(overTime);
        }

        return overTimeRepository.saveAll(overTimes);
    }

    private void initializeEmployeeSummaries(Integer year, YearMonth yearMonth) {
        LocalDate toLocalDate = LocalDate.of(year, yearMonth.getMonth(), yearMonth.lengthOfMonth());
        ZonedDateTime fromDate = ZonedDateTime.of(year, yearMonth.getMonthValue(), 1, 0,0,0,0,ZoneId.systemDefault());
        ZonedDateTime toDate = ZonedDateTime.of(year, yearMonth.getMonthValue(), toLocalDate.getDayOfMonth(), 23,59,59,0,ZoneId.systemDefault());
        List<AttendanceSummary> attendanceSummaries;
        employeeMapAttendanceSummaries = attendanceSummaryRepository
            .findAttendanceSummaryByInTimeIsGreaterThanEqualAndOutTimeIsLessThanEqual(fromDate, toDate)
            .stream()
            .collect(Collectors.groupingBy(a->a.getEmployee().getId()));
    }

    public List<OverTime> regenerateOverTime(Integer year, MonthType monthType){
        overTimeRepository.deleteAllByYearAndMonth(year, monthType);
        overTimeRepository.flush();
        return generateOverTime(year, monthType);
    }

    public OverTime regenerateEmployeeOverTime(Long overTimeId){
        OverTime overTime = overTimeRepository.getOne(overTimeId);
        YearMonth yearMonth = YearMonth.of(overTime.getYear(), overTime.getMonth().ordinal()+1);
        initializeEmployeeSummaries(overTime.getYear(), yearMonth);
        return calculateEmployeeOverTime(overTime);
    }


    private OverTime calculateEmployeeOverTime(OverTime overTime) {
        calculateOverTimeAmount(overTime);
        return overTime;
    }




    private void calculateOverTimeAmount(OverTime overTime) {
        YearMonth yearMonth = YearMonth.of(overTime.getYear(), overTime.getMonth().ordinal()+1);
        LocalDate fromDate = LocalDate.of(overTime.getYear(), yearMonth.getMonth(), 1);
        LocalDate toDate = LocalDate.of(overTime.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());
        List<AttendanceSummary> attendanceSummaries = employeeMapAttendanceSummaries.get(overTime.getEmployee().getId())
            .stream().filter(s-> s.getOvertime().toMinutes()>0)
            .collect(Collectors.toList());
        if(attendanceSummaries==null)
            return;
        Long validOverTimeHour = new Long((attendanceSummaries==null?0:
            attendanceSummaries.size()*2));
        Long totalOverTimeHour = 0L;
        boolean overTimeOverLoaded = false;
        BigDecimal validOverTimeSalary = BigDecimal.ZERO;
        BigDecimal totalOverTimeSalary = BigDecimal.ZERO;
        Duration totalOverTimeInDuration = Duration.ZERO;

        for(AttendanceSummary summaryDTO: attendanceSummaries){
            Long overTimeHourOfTheDay = summaryDTO.getOvertime().toHours();
            totalOverTimeInDuration = totalOverTimeInDuration.plus(summaryDTO.getOvertime());

            log.debug("Total Over Time : [{}]: date: {}: overTimeHourOfTheDay: {}: totalOverTime {}",
                overTime.getEmployee().getEmpId(),
                summaryDTO.getAttendanceDate(),
                overTimeHourOfTheDay,
                totalOverTimeInDuration.toHours());
            EmployeeSalary employeeSalaryOfTheDay = new EmployeeSalary();
            if(summaryDTO.getEmployeeSalary()!=null)
                employeeSalaryOfTheDay = summaryDTO.getEmployeeSalary();
            else
                employeeSalaryOfTheDay = employeeSalaryRepository.findByEmployee_IdAndStatus(summaryDTO.getEmployee().getId(), ActiveStatus.ACTIVE);
            BigDecimal overTimeAmountPerHour =employeeSalaryOfTheDay==null? new BigDecimal (0): (employeeSalaryOfTheDay.getBasic().divide(new BigDecimal(208), RoundingMode.HALF_UP)).multiply(new BigDecimal(2));
            totalOverTimeSalary = totalOverTimeSalary.add(overTimeAmountPerHour.multiply(new BigDecimal(overTimeHourOfTheDay)));

            if(!overTimeOverLoaded){
                if(totalOverTimeInDuration.toHours()>validOverTimeHour){
                    Long validWithinExcess = totalOverTimeInDuration.toHours() - validOverTimeHour;
                    validOverTimeSalary =  validOverTimeSalary.add(overTimeAmountPerHour.multiply(new BigDecimal(validWithinExcess)));
                    overTimeOverLoaded = true;
                }else{
                    validOverTimeSalary = validOverTimeSalary.add(overTimeAmountPerHour.multiply(new BigDecimal(overTimeHourOfTheDay)));
                }
            }
        }
        totalOverTimeHour = totalOverTimeInDuration.toHours();
        Long extraOverTimeHour = totalOverTimeHour>validOverTimeHour? (totalOverTimeHour - validOverTimeHour): 0L;
        BigDecimal extraOverTimeSalary = extraOverTimeHour>0? totalOverTimeSalary.subtract(validOverTimeSalary): BigDecimal.ZERO;

        overTime.setTotalOverTime(Double.parseDouble(totalOverTimeHour.toString()));
        overTime.setOfficialOverTime(Double.parseDouble((totalOverTimeHour>validOverTimeHour? validOverTimeHour: totalOverTimeHour)+""));
        overTime.setExtraOverTime(Double.parseDouble(extraOverTimeHour.toString()));
        overTime.setTotalAmount(totalOverTimeSalary);
        overTime.setOfficialAmount(validOverTimeSalary);
        overTime.setExtraAmount(totalOverTimeSalary.subtract(overTime.getOfficialAmount()));
    }
}
