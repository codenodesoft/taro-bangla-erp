package software.cstl.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.PaymentStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fine.class)
public abstract class Fine_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Fine, String> reason;
	public static volatile SingularAttribute<Fine, BigDecimal> amountLeft;
	public static volatile SingularAttribute<Fine, BigDecimal> amount;
	public static volatile SetAttribute<Fine, FinePaymentHistory> finePaymentHistories;
	public static volatile SingularAttribute<Fine, BigDecimal> amountPaid;
	public static volatile SingularAttribute<Fine, BigDecimal> monthlyFineAmount;
	public static volatile SingularAttribute<Fine, Long> id;
	public static volatile SingularAttribute<Fine, LocalDate> finedOn;
	public static volatile SingularAttribute<Fine, Employee> employee;
	public static volatile SingularAttribute<Fine, PaymentStatus> paymentStatus;
	public static volatile SingularAttribute<Fine, BigDecimal> finePercentage;

	public static final String REASON = "reason";
	public static final String AMOUNT_LEFT = "amountLeft";
	public static final String AMOUNT = "amount";
	public static final String FINE_PAYMENT_HISTORIES = "finePaymentHistories";
	public static final String AMOUNT_PAID = "amountPaid";
	public static final String MONTHLY_FINE_AMOUNT = "monthlyFineAmount";
	public static final String ID = "id";
	public static final String FINED_ON = "finedOn";
	public static final String EMPLOYEE = "employee";
	public static final String PAYMENT_STATUS = "paymentStatus";
	public static final String FINE_PERCENTAGE = "finePercentage";

}

