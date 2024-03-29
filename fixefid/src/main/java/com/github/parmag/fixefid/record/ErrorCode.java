package com.github.parmag.fixefid.record;

/**
 * The enum that represent the error codes
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.1.0
 *
 */
public enum ErrorCode {
	/**
	 * Error to access internal java bean field by reflection
	 */
	RE1,
	/**
	 * Sync field value from record to bean error
	 */
	RE2,
	/**
	 * Error to access internal java bean field by reflection
	 */
	RE3,
	/**
	 * Error to access internal java bean field by reflection
	 */
	RE4,
	/**
	 * Error to access internal java bean field by reflection
	 */
	RE5,
	/**
	 * Record field extended property error
	 */
	RE6,
	/**
	 * Init record error cause input string diff vs record to string
	 */
	RE7,
	/**
	 * Not valid record len
	 */
	RE8,
	/**
	 * Unknown field name
	 */
	RE9,
	/**
	 * To string error cause record error status
	 */
	RE10,
	/**
	 * Error to set field value cause not valid value len
	 */
	RE11,
	/**
	 * Error to create record bean cause null bean
	 */
	RE12,
	/**
	 * Missing Java Bean FixefidRecord annnotation
	 */
	RE13,
	/**
	 * Field ordinal + subordinal must be unique (for type and super type)
	 */
	RE14,
	/**
	 * Java Bean field name reserved
	 */
	RE15,
	/**
	 * Sync field value from bean to record error
	 */
	RE16,
	/**
	 * Not found field by name
	 */
	RE17,
	/**
	 * Enum field name reserved
	 */
	RE18,
	/**
	 * Field name must be unique (for type and super type)
	 */
	RE19,
	/**
	 * "Init CSV record is malformed: found number values diff from record number values
	 */
	RE20,
	/**
	 * CSV separator size must be eq 1
	 */
	RE21,
	/**
	 * Init CSV record is malformed: found value which starts with enclosing string but not ends
	 */
	RE22,
	/**
	 * Init CSV record is malformed: found value with single-enclosing. Expected double-enclosing string.
	 */
	RE23,
	/**
	 * Missing Java Bean FixefidCSVRecord annnotation
	 */
	RE24,
	/**
	 * The bean field len must be greater than zero
	 */
	RE25,
	/**
	 * The field len must be greater than zero
	 */
	RE26,
	/**
	 * The field occurs must be greater than zero
	 */
	RE27,
	/**
	 * The CSV field occurs must be greater than zero
	 */
	RE28,
	/**
	 * The <code>FieldType.CMP</code> bean field occurs must be equals to 1
	 */
	RE29,
	/**
	 * The bean field occurs must be equals to 1
	 */
	RE30,
	/**
	 * The record field type must be equals to <code>FieldType.AN</code> or <code>FieldType.N</code>
	 */
	RE31,
	/**
	 * The CSV record field type must be equals to <code>FieldType.AN</code> or <code>FieldType.N</code>
	 */
	RE32,
	/**
	 * The <code>FieldType.LIST</code> bean field occurs must be greater than zero
	 */
	RE33,
	/**
	 * The <code>FieldType.LIST</code> bean field must be an instance of <code>java.util.ArrayList</code>
	 */
	RE34,
	/**
	 * If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain at least the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain at least 2 integers
	 */
	RE35,
	/**
	 * If the <code>key</code> is composite, the <code>fieldOccur</code> must
	 * contain the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>key</code> is <code>addresses-2.location-1</code>, <code>fieldOccur</code> must contain 2 integers
	 */
	RE36,
	/**
	 * A field extended properties must be set via annotation or via hash map, never together
	 */
	RE37,
	/**
	 * If the initialization of a custom format fails
	 */
	RE38,
	
	/**
	 * NOT USED
	 */
	FE1,
	/**
	 * NOT USED
	 */
	FE2,
	/**
	 * NOT USED
	 */
	FE3,
	/**
	 * NOT USED
	 */
	FE4,
	/**
	 * NOT USED
	 */
	FE5,
	/**
	 * NOT USED
	 */
	FE6,
	/**
	 * NOT USED
	 */
	FE7,
	/**
	 * NOT USED
	 */
	FE8,
	/**
	 * NOT USED
	 */
	FE9,
	/**
	 * Field validation error
	 */
	FE10,
	/**
	 * NOT USED
	 */
	FE11,
	/**
	 * Field Numeric len min 19 error
	 */
	FE12,
	/**
	 * Field Alpha Numeric with decimal format extended property error
	 */
	FE13,
	/**
	 * Field Numeric with date format extended property error
	 */
	FE14,
	/**
	 * Field Numeric with boolean format extended property error
	 */
	FE15,
	/**
	 * Field Numeric with custom format extended property error
	 */
	FE16,
	/**
	 * Field Alpha Numeric with multiple formatters extended properties error
	 */
	FE17,
	/**
	 * Field with remove decimal separator extended property error
	 */
	FE18,
	/**
	 * Field with pad string len != 1 extended property error
	 */
	FE19,
	/**
	 * String parsing error
	 */
	FE20,
	/**
	 * Long parsing number format exception error
	 */
	FE21,
	/**
	 * Long parsing error
	 */
	FE22,
	/**
	 * Integer parsing number format exception error
	 */
	FE23,
	/**
	 * Integer parsing error
	 */
	FE24,
	/**
	 * Double parsing exception error
	 */
	FE25,
	/**
	 * Double parsing error
	 */
	FE26,
	/**
	 * Float parsing exception error
	 */
	FE27,
	/**
	 * Float parsing error
	 */
	FE28,
	/**
	 * BigDecimal parsing exception error
	 */
	FE29,
	/**
	 * BigDecimal parsing error
	 */
	FE30,
	/**
	 * Date parsing exception error
	 */
	FE31,
	/**
	 * Date parsing error
	 */
	FE32,
	/**
	 * Boolean parsing error
	 */
	FE33,
	/**
	 * Setting Log value error
	 */
	FE34,
	/**
	 * Setting Integer value error
	 */
	FE35,
	/**
	 * Setting Double value error
	 */
	FE36,
	/**
	 * Setting Float value error
	 */
	FE37,
	/**
	 * Setting BigDecimal value error
	 */
	FE38,
	/**
	 * Setting Date value error
	 */
	FE39,
	/**
	 * Setting Boolean value error
	 */
	FE40
}
