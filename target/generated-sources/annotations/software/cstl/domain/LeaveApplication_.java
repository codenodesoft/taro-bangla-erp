package software.cstl.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.LeaveApplicationStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LeaveApplication.class)
public abstract class LeaveApplication_ {

	public static volatile SingularAttribute<LeaveApplication, String> reason;
	public static volatile SingularAttribute<LeaveApplication, String> actionTakenBy;
	public static volatile SingularAttribute<LeaveApplication, Employee> employee;
	public static volatile SingularAttribute<LeaveApplication, Instant> appliedOn;
	public static volatile SingularAttribute<LeaveApplication, BigDecimal> totalDays;
	public static volatile SingularAttribute<LeaveApplication, byte[]> attachment;
	public static volatile SingularAttribute<LeaveApplication, LeaveType> leaveType;
	public static volatile SingularAttribute<LeaveApplication, String> attachmentContentType;
	public static volatile SingularAttribute<LeaveApplication, LocalDate> from;
	public static volatile SingularAttribute<LeaveApplication, String> appliedBy;
	public static volatile SingularAttribute<LeaveApplication, Long> id;
	public static volatile SingularAttribute<LeaveApplication, LocalDate> to;
	public static volatile SingularAttribute<LeaveApplication, Instant> actionTakenOn;
	public static volatile SingularAttribute<LeaveApplication, Designation> designation;
	public static volatile SingularAttribute<LeaveApplication, Department> department;
	public static volatile SingularAttribute<LeaveApplication, LeaveApplicationStatus> status;

	public static final String REASON = "reason";
	public static final String ACTION_TAKEN_BY = "actionTakenBy";
	public static final String EMPLOYEE = "employee";
	public static final String APPLIED_ON = "appliedOn";
	public static final String TOTAL_DAYS = "totalDays";
	public static final String ATTACHMENT = "attachment";
	public static final String LEAVE_TYPE = "leaveType";
	public static final String ATTACHMENT_CONTENT_TYPE = "attachmentContentType";
	public static final String FROM = "from";
	public static final String APPLIED_BY = "appliedBy";
	public static final String ID = "id";
	public static final String TO = "to";
	public static final String ACTION_TAKEN_ON = "actionTakenOn";
	public static final String DESIGNATION = "designation";
	public static final String DEPARTMENT = "department";
	public static final String STATUS = "status";

}

