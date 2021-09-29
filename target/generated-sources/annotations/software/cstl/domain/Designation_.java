package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import software.cstl.domain.enumeration.EmployeeCategory;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Designation.class)
public abstract class Designation_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Designation, String> nameInBangla;
	public static volatile SingularAttribute<Designation, String> name;
	public static volatile SingularAttribute<Designation, String> description;
	public static volatile SingularAttribute<Designation, Long> id;
	public static volatile SingularAttribute<Designation, EmployeeCategory> category;
	public static volatile SingularAttribute<Designation, String> shortName;

	public static final String NAME_IN_BANGLA = "nameInBangla";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String SHORT_NAME = "shortName";

}

