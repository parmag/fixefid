package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class PersonWithAddressWithFixedValues extends Person {
	@FixefidField(fieldOrdinal = 11, fieldType = FieldType.CMP, fieldLen = 118)
	private AddressWithFixedValues address = new AddressWithFixedValues();

	public AddressWithFixedValues getAddress() {
		return address;
	}

	public void setAddress(AddressWithFixedValues address) {
		this.address = address;
	} 

	
}
