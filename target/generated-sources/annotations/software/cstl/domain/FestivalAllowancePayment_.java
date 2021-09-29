package software.cstl.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FestivalAllowancePayment.class)
public abstract class FestivalAllowancePayment_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<FestivalAllowancePayment, String> executedBy;
	public static volatile SingularAttribute<FestivalAllowancePayment, MonthType> month;
	public static volatile SingularAttribute<FestivalAllowancePayment, Integer> year;
	public static volatile SetAttribute<FestivalAllowancePayment, FestivalAllowancePaymentDtl> festivalAllowancePaymentDtls;
	public static volatile SingularAttribute<FestivalAllowancePayment, Instant> executedOn;
	public static volatile SingularAttribute<FestivalAllowancePayment, Long> id;
	public static volatile SingularAttribute<FestivalAllowancePayment, Designation> designation;
	public static volatile SingularAttribute<FestivalAllowancePayment, SalaryExecutionStatus> status;

	public static final String EXECUTED_BY = "executedBy";
	public static final String MONTH = "month";
	public static final String YEAR = "year";
	public static final String FESTIVAL_ALLOWANCE_PAYMENT_DTLS = "festivalAllowancePaymentDtls";
	public static final String EXECUTED_ON = "executedOn";
	public static final String ID = "id";
	public static final String DESIGNATION = "designation";
	public static final String STATUS = "status";

}

