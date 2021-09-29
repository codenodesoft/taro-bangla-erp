package software.cstl.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.MonthType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdvancePaymentHistory.class)
public abstract class AdvancePaymentHistory_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<AdvancePaymentHistory, BigDecimal> amount;
	public static volatile SingularAttribute<AdvancePaymentHistory, Integer> year;
	public static volatile SingularAttribute<AdvancePaymentHistory, BigDecimal> before;
	public static volatile SingularAttribute<AdvancePaymentHistory, Long> id;
	public static volatile SingularAttribute<AdvancePaymentHistory, BigDecimal> after;
	public static volatile SingularAttribute<AdvancePaymentHistory, MonthType> monthType;
	public static volatile SingularAttribute<AdvancePaymentHistory, Advance> advance;

	public static final String AMOUNT = "amount";
	public static final String YEAR = "year";
	public static final String BEFORE = "before";
	public static final String ID = "id";
	public static final String AFTER = "after";
	public static final String MONTH_TYPE = "monthType";
	public static final String ADVANCE = "advance";

}

