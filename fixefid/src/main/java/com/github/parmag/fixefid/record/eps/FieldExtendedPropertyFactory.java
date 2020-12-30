package com.github.parmag.fixefid.record.eps;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;

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

}
