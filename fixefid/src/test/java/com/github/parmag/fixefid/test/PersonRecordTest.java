package com.github.parmag.fixefid.test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.test.record.PersonRecord;

public class PersonRecordTest {
	private static final Calendar CAL = Calendar.getInstance();
	
	private static final String PERSON_RECORD_AS_STRING = "Paolo                    Rossi                    05107102002186BO";
	private static final PersonRecord PERSON_RECORD = new PersonRecord(); 
	private static final PersonRecord PERSON_RECORD_STRING = new PersonRecord(PERSON_RECORD_AS_STRING);
	
	static {
		CAL.set(Calendar.DAY_OF_MONTH, 7);
		CAL.set(Calendar.MONTH, 9);
		CAL.set(Calendar.YEAR, 2002);
		CAL.set(Calendar.HOUR_OF_DAY, 0);
		CAL.set(Calendar.MINUTE, 0);
		CAL.set(Calendar.SECOND, 0);
		CAL.set(Calendar.MILLISECOND, 0);
		
		PERSON_RECORD.setValue(PersonRecord.FIELDS.firstName, "Paolo");
		PERSON_RECORD.setValue(PersonRecord.FIELDS.lastName, "Rossi");
		PERSON_RECORD.setValue(PersonRecord.FIELDS.age, 51);
		PERSON_RECORD.setValue(PersonRecord.FIELDS.birthDate, CAL.getTime()); 
		PERSON_RECORD.setValue(PersonRecord.FIELDS.stature, 1.86f);
		PERSON_RECORD.setValue(PersonRecord.FIELDS.birthDistrict, "bo");
	}
	
	@Test
	public void testToString() { 
		Assert.assertTrue(PERSON_RECORD_AS_STRING.contentEquals(PERSON_RECORD.toString()));
	}
	
	@Test
	public void testToStringConstructor() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.contentEquals(PERSON_RECORD_STRING.toString()));
	}
	
	@Test
	public void testLen() {
		Assert.assertTrue(PERSON_RECORD_AS_STRING.length() == PERSON_RECORD.getRecordLen());
	}
	
	@Test
	public void testGetBirthPlaceCustomFormatAsStringValue() {  
		Assert.assertTrue("bo".equals(PERSON_RECORD.getValueAsString(PersonRecord.FIELDS.birthDistrict)));
	}
	
	@Test
	public void testGetBirthPlaceCustomFormatAsString() {  
		Assert.assertTrue("BO".equals(PERSON_RECORD.getValue(PersonRecord.FIELDS.birthDistrict)));
	}
}
