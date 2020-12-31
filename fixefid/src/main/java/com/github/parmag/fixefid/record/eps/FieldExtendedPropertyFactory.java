package com.github.parmag.fixefid.record.eps;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.format.SimpleBooleanFormat;

/**
 * Field extended property factory
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
public class FieldExtendedPropertyFactory {

	public static FieldExtendedProperty createDecimalFormat(String pattern, Locale locale) {
		return new FieldExtendedProperty(
			FieldExtendedPropertyType.DECIMAL_FORMAT, 
			new DecimalFormat(pattern, new DecimalFormatSymbols(locale)));
	}
	
	public static FieldExtendedProperty createRemoveDecimalSeparator(Boolean removeDecimalSeparator) {
		return new FieldExtendedProperty(
			FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, removeDecimalSeparator);
	}
	
	public static FieldExtendedProperty createDateFormat(String pattern, Locale locale) {
		return new FieldExtendedProperty(
			FieldExtendedPropertyType.DATE_FORMAT, 
			new SimpleDateFormat(pattern, locale));
	}
	
	public static FieldExtendedProperty createBooleanFormat(String trueValue, String falseValue) {
		return new FieldExtendedProperty(
			FieldExtendedPropertyType.BOOLEAN_FORMAT, 
			new SimpleBooleanFormat(trueValue, falseValue));
	}

}
