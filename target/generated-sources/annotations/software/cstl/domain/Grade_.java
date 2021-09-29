package software.cstl.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.EmployeeCategory;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Grade.class)
public abstract class Grade_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Grade, String> name;
	public static volatile SingularAttribute<Grade, String> description;
	public static volatile SingularAttribute<Grade, BigDecimal> initialSalary;
	public static volatile SingularAttribute<Grade, Long> id;
	public static volatile SingularAttribute<Grade, EmployeeCategory> category;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String INITIAL_SALARY = "initialSalary";
	public static final String ID = "id";
	public static final String CATEGORY = "category";

}

