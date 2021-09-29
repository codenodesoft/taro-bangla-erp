package software.cstl.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(JobHistory.class)
public abstract class JobHistory_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<JobHistory, Integer> serial;
	public static volatile SingularAttribute<JobHistory, Double> totalExperience;
	public static volatile SingularAttribute<JobHistory, String> organization;
	public static volatile SingularAttribute<JobHistory, BigDecimal> payScale;
	public static volatile SingularAttribute<JobHistory, LocalDate> from;
	public static volatile SingularAttribute<JobHistory, Long> id;
	public static volatile SingularAttribute<JobHistory, String> designation;
	public static volatile SingularAttribute<JobHistory, LocalDate> to;
	public static volatile SingularAttribute<JobHistory, Employee> employee;

	public static final String SERIAL = "serial";
	public static final String TOTAL_EXPERIENCE = "totalExperience";
	public static final String ORGANIZATION = "organization";
	public static final String PAY_SCALE = "payScale";
	public static final String FROM = "from";
	public static final String ID = "id";
	public static final String DESIGNATION = "designation";
	public static final String TO = "to";
	public static final String EMPLOYEE = "employee";

}

