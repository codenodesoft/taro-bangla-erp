package software.cstl.domain;

import java.time.Instant;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.SynchronizationStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AttendanceSynchronization.class)
public abstract class AttendanceSynchronization_ {

	public static volatile SingularAttribute<AttendanceSynchronization, LocalDate> fromDate;
	public static volatile SingularAttribute<AttendanceSynchronization, LocalDate> toDate;
	public static volatile SingularAttribute<AttendanceSynchronization, Instant> startTime;
	public static volatile SingularAttribute<AttendanceSynchronization, Long> id;
	public static volatile SingularAttribute<AttendanceSynchronization, Instant> endTime;
	public static volatile SingularAttribute<AttendanceSynchronization, SynchronizationStatus> status;

	public static final String FROM_DATE = "fromDate";
	public static final String TO_DATE = "toDate";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String END_TIME = "endTime";
	public static final String STATUS = "status";

}

