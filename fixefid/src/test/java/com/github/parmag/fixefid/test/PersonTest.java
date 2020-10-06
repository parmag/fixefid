package com.github.parmag.fixefid.test;

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

import com.github.parmag.fixefid.record.bean.BeanRecord;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.format.CustomFormat;
import com.github.parmag.fixefid.test.bean.FakePerson;
import com.github.parmag.fixefid.test.bean.Person;

public class PersonTest {
	private static final Calendar CAL = Calendar.getInstance();
	
	private static final Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = 
			new HashMap<String, List<FieldExtendedProperty>>();
	
	private static final Person PERSON_BEAN = new Person();
	private static final String PERSON_RECORD_AS_STRING = "Paolo                    Rossi                    05107102002186BO";
	private static final BeanRecord PERSON_BEAN_RECORD; 
	private static final BeanRecord PERSON_BEAN_RECORD_STRING;
	
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
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))),
				new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, Boolean.valueOf(true))));
		MAP_FIELD_EXTENDED_PROPERTIES.put("birthDistrict", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.CUSTOM_FORMAT, new CustomFormat() {
			@Override
			public String format(String value) {
				return value.toUpperCase();
			}

			@Override
			public String parse(String value) {
				return value.toLowerCase();
			}
		})));
		
		PERSON_BEAN_RECORD = new BeanRecord(PERSON_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		PERSON_BEAN_RECORD.setValue("firstName", "Paolo");
		PERSON_BEAN_RECORD.setValue("lastName", "Rossi");
		PERSON_BEAN_RECORD.setValue("age", 51);
		PERSON_BEAN_RECORD.setValue("birthDate", CAL.getTime()); 
		PERSON_BEAN_RECORD.setValue("stature", 1.86f);
		PERSON_BEAN_RECORD.setValue("birthDistrict", "bo");
		
		PERSON_BEAN_RECORD_STRING = new BeanRecord(PERSON_BEAN, PERSON_RECORD_AS_STRING, null, MAP_FIELD_EXTENDED_PROPERTIES);
	}
	
	@Test
	public void testToString() { 
		Assert.assertTrue(PERSON_RECORD_AS_STRING.contentEquals(PERSON_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testToStringConstructor() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.contentEquals(PERSON_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testLen() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.length() == PERSON_BEAN_RECORD.getRecordLen());
	}
	
	@Test
	public void testGetBirthPlaceCustomFormatAsStringValue() {  
		Assert.assertTrue("bo".equals(PERSON_BEAN_RECORD.getValueAsString("birthDistrict")));
	}
	
	@Test
	public void testGetBirthPlaceCustomFormatAsString() {  
		Assert.assertTrue("BO".equals(PERSON_BEAN_RECORD.getValue("birthDistrict")));
	}
	
	@Test
	public void testFakePerson() {
		BeanRecord br = null;
		try {
			br = new BeanRecord(new FakePerson());
		} catch (Exception e) {
			
		}
		
		Assert.assertNull(br); 
	}
}
