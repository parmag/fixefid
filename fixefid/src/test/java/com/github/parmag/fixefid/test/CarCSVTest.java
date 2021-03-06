package com.github.parmag.fixefid.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.csv.CSVBeanRecord;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.format.SimpleBooleanFormat;
import com.github.parmag.fixefid.test.csv.Car;
import com.github.parmag.fixefid.test.csv.CarWithCheckup;
import com.github.parmag.fixefid.test.csv.CarWithCheckupOccurs;
import com.github.parmag.fixefid.test.csv.Checkup;

public class CarCSVTest {
	private static final Calendar CAL = Calendar.getInstance();
	
	private static final Date PRODUCTION_DATE;
	private static final Date CHECKUP_DATE_1;
	private static final Date CHECKUP_DATE_2;
	private static final Date CHECKUP_DATE_3;
	
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);

	private static final Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = 
			new HashMap<String, List<FieldExtendedProperty>>();

	private static final Car CAR_CSV_BEAN = new Car();
	private static final Car CAR_CSV_BEAN_FOR_STRING = new Car();
	private static final Car CAR_CSV_BEAN_FOR_INIT_WITH_STRING = new Car();
	private static final Car CAR_CSV_BEAN_FOR_INIT_FIELD = new Car();
	
	private static final CarWithCheckup CAR_WITH_CHECKUP_CSV_BEAN = new CarWithCheckup();
	private static final CarWithCheckup CAR_WITH_CHECKUP_CSV_BEAN_FOR_STRING = new CarWithCheckup();
	private static final CarWithCheckup CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING = new CarWithCheckup();
	private static final CarWithCheckup CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD = new CarWithCheckup();
	
	private static final CarWithCheckupOccurs CAR_WITH_CHECKUP_OCCURS_CSV_BEAN = new CarWithCheckupOccurs();
	private static final CarWithCheckupOccurs CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_STRING = new CarWithCheckupOccurs();
	private static final CarWithCheckupOccurs CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING = new CarWithCheckupOccurs();
	private static final CarWithCheckupOccurs CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD = new CarWithCheckupOccurs();
	
	public static final String CAR_CSV_RECORD_AS_STRING = "Citroen,C3 Picasso,140800,4078,1730,1624,183,07102002,Y";
	public static final String CAR_WITH_CHECKUP_CSV_RECORD_AS_STRING = "Citroen,C3 Picasso,140800,4078,1730,1624,183,07102002,Y,23122020,35000,Tagliando dei 150.000 KM";
	public static final String CAR_WITH_CHECKUP_OCCURS_CSV_RECORD_AS_STRING = "Citroen,C3 Picasso,140800,4078,1730,1624,183,07102002,Y,23122020,35000,Tagliando dei 150.000 KM,10072015,25000,Tagliando dei 100.000 KM,01022000,15000,Tagliando dei 50.000 KM";
	
	private static final CSVBeanRecord CAR_CSV_BEAN_RECORD; 
	private static final CSVBeanRecord CAR_CSV_BEAN_RECORD_STRING;
	private static final CSVBeanRecord CAR_CSV_BEAN_RECORD_INIT_WITH_STRING;
	private static final CSVBeanRecord CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD;
	
	private static final CSVBeanRecord CAR_WITH_CHECKUP_CSV_BEAN_RECORD;
	private static final CSVBeanRecord CAR_WITH_CHECKUP_CSV_BEAN_RECORD_STRING;
	private static final CSVBeanRecord CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_STRING;
	private static final CSVBeanRecord CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD;
	
	private static final CSVBeanRecord CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD;
	private static final CSVBeanRecord CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_STRING;
	private static final CSVBeanRecord CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_STRING;
	private static final CSVBeanRecord CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD;
	
	static {
		CAL.set(Calendar.DAY_OF_MONTH, 7);
		CAL.set(Calendar.MONTH, 9);
		CAL.set(Calendar.YEAR, 2002);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
		
		PRODUCTION_DATE = CAL.getTime();
		
		CAL.set(Calendar.DAY_OF_MONTH, 23);
		CAL.set(Calendar.MONTH, 11);
		CAL.set(Calendar.YEAR, 2020);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
		
		CHECKUP_DATE_1 = CAL.getTime();
		
		CAL.set(Calendar.DAY_OF_MONTH, 10);
		CAL.set(Calendar.MONTH, 6);
		CAL.set(Calendar.YEAR, 2015);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
		
		CHECKUP_DATE_2 = CAL.getTime();
		
		CAL.set(Calendar.DAY_OF_MONTH, 1);
		CAL.set(Calendar.MONTH, 1);
		CAL.set(Calendar.YEAR, 2000);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
		
		CHECKUP_DATE_3 = CAL.getTime();
		
		MAP_FIELD_EXTENDED_PROPERTIES.put("weight", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, DECIMAL_FORMAT),
				new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, Boolean.valueOf(true)))
		);
		MAP_FIELD_EXTENDED_PROPERTIES.put("productionDate", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, DATE_FORMAT))
		);
		MAP_FIELD_EXTENDED_PROPERTIES.put("used", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new SimpleBooleanFormat("Y", "N"))));
		
		MAP_FIELD_EXTENDED_PROPERTIES.put("checkup.checkupDate", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, DATE_FORMAT))
		);
		MAP_FIELD_EXTENDED_PROPERTIES.put("checkup.cost", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, DECIMAL_FORMAT),
				new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, Boolean.valueOf(true)))
		);
		
		MAP_FIELD_EXTENDED_PROPERTIES.put("checkups.checkupDate", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, DATE_FORMAT))
		);
		MAP_FIELD_EXTENDED_PROPERTIES.put("checkups.cost", Arrays.asList(
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, DECIMAL_FORMAT),
				new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, Boolean.valueOf(true)))
		);
		
		CAR_CSV_BEAN_RECORD = new CSVBeanRecord(CAR_CSV_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		CAR_CSV_BEAN_RECORD.setValue("name", "Citroen");
		CAR_CSV_BEAN_RECORD.setValue("model", "C3 Picasso");
		CAR_CSV_BEAN_RECORD.setValue("weight", 1408.00);
		CAR_CSV_BEAN_RECORD.setValue("length", 4078);
		CAR_CSV_BEAN_RECORD.setValue("width", 1730);
		CAR_CSV_BEAN_RECORD.setValue("height", 1624);
		CAR_CSV_BEAN_RECORD.setValue("speed", 183);
		CAR_CSV_BEAN_RECORD.setValue("productionDate", PRODUCTION_DATE);
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
		CAR_CSV_BEAN_FOR_INIT_FIELD.setProductionDate(PRODUCTION_DATE);
		CAR_CSV_BEAN_FOR_INIT_FIELD.setUsed(true);
		
		CAR_CSV_BEAN_RECORD_INIT_WITH_FIELD = new CSVBeanRecord(CAR_CSV_BEAN_FOR_INIT_FIELD, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
		
		// ******* WITH CHECKUP *******
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD = new CSVBeanRecord(CAR_WITH_CHECKUP_CSV_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("name", "Citroen");
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("model", "C3 Picasso");
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("weight", 1408.00);
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("length", 4078);
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("width", 1730);
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("height", 1624);
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("speed", 183);
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("productionDate", PRODUCTION_DATE);
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("used", true);
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("checkup.checkupDate", CHECKUP_DATE_1);
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("checkup.cost", BigDecimal.valueOf(350.00));
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD.setValue("checkup.description", "Tagliando dei 150.000 KM");
		
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_STRING = new CSVBeanRecord(CAR_WITH_CHECKUP_CSV_BEAN_FOR_STRING, CAR_WITH_CHECKUP_CSV_RECORD_AS_STRING, null, MAP_FIELD_EXTENDED_PROPERTIES);
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_STRING = new CSVBeanRecord(CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
		
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.setName("Citroen");
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.setModel("C3 Picasso");
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.setWeight(1408.00);
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.setLength(4078);
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.setWidth(1730);
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.setHeight(1624);
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.setSpeed(183);
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.setProductionDate(PRODUCTION_DATE);
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.setUsed(true);
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.getCheckup().setCheckupDate(CHECKUP_DATE_1);
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.getCheckup().setCost(BigDecimal.valueOf(350.00));
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.getCheckup().setDescription("Tagliando dei 150.000 KM");
		
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD = new CSVBeanRecord(CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
		
		// ******* WITH CHECKUP OCCURS *******
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD = new CSVBeanRecord(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN, null, null, MAP_FIELD_EXTENDED_PROPERTIES); 
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("name", "Citroen");
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("model", "C3 Picasso");
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("weight", 1408.00);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("length", 4078);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("width", 1730);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("height", 1624);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("speed", 183);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("productionDate", PRODUCTION_DATE);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("used", true);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("checkups.checkupDate", CHECKUP_DATE_1, 1, 1);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("checkups.cost", BigDecimal.valueOf(350.00), 1, 1);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("checkups.description", "Tagliando dei 150.000 KM", 1, 1);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("checkups.checkupDate", CHECKUP_DATE_2, 2, 1);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("checkups.cost", BigDecimal.valueOf(250.00), 2, 1);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("checkups.description", "Tagliando dei 100.000 KM", 2, 1);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("checkups.checkupDate", CHECKUP_DATE_3, 3, 1);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("checkups.cost", BigDecimal.valueOf(150.00), 3, 1);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.setValue("checkups.description", "Tagliando dei 50.000 KM", 3, 1);
		
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_STRING = new CSVBeanRecord(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_STRING, CAR_WITH_CHECKUP_OCCURS_CSV_RECORD_AS_STRING, null, MAP_FIELD_EXTENDED_PROPERTIES);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_STRING = new CSVBeanRecord(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
		
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.setName("Citroen");
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.setModel("C3 Picasso");
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.setWeight(1408.00);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.setLength(4078);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.setWidth(1730);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.setHeight(1624);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.setSpeed(183);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.setProductionDate(PRODUCTION_DATE);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.setUsed(true);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().add(new Checkup());
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(0).setCheckupDate(CHECKUP_DATE_1);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(0).setCost(BigDecimal.valueOf(350.00));
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(0).setDescription("Tagliando dei 150.000 KM");
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().add(new Checkup());
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(1).setCheckupDate(CHECKUP_DATE_2);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(1).setCost(BigDecimal.valueOf(250.00));
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(1).setDescription("Tagliando dei 100.000 KM");
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().add(new Checkup());
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(2).setCheckupDate(CHECKUP_DATE_3);
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(2).setCost(BigDecimal.valueOf(150.00));
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(2).setDescription("Tagliando dei 50.000 KM");
		
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD = new CSVBeanRecord(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
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
		Assert.assertTrue(1624 == CAR_CSV_BEAN_FOR_INIT_WITH_STRING.getHeight());
		Assert.assertTrue(183 == CAR_CSV_BEAN_FOR_INIT_WITH_STRING.getSpeed());
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
	
	// ****** WITH CHECKUP *********
	
	@Test
	public void testCarWithCheckupToString() { 
		Assert.assertTrue(CAR_WITH_CHECKUP_CSV_RECORD_AS_STRING.contentEquals(CAR_WITH_CHECKUP_CSV_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testCarWithCheckupToStringConstructor() {
		Assert.assertTrue(CAR_WITH_CHECKUP_CSV_RECORD_AS_STRING.contentEquals(CAR_WITH_CHECKUP_CSV_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testCarWithCheckupInitRecordToString() {
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_STRING.initRecord(CAR_WITH_CHECKUP_CSV_RECORD_AS_STRING);
		Assert.assertTrue(CAR_WITH_CHECKUP_CSV_RECORD_AS_STRING.equals(CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_STRING.toString()));
	}
	
	@Test
	public void testCarWithCheckupFieldInitRecordToString() {
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_STRING.initRecord(CAR_WITH_CHECKUP_CSV_RECORD_AS_STRING);
		Assert.assertTrue("Citroen".equals(CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING.getName()));
		Assert.assertTrue("C3 Picasso".equals(CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING.getModel()));
		Assert.assertTrue(1408.00 == CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING.getWeight());
		Assert.assertTrue(4078 == CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING.getLength());
		Assert.assertTrue(1730 == CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING.getWidth());
		Assert.assertTrue(1624 == CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING.getHeight());
		Assert.assertTrue(183 == CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING.getSpeed());
		Assert.assertTrue(350.00 == CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_WITH_STRING.getCheckup().getCost().doubleValue());
	}
	
	@Test
	public void testCarWithCheckupToStringForInitField() { 
		Assert.assertTrue(CAR_WITH_CHECKUP_CSV_RECORD_AS_STRING.contentEquals(CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD.toString()));
	}
	
	@Test
	public void testCarWithCheckupSyncValuesFromBeanToRecord() {
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.getCheckup().setDescription("NA");
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
		
		Assert.assertTrue("NA".equals(CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD.getValueAsString("checkup.description"))); 
		
		CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.getCheckup().setDescription("Tagliando dei 150.000 KM");
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
	}
	
	@Test
	public void testCarWithCheckupSyncValuesFromRecordToBean() {
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD.setValue("checkup.description", "NA"); 
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
		
		Assert.assertTrue("NA".equals(CAR_WITH_CHECKUP_CSV_BEAN_FOR_INIT_FIELD.getCheckup().getDescription())); 
		
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD.setValue("checkup.description", "Tagliando dei 150.000 KM"); 
		CAR_WITH_CHECKUP_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
	}
	
	// ****** WITH CHECKUP OCCURS *********
	
	@Test
	public void testCarWithCheckupOccursToString() { 
		Assert.assertTrue(CAR_WITH_CHECKUP_OCCURS_CSV_RECORD_AS_STRING.contentEquals(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testCarWithCheckupOccursToStringConstructor() {
		Assert.assertTrue(CAR_WITH_CHECKUP_OCCURS_CSV_RECORD_AS_STRING.contentEquals(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testCarWithCheckupOccursInitRecordToString() {
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_STRING.initRecord(CAR_WITH_CHECKUP_OCCURS_CSV_RECORD_AS_STRING);
		Assert.assertTrue(CAR_WITH_CHECKUP_OCCURS_CSV_RECORD_AS_STRING.equals(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_STRING.toString()));
	}
	
	@Test
	public void testCarWithCheckupOccursFieldInitRecordToString() {
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_STRING.initRecord(CAR_WITH_CHECKUP_OCCURS_CSV_RECORD_AS_STRING);
		Assert.assertTrue("Citroen".equals(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getName()));
		Assert.assertTrue("C3 Picasso".equals(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getModel()));
		Assert.assertTrue(1408.00 == CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getWeight());
		Assert.assertTrue(4078 == CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getLength());
		Assert.assertTrue(1730 == CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getWidth());
		Assert.assertTrue(1624 == CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getHeight());
		Assert.assertTrue(183 == CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getSpeed());
		Assert.assertTrue(350.00 == CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getCheckups().get(0).getCost().doubleValue());
		Assert.assertTrue(250.00 == CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getCheckups().get(1).getCost().doubleValue());
		Assert.assertTrue(150.00 == CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_WITH_STRING.getCheckups().get(2).getCost().doubleValue());
	}
	
	@Test
	public void testCarWithCheckupOccursToStringForInitField() { 
		Assert.assertTrue(CAR_WITH_CHECKUP_OCCURS_CSV_RECORD_AS_STRING.contentEquals(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD.toString()));
	}
	
	@Test
	public void testCarWithCheckupOccursSyncValuesFromBeanToRecord() {
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(2).setDescription("NA");
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
		
		Assert.assertTrue("NA".equals(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD.getValueAsString("checkups.description", 3, 1))); 
		
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(2).setDescription("Tagliando dei 50.000 KM");
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromBeanToRecord();
	}
	
	@Test
	public void testCarWithCheckupOccursSyncValuesFromRecordToBean() {
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD.setValue("checkups.description", "NA", 3, 1); 
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
		
		Assert.assertTrue("NA".equals(CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_FOR_INIT_FIELD.getCheckups().get(2).getDescription())); 
		
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD.setValue("checkups.description", "Tagliando dei 50.000 KM", 3, 1); 
		CAR_WITH_CHECKUP_OCCURS_CSV_BEAN_RECORD_INIT_WITH_FIELD.syncValuesFromRecordToBean();
	}
	
	@Test
	public void testCarSpeedDisplayName() { 
		Assert.assertTrue("The Car's speed".contentEquals(CAR_CSV_BEAN_RECORD.getFieldDisplayName("speed"))); 
	}
	
	@Test
	public void testCarSpeedDescription() { 
		Assert.assertTrue("The Car's speed must be minor of 200 KM/H".contentEquals(CAR_CSV_BEAN_RECORD.getFieldDescription("speed"))); 
	}

}
