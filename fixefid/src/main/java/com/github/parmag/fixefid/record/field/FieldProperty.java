package com.github.parmag.fixefid.record.field;

import java.util.List;

/**
 * The field property. A field has a name, a type, a len, a mandatory type, a default value, some extended properties and the occurs
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
	default FieldType fieldType() {
		return FieldType.AN;
	}
	
	/**
	 * @return the len of the field
	 */
	default int fieldLen() {
		return 10;
	}
	
	/**
	 * @return the mandatory type of the field. The default implementation returns <code>FieldMandatory.NO</code>
	 */
	default FieldMandatory fieldMandatory() {
		return FieldMandatory.NO;
	}
	
	/**
	 * @return the default value of the field. The default implementation returns empty string
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
	
	/**
	 * @return the occurs of the field. The default implementation returns <code>1</code>
	 */
	default int fieldOccurs() {
		return 1;
	}
	
	/**
	 * @return the display name of the field. The default implementation returns empty string
	 */
	default String fieldDisplayName() {
		return "";
	}
	
	/**
	 * @return the description of the field. The default implementation returns empty string
	 */
	default String fieldDescription() {
		return "";
	}
}
