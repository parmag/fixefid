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

import com.github.parmag.fixefid.record.BeanRecord;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldType;
import com.github.parmag.fixefid.record.field.FieldValidationInfo;
import com.github.parmag.fixefid.record.field.FieldValidator;
import com.github.parmag.fixefid.record.format.CustomFormat;
import com.github.parmag.fixefid.record.format.SimpleBooleanFormat;
import com.github.parmag.fixefid.test.bean.Customer;

public class CustomerTest {
	private static final Calendar CAL = Calendar.getInstance();
	
	private static final List<FieldExtendedProperty> BOOLEAN_FORMAT_LIST = Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new SimpleBooleanFormat("Y", "N")));
	
	private static final Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = 
			new HashMap<String, List<FieldExtendedProperty>>();
	
	private static final Customer CUSTOMER_BEAN = new Customer();
	public static final String CUSTOMER_RECORD_AS_STRING = PersonTest.PERSON_RECORD_AS_STRING + "0000000001paolo.rossi@serverxyz.com                         677 6575744              ";
	private static final BeanRecord CUSTOMER_BEAN_RECORD; 
	
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
		
		MAP_FIELD_EXTENDED_PROPERTIES.put("vip", BOOLEAN_FORMAT_LIST);
		MAP_FIELD_EXTENDED_PROPERTIES.put("tor", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.0000", new DecimalFormatSymbols(Locale.ENGLISH)))));
		MAP_FIELD_EXTENDED_PROPERTIES.put("turnover", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)))));
		MAP_FIELD_EXTENDED_PROPERTIES.put("active", BOOLEAN_FORMAT_LIST);
		
		MAP_FIELD_EXTENDED_PROPERTIES.put("age", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.VALIDATOR, new FieldValidator() {
			@Override
			public FieldValidationInfo valid(String name, int index, int subIndex, int occurIndex, FieldType type, FieldMandatory mandatory, 
					String value, List<FieldExtendedProperty> fieldExtendedProperties, List<String> fixedValues) {
				int age = Integer.valueOf(value);
				return age >= 18 ? new FieldValidationInfo() : new FieldValidationInfo(FieldValidationInfo.RecordFieldValidationStatus.ERROR, "Student age must be >= 18", 100);
			}
		})));
		
		CUSTOMER_BEAN_RECORD = new BeanRecord(CUSTOMER_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		CUSTOMER_BEAN_RECORD.setValue("firstName", "Paolo");
		CUSTOMER_BEAN_RECORD.setValue("lastName", "Rossi");
		CUSTOMER_BEAN_RECORD.setValue("age", 51);
		CUSTOMER_BEAN_RECORD.setValue("birthDate", CAL.getTime()); 
		CUSTOMER_BEAN_RECORD.setValue("stature", 1.86f);
		CUSTOMER_BEAN_RECORD.setValue("birthDistrict", "bo");
		CUSTOMER_BEAN_RECORD.setValue("vip", "N");
		CUSTOMER_BEAN_RECORD.setValue("id", "0000000001");
		CUSTOMER_BEAN_RECORD.setValue("tor", "00001.0001");
		CUSTOMER_BEAN_RECORD.setValue("turnover", "0100000.00");
		CUSTOMER_BEAN_RECORD.setValue("customerId", "0000000001");
		CUSTOMER_BEAN_RECORD.setValue("email", "paolo.rossi@serverxyz.com");
		CUSTOMER_BEAN_RECORD.setValue("phone", "677 6575744");
	}
	
	@Test
	public void testToString() { 
		Assert.assertTrue(CUSTOMER_RECORD_AS_STRING.contentEquals(CUSTOMER_BEAN_RECORD.toString()));
	}
	
}
