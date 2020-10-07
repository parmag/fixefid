package com.github.parmag.fixefid.record.format;

/**
 * A boolean formatter to get control how the boolean value is managed. An instance can be used with the field extended property
 * <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code>. 
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.0
 */
public interface BooleanFormat {
	/**
	 * Format the boolean <code>value</code> param to produce a formatted string  that represent the record
	 * 
	 * @param value the boolean to format
	 * @return the formatted string
	 */
	String format(Boolean value);
	
	/**
	 * Parses the <code>value</code> string param to produce a boolean that represent the value
	 * 
	 * @param value the string to be parsed
	 * @return A <code>Boolean</code> parsed from the string.
	 */
	Boolean parse(String value);
}
