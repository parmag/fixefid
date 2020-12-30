package com.github.parmag.fixefid.record.eps;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Configures a Fixefid extended property of type <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code>
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface FixefidDecimalFormat {
	/**
	 * The pattern of the decimal format. The default returns <code>0.00</code>
	 * 
	 * @return the pattern of the decimal format
	 * 
	 * @see java.text.DecimalFormat
	 */
	public String pattern() default "0.00";
	
	/**
	 * The language tag of the locale. 
	 * The default returns <code>en</code>
	 * 
	 * @return the language tag for the locale
	 */
	public String locale() default "en";
	
	/**
	 * Returns true if the decimal separator must be removed
	 * The default returns <code>true</code>
	 * 
	 * @return true if the decimal separator must be removed
	 */
	public boolean removeDecimalSeparator() default true;
}
