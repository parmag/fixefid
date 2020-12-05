package com.github.parmag.fixefid.test.record;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;
import com.github.parmag.fixefid.record.format.BooleanFormat;
import com.github.parmag.fixefid.record.format.CustomFormat;
import com.github.parmag.fixefid.record.format.SimpleBooleanFormat;

public enum AlphaNumericRecordField implements FieldProperty {
	str(20, FieldMandatory.INOUT, null, null),
	strDefaultValue(20, FieldMandatory.INOUT, "default", null),
	strMandatory(20, FieldMandatory.INOUT, null, null),
	strMandatoryIn(20, FieldMandatory.IN, null, null),
	strMandatoryOut(20, FieldMandatory.OUT, null, null),
	strNoMandatory(20, FieldMandatory.NO, null, null),
	strDate(8, FieldMandatory.INOUT, null, Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH)))),
	strDateNotMandatory(8, FieldMandatory.NO, null, Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH)))),
	strBoolean(1, FieldMandatory.INOUT, null, Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new BooleanFormat() {
				@Override
				public String format(Boolean value) {
					return (value != null && value.booleanValue()) ? "Y" : "N";
				}

				@Override
				public Boolean parse(String value) {
					return "Y".equals(value) ? true : false;
				}
			}))),
	strBooleanNotMandatory(1, FieldMandatory.NO, null, Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new SimpleBooleanFormat("Y", "N")))),
	strRPAD(20, FieldMandatory.INOUT, null, Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.RPAD, " "))),
	strRPADWithXPAD(20, FieldMandatory.INOUT, null, Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.RPAD, "X"))),
	strLPAD(20, FieldMandatory.INOUT, null, Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " "))),
	strLPADWithXPAD(20, FieldMandatory.INOUT, null, Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, "X"))),
	strBooleanLPADWithXPAD(20, FieldMandatory.INOUT, null, Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, "X"),
			new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new SimpleBooleanFormat("Y", "N")))),
	strDateLPADWithXPAD(20, FieldMandatory.INOUT, null, Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, "X"),
			new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH)))),
	strCustom(10, FieldMandatory.INOUT, null, Arrays.asList(
			new FieldExtendedProperty(FieldExtendedPropertyType.CUSTOM_FORMAT, new CustomFormat() {
				@Override
				public String format(String value) {
					char[] charArray = value.toCharArray();
					//invert array
					for (int i = 0; i < charArray.length / 2; i++) {
				        char temp = charArray[i];
				        charArray[i] = charArray[charArray.length - 1 - i];
				        charArray[charArray.length - 1 - i] = temp;
				    } 
					
					return String.valueOf(charArray);
				}

				@Override
				public String parse(String value) {
					char[] charArray = value.toCharArray();
					//invert array
					for (int i = 0; i < charArray.length / 2; i++) {
				        char temp = charArray[i];
				        charArray[i] = charArray[charArray.length - 1 - i];
				        charArray[charArray.length - 1 - i] = temp;
				    } 
					
					return String.valueOf(charArray);
				}
			})));

	private int fieldLen;
	private FieldMandatory fieldMandatory;
	private String defaultValue;
	private List<FieldExtendedProperty> fieldExtendedProperties;

	private AlphaNumericRecordField(int fieldLen, FieldMandatory fieldMandatory, String defaultValue, 
			List<FieldExtendedProperty> fieldExtendedProperties) {
		this.fieldLen = fieldLen;
		this.fieldMandatory = fieldMandatory;
		this.defaultValue = defaultValue;
		this.fieldExtendedProperties = fieldExtendedProperties;
	}
	
	@Override
	public FieldType fieldType() {
		return FieldType.AN;
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
		return fieldExtendedProperties;
	}

}
