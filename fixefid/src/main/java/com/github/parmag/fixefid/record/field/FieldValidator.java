package com.github.parmag.fixefid.record.field;

import java.util.List;

/**
 * A field validator to get control how the field is validated. An instance can be used with the field extended property
 * <code>FieldExtendedPropertyType.VALIDATOR</code>. 
 * 
 * @author Giancarlo Parma
 *
 * @since 1.0
 */
public interface FieldValidator {
	/**
	 * Valid the <code>value</code> param of the field with <code>name</code> param
	 * 
	 * @param name the name of the field to validate
	 * @param index the index of the field to validate
	 * @param subIndex the sub-index of the field to validate
	 * @param occurIndex the occur index of the field to validate
	 * @param type the type of the field to validate
	 * @param mandatory the mandatory type of the field to validate
	 * @param value the value of the field to validate
	 * @param fieldExtendedProperties the field extended properties of the field to validate
	 * @param fixedValues the pipe separated list of valid values
	 * @return the validation info which represents the validation of the <code>value</code> param of the field with <code>name</code> param
	 */
	FieldValidationInfo valid(String name, int index, int subIndex, int occurIndex, FieldType type, FieldMandatory mandatory, String value,
			List<FieldExtendedProperty> fieldExtendedProperties, List<String> fixedValues);
} 
