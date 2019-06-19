package com.github.parmag.fixefid.record.field;

/**
 * The enum that represents the field mandatory type. It's correlated with the enum <code>RecordWay</code>
 * 
 * @author Giancarlo Parma
 *
 * @since 1.0
 */
public enum FieldMandatory {
	/**
	 * The mandatory IN means that the field is mandatory only when the record way is <code>RecordWay.IN</code>
	 */
	IN, 
	/**
	 * The mandatory OUT means that the field is mandatory only when the record way is <code>RecordWay.OUT</code>
	 */
	OUT, 
	/**
	 * The mandatory INOUT means that the field is always mandatory (no matter the <code>RecordWay</code>)
	 */
	INOUT,
	/**
	 * The mandatory NO means that the field is not mandatory (no matter the <code>RecordWay</code>)
	 */
	NO
}
