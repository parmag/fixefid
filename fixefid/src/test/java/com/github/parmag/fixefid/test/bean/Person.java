package com.github.parmag.fixefid.test.bean;

import java.util.Date;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class Person {
	@FixefidField(fieldOrdinal = 1, fieldLen = 25, fieldType = FieldType.AN)
	private String firstName;
	
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastName;
	
	@FixefidField(fieldOrdinal = 3, fieldLen = 3, fieldType = FieldType.N)
	private Integer age;
	
	@FixefidField(fieldOrdinal = 4, fieldLen = 8, fieldType = FieldType.AN)
	private Date birthDate;
	
	@FixefidField(fieldOrdinal = 5, fieldLen = 3, fieldType = FieldType.N)
	private Float stature;
	
	@FixefidField(fieldOrdinal = 6, fieldLen = 2, fieldType = FieldType.AN)
	private String birthDistrict;
	
	@FixefidField(fieldOrdinal = 7, fieldLen = 1, fieldType = FieldType.AN)
	private Boolean vip;
	
	private String notFixefidField;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Float getStature() {
		return stature;
	}
	public void setStature(Float stature) {
		this.stature = stature;
	}
	public String getBirthDistrict() {
		return birthDistrict;
	}
	public void setBirthDistrict(String birthDistrict) {
		this.birthDistrict = birthDistrict;
	}
	public String getNotFixefidField() {
		return notFixefidField;
	}
	public void setNotFixefidField(String notFixefidField) {
		this.notFixefidField = notFixefidField;
	}
	public Boolean getVip() {
		return vip;
	}
	public void setVip(Boolean vip) {
		this.vip = vip;
	}
}
