package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EducationalInfo.class)
public abstract class EducationalInfo_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<EducationalInfo, String> institution;
	public static volatile SingularAttribute<EducationalInfo, Integer> passingYear;
	public static volatile SingularAttribute<EducationalInfo, byte[]> attachment;
	public static volatile SingularAttribute<EducationalInfo, Integer> serial;
	public static volatile SingularAttribute<EducationalInfo, Integer> courseDuration;
	public static volatile SingularAttribute<EducationalInfo, String> degree;
	public static volatile SingularAttribute<EducationalInfo, String> attachmentContentType;
	public static volatile SingularAttribute<EducationalInfo, Long> id;
	public static volatile SingularAttribute<EducationalInfo, Employee> employee;

	public static final String INSTITUTION = "institution";
	public static final String PASSING_YEAR = "passingYear";
	public static final String ATTACHMENT = "attachment";
	public static final String SERIAL = "serial";
	public static final String COURSE_DURATION = "courseDuration";
	public static final String DEGREE = "degree";
	public static final String ATTACHMENT_CONTENT_TYPE = "attachmentContentType";
	public static final String ID = "id";
	public static final String EMPLOYEE = "employee";

}

