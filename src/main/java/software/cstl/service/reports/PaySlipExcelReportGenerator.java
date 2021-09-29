package software.cstl.service.reports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.MonthlySalary;
import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.PayrollReportType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;
import software.cstl.repository.*;
import software.cstl.service.AttendanceSummaryService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional
public class PaySlipExcelReportGenerator {

    private final MonthlySalaryRepository monthlySalaryRepository;
    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;
    private final PayrollExcelReportGenerator payrollExcelReportGenerator;

    public ByteArrayInputStream createReport(Long employeeMonthlySalaryDtlId, PayrollReportType payrollReportType) throws IOException{
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAllById(employeeMonthlySalaryDtlId);
        int year = monthlySalaryDtlList.get(0).getMonthlySalary().getYear();
        BigDecimal salary = monthlySalaryDtlList.get(0).getTotalPayable();
        MonthType month = monthlySalaryDtlList.get(0).getMonthlySalary().getMonth();
        return createReportBody(monthlySalaryDtlList, payrollReportType, year, month);
    }

    public ByteArrayInputStream createReportsForAll(Long monthlySalaryId, PayrollReportType payrollReportType) throws IOException{
        MonthlySalary monthlySalary = monthlySalaryRepository.getOne(monthlySalaryId);
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAllByMonthlySalary_Id(monthlySalaryId)
            .stream()
            .filter(s-> s.getStatus().equals(SalaryExecutionStatus.DONE))
            .collect(Collectors.toList());
        int year = monthlySalary.getYear();
        MonthType month = monthlySalary.getMonth();
        return createReportBody(monthlySalaryDtlList, payrollReportType, year, month);
    }

    public ByteArrayInputStream createReportBody(List<MonthlySalaryDtl> monthlySalaryDtlList, PayrollReportType payrollReportType, int year, MonthType month) throws IOException {
        return payrollExcelReportGenerator.createExclReportBody(payrollReportType, null,  null, null, year, month,monthlySalaryDtlList, "/templates/jxls/pay-slip.xls" );
    }

}
