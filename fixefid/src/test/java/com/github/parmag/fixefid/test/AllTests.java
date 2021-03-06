package com.github.parmag.fixefid.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	PersonRecordFieldTest.class, 
	PersonRecordTest.class,
	DecimalRecordFieldTest.class,
	NumericRecordFieldTest.class,
	AlphaNumericRecordFieldTest.class,
	LPadPersonRecordFieldTest.class,
	CustomerRecordFieldTest.class,
	FlagRecordFieldTest.class,
	
	PersonTest.class,
	StudentTest.class,
	CustomerTest.class, 
	FlagTest.class,
	MultiFlagTest.class,
	MultiMultiFlagTest.class,
	MultiFlagWithFlagOccursTest.class,
	PersonWithEPAnnotationTest.class,
	Point3DTest.class,
	
	CarCSVTest.class,
	FlagCSVTest.class,
	
	CarCSVRecordFieldTest.class,
	FlagCSVRecordFieldTest.class
})
public class AllTests {

}
