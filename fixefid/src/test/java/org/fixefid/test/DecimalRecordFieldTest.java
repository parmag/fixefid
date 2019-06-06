package org.fixefid.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.fixefid.record.Record;
import org.fixefid.record.field.FieldValidationInfo;
import org.fixefid.test.record.DecimalRecordField;
import org.junit.Assert;
import org.junit.Test;

public class DecimalRecordFieldTest {
	private static final Record<DecimalRecordField> DECIMAL_RECORD = new Record<DecimalRecordField>(DecimalRecordField.class);
	
	@Test
	public void testNoDecimalsAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.nodecimals, 123.0);
		Assert.assertTrue("0000000123".equals(DECIMAL_RECORD.getValue(DecimalRecordField.nodecimals)));
	}
	
	@Test
	public void testNegativeNoDecimalsAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.nodecimals, -123.0);
		Assert.assertTrue("000000-123".equals(DECIMAL_RECORD.getValue(DecimalRecordField.nodecimals)));
	}
	
	@Test
	public void testNoDecimalsAsInteger() {
		DECIMAL_RECORD.setValue(DecimalRecordField.nodecimals, 123.0);
		Assert.assertTrue(123 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.nodecimals).intValue());
	}
	
	@Test
	public void testNegativeNoDecimalsAsInteger() {
		DECIMAL_RECORD.setValue(DecimalRecordField.nodecimals, -123.0);
		Assert.assertTrue(-123 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.nodecimals).intValue());
	}
	
	@Test
	public void testNoDecimalsNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.nodecimals, "000000abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testNoDecimalsValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.nodecimals, "0000000123");
		Assert.assertTrue("0000000123".equals(DECIMAL_RECORD.getValue(DecimalRecordField.nodecimals)));
	}
	
	@Test
	public void testNoDecimalsValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.nodecimals, "0000000123");
		Assert.assertTrue(123 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.nodecimals));
	}
	
	@Test
	public void testNegativeNoDecimalsValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.nodecimals, "000000-123");
		Assert.assertTrue("000000-123".equals(DECIMAL_RECORD.getValue(DecimalRecordField.nodecimals)));
	}
	
	@Test
	public void testNegativeNoDecimalsValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.nodecimals, "000000-123");
		Assert.assertTrue(-123 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.nodecimals));
	}
	
	@Test
	public void testDecimalsAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, 123.45);
		Assert.assertTrue("0000123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimals)));
	}
	
	@Test
	public void testNegativeDecimalsAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, -123.45);
		Assert.assertTrue("000-123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimals)));
	}
	
	@Test
	public void testDecimalsAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, 123.45);
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimals).doubleValue());
	}
	
	@Test
	public void testNegativeDecimalsAsDouble() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, -123.45);
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimals).doubleValue());
	}
	
	@Test
	public void testDecimalsNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, "000000abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, "0000123.45");
		Assert.assertTrue("0000123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimals)));
	}
	
	@Test
	public void testDecimalsValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, "0000123.45");
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimals));
	}
	
	@Test
	public void testNegativeDecimalsValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, "000-123.45");
		Assert.assertTrue("000-123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimals)));
	}
	
	@Test
	public void testNegativeDecimalsValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, "000-123.45");
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimals));
	}
	
	@Test
	public void testDecimalsWithNoSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, 123.45);
		Assert.assertTrue("0000012345".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithNoSep)));
	}
	
	@Test
	public void testNegativeDecimalsWithNoSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, -123.45);
		Assert.assertTrue("0000-12345".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithNoSep)));
	}
	
	@Test
	public void testDecimalsWithNoSepAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, 123.45);
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithNoSep).doubleValue());
	}
	
	@Test
	public void testNegativeDecimalsWithNoSepAsDouble() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, -123.45);
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithNoSep).doubleValue());
	}
	
	@Test
	public void testDecimalsWithNoSepNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, "000000abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsWithNoSepValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, "0000012345");
		Assert.assertTrue("0000012345".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithNoSep)));
	}
	
	@Test
	public void testDecimalsWithNoSepValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, "0000012345");
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithNoSep));
	}
	
	@Test
	public void testNegativeDecimalsWithNoSepValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, "0000-12345");
		Assert.assertTrue("0000-12345".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithNoSep)));
	}
	
	@Test
	public void testNegativeDecimalsWithNoSepValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, "0000-12345");
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithNoSep));
	}
	
	@Test
	public void testDecimalsMinOneWithNoSepAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, 0.45);
		Assert.assertTrue(0.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithNoSep).doubleValue());
	}
	
	@Test
	public void testDecimalsMinOneWithNoSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoSep, 0.45);
		Assert.assertTrue("0000000045".contentEquals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithNoSep)));
	}
	
	@Test
	public void testDecimalsWithGroupingSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithGroupingSep, 1123.45);
		Assert.assertTrue("001,123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithGroupingSep)));
	}
	
	@Test
	public void testNegativeDecimalsWithGroupingSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithGroupingSep, -1123.45);
		Assert.assertTrue("0-1,123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithGroupingSep)));
	}
	
	@Test
	public void testDecimalsWithGroupingSepAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithGroupingSep, 1123.45);
		Assert.assertTrue(1123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithGroupingSep).doubleValue());
	}
	
	@Test
	public void testNegativeDecimalsWithGroupingSepAsDouble() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithGroupingSep, -1123.45);
		Assert.assertTrue(-1123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithGroupingSep).doubleValue());
	}
	
	@Test
	public void testDecimalsWithGroupingNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithGroupingSep, "000000abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsWithGroupingValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithGroupingSep, "001,123.45");
		Assert.assertTrue("001,123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithGroupingSep)));
	}
	
	@Test
	public void testDecimalsWithGroupingValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithGroupingSep, "001,123.45");
		Assert.assertTrue(1123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithGroupingSep));
	}
	
	@Test
	public void testNegativeDecimalsWithGroupingValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithGroupingSep, "0-1,123.45");
		Assert.assertTrue("0-1,123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithGroupingSep)));
	}
	
	@Test
	public void testNegativeDecimalsWithGroupingValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithGroupingSep, "0-1,123.45");
		Assert.assertTrue(-1123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithGroupingSep));
	}
	
	@Test
	public void testDecimalsScientificAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsScientific, 1123.45);
		Assert.assertTrue("01.12345E3".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsScientific)));
	}
	
	@Test
	public void testNegativeDecimalsScientificAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsScientific, -1123.45);
		Assert.assertTrue("-1.12345E3".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsScientific)));
	}
	
	@Test
	public void testDecimalsScientificAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsScientific, 1123.45);
		Assert.assertTrue(1123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsScientific).doubleValue());
	}
	
	@Test
	public void testNegativeDecimalsScientificAsDouble() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsScientific, -1123.45);
		Assert.assertTrue(-1123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsScientific).doubleValue());
	}
	
	@Test
	public void testDecimalsScientificNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsScientific, "000000abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsScientificValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsScientific, "01.12345E3");
		Assert.assertTrue("01.12345E3".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsScientific)));
	}
	
	@Test
	public void testDecimalsScientificValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsScientific, "01.12345E3");
		Assert.assertTrue(1123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsScientific));
	}
	
	@Test
	public void testNegativeDecimalsScientificValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsScientific, "-1.12345E3");
		Assert.assertTrue("-1.12345E3".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsScientific)));
	}
	
	@Test
	public void testNegativeDecimalsScientificValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsScientific, "-1.12345E3");
		Assert.assertTrue(-1123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsScientific));
	}
	
	@Test
	public void testDecimalsPercentageAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsPercentage, 0.12);
		Assert.assertTrue("000000012%".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsPercentage)));
	}
	
	@Test
	public void testNegativeDecimalsPercentageAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsPercentage, -0.12);
		Assert.assertTrue("000000-12%".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsPercentage)));
	}
	
	@Test
	public void testDecimalsPercentageAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsPercentage, 0.12);
		Assert.assertTrue(0.12 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsPercentage).doubleValue());
	}
	
	@Test
	public void testNegativeDecimalsPercentageAsDouble() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsPercentage, -0.12);
		Assert.assertTrue(-0.12 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsPercentage).doubleValue());
	}
	
	@Test
	public void testDecimalsPercentageNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsPercentage, "000000abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsPercentageValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsPercentage, "000000012%");
		Assert.assertTrue("000000012%".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsPercentage)));
	}
	
	@Test
	public void testDecimalsPercentageValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsPercentage, "000000012%");
		Assert.assertTrue(0.12 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsPercentage));
	}
	
	@Test
	public void testNegativeDecimalsPercentageValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsPercentage, "000000-12%");
		Assert.assertTrue("000000-12%".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsPercentage)));
	}
	
	@Test
	public void testNegativeDecimalsPercentageValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsPercentage, "000000-12%");
		Assert.assertTrue(-0.12 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsPercentage));
	}
	
	@Test
	public void testDecimalsCurrencyAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsCurrency, 100.12);
		Assert.assertTrue("000100.12$".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsCurrency)));
	}
	
	@Test
	public void testNegativeDecimalsCurrencyAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsCurrency, -100.12);
		Assert.assertTrue("00-100.12$".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsCurrency)));
	}
	
	@Test
	public void testDecimalsCurrencyAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsCurrency, 100.12);
		Assert.assertTrue(100.12 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsCurrency).doubleValue());
	}
	
	@Test
	public void testNegativeDecimalsCurrencyAsDouble() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsCurrency, -100.12);
		Assert.assertTrue(-100.12 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsCurrency).doubleValue());
	}
	
	@Test
	public void testDecimalsCurrencyNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsCurrency, "000000abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsCurrencyValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsCurrency, "000100.12$");
		Assert.assertTrue("000100.12$".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsCurrency)));
	}
	
	@Test
	public void testDecimalsCurrencyValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsCurrency, "000100.12$");
		Assert.assertTrue(100.12 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsCurrency));
	}
	
	@Test
	public void testNegativeDecimalsCurrencyValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsCurrency, "00-100.12$");
		Assert.assertTrue("00-100.12$".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsCurrency)));
	}
	
	@Test
	public void testNegativeDecimalsCurrencyValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsCurrency, "00-100.12$");
		Assert.assertTrue(-100.12 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsCurrency));
	}
	
	@Test
	public void testDefaultDecimalsAsString() {
		Assert.assertTrue("0000999.99".equals(DECIMAL_RECORD.getValue(DecimalRecordField.defaultDecimals)));
	}
	
	@Test
	public void testDefaultNegativeDecimalsAsString() {
		Assert.assertTrue("000-999.99".equals(DECIMAL_RECORD.getValue(DecimalRecordField.defaultNegativeDecimals)));
	}
	
	@Test
	public void testDefaultDecimalsAsDobule() {
		Assert.assertTrue(999.99 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.defaultDecimals).doubleValue());
	}
	
	@Test
	public void testDefaultNegativeDecimalsAsDouble() {
		Assert.assertTrue(-999.99 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.defaultNegativeDecimals).doubleValue());
	}
	
	@Test
	public void testDecimalsNumericSpaceAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsNumericSpace, "          ");
		Assert.assertTrue("          ".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsNumericSpace)));
	}
	
	@Test
	public void testDecimalsNumericSpaceAsDouble() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsNumericSpace, "          ");
		Assert.assertTrue(null == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsNumericSpace));
	}
	
	@Test
	public void testDecimalsWithNoNegativeValidatorRecordFieldStatusInfo() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoNegativeValidator, 123.45);
		Assert.assertTrue(FieldValidationInfo.RecordFieldValidationStatus.INFO.equals(
			DECIMAL_RECORD.getRecordFieldValidationInfo(DecimalRecordField.decimalsWithNoNegativeValidator).getValidationStatus()));
	}
	
	@Test
	public void testDecimalsWithNoNegativeValidatorRecordStatusInfo() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoNegativeValidator, 123.45);
		Assert.assertTrue(DECIMAL_RECORD.isInfoStatus());
	}
	
	@Test
	public void testDecimalsWithNoNegativeValidatorRecordFieldStatusError() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithNoNegativeValidator, -123.45); 
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testFloatsAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.floats, 1.23f);
		Assert.assertTrue("01.23".equals(DECIMAL_RECORD.getValue(DecimalRecordField.floats)));
	}
	
	@Test
	public void testNegativeFloatsAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.floats, -1.23f);
		Assert.assertTrue("-1.23".equals(DECIMAL_RECORD.getValue(DecimalRecordField.floats)));
	}
	
	@Test
	public void testFloatsAsFloat() {
		DECIMAL_RECORD.setValue(DecimalRecordField.floats, 1.23f);
		Assert.assertTrue(1.23f == DECIMAL_RECORD.getValueAsFloat(DecimalRecordField.floats).floatValue());
	}
	
	@Test
	public void testNegativeFloatsAsFloat() {
		DECIMAL_RECORD.setValue(DecimalRecordField.floats, -1.23f);
		Assert.assertTrue(-1.23f == DECIMAL_RECORD.getValueAsFloat(DecimalRecordField.floats).floatValue());
	}
	
	@Test
	public void testFloatsNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.floats, "0abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testFloatsValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.floats, "01.23");
		Assert.assertTrue("01.23".equals(DECIMAL_RECORD.getValue(DecimalRecordField.floats)));
	}
	
	@Test
	public void testFloatsValidStringAsFloat() {
		DECIMAL_RECORD.setValue(DecimalRecordField.floats, "01.23");
		Assert.assertTrue(1.23f == DECIMAL_RECORD.getValueAsFloat(DecimalRecordField.floats));
	}
	
	@Test
	public void testNegativeFloatsValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.floats, "-1.23");
		Assert.assertTrue("-1.23".equals(DECIMAL_RECORD.getValue(DecimalRecordField.floats)));
	}
	
	@Test
	public void testNegativeFloatsValidStringAsFloat() {
		DECIMAL_RECORD.setValue(DecimalRecordField.floats, "-1.23");
		Assert.assertTrue(-1.23f == DECIMAL_RECORD.getValueAsFloat(DecimalRecordField.floats));
	}
	
	@Test
	public void testDecimalsAsBigDecimal() {
		BigDecimal bd = new BigDecimal(123.45);
		bd = bd.setScale(2, RoundingMode.HALF_DOWN);
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, bd);
		Assert.assertTrue(bd.equals(DECIMAL_RECORD.getValueAsBigDecimal(DecimalRecordField.decimals)));
	}
	
	@Test
	public void testNegativeDecimalsAsBigDecimal() {
		BigDecimal bd = new BigDecimal(-123.45);
		bd = bd.setScale(2, RoundingMode.HALF_DOWN);
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, bd);
		Assert.assertTrue(bd.equals(DECIMAL_RECORD.getValueAsBigDecimal(DecimalRecordField.decimals)));
	}
	
	@Test
	public void testDecimalsBigDecimalAsString() {
		BigDecimal bd = new BigDecimal(123.45);
		bd = bd.setScale(2, RoundingMode.HALF_DOWN);
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, bd);
		Assert.assertTrue("0000123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimals)));
	}
	
	@Test
	public void testNegativeDecimalsBigDecimalAsString() {
		BigDecimal bd = new BigDecimal(-123.45);
		bd = bd.setScale(2, RoundingMode.HALF_DOWN);
		DECIMAL_RECORD.setValue(DecimalRecordField.decimals, bd);
		Assert.assertTrue("000-123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimals)));
	}
	
	@Test
	public void testDecimalsWithLPADSpaceNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpace, "      abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsWithLPADSpaceValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpace, "    123.45");
		Assert.assertTrue("    123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithLPADSpace)));
	}
	
	@Test
	public void testDecimalsWithLPADSpaceValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpace, "    123.45");
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithLPADSpace));
	}
	
	@Test
	public void testNegativeDecimalsWithLPADSpaceValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpace, "   -123.45");
		Assert.assertTrue("   -123.45".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithLPADSpace)));
	}
	
	@Test
	public void testNegativeDecimalsWithLPADSpaceValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpace, "   -123.45");
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithLPADSpace));
	}
	
	@Test
	public void testDecimalsWithLPADSpaceAndNoSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, 123.45);
		Assert.assertTrue("     12345".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep)));
	}
	
	@Test
	public void testNegativeDecimalsWithLPADSpaceAndNoSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, -123.45);
		Assert.assertTrue("    -12345".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep)));
	}
	
	@Test
	public void testDecimalsWithLPADSpaceAndNoSepAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, 123.45);
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithLPADSpaceAndNoSep).doubleValue());
	}
	
	@Test
	public void testNegativeDecimalsWithLPADSpaceAndNoSepAsDouble() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, -123.45);
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithLPADSpaceAndNoSep).doubleValue());
	}
	
	@Test
	public void testDecimalsWithLPADSpaceAndNoSepNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, "      abcd");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsWithLPADSpaceAndNoSepValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, "     12345");
		Assert.assertTrue("     12345".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep)));
	}
	
	@Test
	public void testDecimalsWithLPADSpaceAndNoSepValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, "     12345");
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithLPADSpaceAndNoSep));
	}
	
	@Test
	public void testNegativeDecimalsWithLPADSpaceAndNoSepValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, "    -12345");
		Assert.assertTrue("    -12345".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep)));
	}
	
	@Test
	public void testNegativeDecimalsWithLPADSpaceAndNoSepValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, "    -12345");
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithLPADSpaceAndNoSep));
	}
	
	@Test
	public void testDecimalsMinOneWithLPADSpaceAndNoSepAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, 0.45);
		Assert.assertTrue(0.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithLPADSpaceAndNoSep).doubleValue());
	}
	
	@Test
	public void testDecimalsMinOneWithLPADSpaceAndNoSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep, 0.45);
		Assert.assertTrue("       045".contentEquals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithLPADSpaceAndNoSep)));
	}
	
	@Test
	public void testDecimalsWithRPADSpaceNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpace, "abcd      ");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsWithRPADSpaceValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpace, "123.45    ");
		Assert.assertTrue("123.45    ".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithRPADSpace)));
	}
	
	@Test
	public void testDecimalsWithRPADSpaceValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpace, "123.45    ");
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithRPADSpace));
	}
	
	@Test
	public void testNegativeDecimalsWithRPADSpaceValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpace, "-123.45   ");
		Assert.assertTrue("-123.45   ".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithRPADSpace)));
	}
	
	@Test
	public void testNegativeDecimalsWithRPADSpaceValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpace, "-123.45   ");
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithRPADSpace));
	}
	
	@Test
	public void testDecimalsWithRPADSpaceAndNoSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, 123.45);
		Assert.assertTrue("12345     ".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep)));
	}
	
	@Test
	public void testNegativeDecimalsWithRPADSpaceAndNoSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, -123.45);
		Assert.assertTrue("-12345    ".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep)));
	}
	
	@Test
	public void testDecimalsWithRPADSpaceAndNoSepAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, 123.45);
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithRPADSpaceAndNoSep).doubleValue());
	}
	
	@Test
	public void testNegativeDecimalsWithLRADSpaceAndNoSepAsDouble() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, -123.45);
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithRPADSpaceAndNoSep).doubleValue());
	}
	
	@Test
	public void testDecimalsWithRPADSpaceAndNoSepNoValidStringAsString() {
		DECIMAL_RECORD.resetStatus();
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, "abcd      ");
		Assert.assertTrue(DECIMAL_RECORD.isErrorStatus());
	}
	
	@Test
	public void testDecimalsWithRPADSpaceAndNoSepValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, "12345     ");
		Assert.assertTrue("12345     ".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep)));
	}
	
	@Test
	public void testDecimalsWithRPADSpaceAndNoSepValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, "12345     ");
		Assert.assertTrue(123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithRPADSpaceAndNoSep));
	}
	
	@Test
	public void testNegativeDecimalsWithRPADSpaceAndNoSepValidStringAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, "-12345    ");
		Assert.assertTrue("-12345    ".equals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep)));
	}
	
	@Test
	public void testNegativeDecimalsWithRPADSpaceAndNoSepValidStringAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, "-12345    ");
		Assert.assertTrue(-123.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithRPADSpaceAndNoSep));
	}
	
	@Test
	public void testDecimalsMinOneWithRPADSpaceAndNoSepAsDobule() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, 0.45);
		Assert.assertTrue(0.45 == DECIMAL_RECORD.getValueAsDouble(DecimalRecordField.decimalsWithRPADSpaceAndNoSep).doubleValue());
	}
	
	@Test
	public void testDecimalsMinOneWithRPADSpaceAndNoSepAsString() {
		DECIMAL_RECORD.setValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep, 0.45);
		Assert.assertTrue("045       ".contentEquals(DECIMAL_RECORD.getValue(DecimalRecordField.decimalsWithRPADSpaceAndNoSep)));
	}
}
