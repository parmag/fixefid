package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.eps.FixefidCustomFormat;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class AddressWithEPAnnotation {
	@FixefidField(fieldOrdinal = 11, fieldLen = 25, fieldType = FieldType.AN)
	private String location;
	@FixefidField(fieldOrdinal = 12, fieldLen = 5, fieldType = FieldType.AN)
	private String postalCode;
	@FixefidCustomFormat(className = "com.github.parmag.fixefid.test.format.UpperLowerCustomFormat")
	@FixefidField(fieldOrdinal = 13, fieldLen = 2, fieldType = FieldType.AN)
	private String district;
	@FixefidField(fieldOrdinal = 14, fieldLen = 3, fieldType = FieldType.AN)
	private String nationIso3;
	@FixefidField(fieldOrdinal = 15, fieldLen = 30, fieldType = FieldType.AN)
	private String address;
	@FixefidField(fieldOrdinal = 16, fieldLen = 10, fieldType = FieldType.AN)
	private String num;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getNationIso3() {
		return nationIso3;
	}
	public void setNationIso3(String nationIso3) {
		this.nationIso3 = nationIso3;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
}
