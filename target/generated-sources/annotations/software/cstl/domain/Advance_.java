package software.cstl.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.PaymentStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Advance.class)
public abstract class Advance_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Advance, String> reason;
	public static volatile SingularAttribute<Advance, BigDecimal> paymentPercentage;
	public static volatile SingularAttribute<Advance, BigDecimal> amountLeft;
	public static volatile SingularAttribute<Advance, BigDecimal> amount;
	public static volatile SingularAttribute<Advance, BigDecimal> amountPaid;
	public static volatile SingularAttribute<Advance, LocalDate> providedOn;
	public static volatile SetAttribute<Advance, AdvancePaymentHistory> advancePaymentHistories;
	public static volatile SingularAttribute<Advance, Long> id;
	public static volatile SingularAttribute<Advance, BigDecimal> monthlyPaymentAmount;
	public static volatile SingularAttribute<Advance, Employee> employee;
	public static volatile SingularAttribute<Advance, PaymentStatus> paymentStatus;

	public static final String REASON = "reason";
	public static final String PAYMENT_PERCENTAGE = "paymentPercentage";
	public static final String AMOUNT_LEFT = "amountLeft";
	public static final String AMOUNT = "amount";
	public static final String AMOUNT_PAID = "amountPaid";
	public static final String PROVIDED_ON = "providedOn";
	public static final String ADVANCE_PAYMENT_HISTORIES = "advancePaymentHistories";
	public static final String ID = "id";
	public static final String MONTHLY_PAYMENT_AMOUNT = "monthlyPaymentAmount";
	public static final String EMPLOYEE = "employee";
	public static final String PAYMENT_STATUS = "paymentStatus";

}

