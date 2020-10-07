package com.github.parmag.fixefid.record.format;

/**
 * A custom formatter to get control how the string value is managed. An instance can be used with the field extended property
 * <code>FieldExtendedPropertyType.CUSTOM_FORMAT</code>. 
 * 
 * @author Giancarlo Parma
 *
 * @since 1.0
 */
public interface CustomFormat {
	/**
	 * Format the string <code>value</code> param to produce a formatted string that represent the record
	 * 
	 * @param value the string to format
	 * @return the formatted string
	 * 
	 */
	String format(String value);
	
	/**
	 * Parses the <code>value</code> string param to produce a string that represent the value
	 * 
	 * @param value the string to be parsed
	 * @return A <code>String</code> parsed from the string.
	 */
	String parse(String value);
}
