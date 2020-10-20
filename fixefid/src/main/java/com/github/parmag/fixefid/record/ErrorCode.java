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
	 * Field ordinal must be unique (for type and super type)
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
	
	FE1,
	FE2,
	FE3,
	FE4,
	FE5,
	FE6,
	FE7,
	FE8,
	FE9,
	FE10,
	FE11,
	FE12,
	FE13,
	FE14,
	FE15,
	FE16,
	FE17,
	FE18,
	FE19,
	FE20,
	FE21,
	FE22,
	FE23,
	FE24,
	FE25,
	FE26,
	FE27,
	FE28,
	FE29,
	FE30,
	FE31,
	FE32,
	FE33,
	FE34,
	FE35,
	FE36,
	FE37,
	FE38,
	FE39,
	FE40
}
