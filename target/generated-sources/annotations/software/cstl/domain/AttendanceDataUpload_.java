package software.cstl.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AttendanceDataUpload.class)
public abstract class AttendanceDataUpload_ {

	public static volatile SingularAttribute<AttendanceDataUpload, String> fileUploadContentType;
	public static volatile SingularAttribute<AttendanceDataUpload, Long> id;
	public static volatile SingularAttribute<AttendanceDataUpload, byte[]> fileUpload;
	public static volatile SingularAttribute<AttendanceDataUpload, Instant> createdOn;

	public static final String FILE_UPLOAD_CONTENT_TYPE = "fileUploadContentType";
	public static final String ID = "id";
	public static final String FILE_UPLOAD = "fileUpload";
	public static final String CREATED_ON = "createdOn";

}

