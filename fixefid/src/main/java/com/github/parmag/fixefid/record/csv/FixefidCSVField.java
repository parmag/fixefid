package com.github.parmag.fixefid.record.csv;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldType;

/**
 * Configures a java bean property for using as a fixed field in a CSV record
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface FixefidCSVField {
	/**
	 * @return the ordinal of the java bean property
	 */
	public int fieldOrdinal();
	/**
	 * @return the type of the java bean property
	 */
	public FieldType fieldType() default FieldType.AN;
	/**
	 * @return the mandatory of the java bean property
	 */
	public FieldMandatory fieldMandatory() default FieldMandatory.NO;
	/**
	 * @return the default value of the java bean property
	 */
	public String fieldDefaultValue() default "";
	/**
	 * @return the pipe separated fixed values of the java bean property
	 */
	public String fieldFixedValues() default "";
	/**
	 * @return the sub ordinal of the java bean property
	 */
	public int fieldSubOrdinal() default 0;
	/**
	 * The occurs of the java bean property. It can be greater than 1 only if the bean property is a <code>java.util.ArrayList</code>.
	 * 
	 * @return the occurs of the java bean property
	 */
	public int fieldOccurs() default 1;
	/**
	 * The type of the java bean property <code>FieldType.LIST</code>. It has a meaning only if the type of this field is <code>FieldType.LIST</code>
	 * 
	 * @return the type of the java bean property <code>FieldType.LIST</code>
	 */
	public FieldType fieldTypeList() default FieldType.AN;
	/**
	 * @return the display name of the java bean property
	 */
	public String fieldDisplayName() default "";
	/**
	 * @return the description of the java bean property
	 */
	public String fieldDescritption() default "";
}
