package com.github.parmag.fixefid.record.csv;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.parmag.fixefid.record.RecordWay;

/**
 * Configures a java bean for use with a record bean CSV
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface FixefidCSVRecord {
	/**
	 * @return the CSV separator
	 */
	public CSVSep recordSep() default CSVSep.COMMA;
	/**
	 * @return the CSV other separator
	 */
	public String recordOtherSep() default ",";
	/**
	 * @return the CSV enclosing string
	 */
	public CSVEnc recordEnc() default CSVEnc.DOUBLE_QUOTE;
	/**
	 * @return if true all the fields of the CSV record are enclosing with the enclosing string
	 */
	public boolean encloseAllFields() default false;
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
