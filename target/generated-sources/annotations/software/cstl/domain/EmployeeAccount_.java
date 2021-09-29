package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.AccountType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmployeeAccount.class)
public abstract class EmployeeAccount_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<EmployeeAccount, Boolean> isSalaryAccount;
	public static volatile SingularAttribute<EmployeeAccount, AccountType> accountType;
	public static volatile SingularAttribute<EmployeeAccount, String> accountNo;
	public static volatile SingularAttribute<EmployeeAccount, Long> id;
	public static volatile SingularAttribute<EmployeeAccount, Employee> employee;

	public static final String IS_SALARY_ACCOUNT = "isSalaryAccount";
	public static final String ACCOUNT_TYPE = "accountType";
	public static final String ACCOUNT_NO = "accountNo";
	public static final String ID = "id";
	public static final String EMPLOYEE = "employee";

}

