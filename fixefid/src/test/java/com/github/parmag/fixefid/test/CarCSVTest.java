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

import com.github.parmag.fixefid.record.csv.CSVBeanRecord;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.format.BooleanFormat;
import com.github.parmag.fixefid.test.csv.Car;

public class CarCSVTest {
	private static final Calendar CAL = Calendar.getInstance();
	
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);

	private static final Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = 
			new HashMap<String, List<FieldExtendedProperty>>();

	private static final Car CAR_CSV_BEAN = new Car();
	private static final Car CAR_CSV_BEAN_FOR_STRING = new Car();
	private static final Car CAR_CSV_BEAN_FOR_INIT_WITH_STRING = new Car();
	private static final Car CAR_CSV_BEAN_FOR_INIT_FIELD = new Car();
	
	public static final String CAR_CSV_RECORD_AS_STRING = "Citroen,C3 Picasso,140800,4078,1730,1624,183,07102002,Y";
	
	private static final CSVBeanRecord CAR_CSV_BEAN_RECORD; 
	private static final CSVBeanRecord CAR_CSV_BEAN_RECORD_STRING;
	private static final CSVBeanRecord CAR_CSV_BEAN_RECORD_INIT_WITH_STRING;
	private static final CSVBeanRecord CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD;
	
	static {
		CAL.set(Calendar.DAY_OF_MONTH, 7);
		CAL.set(Calendar.MONTH, 9);
		CAL.set(Calendar.YEAR, 2002);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
		
		MAP_FIELD_EXTENDED_PROPERTIES.put("weight", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, DECIMAL_FORMAT),
				new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, Boolean.valueOf(true)))
		);
		MAP_FIELD_EXTENDED_PROPERTIES.put("productionDate", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, DATE_FORMAT))
		);
		MAP_FIELD_EXTENDED_PROPERTIES.put("used", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new BooleanFormat() {
					@Override
					public String format(Boolean value) {
						return value ? "Y" : "N";
					}

					@Override
					public Boolean parse(String value) {
						return "Y".equals(value) ? true : false;
					}
		})));
		
		CAR_CSV_BEAN_RECORD = new CSVBeanRecord(CAR_CSV_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		CAR_CSV_BEAN_RECORD.setValue("name", "Citroen");
		CAR_CSV_BEAN_RECORD.setValue("model", "C3 Picasso");
		CAR_CSV_BEAN_RECORD.setValue("weight", 1408.00);
		CAR_CSV_BEAN_RECORD.setValue("length", 4078);
		CAR_CSV_BEAN_RECORD.setValue("width", 1730);
		CAR_CSV_BEAN_RECORD.setValue("height", 1624);
		CAR_CSV_BEAN_RECORD.setValue("speed", 183);
		CAR_CSV_BEAN_RECORD.setValue("productionDate", CAL.getTime());
		CAR_CSV_BEAN_RECORD.setValue("used", true);
		
		CAR_CSV_BEAN_RECORD_STRING = new CSVBeanRecord(CAR_CSV_BEAN_FOR_STRING, CAR_CSV_RECORD_AS_STRING, null, MAP_FIELD_EXTENDED_PROPERTIES);
		CAR_CSV_BEAN_RECORD_INIT_WITH_STRING = new CSVBeanRecord(CAR_CSV_BEAN_FOR_INIT_WITH_STRING, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
		
		CAR_CSV_BEAN_FOR_INIT_FIELD.setName("Citroen");
		CAR_CSV_BEAN_FOR_INIT_FIELD.setModel("C3 Picasso");
		CAR_CSV_BEAN_FOR_INIT_FIELD.setWeight(1408.00);
		CAR_CSV_BEAN_FOR_INIT_FIELD.setLength(4078);
		CAR_CSV_BEAN_FOR_INIT_FIELD.setWidth(1730);
		CAR_CSV_BEAN_FOR_INIT_FIELD.setHeight(1624);
		CAR_CSV_BEAN_FOR_INIT_FIELD.setSpeed(183);
		CAR_CSV_BEAN_FOR_INIT_FIELD.setProductionDate(CAL.getTime());
		CAR_CSV_BEAN_FOR_INIT_FIELD.setUsed(true);
		
		CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD = new CSVBeanRecord(CAR_CSV_BEAN_FOR_INIT_FIELD, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
	}
	
	@Test
	public void testCarToString() { 
		Assert.assertTrue(CAR_CSV_RECORD_AS_STRING.contentEquals(CAR_CSV_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testCarToStringConstructor() {
		Assert.assertTrue(CAR_CSV_RECORD_AS_STRING.contentEquals(CAR_CSV_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testCarInitRecordToString() {
		CAR_CSV_BEAN_RECORD_INIT_WITH_STRING.initRecord(CAR_CSV_RECORD_AS_STRING);
		Assert.assertTrue(CAR_CSV_RECORD_AS_STRING.equals(CAR_CSV_BEAN_RECORD_INIT_WITH_STRING.toString()));
	}
	
	@Test
	public void testCarFieldInitRecordToString() {
		CAR_CSV_BEAN_RECORD_INIT_WITH_STRING.initRecord(CAR_CSV_RECORD_AS_STRING);
		Assert.assertTrue("Citroen".equals(CAR_CSV_BEAN_FOR_INIT_WITH_STRING.getName()));
		Assert.assertTrue("C3 Picasso".equals(CAR_CSV_BEAN_FOR_INIT_WITH_STRING.getModel()));
		Assert.assertTrue(1408.00 == CAR_CSV_BEAN_FOR_INIT_WITH_STRING.getWeight());
		Assert.assertTrue(4078 == CAR_CSV_BEAN_FOR_INIT_WITH_STRING.getLength());
		Assert.assertTrue(1730 == CAR_CSV_BEAN_FOR_INIT_WITH_STRING.getWidth());
		Assert.assertTrue(1624 == CAR_CSV_BEAN_FOR_STRING.getHeight());
		Assert.assertTrue(183 == CAR_CSV_BEAN_FOR_STRING.getSpeed());
	}
	
	@Test
	public void testCarToStringForInitField() { 
		Assert.assertTrue(CAR_CSV_RECORD_AS_STRING.contentEquals(CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD.toString()));
	}
	
	@Test
	public void testCarSyncValuesFromBeanToRecord() {
		CAR_CSV_BEAN_FOR_INIT_FIELD.setSpeed(25);
		CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
		
		Assert.assertTrue(25 == CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD.getValueAsInteger("speed")); 
		
		CAR_CSV_BEAN_FOR_INIT_FIELD.setSpeed(183);
		CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
	}
	
	@Test
	public void testCarSyncValuesFromRecordToBean() {
		CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD.setValue("speed", 3500); 
		CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
		
		Assert.assertTrue(3500 == CAR_CSV_BEAN_FOR_INIT_FIELD.getSpeed()); 
		
		CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD.setValue("speed", 183); 
		CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
	}

}
