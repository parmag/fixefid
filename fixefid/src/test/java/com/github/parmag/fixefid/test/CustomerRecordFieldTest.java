package com.github.parmag.fixefid.test;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.Record;
import com.github.parmag.fixefid.test.record.CustomerRecordField;

public class CustomerRecordFieldTest {
	private static final String CUSTOMER_RECORD_AS_STRING = "0000000000000000001paul.robinson@serverxyz.com                       ";
	private static final Record<CustomerRecordField> CUSTOMER_RECORD = new Record<CustomerRecordField>(CustomerRecordField.class);
	
	static {
		CUSTOMER_RECORD.setValue(CustomerRecordField.id, "1");
		CUSTOMER_RECORD.setValue(CustomerRecordField.email, "paul.robinson@serverxyz.com");
	}
	
	@Test
	public void testToString() {
		Assert.assertTrue(CUSTOMER_RECORD_AS_STRING.equals(CUSTOMER_RECORD.toString()));
	}
	
	@Test
	public void testFieldPrettyPrint() {
		Assert.assertTrue("email=[2][1][1][20][50][paul.robinson@serverxyz.com                       ][INFO][]".equals(
				CUSTOMER_RECORD.prettyPrint(CustomerRecordField.email.name()))); 
	}
}
