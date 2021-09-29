package software.cstl.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.EmployeeType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SetAttribute<Employee, EmployeeSalary> employeeSalaries;
	public static volatile SingularAttribute<Employee, String> empId;
	public static volatile SingularAttribute<Employee, PersonalInfo> personalInfo;
	public static volatile SingularAttribute<Employee, Line> line;
	public static volatile SingularAttribute<Employee, LocalDate> joiningDate;
	public static volatile SingularAttribute<Employee, EmployeeType> type;
	public static volatile SingularAttribute<Employee, String> localId;
	public static volatile SingularAttribute<Employee, LocalDate> terminationDate;
	public static volatile SetAttribute<Employee, JobHistory> jobHistories;
	public static volatile SetAttribute<Employee, Advance> advances;
	public static volatile SingularAttribute<Employee, Company> company;
	public static volatile SingularAttribute<Employee, Long> id;
	public static volatile SingularAttribute<Employee, Department> department;
	public static volatile SingularAttribute<Employee, String> terminationReason;
	public static volatile SingularAttribute<Employee, Address> address;
	public static volatile SingularAttribute<Employee, String> globalId;
	public static volatile SetAttribute<Employee, Training> trainings;
	public static volatile SetAttribute<Employee, EmployeeAccount> employeeAccounts;
	public static volatile SetAttribute<Employee, ServiceHistory> serviceHistories;
	public static volatile SetAttribute<Employee, OverTime> overTimes;
	public static volatile SetAttribute<Employee, Fine> fines;
	public static volatile SingularAttribute<Employee, Grade> grade;
	public static volatile SingularAttribute<Employee, String> name;
	public static volatile SingularAttribute<Employee, String> attendanceMachineId;
	public static volatile SetAttribute<Employee, EducationalInfo> educationalInfos;
	public static volatile SingularAttribute<Employee, Designation> designation;
	public static volatile SingularAttribute<Employee, EmployeeCategory> category;
	public static volatile SetAttribute<Employee, PartialSalary> partialSalaries;
	public static volatile SingularAttribute<Employee, EmployeeStatus> status;

	public static final String EMPLOYEE_SALARIES = "employeeSalaries";
	public static final String EMP_ID = "empId";
	public static final String PERSONAL_INFO = "personalInfo";
	public static final String LINE = "line";
	public static final String JOINING_DATE = "joiningDate";
	public static final String TYPE = "type";
	public static final String LOCAL_ID = "localId";
	public static final String TERMINATION_DATE = "terminationDate";
	public static final String JOB_HISTORIES = "jobHistories";
	public static final String ADVANCES = "advances";
	public static final String COMPANY = "company";
	public static final String ID = "id";
	public static final String DEPARTMENT = "department";
	public static final String TERMINATION_REASON = "terminationReason";
	public static final String ADDRESS = "address";
	public static final String GLOBAL_ID = "globalId";
	public static final String TRAININGS = "trainings";
	public static final String EMPLOYEE_ACCOUNTS = "employeeAccounts";
	public static final String SERVICE_HISTORIES = "serviceHistories";
	public static final String OVER_TIMES = "overTimes";
	public static final String FINES = "fines";
	public static final String GRADE = "grade";
	public static final String NAME = "name";
	public static final String ATTENDANCE_MACHINE_ID = "attendanceMachineId";
	public static final String EDUCATIONAL_INFOS = "educationalInfos";
	public static final String DESIGNATION = "designation";
	public static final String CATEGORY = "category";
	public static final String PARTIAL_SALARIES = "partialSalaries";
	public static final String STATUS = "status";

}

