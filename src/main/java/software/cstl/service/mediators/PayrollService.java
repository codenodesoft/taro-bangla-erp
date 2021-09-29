package software.cstl.service.mediators;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.*;
import software.cstl.domain.enumeration.*;
import software.cstl.repository.*;
import software.cstl.repository.extended.EmployeeExtRepository;
import software.cstl.security.SecurityUtils;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.LeaveApplicationDateMapService;
import software.cstl.service.WeekendDateMapService;
import software.cstl.service.WeekendService;
import software.cstl.service.dto.LeaveApplicationDateMapDTO;
import software.cstl.utils.CodeNodeErpUtils;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
@Log4j2
public class PayrollService {

    private Integer totalWorkingDays;
    private Integer totalMonthDays;
    private Integer totalHolidays;
    private List<Holiday> holidays;
    private Integer totalWeekLeave;
    private LocalDate initialDay;
    private LocalDate lastDay;
    Map<Long, List<AttendanceSummary>> employeeMapAttendanceSummary;
    Map<LocalDate, List<Long>> leaveDateMapEmployeeIds;

    private final MonthlySalaryRepository monthlySalaryRepository;
    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;
    private final DesignationRepository designationRepository;
    private final EmployeeExtRepository employeeExtRepository;
    private final DefaultAllowanceRepository defaultAllowanceRepository;
    private final FineRepository fineRepository;
    private final FinePaymentHistoryRepository finePaymentHistoryRepository;
    private final AdvanceRepository advanceRepository;
    private final AdvancePaymentHistoryRepository advancePaymentHistoryRepository;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final PartialSalaryRepository partialSalaryRepository;
    private final AttendanceSummaryService attendanceSummaryService;
    private final WeekendService weekendService;
    private final LeaveApplicationRepository leaveApplicationRepository;
    private final HolidayRepository holidayRepository;
    private final WeekendDateMapService weekendDateMapService;
    private final OverTimeRepository overTimeRepository;
    private final AttendanceSummaryRepository attendanceSummaryRepository;
    private final LeaveApplicationDateMapService leaveApplicationDateMapService;
    private final EmployeeLeaveDateRepository employeeLeaveDateRepository;

    public PayrollService(OverTimeRepository overTimeRepository, MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryDtlRepository monthlySalaryDtlRepository, DesignationRepository designationRepository, EmployeeExtRepository employeeExtRepository, DefaultAllowanceRepository defaultAllowanceRepository, FineRepository fineRepository, FinePaymentHistoryRepository finePaymentHistoryRepository, AdvanceRepository advanceRepository, AdvancePaymentHistoryRepository advancePaymentHistoryRepository, AttendanceRepository attendanceRepository, EmployeeSalaryRepository employeeSalaryRepository, PartialSalaryRepository partialSalaryRepository, AttendanceSummaryService attendanceSummaryService, WeekendService weekendService, LeaveApplicationRepository leaveApplicationRepository, HolidayRepository holidayRepository, WeekendDateMapService weekendDateMapService, AttendanceSummaryRepository attendanceSummaryRepository, LeaveApplicationDateMapService leaveApplicationDateMapService, EmployeeLeaveDateRepository employeeLeaveDateRepository) {
        this.overTimeRepository = overTimeRepository;
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.monthlySalaryDtlRepository = monthlySalaryDtlRepository;
        this.designationRepository = designationRepository;
        this.employeeExtRepository = employeeExtRepository;
        this.defaultAllowanceRepository = defaultAllowanceRepository;
        this.fineRepository = fineRepository;
        this.finePaymentHistoryRepository = finePaymentHistoryRepository;
        this.advanceRepository = advanceRepository;
        this.advancePaymentHistoryRepository = advancePaymentHistoryRepository;
        this.attendanceRepository = attendanceRepository;
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.partialSalaryRepository = partialSalaryRepository;
        this.attendanceSummaryService = attendanceSummaryService;
        this.weekendService = weekendService;
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.holidayRepository = holidayRepository;
        this.weekendDateMapService = weekendDateMapService;
        this.attendanceSummaryRepository = attendanceSummaryRepository;
        this.leaveApplicationDateMapService = leaveApplicationDateMapService;
        this.employeeLeaveDateRepository = employeeLeaveDateRepository;
    }

    public MonthlySalary createEmptyMonthlySalaries(MonthlySalary monthlySalary) throws CloneNotSupportedException, ValidationException {
        if(monthlySalaryRepository.existsByYearAndMonthAndDepartmentAndStatus(monthlySalary.getYear(), monthlySalary.getMonth(), monthlySalary.getDepartment(), SalaryExecutionStatus.DONE))
            throw new ValidationException("Monthly salary already generated");
        monthlySalary.setStatus(SalaryExecutionStatus.NOT_DONE);
        getEmptyMonthSalaryDtls(monthlySalary);
        return monthlySalaryRepository.save(monthlySalary);
    }



    private MonthlySalary getEmptyMonthSalaryDtls(MonthlySalary monthlySalary){
        List<Employee> employees  = employeeExtRepository.findAllByDepartment_IdAndStatus(monthlySalary.getDepartment().getId(), EmployeeStatus.ACTIVE);
        for(Employee employee: employees){
            MonthlySalaryDtl monthlySalaryDtl = new MonthlySalaryDtl();
            monthlySalaryDtl.employee(employee)
                .status(SalaryExecutionStatus.NOT_DONE);
            monthlySalary.addMonthlySalaryDtl(monthlySalaryDtl);
        }
        return monthlySalary;
    }


    public void createMonthlySalaries(MonthlySalary monthlySalaryParam){
        initializeGlobalValuesForAMonth(monthlySalaryParam);
        MonthlySalary monthlySalary = monthlySalaryRepository.getOne(monthlySalaryParam.getId());
        List<MonthlySalaryDtl> monthlySalaryDtls = monthlySalaryDtlRepository.findAllByMonthlySalary_Id(monthlySalary.getId());
        int counter=0;
        for(MonthlySalaryDtl monthlySalaryDtl: monthlySalaryDtls){
            assignFine(monthlySalaryDtl);
            assignAdvance(monthlySalaryDtl);
            if(employeeSalaryRepository.findByEmployeeAndStatus(monthlySalaryDtl.getEmployee(), ActiveStatus.ACTIVE)!=null)
                assignSalaryAndAllowances(monthlySalary, monthlySalaryDtl);
            counter+=1;
            log.debug("{} data processed among total {}", counter, monthlySalaryDtls.size());
        }
        monthlySalary.status(SalaryExecutionStatus.DONE);
        monthlySalary.setFromDate(initialDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        monthlySalary.setToDate(lastDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        monthlySalaryRepository.save(monthlySalary);
        log.debug("Monthly salary generation success");
    }


    public MonthlySalary regenerateMonthlySalaries(MonthlySalary monthlySalaryParam) throws CloneNotSupportedException {
        monthlySalaryParam = monthlySalaryRepository.getOne(monthlySalaryParam.getId());
        Set<MonthlySalaryDtl> monthlySalaryDtls = monthlySalaryParam.getMonthlySalaryDtls();
        monthlySalaryDtlRepository.deleteAll(monthlySalaryDtls);
        monthlySalaryDtlRepository.flush();;
        monthlySalaryRepository.delete(monthlySalaryParam);
        monthlySalaryRepository.flush();
        MonthlySalary newMonthlySalary = new MonthlySalary();
        newMonthlySalary.setMonth(monthlySalaryParam.getMonth());
        newMonthlySalary.setDepartment(monthlySalaryParam.getDepartment());
        newMonthlySalary.setYear(monthlySalaryParam.getYear());
        newMonthlySalary.setFromDate(monthlySalaryParam.getFromDate());
        newMonthlySalary.setToDate(monthlySalaryParam.getToDate());
        newMonthlySalary =  createEmptyMonthlySalaries(newMonthlySalary);
        createMonthlySalaries(newMonthlySalary);
        return newMonthlySalary;
    }

    private void initializeGlobalValuesForAMonth(MonthlySalary monthlySalaryParam) {
        YearMonth yearMonth = YearMonth.of(monthlySalaryParam.getYear(), monthlySalaryParam.getMonth().ordinal()+1);
        this.totalMonthDays = yearMonth.lengthOfMonth();
        this.initialDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        this.lastDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());

        List<AttendanceSummary> attendanceSummaryDTOS = attendanceSummaryService.findAll(this.initialDay, this.lastDay);
        employeeMapAttendanceSummary = attendanceSummaryDTOS.stream()
            .collect(Collectors.groupingBy(a-> a.getEmployee().getId()));
        if(attendanceSummaryDTOS.isEmpty())
            this.totalWorkingDays = 0;
        else{
            Set<LocalDate> attendanceDateSet =  attendanceSummaryDTOS
                .parallelStream()
//                .filter(a-> a.getAttendanceMarkedAs()!=null && a.getAttendanceMarkedAs().equals(AttendanceMarkedAs.R)) ---> removing, as already fetching only regular attended data
                .map(a-> a.getAttendanceDate())
                .collect(Collectors.toSet());
            totalWorkingDays = attendanceDateSet.size();
        }

        this.totalWeekLeave = weekendDateMapService.findAllWeekendDateMapDTOs(this.initialDay, this.lastDay).size();
        this.holidays = holidayRepository.getOverLappingHolidays(initialDay, lastDay);
        this.totalHolidays = 0;
        for(Holiday holiday: holidays){
            if(holiday.getFrom().isBefore(initialDay) && holiday.getTo().isBefore(lastDay)){
                totalHolidays = totalHolidays+  Period.between(initialDay, holiday.getTo()).getDays();
            }
            else if(holiday.getFrom().isAfter(initialDay) && holiday.getTo().isBefore(lastDay)){
                totalHolidays = totalHolidays + holiday.getTotalDays();
            }
            else if(holiday.getFrom().isAfter(initialDay) && lastDay.isBefore(holiday.getTo())){
                totalHolidays = totalHolidays + Period.between(holiday.getFrom(), lastDay).getDays();
            }
        }

        // leave application
        List<EmployeeLeaveDate> employeeLeaveDates = employeeLeaveDateRepository.findAllByLeaveDateIsBetween(initialDay, lastDay);
        leaveDateMapEmployeeIds = new HashMap<>();
        for(EmployeeLeaveDate employeeLeaveDate: employeeLeaveDates){
            if(leaveDateMapEmployeeIds.containsKey(employeeLeaveDate.getLeaveDate())){
                List<Long> employeeIds = leaveDateMapEmployeeIds.get(employeeLeaveDate.getLeaveDate());
                employeeIds.add(employeeLeaveDate.getEmployee().getId());
                leaveDateMapEmployeeIds.put(employeeLeaveDate.getLeaveDate(), employeeIds);
            }else{
                List<Long> employeeIds = new ArrayList<>();
                employeeIds.add(employeeLeaveDate.getEmployee().getId());
                leaveDateMapEmployeeIds.put(employeeLeaveDate.getLeaveDate(), employeeIds);
            }
        }
    }

    public MonthlySalaryDtl regenerateMonthlySalaryForAnEmployee(Long monthlySalaryId, Long monthlySalaryDtlId){

        MonthlySalary monthlySalary = monthlySalaryRepository.getOne(monthlySalaryId);
        initializeGlobalValuesForAMonth(monthlySalary);
        MonthlySalaryDtl monthlySalaryDtl = monthlySalaryDtlRepository.getOne(monthlySalaryDtlId);
        assignFine(monthlySalaryDtl);
        assignAdvance(monthlySalaryDtl);
        assignSalaryAndAllowances(monthlySalary, monthlySalaryDtl);
        return monthlySalaryDtlRepository.save(monthlySalaryDtl);
    }

    private void assignSalaryAndAllowances(MonthlySalary monthlySalary, MonthlySalaryDtl monthlySalaryDtl){

        DefaultAllowance defaultAllowance = defaultAllowanceRepository.findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
        //Optional<PartialSalary> partialSalary = partialSalaryRepository.findByEmployeeAndYearAndMonth(monthlySalaryDtl.getEmployee(), monthlySalary.getYear(), monthlySalary.getMonth());

        BigDecimal gross, basic, houseRent, medicalAllowance, convinceAllowance, foodAllowance;
        gross = basic = houseRent = medicalAllowance = foodAllowance = convinceAllowance = BigDecimal.ZERO;


        YearMonth yearMonth = YearMonth.of(monthlySalary.getYear(), monthlySalary.getMonth().ordinal()+1);
        BigDecimal totalMonthDays = new BigDecimal(yearMonth.lengthOfMonth());

        // remove weekends from total working days
        List<Weekend> weekends = weekendService.findAll(WeekendStatus.ACTIVE);
        List<Integer> weekendsInOrdinal = new ArrayList<>();
        for(Weekend weekend: weekends){
            weekendsInOrdinal.add(CodeNodeErpUtils.getWeekDayOrdinalValue(weekend.getDay()));
        }

        LocalDate initialDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate lastDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());
        Map<LocalDate, AttendanceSummary> attendanceMap = new HashMap<>();

        if(employeeMapAttendanceSummary.containsKey(monthlySalaryDtl.getEmployee().getId())){
            for(AttendanceSummary attendanceSummaryDTO: employeeMapAttendanceSummary.get(monthlySalaryDtl.getEmployee().getId())){
                attendanceMap.put(attendanceSummaryDTO.getAttendanceDate(), attendanceSummaryDTO);
            }
        }

        int leaveCounter = 0;
        int holidayCounter = 0;
        EmployeeSalary activeSalaryForTheDay = employeeSalaryRepository.findByEmployeeAndStatus(monthlySalaryDtl.getEmployee(), ActiveStatus.ACTIVE);
        log.debug("Beginning log tracing");
        log.debug("active gross: {}", activeSalaryForTheDay.getGross());
        while(initialDay.isBefore(lastDay)){
            Boolean isWeekend = weekendsInOrdinal.contains(initialDay.getDayOfWeek().getValue());

            AttendanceSummary attendance = attendanceMap.containsKey(initialDay)? attendanceMap.get(initialDay): null;
            boolean grossCalculated = false;
            if(attendance!=null){
                grossCalculated = true;
                 //employeeSalaryRepository.getOne(attendance.getEmployeeSalaryId());
                gross = gross.add(attendance.getEmployeeSalary().getGross().divide(totalMonthDays, RoundingMode.HALF_DOWN));
                //gross = gross.add(activeSalaryForTheDay.getGross().divide(totalMonthDays, RoundingMode.HALF_UP));
                basic = basic.add(activeSalaryForTheDay.getBasic().divide(totalMonthDays, RoundingMode.HALF_UP));
                houseRent = houseRent.add(activeSalaryForTheDay.getHouseRent().divide(totalMonthDays, RoundingMode.HALF_UP));
                medicalAllowance = medicalAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getMedicalAllowance(), defaultAllowance.getMedicalAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));
                foodAllowance = foodAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getFoodAllowance(), defaultAllowance.getFoodAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));
                convinceAllowance = convinceAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getConvinceAllowance(), defaultAllowance.getConvinceAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));

            }
            Boolean holidayExists = holidayRepository.existsAllByFromLessThanEqualAndToGreaterThanEqual(initialDay, initialDay);
            if(holidayExists)
                holidayCounter +=1;
            Boolean leaveExists = false; // = leaveApplicationRepository.existsAllByStatusAndFromLessThanEqualAndToGreaterThanEqual(LeaveApplicationStatus.ACCEPTED, initialDay, initialDay);
            if( leaveDateMapEmployeeIds.containsKey(initialDay)
                && leaveDateMapEmployeeIds.get(initialDay).contains(monthlySalaryDtl.getEmployee().getId())){
                leaveExists = true;
            }
            if(leaveExists)
                leaveCounter +=1;
            else if(grossCalculated==false && (isWeekend || holidayExists || leaveExists)){
                Instant initialDateInstant = initialDay.atStartOfDay(ZoneId.systemDefault()).toInstant();
//                EmployeeSalary activeSalaryForTheDay = employeeSalaryRepository.findByEmployeeAndStatus(monthlySalaryDtl.getEmployee(), ActiveStatus.ACTIVE);  //employeeSalaryRepository.findByEmployee_IdAndSalaryStartDateIsLessThanEqualAndSalaryEndDateGreaterThanEqual(monthlySalaryDtl.getEmployee().getId(), initialDateInstant, initialDateInstant);
                gross = gross.add(activeSalaryForTheDay.getGross().divide(totalMonthDays, RoundingMode.HALF_DOWN));
                basic = basic.add(activeSalaryForTheDay.getBasic().divide(totalMonthDays, RoundingMode.HALF_DOWN));
                houseRent = houseRent.add(activeSalaryForTheDay.getHouseRent().divide(totalMonthDays, RoundingMode.HALF_DOWN));
                medicalAllowance = medicalAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getMedicalAllowance(), defaultAllowance.getMedicalAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));
                foodAllowance = foodAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getFoodAllowance(), defaultAllowance.getFoodAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));
                convinceAllowance = convinceAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getConvinceAllowance(), defaultAllowance.getConvinceAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));

            }

            log.debug("gross per day: "+gross);

            initialDay = initialDay.plusDays(1);
        }

        log.debug("End of gross calculation");
        log.debug("Total sumup gross: {}", gross);
        log.debug("Ending calculation");

        Optional<OverTime> overTime = overTimeRepository.findByYearAndMonthAndEmployee(monthlySalary.getYear(), monthlySalary.getMonth(), monthlySalaryDtl.getEmployee());
        if(overTime.isPresent()){
            monthlySalaryDtl.setOverTimeHour(overTime.get().getTotalOverTime());
            monthlySalaryDtl.setOverTimeSalaryHourly(overTime.get().getTotalAmount().divide(new BigDecimal(overTime.get().getTotalOverTime()),RoundingMode.HALF_UP));
            monthlySalaryDtl.setOverTimeSalary(overTime.get().getTotalAmount());
        }else{
            monthlySalaryDtl.setOverTimeHour(0.0);
            monthlySalaryDtl.setOverTimeSalaryHourly(BigDecimal.ZERO);
            monthlySalaryDtl.setOverTimeSalary(BigDecimal.ZERO);
        }

        log.debug("--------------------");
        log.debug("Actual gross: "+ activeSalaryForTheDay.getGross());
        log.debug("Found monthly gross: "+ gross);
        log.debug("--------------------");

        gross = activeSalaryForTheDay.getGross();
        basic = activeSalaryForTheDay.getBasic();
        houseRent = activeSalaryForTheDay.getHouseRent();
        medicalAllowance = activeSalaryForTheDay.getMedicalAllowance();
        foodAllowance = activeSalaryForTheDay.getFoodAllowance();
        convinceAllowance = activeSalaryForTheDay.getConvinceAllowance();




        monthlySalaryDtl.setTotalMonthDays(Integer.parseInt(totalMonthDays.toString()));
        monthlySalaryDtl.setTotalWorkingDays(this.totalWorkingDays);
        monthlySalaryDtl.setWeeklyLeave(this.totalWeekLeave);
        monthlySalaryDtl.setFestivalLeave(this.totalHolidays);
        monthlySalaryDtl.setRegularLeave(0);
        monthlySalaryDtl.setSickLeave(leaveCounter);
        monthlySalaryDtl.setCompensationLeave(0);
        monthlySalaryDtl.setPresent(attendanceMap.size());
        monthlySalaryDtl.setAbsent(this.totalWorkingDays-attendanceMap.size());
        monthlySalaryDtl.setPresentBonus(monthlySalaryDtl.getAbsent()>0 ? BigDecimal.ZERO:  new BigDecimal(150) );
        monthlySalaryDtl.setStampPrice(new BigDecimal(10));
        monthlySalaryDtl.setType(PayrollGenerationType.FULL);


        BigDecimal dailyGross = gross.divide(new BigDecimal(this.totalWorkingDays), RoundingMode.HALF_UP);
        BigDecimal dailyBasic = basic.divide(new BigDecimal(this.totalWorkingDays), RoundingMode.HALF_UP);
        BigDecimal dailyHouseRent = houseRent.divide(new BigDecimal(this.totalWorkingDays), RoundingMode.HALF_UP);
        BigDecimal dailyMedicalAllowance = medicalAllowance.divide(new BigDecimal(this.totalWorkingDays), RoundingMode.HALF_UP);
        BigDecimal dailyFoodAllowance = foodAllowance.divide(new BigDecimal(this.totalWorkingDays), RoundingMode.HALF_UP);
        BigDecimal dailyConvinceAllowance = convinceAllowance.divide(new BigDecimal(this.totalWorkingDays), RoundingMode.HALF_UP);
        if(monthlySalaryDtl.getAbsent()>0){
            gross = gross.subtract(dailyGross.multiply(new BigDecimal(monthlySalaryDtl.getAbsent())));
            basic = basic.subtract(dailyBasic.multiply(new BigDecimal(monthlySalaryDtl.getAbsent())));
            houseRent = houseRent.subtract(dailyHouseRent.multiply(new BigDecimal(monthlySalaryDtl.getAbsent())));
            medicalAllowance = medicalAllowance.subtract(dailyMedicalAllowance.multiply(new BigDecimal(monthlySalaryDtl.getAbsent())));
            foodAllowance = foodAllowance.subtract(dailyFoodAllowance.multiply(new BigDecimal(monthlySalaryDtl.getAbsent())));
            convinceAllowance = convinceAllowance.subtract(dailyConvinceAllowance.multiply(new BigDecimal(monthlySalaryDtl.getAbsent())));
        }

        monthlySalaryDtl.setGross(gross);

        BigDecimal absentFine = activeSalaryForTheDay.getGross().subtract(gross);
        log.debug("Absent fine: {} - {}:  {}",activeSalaryForTheDay.getGross(), gross, absentFine);
        monthlySalaryDtl.setAbsentFine(absentFine);
        log.debug("beginning calculation of month salary");
        log.debug("Fine {} and advance: {}", monthlySalaryDtl.getFine(), monthlySalaryDtl.getAdvance());
        BigDecimal monthSalary = gross.subtract(monthlySalaryDtl.getFine()).subtract(monthlySalaryDtl.getAdvance());
        log.debug("Month salary: {}-{}-{}: {}", gross, monthlySalaryDtl.getFine(), monthlySalaryDtl.getAdvance(), monthSalary);
        monthlySalaryDtl.setMonthSalary(monthSalary);
        monthlySalaryDtl.setTotalPayable(monthlySalaryDtl.getMonthSalary().add(monthlySalaryDtl.getPresentBonus()).subtract(monthlySalaryDtl.getStampPrice()));
        log.debug("Total salary: month salary+ present bonus - stamp price ===> {}+{}-{}: {}",
            monthlySalaryDtl.getMonthSalary(),
            monthlySalaryDtl.getPresentBonus(),
            monthlySalaryDtl.getStampPrice(),
            monthlySalaryDtl.getTotalPayable());
        monthlySalaryDtl.setBasic(basic);
        monthlySalaryDtl.setHouseRent(houseRent);
        monthlySalaryDtl.setMedicalAllowance(medicalAllowance);
        monthlySalaryDtl.setFoodAllowance(foodAllowance);
        monthlySalaryDtl.setConvinceAllowance(convinceAllowance);
        monthlySalaryDtl.setStatus(SalaryExecutionStatus.DONE);
        monthlySalaryDtl.setExecutedOn(Instant.now());
    }



    public PartialSalary assignPartialSalaryAndAllowances(PartialSalary partialSalary){
        DefaultAllowance defaultAllowance = defaultAllowanceRepository.findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
        List<AttendanceSummary> employeeAttendance = attendanceSummaryService.findAll(partialSalary.getEmployee(), partialSalary.getFromDate().atZone(ZoneId.systemDefault()).toLocalDate() , partialSalary.getToDate().atZone(ZoneId.systemDefault()).toLocalDate());
        Integer totalWorkingDays = employeeAttendance.size(); // = attendanceRepository.countAttendancesByEmployeeAndAttendanceTimeBetween(partialSalary.getEmployee(), Instant.from(partialSalary.getFromDate()) , Instant.from(partialSalary.getToDate()));

        String notes = partialSalary.getNote()!=null && partialSalary.getNote().length()>0?partialSalary.getNote(): "";
        if(employeeAttendance.size()<totalWorkingDays){
            notes = notes.concat(" Employee has total missing attendance in days: "+ (totalWorkingDays-employeeAttendance.size()));
        }

        BigDecimal gross, basic, houseRent, medicalAllowance, convinceAllowance, foodAllowance;
        gross = basic = houseRent = medicalAllowance = foodAllowance = convinceAllowance = BigDecimal.ZERO;
        BigDecimal totalMonthDays = new BigDecimal(partialSalary.getTotalMonthDays());
        for(AttendanceSummary attendance: employeeAttendance){
            EmployeeSalary activeSalaryForTheDay = attendance.getEmployeeSalary();
            gross = gross.add(activeSalaryForTheDay.getGross().divide(totalMonthDays));
            basic = basic.add(activeSalaryForTheDay.getBasic().divide(totalMonthDays));
            houseRent = houseRent.add(activeSalaryForTheDay.getHouseRent().divide(totalMonthDays));
            medicalAllowance = medicalAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getMedicalAllowance(), defaultAllowance.getMedicalAllowance()).divide(totalMonthDays));
            foodAllowance = foodAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getFoodAllowance(), defaultAllowance.getFoodAllowance()).divide(totalMonthDays));
            convinceAllowance = convinceAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getConvinceAllowance(), defaultAllowance.getConvinceAllowance()).divide(totalMonthDays));
        }
        partialSalary.setGross(gross);
        partialSalary.setBasic(basic);
        partialSalary.setHouseRent(houseRent);
        partialSalary.setMedicalAllowance(medicalAllowance);
        partialSalary.setFoodAllowance(foodAllowance);
        partialSalary.setConvinceAllowance(convinceAllowance);

        partialSalary.setStatus(SalaryExecutionStatus.DONE);
        partialSalary.setNote(notes);
        partialSalary.setExecutedBy(SecurityUtils.getCurrentUserLogin().get());
        partialSalary.setExecutedOn(Instant.now());
        return partialSalary;
    }

/*
* Only assign <b>Fine</b> only if no fine is paid in the current month.
* First we check whether a valid fine really exists or not.
* So, we need to first check the <b>FinePaymentHistory<b/> for the month.
* If not found, then we proceed.
* */
    private void assignFine(MonthlySalaryDtl monthlySalaryDtl){
        Integer year = monthlySalaryDtl.getMonthlySalary().getYear();
        MonthType monthType = monthlySalaryDtl.getMonthlySalary().getMonth();
        monthlySalaryDtl.setFine(BigDecimal.ZERO);
        if(fineRepository.existsByEmployee_IdAndPaymentStatusIn(monthlySalaryDtl.getEmployee().getId(), Arrays.asList( PaymentStatus.PAID))) {  // check whether fine exists
            Fine fine = fineRepository.findFineByEmployee_IdAndPaymentStatusIn(monthlySalaryDtl.getEmployee().getId(), Arrays.asList( PaymentStatus.PAID));  // if exists, then we fetch the fine
            monthlySalaryDtl.setFine(fine.getAmount());
        }
        else if(fineRepository.existsByEmployee_IdAndPaymentStatusIn(monthlySalaryDtl.getEmployee().getId(), Arrays.asList(PaymentStatus.IN_PROGRESS, PaymentStatus.NOT_PAID))){  // check whether fine exists
            Fine fine = fineRepository.findFineByEmployee_IdAndPaymentStatusIn(monthlySalaryDtl.getEmployee().getId(), Arrays.asList(PaymentStatus.NOT_PAID, PaymentStatus.IN_PROGRESS));  // if exists, then we fetch the fine

            // create fine payment history
            if(!finePaymentHistoryRepository.existsByFineAndYearAndMonthType(fine, year, monthType)){
                BigDecimal totalPayableAmount = fine.getAmountLeft().compareTo(fine.getMonthlyFineAmount())>0? fine.getMonthlyFineAmount(): fine.getAmountLeft();
                FinePaymentHistory finePaymentHistory = new FinePaymentHistory()
                    .year(year)
                    .monthType(monthType)
                    .fine(fine)
                    .amount(totalPayableAmount)
                    .beforeFine(fine.getAmountLeft())
                    .afterFine(fine.getAmountLeft().subtract(totalPayableAmount));
                monthlySalaryDtl.setFine(totalPayableAmount);
                finePaymentHistoryRepository.save(finePaymentHistory); // saving history

                fine.setAmountLeft(fine.getAmountLeft().subtract(totalPayableAmount));
                fine.setAmountPaid(fine.getAmountPaid().add(totalPayableAmount));

                if(fine.getAmountPaid().compareTo(fine.getAmount())>=0){ // checking whether the total paid amount is greater or equal than the fine amount
                    fine.setPaymentStatus(PaymentStatus.PAID); // if yes, then we will assign the fine as completed
                }else{
                    fine.setPaymentStatus(PaymentStatus.IN_PROGRESS); // else the fine payment is in progress
                }
                fineRepository.save(fine);
            }else{
                FinePaymentHistory finePaymentHistory = finePaymentHistoryRepository.findByFineAndYearAndMonthType(fine, year, monthType);
                monthlySalaryDtl.setFine(finePaymentHistory.getAmount());
            }
        }
    }

    /*
     * Only assign <b>Advance</b> only if no Advance is paid in the current month.
     * First we check whether a valid Advance really exists or not.
     * So, we need to first check the <b>AdvancePaymentHistory<b/> for the month.
     * If not found, then we proceed.
     * */
    private void assignAdvance(MonthlySalaryDtl monthlySalaryDtl){
        Integer year = monthlySalaryDtl.getMonthlySalary().getYear();
        MonthType monthType = monthlySalaryDtl.getMonthlySalary().getMonth();
        monthlySalaryDtl.setAdvance(BigDecimal.ZERO);
        if(advanceRepository.existsByEmployee_IdAndPaymentStatusIn(monthlySalaryDtl.getEmployee().getId(), Arrays.asList( PaymentStatus.PAID))) {  // check whether fine exists
            Advance advance = advanceRepository.findByEmployee_IdAndPaymentStatusIn(monthlySalaryDtl.getEmployee().getId(), Arrays.asList(PaymentStatus.PAID));
            monthlySalaryDtl.setAdvance(advance.getAmount());
        }
        else if(advanceRepository.existsByEmployee_IdAndPaymentStatusIn(monthlySalaryDtl.getEmployee().getId(), Arrays.asList(PaymentStatus.IN_PROGRESS, PaymentStatus.NOT_PAID))){  // check whether fine exists
            Advance advance = advanceRepository.findByEmployee_IdAndPaymentStatusIn(monthlySalaryDtl.getEmployee().getId(), Arrays.asList(PaymentStatus.NOT_PAID, PaymentStatus.IN_PROGRESS));  // if exists, then we fetch the fine

            // create advance payment history
            if(!advancePaymentHistoryRepository.existsByAdvanceAndYearAndMonthType(advance, year, monthType)){
                BigDecimal totalPayableAmount = advance.getAmountLeft().compareTo(advance.getMonthlyPaymentAmount())>0? advance.getMonthlyPaymentAmount(): advance.getAmountLeft();
                AdvancePaymentHistory advancePaymentHistory = new AdvancePaymentHistory()
                    .year(year)
                    .advance(advance)
                    .monthType(monthType)
                    .amount(totalPayableAmount)
                    .before(advance.getAmountLeft())
                    .after(advance.getAmountLeft().subtract(totalPayableAmount));

                monthlySalaryDtl.setAdvance(totalPayableAmount);
                advancePaymentHistoryRepository.save(advancePaymentHistory); // saving history

                advance.setAmountLeft(advance.getAmountLeft().subtract(totalPayableAmount));
                advance.setAmountPaid(advance.getAmountPaid().add(totalPayableAmount));

                if(advance.getAmountPaid().compareTo(advance.getAmount())>=0){ // checking whether the total paid amount is greater or equal than the advance amount
                    advance.setPaymentStatus(PaymentStatus.PAID); // if yes, then we will assign the advance as completed
                }else{
                    advance.setPaymentStatus(PaymentStatus.IN_PROGRESS); // else the advance payment is in progress
                }

                advanceRepository.save(advance);
            }else{
                monthlySalaryDtl.setAdvance(advancePaymentHistoryRepository.findByAdvanceAndYearAndMonthType(advance, year, monthType).getAmount());
            }
        }
    }

}
