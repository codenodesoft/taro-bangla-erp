package software.cstl.domain;

import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.InsuranceProcessType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmployeeSalary.class)
public abstract class EmployeeSalary_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> medicalAllowance;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> foodAllowancePercent;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> convinceAllowancePercent;
	public static volatile SingularAttribute<EmployeeSalary, ActiveStatus> specialAllowanceActiveStatus;
	public static volatile SingularAttribute<EmployeeSalary, String> insuranceDescription;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> convinceAllowance;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> specialAllowance;
	public static volatile SingularAttribute<EmployeeSalary, Employee> employee;
	public static volatile SingularAttribute<EmployeeSalary, ActiveStatus> insuranceActiveStatus;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> incrementPercentage;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> medicalAllowancePercent;
	public static volatile SingularAttribute<EmployeeSalary, Long> id;
	public static volatile SingularAttribute<EmployeeSalary, String> specialAllowanceDescription;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> insuranceAmount;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> incrementAmount;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> totalAllowance;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> foodAllowance;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> gross;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> houseRent;
	public static volatile SingularAttribute<EmployeeSalary, InsuranceProcessType> insuranceProcessType;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> houseRentPercent;
	public static volatile SingularAttribute<EmployeeSalary, Instant> nextIncrementDate;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> insurancePercent;
	public static volatile SingularAttribute<EmployeeSalary, Instant> salaryStartDate;
	public static volatile SingularAttribute<EmployeeSalary, Instant> salaryEndDate;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> specialAllowancePercent;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> basicPercent;
	public static volatile SingularAttribute<EmployeeSalary, BigDecimal> basic;
	public static volatile SingularAttribute<EmployeeSalary, ActiveStatus> status;

	public static final String MEDICAL_ALLOWANCE = "medicalAllowance";
	public static final String FOOD_ALLOWANCE_PERCENT = "foodAllowancePercent";
	public static final String CONVINCE_ALLOWANCE_PERCENT = "convinceAllowancePercent";
	public static final String SPECIAL_ALLOWANCE_ACTIVE_STATUS = "specialAllowanceActiveStatus";
	public static final String INSURANCE_DESCRIPTION = "insuranceDescription";
	public static final String CONVINCE_ALLOWANCE = "convinceAllowance";
	public static final String SPECIAL_ALLOWANCE = "specialAllowance";
	public static final String EMPLOYEE = "employee";
	public static final String INSURANCE_ACTIVE_STATUS = "insuranceActiveStatus";
	public static final String INCREMENT_PERCENTAGE = "incrementPercentage";
	public static final String MEDICAL_ALLOWANCE_PERCENT = "medicalAllowancePercent";
	public static final String ID = "id";
	public static final String SPECIAL_ALLOWANCE_DESCRIPTION = "specialAllowanceDescription";
	public static final String INSURANCE_AMOUNT = "insuranceAmount";
	public static final String INCREMENT_AMOUNT = "incrementAmount";
	public static final String TOTAL_ALLOWANCE = "totalAllowance";
	public static final String FOOD_ALLOWANCE = "foodAllowance";
	public static final String GROSS = "gross";
	public static final String HOUSE_RENT = "houseRent";
	public static final String INSURANCE_PROCESS_TYPE = "insuranceProcessType";
	public static final String HOUSE_RENT_PERCENT = "houseRentPercent";
	public static final String NEXT_INCREMENT_DATE = "nextIncrementDate";
	public static final String INSURANCE_PERCENT = "insurancePercent";
	public static final String SALARY_START_DATE = "salaryStartDate";
	public static final String SALARY_END_DATE = "salaryEndDate";
	public static final String SPECIAL_ALLOWANCE_PERCENT = "specialAllowancePercent";
	public static final String BASIC_PERCENT = "basicPercent";
	public static final String BASIC = "basic";
	public static final String STATUS = "status";

}

