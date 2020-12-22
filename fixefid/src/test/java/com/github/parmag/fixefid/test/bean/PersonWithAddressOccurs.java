package com.github.parmag.fixefid.test.bean;

import java.util.ArrayList;
import java.util.List;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class PersonWithAddressOccurs extends Person {
	@FixefidField(fieldOrdinal = 11, fieldLen = 75, fieldType = FieldType.LIST, fieldOccurs = 3, fieldTypeList = FieldType.CMP)
	private List<Address> addresses = new ArrayList<Address>();
	
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	

	
}
