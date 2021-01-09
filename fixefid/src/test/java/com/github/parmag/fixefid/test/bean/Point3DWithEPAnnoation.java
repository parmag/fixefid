package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.eps.FixefidDecimalFormat;
import com.github.parmag.fixefid.record.eps.FixefidRPAD;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRPAD(padChar = "X")
@FixefidRecord
public class Point3DWithEPAnnoation {

	@FixefidDecimalFormat
	@FixefidField(fieldOrdinal = 1, fieldType = FieldType.N, fieldLen = 10)
	private double x;
	
	@FixefidDecimalFormat
	@FixefidField(fieldOrdinal = 2, fieldType = FieldType.N, fieldLen = 10)
	private double y;
	
	@FixefidDecimalFormat
	@FixefidField(fieldOrdinal = 3, fieldType = FieldType.N, fieldLen = 10)
	private double z;
	
	public Point3DWithEPAnnoation(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}

}
