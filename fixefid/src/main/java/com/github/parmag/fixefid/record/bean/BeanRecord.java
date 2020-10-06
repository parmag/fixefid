package com.github.parmag.fixefid.record.bean;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.parmag.fixefid.record.AbstractRecord;
import com.github.parmag.fixefid.record.RecordException;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldValidationInfo;

public class BeanRecord extends AbstractRecord {
	private Object bean;
	private Map<String, List<FieldExtendedProperty>> mapFieldExtendedProperties;

	public BeanRecord(Object bean) {
		this(bean, null, null, null);
	}
	
	public BeanRecord(Object bean, String record) {
		this(bean, record, null, null);
	}
	
	public BeanRecord(Object bean, String record, List<FieldExtendedProperty> fieldExtendedProperties) {
		this(bean, record, fieldExtendedProperties, null);
	}
	
	public BeanRecord(Object bean, String record, List<FieldExtendedProperty> fieldExtendedProperties, 
			Map<String, List<FieldExtendedProperty>> mapFieldExtendedProperties) {
		this.mapFieldExtendedProperties = mapFieldExtendedProperties;
		
		initFieldExtendedProperties(fieldExtendedProperties); 
		initBean(bean, record);
	}
	
	private void initBean(Object bean, String record) {
		if (bean == null) {
            throw new RecordException("Can't create record with a null bean");
        }
		
		this.bean = bean;

        Class<?> clazz = bean.getClass();
        if (!clazz.isAnnotationPresent(FixefidRecord.class)) {
            throw new RecordException("The class " + clazz.getSimpleName() + " is not annotated with FixefidRecord");
        } else {
        	initRecordLen(clazz);
        	initRecordWay(clazz);
        	initFieldsMap();
        	addFiller();
        	if (record != null) {
    			initRecord(record);
    		}
        	
        }
	}
	
	private void initRecordLen(Class<?> clazz) {
		FixefidRecord fixefidRecord = clazz.getAnnotation(FixefidRecord.class);
		int recordLen = fixefidRecord.recordLen();
    	if (recordLen == 0) {
    		for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(FixefidField.class)) {
                    FixefidField fixefidField = field.getAnnotation(FixefidField.class);
                    recordLen += fixefidField.fieldLen();
                }
            }
    	}
    	
    	this.recordLen = recordLen;
	}
	
	private void initRecordWay(Class<?> clazz) {
		FixefidRecord fixefidRecord = clazz.getAnnotation(FixefidRecord.class);
		this.recordWay = fixefidRecord.recordWay();
	}
	
	protected void initFieldsMap() throws RecordException, FieldException {
		Class<?> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Arrays.sort(fields, new Comparator<Field>() {
			@Override
			public int compare(Field f1, Field f2) {
				int fieldOrdinal1 = 0;
				int fieldOrdinal2 = 0;
				
				if (f1.isAnnotationPresent(FixefidField.class)) {
					fieldOrdinal1 = f1.getAnnotation(FixefidField.class).fieldOrdinal();
				}
				
				if (f2.isAnnotationPresent(FixefidField.class)) {
					fieldOrdinal2 = f2.getAnnotation(FixefidField.class).fieldOrdinal();
				}
				
				return fieldOrdinal1 - fieldOrdinal2;
			}
		});
		
		for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(FixefidField.class)) {
                FixefidField fixefidField = field.getAnnotation(FixefidField.class);
                String fieldName = fixefidField.fieldName();
                if (fieldName.isEmpty()) {
                	fieldName = field.getName();
                }
                
                if (FINAL_FILLER_NAME.equals(fieldName)) {
    				throw new RecordException("The field name=[" + FINAL_FILLER_NAME + "] is reserved");
    			}
                
                List<FieldExtendedProperty> eps = normalizeFieldExtendedProperties(
                		mapFieldExtendedProperties != null ? mapFieldExtendedProperties.get(fieldName) : null);
                
                fieldsMap.put(fieldName, new com.github.parmag.fixefid.record.field.Field(
                		fieldName, fixefidField.fieldOrdinal(), fixefidField.fieldType(), fixefidField.fieldLen(), 
                		fixefidField.fieldMandatory(), recordWay, fixefidField.fieldDefaultValue(), eps));
            }
        }
		
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldName</code> param is mandatory
	 * 
	 * @param fieldName the field name to know if the relative field is mandatory
	 * @return <code>true</code> if the field represented by the <code>fieldName</code> param is mandatory
	 */
	public boolean isMandatory(String fieldName) {
		return getRecordField(fieldName).isMandatory();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldName</code> param is a <code>String</code>.
	 * A field is a <code>String</code> if is of type <code>FieldType.AN</code>
	 * 
	 * @param fieldName the field property to know if the relative field is a <code>String</code>
	 * @return <code>true</code> if the field represented by the <code>fieldName</code> param is a <code>String</code>
	 */
	public boolean isString(String fieldName) {
		return getRecordField(fieldName).isString();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldName</code> param is <code>Long</code>.
	 * A field is a <code>Long</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len >= 10</code>
	 * 
	 * @param fieldName the field property to know if the relative field is a <code>Long</code>
	 * @return <code>true</code> if the field represented by the <code>fieldName</code> param is <code>Long</code>
	 */
	public boolean isLong(String fieldName) {
		return getRecordField(fieldName).isLong();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldName</code> param is <code>Integer</code>.
	 * A field is a <code>Integer</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len < 10</code>
	 * 
	 * @param fieldName the field property to know if the relative field is a <code>Integer</code>
	 * @return <code>true</code> if the field represented by the <code>fieldName</code> param is <code>Integer</code>
	 */
	public boolean isInteger(String fieldName) {
		return getRecordField(fieldName).isInteger();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldName</code> param is a <code>Date</code>.
	 * A field is a <code>Date</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.DATE_FORMAT</code> is present.
	 * 
	 * @param fieldName the field property to know if the relative field is a <code>Date</code>
	 * @return <code>true</code> if the field represented by the <code>fieldName</code> param is a <code>Date</code>
	 */
	public boolean isDate(String fieldName) {
		return getRecordField(fieldName).isDate();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldName</code> param is a <code>Boolean</code>.
	 * A field is a <code>Boolean</code> if is of type <code>FieldType.AN</code> and the 
	 * <code>FieldExtendedPropertyType.BOOLEAN_FORMAT</code> is present.
	 * 
	 * @param fieldName the field property to know if the relative field is a <code>Boolean</code>
	 * @return <code>true</code> if the field represented by the <code>fieldName</code> param is a <code>Boolean</code>
	 */
	public boolean isBoolean(String fieldName) {
		return getRecordField(fieldName).isBoolean();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldName</code> param is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len >= 10</code>
	 * 
	 * @param fieldName the field property to know if the relative field is a <code>Double</code>
	 * @return <code>true</code> if the field represented by the <code>fieldName</code> param is <code>Double</code>
	 */
	public boolean isDouble(String fieldName) {
		return getRecordField(fieldName).isDouble();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldName</code> param is <code>Double</code>.
	 * A field is a <code>Double</code> if is of type <code>FieldType.N</code>,
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len < 10</code>
	 * 
	 * @param fieldName the field property to know if the relative field is a <code>Double</code>
	 * @return <code>true</code> if the field represented by the <code>fieldName</code> param is <code>Double</code>
	 */
	public boolean isFloat(String fieldName) {
		return getRecordField(fieldName).isFloat();
	}
	
	/**
	 * The result is <code>true</code> if the field represented by the <code>fieldName</code> param is <code>BigDecimal</code>.
	 * A field is a <code>BigDecimal</code> if is <code>Double</code> or a <code>Float</code>.
	 * 
	 * @param fieldName the field property to know if the relative field is a <code>BigDecimal</code>
	 * @return <code>true</code> if the field represented by the <code>fieldName</code> param is <code>BigDecimal</code>
	 */
	public boolean isBigDecimal(String fieldName) {
		return getRecordField(fieldName).isBigDecimal();
	}
	
	/**
	 * Returns the formatted value of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property to get the formatted value of the relative field
	 * @return the formatted value of the field represented by the <code>fieldName</code> param
	 */
	public String getValue(String fieldName) {
		return getRecordField(fieldName).getValue();
	}
	
	/**
	 * Returns the value as <code>String</code> of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property to get the value as <code>String</code> of the relative field
	 * @return the value as <code>String</code> of the field represented by the <code>fieldName</code> param
	 * @throws FieldException if the field is not a String
	 */
	public String getValueAsString(String fieldName) throws FieldException {
		return getRecordField(fieldName).getValueAsString();
	}
	
	/**
	 * Returns the value as <code>Long</code> of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property to get the value as <code>Long</code> of the relative field
	 * @return the value as <code>Long</code> of the field represented by the <code>fieldName</code> param
	 * @throws FieldException if the field is not a Long
	 */
	public Long getValueAsLong(String fieldName) throws FieldException {
		return getRecordField(fieldName).getValueAsLong();
	}
	
	/**
	 * Returns the value as <code>Integer</code> of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property to get the value as <code>Integer</code> of the relative field
	 * @return the value as <code>Integer</code> of the field represented by the <code>fieldName</code> param
	 * @throws FieldException if the field is not a Integer
	 */
	public Integer getValueAsInteger(String fieldName) throws FieldException {
		return getRecordField(fieldName).getValueAsInteger();
	}
	
	/**
	 * Returns the value as <code>Double</code> of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property to get the value as <code>Double</code> of the relative field
	 * @return the value as <code>Double</code> of the field represented by the <code>fieldName</code> param
	 * @throws FieldException if the field is not a Double
	 */
	public Double getValueAsDouble(String fieldName) throws FieldException {
		return getRecordField(fieldName).getValueAsDouble();
	}
	
	/**
	 * Returns the value as <code>Float</code> of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property to get the value as <code>Float</code> of the relative field
	 * @return the value as <code>Float</code> of the field represented by the <code>fieldName</code> param
	 * @throws FieldException if the field is not a Float
	 */
	public Float getValueAsFloat(String fieldName) throws FieldException {
		return getRecordField(fieldName).getValueAsFloat();
	}
	
	/**
	 * Returns the value as <code>BigDecimal</code> of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property to get the value as <code>BigDecimal</code> of the relative field
	 * @return the value as <code>BigDecimal</code> of the field represented by the <code>fieldName</code> param
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public BigDecimal getValueAsBigDecimal(String fieldName) throws FieldException {
		return getRecordField(fieldName).getValueAsBigDecimal();
	}
	
	/**
	 * Returns the value as <code>Date</code> of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property to get the value as <code>Date</code> of the relative field
	 * @return the value as <code>Date</code> of the field represented by the <code>fieldName</code> param
	 * @throws FieldException if the field is not a Date
	 */
	public Date getValueAsDate(String fieldName) throws FieldException {
		return getRecordField(fieldName).getValueAsDate();
	}
	
	/**
	 * Returns the value as <code>Boolean</code> of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property to get the value as <code>Boolean</code> of the relative field
	 * @return the value as <code>Boolean</code> of the field represented by the <code>fieldName</code> param
	 * @throws FieldException if the field is not a Boolean
	 */
	public Boolean getValueAsBoolean(String fieldName) throws FieldException {
		return getRecordField(fieldName).getValueAsBoolean();
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 */
	public void setValue(String fieldName, String value) {
		getRecordField(fieldName).setValue(value, true); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param. 
	 * 
	 * @param fieldName the field proerty of the field to set the specified value
	 * @param value the value to set
	 * @param truncate If the <code>truncate</code> param is <code>true</code> and the len of the specified value is greater than the len of the 
	 * field, the specified value will be truncated at the len od the field. 
	 * @throws RecordException If the <code>truncate</code> param is <code>false</code> and the len of the specified value is 
	 * greater than the len of the field
	 */
	public void setValue(String fieldName, String value, boolean truncate) throws RecordException {
		int fieldLen = getFieldLen(fieldName);
		if (value != null && value.length() > fieldLen) { 
			if (truncate) {
				value = value.substring(0, fieldLen);
			} else {
				throw new RecordException("Cannot set value=[" + value + "] for field=[" + fieldName + "]: not valid len (expected" + fieldLen + ")");
			} 
		}
		
		setValue(fieldName, value);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(String fieldName, Long value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(String fieldName, Integer value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(String fieldName, Double value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(String fieldName, Float value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(String fieldName, BigDecimal value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(String fieldName, Date value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(String fieldName, Boolean value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Apply the method {@link String#toUpperCase} to the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to apply the upper case
	 */
	public void toUpperCase(String fieldName) {
		getRecordField(fieldName).toUpperCase();
	}
	
	/**
	 * Apply the method toRemoveAccents to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldName</code> param
	 * <p>
	 * Every character with accent present in the value of the field of type <code>FieldType.AN</code>, is replaced with the relative
	 * character without accent. For example the character ï¿½ is replaced with the character a
	 * 
	 * @param fieldName the field property of the field to removing accents
	 */
	public void toRemoveAccents(String fieldName) {
		getRecordField(fieldName).toRemoveAccents();
	}
	
	/**
	 * Apply the encoding with the Charset "US-ASCII" to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldName</code> param
	 * <p>
	 * Every character out of the Charset "US-ASCII" present in the value of the field of type <code>FieldType.AN</code>, 
	 * is replaced with the character ?. For example the character ï¿½ is replaced with the character ?
	 * 
	 * @param fieldName the field property of the field to apply the encoding
	 */
	public void toAscii(String fieldName) {
		getRecordField(fieldName).toAscii();
	}
	
	/**
	 * Applay toUpperCase, toRemoveAccents and toAscii to the value of the field of type <code>FieldType.AN</code> represented by 
	 * the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to apply the normalize
	 */
	public void toNormalize(String fieldName) {
		getRecordField(fieldName).toNormalize();
	}
	
	/**
	 * Returns the validion info of the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to return the validation info
	 * @return the validation info of the field represented by the <code>fieldName</code> param
	 */
	public FieldValidationInfo getRecordFieldValidationInfo(String fieldName) {
		return getRecordField(fieldName).getValidationInfo();
	}
	
	/**
	 * Returns true if the field represented by the <code>fieldName</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param fieldName the field property of the field to check the validation status
	 * @return true if the field represented by the <code>fieldName</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isErrorStatus(String fieldName) {
		boolean result = false;
		
		com.github.parmag.fixefid.record.field.Field rf = getRecordField(fieldName);
		FieldValidationInfo vi = rf.getValidationInfo();
		if (vi != null && FieldValidationInfo.RecordFieldValidationStatus.ERROR.equals(vi.getValidationStatus())) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Returns true if the field represented by the <code>fieldName</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 * 
	 * @param fieldName the field property of the field to check the validation status
	 * @return true if the field represented by the <code>fieldName</code> param has 
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> status
	 */
	public boolean isWarnStatus(String fieldName) {
		boolean result = false;
		
		com.github.parmag.fixefid.record.field.Field rf = getRecordField(fieldName);
		FieldValidationInfo vi = rf.getValidationInfo();
		if (vi != null && FieldValidationInfo.RecordFieldValidationStatus.WARN.equals(vi.getValidationStatus())) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Returns true if the field represented by the <code>fieldName</code> param has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 * 
	 * @param fieldName the field property of the field to check the validation status
	 * @return true if the field represented by the <code>fieldName</code> param has NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.WARN</code> and NOT
	 * <code>FieldValidationInfo.RecordFieldValidationStatus.ERROR</code> status
	 */
	public boolean isInfoStatus(String fieldName) {
		return !isErrorStatus(fieldName) && !isWarnStatus(fieldName);
	}
}
