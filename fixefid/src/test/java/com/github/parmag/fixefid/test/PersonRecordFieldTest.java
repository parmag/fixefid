package com.github.parmag.fixefid.test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.Record;
import com.github.parmag.fixefid.record.csv.CSVEnc;
import com.github.parmag.fixefid.record.csv.CSVSep;
import com.github.parmag.fixefid.test.record.PersonRecordField;

public class PersonRecordFieldTest {
	private static final Calendar CAL = Calendar.getInstance();
	
	private static final String PERSON_RECORD_AS_STRING = "Paolo                    Rossi                    05107102002186";
	private static final String PERSON_RECORD_AS_CSV_STRING = "Paolo,Rossi,51,07102002,186";
	private static final String PERSON_RECORD_AS_CSV_STRING_1 = "\"Paolo\",\"Rossi\",\"51\",\"07102002\",\"186\"";
	private static final String PERSON_RECORD_AS_CSV_STRING_2 = "\"Pao\"\"lo\",\"Rossi\",\"51\",\"07102002\",\"186\"";
	private static final String PERSON_RECORD_AS_CSV_STRING_3 = "\"Pao\"\"lo\",\"Ros,si\",51,07102002,186";
	private static final String PERSON_RECORD_AS_CSV_STRING_4 = "Paolo;Rossi;51;07102002;186";
	private static final String PERSON_RECORD_AS_CSV_STRING_5 = "Paolo+Rossi+51+07102002+186";
	private static final String PERSON_RECORD_AS_CSV_STRING_6 = "'Paolo','Rossi','51','07102002','186'";
	private static final Record<PersonRecordField> PERSON_RECORD = new Record<PersonRecordField>(PersonRecordField.class);
	private static final Record<PersonRecordField> PERSON_RECORD_1000 = new Record<PersonRecordField>(PersonRecordField.class, 1000);
	private static final Record<PersonRecordField> PERSON_RECORD_STRING = new Record<PersonRecordField>(PERSON_RECORD_AS_STRING, PersonRecordField.class);
	private static final Record<PersonRecordField> PERSON_RECORD_INIT_WITH_STRING = new Record<PersonRecordField>(PersonRecordField.class);
	
	static {
		CAL.set(Calendar.DAY_OF_MONTH, 7);
		CAL.set(Calendar.MONTH, 9);
		CAL.set(Calendar.YEAR, 2002);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
		
		PERSON_RECORD.setValue(PersonRecordField.firstName, "Paolo");
		PERSON_RECORD.setValue(PersonRecordField.lastName, "Rossi");
		PERSON_RECORD.setValue(PersonRecordField.age, 51);
		PERSON_RECORD.setValue(PersonRecordField.birthDate, CAL.getTime()); 
		PERSON_RECORD.setValue(PersonRecordField.stature, 1.86f);
		
		PERSON_RECORD_1000.setValue(PersonRecordField.firstName, "Paolo");
		PERSON_RECORD_1000.setValue(PersonRecordField.lastName, "Rossi");
		PERSON_RECORD_1000.setValue(PersonRecordField.age, 51);
		PERSON_RECORD_1000.setValue(PersonRecordField.birthDate, CAL.getTime()); 
		PERSON_RECORD_1000.setValue(PersonRecordField.stature, 1.86f);
	}
	
	@Test
	public void testToString() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.equals(PERSON_RECORD.toString()));
	}
	
	@Test
	public void testToStringConstructor() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.equals(PERSON_RECORD_STRING.toString()));
	}
	
	@Test
	public void testToStringConstructorGetStringValue() {
		Assert.assertTrue("Paolo".equals(PERSON_RECORD_STRING.getValueAsString(PersonRecordField.firstName)));
	}
	
	@Test
	public void testToStringConstructorGetIntValue() {
		Assert.assertTrue(51 == PERSON_RECORD_STRING.getValueAsInteger(PersonRecordField.age));
	}
	
	@Test
	public void testToStringConstructorGetDateValue() { 
		Assert.assertTrue(CAL.getTime().equals(PERSON_RECORD_STRING.getValueAsDate(PersonRecordField.birthDate)));
	}
	
	@Test
	public void testToStringConstructorGetFloatValue() {
		Assert.assertTrue(1.86f == PERSON_RECORD_STRING.getValueAsFloat(PersonRecordField.stature));
	}
	
	@Test
	public void testLen() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.length() == PERSON_RECORD.getRecordLen());
	}
	
	@Test
	public void testLenConstructor() {
		Assert.assertTrue(1000 == PERSON_RECORD_1000.toString().length());
	}
	
	@Test
	public void testInitRecordToString() {
		PERSON_RECORD_INIT_WITH_STRING.initRecord(PERSON_RECORD_AS_STRING);
		Assert.assertTrue(PERSON_RECORD_AS_STRING.equals(PERSON_RECORD_INIT_WITH_STRING.toString()));
	}
	
	@Test
	public void testToStringCSV() {
		Assert.assertTrue(PERSON_RECORD_AS_CSV_STRING.equals(PERSON_RECORD.toStringCSV(null, null, null, false)));
	}
	
	@Test
	public void testToStringCSV1() {
		Assert.assertTrue(PERSON_RECORD_AS_CSV_STRING_1.equals(PERSON_RECORD.toStringCSV(null, null, null, true)));
	}
	
	@Test
	public void testToStringCSV2() {
		PERSON_RECORD.setValue(PersonRecordField.firstName, "Pao\"lo");
		Assert.assertTrue(PERSON_RECORD_AS_CSV_STRING_2.equals(PERSON_RECORD.toStringCSV(null, null, null, true)));
		PERSON_RECORD.setValue(PersonRecordField.firstName, "Paolo");
	}
	
	@Test
	public void testToStringCSV3() {
		PERSON_RECORD.setValue(PersonRecordField.firstName, "Pao\"lo");
		PERSON_RECORD.setValue(PersonRecordField.lastName, "Ros,si");
		Assert.assertTrue(PERSON_RECORD_AS_CSV_STRING_3.equals(PERSON_RECORD.toStringCSV(null, null, null, false)));
		PERSON_RECORD.setValue(PersonRecordField.firstName, "Paolo");
		PERSON_RECORD.setValue(PersonRecordField.lastName, "Rossi");
	}
	
	@Test
	public void testToStringCSV4() {
		Assert.assertTrue(PERSON_RECORD_AS_CSV_STRING_4.equals(PERSON_RECORD.toStringCSV(CSVSep.SEMICOLON, null, null, false)));
	}
	
	@Test
	public void testToStringCSV5() {
		Assert.assertTrue(PERSON_RECORD_AS_CSV_STRING_5.equals(PERSON_RECORD.toStringCSV(CSVSep.OTHER, "+", null, false)));
	}
	
	@Test
	public void testToStringCSV6() {
		Assert.assertTrue(PERSON_RECORD_AS_CSV_STRING_6.equals(PERSON_RECORD.toStringCSV(CSVSep.COMMA, null, CSVEnc.SINGLE_QUOTE, true)));
	}

}
