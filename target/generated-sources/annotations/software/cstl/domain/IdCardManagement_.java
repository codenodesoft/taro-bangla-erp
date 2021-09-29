package software.cstl.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IdCardManagement.class)
public abstract class IdCardManagement_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<IdCardManagement, LocalDate> validTill;
	public static volatile SingularAttribute<IdCardManagement, String> ticketNo;
	public static volatile SingularAttribute<IdCardManagement, Long> id;
	public static volatile SingularAttribute<IdCardManagement, LocalDate> issueDate;
	public static volatile SingularAttribute<IdCardManagement, Employee> employee;
	public static volatile SingularAttribute<IdCardManagement, String> cardNo;

	public static final String VALID_TILL = "validTill";
	public static final String TICKET_NO = "ticketNo";
	public static final String ID = "id";
	public static final String ISSUE_DATE = "issueDate";
	public static final String EMPLOYEE = "employee";
	public static final String CARD_NO = "cardNo";

}

