package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Company, String> nameInBangla;
	public static volatile SingularAttribute<Company, String> address;
	public static volatile SingularAttribute<Company, String> phone;
	public static volatile SingularAttribute<Company, String> name;
	public static volatile SingularAttribute<Company, String> description;
	public static volatile SingularAttribute<Company, Long> id;
	public static volatile SingularAttribute<Company, String> shortName;

	public static final String NAME_IN_BANGLA = "nameInBangla";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String SHORT_NAME = "shortName";

}

