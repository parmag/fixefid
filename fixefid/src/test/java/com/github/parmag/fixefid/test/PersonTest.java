package com.github.parmag.fixefid.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.BeanRecord;
import com.github.parmag.fixefid.record.ErrorCode;
import com.github.parmag.fixefid.record.RecordException;
import com.github.parmag.fixefid.record.RecordWay;
import com.github.parmag.fixefid.record.eps.FieldExtendedPropertyFactory;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.field.FieldValidationInfo;
import com.github.parmag.fixefid.record.format.CustomFormat;
import com.github.parmag.fixefid.record.format.SimpleBooleanFormat;
import com.github.parmag.fixefid.test.bean.Address;
import com.github.parmag.fixefid.test.bean.FakePerson;
import com.github.parmag.fixefid.test.bean.Person;
import com.github.parmag.fixefid.test.bean.Person1000;
import com.github.parmag.fixefid.test.bean.PersonWithAddress;
import com.github.parmag.fixefid.test.bean.PersonWithAddressOccurs;
import com.github.parmag.fixefid.test.bean.PersonWithAddressWithFixedValues;
import com.github.parmag.fixefid.test.bean.PersonWithNotUniqueOrdinal;

public class PersonTest {
	private static final Calendar CAL = Calendar.getInstance();
	
	private static final List<FieldExtendedProperty> CUSTOM_FORMAT_LIST = Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.CUSTOM_FORMAT, new CustomFormat() {
		@Override
		public String format(String value) {
			return value.toUpperCase();
		}

		@Override
		public String parse(String value) {
			return value.toLowerCase();
		}
	}));
	
	private static final Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = 
			new HashMap<String, List<FieldExtendedProperty>>();
	
	private static final Person PERSON_BEAN = new Person();
	private static final Person PERSON_BEAN_FOR_STRING = new Person();
	private static final Person PERSON_BEAN_FOR_INIT_WITH_STRING = new Person();
	private static final Person PERSON_BEAN_FOR_INIT_FIELD = new Person();
	
	private static final Person1000 PERSON_1000_BEAN = new Person1000();
	private static final Person1000 PERSON_1000_BEAN_FOR_STRING = new Person1000();
	
	private static final PersonWithAddress PERSON_WITH_ADDRESS_BEAN = new PersonWithAddress();
	private static final PersonWithAddressWithFixedValues PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN = new PersonWithAddressWithFixedValues();
	private static final PersonWithAddress PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD = new PersonWithAddress();
	
	private static final PersonWithAddressOccurs PERSON_WITH_ADDRESS_OCCURS_BEAN = new PersonWithAddressOccurs();
	private static final PersonWithAddressOccurs PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD = new PersonWithAddressOccurs();
	
	public static final String PERSON_RECORD_AS_STRING = "Paolo                    Rossi                    05107102002186BON000000000100001.00010100000.00";
	public static final String ADDRESS1_AS_STRING = "Bologna                  40128BOITAVia Ugo Bassi                 77        ";
	public static final String ADDRESS1_WITH_FIXED_VALUES_AS_STRING = "Bologna                  40128BOITAVia Ugo Bassi                 77                0000010000001,0000002.00Y2022-04-11";
	public static final String ADDRESS2_AS_STRING = "Modena                   41100MOITAVia Modenese                  81        ";
	public static final String ADDRESS3_AS_STRING = "Piacenza                 29121PCITAVia Piacentina                34        ";
	
	public static final String PERSON_WITH_ADDRESS_RECORD_AS_STRING = PERSON_RECORD_AS_STRING + ADDRESS1_AS_STRING;
	public static final String PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_RECORD_AS_STRING = PERSON_RECORD_AS_STRING + ADDRESS1_WITH_FIXED_VALUES_AS_STRING;
	public static final String PERSON_WITH_ADDRESS_OCCURS_RECORD_AS_STRING = PERSON_RECORD_AS_STRING + ADDRESS1_AS_STRING + ADDRESS2_AS_STRING + ADDRESS3_AS_STRING;
			
	private static final BeanRecord PERSON_BEAN_RECORD; 
	private static final BeanRecord PERSON_BEAN_RECORD_WAY_OUT; 
	private static final BeanRecord PERSON_1000_BEAN_RECORD; 
	private static final BeanRecord PERSON_1000_BEAN_RECORD_STRING; 
	private static final BeanRecord PERSON_BEAN_RECORD_STRING;
	private static final BeanRecord PERSON_BEAN_RECORD_INIT_WITH_STRING;
	private static final BeanRecord PERSON_BEAN_RECORD_INIT_WITH_FIELD;
	private static final BeanRecord PERSON_WITH_ADDRESS_BEAN_RECORD;
	private static final BeanRecord PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD;
	private static final BeanRecord PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD;
	private static final BeanRecord PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD;
	private static final BeanRecord PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD;
	
	static {
		CAL.set(Calendar.DAY_OF_MONTH, 7);
		CAL.set(Calendar.MONTH, 9);
		CAL.set(Calendar.YEAR, 2002);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
		
		MAP_FIELD_EXTENDED_PROPERTIES.put("birthDate", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH))));
		MAP_FIELD_EXTENDED_PROPERTIES.put("stature", Arrays.asList(
				FieldExtendedPropertyFactory.createDecimalFormat("0.00", Locale.ENGLISH),
				FieldExtendedPropertyFactory.createRemoveDecimalSeparator(Boolean.valueOf(true))));
		
		MAP_FIELD_EXTENDED_PROPERTIES.put("birthDistrict", CUSTOM_FORMAT_LIST);
		MAP_FIELD_EXTENDED_PROPERTIES.put("vip", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new SimpleBooleanFormat("Y", "N"))));
		MAP_FIELD_EXTENDED_PROPERTIES.put("tor", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.0000", new DecimalFormatSymbols(Locale.ENGLISH)))));
		MAP_FIELD_EXTENDED_PROPERTIES.put("turnover", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)))));
		MAP_FIELD_EXTENDED_PROPERTIES.put("address.district", CUSTOM_FORMAT_LIST);
		MAP_FIELD_EXTENDED_PROPERTIES.put("addresses.district", CUSTOM_FORMAT_LIST);
		
		PERSON_BEAN_RECORD = new BeanRecord(PERSON_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		PERSON_BEAN_RECORD.setValue("firstName", "Paolo");
		PERSON_BEAN_RECORD.setValue("lastName", "Rossi");
		PERSON_BEAN_RECORD.setValue("age", 51);
		PERSON_BEAN_RECORD.setValue("birthDate", CAL.getTime()); 
		PERSON_BEAN_RECORD.setValue("stature", 1.86f);
		PERSON_BEAN_RECORD.setValue("birthDistrict", "bo");
		PERSON_BEAN_RECORD.setValue("vip", "N");
		PERSON_BEAN_RECORD.setValue("id", "0000000001");
		PERSON_BEAN_RECORD.setValue("tor", "00001.0001");
		PERSON_BEAN_RECORD.setValue("turnover", "0100000.00");
		
		PERSON_BEAN_RECORD_WAY_OUT = new BeanRecord(PERSON_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES, RecordWay.OUT); 
		PERSON_BEAN_RECORD_WAY_OUT.setValue("firstName", "Paolo");
		PERSON_BEAN_RECORD_WAY_OUT.setValue("lastName", "Rossi");
		PERSON_BEAN_RECORD_WAY_OUT.setValue("age", 51);
		PERSON_BEAN_RECORD_WAY_OUT.setValue("birthDate", CAL.getTime()); 
		PERSON_BEAN_RECORD_WAY_OUT.setValue("stature", 1.86f);
		PERSON_BEAN_RECORD_WAY_OUT.setValue("birthDistrict", "bo");
		PERSON_BEAN_RECORD_WAY_OUT.setValue("vip", "N");
		PERSON_BEAN_RECORD_WAY_OUT.setValue("id", "0000000001");
		PERSON_BEAN_RECORD_WAY_OUT.setValue("tor", "00001.0001");
		PERSON_BEAN_RECORD_WAY_OUT.setValue("turnover", "0100000.00");
		
		PERSON_1000_BEAN_RECORD = new BeanRecord(PERSON_1000_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		PERSON_1000_BEAN_RECORD_STRING = new BeanRecord(PERSON_1000_BEAN_FOR_STRING, PERSON_RECORD_AS_STRING, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		
		PERSON_BEAN_RECORD_STRING = new BeanRecord(PERSON_BEAN_FOR_STRING, PERSON_RECORD_AS_STRING, null, MAP_FIELD_EXTENDED_PROPERTIES);
		PERSON_BEAN_RECORD_INIT_WITH_STRING = new BeanRecord(PERSON_BEAN_FOR_INIT_WITH_STRING, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
		
		PERSON_BEAN_FOR_INIT_FIELD.setFirstName("Paolo");
		PERSON_BEAN_FOR_INIT_FIELD.setLastName("Rossi");
		PERSON_BEAN_FOR_INIT_FIELD.setAge(51);
		PERSON_BEAN_FOR_INIT_FIELD.setBirthDate(CAL.getTime());
		PERSON_BEAN_FOR_INIT_FIELD.setStature(1.86f);
		PERSON_BEAN_FOR_INIT_FIELD.setBirthDistrict("bo");
		PERSON_BEAN_FOR_INIT_FIELD.setVip(false);
		PERSON_BEAN_FOR_INIT_FIELD.setId(1L);
		PERSON_BEAN_FOR_INIT_FIELD.setTor(1.0001);
		PERSON_BEAN_FOR_INIT_FIELD.setTurnover(BigDecimal.valueOf(100000.00)); 
		
		PERSON_BEAN_RECORD_INIT_WITH_FIELD = new BeanRecord(PERSON_BEAN_FOR_INIT_FIELD, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
		
		PERSON_WITH_ADDRESS_BEAN_RECORD = new BeanRecord(PERSON_WITH_ADDRESS_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("firstName", "Paolo");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("lastName", "Rossi");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("age", 51);
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("birthDate", CAL.getTime()); 
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("stature", 1.86f);
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("birthDistrict", "bo");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("vip", "N");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("id", "0000000001");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("tor", "00001.0001");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("turnover", "0100000.00");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("address.location", "Bologna");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("address.postalCode", "40128");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("address.district", "bo");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("address.nationIso3", "ITA");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("address.address", "Via Ugo Bassi");
		PERSON_WITH_ADDRESS_BEAN_RECORD.setValue("address.num", "77");
		
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD = new BeanRecord(PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("firstName", "Paolo");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("lastName", "Rossi");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("age", 51);
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("birthDate", CAL.getTime()); 
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("stature", 1.86f);
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("birthDistrict", "bo");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("vip", "N");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("id", "0000000001");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("tor", "00001.0001");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("turnover", "0100000.00");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.location", "Bologna");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.postalCode", "40128");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.district", "bo");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.nationIso3", "ITA");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.address", "Via Ugo Bassi");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.num", "77");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.addressId2", "1,00");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.addressId3", "2.00");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.vip", "Y");
		PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.setValue("address.addressDate", "2022-04-11");
		
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setFirstName("Paolo");
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setLastName("Rossi");
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setAge(51);
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setBirthDate(CAL.getTime());
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setStature(1.86f);
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setBirthDistrict("bo");
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setVip(false);
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setId(1L);
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setTor(1.0001);
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setTurnover(BigDecimal.valueOf(100000.00)); 
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.setAddress(new Address());
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.getAddress().setLocation("Bologna");
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.getAddress().setPostalCode("40128");
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.getAddress().setDistrict("bo");
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.getAddress().setNationIso3("ITA");
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.getAddress().setAddress("Via Ugo Bassi");
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.getAddress().setNum("77");
		
		PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD = new BeanRecord(PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
		
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD = new BeanRecord(PERSON_WITH_ADDRESS_OCCURS_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("firstName", "Paolo");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("lastName", "Rossi");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("age", 51);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("birthDate", CAL.getTime()); 
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("stature", 1.86f);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("birthDistrict", "bo");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("vip", "N");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("id", "0000000001");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("tor", "00001.0001");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("turnover", "0100000.00");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.location", "Bologna", 1, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.postalCode", "40128", 1, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.district", "bo", 1, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.nationIso3", "ITA", 1, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.address", "Via Ugo Bassi", 1, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.num", "77", 1, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.location", "Modena", 2, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.postalCode", "41100", 2, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.district", "mo", 2, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.nationIso3", "ITA", 2, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.address", "Via Modenese", 2, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.num", "81", 2, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.location", "Piacenza", 3, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.postalCode", "29121", 3, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.district", "pc", 3, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.nationIso3", "ITA", 3, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.address", "Via Piacentina", 3, 1);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.setValue("addresses.num", "34", 3, 1);
		
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setFirstName("Paolo");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setLastName("Rossi");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setAge(51);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setBirthDate(CAL.getTime());
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setStature(1.86f);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setBirthDistrict("bo");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setVip(false);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setId(1L);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setTor(1.0001);
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.setTurnover(BigDecimal.valueOf(100000.00)); 
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().add(new Address());
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(0).setLocation("Bologna");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(0).setPostalCode("40128");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(0).setDistrict("bo");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(0).setNationIso3("ITA");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(0).setAddress("Via Ugo Bassi");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(0).setNum("77");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().add(new Address());
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(1).setLocation("Modena");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(1).setPostalCode("41100");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(1).setDistrict("mo");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(1).setNationIso3("ITA");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(1).setAddress("Via Modenese");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(1).setNum("81");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().add(new Address());
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(2).setLocation("Piacenza");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(2).setPostalCode("29121");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(2).setDistrict("pc");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(2).setNationIso3("ITA");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(2).setAddress("Via Piacentina");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(2).setNum("34");
		
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD = new BeanRecord(PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
		
	}
	
	@Test
	public void testPersonToString() { 
		Assert.assertTrue(PERSON_RECORD_AS_STRING.contentEquals(PERSON_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testPersonWithAddressToString() { 
		Assert.assertTrue(PERSON_WITH_ADDRESS_RECORD_AS_STRING.contentEquals(PERSON_WITH_ADDRESS_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testPersonWithAddressWithFixedValuesToString() { 
		Assert.assertTrue(PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_RECORD_AS_STRING.contentEquals(PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testPersonWithAddressOccursToString() { 
		Assert.assertTrue(PERSON_WITH_ADDRESS_OCCURS_RECORD_AS_STRING.contentEquals(PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testPersonToStringConstructor() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.contentEquals(PERSON_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testPersonToStringForInitField() { 
		Assert.assertTrue(PERSON_RECORD_AS_STRING.contentEquals(PERSON_BEAN_RECORD_INIT_WITH_FIELD.toString()));
	}
	
	@Test
	public void testPersonWithAddressToStringForInitField() { 
		Assert.assertTrue(PERSON_WITH_ADDRESS_RECORD_AS_STRING.contentEquals(PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD.toString()));
	}
	
	@Test
	public void testPersonWithAddressOccursToStringForInitField() { 
		Assert.assertTrue(PERSON_WITH_ADDRESS_OCCURS_RECORD_AS_STRING.contentEquals(PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD.toString()));
	}
	
	@Test
	public void testPersonLen() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.length() == PERSON_BEAN_RECORD.getRecordLen());
	}
	
	@Test
	public void testPersonWithAddressLen() {
		Assert.assertTrue(PERSON_WITH_ADDRESS_RECORD_AS_STRING.length() == PERSON_WITH_ADDRESS_BEAN_RECORD.getRecordLen());
	}
	
	@Test
	public void testPersonWithAddressOccursLen() {
		Assert.assertTrue(PERSON_WITH_ADDRESS_OCCURS_RECORD_AS_STRING.length() == PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.getRecordLen());
	}
	
	@Test
	public void testPersonGetBirthDistrictCustomFormatAsStringValue() {  
		Assert.assertTrue("bo".equals(PERSON_BEAN_RECORD.getValueAsString("birthDistrict")));
	}
	
	@Test
	public void testPersonGetVipBooleanFormatAsString() {  
		Assert.assertTrue("N".equals(PERSON_BEAN_RECORD.getValue("vip")));
	}
	
	@Test
	public void testPersonGetVipBooleanFormatAsBooleanValue() {  
		Assert.assertFalse(PERSON_BEAN_RECORD.getValueAsBoolean("vip"));
	}
	
	@Test
	public void testPersonGetBirthDistrictCustomFormatAsString() {  
		Assert.assertTrue("BO".equals(PERSON_BEAN_RECORD.getValue("birthDistrict")));
	}
	
	@Test
	public void testPersonGetTurnoverAsString() {  
		Assert.assertTrue("0100000.00".equals(PERSON_BEAN_RECORD.getValue("turnover")));
	}
	
	@Test
	public void testPersonWithAddressGetLocationAsString() {  
		Assert.assertTrue("Bologna                  ".equals(PERSON_WITH_ADDRESS_BEAN_RECORD.getValue("address.location")));
	}
	
	@Test
	public void testPersonWithAddressGetLocationAsStringValue() {  
		Assert.assertTrue("Bologna".equals(PERSON_WITH_ADDRESS_BEAN_RECORD.getValueAsString("address.location")));
	}
	
	@Test
	public void testPersonWithAddressOccursGetLocation1AsString() {  
		Assert.assertTrue("Bologna                  ".equals(PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.getValue("addresses.location", 1, 1)));
	}
	
	@Test
	public void testPersonWithAddressOccursGetLocation1AsStringValue() {  
		Assert.assertTrue("Bologna".equals(PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.getValueAsString("addresses.location", 1, 1)));
	}
	
	@Test
	public void testPersonWithAddressOccursGetLocation2AsString() {  
		Assert.assertTrue("Modena                   ".equals(PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.getValue("addresses.location", 2, 1)));
	}
	
	@Test
	public void testPersonWithAddressOccursGetLocation2AsStringValue() {  
		Assert.assertTrue("Modena".equals(PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD.getValueAsString("addresses.location", 2, 1)));
	}
	
	@Test
	public void testPersonSyncValuesFromBeanToRecord() {
		PERSON_BEAN_FOR_INIT_FIELD.setAge(25);
		PERSON_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
		
		Assert.assertTrue(25 == PERSON_BEAN_RECORD_INIT_WITH_FIELD.getValueAsInteger("age")); 
		
		PERSON_BEAN_FOR_INIT_FIELD.setAge(51);
		PERSON_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
	}
	
	@Test
	public void testPersonSyncValuesFromRecordToBean() {
		PERSON_BEAN_RECORD_INIT_WITH_FIELD.setValue("age", 35); 
		PERSON_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
		
		Assert.assertTrue(35 == PERSON_BEAN_FOR_INIT_FIELD.getAge()); 
		
		PERSON_BEAN_RECORD_INIT_WITH_FIELD.setValue("age", 51); 
		PERSON_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
	}
	
	@Test
	public void testPersonWithAddressSyncValuesFromBeanToRecord() {
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.getAddress().setLocation("Ozzano");
		PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
		
		Assert.assertTrue("Ozzano".equals(PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD.getValueAsString("address.location"))); 
		
		PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.getAddress().setLocation("Bologna");
		PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
	}
	
	@Test
	public void testPersonWithAddressSyncValuesFromRecordToBean() {
		PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD.setValue("address.location", "San Lazzaro"); 
		PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
		
		Assert.assertTrue("San Lazzaro".equals(PERSON_WITH_ADDRESS_BEAN_FOR_INIT_FIELD.getAddress().getLocation())); 
		
		PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD.setValue("address.location", "Bologna"); 
		PERSON_WITH_ADDRESS_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
	}
	
	@Test
	public void testPersonWithAddressOccursSyncValuesFromBeanToRecord() {
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(1).setLocation("Vignola");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
		
		Assert.assertTrue("Vignola".equals(PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD.getValueAsString("addresses.location", 2, 1))); 
		
		PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(1).setLocation("Modena");
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
	}
	
	@Test
	public void testPersonWithAddressOccursSyncValuesFromRecordToBean() {
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD.setValue("addresses.location", "Spilamberto", 2, 1); 
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
		
		Assert.assertTrue("Spilamberto".equals(PERSON_WITH_ADDRESS_OCCURS_BEAN_FOR_INIT_FIELD.getAddresses().get(1).getLocation())); 
		
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD.setValue("addresses.location", "Modena", 2, 1); 
		PERSON_WITH_ADDRESS_OCCURS_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
	}
	
	@Test
	public void testFakePerson() {
		ErrorCode errorCode = null;
		try {
			new BeanRecord(new FakePerson());
		} catch (RecordException e) {
			errorCode = e.getErrorCode();
		}
		
		Assert.assertTrue(ErrorCode.RE13.equals(errorCode));
	}
	
	@Test
	public void testPersonWithNotUniqueOrdinal() {
		ErrorCode errorCode = null;
		try {
			new BeanRecord(new PersonWithNotUniqueOrdinal());
		} catch (RecordException e) {
			errorCode = e.getErrorCode();
		}
		
		Assert.assertTrue(ErrorCode.RE14.equals(errorCode));
	}
	
	@Test
	public void testPersonWithWrongAgeLen() {
		ErrorCode errorCode = null;
		try {
			PERSON_BEAN_RECORD.setValue("age", 5100);
		} catch (FieldException e) {
			errorCode = e.getErrorCode();
			PERSON_BEAN_RECORD.setValue("age", 51);
		}
		
		Assert.assertTrue(ErrorCode.FE10.equals(errorCode));
	}
	
	@Test
	public void testPersonWithWrongAgeNumeric() {
		ErrorCode errorCode = null;
		try {
			PERSON_BEAN_RECORD.setValue("age", "abc");
		} catch (FieldException e) {
			errorCode = e.getErrorCode();
			PERSON_BEAN_RECORD.setValue("age", 51);
		}
		
		Assert.assertTrue(ErrorCode.FE10.equals(errorCode));
	}
	
	@Test
	public void testPerson1000LenConstructor() {
		Assert.assertTrue(1000 == PERSON_1000_BEAN_RECORD.toString().length());
	}
	
	@Test
	public void testPerson1000ToStringConstructor() {
		Assert.assertTrue((PERSON_RECORD_AS_STRING + String.format("%-903s", "")).contentEquals(PERSON_1000_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testPersonInitRecordToString() {
		PERSON_BEAN_RECORD_INIT_WITH_STRING.initRecord(PERSON_RECORD_AS_STRING);
		Assert.assertTrue(PERSON_RECORD_AS_STRING.equals(PERSON_BEAN_RECORD_INIT_WITH_STRING.toString()));
	}
	
	@Test
	public void testPersonField() {
		Assert.assertTrue("Paolo".equals(PERSON_BEAN.getFirstName()));
		Assert.assertTrue("Rossi".equals(PERSON_BEAN.getLastName()));
		Assert.assertTrue(51 == PERSON_BEAN.getAge());
		Assert.assertTrue(CAL.getTime().equals(PERSON_BEAN.getBirthDate()));
		Assert.assertTrue(1.86f == PERSON_BEAN.getStature());
		Assert.assertTrue("bo".equals(PERSON_BEAN.getBirthDistrict()));
		Assert.assertFalse(PERSON_BEAN.getVip());
	}
	
	@Test
	public void testPersonFieldNotSet() {
		Assert.assertNull(PERSON_1000_BEAN.getFirstName());
	}
	
	@Test
	public void testPersonFieldToString() {
		Assert.assertTrue("Paolo".equals(PERSON_BEAN_FOR_STRING.getFirstName()));
		Assert.assertTrue("Rossi".equals(PERSON_BEAN_FOR_STRING.getLastName()));
		Assert.assertTrue(51 == PERSON_BEAN_FOR_STRING.getAge());
		Assert.assertTrue(CAL.getTime().equals(PERSON_BEAN_FOR_STRING.getBirthDate()));
		Assert.assertTrue(1.86f == PERSON_BEAN_FOR_STRING.getStature());
		Assert.assertTrue("bo".equals(PERSON_BEAN_FOR_STRING.getBirthDistrict()));
		Assert.assertFalse(PERSON_BEAN_FOR_STRING.getVip());
	}
	
	@Test
	public void testPersonFieldInitRecordToString() {
		PERSON_BEAN_RECORD_INIT_WITH_STRING.initRecord(PERSON_RECORD_AS_STRING);
		Assert.assertTrue("Paolo".equals(PERSON_BEAN_FOR_INIT_WITH_STRING.getFirstName()));
		Assert.assertTrue("Rossi".equals(PERSON_BEAN_FOR_INIT_WITH_STRING.getLastName()));
		Assert.assertTrue(51 == PERSON_BEAN_FOR_INIT_WITH_STRING.getAge());
		Assert.assertTrue(CAL.getTime().equals(PERSON_BEAN_FOR_INIT_WITH_STRING.getBirthDate()));
		Assert.assertTrue(1.86f == PERSON_BEAN_FOR_INIT_WITH_STRING.getStature());
		Assert.assertTrue("bo".equals(PERSON_BEAN_FOR_INIT_WITH_STRING.getBirthDistrict()));
		Assert.assertFalse(PERSON_BEAN_FOR_STRING.getVip());
	}
	
	@Test
	public void testPersonDefaultRecordWay() { 
		Assert.assertTrue(RecordWay.IN.equals(PERSON_BEAN_RECORD.getRecordWay()));
	}
	
	@Test
	public void testPersonSetRecordWay() {
		PERSON_BEAN_RECORD.setRecordWay(RecordWay.OUT); 
		Assert.assertTrue(RecordWay.OUT.equals(PERSON_BEAN_RECORD.getRecordWay()));
		PERSON_BEAN_RECORD.setRecordWay(RecordWay.IN); 
	}
	
	@Test
	public void testPersonCreateRecordWay() { 
		Assert.assertTrue(RecordWay.OUT.equals(PERSON_BEAN_RECORD_WAY_OUT.getRecordWay()));
	}
	
	@Test
	public void testRecordFieldValidationInfo() {
		Map<String, FieldValidationInfo> recordFieldValidationInfoMap = PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.getRecordFieldValidationInfo();
		Assert.assertTrue(21 == recordFieldValidationInfoMap.size());
	}
	
	@Test
	public void testRecordFieldErrorValidationInfo() {
		Map<String, FieldValidationInfo> recordFieldErrorValidationInfoMap = PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.getRecordFieldErrorValidationInfo();
		Assert.assertTrue(0 == recordFieldErrorValidationInfoMap.size());
	}
	
	@Test
	public void testPrettyPrint() {
		String prettyPrint = PERSON_WITH_ADDRESS_WITH_FIXED_VALUES_BEAN_RECORD.prettyPrint();
		Assert.assertTrue(0 < prettyPrint.length()); 
	}
}
