package software.cstl.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.BloodGroupType;
import software.cstl.domain.enumeration.GenderType;
import software.cstl.domain.enumeration.MaritalStatus;
import software.cstl.domain.enumeration.ReligionType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersonalInfo.class)
public abstract class PersonalInfo_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<PersonalInfo, String> fatherName;
	public static volatile SingularAttribute<PersonalInfo, String> birthRegistration;
	public static volatile SingularAttribute<PersonalInfo, String> fatherNameBangla;
	public static volatile SingularAttribute<PersonalInfo, GenderType> gender;
	public static volatile SingularAttribute<PersonalInfo, String> nationalIdAttachmentContentType;
	public static volatile SingularAttribute<PersonalInfo, String> spouseNameBangla;
	public static volatile SingularAttribute<PersonalInfo, Employee> employee;
	public static volatile SingularAttribute<PersonalInfo, byte[]> nationalIdAttachment;
	public static volatile SingularAttribute<PersonalInfo, BloodGroupType> bloodGroup;
	public static volatile SingularAttribute<PersonalInfo, String> birthRegistrationAttachmentContentType;
	public static volatile SingularAttribute<PersonalInfo, Long> id;
	public static volatile SingularAttribute<PersonalInfo, String> nationalIdAttachmentId;
	public static volatile SingularAttribute<PersonalInfo, Double> height;
	public static volatile SingularAttribute<PersonalInfo, String> emergencyContact;
	public static volatile SingularAttribute<PersonalInfo, String> motherName;
	public static volatile SingularAttribute<PersonalInfo, byte[]> photo;
	public static volatile SingularAttribute<PersonalInfo, String> photoId;
	public static volatile SingularAttribute<PersonalInfo, LocalDate> dateOfBirth;
	public static volatile SingularAttribute<PersonalInfo, String> birthRegistrationAttachmentId;
	public static volatile SingularAttribute<PersonalInfo, String> banglaName;
	public static volatile SingularAttribute<PersonalInfo, String> motherNameBangla;
	public static volatile SingularAttribute<PersonalInfo, ReligionType> religion;
	public static volatile SingularAttribute<PersonalInfo, String> nationalId;
	public static volatile SingularAttribute<PersonalInfo, String> name;
	public static volatile SingularAttribute<PersonalInfo, byte[]> birthRegistrationAttachment;
	public static volatile SingularAttribute<PersonalInfo, String> spouseName;
	public static volatile SingularAttribute<PersonalInfo, String> photoContentType;
	public static volatile SingularAttribute<PersonalInfo, MaritalStatus> maritalStatus;

	public static final String FATHER_NAME = "fatherName";
	public static final String BIRTH_REGISTRATION = "birthRegistration";
	public static final String FATHER_NAME_BANGLA = "fatherNameBangla";
	public static final String GENDER = "gender";
	public static final String NATIONAL_ID_ATTACHMENT_CONTENT_TYPE = "nationalIdAttachmentContentType";
	public static final String SPOUSE_NAME_BANGLA = "spouseNameBangla";
	public static final String EMPLOYEE = "employee";
	public static final String NATIONAL_ID_ATTACHMENT = "nationalIdAttachment";
	public static final String BLOOD_GROUP = "bloodGroup";
	public static final String BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE = "birthRegistrationAttachmentContentType";
	public static final String ID = "id";
	public static final String NATIONAL_ID_ATTACHMENT_ID = "nationalIdAttachmentId";
	public static final String HEIGHT = "height";
	public static final String EMERGENCY_CONTACT = "emergencyContact";
	public static final String MOTHER_NAME = "motherName";
	public static final String PHOTO = "photo";
	public static final String PHOTO_ID = "photoId";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String BIRTH_REGISTRATION_ATTACHMENT_ID = "birthRegistrationAttachmentId";
	public static final String BANGLA_NAME = "banglaName";
	public static final String MOTHER_NAME_BANGLA = "motherNameBangla";
	public static final String RELIGION = "religion";
	public static final String NATIONAL_ID = "nationalId";
	public static final String NAME = "name";
	public static final String BIRTH_REGISTRATION_ATTACHMENT = "birthRegistrationAttachment";
	public static final String SPOUSE_NAME = "spouseName";
	public static final String PHOTO_CONTENT_TYPE = "photoContentType";
	public static final String MARITAL_STATUS = "maritalStatus";

}

