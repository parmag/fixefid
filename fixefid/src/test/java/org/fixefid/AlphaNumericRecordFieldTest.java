package org.fixefid;

import java.util.Calendar;

import org.fixefid.record.AlphaNumericRecordField;
import org.fixefid.record.Record;
import org.fixefid.record.RecordWay;
import org.junit.Assert;
import org.junit.Test;

public class AlphaNumericRecordFieldTest {
	private static final Calendar CAL = Calendar.getInstance();
	
	static {
		CAL.set(Calendar.DAY_OF_MONTH, 7);
		CAL.set(Calendar.MONTH, 9);
		CAL.set(Calendar.YEAR, 2002);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
	}

	private static final Record<AlphaNumericRecordField> ALPHA_NUMERIC_RECORD = new Record<AlphaNumericRecordField>(AlphaNumericRecordField.class);

	@Test
	public void testStringAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.str, "Lorem ipsum");
		Assert.assertTrue("Lorem ipsum         ".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.str)));
	}
	
	@Test
	public void testStringAsStringValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.str, "Lorem ipsum");
		Assert.assertTrue("Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.str)));
	}
	
	@Test
	public void testStringWrongLenErrorStatus() {
		ALPHA_NUMERIC_RECORD.resetStatus();
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.str, "Lorem ipsum XXXXXXXXXXXXXXXXXXXXXXXX");
		Assert.assertTrue(ALPHA_NUMERIC_RECORD.isErrorStatus());
	}
	
	@Test
	public void testStringWrongLenException() {
		boolean error = false;
		ALPHA_NUMERIC_RECORD.resetStatus();
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.str, "Lorem ipsum XXXXXXXXXXXXXXXXXXXXXXXX");
		try {
			ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.str);
		} catch (Exception e) {
			error = true;
		}
		
		Assert.assertTrue(error);
	}
	
	@Test
	public void testStringNumberException() {
		boolean error = false;
		ALPHA_NUMERIC_RECORD.resetStatus();
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.str, "Lorem ipsum");
		try {
			ALPHA_NUMERIC_RECORD.getValueAsInteger(AlphaNumericRecordField.str);
		} catch (Exception e) {
			error = true;
		}
		
		Assert.assertTrue(error);
	}
	
	@Test
	public void testStringDefaultAsStringValue() {
		Assert.assertTrue("default".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strDefaultValue)));
	}
	
	@Test
	public void testStringMandatoryAsStringValue() {
		boolean error = false;
		ALPHA_NUMERIC_RECORD.resetStatus();
		try {
			ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strMandatory);
		} catch (Exception e) {
			error = true;
		}
		
		Assert.assertTrue(error);
	}
	
	@Test
	public void testStringMandatoryAsString() {
		boolean error = false;
		ALPHA_NUMERIC_RECORD.resetStatus();
		try {
			ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strMandatory);
		} catch (Exception e) {
			error = true;
		}
		
		Assert.assertTrue(error);
	}
	
	@Test
	public void testStringNoMandatoryAsStringValue() {
		Assert.assertTrue("".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strNoMandatory)));
	}
	
	@Test
	public void testStringNoMandatoryAsString() {
		Assert.assertTrue("                    ".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strNoMandatory)));
	}
	
	@Test
	public void testStringMandatoryInAsStringValue() {
		boolean error = false;
		ALPHA_NUMERIC_RECORD.resetStatus();
		try {
			ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strMandatoryIn);
		} catch (Exception e) {
			error = true;
		}
		
		Assert.assertTrue(error);
	}
	
	@Test
	public void testStringMandatoryInAsString() {
		boolean error = false;
		ALPHA_NUMERIC_RECORD.resetStatus();
		try {
			ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strMandatoryIn);
		} catch (Exception e) {
			error = true;
		}
		
		Assert.assertTrue(error);
	}
	
	@Test
	public void testStringMandatoryOutRecordInAsStringValue() {
		Assert.assertTrue("".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strMandatoryOut)));
	}
	
	@Test
	public void testStringOutRecordInMandatoryAsString() {
		Assert.assertTrue("                    ".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strMandatoryOut)));
	}
	
	@Test
	public void testStringMandatoryOutRecordOutAsStringValue() {
		boolean error = false;
		ALPHA_NUMERIC_RECORD.resetStatus();
		ALPHA_NUMERIC_RECORD.setRecordWay(RecordWay.OUT);
		try {
			ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strMandatoryOut);
		} catch (Exception e) {
			error = true;
		}
		
		Assert.assertTrue(error);
	}
	
	@Test
	public void testStringMandatoryOutRecordOutAsString() {
		boolean error = false;
		ALPHA_NUMERIC_RECORD.resetStatus();
		ALPHA_NUMERIC_RECORD.setRecordWay(RecordWay.OUT);
		try {
			ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strMandatoryOut);
		} catch (Exception e) {
			error = true;
		}
		
		Assert.assertTrue(error);
	}
	
	@Test
	public void testNormalizeAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.str, "àx@°§12");
		ALPHA_NUMERIC_RECORD.toNormalize(AlphaNumericRecordField.str);
		Assert.assertTrue("AX@??12             ".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.str)));
	}
	
	@Test
	public void testNormalizeAsStringValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.str, "àx@°§12");
		ALPHA_NUMERIC_RECORD.toNormalize(AlphaNumericRecordField.str);
		Assert.assertTrue("AX@??12".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.str)));
	}
	
	@Test
	public void testDateAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strDate, CAL.getTime());
		Assert.assertTrue("07102002".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strDate)));
	}
	
	@Test
	public void testDateAsDateValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strDate, CAL.getTime());
		Assert.assertTrue(CAL.getTime().equals(ALPHA_NUMERIC_RECORD.getValueAsDate(AlphaNumericRecordField.strDate)));
	}
	
	@Test
	public void testDateNotMandatory() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strDateNotMandatory, "        ");
		Assert.assertTrue(null == ALPHA_NUMERIC_RECORD.getValueAsDate(AlphaNumericRecordField.strDateNotMandatory));
	}
	
	@Test
	public void testBooleanTrueAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strBoolean, true);
		Assert.assertTrue("Y".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strBoolean)));
	}
	
	@Test
	public void testBooleanMandatory() {
		boolean error = false;
		ALPHA_NUMERIC_RECORD.resetStatus();
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strBoolean, " ");
		try {
			ALPHA_NUMERIC_RECORD.getValueAsBoolean(AlphaNumericRecordField.strBoolean);
		} catch (Exception e) {
			error = true;
		}
		
		Assert.assertTrue(error);
	}
	
	@Test
	public void testBooleanNotMandatory() {
		Assert.assertTrue(!ALPHA_NUMERIC_RECORD.getValueAsBoolean(AlphaNumericRecordField.strBooleanNotMandatory));
	}
	
	@Test
	public void testBooleanTrueAsBooleanValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strBoolean, true);
		Assert.assertTrue(ALPHA_NUMERIC_RECORD.getValueAsBoolean(AlphaNumericRecordField.strBoolean));
	}
	
	@Test
	public void testBooleanFalseAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strBoolean, false);
		Assert.assertTrue("N".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strBoolean)));
	}
	
	@Test
	public void testBooleanFalseAsBooleanValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strBoolean, false);
		Assert.assertTrue(!ALPHA_NUMERIC_RECORD.getValueAsBoolean(AlphaNumericRecordField.strBoolean));
	}
	
	@Test
	public void testRPADStringAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strRPAD, "Lorem ipsum");
		Assert.assertTrue("Lorem ipsum         ".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strRPAD)));
	}
	
	@Test
	public void testRPADStringAsValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strRPAD, "Lorem ipsum");
		Assert.assertTrue("Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strRPAD)));
		
	}
	
	@Test
	public void testRPADStringPadAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strRPAD, "Lorem ipsum         ");
		Assert.assertTrue("Lorem ipsum         ".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strRPAD)));
	}
	
	@Test
	public void testRPADStringPadAsValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strRPAD, "Lorem ipsum         ");
		Assert.assertTrue("Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strRPAD)));
		
	}
	
	@Test
	public void testRPADWithXPadStrStringAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strRPADWithXPAD, "Lorem ipsum");
		Assert.assertTrue("Lorem ipsumXXXXXXXXX".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strRPADWithXPAD)));
	}
	
	@Test
	public void testRPADWithXPadStrStringAsValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strRPADWithXPAD, "Lorem ipsum");
		Assert.assertTrue("Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strRPADWithXPAD)));
	}
	
	@Test
	public void testRPADWithXPadStrStringPadAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strRPADWithXPAD, "Lorem ipsumXXXXXXXXX");
		Assert.assertTrue("Lorem ipsumXXXXXXXXX".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strRPADWithXPAD)));
	}
	
	@Test
	public void testRPADWithXPadStrStringPadAsValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strRPADWithXPAD, "Lorem ipsumXXXXXXXXX");
		Assert.assertTrue("Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strRPADWithXPAD)));
	}
	
	@Test
	public void testLPADStringAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strLPAD, "Lorem ipsum"); 
		Assert.assertTrue("         Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strLPAD)));
	}
	
	@Test
	public void testLPADStringAsValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strLPAD, "Lorem ipsum");
		Assert.assertTrue("Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strLPAD)));
		
	}
	
	@Test
	public void testLPADStringPadAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strLPAD, "         Lorem ipsum"); 
		Assert.assertTrue("         Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strLPAD)));
	}
	
	@Test
	public void testLPADStringPadAsValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strLPAD, "         Lorem ipsum");
		Assert.assertTrue("Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strLPAD)));
		
	}
	
	@Test
	public void testLPADWithXPadStrStringAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strLPADWithXPAD, "Lorem ipsum");
		Assert.assertTrue("XXXXXXXXXLorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strLPADWithXPAD)));
	}
	
	@Test
	public void testLPADWithXPadStrStringAsValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strLPADWithXPAD, "Lorem ipsum");
		Assert.assertTrue("Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strLPADWithXPAD)));
	}
	
	@Test
	public void testLPADWithXPadStrStringPadAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strLPADWithXPAD, "XXXXXXXXXLorem ipsum");
		Assert.assertTrue("XXXXXXXXXLorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strLPADWithXPAD)));
	}
	
	@Test
	public void testLPADWithXPadStrStringPadAsValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strLPADWithXPAD, "XXXXXXXXXLorem ipsum");
		Assert.assertTrue("Lorem ipsum".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strLPADWithXPAD)));
	}
	
	@Test
	public void testDateLPADWithXPadStringAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strDateLPADWithXPAD, CAL.getTime());
		Assert.assertTrue("XXXXXXXXXXXX07102002".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strDateLPADWithXPAD)));
	}
	
	@Test
	public void testDateLPADWithXPadAsDateValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strDateLPADWithXPAD, CAL.getTime());
		Assert.assertTrue(CAL.getTime().equals(ALPHA_NUMERIC_RECORD.getValueAsDate(AlphaNumericRecordField.strDateLPADWithXPAD)));
	}
	
	@Test
	public void testDateLPADWithXPadStringPadAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strDateLPADWithXPAD, "XXXXXXXXXXXX07102002");
		Assert.assertTrue("XXXXXXXXXXXX07102002".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strDateLPADWithXPAD)));
	}
	
	@Test
	public void testDateLPADWithXPadStringPadAsDateValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strDateLPADWithXPAD, "XXXXXXXXXXXX07102002");
		Assert.assertTrue(CAL.getTime().equals(ALPHA_NUMERIC_RECORD.getValueAsDate(AlphaNumericRecordField.strDateLPADWithXPAD)));
	}
	
	@Test
	public void testBooleanTrueLPADWithXPadAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strBooleanLPADWithXPAD, true);
		Assert.assertTrue("XXXXXXXXXXXXXXXXXXXY".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strBooleanLPADWithXPAD)));
	}
	
	@Test
	public void testBooleanTrueLPADWithXPadAsBooleanValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strBooleanLPADWithXPAD, true);
		Assert.assertTrue(ALPHA_NUMERIC_RECORD.getValueAsBoolean(AlphaNumericRecordField.strBooleanLPADWithXPAD));
	}
	
	@Test
	public void testBooleanTrueLPADWithXPadStringPadAsString() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strBooleanLPADWithXPAD, "XXXXXXXXXXXXXXXXXXXY");
		Assert.assertTrue("XXXXXXXXXXXXXXXXXXXY".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strBooleanLPADWithXPAD)));
	}
	
	@Test
	public void testBooleanTrueLPADWithXPadStringPadAsBooleanValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strBooleanLPADWithXPAD, "XXXXXXXXXXXXXXXXXXXY");
		Assert.assertTrue(ALPHA_NUMERIC_RECORD.getValueAsBoolean(AlphaNumericRecordField.strBooleanLPADWithXPAD));
	}
	
	@Test
	public void testCustomFormatAsValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strCustom, "ABCDEFGHIJ");
		Assert.assertTrue("ABCDEFGHIJ".equals(ALPHA_NUMERIC_RECORD.getValueAsString(AlphaNumericRecordField.strCustom)));
	}
	
	@Test
	public void testCustomFormatAsStringValue() {
		ALPHA_NUMERIC_RECORD.setValue(AlphaNumericRecordField.strCustom, "ABCDEFGHIJ");
		Assert.assertTrue("JIHGFEDCBA".equals(ALPHA_NUMERIC_RECORD.getValue(AlphaNumericRecordField.strCustom)));
	}
}
