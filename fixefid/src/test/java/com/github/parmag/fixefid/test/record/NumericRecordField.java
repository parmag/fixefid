package com.github.parmag.fixefid.test.record;

import java.util.Arrays;
import java.util.List;

import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;
import com.github.parmag.fixefid.record.field.FieldValidationInfo;
import com.github.parmag.fixefid.record.field.FieldValidator;
import com.github.parmag.fixefid.record.field.FieldValidationInfo.RecordFieldValidationStatus;

public enum NumericRecordField implements FieldProperty {
	intNumeric(5, null, FieldMandatory.INOUT, null),
	intNumericDefault(5, "11111", FieldMandatory.INOUT, null),
	intNumericNoMandatory(5, null, FieldMandatory.NO, null),
	intNumericValidator(5, null, FieldMandatory.INOUT, 
		Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.VALIDATOR, new FieldValidator() {
			@Override
			public FieldValidationInfo valid(String name, int index, int subIndex, int occurIndex, FieldType type, FieldMandatory mandatory, String value,
					List<FieldExtendedProperty> fieldExtendedProperties, List<String> fixedValues) {
				if (value.contains("-")) {
					return new FieldValidationInfo(RecordFieldValidationStatus.ERROR, "value cannot be negative");
				} else {
					return new FieldValidationInfo();
				}
			}
		}))),
	longNumeric(15, null, FieldMandatory.INOUT, null),
	longNumericDefault(15, "111111111111111", FieldMandatory.INOUT, null),
	intNumericWithLPADSpace(5, null, FieldMandatory.INOUT, 
		Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " "))),
	intNumericWithRPADSpace(5, null, FieldMandatory.INOUT, 
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.RPAD, " ")));
	
	private int fieldLen;
	private String defaultValue;
	private FieldMandatory fieldMandatory;
	private List<FieldExtendedProperty> recordFieldExtendedProperties;
	
	private NumericRecordField(int fieldLen, String defaultValue, FieldMandatory fieldMandatory, 
			List<FieldExtendedProperty> recordFieldExtendedProperties) {
		this.fieldLen = fieldLen;
		this.defaultValue = defaultValue;
		this.fieldMandatory = fieldMandatory; 
		this.recordFieldExtendedProperties = recordFieldExtendedProperties;
	} 

	@Override
	public FieldType fieldType() {
		return FieldType.N;
	}

	@Override
	public int fieldLen() {
		return fieldLen;
	}

	@Override
	public FieldMandatory fieldMandatory() {
		return fieldMandatory;
	}

	@Override
	public String fieldDefaultValue() {
		return defaultValue;
	}

	@Override
	public List<FieldExtendedProperty> fieldExtendedProperties() {
		return recordFieldExtendedProperties;
	}
}
