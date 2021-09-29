package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Line.class)
public abstract class Line_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Line, String> name;
	public static volatile SingularAttribute<Line, String> description;
	public static volatile SingularAttribute<Line, Long> id;
	public static volatile SingularAttribute<Line, Department> department;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String DEPARTMENT = "department";

}

