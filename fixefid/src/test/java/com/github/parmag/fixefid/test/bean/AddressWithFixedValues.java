package com.github.parmag.fixefid.test.bean;

import java.util.Date;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.eps.FixefidBooleanFormat;
import com.github.parmag.fixefid.record.eps.FixefidDateFormat;
import com.github.parmag.fixefid.record.eps.FixefidDecimalFormat;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class AddressWithFixedValues {
	@FixefidField(fieldOrdinal = 11, fieldLen = 25, fieldType = FieldType.AN)
	private String location;
	@FixefidField(fieldOrdinal = 12, fieldLen = 5, fieldType = FieldType.AN)
	private String postalCode;
	@FixefidField(fieldOrdinal = 13, fieldLen = 2, fieldType = FieldType.AN, fieldFixedValues = "BO|MO")
	private String district;
	@FixefidField(fieldOrdinal = 14, fieldLen = 3, fieldType = FieldType.AN)
	private String nationIso3;
	@FixefidField(fieldOrdinal = 15, fieldLen = 30, fieldType = FieldType.AN)
	private String address;
	@FixefidField(fieldOrdinal = 16, fieldLen = 10, fieldType = FieldType.AN)
	private String num;
	@FixefidField(fieldOrdinal = 17, fieldLen = 8, fieldType = FieldType.N, fieldFixedValues = "100|200")
	private Integer addressRef;
	@FixefidDecimalFormat(pattern = "0.00", removeDecimalSeparator = true)
	@FixefidField(fieldOrdinal = 18, fieldLen = 8, fieldType = FieldType.N, fieldFixedValues = "100|200", fieldMandatory = FieldMandatory.INOUT, fieldDefaultValue = "100")
	private Float addressId;
	@FixefidDecimalFormat(pattern = "0.00", removeDecimalSeparator = false, locale = "it")
	@FixefidField(fieldOrdinal = 19, fieldLen = 8, fieldType = FieldType.N, fieldFixedValues = "1,00|2,00", fieldMandatory = FieldMandatory.INOUT)
	private Float addressId2;
	@FixefidDecimalFormat(pattern = "0.00", removeDecimalSeparator = false, locale = "en")
	@FixefidField(fieldOrdinal = 20, fieldLen = 8, fieldType = FieldType.N, fieldFixedValues = "1.00|2.00", fieldMandatory = FieldMandatory.INOUT)
	private Float addressId3;
	@FixefidBooleanFormat(trueValue = "Y", falseValue = "N")
	@FixefidField(fieldOrdinal = 21, fieldLen = 1, fieldType = FieldType.AN, fieldFixedValues = "Y|N")
	private Boolean vip;
	@FixefidDateFormat(pattern = "yyyy-MM-dd")
	@FixefidField(fieldOrdinal = 22, fieldLen = 10, fieldType = FieldType.AN, fieldFixedValues = "2022-04-11|2022-04-12")
	private Date addressDate;
	
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
	public Integer getAddressRef() {
		return addressRef;
	}
	public void setAddressRef(Integer addressRef) {
		this.addressRef = addressRef;
	}
	public Float getAddressId() {
		return addressId;
	}
	public void setAddressId(Float addressId) {
		this.addressId = addressId;
	}
	public Float getAddressId2() {
		return addressId2;
	}
	public void setAddressId2(Float addressId2) {
		this.addressId2 = addressId2;
	}
	public Float getAddressId3() {
		return addressId3;
	}
	public void setAddressId3(Float addressId3) {
		this.addressId3 = addressId3;
	}
	public Boolean getVip() {
		return vip;
	}
	public void setVip(Boolean vip) {
		this.vip = vip;
	}
	public Date getAddressDate() {
		return addressDate;
	}
	public void setAddressDate(Date addressDate) {
		this.addressDate = addressDate;
	}
	

}
