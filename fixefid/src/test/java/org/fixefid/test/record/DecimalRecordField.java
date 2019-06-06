package org.fixefid.test.record;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.fixefid.record.field.FieldExtendedProperty;
import org.fixefid.record.field.FieldExtendedPropertyType;
import org.fixefid.record.field.FieldMandatory;
import org.fixefid.record.field.FieldProperty;
import org.fixefid.record.field.FieldType;
import org.fixefid.record.field.FieldValidationInfo;
import org.fixefid.record.field.FieldValidationInfo.RecordFieldValidationStatus;
import org.fixefid.record.field.FieldValidator;

public enum DecimalRecordField implements FieldProperty {
	nodecimals (10, null, FieldMandatory.INOUT, 
		Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0", new DecimalFormatSymbols(Locale.ENGLISH))))),
	decimals (10, null, FieldMandatory.INOUT, 
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))))),
	decimalsWithNoSep (10, null, FieldMandatory.INOUT,
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, true),
				new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))))),
	decimalsWithGroupingSep (10, null, FieldMandatory.INOUT, 
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("###,##0.00", new DecimalFormatSymbols(Locale.ENGLISH))))),
	decimalsScientific (10, null, FieldMandatory.INOUT,
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.#####E0", new DecimalFormatSymbols(Locale.ENGLISH))))),
	decimalsPercentage (10, null, FieldMandatory.INOUT,
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0%", new DecimalFormatSymbols(Locale.ENGLISH))))),
	decimalsCurrency (10, null, FieldMandatory.INOUT,
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00'$'", new DecimalFormatSymbols(Locale.ENGLISH))))),
	defaultDecimals (10, "999.99", FieldMandatory.INOUT,
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))))),
	defaultNegativeDecimals (10, "-999.99", FieldMandatory.INOUT,
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))))),
	decimalsNumericSpace (10, null, FieldMandatory.NO,
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))))),
	decimalsWithNoNegativeValidator (10, null, FieldMandatory.NO, 
		Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.VALIDATOR, new FieldValidator() {
				@Override
				public FieldValidationInfo valid(String name, int index, FieldType type, FieldMandatory mandatory, String value,
						List<FieldExtendedProperty> fieldExtendedProperties) {
					
					if (value.contains("-")) {
						return new FieldValidationInfo(RecordFieldValidationStatus.ERROR, "value cannot be negative");
					} else {
						return new FieldValidationInfo();
					}
				}
			}),
		new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))))),
	floats (5, null, FieldMandatory.INOUT, 
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))))),
	decimalsWithLPADSpace(10, null, FieldMandatory.INOUT, 
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))),
					new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " "))),
	decimalsWithLPADSpaceAndNoSep(10, null, FieldMandatory.INOUT, 
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))),
					new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " "),
					new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, true))),
	decimalsWithRPADSpace(10, null, FieldMandatory.INOUT, 
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))),
					new FieldExtendedProperty(FieldExtendedPropertyType.RPAD, " "))),
	decimalsWithRPADSpaceAndNoSep(10, null, FieldMandatory.INOUT, 
			Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))),
					new FieldExtendedProperty(FieldExtendedPropertyType.RPAD, " "),
					new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, true)));

	private int fieldLen; 
	private String defaultValue;
	private FieldMandatory fieldMandatory;
	private List<FieldExtendedProperty> recordFieldExtendedProperties;
	
	private DecimalRecordField(int fieldLen, String defaultValue, FieldMandatory fieldMandatory,
			List<FieldExtendedProperty> recordFieldExtendedProperties) {
		this.fieldLen = fieldLen;
		this.defaultValue = defaultValue;
		this.fieldMandatory = fieldMandatory;
		this.recordFieldExtendedProperties = recordFieldExtendedProperties;  
	}

	@Override
	public int fieldLen() {
		return fieldLen;
	}

	@Override
	public FieldType fieldType() {
		return FieldType.N;
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
