package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Department, String> nameInBangla;
	public static volatile SingularAttribute<Department, String> name;
	public static volatile SingularAttribute<Department, String> description;
	public static volatile SingularAttribute<Department, Long> id;
	public static volatile SingularAttribute<Department, String> shortName;

	public static final String NAME_IN_BANGLA = "nameInBangla";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String SHORT_NAME = "shortName";

}

