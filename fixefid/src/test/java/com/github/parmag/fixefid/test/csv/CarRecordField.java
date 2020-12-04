package com.github.parmag.fixefid.test.csv;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.github.parmag.fixefid.record.csv.CSVFieldProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.field.FieldType;
import com.github.parmag.fixefid.record.format.BooleanFormat;

public enum CarRecordField implements CSVFieldProperty {
	name(FieldType.AN, null),
	model(FieldType.AN, null),
	weight(FieldType.N,  Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))),
			new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, Boolean.valueOf(true)))
	),
	length(FieldType.N, null),
	width(FieldType.N, null),
	height(FieldType.N, null),
	speed(FieldType.N, null),
	productionDate(FieldType.AN, Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH)))
	),
	used(FieldType.AN,  Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new BooleanFormat() {
				@Override
				public String format(Boolean value) {
					return value ? "Y" : "N";
				}

				@Override
				public Boolean parse(String value) {
					return "Y".equals(value) ? true : false;
				}
			}))
	);
	
	private FieldType fieldType;
	private List<FieldExtendedProperty> recordFieldExtendedProperties;
	
	private CarRecordField(FieldType fieldType, List<FieldExtendedProperty> recordFieldExtendedProperties) {
		this.fieldType = fieldType; 
		this.recordFieldExtendedProperties = recordFieldExtendedProperties;
	}

	@Override
	public FieldType fieldType() {
		return fieldType;
	}
	
	@Override
	public List<FieldExtendedProperty> fieldExtendedProperties() {
		return recordFieldExtendedProperties;
	}

}
