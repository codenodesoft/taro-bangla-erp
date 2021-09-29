package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Thana.class)
public abstract class Thana_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Thana, String> web;
	public static volatile SingularAttribute<Thana, District> district;
	public static volatile SingularAttribute<Thana, String> name;
	public static volatile SingularAttribute<Thana, String> bangla;
	public static volatile SingularAttribute<Thana, Long> id;

	public static final String WEB = "web";
	public static final String DISTRICT = "district";
	public static final String NAME = "name";
	public static final String BANGLA = "bangla";
	public static final String ID = "id";

}

