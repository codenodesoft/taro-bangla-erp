package software.cstl.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.domain.enumeration.ServiceStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ServiceHistory.class)
public abstract class ServiceHistory_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<ServiceHistory, EmployeeType> employeeType;
	public static volatile SingularAttribute<ServiceHistory, byte[]> attachment;
	public static volatile SingularAttribute<ServiceHistory, Grade> grade;
	public static volatile SingularAttribute<ServiceHistory, String> attachmentContentType;
	public static volatile SingularAttribute<ServiceHistory, LocalDate> from;
	public static volatile SingularAttribute<ServiceHistory, Long> id;
	public static volatile SingularAttribute<ServiceHistory, LocalDate> to;
	public static volatile SingularAttribute<ServiceHistory, Designation> designation;
	public static volatile SingularAttribute<ServiceHistory, EmployeeCategory> category;
	public static volatile SingularAttribute<ServiceHistory, Department> department;
	public static volatile SingularAttribute<ServiceHistory, Employee> employee;
	public static volatile SingularAttribute<ServiceHistory, ServiceStatus> status;

	public static final String EMPLOYEE_TYPE = "employeeType";
	public static final String ATTACHMENT = "attachment";
	public static final String GRADE = "grade";
	public static final String ATTACHMENT_CONTENT_TYPE = "attachmentContentType";
	public static final String FROM = "from";
	public static final String ID = "id";
	public static final String TO = "to";
	public static final String DESIGNATION = "designation";
	public static final String CATEGORY = "category";
	public static final String DEPARTMENT = "department";
	public static final String EMPLOYEE = "employee";
	public static final String STATUS = "status";

}

