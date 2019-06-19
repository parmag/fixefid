package com.github.parmag.fixefid.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.Record;
import com.github.parmag.fixefid.record.RecordWay;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.test.record.LPadPersonRecordField;

public class LPadPersonRecordFieldTest {

	private static final String LPAD_RECORD_AS_STRING = "               Paolo               Rossi                  18";
	private static final Record<LPadPersonRecordField> LPAD_RECORD = new Record<LPadPersonRecordField>(RecordWay.IN, null, LPadPersonRecordField.class, 
		null, Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " ")));
	
	static {
		LPAD_RECORD.setValue(LPadPersonRecordField.firstName, "Paolo");
		LPAD_RECORD.setValue(LPadPersonRecordField.lastName, "Rossi");
		LPAD_RECORD.setValue(LPadPersonRecordField.age, "18");
	}
	
	@Test
	public void testToString() {
		Assert.assertTrue(LPAD_RECORD_AS_STRING.equals(LPAD_RECORD.toString()));
	}
}
