package software.cstl.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ extends software.cstl.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Address, String> presentStreetBangla;
	public static volatile SingularAttribute<Address, Integer> permanentPostCode;
	public static volatile SingularAttribute<Address, String> permenentPostCodeBangla;
	public static volatile SingularAttribute<Address, Division> permanentDivision;
	public static volatile SingularAttribute<Address, String> permanentAreaBangla;
	public static volatile SingularAttribute<Address, Thana> permanentThana;
	public static volatile SingularAttribute<Address, District> permanentDistrict;
	public static volatile SingularAttribute<Address, String> presentAreaBangla;
	public static volatile SingularAttribute<Address, District> presentDistrict;
	public static volatile SingularAttribute<Address, String> presentStreet;
	public static volatile SingularAttribute<Address, String> permanentThanaTxt;
	public static volatile SingularAttribute<Address, Thana> presentThana;
	public static volatile SingularAttribute<Address, Employee> employee;
	public static volatile SingularAttribute<Address, Division> presentDivision;
	public static volatile SingularAttribute<Address, String> permanentStreet;
	public static volatile SingularAttribute<Address, String> presentThanaTxt;
	public static volatile SingularAttribute<Address, String> permanentStreetBangla;
	public static volatile SingularAttribute<Address, Integer> presentPostCode;
	public static volatile SingularAttribute<Address, String> presentPostCodeBangla;
	public static volatile SingularAttribute<Address, Boolean> isSame;
	public static volatile SingularAttribute<Address, String> permanentArea;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, String> presentArea;

	public static final String PRESENT_STREET_BANGLA = "presentStreetBangla";
	public static final String PERMANENT_POST_CODE = "permanentPostCode";
	public static final String PERMENENT_POST_CODE_BANGLA = "permenentPostCodeBangla";
	public static final String PERMANENT_DIVISION = "permanentDivision";
	public static final String PERMANENT_AREA_BANGLA = "permanentAreaBangla";
	public static final String PERMANENT_THANA = "permanentThana";
	public static final String PERMANENT_DISTRICT = "permanentDistrict";
	public static final String PRESENT_AREA_BANGLA = "presentAreaBangla";
	public static final String PRESENT_DISTRICT = "presentDistrict";
	public static final String PRESENT_STREET = "presentStreet";
	public static final String PERMANENT_THANA_TXT = "permanentThanaTxt";
	public static final String PRESENT_THANA = "presentThana";
	public static final String EMPLOYEE = "employee";
	public static final String PRESENT_DIVISION = "presentDivision";
	public static final String PERMANENT_STREET = "permanentStreet";
	public static final String PRESENT_THANA_TXT = "presentThanaTxt";
	public static final String PERMANENT_STREET_BANGLA = "permanentStreetBangla";
	public static final String PRESENT_POST_CODE = "presentPostCode";
	public static final String PRESENT_POST_CODE_BANGLA = "presentPostCodeBangla";
	public static final String IS_SAME = "isSame";
	public static final String PERMANENT_AREA = "permanentArea";
	public static final String ID = "id";
	public static final String PRESENT_AREA = "presentArea";

}

