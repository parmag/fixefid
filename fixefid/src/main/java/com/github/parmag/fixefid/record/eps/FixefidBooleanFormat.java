package com.github.parmag.fixefid.record.eps;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Configures a Fixefid extended property of type <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code>
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface FixefidBooleanFormat {
	/**
	 * The true value of the boolean format. The default returns <code>Y</code>
	 * 
	 * @return the true value of the boolean format
	 * 
	 * @see com.github.parmag.fixefid.record.format.SimpleBooleanFormat
	 */
	public String trueValue() default "Y";
	
	/**
	 * The false value of the boolean format. The default returns <code>Y</code>
	 * 
	 * @return the false value of the boolean format
	 * 
	 * @see com.github.parmag.fixefid.record.format.SimpleBooleanFormat
	 */
	public String falseValue() default "N";
}
