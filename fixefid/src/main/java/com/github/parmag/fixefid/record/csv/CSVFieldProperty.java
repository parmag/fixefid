package com.github.parmag.fixefid.record.csv;

import java.util.List;

import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldType;

/**
 * The field property of a CSV Record. A field has a name, a type, a mandatory type, a default value and some extended properties
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
