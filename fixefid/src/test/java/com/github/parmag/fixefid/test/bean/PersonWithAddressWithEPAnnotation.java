package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class PersonWithAddressWithEPAnnotation extends PersonWithEPAnnotation {
	@FixefidField(fieldOrdinal = 11, fieldType = FieldType.CMP, fieldLen = 75)
	private AddressWithEPAnnotation address = new AddressWithEPAnnotation();

	public AddressWithEPAnnotation getAddress() {
		return address;
	}

	public void setAddress(AddressWithEPAnnotation address) {
		this.address = address;
	} 

	
}
