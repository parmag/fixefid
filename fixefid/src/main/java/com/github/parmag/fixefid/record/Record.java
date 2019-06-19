package com.github.parmag.fixefid.record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.parmag.fixefid.record.field.Field;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;
import com.github.parmag.fixefid.record.field.FieldValidationInfo;

/**
 * The <code>Record</code> represents a fixed fields formatted text.
 * 
 * @author Giancarlo Parma
 *
 * @param <T>
 * 
 * @since 1.0
 */
public class Record<T extends Enum<T> & FieldProperty> {
	private static final String FINAL_FILLER_NAME = "finalFiller";
	private static final String NO_VALIDATION_INFO = "NO VALIDATION INFO";
	
	private Class<T> fields; 
	
	private final Map<String, Field> fieldsMap = new LinkedHashMap<String, Field>();
	private RecordWay recordWay; 
	private int recordLen;
	private List<FieldExtendedProperty> fieldExtendedProperties;
	
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
		
		if (fieldExtendedProperties != null) {
			for (FieldExtendedProperty fep : fieldExtendedProperties) {
				FieldExtendedPropertyType repType = fep.getType();
				if (!(FieldExtendedPropertyType.LPAD.equals(repType) ||
						FieldExtendedPropertyType.RPAD.equals(repType) ||
						FieldExtendedPropertyType.VALIDATOR.equals(repType))) {
					throw new RecordException("Field Extended Property=[" + repType.name() + " not valid at record level.");
				}
			}
			
			this.fieldExtendedProperties = fieldExtendedProperties;
		} 
		
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
	 * Apply to the fields the formatted values present in the <code>record</code> parameter
	 * 
	 * @param record the formatted string
	 * @throws RecordException if the formatted string obtained from <code>toString</code> method diff from the <code>record</code> parameter
	 */
	public void initRecord(String record) throws RecordException {
		if (record.length() > getRecordLen()) {
			doValidRecordLen(record, "input");
		} else if (record.length() < getRecordLen()) {
			record = String.format("%-" + getRecordLen() + "s", record);
		}
		
		doFill(record);
		
		String toStringRecord = toString();
		if (!toStringRecord.equals(record)) {
			throw new RecordException("Input record=[" + record + "] diff from toString record=[" + toStringRecord + "]");
		}
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is mandatory
	 * 
	 * @param fieldProperty the field property to know if the relative field is mandatory
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is mandatory
	 */
	public boolean isMandatory(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).isMandatory();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is a <code>String</code>.
	 * A field is a <code>String</code> if is of type <code>FieldType.AN</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>String</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is a <code>String</code>
	 */
	public boolean isString(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).isString();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Long</code>.
	 * A field is a <code>Long</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len >= 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Long</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Long</code>
	 */
	public boolean isLong(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).isLong();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Integer</code>.
	 * A field is a <code>Integer</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len < 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Integer</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Integer</code>
	 */
	public boolean isInteger(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).isInteger();
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
		return getRecordField(fieldProperty).isDate();
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
		return getRecordField(fieldProperty).isBoolean();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len >= 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Double</code>
	 */
	public boolean isDouble(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).isDouble();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len < 10</code>
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>Double</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>Double</code>
	 */
	public boolean isFloat(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).isFloat();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>BigDecimal</code>.
	 * A field is a <code>BigDecimal</code> if is <code>Double</code> or a <code>Float</code>.
	 * 
	 * @param fieldProperty the field property to know if the relative field is a <code>BigDecimal</code>
	 * @return <code>true</code> if the field represented by the <code>fieldProperty</code> param is <code>BigDecimal</code>
	 */
	public boolean isBigDecimal(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).isBigDecimal();
	}
	
	/**
	 * Returns the formatted value of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the formatted value of the relative field
	 * @return the formatted value of the field represented by the <code>fieldProperty</code> param
	 */
	public String getValue(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).getValue();
	}
	
	/**
	 * Returns the value as <code>String</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>String</code> of the relative field
	 * @return the value as <code>String</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a String
	 */
	public String getValueAsString(FieldProperty fieldProperty) throws FieldException {
		return getRecordField(fieldProperty).getValueAsString();
	}
	
	/**
	 * Returns the value as <code>Long</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Long</code> of the relative field
	 * @return the value as <code>Long</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Long
	 */
	public Long getValueAsLong(FieldProperty fieldProperty) throws FieldException {
		return getRecordField(fieldProperty).getValueAsLong();
	}
	
	/**
	 * Returns the value as <code>Integer</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Integer</code> of the relative field
	 * @return the value as <code>Integer</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Integer
	 */
	public Integer getValueAsInteger(FieldProperty fieldProperty) throws FieldException {
		return getRecordField(fieldProperty).getValueAsInteger();
	}
	
	/**
	 * Returns the value as <code>Double</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Double</code> of the relative field
	 * @return the value as <code>Double</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Double
	 */
	public Double getValueAsDouble(FieldProperty fieldProperty) throws FieldException {
		return getRecordField(fieldProperty).getValueAsDouble();
	}
	
	/**
	 * Returns the value as <code>Float</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Float</code> of the relative field
	 * @return the value as <code>Float</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Float
	 */
	public Float getValueAsFloat(FieldProperty fieldProperty) throws FieldException {
		return getRecordField(fieldProperty).getValueAsFloat();
	}
	
	/**
	 * Returns the value as <code>BigDecimal</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>BigDecimal</code> of the relative field
	 * @return the value as <code>BigDecimal</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public BigDecimal getValueAsBigDecimal(FieldProperty fieldProperty) throws FieldException {
		return getRecordField(fieldProperty).getValueAsBigDecimal();
	}
	
	/**
	 * Returns the value as <code>Date</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Date</code> of the relative field
	 * @return the value as <code>Date</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Date
	 */
	public Date getValueAsDate(FieldProperty fieldProperty) throws FieldException {
		return getRecordField(fieldProperty).getValueAsDate();
	}
	
	/**
	 * Returns the value as <code>Boolean</code> of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property to get the value as <code>Boolean</code> of the relative field
	 * @return the value as <code>Boolean</code> of the field represented by the <code>fieldProperty</code> param
	 * @throws FieldException if the field is not a Boolean
	 */
	public Boolean getValueAsBoolean(FieldProperty fieldProperty) throws FieldException {
		return getRecordField(fieldProperty).getValueAsBoolean();
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 */
	public void setValue(FieldProperty fieldProperty, String value) {
		getRecordField(fieldProperty).setValue(value, true); 
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
		int fieldLen = getFieldLen(fieldProperty);
		if (value != null && value.length() > fieldLen) { 
			if (truncate) {
				value = value.substring(0, fieldLen);
			} else {
				throw new RecordException("Cannot set value=[" + value + "] for field=[" + fieldProperty.name() + "]: not valid len (expected" + fieldLen + ")");
			} 
		}
		
		setValue(fieldProperty, value);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(FieldProperty fieldProperty, Long value) throws FieldException {
		getRecordField(fieldProperty).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(FieldProperty fieldProperty, Integer value) throws FieldException {
		getRecordField(fieldProperty).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(FieldProperty fieldProperty, Double value) throws FieldException {
		getRecordField(fieldProperty).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(FieldProperty fieldProperty, Float value) throws FieldException {
		getRecordField(fieldProperty).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(FieldProperty fieldProperty, BigDecimal value) throws FieldException {
		getRecordField(fieldProperty).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(FieldProperty fieldProperty, Date value) throws FieldException {
		getRecordField(fieldProperty).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(FieldProperty fieldProperty, Boolean value) throws FieldException {
		getRecordField(fieldProperty).setValue(value); 
	}
	
	/**
	 * Returns the field represented by the <code>fieldProperty</code> param
	 *  
	 * @param fieldProperty the field property to get the field
	 * @return the field represented by the <code>fieldProperty</code> param
	 * @throws RecordException if the <code>fieldProperty</code> param doesn't represent any field of the record
	 */
	public Field getRecordField(FieldProperty fieldProperty) throws RecordException {
		if (!fieldsMap.containsKey(fieldProperty.name())) {
			throw new RecordException("Unknown fieldName=[" + fieldProperty.name() + "]");
		}
		
		return fieldsMap.get(fieldProperty.name());
	}
	
	/**
	 * Returns the list of fields with status equals the <code>status</code> param
	 * 
	 * @param status the status of tthe fields to return 
	 * @return the list of fields with status equals the <code>status</code> param
	 */
	public List<Field> getRecordFields(FieldValidationInfo.RecordFieldValidationStatus status) {
		List<Field> result = new ArrayList<Field>();
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key);
			if (rf.getValidationInfo().getValidationStatus().equals(status)) {
				result.add(rf);
			}
		}
		
		return result;
	}
	
	/**
	 * Returns the list of fields of this record
	 *  
	 * @return the list of fields of this record
	 */
	public List<Field> getRecordFields() {
		List<Field> result = new ArrayList<Field>();
		for (String key : fieldsMap.keySet()) {
			result.add(fieldsMap.get(key));
		}
		
		return result;
	}
	
	/**
	 * Fill the value of every field of this record with the formatted value presented in the <code>record</code> param
	 * 
	 * @param record the record with the formatted value to fill
	 */
	protected void doFill(String record) {
		int index = 0;
		
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key);
			rf.setIndex(index); 
			rf.setValue(record.substring(index, index += rf.getLen()), false); 
		}
	}
	
	/**
	 * Valid the len of the <code>record</code> param. The len of the <code>record</code> param is valid if is equals the 
	 * len of this record
	 * 
	 * @param record the record to validate the len
	 * @param info only for log reading purpose
	 * @throws RecordException if the len of the <code>record</code> param is diff from the len of this record
	 */
	protected void doValidRecordLen(String record, String info) throws RecordException {
		int len = record != null ? record.length() : 0; 
		if (len != getRecordLen()) {
			throw new RecordException("Not valid len=[" + len + "] for " + info + " record=[" + record + 
				"]. Expected len=[" + getRecordLen() + "]");
		}
	}
	
	 /**
     * Returns a <code>String</code> object representing this record. The returned string is composed with the formatted
     * value of evry field of this record
     *
     * @return  a string representation of this record
     * @throws RecordException it the status of this record is ERROR
     */
	@Override
	public String toString() throws RecordException {
		if (isErrorStatus()) {
			throw new RecordException("Record has Error status. Cause: " + prettyPrintErrorValidationInfo());
		}
		
		StringBuilder sb = new StringBuilder();
		for (String key : fieldsMap.keySet()) {
			sb.append(fieldsMap.get(key).getValue());
		}
		String record = sb.toString();
		doValidRecordLen(record, "toString");
		
		return record;
	}
	
	/**
	 * Returns a <code>String</code> object representing the pretty print of this record.
	 * The pretty print is composed as following:
	 * <p>
	 * name=[index][offset][len][value][validation status][validation msg (if present)]
	 * 
	 * @return the pretty print of this record
	 */
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		int offset = 1;
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key);
			int len = rf.getLen(); 
			sb.append(key + "=[" + rf.getIndex() + "][" + offset + "][" + len + "][" + rf.getValue() + "]");
			FieldValidationInfo vi = rf.getValidationInfo();
			if (vi != null) {
				sb.append("[" + vi.getValidationStatus().name() + "][" + vi.getValidationMessage() + "]\n");
			} else {
				sb.append("[" + NO_VALIDATION_INFO + "]\n");
			}
			offset += len;
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a <code>String</code> object representing the pretty print offset of this record.
	 * The pretty print offset is composed as following:
	 * <p>
	 * name=[offset]
	 * 
	 * @return the pretty print offset of this record
	 */
	public String prettyPrintOffset() {
		StringBuilder sb = new StringBuilder();
		int offset = 1;
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key); 
			sb.append(key + "=[" + offset + "]\n");
			offset += rf.getLen();
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a <code>String</code> object representing the pretty print offset and len of this record.
	 * The pretty print is composed as following:
	 * <p>
	 * name=[offset][len]
	 * 
	 * @return the pretty print offset and len of this record
	 */
	public String prettyPrintOffsetAndLen() {
		StringBuilder sb = new StringBuilder();
		int offset = 1;
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key); 
			int len = rf.getLen(); 
			sb.append(key + "=[" + offset + "][" + len + "]\n");
			offset += len;
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a <code>String</code> object representing the pretty print index, offset and len of this record.
	 * The pretty print is composed as following:
	 * <p>
	 * name=[index][offset][len]
	 * 
	 * @return the pretty print index, offset and len of this record
	 */
	public String prettyPrintIndexAndOffsetAndLen() {
		StringBuilder sb = new StringBuilder();
		int offset = 1;
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key); 
			int len = rf.getLen(); 
			sb.append(key + "=[" + rf.getIndex() + "][" + offset + "][" + len + "]\n");
			offset += len;
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a <code>String</code> object representing the pretty print validation info of this record.
	 * The pretty print is composed as following:
	 * <p>
	 * name=[validation status][validation msg (if present)]
	 * 
	 * @return the pretty print validation info of this record
	 */
	public String prettyPrintValidationInfo() {
		StringBuilder sb = new StringBuilder();
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key); 
			FieldValidationInfo vi = rf.getValidationInfo();
			if (vi != null) {
				sb.append(key + "=[" + vi.getValidationStatus().name() + "][" + vi.getValidationMessage() + "]\n");
			} else {
				sb.append(key + "=[" + NO_VALIDATION_INFO + "]\n");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a <code>String</code> object representing the pretty print of the fields of this record which the validation
	 * info is WARN. The pretty print is composed as following:
	 * <p>
	 * name=[WARN][validation msg]
	 * 
	 * @return the pretty print of this record
	 */
	public String prettyPrintWarnValidationInfo() {
		StringBuilder sb = new StringBuilder();
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key); 
			FieldValidationInfo vi = rf.getValidationInfo();
			if (vi != null && FieldValidationInfo.RecordFieldValidationStatus.WARN.equals(vi.getValidationStatus())) {
				sb.append(key + "=[" + vi.getValidationStatus().name() + "][" + vi.getValidationMessage() + "]\n");
			} 
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a <code>String</code> object representing the pretty print of the fields of this record which the validation
	 * info is ERROR. The pretty print is composed as following:
	 * <p>
	 * name=[ERROR][validation msg]
	 * 
	 * @return the pretty print of this record
	 */
	public String prettyPrintErrorValidationInfo() {
		StringBuilder sb = new StringBuilder();
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key); 
			FieldValidationInfo vi = rf.getValidationInfo();
			if (vi != null && FieldValidationInfo.RecordFieldValidationStatus.ERROR.equals(vi.getValidationStatus())) {
				sb.append(key + "=[" + vi.getValidationStatus().name() + "][" + vi.getValidationMessage() + "]\n");
			} 
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a <code>String</code> object representing the pretty print of the fields of this record which the validation
	 * info is INFO. The pretty print is composed as following:
	 * <p>
	 * name=[INFO][validation msg]
	 * 
	 * @return the pretty print of this record
	 */
	public String prettyPrintInfoValidationInfo() {
		StringBuilder sb = new StringBuilder();
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key); 
			FieldValidationInfo vi = rf.getValidationInfo();
			if (vi != null && FieldValidationInfo.RecordFieldValidationStatus.INFO.equals(vi.getValidationStatus())) {
				sb.append(key + "=[" + vi.getValidationStatus().name() + "][" + vi.getValidationMessage() + "]\n");
			} 
		}
		
		return sb.toString();
	}
	
	/**
	 * Inserts if needed a filler at the end of this record with name=finalFiller in such a way the len of the record is respected
	 */
	protected void addFiller() {
		int fillerIndex = 0;
		for (String key : fieldsMap.keySet()) {
			fillerIndex += fieldsMap.get(key).getLen();
		}
		
		int fillerLen = getRecordLen() - fillerIndex;
		
		if (fillerLen > 0) {
			fieldsMap.put(FINAL_FILLER_NAME, new Field(FINAL_FILLER_NAME, fields.getEnumConstants().length + 1,
				FieldType.AN, fillerLen, FieldMandatory.NO, recordWay, null, null));
		}
	}
	
	/**
	 * @return the RecordWay of this record
	 */
	public RecordWay getRecordWay() {
		return recordWay;
	}
	
	/**
	 * Returns the len of the record represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the property of the field to know the len
	 * @return the len of the record represented by the <code>fieldProperty</code> param
	 */
	public int getFieldLen(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).getLen();
	}

	/**
	 * @return the fields map of this record. The fields map is as following:
	 * <p>
	 * name=Field
	 */
	protected Map<String, Field> getFieldsMap() {
		return fieldsMap;
	}
	
	/**
	 * Apply the method {@link String#toUpperCase} to the value of every field of type <code>FieldType.AN</code> of this record
	 */
	public void toUpperCase() {
		for (String key : fieldsMap.keySet()) {
			fieldsMap.get(key).toUpperCase();
		}
	}
	
	/**
	 * Apply the method {@link String#toUpperCase} to the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property of the field to apply the upper case
	 */
	public void toUpperCase(FieldProperty fieldProperty) {
		getRecordField(fieldProperty).toUpperCase();
	}
	
	/**
	 * Apply the method toRemoveAccents to the value of every field of type <code>FieldType.AN</code> of this record.
	 * <p>
	 * Every character with accent present in the value of a field of type <code>FieldType.AN</code>, is replaced with the relative
	 * character without accent. For example the character � is replaced with the character a
	 */
	public void toRemoveAccents() {
		for (String key : fieldsMap.keySet()) {
			fieldsMap.get(key).toRemoveAccents();
		}
	}
	
	/**
	 * Apply the method toRemoveAccents to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> param
	 * <p>
	 * Every character with accent present in the value of the field of type <code>FieldType.AN</code>, is replaced with the relative
	 * character without accent. For example the character � is replaced with the character a
	 * 
	 * @param fieldProperty the field property of the field to removing accents
	 */
	public void toRemoveAccents(FieldProperty fieldProperty) {
		getRecordField(fieldProperty).toRemoveAccents();
	}
	
	/**
	 * Apply the encoding with the Charset "US-ASCII" to the value of every field of type <code>FieldType.AN</code> of this record.
	 * <p>
	 * Every character out of the Charset "US-ASCII" present in the value of a field of type <code>FieldType.AN</code>, 
	 * is replaced with the character ?. For example the character � is replaced with the character ?
	 */
	public void toAscii() {
		for (String key : fieldsMap.keySet()) {
			fieldsMap.get(key).toAscii();
		}
	}
	
	/**
	 * Apply the encoding with the Charset "US-ASCII" to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> param
	 * <p>
	 * Every character out of the Charset "US-ASCII" present in the value of the field of type <code>FieldType.AN</code>, 
	 * is replaced with the character ?. For example the character � is replaced with the character ?
	 * 
	 * @param fieldProperty the field property of the field to apply the encoding
	 */
	public void toAscii(FieldProperty fieldProperty) {
		getRecordField(fieldProperty).toAscii();
	}
	
	/**
	 * Applay toUpperCase, toRemoveAccents and toAscii to the value of every field of type <code>FieldType.AN</code> of this record. 
	 */
	public void toNormalize() {
		for (String key : fieldsMap.keySet()) {
			fieldsMap.get(key).toNormalize();
		}
	}
	
	/**
	 * Applay toUpperCase, toRemoveAccents and toAscii to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property of the field to apply the normalize
	 */
	public void toNormalize(FieldProperty fieldProperty) {
		getRecordField(fieldProperty).toNormalize();
	}
	
	/**
	 * Apply the <code>recordWay</code> param to this record
	 * 
	 * @param recordWay the record way to apply to this record
	 */
	public void setRecordWay(RecordWay recordWay) {
		this.recordWay = recordWay;
		for (String key : fieldsMap.keySet()) {
			fieldsMap.get(key).setRecordWay(recordWay); 
		}
	}
	
	/**
	 * @return the record len
	 */
	public int getRecordLen() {
		return recordLen;
	}
	
	/**
	 * Returns the validion info of the field represented by the <code>fieldProperty</code> param
	 * 
	 * @param fieldProperty the field property of the field to return the validation info
	 * @return the validation info of the field represented by the <code>fieldProperty</code> param
	 */
	public FieldValidationInfo getRecordFieldValidationInfo(FieldProperty fieldProperty) {
		return getRecordField(fieldProperty).getValidationInfo();
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
		boolean result = false;
		
		Field rf = getRecordField(fieldProperty);
		FieldValidationInfo vi = rf.getValidationInfo();
		if (vi != null && FieldValidationInfo.RecordFieldValidationStatus.ERROR.equals(vi.getValidationStatus())) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Returns true if at least one field has <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @return true if at least one field has <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isErrorStatus() {
		boolean result = false;
		
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key); 
			FieldValidationInfo vi = rf.getValidationInfo();
			if (vi != null && FieldValidationInfo.RecordFieldValidationStatus.ERROR.equals(vi.getValidationStatus())) {
				result = true;
				break;
			} 
		}
		
		return result;
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
		boolean result = false;
		
		Field rf = getRecordField(fieldProperty);
		FieldValidationInfo vi = rf.getValidationInfo();
		if (vi != null && FieldValidationInfo.RecordFieldValidationStatus.WARN.equals(vi.getValidationStatus())) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Returns true if at least one field has <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 * 
	 * @return true if at least one field has <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 */
	public boolean isWarnStatus() {
		boolean result = false;
		
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key); 
			FieldValidationInfo vi = rf.getValidationInfo();
			if (vi != null && FieldValidationInfo.RecordFieldValidationStatus.WARN.equals(vi.getValidationStatus())) {
				result = true;
				break;
			} 
		}
		
		return result;
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
		return !isErrorStatus(fieldProperty) && !isWarnStatus(fieldProperty);
	}
	
	/**
	 * Returns true if all field has NOT <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @return true if all field has <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isInfoStatus() {
		return !isErrorStatus() && !isWarnStatus();
	}
	
	/**
	 * Reset the validation status of all fields of this record. To reset means that the validation status is set to 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.INFO</code>
	 */
	public void resetStatus() {
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key);
			rf.resetStatus();
		}
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
				throw new RecordException("The field name=[" + FINAL_FILLER_NAME + "] is reserved");
			}
			
			List<FieldExtendedProperty> eps = p.fieldExtendedProperties();
			if (eps == null) {
				eps = fieldExtendedProperties;
			} else if (fieldExtendedProperties != null) {
				for (FieldExtendedProperty rep : fieldExtendedProperties) {
					boolean found = false;
					FieldExtendedPropertyType repType = rep.getType();
					for (FieldExtendedProperty fep : eps) {
						if (repType.equals(fep.getType())) {
							found = true;
							break;
						}
					}
					
					if (!found) {
						// index 0 => it win the last => it win the field vs record
						// think for example to LPAD and RPAD
						eps.add(0, rep);
					}
				}
			}
			
			fieldsMap.put(p.name(), new Field(p.name(), ((Enum<?>)p).ordinal() + 1, p.fieldType(), p.fieldLen(), 
				p.fieldMandatory(), recordWay, p.fieldDefaultValue(), eps));
        }
	}
	
	private int retrieveRecordLen() {
		int recordLen = 0;
		for(FieldProperty p : fields.getEnumConstants()) {
			recordLen += p.fieldLen();
		}
		
		return recordLen;
	}
}
