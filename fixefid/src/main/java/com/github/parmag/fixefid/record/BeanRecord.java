package com.github.parmag.fixefid.record;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;

public class BeanRecord extends AbstractRecord {
	private static final String JAVA_MATH_BIG_DECIMAL = "java.math.BigDecimal";
	private static final String JAVA_LANG_LONG = "java.lang.Long";
	private static final String JAVA_LANG_INTEGER = "java.lang.Integer";
	private static final String JAVA_LANG_DOUBLE = "java.lang.Double";
	private static final String JAVA_LANG_FLOAT = "java.lang.Float";
	private static final String JAVA_LANG_BOOLEAN = "java.lang.Boolean";
	private static final String JAVA_UTIL_DATE = "java.util.Date"; 
	private static final String JAVA_LANG_STRING = "java.lang.String";
	
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
    		for (Field field : retrieveAllFields(new ArrayList<Field>(), clazz)) {
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
		
		List<Integer> ordinals = new ArrayList<Integer>();
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), clazz);
		Collections.sort(fields, new Comparator<Field>() {
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
                String fieldName = field.getName();
                int fieldOrdinal = fixefidField.fieldOrdinal();
                
                // check ordinals => must be unique
                if (ordinals.contains(fieldOrdinal)) {
                	throw new RecordException("The ordinal " + fieldOrdinal + " must be unique for the type (and super type) " + clazz.getName());
                } else {
                	ordinals.add(fieldOrdinal);
                }
                
                if (FINAL_FILLER_NAME.equals(fieldName)) {
    				throw new RecordException("The field name=[" + FINAL_FILLER_NAME + "] is reserved");
    			}
                
                List<FieldExtendedProperty> eps = normalizeFieldExtendedProperties(
                		mapFieldExtendedProperties != null ? mapFieldExtendedProperties.get(fieldName) : null);
                
				fieldsMap.put(fieldName, new com.github.parmag.fixefid.record.field.Field(
                		fieldName, fieldOrdinal, fixefidField.fieldType(), fixefidField.fieldLen(), 
                		fixefidField.fieldMandatory(), recordWay, fixefidField.fieldDefaultValue(), eps));
                
                syncValueFromBeanFieldToRecordField(field);
            }
        }
		
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param syncToBean if <code>true</code> set the value to bean field
	 */
	public void setValue(String fieldName, String value, boolean syncToBean) {
		super.setValue(fieldName, value); 
		if (syncToBean) {
			try {
				syncValueFromRecordFieldToBeanField(fielForName(fieldName, (bean.getClass())));
			} catch (Exception e) {
				throw new FieldException(e);
			}
		}
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param. 
	 * 
	 * @param fieldName the field proerty of the field to set the specified value
	 * @param value the value to set
	 * @param truncate If the <code>truncate</code> param is <code>true</code> and the len of the specified value is greater than the len of the 
	 * field, the specified value will be truncated at the len od the field. 
	 * @param syncToBean if <code>true</code> set the value to bean field
	 * @throws RecordException If the <code>truncate</code> param is <code>false</code> and the len of the specified value is 
	 * greater than the len of the field
	 */
	public void setValue(String fieldName, String value, boolean truncate, boolean syncToBean) throws RecordException {
		super.setValue(fieldName, value, truncate);
		if (syncToBean) {
			try {
				syncValueFromRecordFieldToBeanField(fielForName(fieldName, (bean.getClass())));
			} catch (Exception e) {
				throw new FieldException(e);
			}
		}
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param syncToBean if <code>true</code> set the value to bean field
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(String fieldName, Long value, boolean syncToBean) throws FieldException {
		super.setValue(fieldName, value); 
		if (syncToBean) {
			try {
				syncValueFromRecordFieldToBeanField(fielForName(fieldName, (bean.getClass())));
			} catch (Exception e) {
				throw new FieldException(e);
			}
		}
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param syncToBean if <code>true</code> set the value to bean field
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(String fieldName, Integer value, boolean syncToBean) throws FieldException {
		super.setValue(fieldName, value);
		if (syncToBean) {
			try {
				syncValueFromRecordFieldToBeanField(fielForName(fieldName, (bean.getClass())));
			} catch (Exception e) {
				throw new FieldException(e);
			}
		}
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param syncToBean if <code>true</code> set the value to bean field
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(String fieldName, Double value, boolean syncToBean) throws FieldException {
		super.setValue(fieldName, value); 
		if (syncToBean) {
			try {
				syncValueFromRecordFieldToBeanField(fielForName(fieldName, (bean.getClass())));
			} catch (Exception e) {
				throw new FieldException(e);
			}
		}
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param syncToBean if <code>true</code> set the value to bean field
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(String fieldName, Float value, boolean syncToBean) throws FieldException {
		super.setValue(fieldName, value); 
		if (syncToBean) {
			try {
				syncValueFromRecordFieldToBeanField(fielForName(fieldName, (bean.getClass())));
			} catch (Exception e) {
				throw new FieldException(e);
			}
		}
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 * @param syncToBean if <code>true</code> set the value to bean field
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(String fieldName, BigDecimal value, boolean syncToBean) throws FieldException {
		super.setValue(fieldName, value);
		if (syncToBean) {
			try {
				syncValueFromRecordFieldToBeanField(fielForName(fieldName, (bean.getClass())));
			} catch (Exception e) {
				throw new FieldException(e);
			}
		}
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param syncToBean if <code>true</code> set the value to bean field
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(String fieldName, Date value, boolean syncToBean) throws FieldException {
		super.setValue(fieldName, value); 
		if (syncToBean) {
			try {
				syncValueFromRecordFieldToBeanField(fielForName(fieldName, (bean.getClass())));
			} catch (Exception e) {
				throw new FieldException(e);
			}
		}
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param syncToBean if <code>true</code> set the value to bean field
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(String fieldName, Boolean value, boolean syncToBean) throws FieldException {
		super.setValue(fieldName, value); 
		if (syncToBean) {
			try {
				syncValueFromRecordFieldToBeanField(fielForName(fieldName, (bean.getClass())));
			} catch (Exception e) {
				throw new FieldException(e);
			}
		}
	}
	
	/**
	 * Fill the value of every field of this record with the formatted value presented in the <code>record</code> param
	 * 
	 * @param record the record with the formatted value to fill
	 */
	protected void doFill(String record) {
		super.doFill(record); 
		
		syncValuesFromRecordToBean();
	}
	
	public void syncValuesFromRecordToBean() {
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), bean.getClass());
		for (Field field : fields) {
		    syncValueFromRecordFieldToBeanField(field);
		} 
	}
	
	public void syncValuesFromBeanToRecord() {
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), bean.getClass());
		for (Field field : fields) {
		    syncValueFromBeanFieldToRecordField(field);
		} 
	}

	private void syncValueFromRecordFieldToBeanField(Field field) {
		field.setAccessible(true);
		if (field.isAnnotationPresent(FixefidField.class)) {
		    String fieldName = field.getName();
		    boolean error = false;
		    
		    com.github.parmag.fixefid.record.field.Field rf = fieldsMap.get(fieldName);
		    String typeName = field.getType().getName();
		    Object value = null;
		    
		    if (JAVA_LANG_STRING.equals(typeName)) {
		    	if (rf.isString()) {
		    		value = rf.getValueAsString();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_UTIL_DATE.equals(typeName)) {
		    	if (rf.isDate()) {
		    		value = rf.getValueAsDate();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_BOOLEAN.equals(typeName)) {
		    	if (rf.isBoolean()) {
		    		value = rf.getValueAsBoolean();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_FLOAT.equals(typeName)) {
		    	if (rf.isFloat()) {
		    		value = rf.getValueAsFloat();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_DOUBLE.equals(typeName)) {
		    	if (rf.isDouble()) {
		    		value = rf.getValueAsDouble();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_INTEGER.equals(typeName)) {
		    	if (rf.isInteger()) {
		    		value = rf.getValueAsInteger();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_LONG.equals(typeName)) {
		    	if (rf.isLong()) {
		    		value = rf.getValueAsLong();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_MATH_BIG_DECIMAL.equals(typeName)) {
		    	if (rf.isBigDecimal()) {
		    		value = rf.getValueAsBigDecimal();
		    	} else {
		    		error = true;
		    	}
		    }
		    
		    if (error) {
		    	throw new RecordException("Cannot set to field " + fieldName + " of type " + typeName + " the value from " + rf.toString());
		    } else {
		    	try {
		    		field.set(bean, value);
		    	} catch (Exception e) {
					throw new RecordException(e);
				} 
		    }
		}
	}
	
	private void syncValueFromBeanFieldToRecordField(Field field) {
		field.setAccessible(true);
		Object value = null;
	    try {
		    value = field.get(bean);
		} catch (Exception e) {
			throw new RecordException(e);
		}
		if (field.isAnnotationPresent(FixefidField.class) && value != null) {
		    String fieldName = field.getName();
		    boolean error = false;
		    
		    com.github.parmag.fixefid.record.field.Field rf = fieldsMap.get(fieldName);
		    String typeName = field.getType().getName();
		    
		    if (JAVA_LANG_STRING.equals(typeName)) {
		    	if (rf.isString()) {
		    		String valueAsString = (String) value;
		    		if (valueAsString.length() > rf.getLen()) { 
		    			valueAsString = valueAsString.substring(0, rf.getLen());
		    		}
		    		
		    		rf.setValue(valueAsString, true);
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_UTIL_DATE.equals(typeName)) {
		    	if (rf.isDate()) {
		    		rf.setValue((Date)value); 
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_BOOLEAN.equals(typeName)) {
		    	if (rf.isBoolean()) {
		    		rf.setValue((Boolean)value); 
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_FLOAT.equals(typeName)) {
		    	if (rf.isFloat()) {
		    		rf.setValue((Float)value);
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_DOUBLE.equals(typeName)) {
		    	if (rf.isDouble()) {
		    		rf.setValue((Double)value);
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_INTEGER.equals(typeName)) {
		    	if (rf.isInteger()) {
		    		rf.setValue((Integer)value);
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_LONG.equals(typeName)) {
		    	if (rf.isLong()) {
		    		rf.setValue((Long)value);
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_MATH_BIG_DECIMAL.equals(typeName)) {
		    	if (rf.isBigDecimal()) {
		    		rf.setValue((BigDecimal)value);
		    	} else {
		    		error = true;
		    	}
		    }
		    
		    if (error) {
		    	throw new RecordException("Cannot set to " + rf.toString() + " the value from field " + fieldName + " of type " + typeName);
		    }
		}
	}
	
	private static List<Field> retrieveAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        retrieveAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
	
	private static Field fielForName(String fieldName, Class<?> type) {
		Field result = null;
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), type);
		for (Field field : fields) {
			field.setAccessible(true);
            if (field.isAnnotationPresent(FixefidField.class)) {
				if (fieldName.equals(field.getName())) {
					result = field;
					break;
				}
            }
		}
		
		if (result == null) {
			throw new RecordException("Not found field with name " + fieldName);
		}
		
		return result;
	}
}
