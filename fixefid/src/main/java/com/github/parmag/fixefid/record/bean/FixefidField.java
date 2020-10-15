package com.github.parmag.fixefid.record.bean;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldType;

@Retention(RUNTIME)
@Target(FIELD)
public @interface FixefidField {
	public int fieldOrdinal();
	public int fieldLen();
	public FieldType fieldType();
	public FieldMandatory fieldMandatory() default FieldMandatory.NO;
	public String fieldDefaultValue() default "";
}
