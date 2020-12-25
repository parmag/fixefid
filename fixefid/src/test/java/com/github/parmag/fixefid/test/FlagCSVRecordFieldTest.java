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
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "S", 1); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "N", 2); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "S", 3); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "S", 4); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "N", 5); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "S", 6); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "S", 7); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "N", 8); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "N", 9); 
		FLAG_CSV_RECORD.setValue(FlagCSVRecordField.flags, "N", 10); 
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
