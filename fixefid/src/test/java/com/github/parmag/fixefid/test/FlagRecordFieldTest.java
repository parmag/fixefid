package com.github.parmag.fixefid.test;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.Record;
import com.github.parmag.fixefid.test.record.FlagRecordField;

public class FlagRecordFieldTest {

	private static final String FLAG_RECORD_AS_STRING = "Circuit             SNSSNSSNNNCX34ZY7   ";
	
	private static final Record<FlagRecordField> FLAG_RECORD = new Record<FlagRecordField>(FlagRecordField.class);
	private static final Record<FlagRecordField> FLAG_RECORD_STRING = new Record<FlagRecordField>(FLAG_RECORD_AS_STRING, FlagRecordField.class);
	private static final Record<FlagRecordField> FLAG_RECORD_INIT_WITH_STRING = new Record<FlagRecordField>(FlagRecordField.class);
	
	static {
		FLAG_RECORD.setValue(FlagRecordField.name, "Circuit"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 1, "S"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 2, "N"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 3, "S"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 4, "S"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 5, "N"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 6, "S"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 7, "S"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 8, "N"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 9, "N"); 
		FLAG_RECORD.setValue(FlagRecordField.flags, 10, "N"); 
		FLAG_RECORD.setValue(FlagRecordField.program, "CX34ZY7"); 
	}

	@Test
	public void testToString() {
		Assert.assertTrue(FLAG_RECORD_AS_STRING.equals(FLAG_RECORD.toString()));
	}
	
	@Test
	public void testToStringConstructor() {
		Assert.assertTrue(FLAG_RECORD_AS_STRING.equals(FLAG_RECORD_STRING.toString()));
	}
	
	@Test
	public void testToStringConstructorGetStringValue() {
		Assert.assertTrue("CX34ZY7".equals(FLAG_RECORD_STRING.getValueAsString(FlagRecordField.program)));
	}
	
	@Test
	public void testToStringConstructorGetStringNValueWithOccur() {
		Assert.assertTrue("N".equals(FLAG_RECORD_STRING.getValueAsString(FlagRecordField.flags, 5)));
	}
	
	@Test
	public void testToStringConstructorGetStringSValueWithOccur() {
		Assert.assertTrue("S".equals(FLAG_RECORD_STRING.getValueAsString(FlagRecordField.flags, 7)));
	}
	
	@Test
	public void testInitRecordToString() {
		FLAG_RECORD_INIT_WITH_STRING.initRecord(FLAG_RECORD_AS_STRING);
		Assert.assertTrue(FLAG_RECORD_AS_STRING.equals(FLAG_RECORD_INIT_WITH_STRING.toString()));
	}
}
