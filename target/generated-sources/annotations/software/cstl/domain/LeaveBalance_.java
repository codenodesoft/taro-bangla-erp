package software.cstl.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.LeaveBalanceStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LeaveBalance.class)
public abstract class LeaveBalance_ {

	public static volatile SingularAttribute<LeaveBalance, BigDecimal> amount;
	public static volatile SingularAttribute<LeaveBalance, Instant> lastSynchronizedOn;
	public static volatile SingularAttribute<LeaveBalance, BigDecimal> remainingDays;
	public static volatile SingularAttribute<LeaveBalance, Employee> employee;
	public static volatile SingularAttribute<LeaveBalance, BigDecimal> totalDays;
	public static volatile SingularAttribute<LeaveBalance, LeaveType> leaveType;
	public static volatile SingularAttribute<LeaveBalance, LocalDate> from;
	public static volatile SingularAttribute<LeaveBalance, Long> id;
	public static volatile SingularAttribute<LeaveBalance, LocalDate> to;
	public static volatile SingularAttribute<LeaveBalance, Designation> designation;
	public static volatile SingularAttribute<LeaveBalance, Department> department;
	public static volatile SingularAttribute<LeaveBalance, String> remarks;
	public static volatile SingularAttribute<LeaveBalance, LeaveBalanceStatus> status;
	public static volatile SingularAttribute<LeaveBalance, Integer> assessmentYear;

	public static final String AMOUNT = "amount";
	public static final String LAST_SYNCHRONIZED_ON = "lastSynchronizedOn";
	public static final String REMAINING_DAYS = "remainingDays";
	public static final String EMPLOYEE = "employee";
	public static final String TOTAL_DAYS = "totalDays";
	public static final String LEAVE_TYPE = "leaveType";
	public static final String FROM = "from";
	public static final String ID = "id";
	public static final String TO = "to";
	public static final String DESIGNATION = "designation";
	public static final String DEPARTMENT = "department";
	public static final String REMARKS = "remarks";
	public static final String STATUS = "status";
	public static final String ASSESSMENT_YEAR = "assessmentYear";

}

