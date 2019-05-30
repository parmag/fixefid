package org.fixefid.record.field;

import java.util.List;

/**
 * The field property. A field has a name, a type, a mandatory type, a default value and some extended properties
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
	 * @return the mandatory type of the field
	 */
	FieldMandatory fieldMandatory();
	
	/**
	 * @return the default value of the field
	 */
	String fieldDefaultValue();
	
	/**
	 * @return the extended properties of the field
	 */
	List<FieldExtendedProperty> fieldExtendedProperties();
}
