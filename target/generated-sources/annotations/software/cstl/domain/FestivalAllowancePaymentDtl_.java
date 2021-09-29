package software.cstl.domain;

import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.SalaryExecutionStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FestivalAllowancePaymentDtl.class)
public abstract class FestivalAllowancePaymentDtl_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<FestivalAllowancePaymentDtl, String> executedBy;
	public static volatile SingularAttribute<FestivalAllowancePaymentDtl, String> note;
	public static volatile SingularAttribute<FestivalAllowancePaymentDtl, BigDecimal> amount;
	public static volatile SingularAttribute<FestivalAllowancePaymentDtl, FestivalAllowancePayment> festivalAllowancePayment;
	public static volatile SingularAttribute<FestivalAllowancePaymentDtl, Instant> executedOn;
	public static volatile SingularAttribute<FestivalAllowancePaymentDtl, Long> id;
	public static volatile SingularAttribute<FestivalAllowancePaymentDtl, Employee> employee;
	public static volatile SingularAttribute<FestivalAllowancePaymentDtl, SalaryExecutionStatus> status;

	public static final String EXECUTED_BY = "executedBy";
	public static final String NOTE = "note";
	public static final String AMOUNT = "amount";
	public static final String FESTIVAL_ALLOWANCE_PAYMENT = "festivalAllowancePayment";
	public static final String EXECUTED_ON = "executedOn";
	public static final String ID = "id";
	public static final String EMPLOYEE = "employee";
	public static final String STATUS = "status";

}

