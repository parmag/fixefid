package com.github.parmag.fixefid.record.field;

/**
 * The enum that represents the field extended property. Some of them are valid only with certain  <code>FieldType</code>.
 * <p>
 * Used to create a <code>FieldExtendedProperty</code>
 * 
 * @author Giancarlo Parma
 *
 * @since 1.0
 */
public enum FieldExtendedPropertyType {
	/**
	 * Used to get control of the formatting of a numeric field.
	 * <p>
	 * It's valid only when the field is of type <code>FieldType.N</code>. The value must be a <code>java.text.DecimalFormat</code>
	 */
	DECIMAL_FORMAT,
	/**
	 * Used to remove decimal separator on the formatting of a numeric field.
	 * <p>
	 * It's valid only when the field is of type <code>FieldType.N</code> and the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> 
	 * is used. The value must be a <code>java.lang.Boolean</code>
	 */
	REMOVE_DECIMAL_SEPARATOR,
	/**
	 * Used to get control of the formatting of a alphanumeric field when the field represents a date.
	 * <p>
	 * It's valid only when the field is of type <code>FieldType.AN</code> and the <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code> 
	 * is not used and the <code>FieldExtendedPropertyType.CUSTOM_FORMAT</code> is not used. The value must be a <code>java.text.DateFormat</code>
	 */
	DATE_FORMAT,
	/**
	 * Used to get control of the formatting of a alphanumeric field when the field represents a boolean.
	 * <p>
	 * It's valid only when the field is of type <code>FieldType.AN</code> and the <code>FieldExtendedPropertyType.DATE_FORMAT</code> 
	 * is not used and the <code>FieldExtendedPropertyType.CUSTOM_FORMAT</code> is not used. The value must be a 
	 * <code>org.fixefid.record.format.BooleanFormat</code>
	 */
	BOOLEAN_FORMAT,
	/**
	 * Used to get control of the formatting of a alphanumeric field when the field represents a string.
	 * <p>
	 * It's valid only when the field is of type <code>FieldType.AN</code> and the <code>FieldExtendedPropertyType.DATE_FORMAT</code> 
	 * is not used and the <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code> is not used. The value must be a 
	 * <code>org.fixefid.record.format.CustomFormat</code>
	 */
	CUSTOM_FORMAT,
	/**
	 * Used to get control of the field padding. The LPAD force the left padding with the value provided with the 
	 * <code>FieldExtendedProperty</code>
	 */
	LPAD,
	/**
	 * Used to get control of the field padding. The RPAD force the right padding with the value provided with the 
	 * <code>FieldExtendedProperty</code>
	 */
	RPAD,
	/**
	 * Used to get control of the field validation. The value must be a <code>org.fixefid.record.field.FieldValidator</code>
	 */
	VALIDATOR;
}
