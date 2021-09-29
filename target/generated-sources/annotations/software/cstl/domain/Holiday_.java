package software.cstl.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Holiday.class)
public abstract class Holiday_ {

	public static volatile SingularAttribute<Holiday, HolidayType> holidayType;
	public static volatile SingularAttribute<Holiday, Integer> totalDays;
	public static volatile SingularAttribute<Holiday, LocalDate> from;
	public static volatile SingularAttribute<Holiday, Long> id;
	public static volatile SingularAttribute<Holiday, LocalDate> to;

	public static final String HOLIDAY_TYPE = "holidayType";
	public static final String TOTAL_DAYS = "totalDays";
	public static final String FROM = "from";
	public static final String ID = "id";
	public static final String TO = "to";

}

