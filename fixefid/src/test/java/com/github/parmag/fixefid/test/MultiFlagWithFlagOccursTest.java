package com.github.parmag.fixefid.test;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.BeanRecord;
import com.github.parmag.fixefid.test.bean.MultiFlagWithFlagOccurs;

public class MultiFlagWithFlagOccursTest {

	private static final String MULTI_FLAG_RECORD_AS_STRING = "0000000001234567890Circuit1            SNSSNSSNNNCX34ZY7   Circuit2            SNSSSSNNNNCX34ZY8   ";

	private static final MultiFlagWithFlagOccurs MULTI_FLAG_BEAN = new MultiFlagWithFlagOccurs();
	private static final MultiFlagWithFlagOccurs MULTI_FLAG_BEAN_FOR_STRING = new MultiFlagWithFlagOccurs();
	
	private static final BeanRecord MULTI_FLAG_BEAN_RECORD; 
	private static final BeanRecord MULTI_FLAG_BEAN_RECORD_STRING;
	private static final BeanRecord MULTI_FLAG_BEAN_RECORD_INIT_WITH_STRING;
	
	static {
		MULTI_FLAG_BEAN_RECORD = new BeanRecord(MULTI_FLAG_BEAN);
		MULTI_FLAG_BEAN_RECORD.setValue("id", 1234567890L);
		MULTI_FLAG_BEAN_RECORD.setValue("flags.name", "Circuit1", 1, 1); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 1, 1); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 1, 2); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 1, 3); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 1, 4); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 1, 5); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 1, 6); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 1, 7); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 1, 8); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 1, 9); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 1, 10); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.program", "CX34ZY7", 1, 1);
		MULTI_FLAG_BEAN_RECORD.setValue("flags.name", "Circuit2", 2, 1); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 2, 1); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 2, 2); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 2, 3); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 2, 4); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 2, 5); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "S", 2, 6); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 2, 7); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 2, 8); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 2, 9); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.flags", "N", 2, 10); 
		MULTI_FLAG_BEAN_RECORD.setValue("flags.program", "CX34ZY8", 2, 1); 
		
		MULTI_FLAG_BEAN_RECORD_STRING = new BeanRecord(MULTI_FLAG_BEAN_FOR_STRING, MULTI_FLAG_RECORD_AS_STRING);
		MULTI_FLAG_BEAN_RECORD_INIT_WITH_STRING = new BeanRecord(MULTI_FLAG_BEAN_FOR_STRING);
	}

	@Test
	public void testFlagToString() { 
		Assert.assertTrue(MULTI_FLAG_RECORD_AS_STRING.contentEquals(MULTI_FLAG_BEAN_RECORD.toString()));
	}
	
	@Test
	public void testFlagToStringConstructor() {
		Assert.assertTrue(MULTI_FLAG_RECORD_AS_STRING.contentEquals(MULTI_FLAG_BEAN_RECORD_STRING.toString()));
	}
	
	@Test
	public void testToStringConstructorGetStringValue() {
		Assert.assertTrue("CX34ZY7".equals(MULTI_FLAG_BEAN_RECORD_STRING.getValueAsString("flags.program", 1, 1)));
		Assert.assertTrue("CX34ZY8".equals(MULTI_FLAG_BEAN_RECORD_STRING.getValueAsString("flags.program", 2, 1)));
	}
	
	@Test
	public void testToStringConstructorGetStringNValueWithOccur() {
		Assert.assertTrue("N".equals(MULTI_FLAG_BEAN_RECORD_STRING.getValueAsString("flags.flags", 1, 5)));
		Assert.assertTrue("S".equals(MULTI_FLAG_BEAN_RECORD_STRING.getValueAsString("flags.flags", 2, 5)));
	}
	
	@Test
	public void testToStringConstructorGetStringSValueWithOccur() {
		Assert.assertTrue("S".equals(MULTI_FLAG_BEAN_RECORD_STRING.getValueAsString("flags.flags", 1, 7)));
		Assert.assertTrue("N".equals(MULTI_FLAG_BEAN_RECORD_STRING.getValueAsString("flags.flags", 2, 7)));
	}
	
	@Test
	public void testInitRecordToString() {
		MULTI_FLAG_BEAN_RECORD_INIT_WITH_STRING.initRecord(MULTI_FLAG_RECORD_AS_STRING);
		Assert.assertTrue(MULTI_FLAG_RECORD_AS_STRING.equals(MULTI_FLAG_BEAN_RECORD_INIT_WITH_STRING.toString()));
	}
}
