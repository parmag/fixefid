package com.github.parmag.fixefid.record.csv;

import java.util.List;

import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldType;

/**
 * The field property of a CSV Record. A field has a name, a type, a mandatory type, a default value, some extended properties and the occurs
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 */
public interface CSVFieldProperty {
	/**
	 * @return the name of the field
	 */
	String name();
	
	/**
	 * @return the field type of the field
	 */
	FieldType fieldType();
	
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
	 * @return the occurs of the field. The default implementation returns 1
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
