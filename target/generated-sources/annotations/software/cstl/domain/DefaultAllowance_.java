package software.cstl.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.ActiveStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DefaultAllowance.class)
public abstract class DefaultAllowance_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> medicalAllowance;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> foodAllowancePercent;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> totalAllowance;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> foodAllowance;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> convinceAllowancePercent;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> festivalAllowancePercent;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> convinceAllowance;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> festivalAllowance;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> medicalAllowancePercent;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> basicPercent;
	public static volatile SingularAttribute<DefaultAllowance, Long> id;
	public static volatile SingularAttribute<DefaultAllowance, BigDecimal> basic;
	public static volatile SingularAttribute<DefaultAllowance, ActiveStatus> status;

	public static final String MEDICAL_ALLOWANCE = "medicalAllowance";
	public static final String FOOD_ALLOWANCE_PERCENT = "foodAllowancePercent";
	public static final String TOTAL_ALLOWANCE = "totalAllowance";
	public static final String FOOD_ALLOWANCE = "foodAllowance";
	public static final String CONVINCE_ALLOWANCE_PERCENT = "convinceAllowancePercent";
	public static final String FESTIVAL_ALLOWANCE_PERCENT = "festivalAllowancePercent";
	public static final String CONVINCE_ALLOWANCE = "convinceAllowance";
	public static final String FESTIVAL_ALLOWANCE = "festivalAllowance";
	public static final String MEDICAL_ALLOWANCE_PERCENT = "medicalAllowancePercent";
	public static final String BASIC_PERCENT = "basicPercent";
	public static final String ID = "id";
	public static final String BASIC = "basic";
	public static final String STATUS = "status";

}

