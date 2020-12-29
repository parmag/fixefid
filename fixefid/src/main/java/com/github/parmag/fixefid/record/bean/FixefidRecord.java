package com.github.parmag.fixefid.record.bean;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.parmag.fixefid.record.RecordWay;

/**
 * Configures a java bean for use with a record bean
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.1.0
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface FixefidRecord {
	/**
	 * @return the len of the java bean
	 */
	public int recordLen() default 0;
	/**
	 * @return the way of the java bean property
	 */
	public RecordWay recordWay() default RecordWay.IN;
	/**
	 * @return the type of the record
	 */
	public String recordType() default "";
	/**
	 * @return the name of the record
	 */
	public String recordName() default "";
	/**
	 * @return the description of the record
	 */
	public String recordDescription() default "";
}
