package com.github.parmag.fixefid.test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.csv.CSVRecord;
import com.github.parmag.fixefid.test.csv.CarRecordField;

public class CarCSVRecordFieldTest {
	private static final Calendar CAL = Calendar.getInstance();

	public static final String CAR_CSV_RECORD_AS_STRING = "Citroen,C3 Picasso,140800,4078,1730,1624,183,07102002,Y";
	
	private static final CSVRecord<CarRecordField> CAR_CSV_RECORD = new CSVRecord<CarRecordField>(CarRecordField.class);
	private static final CSVRecord<CarRecordField> CAR_CSV_RECORD_STRING = new CSVRecord<CarRecordField>(CAR_CSV_RECORD_AS_STRING, CarRecordField.class);
	private static final CSVRecord<CarRecordField> CAR_CSV_RECORD_INIT_WITH_STRING = new CSVRecord<CarRecordField>(CarRecordField.class);
	
	static {
		CAL.set(Calendar.DAY_OF_MONTH, 7);
		CAL.set(Calendar.MONTH, 9);
		CAL.set(Calendar.YEAR, 2002);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
		
		CAR_CSV_RECORD.setValue("name", "Citroen");
		CAR_CSV_RECORD.setValue("model", "C3 Picasso");
		CAR_CSV_RECORD.setValue("weight", 1408.00);
		CAR_CSV_RECORD.setValue("length", 4078);
		CAR_CSV_RECORD.setValue("width", 1730);
		CAR_CSV_RECORD.setValue("height", 1624);
		CAR_CSV_RECORD.setValue("speed", 183);
		CAR_CSV_RECORD.setValue("productionDate", CAL.getTime());
		CAR_CSV_RECORD.setValue("used", true);
		
	}
	
	@Test
	public void testCarToString() { 
		Assert.assertTrue(CAR_CSV_RECORD_AS_STRING.contentEquals(CAR_CSV_RECORD.toString()));
	}
	
	@Test
	public void testCarToStringConstructor() {
		Assert.assertTrue(CAR_CSV_RECORD_AS_STRING.contentEquals(CAR_CSV_RECORD_STRING.toString()));
	}
	
	@Test
	public void testCarInitRecordToString() {
		CAR_CSV_RECORD_INIT_WITH_STRING.initRecord(CAR_CSV_RECORD_AS_STRING);
		Assert.assertTrue(CAR_CSV_RECORD_AS_STRING.equals(CAR_CSV_RECORD_INIT_WITH_STRING.toString()));
	}
	
	@Test
	public void testCarFieldInitRecordToString() {
		CAR_CSV_RECORD_INIT_WITH_STRING.initRecord(CAR_CSV_RECORD_AS_STRING);
		Assert.assertTrue("Citroen".equals(CAR_CSV_RECORD_INIT_WITH_STRING.getValueAsString(CarRecordField.name)));
		Assert.assertTrue("C3 Picasso".equals(CAR_CSV_RECORD_INIT_WITH_STRING.getValueAsString(CarRecordField.model)));
		Assert.assertTrue(1408.00 == CAR_CSV_RECORD_INIT_WITH_STRING.getValueAsDouble(CarRecordField.weight));
		Assert.assertTrue(4078 == CAR_CSV_RECORD_INIT_WITH_STRING.getValueAsInteger(CarRecordField.length));
		Assert.assertTrue(1730 == CAR_CSV_RECORD_INIT_WITH_STRING.getValueAsInteger(CarRecordField.width));
		Assert.assertTrue(1624 == CAR_CSV_RECORD_INIT_WITH_STRING.getValueAsInteger(CarRecordField.height));
		Assert.assertTrue(183 == CAR_CSV_RECORD_INIT_WITH_STRING.getValueAsInteger(CarRecordField.speed));
	}
}
