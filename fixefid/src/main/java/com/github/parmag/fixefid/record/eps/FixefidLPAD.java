package com.github.parmag.fixefid.record.eps;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Configures a Fixefid extended property of type <code>FieldExtendedPropertyType.LPAD</code>
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, FIELD })
public @interface FixefidLPAD {
	/**
	 * @return the left pad char
	 */
	public String padChar() default "0";
}
