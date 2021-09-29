package software.cstl.domain;

import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PartialSalary.class)
public abstract class PartialSalary_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<PartialSalary, BigDecimal> medicalAllowance;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> foodAllowancePercent;
	public static volatile SingularAttribute<PartialSalary, String> executedBy;
	public static volatile SingularAttribute<PartialSalary, String> note;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> foodAllowance;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> gross;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> convinceAllowancePercent;
	public static volatile SingularAttribute<PartialSalary, Integer> year;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> houseRent;
	public static volatile SingularAttribute<PartialSalary, Instant> toDate;
	public static volatile SingularAttribute<PartialSalary, Instant> executedOn;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> convinceAllowance;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> houseRentPercent;
	public static volatile SingularAttribute<PartialSalary, Employee> employee;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> advance;
	public static volatile SingularAttribute<PartialSalary, Instant> fromDate;
	public static volatile SingularAttribute<PartialSalary, Integer> totalMonthDays;
	public static volatile SingularAttribute<PartialSalary, MonthType> month;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> fine;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> medicalAllowancePercent;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> basicPercent;
	public static volatile SingularAttribute<PartialSalary, Long> id;
	public static volatile SingularAttribute<PartialSalary, BigDecimal> basic;
	public static volatile SingularAttribute<PartialSalary, SalaryExecutionStatus> status;

	public static final String MEDICAL_ALLOWANCE = "medicalAllowance";
	public static final String FOOD_ALLOWANCE_PERCENT = "foodAllowancePercent";
	public static final String EXECUTED_BY = "executedBy";
	public static final String NOTE = "note";
	public static final String FOOD_ALLOWANCE = "foodAllowance";
	public static final String GROSS = "gross";
	public static final String CONVINCE_ALLOWANCE_PERCENT = "convinceAllowancePercent";
	public static final String YEAR = "year";
	public static final String HOUSE_RENT = "houseRent";
	public static final String TO_DATE = "toDate";
	public static final String EXECUTED_ON = "executedOn";
	public static final String CONVINCE_ALLOWANCE = "convinceAllowance";
	public static final String HOUSE_RENT_PERCENT = "houseRentPercent";
	public static final String EMPLOYEE = "employee";
	public static final String ADVANCE = "advance";
	public static final String FROM_DATE = "fromDate";
	public static final String TOTAL_MONTH_DAYS = "totalMonthDays";
	public static final String MONTH = "month";
	public static final String FINE = "fine";
	public static final String MEDICAL_ALLOWANCE_PERCENT = "medicalAllowancePercent";
	public static final String BASIC_PERCENT = "basicPercent";
	public static final String ID = "id";
	public static final String BASIC = "basic";
	public static final String STATUS = "status";

}

