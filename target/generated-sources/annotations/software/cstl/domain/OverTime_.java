package software.cstl.domain;

import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.MonthType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OverTime.class)
public abstract class OverTime_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<OverTime, String> executedBy;
	public static volatile SingularAttribute<OverTime, String> note;
	public static volatile SingularAttribute<OverTime, BigDecimal> extraAmount;
	public static volatile SingularAttribute<OverTime, Integer> year;
	public static volatile SingularAttribute<OverTime, Instant> toDate;
	public static volatile SingularAttribute<OverTime, Instant> executedOn;
	public static volatile SingularAttribute<OverTime, Employee> employee;
	public static volatile SingularAttribute<OverTime, Double> extraOverTime;
	public static volatile SingularAttribute<OverTime, Instant> fromDate;
	public static volatile SingularAttribute<OverTime, BigDecimal> totalAmount;
	public static volatile SingularAttribute<OverTime, Double> officialOverTime;
	public static volatile SingularAttribute<OverTime, MonthType> month;
	public static volatile SingularAttribute<OverTime, Double> totalOverTime;
	public static volatile SingularAttribute<OverTime, Long> id;
	public static volatile SingularAttribute<OverTime, Designation> designation;
	public static volatile SingularAttribute<OverTime, BigDecimal> officialAmount;

	public static final String EXECUTED_BY = "executedBy";
	public static final String NOTE = "note";
	public static final String EXTRA_AMOUNT = "extraAmount";
	public static final String YEAR = "year";
	public static final String TO_DATE = "toDate";
	public static final String EXECUTED_ON = "executedOn";
	public static final String EMPLOYEE = "employee";
	public static final String EXTRA_OVER_TIME = "extraOverTime";
	public static final String FROM_DATE = "fromDate";
	public static final String TOTAL_AMOUNT = "totalAmount";
	public static final String OFFICIAL_OVER_TIME = "officialOverTime";
	public static final String MONTH = "month";
	public static final String TOTAL_OVER_TIME = "totalOverTime";
	public static final String ID = "id";
	public static final String DESIGNATION = "designation";
	public static final String OFFICIAL_AMOUNT = "officialAmount";

}

