package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(District.class)
public abstract class District_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<District, Division> division;
	public static volatile SingularAttribute<District, String> web;
	public static volatile SingularAttribute<District, String> name;
	public static volatile SingularAttribute<District, String> bangla;
	public static volatile SingularAttribute<District, Long> id;

	public static final String DIVISION = "division";
	public static final String WEB = "web";
	public static final String NAME = "name";
	public static final String BANGLA = "bangla";
	public static final String ID = "id";

}

