package com.github.parmag.fixefid.record;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.parmag.fixefid.record.field.Field;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;
import com.github.parmag.fixefid.record.field.FieldValidationInfo;

/**
 * The <code>Record</code> represents a fixed fields formatted text.
 * 
 * @author Giancarlo Parma
 *
 * 
 * @since 1.0
 */
public class Record<T extends Enum<T> & FieldProperty> extends AbstractRecord {
	private Class<T> fields; 
	
	/**
	 * Constructs a new <code>Record</code> that represents the fixed fields indicated by the <code>fields</code> parameter.
	 * The record way is <code>RecordWay.IN</code>. The lenght of this record is calculated with the sum of the lenght of every
	 * field. The value of every field is initialized with a default value (if present)
	 * 
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 */
	public Record(Class<T> fields) {
		this(RecordWay.IN, null, fields, null, null);
	}
	
	/**
	 * Constructs a new <code>Record</code> that represents the fixed fields indicated by the <code>fields</code> parameter.
	 * The record way is <code>RecordWay.IN</code>. The lenght of this record is calculated with the sum of the lenght of every
	 * field. The value of every field is initialized with the formatted value present in the relative position of the <code>record</code> 
	 * parameter
	 * 
	 * @param record the formatted string of this <code>Record</code>
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 */
	public Record(String record, Class<T> fields) {
		this(RecordWay.IN, record, fields, null, null);
	}
	
	/**
	 * Constructs a new <code>Record</code> that represents the fixed fields indicated by the <code>fields</code> parameter.
	 * The record way is <code>RecordWay.IN</code>. The length of the record is initialized with the value present in the
	 * <code>recordLen</code> parameter. The value of every field is initialized with a default value (if present)
	 * 
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 * @param recordLen the lenght of this <code>Record</code>
	 */
	public Record(Class<T> fields, Integer recordLen) {
		this(RecordWay.IN, null, fields, recordLen, null);
	}
	
	/**
	 * Constructs a new <code>Record</code> that represents the fixed fields indicated by the <code>fields</code> parameter.
	 * The record way is <code>RecordWay.IN</code>. The value of every field is initialized with the value present in the
	 * relative position of the <code>record</code> parameter. The length of the record is initialized with the value present in the
	 * <code>recordLen</code> parameter
	 * 
	 * @param record the formatted string of this <code>Record</code>
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 * @param recordLen the lenght of this <code>Record</code>
	 */
	public Record(String record, Class<T> fields, Integer recordLen) {
		this(RecordWay.IN, record, fields, recordLen, null);
	}
	
	/**
	 * Constructs a new <code>Record</code> that represents the fixed fields indicated by the <code>fields</code> parameter.
	 * The record way is initialized with the value of the <code>recordWay</code> parameter. The lenght of this record is 
	 * calculated with the sum of the lenght of every field. The value of every field is initialized with a default value (if present)
	 * 
	 * @param recordWay the way of this record
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 */
	public Record(RecordWay recordWay, Class<T> fields) {
		this(recordWay, null, fields, null, null);
	}
	
	/**
	 * Constructs a new <code>Record</code> that represents the fixed fields indicated by the <code>fields</code> parameter.
	 * The record way is initialized with the value of the <code>recordWay</code> parameter. The lenght of this record is 
	 * calculated with the sum of the lenght of every field. The value of every field is initialized with the formatted value present in the
	 * relative position of the <code>record</code> parameter. 
	 * 
	 * @param recordWay the way of this record
	 * @param record the formatted string of this <code>Record</code>
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 */
	public Record(RecordWay recordWay, String record, Class<T> fields) {
		this(recordWay, record, fields, null, null);
	}
	
	/**
	 * Constructs a new <code>Record</code> that represents the fixed fields indicated by the <code>fields</code> parameter.
	 * The record way is initialized with the value of the <code>recordWay</code> parameter. The length of the record is initialized 
	 * with the value present in the <code>recordLen</code> parameter. The value of every field is initialized with a default value 
	 * (if present)
	 * 
	 * @param recordWay the way of this record
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 * @param recordLen the lenght of this <code>Record</code>
	 */
	public Record(RecordWay recordWay, Class<T> fields, Integer recordLen) {
		this(recordWay, null, fields, recordLen, null);
	}
	
	/**
	 * Constructs a new <code>Record</code> that represents the fixed fields indicated by the <code>fields</code> parameter.
	 * The record way is initialized with the value of the <code>recordWay</code> parameter. The length of the record is initialized 
	 * with the value present in the <code>recordLen</code> parameter. The value of every field is initialized with the formatted value present in the
	 * relative position of the <code>record</code> parameter. To every field of the record are applied the extended properties
	 * present in the <code>fieldExtendedProperties</code> parameter. Only the following properties are permitted at record level:
	 * <ul>
	 * <li><code>FieldExtendedPropertyType.LPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.RPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.VALIDATOR</code></li>
	 * </ul>
	 * 
	 * @param recordWay the way of this record
	 * @param record the formatted string of this <code>Record</code>
	 * @param fields the <code>fields</code> of this <code>Record</code>
	 * @param recordLen the lenght of this <code>Record</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 */
	public Record(RecordWay recordWay, String record, Class<T> fields, Integer recordLen, 
			List<FieldExtendedProperty> fieldExtendedProperties) {
		this.fields = fields;
		
		initFieldExtendedProperties(fieldExtendedProperties);
		
		if (recordLen == null) {
			recordLen = retrieveRecordLen();
		}
		this.recordLen = recordLen;
		this.recordWay = recordWay;
		initFieldsMap(); 
		addFiller();
		if (record != null) {
			initRecord(record);
		}
	}
	
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is mandatory
	 * 
	 * @param fieldProperty the field property to know if the relative field is mandatory
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is mandatory
	 */
	public boolean isMandatory(FieldProperty fieldProperty) {
		return isMandatory(fieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is mandatory
	 * 
	 * @param fieldProperty the field property to know if the relative field is mandatory
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is mandatory
	 */
	public boolean isMandatory(FieldProperty fieldProperty, int fieldOccur) {
		return isMandatory(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is a <code>String</code>.
	 * A field is a <code>String</code> if is of type <code>FieldType.AN</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>String</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is a <code>String</code>
	 */
	public boolean isString(FieldProperty fieldProperty) {
		return isString(fieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is a <code>String</code>.
	 * A field is a <code>String</code> if is of type <code>FieldType.AN</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>String</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is a <code>String</code>
	 */
	public boolean isString(FieldProperty fieldProperty, int fieldOccur) {
		return isString(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Long</code>.
	 * A field is a <code>Long</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &ge; 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Long</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Long</code>
	 */
	public boolean isLong(FieldProperty fieldProperty) {
		return isLong(fieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>Long</code>.
	 * A field is a <code>Long</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &ge; 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Long</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>Long</code>
	 */
	public boolean isLong(FieldProperty fieldProperty, int fieldOccur) {
		return isLong(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Integer</code>.
	 * A field is a <code>Integer</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &lt; 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Integer</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Integer</code>
	 */
	public boolean isInteger(FieldProperty fieldProperty) {
		return isInteger(fieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>Integer</code>.
	 * A field is a <code>Integer</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &lt; 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Integer</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>Integer</code>
	 */
	public boolean isInteger(FieldProperty fieldProperty, int fieldOccur) {
		return isInteger(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is a <code>Date</code>.
	 * A field is a <code>Date</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.DATE_FORMAT</code> is present.
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Date</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is a <code>Date</code>
	 */
	public boolean isDate(FieldProperty fieldProperty) {
		return isDate(fieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is a <code>Date</code>.
	 * A field is a <code>Date</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.DATE_FORMAT</code> is present.
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Date</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is a <code>Date</code>
	 */
	public boolean isDate(FieldProperty fieldProperty, int fieldOccur) {
		return isDate(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is a <code>Boolean</code>.
	 * A field is a <code>Boolean</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code> is present.
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Boolean</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is a <code>Boolean</code>
	 */
	public boolean isBoolean(FieldProperty fieldProperty) {
		return isBoolean(fieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is a <code>Boolean</code>.
	 * A field is a <code>Boolean</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code> is present.
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Boolean</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is a <code>Boolean</code>
	 */
	public boolean isBoolean(FieldProperty fieldProperty, int fieldOccur) {
		return isBoolean(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &ge; 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Double</code>
	 */
	public boolean isDouble(FieldProperty fieldProperty) {
		return isDouble(fieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &ge; 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>Double</code>
	 */
	public boolean isDouble(FieldProperty fieldProperty, int fieldOccur) {
		return isDouble(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &lt; 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Double</code>
	 */
	public boolean isFloat(FieldProperty fieldProperty) {
		return isFloat(fieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &lt; 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>Double</code>
	 */
	public boolean isFloat(FieldProperty fieldProperty, int fieldOccur) {
		return isFloat(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>BigDecimal</code>.
	 * A field is a <code>BigDecimal</code> if is <code>Double</code> or a <code>Float</code>.
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>BigDecimal</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>BigDecimal</code>
	 */
	public boolean isBigDecimal(FieldProperty fieldProperty) {
		return isBigDecimal(fieldProperty.name());
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>BigDecimal</code>.
	 * A field is a <code>BigDecimal</code> if is <code>Double</code> or a <code>Float</code>.
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>BigDecimal</code>
	 * @param fieldOccur the field occur to get the field
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params is <code>BigDecimal</code>
	 */
	public boolean isBigDecimal(FieldProperty fieldProperty, int fieldOccur) {
		return isBigDecimal(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the formatted value of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the formatted value of the relative field
	 * @return the formatted value of the field represented by the <code>fieldProperty</code> param
	 */
	public String getValue(FieldProperty fieldProperty) {
		return getValue(fieldProperty.name());
	}
	
	/**
	 * Returns the formatted value of the field represented by the <code>fieldProperty</code>
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property to get the formatted value of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the formatted value of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 */
	public String getValue(FieldProperty fieldProperty, int fieldOccur) {
		return getValue(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>String</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>String</code> of the relative field
	 * @return the value as <code>String</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a String
	 */
	public String getValueAsString(FieldProperty fieldProperty) throws FieldException {
		return getValueAsString(fieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>String</code> of the field represented by the <code>fieldProperty</code>
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property to get the value as <code>String</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>String</code> of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a String
	 */
	public String getValueAsString(FieldProperty fieldProperty, int fieldOccur) throws FieldException {
		return getValueAsString(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Long</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Long</code> of the relative field
	 * @return the value as <code>Long</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Long
	 */
	public Long getValueAsLong(FieldProperty fieldProperty) throws FieldException {
		return getValueAsLong(fieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Long</code> of the field represented by the <code>fieldProperty</code>
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property to get the value as <code>Long</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Long</code> of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Long
	 */
	public Long getValueAsLong(FieldProperty fieldProperty, int fieldOccur) throws FieldException {
		return getValueAsLong(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Integer</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Integer</code> of the relative field
	 * @return the value as <code>Integer</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Integer
	 */
	public Integer getValueAsInteger(FieldProperty fieldProperty) throws FieldException {
		return getValueAsInteger(fieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Integer</code> of the field represented by the <code>fieldProperty</code>
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property to get the value as <code>Integer</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Integer</code> of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Integer
	 */
	public Integer getValueAsInteger(FieldProperty fieldProperty, int fieldOccur) throws FieldException {
		return getValueAsInteger(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Double</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Double</code> of the relative field
	 * @return the value as <code>Double</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Double
	 */
	public Double getValueAsDouble(FieldProperty fieldProperty) throws FieldException {
		return getValueAsDouble(fieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Double</code> of the field represented by the <code>fieldProperty</code>
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property to get the value as <code>Double</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Double</code> of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Double
	 */
	public Double getValueAsDouble(FieldProperty fieldProperty, int fieldOccur) throws FieldException {
		return getValueAsDouble(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Float</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Float</code> of the relative field
	 * @return the value as <code>Float</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Float
	 */
	public Float getValueAsFloat(FieldProperty fieldProperty) throws FieldException {
		return getValueAsFloat(fieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Float</code> of the field represented by the <code>fieldProperty</code>
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property to get the value as <code>Float</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Float</code> of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Float
	 */
	public Float getValueAsFloat(FieldProperty fieldProperty, int fieldOccur) throws FieldException {
		return getValueAsFloat(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>BigDecimal</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>BigDecimal</code> of the relative field
	 * @return the value as <code>BigDecimal</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public BigDecimal getValueAsBigDecimal(FieldProperty fieldProperty) throws FieldException {
		return getValueAsBigDecimal(fieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>BigDecimal</code> of the field represented by the <code>fieldProperty</code>
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property to get the value as <code>BigDecimal</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>BigDecimal</code> of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public BigDecimal getValueAsBigDecimal(FieldProperty fieldProperty, int fieldOccur) throws FieldException {
		return getValueAsBigDecimal(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Date</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Date</code> of the relative field
	 * @return the value as <code>Date</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Date
	 */
	public Date getValueAsDate(FieldProperty fieldProperty) throws FieldException {
		return getValueAsDate(fieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Date</code> of the field represented by the <code>fieldProperty</code>
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property to get the value as <code>Date</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Date</code> of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Date
	 */
	public Date getValueAsDate(FieldProperty fieldProperty, int fieldOccur) throws FieldException {
		return getValueAsDate(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the value as <code>Boolean</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Boolean</code> of the relative field
	 * @return the value as <code>Boolean</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Boolean
	 */
	public Boolean getValueAsBoolean(FieldProperty fieldProperty) throws FieldException {
		return getValueAsBoolean(fieldProperty.name());
	}
	
	/**
	 * Returns the value as <code>Boolean</code> of the field represented by the <code>fieldProperty</code>
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property to get the value as <code>Boolean</code> of the relative field
	 * @param fieldOccur the field occur to get the field
	 * @return the value as <code>Boolean</code> of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws FieldException if the field is not a Boolean
	 */
	public Boolean getValueAsBoolean(FieldProperty fieldProperty, int fieldOccur) throws FieldException {
		return getValueAsBoolean(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 */
	public void setValue(FieldProperty fieldProperty, String value) {
		setValue(fieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 */
	public void setValue(FieldProperty fieldProperty,String value,  int fieldOccur) {
		setValue(fieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param. 
	 * 
	 * @param fieldProperty the field proerty of the field to set the specified value
	 * @param value the value to set
	 * @param truncate If the <code>truncate</code> param is <code>true</code> and the len of the specified value is greater than the len of the 
	 * field, the specified value will be truncated at the len od the field. 
	 * @throws RecordException If the <code>truncate</code> param is <code>false</code> and the len of the specified value is 
	 * greater than the len of the field
	 */
	public void setValue(FieldProperty fieldProperty, String value, boolean truncate) throws RecordException {
		setValue(fieldProperty.name(), value, truncate);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field proerty of the field to set the specified value
	 * @param value the value to set
	 * @param truncate If the <code>truncate</code> param is <code>true</code> and the len of the specified value is greater than the len of the 
	 * field, the specified value will be truncated at the len od the field. 
	 * @param fieldOccur the field occur to get the field
	 * @throws RecordException If the <code>truncate</code> param is <code>false</code> and the len of the specified value is 
	 * greater than the len of the field
	 */
	public void setValue(FieldProperty fieldProperty, String value, boolean truncate, int fieldOccur) throws RecordException {
		setValue(fieldProperty.name(), value, truncate, fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(FieldProperty fieldProperty, Long value) throws FieldException {
		setValue(fieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(FieldProperty fieldProperty, Long value, int fieldOccur) throws FieldException {
		setValue(fieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(FieldProperty fieldProperty, Integer value) throws FieldException {
		setValue(fieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(FieldProperty fieldProperty, Integer value, int fieldOccur) throws FieldException {
		setValue(fieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(FieldProperty fieldProperty, Double value) throws FieldException {
		setValue(fieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(FieldProperty fieldProperty, Double value, int fieldOccur) throws FieldException {
		setValue(fieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(FieldProperty fieldProperty, Float value) throws FieldException {
		setValue(fieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(FieldProperty fieldProperty, Float value, int fieldOccur) throws FieldException {
		setValue(fieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(FieldProperty fieldProperty, BigDecimal value) throws FieldException {
		setValue(fieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(FieldProperty fieldProperty, BigDecimal value, int fieldOccur) throws FieldException {
		setValue(fieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(FieldProperty fieldProperty, Date value) throws FieldException {
		setValue(fieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(FieldProperty fieldProperty, Date value, int fieldOccur) throws FieldException {
		setValue(fieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(FieldProperty fieldProperty, Boolean value) throws FieldException {
		setValue(fieldProperty.name(), value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(FieldProperty fieldProperty, Boolean value, int fieldOccur) throws FieldException {
		setValue(fieldProperty.name(), value, fieldOccur); 
	}
	
	/**
	 * Returns the field represented by the <code>fieldProperty</code> param
	 *  
	 * @param fieldProperty the field property to get the field
	 * @return the field represented by the <code>fieldProperty</code> param
	 * @throws RecordException if the <code>fieldProperty</code> param doesn't represent any field of the record
	 */
	protected Field getRecordField(FieldProperty fieldProperty) throws RecordException {
		return getRecordField(fieldProperty.name());
	}
	
	/**
	 * Returns the field represented by the <code>fieldProperty</code> param
	 *  
	 * @param fieldProperty the field property to get the field
	 * @param fieldOccur the field occur to get the field
	 * @return the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * @throws RecordException if the <code>fieldProperty</code> param doesn't represent any field of the record
	 */
	protected Field getRecordField(FieldProperty fieldProperty, int fieldOccur) throws RecordException {
		return getRecordField(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the len of the record represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the property of the field to know the len
	 * @return the len of the record represented by the <code>fieldProperty</code> param
	 */
	public int getFieldLen(FieldProperty fieldProperty) {
		return getFieldLen(fieldProperty.name());
	}
	
	/**
	 * Returns the len of the record represented by the <code>fieldProperty</code> 
	 * and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the property of the field to know the len
	 * @param fieldOccur the field occur to get the field
	 * @return the len of the record represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 */
	public int getFieldLen(FieldProperty fieldProperty, int fieldOccur) {
		return getFieldLen(fieldProperty.name(), fieldOccur);
	}

	/**
	 * Apply the method {@link String#toUpperCase} to the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property of the field to apply the upper case
	 */
	public void toUpperCase(FieldProperty fieldProperty) {
		toUpperCase(fieldProperty.name());
	}
	
	/**
	 * Apply the method {@link String#toUpperCase} to the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property of the field to apply the upper case
	 * @param fieldOccur the field occur to get the field
	 */
	public void toUpperCase(FieldProperty fieldProperty, int fieldOccur) {
		toUpperCase(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Apply the method toRemoveAccents to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> param
	 * <p>
	 * Every character with accent present in the value of the field of type <code>FieldType.AN</code>, is replaced with the relative
	 * character without accent. For example the character ï¿½ is replaced with the character a
	 * 
	 * @param fieldProperty the field property of the field to removing accents
	 */
	public void toRemoveAccents(FieldProperty fieldProperty) {
		toRemoveAccents(fieldProperty.name());
	}
	
	/**
	 * Apply the method toRemoveAccents to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * <p>
	 * Every character with accent present in the value of the field of type <code>FieldType.AN</code>, is replaced with the relative
	 * character without accent. For example the character ï¿½ is replaced with the character a
	 * 
	 * @param fieldProperty the field property of the field to removing accents
	 * @param fieldOccur the field occur to get the field
	 */
	public void toRemoveAccents(FieldProperty fieldProperty, int fieldOccur) {
		toRemoveAccents(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Apply the encoding with the Charset "US-ASCII" to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> param
	 * <p>
	 * Every character out of the Charset "US-ASCII" present in the value of the field of type <code>FieldType.AN</code>, 
	 * is replaced with the character ?. For example the character ï¿½ is replaced with the character ?
	 * 
	 * @param fieldProperty the field property of the field to apply the encoding
	 */
	public void toAscii(FieldProperty fieldProperty) {
		toAscii(fieldProperty.name());
	}
	
	/**
	 * Apply the encoding with the Charset "US-ASCII" to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * <p>
	 * Every character out of the Charset "US-ASCII" present in the value of the field of type <code>FieldType.AN</code>, 
	 * is replaced with the character ?. For example the character ï¿½ is replaced with the character ?
	 * 
	 * @param fieldProperty the field property of the field to apply the encoding
	 * @param fieldOccur the field occur to get the field
	 */
	public void toAscii(FieldProperty fieldProperty, int fieldOccur) {
		toAscii(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Applay toUpperCase, toRemoveAccents and toAscii to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property of the field to apply the normalize
	 */
	public void toNormalize(FieldProperty fieldProperty) {
		toNormalize(fieldProperty.name());
	}
	
	/**
	 * Applay toUpperCase, toRemoveAccents and toAscii to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 * 
	 * @param fieldProperty the field property of the field to apply the normalize
	 * @param fieldOccur the field occur to get the field
	 */
	public void toNormalize(FieldProperty fieldProperty, int fieldOccur) {
		toNormalize(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns the validion info of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property of the field to return the validation info
	 * @return the validation info of the field represented by the <code>fieldProperty</code> param
	 */
	public FieldValidationInfo getRecordFieldValidationInfo(FieldProperty fieldProperty) {
		return getRecordFieldValidationInfo(fieldProperty.name());
	}
	
	/**
	 * Returns the validion info of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property of the field to return the validation info
	 * @param fieldOccur the field occur to get the field
	 * @return the validation info of the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params
	 */
	public FieldValidationInfo getRecordFieldValidationInfo(FieldProperty fieldProperty, int fieldOccur) {
		return getRecordFieldValidationInfo(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns true if the field represented by the <code>fieldProperty</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param fieldProperty the field property of the field to check the validation status
	 * @return true if the field represented by the <code>fieldProperty</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isErrorStatus(FieldProperty fieldProperty) {
		return isErrorStatus(fieldProperty.name());
	}
	
	/**
	 * Returns true if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param fieldProperty the field property of the field to check the validation status
	 * @param fieldOccur the field occur to get the field
	 * @return true if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isErrorStatus(FieldProperty fieldProperty, int fieldOccur) {
		return isErrorStatus(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns true if the field represented by the <code>fieldProperty</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 * 
	 * @param fieldProperty the field property of the field to check the validation status
	 * @return true if the field represented by the <code>fieldProperty</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 */
	public boolean isWarnStatus(FieldProperty fieldProperty) {
		return isWarnStatus(fieldProperty.name());
	}
	
	/**
	 * Returns true if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 * 
	 * @param fieldProperty the field property of the field to check the validation status
	 * @param fieldOccur the field occur to get the field
	 * @return true if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 */
	public boolean isWarnStatus(FieldProperty fieldProperty, int fieldOccur) {
		return isWarnStatus(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Returns true if the field represented by the <code>fieldProperty</code> param has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param fieldProperty the field property of the field to check the validation status
	 * @return true if the field represented by the <code>fieldProperty</code> param has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isInfoStatus(FieldProperty fieldProperty) {
		return isInfoStatus(fieldProperty.name());
	}
	
	/**
	 * Returns true if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param fieldProperty the field property of the field to check the validation status
	 * @param fieldOccur the field occur to get the field
	 * @return true if the field represented by the <code>fieldProperty</code> and <code>fieldOccur</code> params has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isInfoStatus(FieldProperty fieldProperty, int fieldOccur) {
		return isInfoStatus(fieldProperty.name(), fieldOccur);
	}
	
	/**
	 * Init the fields map
	 * 
	 * @throws RecordException if a field has the name equals to "finalFiller" 
	 * @throws FieldException if the properties of the fields are in some non valid status
	 */
	protected void initFieldsMap() throws RecordException, FieldException {
		for(FieldProperty p : fields.getEnumConstants()) {
			if (FINAL_FILLER_NAME.equals(p.name())) {
				throw new RecordException(ErrorCode.RE18, "The field name=[" + FINAL_FILLER_NAME + "] is reserved");
			}
			
			List<FieldExtendedProperty> eps = normalizeFieldExtendedProperties(p.fieldExtendedProperties());
			
			int fieldLen = p.fieldLen();
			if (fieldLen == 0) {
				throw new RecordException(ErrorCode.RE26, "The len of the field " + p.name() + " must be greater than zero");
			}
			
			int fieldOccurs = p.fieldOccurs();
			if (fieldOccurs < 1) {
				throw new RecordException(ErrorCode.RE27, "The occurs of the field " + p.name() + " must be greater than zero");
			}
			
			FieldType fieldType = p.fieldType();
			if (!FieldType.AN.equals(fieldType) && !FieldType.N.equals(fieldType)) {
				throw new RecordException(ErrorCode.RE31, "The fiel type of the field " + p.name() + " must be equals to " + 
						FieldType.AN.name() + " or " + FieldType.N.name() );
			}
			
			for (int fieldOccur = 1; fieldOccur <= fieldOccurs; fieldOccur++) {
				fieldsMap.put(keyForFieldNameAndFieldOccur(p.name(), fieldOccur), new Field(p.name(), ((Enum<?>)p).ordinal() + 1, 1, fieldOccur, 
					fieldType, fieldLen, p.fieldMandatory(), recordWay, p.fieldDefaultValue(), null, eps, p.fieldDisplayName(), p.fieldDescription()));
			}
        }
	}
	
	private int retrieveRecordLen() {
		int recordLen = 0;
		for(FieldProperty p : fields.getEnumConstants()) {
			int fieldLen = p.fieldLen();
			int fieldOccurs = p.fieldOccurs();
			recordLen += (fieldLen * fieldOccurs);
		}
		
		return recordLen;
	}

	/**
	 * @return true if the instance of this record is a CSV record
	 */
	@Override
	protected boolean isCSVRecord() {
		return false;
	}
}
