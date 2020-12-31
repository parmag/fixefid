package com.github.parmag.fixefid.test;

import java.math.BigDecimal;
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
import com.github.parmag.fixefid.record.RecordWay;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.format.CustomFormat;
import com.github.parmag.fixefid.record.format.SimpleBooleanFormat;
import com.github.parmag.fixefid.test.bean.PersonWithEPAnnotation;

public class PersonWithEPAnnotationTest {
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
	
	private static final PersonWithEPAnnotation PERSON_BEAN = new PersonWithEPAnnotation();
	private static final PersonWithEPAnnotation PERSON_BEAN_FOR_STRING = new PersonWithEPAnnotation();
	private static final PersonWithEPAnnotation PERSON_BEAN_FOR_INIT_WITH_STRING = new PersonWithEPAnnotation();
	private static final PersonWithEPAnnotation PERSON_BEAN_FOR_INIT_FIELD = new PersonWithEPAnnotation();
	
	public static final String PERSON_RECORD_AS_STRING = "Paolo                    Rossi                    05107102002186BON000000000100001.00010100000.00";
	
	private static final BeanRecord PERSON_BEAN_RECORD; 
	private static final BeanRecord PERSON_BEAN_RECORD_WAY_OUT; 
	private static final BeanRecord PERSON_BEAN_RECORD_STRING;
	private static final BeanRecord PERSON_BEAN_RECORD_INIT_WITH_STRING;
	private static final BeanRecord PERSON_BEAN_RECORD_INIT_WITH_FIELD;
	
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
		
		MAP_FIELD_EXTENDED_PROPERTIES.put("birthDistrict", CUSTOM_FORMAT_LIST);
		MAP_FIELD_EXTENDED_PROPERTIES.put("vip", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new SimpleBooleanFormat("Y", "N"))));
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
	}
	
	@Test
	public void testPersonToString() { 
		Assert.assertTrue(PERSON_RECORD_AS_STRING.contentEquals(PERSON_BEAN_RECORD.toString()));
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
	public void testPersonLen() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.length() == PERSON_BEAN_RECORD.getRecordLen());
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
}
