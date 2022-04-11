package com.github.parmag.fixefid.record.csv;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.parmag.fixefid.record.AbstractRecord;
import com.github.parmag.fixefid.record.ErrorCode;
import com.github.parmag.fixefid.record.RecordException;
import com.github.parmag.fixefid.record.RecordWay;
import com.github.parmag.fixefid.record.field.Field;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;
import com.github.parmag.fixefid.record.field.FieldValidationInfo;

/**
 * The <code>CSVRecord</code> represents a CSV text.
 * 
 * @author Giancarlo Parma
 *
 * 
 * @since 2.0
 */
public class CSVRecord<T extends Enum<T> & CSVFieldProperty> extends AbstractRecord {
	private Class<T> fields; 
	
	private CSVSep recordSep;
	private String recordOtherSep;
	private CSVEnc recordEnclosing;
	private boolean recordEncloseAllFields;
	
	/**
	 * Constructs a new <code>CSVRecord</code> that represents the CSV fields indicated by the <code>fields</code> parameter.
	 * The record way is <code>RecordWay.IN</code>. The lenght of this record is calculated with the sum of the lenght of every
	 * field. The value of every field is initialized with a default value (if present)
	 * 
	 * Every value is separated with the <code>CSVSep.COMMA</code>. The fields aren't enclosing, 
     * eccept they contain the char <code>CSVSep.COMMA</code>. The enclosing char is <code>CSVEnc.DOUBLE_QUOTE</code>. 
     * Each of the embedded <code>CSVEnc.DOUBLE_QUOTE</code> characters is represented by a pair of 
     * double-enclosing characters.
	 * 
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 */
	public CSVRecord(Class<T> fields) {
		this(RecordWay.IN, null, fields, null);
	}
	
	/**
	 * Constructs a new <code>CSVRecord</code> that represents the CSV fields indicated by the <code>fields</code> parameter.
	 * The record way is <code>RecordWay.IN</code>. The value of every field is initialized with the formatted value present in 
	 * the relative position of the <code>csvRecord</code> parameter
	 * 
	 * Every value is separated with the <code>CSVSep.COMMA</code>. The fields aren't enclosing, 
     * eccept they contain the char <code>CSVSep.COMMA</code>. The enclosing char is <code>CSVEnc.DOUBLE_QUOTE</code>. 
     * Each of the embedded <code>CSVEnc.DOUBLE_QUOTE</code> characters is represented by a pair of 
     * double-enclosing characters.
	 * 
	 * @param csvRecord the formatted string of this <code>Record</code>
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 */
	public CSVRecord(String csvRecord, Class<T> fields) {
		this(RecordWay.IN, csvRecord, fields, null);
	}
	
	/**
	 * Constructs a new <code>CSVRecord</code> that represents the CSV fields indicated by the <code>fields</code> parameter.
	 * The record way is initialized with the value of the <code>recordWay</code> parameter. The value of every field is 
	 * initialized with a default value (if present)
	 * 
	 * Every value is separated with the <code>CSVSep.COMMA</code>. The fields aren't enclosing, 
     * eccept they contain the char <code>CSVSep.COMMA</code>. The enclosing char is <code>CSVEnc.DOUBLE_QUOTE</code>. 
     * Each of the embedded <code>CSVEnc.DOUBLE_QUOTE</code> characters is represented by a pair of 
     * double-enclosing characters.
	 * 
	 * @param recordWay the way of this record
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 */
	public CSVRecord(RecordWay recordWay, Class<T> fields) {
		this(recordWay, null, fields, null);
	}
	
	/**
	 * Constructs a new <code>CSVRecord</code> that represents the CSV fields indicated by the <code>fields</code> parameter.
	 * The record way is initialized with the value of the <code>recordWay</code> parameter. The lenght of this record is 
	 * calculated with the sum of the lenght of every field. The value of every field is initialized with the formatted value present in the
	 * relative position of the <code>record</code> parameter. 
	 * 
	 * Every value is separated with the <code>CSVSep.COMMA</code>. The fields aren't enclosing, 
     * eccept they contain the char <code>CSVSep.COMMA</code>. The enclosing char is <code>CSVEnc.DOUBLE_QUOTE</code>. 
     * Each of the embedded <code>CSVEnc.DOUBLE_QUOTE</code> characters is represented by a pair of 
     * double-enclosing characters.
     * 
	 * @param recordWay the way of this record
	 * @param csvRecord the formatted string of this <code>Record</code>
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 */
	public CSVRecord(RecordWay recordWay, String csvRecord, Class<T> fields) {
		this(recordWay, csvRecord, fields, null);
	}
	
	/**
	 * Constructs a new <code>CSVRecord</code> that represents the CSV fields indicated by the <code>fields</code> parameter.
	 * The record way is initialized with the value of the <code>recordWay</code> parameter. The value of every field is initialized with 
	 * the formatted value present in the relative position of the <code>csvRecord</code> parameter. To every field of the record are applied the extended properties
	 * present in the <code>fieldExtendedProperties</code> parameter. Only the following properties are permitted at record level:
	 * <ul>
	 * <li><code>FieldExtendedPropertyType.LPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.RPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.VALIDATOR</code></li>
	 * </ul>
	 * 
	 * Every value is separated with the <code>CSVSep.COMMA</code>. The fields aren't enclosing, 
     * eccept they contain the char <code>CSVSep.COMMA</code>. The enclosing char is <code>CSVEnc.DOUBLE_QUOTE</code>. 
     * Each of the embedded <code>CSVEnc.DOUBLE_QUOTE</code> characters is represented by a pair of 
     * double-enclosing characters.
     * 
	 * @param recordWay the way of this record
	 * @param csvRecord the formatted string of this <code>Record</code>
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 */
	public CSVRecord(RecordWay recordWay, String csvRecord, Class<T> fields, List<FieldExtendedProperty> fieldExtendedProperties) {
		this(recordWay, csvRecord, fields, fieldExtendedProperties, CSVSep.COMMA, null, CSVEnc.DOUBLE_QUOTE, false);
	}
	
	/**
	 * Constructs a new <code>CSVRecord</code> that represents the CSV fields indicated by the <code>fields</code> parameter.
	 * The record way is initialized with the value of the <code>recordWay</code> parameter. The value of every field is initialized with 
	 * the formatted value present in the relative position of the <code>csvRecord</code> parameter. To every field of the record are applied the extended properties
	 * present in the <code>fieldExtendedProperties</code> parameter. Only the following properties are permitted at record level:
	 * <ul>
	 * <li><code>FieldExtendedPropertyType.LPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.RPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.VALIDATOR</code></li>
	 * </ul>
	 * 
	 * Every value is separated with the <code>recordSep</code> param.
     * If the <code>recordSep</code> param is null, the default sep is <code>CSVSep.COMMA</code>. If <code>recordEncloseAllFields</code> is true,
     * every field of the record is enclosed with the <code>recordEnclosing</code> param. If the <code>recordEnclosing</code> param is null,
     * the default enclosing is <code>CSVEnc.DOUBLE_QUOTE</code>. If the <code>recordEncloseAllFields</code> param is false the fields aren't enclosing, 
     * eccept they contain the char sep. Each of the embedded char enclosing characters is represented by a pair of 
     * double-enclosing characters.
     * 
	 * @param recordWay the way of this record
	 * @param csvRecord the formatted string of this <code>Record</code>
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 * @param recordSep the field separator. If null the sep is <code>CSVSep.COMMA</code>
     * @param recordOtherSep the field separator if <code>sep</code> param is <code>CSVSep.OTHER</code>. Size must be eq 1. If null the default sep is <code>CSVSep.COMMA</code>
     * @param recordEnclosing the enclosing char. If null the enclosing char is <code>CSVEnc.DOUBLE_QUOTE</code>
     * @param recordEncloseAllFields if true every field of the record is enclosed with the enclosing char, otherwise only field which contains sep
	 */
	public CSVRecord(RecordWay recordWay, String csvRecord, Class<T> fields, List<FieldExtendedProperty> fieldExtendedProperties,
			CSVSep recordSep, String recordOtherSep, CSVEnc recordEnclosing, boolean recordEncloseAllFields) {
		this.fields = fields;
		this.recordSep = recordSep != null ? recordSep : CSVSep.COMMA;
		this.recordOtherSep = recordOtherSep;
		this.recordEnclosing = recordEnclosing != null ? recordEnclosing : CSVEnc.DOUBLE_QUOTE;
		this.recordEncloseAllFields = recordEncloseAllFields;
		
		initFieldExtendedProperties(fieldExtendedProperties);
		
		this.recordLen = 0;
		this.recordWay = recordWay;
		initFieldsMap(); 
		if (csvRecord != null) {
			initRecord(csvRecord);
		}
	}
	
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is mandatory
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is mandatory
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is mandatory
	 */
	public boolean isMandatory(FieldProperty csvFieldProperty) {
		return isMandatory(csvFieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is mandatory
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is mandatory
	 * @param fieldOccur the field occur to get the field
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is mandatory
	 */
	public boolean isMandatory(FieldProperty csvFieldProperty, int fieldOccur) {
		return isMandatory(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is a <code>String</code>.
	 * A field is a <code>String</code> if is of type <code>FieldType.AN</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>String</code>
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is a <code>String</code>
	 */
	public boolean isString(CSVFieldProperty csvFieldProperty) {
		return isString(csvFieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is a <code>String</code>.
	 * A field is a <code>String</code> if is of type <code>FieldType.AN</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>String</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is a <code>String</code>
	 */
	public boolean isString(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isString(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>Long</code>.
	 * A field is a <code>Long</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &ge; 10</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Long</code>
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>Long</code>
	 */
	public boolean isLong(CSVFieldProperty csvFieldProperty) {
		return isLong(csvFieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>Long</code>.
	 * A field is a <code>Long</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &ge; 10</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Long</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>Long</code>
	 */
	public boolean isLong(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isLong(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>Integer</code>.
	 * A field is a <code>Integer</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &lt; 10</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Integer</code>
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>Integer</code>
	 */
	public boolean isInteger(CSVFieldProperty csvFieldProperty) {
		return isInteger(csvFieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>Integer</code>.
	 * A field is a <code>Integer</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &lt; 10</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Integer</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>Integer</code>
	 */
	public boolean isInteger(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isInteger(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is a <code>Date</code>.
	 * A field is a <code>Date</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.DATE_FORMAT</code> is present.
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Date</code>
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is a <code>Date</code>
	 */
	public boolean isDate(CSVFieldProperty csvFieldProperty) {
		return isDate(csvFieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is a <code>Date</code>.
	 * A field is a <code>Date</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.DATE_FORMAT</code> is present.
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Date</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is a <code>Date</code>
	 */
	public boolean isDate(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isDate(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is a <code>Boolean</code>.
	 * A field is a <code>Boolean</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code> is present.
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Boolean</code>
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is a <code>Boolean</code>
	 */
	public boolean isBoolean(CSVFieldProperty csvFieldProperty) {
		return isBoolean(csvFieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is a <code>Boolean</code>.
	 * A field is a <code>Boolean</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code> is present.
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Boolean</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is a <code>Boolean</code>
	 */
	public boolean isBoolean(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isBoolean(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &ge; 10</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>Double</code>
	 */
	public boolean isDouble(CSVFieldProperty csvFieldProperty) {
		return isDouble(csvFieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &ge; 10</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>Double</code>
	 */
	public boolean isDouble(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isDouble(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &lt; 10</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>Double</code>
	 */
	public boolean isFloat(CSVFieldProperty csvFieldProperty) {
		return isFloat(csvFieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &lt; 10</code>
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>Double</code>
	 */
	public boolean isFloat(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isFloat(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>BigDecimal</code>.
	 * A field is a <code>BigDecimal</code> if is <code>Double</code> or a <code>Float</code>.
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>BigDecimal</code>
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> param is <code>BigDecimal</code>
	 */
	public boolean isBigDecimal(CSVFieldProperty csvFieldProperty) {
		return isBigDecimal(csvFieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>BigDecimal</code>.
	 * A field is a <code>BigDecimal</code> if is <code>Double</code> or a <code>Float</code>.
	 * 
	 * @param csvFieldProperty the field property to know if the relative field is a <code>BigDecimal</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params is <code>BigDecimal</code>
	 */
	public boolean isBigDecimal(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isBigDecimal(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the formatted value of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property to get the formatted value of the relative field
	 * @return the formatted value of the field represented by the <code>csvFieldProperty</code> param
	 */
	public String getValue(CSVFieldProperty csvFieldProperty) {
		return getValue(csvFieldProperty.name());
	}
	
	/**
	 * Returns the formatted value of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property to get the formatted value of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the formatted value of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 */
	public String getValue(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return getValue(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>String</code> of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>String</code> of the relative field
	 * @return the value as <code>String</code> of the field represented by the <code>csvFieldProperty</code> param
	 * @throws FieldException if the field is not a String
	 */
	public String getValueAsString(CSVFieldProperty csvFieldProperty) throws FieldException {
		return getValueAsString(csvFieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>String</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>String</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>String</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a String
	 */
	public String getValueAsString(CSVFieldProperty csvFieldProperty, int fieldOccur) throws FieldException {
		return getValueAsString(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Long</code> of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Long</code> of the relative field
	 * @return the value as <code>Long</code> of the field represented by the <code>csvFieldProperty</code> param
	 * @throws FieldException if the field is not a Long
	 */
	public Long getValueAsLong(CSVFieldProperty csvFieldProperty) throws FieldException {
		return getValueAsLong(csvFieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Long</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Long</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Long</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Long
	 */
	public Long getValueAsLong(CSVFieldProperty csvFieldProperty, int fieldOccur) throws FieldException {
		return getValueAsLong(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Integer</code> of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Integer</code> of the relative field
	 * @return the value as <code>Integer</code> of the field represented by the <code>csvFieldProperty</code> param
	 * @throws FieldException if the field is not a Integer
	 */
	public Integer getValueAsInteger(CSVFieldProperty csvFieldProperty) throws FieldException {
		return getValueAsInteger(csvFieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Integer</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Integer</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Integer</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Integer
	 */
	public Integer getValueAsInteger(CSVFieldProperty csvFieldProperty, int fieldOccur) throws FieldException {
		return getValueAsInteger(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Double</code> of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Double</code> of the relative field
	 * @return the value as <code>Double</code> of the field represented by the <code>csvFieldProperty</code> param
	 * @throws FieldException if the field is not a Double
	 */
	public Double getValueAsDouble(CSVFieldProperty csvFieldProperty) throws FieldException {
		return getValueAsDouble(csvFieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Double</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Double</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Double</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Double
	 */
	public Double getValueAsDouble(CSVFieldProperty csvFieldProperty, int fieldOccur) throws FieldException {
		return getValueAsDouble(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Float</code> of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Float</code> of the relative field
	 * @return the value as <code>Float</code> of the field represented by the <code>csvFieldProperty</code> param
	 * @throws FieldException if the field is not a Float
	 */
	public Float getValueAsFloat(CSVFieldProperty csvFieldProperty) throws FieldException {
		return getValueAsFloat(csvFieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Float</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Float</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Float</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Float
	 */
	public Float getValueAsFloat(CSVFieldProperty csvFieldProperty, int fieldOccur) throws FieldException {
		return getValueAsFloat(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>BigDecimal</code> of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>BigDecimal</code> of the relative field
	 * @return the value as <code>BigDecimal</code> of the field represented by the <code>csvFieldProperty</code> param
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public BigDecimal getValueAsBigDecimal(CSVFieldProperty csvFieldProperty) throws FieldException {
		return getValueAsBigDecimal(csvFieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>BigDecimal</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>BigDecimal</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>BigDecimal</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public BigDecimal getValueAsBigDecimal(CSVFieldProperty csvFieldProperty, int fieldOccur) throws FieldException {
		return getValueAsBigDecimal(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Date</code> of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Date</code> of the relative field
	 * @return the value as <code>Date</code> of the field represented by the <code>csvFieldProperty</code> param
	 * @throws FieldException if the field is not a Date
	 */
	public Date getValueAsDate(CSVFieldProperty csvFieldProperty) throws FieldException {
		return getValueAsDate(csvFieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Date</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Date</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Date</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Date
	 */
	public Date getValueAsDate(CSVFieldProperty csvFieldProperty, int fieldOccur) throws FieldException {
		return getValueAsDate(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Boolean</code> of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Boolean</code> of the relative field
	 * @return the value as <code>Boolean</code> of the field represented by the <code>csvFieldProperty</code> param
	 * @throws FieldException if the field is not a Boolean
	 */
	public Boolean getValueAsBoolean(CSVFieldProperty csvFieldProperty) throws FieldException {
		return getValueAsBoolean(csvFieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Boolean</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property to get the value as <code>Boolean</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Boolean</code> of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Boolean
	 */
	public Boolean getValueAsBoolean(CSVFieldProperty csvFieldProperty, int fieldOccur) throws FieldException {
		return getValueAsBoolean(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, String value) {
		setValue(csvFieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, String value, int fieldOccur) {
		setValue(csvFieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> param. 
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the specified value
	 * @param value the value to set
	 * @param truncate If the <code>truncate</code> param is <code>true</code> and the len of the specified value is greater than the len of the 
	 * field, the specified value will be truncated at the len od the field. 
	 * @throws RecordException If the <code>truncate</code> param is <code>false</code> and the len of the specified value is 
	 * greater than the len of the field
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, String value, boolean truncate) throws RecordException {
		setValue(csvFieldProperty.name(), value, truncate);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params. 
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the specified value
	 * @param value the value to set
	 * @param truncate If the <code>truncate</code> param is <code>true</code> and the len of the specified value is greater than the len of the 
	 * field, the specified value will be truncated at the len od the field. 
	 * @param fieldOccur the field occur to get the field
	 * @throws RecordException If the <code>truncate</code> param is <code>false</code> and the len of the specified value is 
	 * greater than the len of the field
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, String value, boolean truncate, int fieldOccur) throws RecordException {
		setValue(csvFieldProperty.name(), value, truncate, fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Long value) throws FieldException {
		setValue(csvFieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(CSVFieldProperty csvFieldProperty,Long value,  int fieldOccur) throws FieldException {
		setValue(csvFieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Integer value) throws FieldException {
		setValue(csvFieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Integer value, int fieldOccur) throws FieldException {
		setValue(csvFieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Double value) throws FieldException {
		setValue(csvFieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Double value, int fieldOccur) throws FieldException {
		setValue(csvFieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Float value) throws FieldException {
		setValue(csvFieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Float value, int fieldOccur) throws FieldException {
		setValue(csvFieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, BigDecimal value) throws FieldException {
		setValue(csvFieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, BigDecimal value, int fieldOccur) throws FieldException {
		setValue(csvFieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Date value) throws FieldException {
		setValue(csvFieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Date value, int fieldOccur) throws FieldException {
		setValue(csvFieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Boolean value) throws FieldException {
		setValue(csvFieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(CSVFieldProperty csvFieldProperty, Boolean value, int fieldOccur) throws FieldException {
		setValue(csvFieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Returns the field represented by the <code>csvFieldProperty</code> param
	 *  
	 * @param csvFieldProperty the field property to get the field
	 * @return the field represented by the <code>fieldProperty</code> param
	 * @throws RecordException if the <code>csvFieldProperty</code> param doesn't represent any field of the record
	 */
	protected Field getRecordField(CSVFieldProperty csvFieldProperty) throws RecordException {
		return getRecordField(csvFieldProperty.name());
	}
	
	/**
	 * Returns the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 *  
	 * @param csvFieldProperty the field property to get the field
	 * @param fieldOccur the field occur to get the field
	 * @return the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws RecordException if the <code>csvFieldProperty</code> param doesn't represent any field of the record
	 */
	protected Field getRecordField(CSVFieldProperty csvFieldProperty, int fieldOccur) throws RecordException {
		return getRecordField(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the len of the record represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the property of the field to know the len
	 * @return the len of the record represented by the <code>csvFieldProperty</code> param
	 */
	public int getFieldLen(CSVFieldProperty csvFieldProperty) {
		return getFieldLen(csvFieldProperty.name());
	}
	
	/**
	 * Returns the len of the record represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the property of the field to know the len
	 * @param fieldOccur the field occur to get the field
	 * @return the len of the record represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 */
	public int getFieldLen(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return getFieldLen(csvFieldProperty.name(), fieldOccur);
	}

	/**
	 * Apply the method {@link String#toUpperCase} to the field of type <code>FieldType.AN</code> represented by 
	 * the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property of the field to apply the upper case
	 */
	public void toUpperCase(CSVFieldProperty csvFieldProperty) {
		toUpperCase(csvFieldProperty.name());
	}
	
	/**
	 * Apply the method {@link String#toUpperCase} to the field of type <code>FieldType.AN</code> represented by 
	 * the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property of the field to apply the upper case
	 * @param fieldOccur the field occur to get the field
	 */
	public void toUpperCase(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		toUpperCase(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Apply the method toRemoveAccents to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>csvFieldProperty</code> param
	 * <p>
	 * Every character with accent present in the value of the field of type <code>FieldType.AN</code>, is replaced with the relative
	 * character without accent. For example the character ï¿½ is replaced with the character a
	 * 
	 * @param csvFieldProperty the field property of the field to removing accents
	 */
	public void toRemoveAccents(CSVFieldProperty csvFieldProperty) {
		toRemoveAccents(csvFieldProperty.name());
	}
	
	/**
	 * Apply the method toRemoveAccents to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * <p>
	 * Every character with accent present in the value of the field of type <code>FieldType.AN</code>, is replaced with the relative
	 * character without accent. For example the character ï¿½ is replaced with the character a
	 * 
	 * @param csvFieldProperty the field property of the field to removing accents
	 * @param fieldOccur the field occur to get the field
	 */
	public void toRemoveAccents(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		toRemoveAccents(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Apply the encoding with the Charset "US-ASCII" to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>csvFieldProperty</code> param
	 * <p>
	 * Every character out of the Charset "US-ASCII" present in the value of the field of type <code>FieldType.AN</code>, 
	 * is replaced with the character ?. For example the character ï¿½ is replaced with the character ?
	 * 
	 * @param csvFieldProperty the field property of the field to apply the encoding
	 */
	public void toAscii(CSVFieldProperty csvFieldProperty) {
		toAscii(csvFieldProperty.name());
	}
	
	/**
	 * Apply the encoding with the Charset "US-ASCII" to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * <p>
	 * Every character out of the Charset "US-ASCII" present in the value of the field of type <code>FieldType.AN</code>, 
	 * is replaced with the character ?. For example the character ï¿½ is replaced with the character ?
	 * 
	 * @param csvFieldProperty the field property of the field to apply the encoding
	 * @param fieldOccur the field occur to get the field
	 */
	public void toAscii(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		toAscii(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Applay toUpperCase, toRemoveAccents and toAscii to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property of the field to apply the normalize
	 */
	public void toNormalize(CSVFieldProperty csvFieldProperty) {
		toNormalize(csvFieldProperty.name());
	}
	
	/**
	 * Applay toUpperCase, toRemoveAccents and toAscii to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property of the field to apply the normalize
	 * @param fieldOccur the field occur to get the field
	 */
	public void toNormalize(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		toNormalize(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the validion info of the field represented by the <code>csvFieldProperty</code> param
	 * 
	 * @param csvFieldProperty the field property of the field to return the validation info
	 * @return the validation info of the field represented by the <code>csvFieldProperty</code> param
	 */
	public FieldValidationInfo getRecordFieldValidationInfo(CSVFieldProperty csvFieldProperty) {
		return getRecordFieldValidationInfo(csvFieldProperty.name());
	}
	
	/**
	 * Returns the validion info of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param csvFieldProperty the field property of the field to return the validation info
	 * @param fieldOccur the field occur to get the field
	 * @return the validation info of the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params
	 */
	public FieldValidationInfo getRecordFieldValidationInfo(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return getRecordFieldValidationInfo(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns true if the field represented by the <code>csvFieldProperty</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param csvFieldProperty the field property of the field to check the validation status
	 * @return true if the field represented by the <code>csvFieldProperty</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isErrorStatus(CSVFieldProperty csvFieldProperty) {
		return isErrorStatus(csvFieldProperty.name());
	}
	
	/**
	 * Returns true if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param csvFieldProperty the field property of the field to check the validation status
	 * @param fieldOccur the field occur to get the field
	 * @return true if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isErrorStatus(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isErrorStatus(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns true if the field represented by the <code>csvFieldProperty</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 * 
	 * @param csvFieldProperty the field property of the field to check the validation status
	 * @return true if the field represented by the <code>csvFieldProperty</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 */
	public boolean isWarnStatus(CSVFieldProperty csvFieldProperty) {
		return isWarnStatus(csvFieldProperty.name());
	}
	
	/**
	 * Returns true if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 * 
	 * @param csvFieldProperty the field property of the field to check the validation status
	 * @param fieldOccur the field occur to get the field
	 * @return true if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 */
	public boolean isWarnStatus(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return isWarnStatus(csvFieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns true if the field represented by the <code>csvFieldProperty</code> param has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param csvFieldProperty the field property of the field to check the validation status
	 * @return true if the field represented by the <code>csvFieldProperty</code> param has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isInfoStatus(CSVFieldProperty csvFieldProperty) {
		return !isErrorStatus(csvFieldProperty) && !isWarnStatus(csvFieldProperty);
	}
	
	/**
	 * Returns true if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param csvFieldProperty the field property of the field to check the validation status
	 * @param fieldOccur the field occur to get the field
	 * @return true if the field represented by the <code>csvFieldProperty</code> and <code>fieldOccur</code> params has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isInfoStatus(CSVFieldProperty csvFieldProperty, int fieldOccur) {
		return !isErrorStatus(csvFieldProperty, fieldOccur) && !isWarnStatus(csvFieldProperty, fieldOccur);
	}
	
	/**
	 * Init the fields map
	 * 
	 * @throws RecordException if a field has the name equals to "finalFiller" 
	 * @throws FieldException if the properties of the fields are in some non valid status
	 */
	protected void initFieldsMap() throws RecordException, FieldException {
		for(CSVFieldProperty p : fields.getEnumConstants()) {
			if (FINAL_FILLER_NAME.equals(p.name())) {
				throw new RecordException(ErrorCode.RE18, "The field name=[" + FINAL_FILLER_NAME + "] is reserved");
			}
			
			List<FieldExtendedProperty> eps = normalizeFieldExtendedProperties(p.fieldExtendedProperties());
			
			int fieldOccurs = p.fieldOccurs();
			if (fieldOccurs < 1) {
				throw new RecordException(ErrorCode.RE28, "The occurs of the CSV field " + p.name() + " must be greater than zero");
			}
			
			FieldType fieldType = p.fieldType();
			if (!FieldType.AN.equals(fieldType) && !FieldType.N.equals(fieldType)) {
				throw new RecordException(ErrorCode.RE32, "The fiel type of the field " + p.name() + " must be equals to " + 
						FieldType.AN.name() + " or " + FieldType.N.name() );
			}
			
			for (int fieldOccur = 1; fieldOccur <= fieldOccurs; fieldOccur++) {
				fieldsMap.put(keyForFieldNameAndFieldOccur(p.name(), fieldOccur), new Field(p.name(), ((Enum<?>)p).ordinal() + 1, 1, fieldOccur, 
					fieldType, 0, p.fieldMandatory(), recordWay, p.fieldDefaultValue(), null, eps, p.fieldDisplayName(), p.fieldDescription()));
			}
        }
	}
	
	/**
	 * CSV Record has not final filler
	 */
	protected void addFiller() {
		// NOT FOR CSV RECORD
	}
	
	/**
	 * Apply to the fields the formatted values present in the <code>csvRecord</code> parameter
	 * 
	 * @param csvRecord the formatted string
	 * @throws RecordException if the <code>csvRecord</code> is malformed
	 */
	public void initRecord(String csvRecord) throws RecordException {
		doFill(csvRecord);
	}
	
	/**
	 * Fill the value of every field of this record with the formatted value presented in the <code>csvRecord</code> param
	 * 
	 * @param csvRecord the csvRecord with the formatted value to fill
	 */
	protected void doFill(String csvRecord) {
		initRecordCSV(csvRecord, recordSep, recordOtherSep, recordEnclosing);
	}
	
	/**
     * Returns a CSV <code>String</code> object representing this record. The returned string is composed with the formatted
     * unpadded value of every field of this record. Every value is separated with the <code>CSVSep</code> defined for this CSV record.
     * If <code>encloseAllFields</code> defined for this CSV record is true, every field of the record is enclosed with the <code>enclosing</code>
     * defined for this CSV record. If the <code>encloseAllFields</code> is false the fields aren't enclosing, 
     * eccept they contain the char sep. Each of the embedded char enclosing characters is represented by a pair of 
     * double-enclosing characters.
     *
     * @return  a CSV string representation of this record
     * @throws RecordException it the status of this record is ERROR
     */
	@Override
	public String toString() throws RecordException {
		return toStringCSV();
	}
	
	/**
     * Returns a CSV <code>String</code> object representing this record. The returned string is composed with the formatted
     * unpadded value of every field of this record. Every value is separated with the <code>CSVSep</code> defined for this CSV record.
     * If <code>encloseAllFields</code> defined for this CSV record is true, every field of the record is enclosed with the <code>enclosing</code>
     * defined for this CSV record. If the <code>encloseAllFields</code> is false the fields aren't enclosing, 
     * eccept they contain the char sep. Each of the embedded char enclosing characters is represented by a pair of 
     * double-enclosing characters.
     *
     * @return  a CSV string representation of this record
     * @throws RecordException it the status of this record is ERROR
     */
	@Override
	public String toStringCSV() throws RecordException {
		return toStringCSV(recordSep, recordOtherSep, recordEnclosing, recordEncloseAllFields);
	}
	
	/**
	 * @return the record len
	 */
	public int getRecordLen() {
		return toString().length();
	}

	/**
	 * @return true if the instance of this record is a CSV record
	 */
	@Override
	protected boolean isCSVRecord() {
		return true;
	}
}
