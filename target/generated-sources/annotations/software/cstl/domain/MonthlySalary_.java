package software.cstl.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MonthlySalary.class)
public abstract class MonthlySalary_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<MonthlySalary, Instant> fromDate;
	public static volatile SingularAttribute<MonthlySalary, String> executedBy;
	public static volatile SingularAttribute<MonthlySalary, MonthType> month;
	public static volatile SingularAttribute<MonthlySalary, Integer> year;
	public static volatile SingularAttribute<MonthlySalary, Instant> toDate;
	public static volatile SetAttribute<MonthlySalary, MonthlySalaryDtl> monthlySalaryDtls;
	public static volatile SingularAttribute<MonthlySalary, Instant> executedOn;
	public static volatile SingularAttribute<MonthlySalary, Long> id;
	public static volatile SingularAttribute<MonthlySalary, Department> department;
	public static volatile SingularAttribute<MonthlySalary, SalaryExecutionStatus> status;

	public static final String FROM_DATE = "fromDate";
	public static final String EXECUTED_BY = "executedBy";
	public static final String MONTH = "month";
	public static final String YEAR = "year";
	public static final String TO_DATE = "toDate";
	public static final String MONTHLY_SALARY_DTLS = "monthlySalaryDtls";
	public static final String EXECUTED_ON = "executedOn";
	public static final String ID = "id";
	public static final String DEPARTMENT = "department";
	public static final String STATUS = "status";

}

