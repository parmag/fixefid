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
	
	static {
		FLAG_BEAN_RECORD = new BeanRecord(FLAG_BEAN);
		FLAG_BEAN_RECORD.setValue("name", "Circuit"); 
		FLAG_BEAN_RECORD.setValue("flags", 1, "S"); 
		FLAG_BEAN_RECORD.setValue("flags", 2, "N"); 
		FLAG_BEAN_RECORD.setValue("flags", 3, "S"); 
		FLAG_BEAN_RECORD.setValue("flags", 4, "S"); 
		FLAG_BEAN_RECORD.setValue("flags", 5, "N"); 
		FLAG_BEAN_RECORD.setValue("flags", 6, "S"); 
		FLAG_BEAN_RECORD.setValue("flags", 7, "S"); 
		FLAG_BEAN_RECORD.setValue("flags", 8, "N"); 
		FLAG_BEAN_RECORD.setValue("flags", 9, "N"); 
		FLAG_BEAN_RECORD.setValue("flags", 10, "N"); 
		FLAG_BEAN_RECORD.setValue("program", "CX34ZY7"); 
		
		FLAG_BEAN_RECORD_STRING = new BeanRecord(FLAG_BEAN_FOR_STRING, FLAG_RECORD_AS_STRING);
	}

	@Test
	public void testFlagToString() { 
		Assert.assertTrue(FLAG_RECORD_AS_STRING.contentEquals(FLAG_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testFlagToStringConstructor() {
		Assert.assertTrue(FLAG_RECORD_AS_STRING.contentEquals(FLAG_BEAN_RECORD_STRING.toString()));
	}
}