package com.github.parmag.fixefid.test.record;

import java.util.List;

import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;

public enum LPadPersonRecordField implements FieldProperty {
	firstName,
	lastName,
	age;

	@Override
	public FieldType fieldType() {
		return FieldType.AN;
	}

	@Override
	public int fieldLen() {
		return 20;
	}

	@Override
	public FieldMandatory fieldMandatory() {
		return FieldMandatory.NO;
	}

	@Override
	public String fieldDefaultValue() {
		return null;
	}

	@Override
	public List<FieldExtendedProperty> fieldExtendedProperties() {
		return null;
	}

}
