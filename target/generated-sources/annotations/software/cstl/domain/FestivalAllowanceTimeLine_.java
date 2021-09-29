package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.MonthType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FestivalAllowanceTimeLine.class)
public abstract class FestivalAllowanceTimeLine_ {

	public static volatile SingularAttribute<FestivalAllowanceTimeLine, MonthType> month;
	public static volatile SingularAttribute<FestivalAllowanceTimeLine, Integer> year;
	public static volatile SingularAttribute<FestivalAllowanceTimeLine, Long> id;

	public static final String MONTH = "month";
	public static final String YEAR = "year";
	public static final String ID = "id";

}

