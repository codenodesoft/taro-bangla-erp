package software.cstl.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScheduledEmployeeLeaveApplication.class)
public abstract class ScheduledEmployeeLeaveApplication_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<ScheduledEmployeeLeaveApplication, Instant> executedOn;
	public static volatile SingularAttribute<ScheduledEmployeeLeaveApplication, Long> id;
	public static volatile SingularAttribute<ScheduledEmployeeLeaveApplication, Long> leaveApplicationId;
	public static volatile SetAttribute<ScheduledEmployeeLeaveApplication, EmployeeLeaveDate> employeeLeaveDates;

	public static final String EXECUTED_ON = "executedOn";
	public static final String ID = "id";
	public static final String LEAVE_APPLICATION_ID = "leaveApplicationId";
	public static final String EMPLOYEE_LEAVE_DATES = "employeeLeaveDates";

}

