package software.cstl.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.WeekDay;
import software.cstl.domain.enumeration.WeekendStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Weekend.class)
public abstract class Weekend_ {

	public static volatile SingularAttribute<Weekend, LocalDate> endDate;
	public static volatile SingularAttribute<Weekend, Long> id;
	public static volatile SingularAttribute<Weekend, WeekDay> day;
	public static volatile SingularAttribute<Weekend, LocalDate> startDate;
	public static volatile SingularAttribute<Weekend, String> remarks;
	public static volatile SingularAttribute<Weekend, WeekendStatus> status;

	public static final String END_DATE = "endDate";
	public static final String ID = "id";
	public static final String DAY = "day";
	public static final String START_DATE = "startDate";
	public static final String REMARKS = "remarks";
	public static final String STATUS = "status";

}

