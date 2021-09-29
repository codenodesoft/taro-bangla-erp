package software.cstl.service.reports;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.OverTime;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.PayrollReportType;
import software.cstl.repository.*;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.dto.salary.EmployeeInfoDto;
import software.cstl.service.dto.salary.SalaryDetailsDto;
import software.cstl.service.dto.salary.SalaryReportDto;
import software.cstl.utils.CodeNodeErpUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Transactional(readOnly = true)
@AllArgsConstructor
public class PayrollExcelReportGenerator {
    private final MonthlySalaryRepository monthlySalaryRepository;
    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;
    private final CompanyRepository companyRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceSummaryService attendanceSummaryService;
    private final OverTimeRepository overTimeRepository;
    private final FineRepository fineRepository;
    private final FinePaymentHistoryRepository finePaymentHistoryRepository;
    private final AdvanceRepository advanceRepository;
    private final AdvancePaymentHistoryRepository advancePaymentHistoryRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;

    public ByteArrayInputStream createReport(Integer year, MonthType month, Long departmentId, Long designationId, PayrollReportType payrollReportType) throws IOException {

        YearMonth yearMonth = YearMonth.of(year, month.ordinal()+1);
        LocalDate initialDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate lastDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());

        String departmentName = departmentId!=null? departmentRepository.getOne(departmentId).getNameInBangla(): "";
        String startDate = initialDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String endDate = lastDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));


        List<MonthlySalaryDtl> monthlySalaryDtls = new ArrayList<>();
        if(departmentId!=null && designationId!=null){
            monthlySalaryDtls = monthlySalaryDtlRepository.findAllByMonthlySalary_YearAndMonthlySalary_MonthAndMonthlySalary_Department_IdAndEmployee_Designation_id(year, month, departmentId, designationId);
        }else if(departmentId!=null && designationId==null){
            monthlySalaryDtls = monthlySalaryDtlRepository.findAllByMonthlySalary_YearAndMonthlySalary_MonthAndEmployee_Department_Id(year, month, departmentId);
        }else if(departmentId==null && designationId==null){
            monthlySalaryDtls = monthlySalaryDtlRepository.findAllByMonthlySalary_YearAndMonthlySalary_Month(year, month);
        }

        return createExclReportBody(payrollReportType, departmentName, startDate, endDate, year, month, monthlySalaryDtls, "/templates/jxls/PayrollReport.xls");
    }

    public ByteArrayInputStream createExclReportBody(PayrollReportType payrollReportType, String departmentName, String startDate, String endDate, Integer year, MonthType month, List<MonthlySalaryDtl> monthlySalaryDtls, String excelFileLocation) throws IOException {

        Optional<List<OverTime>> overTimes = overTimeRepository.findByYearAndMonth(year, month);
        Map<Long, OverTime> employeeMapOverTime = new HashMap<>();
        if(overTimes.isPresent()){
            for(OverTime overTime: overTimes.get()){
                employeeMapOverTime.put(overTime.getEmployee().getId(), overTime);
            }
        }
        Integer totalMonthDays=0;
        YearMonth yearMonth = YearMonth.of(year, month.ordinal()+1);
        totalMonthDays = yearMonth.lengthOfMonth();


        List<SalaryReportDto> salaryReportDtoList = new ArrayList<>();

        for(int i = 0; i< monthlySalaryDtls.size(); i++){
            MonthlySalaryDtl monthlySalaryDtl = monthlySalaryDtls.get(i);

            if(monthlySalaryDtl.getGross()==null)
                continue;
            SalaryReportDto salaryReportDto = new SalaryReportDto();
            salaryReportDto.setSerial(i+1);
            salaryReportDto.setTotalMonthDays(CodeNodeErpUtils.getDigitBanglaFromEnglish(totalMonthDays.toString()));
            String monthName = CodeNodeErpUtils.convertMonthToBangla(monthlySalaryDtl.getMonthlySalary().getMonth().name()+", "+monthlySalaryDtl.getMonthlySalary().getYear());
            salaryReportDto.setMonthName(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthName));
            EmployeeInfoDto employeeInfoDto = new EmployeeInfoDto();
            employeeInfoDto.setName(monthlySalaryDtl.getEmployee().getPersonalInfo()==null?"No-Name": monthlySalaryDtl.getEmployee().getPersonalInfo().getBanglaName());
            employeeInfoDto.setDesignation(monthlySalaryDtl.getEmployee().getDesignation().getNameInBangla());
            employeeInfoDto.setEmployeeId(monthlySalaryDtl.getEmployee().getLocalId());
            employeeInfoDto.setJoiningDate(monthlySalaryDtl.getEmployee().getJoiningDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            employeeInfoDto.setCardNo(monthlySalaryDtl.getEmployee().getAttendanceMachineId());
            if(monthlySalaryDtl.getEmployee().getDepartment().getNameInBangla()!=null)
                employeeInfoDto.setDepartmentName(monthlySalaryDtl.getEmployee().getDepartment().getNameInBangla());
            else
                employeeInfoDto.setDepartmentName(monthlySalaryDtl.getEmployee().getDepartment().getName());
            salaryReportDto.setEmployeeInfoDto(employeeInfoDto);

            salaryReportDto.setGrade(ObjectUtils.defaultIfNull(monthlySalaryDtl.getEmployee().getGrade().getName(),""));

            SalaryDetailsDto salaryDetailsDto = new SalaryDetailsDto();
            salaryDetailsDto.setTotalSalary( CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getGross()));
            salaryDetailsDto.setMainSalary( CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getBasic()));
            salaryDetailsDto.setHouseRent( CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getHouseRent()));
            salaryReportDto.setSalaryDetailsDto(salaryDetailsDto);

            salaryReportDto.setFoodAllowance(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getFoodAllowance()));
            salaryReportDto.setTransportAllowance(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getConvinceAllowance()));
            salaryReportDto.setMainSalary(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getBasic()));
            salaryReportDto.setHouseRent(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getHouseRent()));
            salaryReportDto.setMedicalAllowance(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getMedicalAllowance()));
            salaryReportDto.setMonthlySalary(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getMonthSalary()));
            salaryReportDto.setTotalAttendance(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthlySalaryDtl.getTotalMonthDays().toString()));
            salaryReportDto.setRegularLeave(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthlySalaryDtl.getRegularLeave().toString()));
            salaryReportDto.setSickLeave(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthlySalaryDtl.getSickLeave().toString()));
            salaryReportDto.setEarnedLeave(CodeNodeErpUtils.getDigitBanglaFromEnglish("0"));
            salaryReportDto.setCompensationLeave(CodeNodeErpUtils.getDigitBanglaFromEnglish("0"));
            salaryReportDto.setFestivalLeave(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthlySalaryDtl.getFestivalLeave().toString()));
            Integer totalLeave = monthlySalaryDtl.getSickLeave()+monthlySalaryDtl.getRegularLeave();
            salaryReportDto.setTotalLeave(CodeNodeErpUtils.getDigitBanglaFromEnglish(totalLeave.toString()));
            salaryReportDto.setWeeklyLeave(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthlySalaryDtl.getWeeklyLeave().toString()));
            salaryReportDto.setPresent(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthlySalaryDtl.getPresent().toString()));
            salaryReportDto.setAbsent(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthlySalaryDtl.getAbsent().toString()));
            Integer totalProviddedLeave = (Integer.parseInt(salaryReportDto.getRegularLeave())+Integer.parseInt(salaryReportDto.getSickLeave())+Integer.parseInt(salaryReportDto.getEarnedLeave())+Integer.parseInt(salaryReportDto.getFestivalLeave())+Integer.parseInt(salaryReportDto.getWeeklyLeave()));
            salaryReportDto.setTotalProvidedLeave(CodeNodeErpUtils.getDigitBanglaFromEnglish(totalProviddedLeave.toString()));
            salaryReportDto.setTotalWorkingDays(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthlySalaryDtl.getTotalMonthDays().toString()));
            if(employeeMapOverTime.containsKey(monthlySalaryDtl.getEmployee().getId())){
                OverTime overTime = employeeMapOverTime.get(monthlySalaryDtl.getEmployee().getId());

                if(payrollReportType.equals(PayrollReportType.WITH_FULL_CALCULATION) ){
                    monthlySalaryDtl.setOverTimeHour(overTime.getTotalOverTime());
                    monthlySalaryDtl.setOverTimeSalaryHourly(overTime.getTotalAmount().divide(new BigDecimal(overTime.getTotalOverTime()), BigDecimal.ROUND_UP));
                    monthlySalaryDtl.setOverTimeSalary(overTime.getTotalAmount());
                    monthlySalaryDtl.setTotalPayable(monthlySalaryDtl.getTotalPayable().add(overTime.getTotalAmount()));
                }else if(payrollReportType.equals(PayrollReportType.WITH_GOVT_CALCULATION)){
                    monthlySalaryDtl.setOverTimeHour(overTime.getOfficialOverTime());
                    monthlySalaryDtl.setOverTimeSalaryHourly(overTime.getOfficialAmount().divide(new BigDecimal(overTime.getOfficialOverTime()), BigDecimal.ROUND_HALF_UP));
                    monthlySalaryDtl.setOverTimeSalary(overTime.getOfficialAmount());
                    monthlySalaryDtl.setTotalPayable(monthlySalaryDtl.getTotalPayable().add(overTime.getOfficialAmount()));
                }else if(payrollReportType.equals(PayrollReportType.WITH_EXTRA_CALCULATION)){
                    monthlySalaryDtl.setOverTimeHour(overTime.getExtraOverTime());
                    monthlySalaryDtl.setOverTimeSalaryHourly(overTime.getExtraOverTime()==0?BigDecimal.ZERO:  overTime.getExtraAmount().divide(new BigDecimal(overTime.getExtraOverTime()), BigDecimal.ROUND_HALF_UP));
                    monthlySalaryDtl.setOverTimeSalary(overTime.getExtraAmount());
                    monthlySalaryDtl.setTotalPayable(monthlySalaryDtl.getTotalPayable().add(overTime.getExtraAmount()));
                }
            }

            salaryReportDto.setOvertimeHour(CodeNodeErpUtils.getDigitBanglaFromEnglish(monthlySalaryDtl.getOverTimeHour().toString()));
            salaryReportDto.setOverTimePerHour(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getOverTimeSalaryHourly()));
            salaryReportDto.setTotalOverTimeSalary(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getOverTimeSalary()));
            salaryReportDto.setAttendanceBonus(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getPresentBonus()));
            salaryReportDto.setStamp(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getStampPrice()));
            salaryReportDto.setAdvance(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getAdvance()));
            salaryReportDto.setFine(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getFine()));
            EmployeeSalary employeeSalary= employeeSalaryRepository.findByEmployeeAndStatus(monthlySalaryDtl.getEmployee(), ActiveStatus.ACTIVE);
            if(employeeSalary!=null){
                salaryReportDto.setAbsentDeduction(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getAbsentFine()));
            }else{
                salaryReportDto.setAbsentDeduction(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(BigDecimal.ZERO));
            }
            salaryReportDto.setTotalPayable(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getTotalPayable()));
            salaryReportDtoList.add(salaryReportDto);
        }
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        InputStream is = PayrollExcelReportGenerator.class.getResourceAsStream(excelFileLocation);  // "/templates/jxls/PayrollReport.xls"
        Context context = new Context();
        context.putVar("department", departmentName);
        context.putVar("startDate",startDate==null?"": CodeNodeErpUtils.getDigitBanglaFromEnglish(startDate));
        context.putVar("endDate", endDate==null?"": CodeNodeErpUtils.getDigitBanglaFromEnglish(endDate));
        context.putVar("salaryReport", salaryReportDtoList);
        jxlsHelper.processTemplate(is, bis, context);
        return new ByteArrayInputStream(bis.toByteArray());
    }
}
