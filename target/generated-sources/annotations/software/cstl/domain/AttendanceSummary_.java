package software.cstl.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.AttendanceStatus;
import software.cstl.domain.enumeration.AttendanceType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AttendanceSummary.class)
public abstract class AttendanceSummary_ {

	public static volatile SingularAttribute<AttendanceSummary, Duration> totalHours;
	public static volatile SingularAttribute<AttendanceSummary, Line> line;
	public static volatile SingularAttribute<AttendanceSummary, Employee> employee;
	public static volatile SingularAttribute<AttendanceSummary, ZonedDateTime> inTime;
	public static volatile SingularAttribute<AttendanceSummary, AttendanceType> attendanceType;
	public static volatile SingularAttribute<AttendanceSummary, Grade> grade;
	public static volatile SingularAttribute<AttendanceSummary, Duration> overtime;
	public static volatile SingularAttribute<AttendanceSummary, Long> id;
	public static volatile SingularAttribute<AttendanceSummary, Designation> designation;
	public static volatile SingularAttribute<AttendanceSummary, Department> department;
	public static volatile SingularAttribute<AttendanceSummary, Duration> workingHours;
	public static volatile SingularAttribute<AttendanceSummary, LocalDate> attendanceDate;
	public static volatile SingularAttribute<AttendanceSummary, ZonedDateTime> outTime;
	public static volatile SingularAttribute<AttendanceSummary, AttendanceStatus> attendanceStatus;
	public static volatile SingularAttribute<AttendanceSummary, String> remarks;
	public static volatile SingularAttribute<AttendanceSummary, EmployeeSalary> employeeSalary;

	public static final String TOTAL_HOURS = "totalHours";
	public static final String LINE = "line";
	public static final String EMPLOYEE = "employee";
	public static final String IN_TIME = "inTime";
	public static final String ATTENDANCE_TYPE = "attendanceType";
	public static final String GRADE = "grade";
	public static final String OVERTIME = "overtime";
	public static final String ID = "id";
	public static final String DESIGNATION = "designation";
	public static final String DEPARTMENT = "department";
	public static final String WORKING_HOURS = "workingHours";
	public static final String ATTENDANCE_DATE = "attendanceDate";
	public static final String OUT_TIME = "outTime";
	public static final String ATTENDANCE_STATUS = "attendanceStatus";
	public static final String REMARKS = "remarks";
	public static final String EMPLOYEE_SALARY = "employeeSalary";

}

