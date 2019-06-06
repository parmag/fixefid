package org.fixefid.record;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.fixefid.record.field.FieldExtendedProperty;
import org.fixefid.record.field.FieldExtendedPropertyType;
import org.fixefid.record.field.FieldMandatory;
import org.fixefid.record.field.FieldProperty;
import org.fixefid.record.field.FieldType;
import org.fixefid.record.format.CustomFormat;

public class PersonRecord extends Record<PersonRecord.FIELDS> {  

	public static enum FIELDS implements FieldProperty {
		firstName(25, FieldType.AN, null),
		lastName(25, FieldType.AN, null),
		age(3, FieldType.N, null), 
		birthDate(8, FieldType.AN, Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH)))), 
		stature(3, FieldType.N, Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))),
					new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, Boolean.valueOf(true)))),
		birthDitrict(2, FieldType.AN, Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.CUSTOM_FORMAT, new CustomFormat() {
			@Override
			public String format(String value) {
				return value.toUpperCase();
			}

			@Override
			public String parse(String value) {
				return value.toLowerCase();
			}
		})));
		
		private int fieldLen; 
		private FieldType fieldType;
		private List<FieldExtendedProperty> recordFieldExtendedProperties;
		
		private FIELDS(int fieldLen, FieldType fieldType, List<FieldExtendedProperty> recordFieldExtendedProperties) {
			this.fieldLen = fieldLen;
			this.fieldType = fieldType;
			this.recordFieldExtendedProperties = recordFieldExtendedProperties;
		}

		@Override
		public int fieldLen() {
			return fieldLen;
		}

		@Override
		public FieldType fieldType() {
			return fieldType;
		}

		@Override
		public FieldMandatory fieldMandatory() {
			return FieldMandatory.INOUT;
		}

		@Override
		public String fieldDefaultValue() {
			return null;
		}

		@Override
		public List<FieldExtendedProperty> fieldExtendedProperties() {
			return recordFieldExtendedProperties;
		}
	}
	 
	public PersonRecord() {
		super(FIELDS.class);
	}
	
	public PersonRecord(String record) {
		super(record, FIELDS.class);
	}
}
