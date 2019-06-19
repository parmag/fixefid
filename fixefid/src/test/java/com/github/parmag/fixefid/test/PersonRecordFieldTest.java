package com.github.parmag.fixefid.test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.Record;
import com.github.parmag.fixefid.test.record.PersonRecordField;

public class PersonRecordFieldTest {
	private static final Calendar CAL = Calendar.getInstance();
	
	private static final String PERSON_RECORD_AS_STRING = "Paolo                    Rossi                    05107102002186";
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

}
