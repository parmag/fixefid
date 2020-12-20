package com.github.parmag.fixefid.test;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.csv.CSVRecord;
import com.github.parmag.fixefid.test.csv.FlagCSVRecordField;

public class FlagCSVRecordFieldTest {

	private static final String FLAG_CSV_RECORD_AS_STRING = "Circuit,S,N,S,S,N,S,S,N,N,N,CX34ZY7";
	
	private static final CSVRecord<FlagCSVRecordField> FLAG_CSV_RECORD = new CSVRecord<FlagCSVRecordField>(FlagCSVRecordField.class);
	private static final CSVRecord<FlagCSVRecordField> FLAG_CSV_RECORD_STRING = new CSVRecord<FlagCSVRecordField>(FLAG_CSV_RECORD_AS_STRING, FlagCSVRecordField.class);
	private static final CSVRecord<FlagCSVRecordField> FLAG_CSV_RECORD_INIT_WITH_STRING = new CSVRecord<FlagCSVRecordField>(FlagCSVRecordField.class);
	
	static {
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.name, "Circuit"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 1, "S"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 2, "N"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 3, "S"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 4, "S"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 5, "N"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 6, "S"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 7, "S"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 8, "N"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 9, "N"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, 10, "N"); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.program, "CX34ZY7"); 
	}

	@Test
	public void testToString() {
		Assert.assertTrue(FLAG_CSV_RECORD_AS_STRING.equals(FLAG_CSV_RECORD.toString()));
	}
	
	@Test
	public void testToStringConstructor() {
		Assert.assertTrue(FLAG_CSV_RECORD_AS_STRING.equals(FLAG_CSV_RECORD_STRING.toString()));
	}
	
	@Test
	public void testToStringConstructorGetStringValue() {
		Assert.assertTrue("CX34ZY7".equals(FLAG_CSV_RECORD_STRING.getValueAsString(FlagCSVRecordField.program)));
	}
	
	@Test
	public void testToStringConstructorGetStringNValueWithOccur() {
		Assert.assertTrue("N".equals(FLAG_CSV_RECORD_STRING.getValueAsString(FlagCSVRecordField.flags, 5)));
	}
	
	@Test
	public void testToStringConstructorGetStringSValueWithOccur() {
		Assert.assertTrue("S".equals(FLAG_CSV_RECORD_STRING.getValueAsString(FlagCSVRecordField.flags, 7)));
	}
	
	@Test
	public void testInitRecordToString() {
		FLAG_CSV_RECORD_INIT_WITH_STRING.initRecord(FLAG_CSV_RECORD_AS_STRING);
		Assert.assertTrue(FLAG_CSV_RECORD_AS_STRING.equals(FLAG_CSV_RECORD_INIT_WITH_STRING.toString()));
	}
}
