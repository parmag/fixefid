package com.github.parmag.fixefid.record.eps;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Configures a Fixefid extended property of type <code>FieldExtendedPropertyType.CUSTOM_FORMAT</code>
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface FixefidCustomFormat {
	/**
	 * The class name of the custom format.
	 * 
	 * @return the class name of the custom format
	 * 
	 * @see com.github.parmag.fixefid.record.format.CustomFormat
	 */
	public String className();
}
