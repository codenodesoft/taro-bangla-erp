package software.cstl.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmployeeLeaveDate.class)
public abstract class EmployeeLeaveDate_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<EmployeeLeaveDate, ScheduledEmployeeLeaveApplication> scheduledLeaveApplication;
	public static volatile SingularAttribute<EmployeeLeaveDate, LocalDate> leaveDate;
	public static volatile SingularAttribute<EmployeeLeaveDate, Long> id;
	public static volatile SingularAttribute<EmployeeLeaveDate, Employee> employee;

	public static final String SCHEDULED_LEAVE_APPLICATION = "scheduledLeaveApplication";
	public static final String LEAVE_DATE = "leaveDate";
	public static final String ID = "id";
	public static final String EMPLOYEE = "employee";

}

