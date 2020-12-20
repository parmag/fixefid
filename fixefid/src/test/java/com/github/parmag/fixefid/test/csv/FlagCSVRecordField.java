package com.github.parmag.fixefid.test.csv;

import com.github.parmag.fixefid.record.csv.CSVFieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;

public enum FlagCSVRecordField implements CSVFieldProperty {
	name(FieldType.AN, 1),
	flags(FieldType.AN, 10),
	program(FieldType.AN, 1);
	
	private FieldType fieldType;
	private int fieldOccurs;
	
	private FlagCSVRecordField(FieldType fieldType, int fieldOccurs) {
		this.fieldType = fieldType;
		this.fieldOccurs = fieldOccurs;
	}

	@Override
	public FieldType fieldType() {
		return fieldType;
	}

	@Override
	public int fieldOccurs() {
		return fieldOccurs;
	}

}
