package com.github.parmag.fixefid.record.eps;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Configures a Fixefid extended property of type <code>FieldExtendedPropertyType.DATE_FORMAT</code>
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface FixefidDateFormat {
	/**
	 * The pattern of the date format. The default returns <code>yyyyMMdd</code>
	 * 
	 * @return the pattern of the date format
	 * 
	 * @see java.text.DateFormat
	 */
	public String pattern() default "yyyyMMdd";
	
	/**
	 * The language tag of the locale. 
	 * The default returns <code>en</code>
	 * 
	 * @return the language tag for the locale
	 */
	public String locale() default "en";
}
