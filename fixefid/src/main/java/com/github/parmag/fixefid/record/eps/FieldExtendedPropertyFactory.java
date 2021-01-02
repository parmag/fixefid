package com.github.parmag.fixefid.record.eps;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.github.parmag.fixefid.record.ErrorCode;
import com.github.parmag.fixefid.record.RecordException;
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

	/**
	 * Create a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code>
	 * and value <code>java.text.DecimalFormat</code>.
	 * 
	 * @param pattern the pattern string of the <code>java.text.DecimalFormat</code>
	 * @param locale the locale of the <code>java.text.DecimalFormatSymbols</code>
	 * 
	 * @return a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code>
	 * and value <code>java.text.DecimalFormat</code>
	 */
	public static FieldExtendedProperty createDecimalFormat(String pattern, Locale locale) {
		return new FieldExtendedProperty(
			FieldExtendedPropertyType.DECIMAL_FORMAT, 
			new DecimalFormat(pattern, new DecimalFormatSymbols(locale)));
	}
	
	/**
	 * Create a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR</code>
	 * 
	 * @param removeDecimalSeparator if true remove the decimal separator
	 * 
	 * @return a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR</code>
	 */
	public static FieldExtendedProperty createRemoveDecimalSeparator(Boolean removeDecimalSeparator) {
		return new FieldExtendedProperty(
			FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, removeDecimalSeparator);
	}
	
	/**
	 * Create a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.DATE_FORMAT</code>
	 * and value <code>java.text.SimpleDateFormat</code>.
	 * 
	 * @param pattern  the pattern describing the date and time format of the <code>java.text.SimpleDateFormat</code>
	 * @param the locale whose date format symbols should be used
	 * 
	 * @return a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.DATE_FORMAT</code>
	 * and value <code>java.text.SimpleDateFormat</code>
	 */
	public static FieldExtendedProperty createDateFormat(String pattern, Locale locale) {
		return new FieldExtendedProperty(
			FieldExtendedPropertyType.DATE_FORMAT, 
			new SimpleDateFormat(pattern, locale));
	}
	
	/**
	 * Create a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code>
	 * and value <code>com.github.parmag.fixefid.record.format.SimpleBooleanFormat</code>.
	 * 
	 * @param trueValue the tue value
	 * @param falseValue the false value
	 * 
	 * @return a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code>
	 * and value <code>com.github.parmag.fixefid.record.format.SimpleBooleanFormat</code>
	 */
	public static FieldExtendedProperty createBooleanFormat(String trueValue, String falseValue) {
		return new FieldExtendedProperty(
			FieldExtendedPropertyType.BOOLEAN_FORMAT, 
			new SimpleBooleanFormat(trueValue, falseValue));
	}
	
	/**
	 * Create a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.CUSTOM_FORMAT</code>
	 * and value an implementation of the interface <code>com.github.parmag.fixefid.record.format.CustomFormat</code>
	 * 
	 * @param className the class name of the custom format
	 * 
	 * @return a <code>FieldExtendedProperty</code> with type <code>FieldExtendedPropertyType.CUSTOM_FORMAT</code>
	 * and value an implementation of the interface <code>com.github.parmag.fixefid.record.format.CustomFormat</code>
	 */
	public static FieldExtendedProperty createCustomFormat(String className) {
		try {
			return new FieldExtendedProperty(
				FieldExtendedPropertyType.CUSTOM_FORMAT, 
				Class.forName(className).newInstance());
		} catch (Exception e) {
			throw new RecordException(ErrorCode.RE38, e);
		} 
	}

}
