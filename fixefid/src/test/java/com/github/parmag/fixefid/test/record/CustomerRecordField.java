package com.github.parmag.fixefid.test.record;

import com.github.parmag.fixefid.record.field.FieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;

public enum CustomerRecordField implements FieldProperty {
	id(19, FieldType.N),
	email(50, FieldType.AN);

	private int fieldLen; 
	private FieldType fieldType;
	
	private CustomerRecordField(int fieldLen, FieldType fieldType) {
		this.fieldLen = fieldLen;
		this.fieldType = fieldType;
	}
	
	@Override
	public FieldType fieldType() {
		return fieldType;
	}

	@Override
	public int fieldLen() {
		return fieldLen;
	}

}
