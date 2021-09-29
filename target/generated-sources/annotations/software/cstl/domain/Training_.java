package software.cstl.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Training.class)
public abstract class Training_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Training, byte[]> attachment;
	public static volatile SingularAttribute<Training, Integer> serial;
	public static volatile SingularAttribute<Training, String> name;
	public static volatile SingularAttribute<Training, LocalDate> receivedOn;
	public static volatile SingularAttribute<Training, String> attachmentContentType;
	public static volatile SingularAttribute<Training, Long> id;
	public static volatile SingularAttribute<Training, Employee> employee;
	public static volatile SingularAttribute<Training, String> trainingInstitute;

	public static final String ATTACHMENT = "attachment";
	public static final String SERIAL = "serial";
	public static final String NAME = "name";
	public static final String RECEIVED_ON = "receivedOn";
	public static final String ATTACHMENT_CONTENT_TYPE = "attachmentContentType";
	public static final String ID = "id";
	public static final String EMPLOYEE = "employee";
	public static final String TRAINING_INSTITUTE = "trainingInstitute";

}

