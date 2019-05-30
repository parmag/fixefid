package org.fixefid.record.field;

/**
 * The <code>FieldExtendedProperty</code> represents a field extended property
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.0
 */
public class FieldExtendedProperty {

	private FieldExtendedPropertyType type;
	private Object value;

	/**
	 * Constructs a new <code>FieldExtendedProperty</code>
	 *  
	 * @param type the field extended property type
	 * @param value the value of the field extended property
	 */
	public FieldExtendedProperty(FieldExtendedPropertyType type, Object value) {
		this.setType(type);
		this.setValue(value);
	}

	/**
	 * @return the type of this <code>FieldExtendedProperty</code>
	 */
	public FieldExtendedPropertyType getType() {
		return type;
	}

	/**
	 * Set the type of this <code>FieldExtendedProperty</code>
	 * 
	 * @param type the type of this <code>FieldExtendedProperty</code>
	 */
	public void setType(FieldExtendedPropertyType type) {
		this.type = type;
	}

	/**
	 * @return the value of this <code>FieldExtendedProperty</code>
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Set the value of this <code>FieldExtendedProperty</code>
	 * 
	 * @param value the value of this <code>FieldExtendedProperty</code>
	 */
	public void setValue(Object value) {
		this.value = value;
	} 

}
