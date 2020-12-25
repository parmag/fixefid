package com.github.parmag.fixefid.test;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.BeanRecord;
import com.github.parmag.fixefid.test.bean.Flag;

public class FlagTest {

	private static final String FLAG_RECORD_AS_STRING = "Circuit             SNSSNSSNNNCX34ZY7   ";
	
	private static final Flag FLAG_BEAN = new Flag();
	private static final Flag FLAG_BEAN_FOR_STRING = new Flag();
	
	private static final BeanRecord FLAG_BEAN_RECORD; 
	private static final BeanRecord FLAG_BEAN_RECORD_STRING;
	private static final BeanRecord FLAG_BEAN_RECORD_INIT_WITH_STRING;
	
	static {
		FLAG_BEAN_RECORD = new BeanRecord(FLAG_BEAN);
		FLAG_BEAN_RECORD.setValue("name", "Circuit"); 
		FLAG_BEAN_RECORD.setValue("flags", "S", 1); 
		FLAG_BEAN_RECORD.setValue("flags", "N", 2); 
		FLAG_BEAN_RECORD.setValue("flags", "S", 3); 
		FLAG_BEAN_RECORD.setValue("flags", "S", 4); 
		FLAG_BEAN_RECORD.setValue("flags", "N", 5); 
		FLAG_BEAN_RECORD.setValue("flags", "S", 6); 
		FLAG_BEAN_RECORD.setValue("flags", "S", 7); 
		FLAG_BEAN_RECORD.setValue("flags", "N", 8); 
		FLAG_BEAN_RECORD.setValue("flags", "N", 9); 
		FLAG_BEAN_RECORD.setValue("flags", "N", 10); 
		FLAG_BEAN_RECORD.setValue("program", "CX34ZY7"); 
		
		FLAG_BEAN_RECORD_STRING = new BeanRecord(FLAG_BEAN_FOR_STRING, FLAG_RECORD_AS_STRING);
		FLAG_BEAN_RECORD_INIT_WITH_STRING = new BeanRecord(FLAG_BEAN_FOR_STRING);
	}

	@Test
	public void testFlagToString() { 
		Assert.assertTrue(FLAG_RECORD_AS_STRING.contentEquals(FLAG_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testFlagToStringConstructor() {
		Assert.assertTrue(FLAG_RECORD_AS_STRING.contentEquals(FLAG_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testToStringConstructorGetStringValue() {
		Assert.assertTrue("CX34ZY7".equals(FLAG_BEAN_RECORD_STRING.getValueAsString("program")));
	}
	
	@Test
	public void testToStringConstructorGetStringNValueWithOccur() {
		Assert.assertTrue("N".equals(FLAG_BEAN_RECORD_STRING.getValueAsString("flags", 5)));
	}
	
	@Test
	public void testToStringConstructorGetStringSValueWithOccur() {
		Assert.assertTrue("S".equals(FLAG_BEAN_RECORD_STRING.getValueAsString("flags", 7)));
	}
	
	@Test
	public void testInitRecordToString() {
		FLAG_BEAN_RECORD_INIT_WITH_STRING.initRecord(FLAG_RECORD_AS_STRING);
		Assert.assertTrue(FLAG_RECORD_AS_STRING.equals(FLAG_BEAN_RECORD_INIT_WITH_STRING.toString()));
	}
}
