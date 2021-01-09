package com.github.parmag.fixefid.test;

import org.junit.Assert;
import org.junit.Test;

import com.github.parmag.fixefid.record.BeanRecord;
import com.github.parmag.fixefid.test.bean.Point3DWithEPAnnoation;

public class Point3DTest {

	private static final Point3DWithEPAnnoation POINT3D_BEAN = new Point3DWithEPAnnoation(1.2, 3.56, 7.77);
	
	private static final BeanRecord POINT3D_BEAN_RECORD; 
	
	private static final String POINT3D_RECORD_AS_STRING = "120XXXXXXX356XXXXXXX777XXXXXXX";
	
	static {
		POINT3D_BEAN_RECORD = new BeanRecord(POINT3D_BEAN);
	}
	
	@Test
	public void testToString() { 
		Assert.assertTrue(POINT3D_RECORD_AS_STRING.contentEquals(POINT3D_BEAN_RECORD.toString()));
	}

}
