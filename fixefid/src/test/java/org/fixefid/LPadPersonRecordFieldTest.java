package org.fixefid;

import java.util.Arrays;

import org.fixefid.record.LPadPersonRecordField;
import org.fixefid.record.Record;
import org.fixefid.record.RecordWay;
import org.fixefid.record.field.FieldExtendedProperty;
import org.fixefid.record.field.FieldExtendedPropertyType;
import org.junit.Assert;
import org.junit.Test;

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
