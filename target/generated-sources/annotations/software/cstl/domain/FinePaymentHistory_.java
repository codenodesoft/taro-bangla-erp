package software.cstl.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.MonthType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FinePaymentHistory.class)
public abstract class FinePaymentHistory_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<FinePaymentHistory, BigDecimal> amount;
	public static volatile SingularAttribute<FinePaymentHistory, Integer> year;
	public static volatile SingularAttribute<FinePaymentHistory, Fine> fine;
	public static volatile SingularAttribute<FinePaymentHistory, BigDecimal> beforeFine;
	public static volatile SingularAttribute<FinePaymentHistory, Long> id;
	public static volatile SingularAttribute<FinePaymentHistory, MonthType> monthType;
	public static volatile SingularAttribute<FinePaymentHistory, BigDecimal> afterFine;

	public static final String AMOUNT = "amount";
	public static final String YEAR = "year";
	public static final String FINE = "fine";
	public static final String BEFORE_FINE = "beforeFine";
	public static final String ID = "id";
	public static final String MONTH_TYPE = "monthType";
	public static final String AFTER_FINE = "afterFine";

}

