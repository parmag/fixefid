package org.fixefid.test.record;

import java.util.List;

import org.fixefid.record.field.FieldExtendedProperty;
import org.fixefid.record.field.FieldMandatory;
import org.fixefid.record.field.FieldProperty;
import org.fixefid.record.field.FieldType;

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
