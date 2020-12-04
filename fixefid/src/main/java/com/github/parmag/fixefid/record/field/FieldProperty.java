package com.github.parmag.fixefid.record.field;

import java.util.List;

/**
 * The field property. A field has a name, a type, a len, a mandatory type, a default value and some extended properties
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.0
 */
public interface FieldProperty {
	/**
	 * @return the name of the field
	 */
	String name();
	
	/**
	 * @return the field type of the field
	 */
	FieldType fieldType();
	
	/**
	 * @return the len of the field
	 */
	int fieldLen();
	
	/**
	 * @return the mandatory type of the field. The default implementation returns <code>FieldMandatory.NO</code>
	 */
	default FieldMandatory fieldMandatory() {
		return FieldMandatory.NO;
	}
	
	/**
	 * @return the default value of the field. The default implementation returns <code>null</code>
	 */
	default String fieldDefaultValue() {
		return "";
	}
	
	/**
	 * @return the extended properties of the field. The default implementation returns <code>null</code>
	 */
	default List<FieldExtendedProperty> fieldExtendedProperties() {
		return null;
	}
}
