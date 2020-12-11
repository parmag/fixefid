package com.github.parmag.fixefid.record.field;

/**
 * The enum that represents the field type. A field can be alphanumeric, numeric, composite or list.
 * The composite and list types can be used only with a bean record
 * 
 * @author Giancarlo Parma
 *
 * @since 1.0
 */
public enum FieldType {
	/**
	 * the alphanumeric type
	 */
	AN, 
	/**
	 * the numeric type
	 */
	N,
	/**
	 * the composite type
	 */
	CMP,
	/**
	 * the list type
	 */
	LIST 
}
