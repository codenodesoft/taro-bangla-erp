package software.cstl.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.LeaveTypeName;
import software.cstl.domain.enumeration.LeaveTypeStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LeaveType.class)
public abstract class LeaveType_ {

	public static volatile SingularAttribute<LeaveType, BigDecimal> totalDays;
	public static volatile SingularAttribute<LeaveType, LocalDate> endDate;
	public static volatile SingularAttribute<LeaveType, LeaveTypeName> name;
	public static volatile SingularAttribute<LeaveType, Long> id;
	public static volatile SingularAttribute<LeaveType, LocalDate> startDate;
	public static volatile SingularAttribute<LeaveType, LeaveTypeStatus> status;

	public static final String TOTAL_DAYS = "totalDays";
	public static final String END_DATE = "endDate";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String START_DATE = "startDate";
	public static final String STATUS = "status";

}

