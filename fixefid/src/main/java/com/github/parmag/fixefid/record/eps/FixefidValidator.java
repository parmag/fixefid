package com.github.parmag.fixefid.record.eps;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Configures a Fixefid extended property of type <code>FieldExtendedPropertyType.VALIDATOR</code>
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, FIELD })
public @interface FixefidValidator {
	/**
	 * The class name of the validator format.
	 * 
	 * @return the class name of the validator format
	 * 
	 * @see com.github.parmag.fixefid.record.field.FieldValidator
	 */
	public String className();
}
