package com.github.parmag.fixefid.test;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.csv.CSVBeanRecord;
import com.github.parmag.fixefid.test.csv.FlagCSV;

public class FlagCSVTest {

	private static final String FLAG_CSV_RECORD_AS_STRING = "Circuit,S,N,S,S,N,S,S,N,N,N,CX34ZY7";
	
	private static final FlagCSV FLAG_CSV_BEAN = new FlagCSV();
	private static final FlagCSV FLAG_CSV_BEAN_FOR_STRING = new FlagCSV();
	
	private static final CSVBeanRecord FLAG_CSV_BEAN_RECORD; 
	private static final CSVBeanRecord FLAG_CSV_BEAN_RECORD_STRING;
	private static final CSVBeanRecord FLAG_CSV_BEAN_RECORD_INIT_WITH_STRING;
	
	static {
		FLAG_CSV_BEAN_RECORD = new CSVBeanRecord(FLAG_CSV_BEAN);
		FLAG_CSV_BEAN_RECORD.setValue("name", "Circuit"); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "S", 1); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "N", 2); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "S", 3); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "S", 4); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "N", 5); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "S", 6); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "S", 7); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "N", 8); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "N", 9); 
		FLAG_CSV_BEAN_RECORD.setValue("flags", "N", 10); 
		FLAG_CSV_BEAN_RECORD.setValue("program", "CX34ZY7"); 
		
		FLAG_CSV_BEAN_RECORD_STRING = new CSVBeanRecord(FLAG_CSV_BEAN_FOR_STRING, FLAG_CSV_RECORD_AS_STRING);
		FLAG_CSV_BEAN_RECORD_INIT_WITH_STRING = new CSVBeanRecord(FLAG_CSV_BEAN_FOR_STRING);
	}

	@Test
	public void testFlagCSVToString() { 
		Assert.assertTrue(FLAG_CSV_RECORD_AS_STRING.contentEquals(FLAG_CSV_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testFlagCSVToStringConstructor() {
		Assert.assertTrue(FLAG_CSV_RECORD_AS_STRING.contentEquals(FLAG_CSV_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testToStringConstructorGetStringValue() {
		Assert.assertTrue("CX34ZY7".equals(FLAG_CSV_BEAN_RECORD_STRING.getValueAsString("program")));
	}
	
	@Test
	public void testToStringConstructorGetStringNValueWithOccur() {
		Assert.assertTrue("N".equals(FLAG_CSV_BEAN_RECORD_STRING.getValueAsString("flags", 5)));
	}
	
	@Test
	public void testToStringConstructorGetStringSValueWithOccur() {
		Assert.assertTrue("S".equals(FLAG_CSV_BEAN_RECORD_STRING.getValueAsString("flags", 7)));
	}
	
	@Test
	public void testInitRecordToString() {
		FLAG_CSV_BEAN_RECORD_INIT_WITH_STRING.initRecord(FLAG_CSV_RECORD_AS_STRING);
		Assert.assertTrue(FLAG_CSV_RECORD_AS_STRING.equals(FLAG_CSV_BEAN_RECORD_INIT_WITH_STRING.toString()));
	}
}
