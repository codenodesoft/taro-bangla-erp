package software.cstl.domain;

import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.PayrollGenerationType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MonthlySalaryDtl.class)
public abstract class MonthlySalaryDtl_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> medicalAllowance;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> foodAllowancePercent;
	public static volatile SingularAttribute<MonthlySalaryDtl, String> note;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> convinceAllowancePercent;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> convinceAllowance;
	public static volatile SingularAttribute<MonthlySalaryDtl, PayrollGenerationType> type;
	public static volatile SingularAttribute<MonthlySalaryDtl, Employee> employee;
	public static volatile SingularAttribute<MonthlySalaryDtl, Integer> sickLeave;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> advance;
	public static volatile SingularAttribute<MonthlySalaryDtl, Integer> festivalLeave;
	public static volatile SingularAttribute<MonthlySalaryDtl, MonthlySalary> monthlySalary;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> stampPrice;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> fine;
	public static volatile SingularAttribute<MonthlySalaryDtl, Integer> weeklyLeave;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> medicalAllowancePercent;
	public static volatile SingularAttribute<MonthlySalaryDtl, Integer> absent;
	public static volatile SingularAttribute<MonthlySalaryDtl, Long> id;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> others;
	public static volatile SingularAttribute<MonthlySalaryDtl, String> executedBy;
	public static volatile SingularAttribute<MonthlySalaryDtl, Double> overTimeHour;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> overTimeSalary;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> foodAllowance;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> gross;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> houseRent;
	public static volatile SingularAttribute<MonthlySalaryDtl, Instant> executedOn;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> overTimeSalaryHourly;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> tax;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> houseRentPercent;
	public static volatile SingularAttribute<MonthlySalaryDtl, Integer> totalWorkingDays;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> absentFine;
	public static volatile SingularAttribute<MonthlySalaryDtl, Integer> regularLeave;
	public static volatile SingularAttribute<MonthlySalaryDtl, Integer> totalMonthDays;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> monthSalary;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> totalPayable;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> presentBonus;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> basicPercent;
	public static volatile SingularAttribute<MonthlySalaryDtl, BigDecimal> basic;
	public static volatile SingularAttribute<MonthlySalaryDtl, Integer> present;
	public static volatile SingularAttribute<MonthlySalaryDtl, Integer> compensationLeave;
	public static volatile SingularAttribute<MonthlySalaryDtl, SalaryExecutionStatus> status;

	public static final String MEDICAL_ALLOWANCE = "medicalAllowance";
	public static final String FOOD_ALLOWANCE_PERCENT = "foodAllowancePercent";
	public static final String NOTE = "note";
	public static final String CONVINCE_ALLOWANCE_PERCENT = "convinceAllowancePercent";
	public static final String CONVINCE_ALLOWANCE = "convinceAllowance";
	public static final String TYPE = "type";
	public static final String EMPLOYEE = "employee";
	public static final String SICK_LEAVE = "sickLeave";
	public static final String ADVANCE = "advance";
	public static final String FESTIVAL_LEAVE = "festivalLeave";
	public static final String MONTHLY_SALARY = "monthlySalary";
	public static final String STAMP_PRICE = "stampPrice";
	public static final String FINE = "fine";
	public static final String WEEKLY_LEAVE = "weeklyLeave";
	public static final String MEDICAL_ALLOWANCE_PERCENT = "medicalAllowancePercent";
	public static final String ABSENT = "absent";
	public static final String ID = "id";
	public static final String OTHERS = "others";
	public static final String EXECUTED_BY = "executedBy";
	public static final String OVER_TIME_HOUR = "overTimeHour";
	public static final String OVER_TIME_SALARY = "overTimeSalary";
	public static final String FOOD_ALLOWANCE = "foodAllowance";
	public static final String GROSS = "gross";
	public static final String HOUSE_RENT = "houseRent";
	public static final String EXECUTED_ON = "executedOn";
	public static final String OVER_TIME_SALARY_HOURLY = "overTimeSalaryHourly";
	public static final String TAX = "tax";
	public static final String HOUSE_RENT_PERCENT = "houseRentPercent";
	public static final String TOTAL_WORKING_DAYS = "totalWorkingDays";
	public static final String ABSENT_FINE = "absentFine";
	public static final String REGULAR_LEAVE = "regularLeave";
	public static final String TOTAL_MONTH_DAYS = "totalMonthDays";
	public static final String MONTH_SALARY = "monthSalary";
	public static final String TOTAL_PAYABLE = "totalPayable";
	public static final String PRESENT_BONUS = "presentBonus";
	public static final String BASIC_PERCENT = "basicPercent";
	public static final String BASIC = "basic";
	public static final String PRESENT = "present";
	public static final String COMPENSATION_LEAVE = "compensationLeave";
	public static final String STATUS = "status";

}

