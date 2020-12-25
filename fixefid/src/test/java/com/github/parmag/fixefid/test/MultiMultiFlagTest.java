package com.github.parmag.fixefid.test;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.BeanRecord;
import com.github.parmag.fixefid.test.bean.MultiMultiFlag;

public class MultiMultiFlagTest {

	private static final String MULTI_MULTI_FLAG_RECORD_AS_STRING = "MULTIMULTIFLAG      0000000001234567890Circuit             SNSSNSSNNNCX34ZY7   ";

	private static final MultiMultiFlag MULTI_MULTI_FLAG_BEAN = new MultiMultiFlag();
	private static final MultiMultiFlag MULTI_MULTI_FLAG_BEAN_FOR_STRING = new MultiMultiFlag();
	
	private static final BeanRecord MULTI_MULTI_FLAG_BEAN_RECORD; 
	private static final BeanRecord MULTI_MULTI_FLAG_BEAN_RECORD_STRING;
	private static final BeanRecord MULTI_MULTI_FLAG_BEAN_RECORD_INIT_WITH_STRING;
	
	static {
		MULTI_MULTI_FLAG_BEAN_RECORD = new BeanRecord(MULTI_MULTI_FLAG_BEAN);
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiMultiName", "MULTIMULTIFLAG");
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.id", 1234567890L);
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.name", "Circuit"); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "S", 1, 1, 1); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "N", 1, 1, 2); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "S", 1, 1, 3); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "S", 1, 1, 4); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "N", 1, 1, 5); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "S", 1, 1, 6); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "S", 1, 1, 7); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "N", 1, 1, 8); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "N", 1, 1, 9); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.flags", "N", 1, 1, 10); 
		MULTI_MULTI_FLAG_BEAN_RECORD.setValue("multiFlag.flag.program", "CX34ZY7"); 
		
		MULTI_MULTI_FLAG_BEAN_RECORD_STRING = new BeanRecord(MULTI_MULTI_FLAG_BEAN_FOR_STRING, MULTI_MULTI_FLAG_RECORD_AS_STRING);
		MULTI_MULTI_FLAG_BEAN_RECORD_INIT_WITH_STRING = new BeanRecord(MULTI_MULTI_FLAG_BEAN_FOR_STRING);
	}

	@Test
	public void testFlagToString() { 
		Assert.assertTrue(MULTI_MULTI_FLAG_RECORD_AS_STRING.contentEquals(MULTI_MULTI_FLAG_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testFlagToStringConstructor() {
		Assert.assertTrue(MULTI_MULTI_FLAG_RECORD_AS_STRING.contentEquals(MULTI_MULTI_FLAG_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testToStringConstructorGetStringValue() {
		Assert.assertTrue("CX34ZY7".equals(MULTI_MULTI_FLAG_BEAN_RECORD_STRING.getValueAsString("multiFlag.flag.program")));
	}
	
	@Test
	public void testToStringConstructorGetStringNValueWithOccur() {
		Assert.assertTrue("N".equals(MULTI_MULTI_FLAG_BEAN_RECORD_STRING.getValueAsString("multiFlag.flag.flags", 1, 1, 5)));
	}
	
	@Test
	public void testToStringConstructorGetStringSValueWithOccur() {
		Assert.assertTrue("S".equals(MULTI_MULTI_FLAG_BEAN_RECORD_STRING.getValueAsString("multiFlag.flag.flags", 1, 1, 7)));
	}
	
	@Test
	public void testInitRecordToString() {
		MULTI_MULTI_FLAG_BEAN_RECORD_INIT_WITH_STRING.initRecord(MULTI_MULTI_FLAG_RECORD_AS_STRING);
		Assert.assertTrue(MULTI_MULTI_FLAG_RECORD_AS_STRING.equals(MULTI_MULTI_FLAG_BEAN_RECORD_INIT_WITH_STRING.toString()));
	}
}
