package software.cstl.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Attendance.class)
public abstract class Attendance_ {

	public static volatile SingularAttribute<Attendance, ZonedDateTime> attendanceDateTime;
	public static volatile SingularAttribute<Attendance, String> machineNo;
	public static volatile SingularAttribute<Attendance, String> employeeMachineId;
	public static volatile SingularAttribute<Attendance, Long> id;
	public static volatile SingularAttribute<Attendance, AttendanceDataUpload> attendanceDataUpload;
	public static volatile SingularAttribute<Attendance, String> remarks;

	public static final String ATTENDANCE_DATE_TIME = "attendanceDateTime";
	public static final String MACHINE_NO = "machineNo";
	public static final String EMPLOYEE_MACHINE_ID = "employeeMachineId";
	public static final String ID = "id";
	public static final String ATTENDANCE_DATA_UPLOAD = "attendanceDataUpload";
	public static final String REMARKS = "remarks";

}

