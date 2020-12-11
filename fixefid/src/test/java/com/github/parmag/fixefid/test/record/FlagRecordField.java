package com.github.parmag.fixefid.test.record;

import com.github.parmag.fixefid.record.field.FieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;

public enum FlagRecordField implements FieldProperty {
	name(FieldType.AN, 20, 1),
	flags(FieldType.AN, 1, 10),
	program(FieldType.AN, 10, 1);
	
	private FieldType fieldType;
	private int fieldLen;
	private int fieldOccurs;
	
	private FlagRecordField(FieldType fieldType, int fieldLen, int fieldOccurs) {
		this.fieldType = fieldType;
		this.fieldLen = fieldLen;
		this.fieldOccurs = fieldOccurs;
	}


	@Override
	public FieldType fieldType() {
		return fieldType;
	}

	@Override
	public int fieldLen() {
		return fieldLen;
	}
	
	@Override
	public int fieldOccurs() {
		return fieldOccurs;
	}

}
