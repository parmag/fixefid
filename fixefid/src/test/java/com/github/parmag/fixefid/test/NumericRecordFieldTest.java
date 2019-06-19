package com.github.parmag.fixefid.test;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.Record;
import com.github.parmag.fixefid.test.record.NumericRecordField;

public class NumericRecordFieldTest {
	private static final Record<NumericRecordField> NUMERIC_RECORD = new Record<NumericRecordField>(NumericRecordField.class);
	
	@Test
	public void testIntNumericWrongLenToString() {
		boolean error = false;
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, 999999999);
		try {
			NUMERIC_RECORD.toString();
		} catch (Exception e) {
			error = true;
		}
		Assert.assertTrue(error);
	}
	
	@Test
	public void testIntNumericWrongLenIsError() {
		NUMERIC_RECORD.resetStatus();
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, 999999999);
		Assert.assertTrue(NUMERIC_RECORD.isErrorStatus());
	}
	
	@Test
	public void testIntNumericWrongLenGetValue() {
		boolean error = false;
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, 999999999);
		try {
			NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumeric); 
		} catch (Exception e) {
			error = true;
		}
		Assert.assertTrue(error);
	}
	
	@Test
	public void testIntNumericNoValidStringValue() {
		boolean error = false;
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, "0123a");
		try {
			NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumeric); 
		} catch (Exception e) {
			error = true;
		}
		Assert.assertTrue(error);
	}
	
	@Test
	public void testIntNumericMandatory() {
		boolean error = false;
		try {
			NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumeric); 
		} catch (Exception e) {
			error = true;
		}
		Assert.assertTrue(error);
	}
	
	@Test
	public void testIntNumericNoMandatory() {
		Assert.assertTrue(null == NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumericNoMandatory));
	}
	
	@Test
	public void testIntNumericDefault() {
		Assert.assertTrue(11111 == NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumericDefault));
	}
	
	@Test
	public void testIntNumericValidator() {
		NUMERIC_RECORD.resetStatus();
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericValidator, 123);
		Assert.assertTrue(!NUMERIC_RECORD.isErrorStatus());
	}
	
	@Test
	public void testIntNumericValidatorNegative() {
		NUMERIC_RECORD.resetStatus();
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericValidator, -123);
		Assert.assertTrue(NUMERIC_RECORD.isErrorStatus());
	}
	
	@Test
	public void testIntNumeric() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, 123);
		Assert.assertTrue(123 == NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumeric));
	}
	
	@Test
	public void testIntNumericsAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, 123);
		Assert.assertTrue("00123".equals(NUMERIC_RECORD.getValue(NumericRecordField.intNumeric)));
	}
	
	@Test
	public void testIntNumericsStringAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, "00123");
		Assert.assertTrue("00123".equals(NUMERIC_RECORD.getValue(NumericRecordField.intNumeric)));
	}
	
	@Test
	public void testIntNumericsStringAsInt() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, "00123");
		Assert.assertTrue(123 == NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumeric));
	}
	
	@Test
	public void testNegativeIntNumeric() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, -123);
		Assert.assertTrue(-123 == NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumeric));
	}
	
	@Test
	public void testNegativeIntNumericsAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, -123);
		Assert.assertTrue("0-123".equals(NUMERIC_RECORD.getValue(NumericRecordField.intNumeric)));
	}
	
	@Test
	public void testNegativeIntNumericsStringAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, "0-123");
		Assert.assertTrue("0-123".equals(NUMERIC_RECORD.getValue(NumericRecordField.intNumeric)));
	}
	
	@Test
	public void testNegativeIntNumericsStringAsInt() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumeric, "0-123");
		Assert.assertTrue(-123 == NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumeric));
	}
	
	@Test
	public void testLongNumericDefault() {
		Assert.assertTrue(111111111111111L == NUMERIC_RECORD.getValueAsLong(NumericRecordField.longNumericDefault));
	}
	
	@Test
	public void testLongNumeric() {
		NUMERIC_RECORD.setValue(NumericRecordField.longNumeric, 123456789999L);
		Assert.assertTrue(123456789999L == NUMERIC_RECORD.getValueAsLong(NumericRecordField.longNumeric));
	}
	
	@Test
	public void testLongNumericsAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.longNumeric, 123456789999L);
		Assert.assertTrue("000123456789999".equals(NUMERIC_RECORD.getValue(NumericRecordField.longNumeric)));
	}
	
	@Test
	public void testLongNumericsStringAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.longNumeric, "000123456789999");
		Assert.assertTrue("000123456789999".equals(NUMERIC_RECORD.getValue(NumericRecordField.longNumeric)));
	}
	
	@Test
	public void testLongNumericsStringAsLong() {
		NUMERIC_RECORD.setValue(NumericRecordField.longNumeric, "000123456789999");
		Assert.assertTrue(123456789999L == NUMERIC_RECORD.getValueAsLong(NumericRecordField.longNumeric));
	}
	
	@Test
	public void testNegativeLongNumeric() {
		NUMERIC_RECORD.setValue(NumericRecordField.longNumeric, -123456789999L);
		Assert.assertTrue(-123456789999L == NUMERIC_RECORD.getValueAsLong(NumericRecordField.longNumeric));
	}
	
	@Test
	public void testNegativeLongNumericsAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.longNumeric, -123456789999L);
		Assert.assertTrue("00-123456789999".equals(NUMERIC_RECORD.getValue(NumericRecordField.longNumeric)));
	}
	
	@Test
	public void testNegativeLongNumericsStringAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.longNumeric, "00-123456789999");
		Assert.assertTrue("00-123456789999".equals(NUMERIC_RECORD.getValue(NumericRecordField.longNumeric)));
	}
	
	@Test
	public void testNegativeLongNumericsStringAsInt() {
		NUMERIC_RECORD.setValue(NumericRecordField.longNumeric, "00-123456789999");
		Assert.assertTrue(-123456789999L == NUMERIC_RECORD.getValueAsLong(NumericRecordField.longNumeric));
	}
	
	@Test
	public void testIntNumericSpaceAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericNoMandatory, "     ");
		Assert.assertTrue("     ".equals(NUMERIC_RECORD.getValue(NumericRecordField.intNumericNoMandatory)));
	}
	
	@Test
	public void testIntNumericSpaceAsInt() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericNoMandatory, "     ");
		Assert.assertTrue(null == NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumericNoMandatory));
	}
	
	@Test
	public void testIntNumericWithLPADSPaceAsInt() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericWithLPADSpace, 12);
		Assert.assertTrue(12 == NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumericWithLPADSpace));
	}
	
	@Test
	public void testIntNumericWithLPADSPaceAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericWithLPADSpace, 12);
		Assert.assertTrue("   12".equals(NUMERIC_RECORD.getValue(NumericRecordField.intNumericWithLPADSpace)));
	}
	
	@Test
	public void testIntNumericWithLPADSPaceStringAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericWithLPADSpace, "   12");
		Assert.assertTrue("   12".equals(NUMERIC_RECORD.getValue(NumericRecordField.intNumericWithLPADSpace)));
	}
	
	@Test
	public void testIntNumericWithRPADSPaceAsInt() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericWithRPADSpace, 12);
		Assert.assertTrue(12 == NUMERIC_RECORD.getValueAsInteger(NumericRecordField.intNumericWithRPADSpace));
	}
	
	@Test
	public void testIntNumericWithRPADSPaceAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericWithRPADSpace, 12);
		Assert.assertTrue("12   ".equals(NUMERIC_RECORD.getValue(NumericRecordField.intNumericWithRPADSpace)));
	}
	
	@Test
	public void testIntNumericWithLRADSPaceStringAsString() {
		NUMERIC_RECORD.setValue(NumericRecordField.intNumericWithRPADSpace, "12   ");
		Assert.assertTrue("12   ".equals(NUMERIC_RECORD.getValue(NumericRecordField.intNumericWithRPADSpace)));
	}
}
