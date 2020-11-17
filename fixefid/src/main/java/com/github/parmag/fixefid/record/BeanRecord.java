package com.github.parmag.fixefid.record;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldType;

/**
 * The <code>BeanRecord</code> represents a fixed fields formatted text backed by a java bean.
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.1.0
 *
 */
public class BeanRecord extends AbstractRecord {
	private static final String CMP_FIELD_NAME_SEP = ".";
	private static final String JAVA_MATH_BIG_DECIMAL = "java.math.BigDecimal";
	private static final String JAVA_LANG_LONG = "java.lang.Long";
	private static final String JAVA_LANG_LONG_PR = "long";
	private static final String JAVA_LANG_INTEGER = "java.lang.Integer";
	private static final String JAVA_LANG_INTEGER_PR = "int";
	private static final String JAVA_LANG_DOUBLE = "java.lang.Double";
	private static final String JAVA_LANG_DOUBLE_PR = "double";
	private static final String JAVA_LANG_FLOAT = "java.lang.Float";
	private static final String JAVA_LANG_FLOAT_PR = "float";
	private static final String JAVA_LANG_BOOLEAN = "java.lang.Boolean";
	private static final String JAVA_LANG_BOOLEAN_PR = "boolean";
	private static final String JAVA_UTIL_DATE = "java.util.Date"; 
	private static final String JAVA_LANG_STRING = "java.lang.String";
	
	private Object bean;
	private Map<String, List<FieldExtendedProperty>> mapFieldExtendedProperties;

	/**
	 * Constructs a new <code>BeanRecord</code> that represents the fixed fields indicated by the <code>bean</code> parameter.
	 * The lenght of this record is calculated with the sum of the lenght of every
	 * field. The value of every field is initialized with a default value (if present)
	 * 
	 * @param bean the <code>bean</code> of this <code>BeanRecord</code>
	 */
	public BeanRecord(Object bean) {
		this(bean, null, null, null);
	}
	
	/**
	 * Constructs a new <code>BeanRecord</code> that represents the fixed fields indicated by the <code>bean</code> parameter.
	 * The lenght of this record is calculated with the sum of the lenght of every
	 * field. The value of every field is initialized with the formatted value present in the relative position of the <code>record</code> 
	 * parameter
	 * 
	 * @param bean the <code>bean</code> of this <code>BeanRecord</code>
	 * @param record the formatted string of this <code>BeanRecord</code>
	 */
	public BeanRecord(Object bean, String record) {
		this(bean, record, null, null);
	}
	
	/**
	 * Constructs a new <code>BeanRecord</code> that represents the fixed fields indicated by the <code>bean</code> parameter.
	 * The value of every field is initialized with the formatted value present in the
	 * relative position of the <code>record</code> parameter. To every field of the record are applied the extended properties
	 * present in the <code>fieldExtendedProperties</code> parameter. Only the following properties are permitted at record level:
	 * <ul>
	 * <li><code>FieldExtendedPropertyType.LPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.RPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.VALIDATOR</code></li>
	 * </ul>
	 * 
	 * @param bean bean the <code>bean</code> of this <code>BeanRecord</code>
	 * @param record the formatted string of this <code>BeanRecord</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 */
	public BeanRecord(Object bean, String record, List<FieldExtendedProperty> fieldExtendedProperties) {
		this(bean, record, fieldExtendedProperties, null);
	}
	
	/**
	 * Constructs a new <code>BeanRecord</code> that represents the fixed fields indicated by the <code>bean</code> parameter.
	 * The value of every field is initialized with the formatted value present in the
	 * relative position of the <code>record</code> parameter. To every field of the record are applied the extended properties
	 * present in the <code>fieldExtendedProperties</code> parameter. Only the following properties are permitted at record level:
	 * <ul>
	 * <li><code>FieldExtendedPropertyType.LPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.RPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.VALIDATOR</code></li>
	 * </ul>
	 * The extended property of every field can be overrided with the extended property present in the <code>mapFieldExtendedProperties</code>
	 * 
	 * @param bean bean the <code>bean</code> of this <code>BeanRecord</code>
	 * @param record the formatted string of this <code>BeanRecord</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 * @param mapFieldExtendedProperties the extended properties of fields to override the relative property at record level
	 */
	public BeanRecord(Object bean, String record, List<FieldExtendedProperty> fieldExtendedProperties, 
			Map<String, List<FieldExtendedProperty>> mapFieldExtendedProperties) {
		this.mapFieldExtendedProperties = mapFieldExtendedProperties;
		
		initFieldExtendedProperties(fieldExtendedProperties); 
		initBean(bean, record);
	}
	
	private void initBean(Object bean, String record) {
		if (bean == null) {
            throw new RecordException(ErrorCode.RE12, "Can't create record with a null bean");
        }
		
		this.bean = bean;

        Class<?> clazz = bean.getClass();
        if (!clazz.isAnnotationPresent(FixefidRecord.class)) {
            throw new RecordException(ErrorCode.RE13, "The class " + clazz.getSimpleName() + " is not annotated with FixefidRecord");
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
	
	/**
	 * Init the fields map
	 * 
	 * @throws RecordException if a field has the name equals to "finalFiller" or some reflection field access problem
	 * @throws FieldException if the properties of the fields are in some non valid status
	 */
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
                	throw new RecordException(ErrorCode.RE14, "The ordinal " + fieldOrdinal + " must be unique for the type (and super type) " + clazz.getName());
                } else {
                	ordinals.add(fieldOrdinal);
                }
                
                if (FINAL_FILLER_NAME.equals(fieldName)) {
    				throw new RecordException(ErrorCode.RE15, "The field name=[" + FINAL_FILLER_NAME + "] is reserved");
    			}
                
				FieldType fieldType = fixefidField.fieldType();
				if (FieldType.CMP.equals(fieldType)) {
					Map<String, List<FieldExtendedProperty>> mapCmpFieldExtendedProperties = null;
					if (mapFieldExtendedProperties != null) {
						mapCmpFieldExtendedProperties = new HashMap<String, List<FieldExtendedProperty>>();
						for (String key : mapFieldExtendedProperties.keySet()) { 
							 if (key.startsWith(fieldName + CMP_FIELD_NAME_SEP)) { 
								 mapCmpFieldExtendedProperties.put(key.substring(key.indexOf(CMP_FIELD_NAME_SEP) + 1), mapFieldExtendedProperties.get(key));
							 }
						}
					}
					
					try {
						Map<String, com.github.parmag.fixefid.record.field.Field> cmpFieldsMap =
							new BeanRecord(field.get(bean), null, fieldExtendedProperties, mapCmpFieldExtendedProperties).getFieldsMap();
						for (String cmpFieldName : cmpFieldsMap.keySet()) {
							fieldsMap.put(fieldName + CMP_FIELD_NAME_SEP + cmpFieldName, cmpFieldsMap.get(cmpFieldName));
						}
					} catch (Exception e) {
						throw new RecordException(ErrorCode.RE1, e);
					}
				} else {
					List<FieldExtendedProperty> eps = normalizeFieldExtendedProperties(
	                		mapFieldExtendedProperties != null ? mapFieldExtendedProperties.get(fieldName) : null);
					fieldsMap.put(fieldName, new com.github.parmag.fixefid.record.field.Field(
                		fieldName, fieldOrdinal, fieldType, fixefidField.fieldLen(), 
                		fixefidField.fieldMandatory(), recordWay, fixefidField.fieldDefaultValue(), eps));
					
					syncValueFromBeanFieldToRecordField(null, field, bean, fieldsMap);
				}
				
            }
        }
		
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 */
	public void setValue(String fieldName, String value) {
		super.setValue(fieldName, value); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
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
		super.setValue(fieldName, value, truncate);
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(String fieldName, Long value) throws FieldException {
		super.setValue(fieldName, value); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(String fieldName, Integer value) throws FieldException {
		super.setValue(fieldName, value);
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(String fieldName, Double value) throws FieldException {
		super.setValue(fieldName, value); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(String fieldName, Float value) throws FieldException {
		super.setValue(fieldName, value); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(String fieldName, BigDecimal value) throws FieldException {
		super.setValue(fieldName, value);
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(String fieldName, Date value) throws FieldException {
		super.setValue(fieldName, value); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(String fieldName, Boolean value) throws FieldException {
		super.setValue(fieldName, value); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
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
	
	/**
	 * update the value of every fields from record to the backed bean
	 */
	public void syncValuesFromRecordToBean() {
		for (String fieldName : fieldsMap.keySet()) {
		    syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap);
		} 
	}
	
	/**
	 * update the value of every fields from the backed bean the the record
	 */
	public void syncValuesFromBeanToRecord() {
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), bean.getClass());
		for (Field field : fields) {
		    syncValueFromBeanFieldToRecordField(null, field, bean, fieldsMap);
		} 
	}

	private static void syncValueFromRecordFieldToBeanField(String fieldName, Object bean,
			Map<String, com.github.parmag.fixefid.record.field.Field> fieldsMap) {
		if (FINAL_FILLER_NAME.equals(fieldName)) {
			return;
		}
		
		Object[] fieldAndBean = fieldAndBeanForName(fieldName, bean); 
		Field field = (Field) fieldAndBean[0];
		bean = fieldAndBean[1];
		field.setAccessible(true);
		if (field.isAnnotationPresent(FixefidField.class)) {
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
		    } else if (JAVA_LANG_BOOLEAN.equals(typeName) || JAVA_LANG_BOOLEAN_PR.equals(typeName)) {
		    	if (rf.isBoolean()) {
		    		value = rf.getValueAsBoolean();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_FLOAT.equals(typeName) || JAVA_LANG_FLOAT_PR.equals(typeName)) {
		    	if (rf.isFloat()) {
		    		value = rf.getValueAsFloat();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_DOUBLE.equals(typeName) || JAVA_LANG_DOUBLE_PR.equals(typeName)) {
		    	if (rf.isDouble()) {
		    		value = rf.getValueAsDouble();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_INTEGER.equals(typeName) || JAVA_LANG_INTEGER_PR.equals(typeName)) {
		    	if (rf.isInteger()) {
		    		value = rf.getValueAsInteger();
		    	} else {
		    		error = true;
		    	}
		    } else if (JAVA_LANG_LONG.equals(typeName) || JAVA_LANG_LONG_PR.equals(typeName)) {
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
		    } else {
		    	error = true;
		    }
		    
		    if (error) {
		    	throw new RecordException(ErrorCode.RE2, "Cannot set to field " + fieldName + " of type " + typeName + " the value from " + rf.toString());
		    } else {
		    	try {
		    		field.set(bean, value);
		    	} catch (Exception e) {
					throw new RecordException(ErrorCode.RE3, e);
				} 
		    }
		}
	}
	
	private static void syncValueFromBeanFieldToRecordField(String parentFieldName, Field field, Object bean,
			Map<String, com.github.parmag.fixefid.record.field.Field> fieldsMap) {
		field.setAccessible(true);
		String fieldName = parentFieldName != null ? parentFieldName + CMP_FIELD_NAME_SEP + field.getName() : field.getName();
	    try {
	    	Object value = field.get(bean);
			if (field.isAnnotationPresent(FixefidField.class) && value != null) {
				FixefidField fixefidField = field.getAnnotation(FixefidField.class);
				if (FieldType.CMP.equals(fixefidField.fieldType())) {
					List<Field> cmpFields = retrieveAllFields(new ArrayList<Field>(), field.get(bean).getClass());
					for (Field cmpField : cmpFields) {
						syncValueFromBeanFieldToRecordField(fieldName, cmpField, field.get(bean), fieldsMap);
					}
				} else {
				    syncValueFromBeanFieldToRecordField(fieldName, field.getType().getName(), value, fieldsMap);
				}
			}
	    } catch (Exception e) {
	    	throw new RecordException(ErrorCode.RE4, e);
	    }
	}
	
	private static void syncValueFromBeanFieldToRecordField(String fieldName, String typeName, Object value, 
			Map<String, com.github.parmag.fixefid.record.field.Field> fieldsMap) {
		boolean error = false;
	    
	    com.github.parmag.fixefid.record.field.Field rf = fieldsMap.get(fieldName);
	    
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
	    } else if (JAVA_LANG_BOOLEAN.equals(typeName) || JAVA_LANG_BOOLEAN_PR.equals(typeName)) {
	    	if (rf.isBoolean()) {
	    		rf.setValue((Boolean)value); 
	    	} else {
	    		error = true;
	    	}
	    } else if (JAVA_LANG_FLOAT.equals(typeName) || JAVA_LANG_FLOAT_PR.equals(typeName)) {
	    	if (rf.isFloat()) {
	    		rf.setValue((Float)value);
	    	} else {
	    		error = true;
	    	}
	    } else if (JAVA_LANG_DOUBLE.equals(typeName) || JAVA_LANG_DOUBLE_PR.equals(typeName)) {
	    	if (rf.isDouble()) {
	    		rf.setValue((Double)value);
	    	} else {
	    		error = true;
	    	}
	    } else if (JAVA_LANG_INTEGER.equals(typeName) || JAVA_LANG_INTEGER_PR.equals(typeName)) {
	    	if (rf.isInteger()) {
	    		rf.setValue((Integer)value);
	    	} else {
	    		error = true;
	    	}
	    } else if (JAVA_LANG_LONG.equals(typeName) || JAVA_LANG_LONG_PR.equals(typeName)) {
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
	    } else {
	    	error = true;
	    }
	    
	    if (error) {
	    	throw new RecordException(ErrorCode.RE16, "Cannot set to " + rf.toString() + " the value from field " + fieldName + " of type " + typeName);
	    }
	}
	
	private static List<Field> retrieveAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        retrieveAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
	
	private static Object[] fieldAndBeanForName(String fieldName, Object bean) {
		Object[] result = null;
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), bean.getClass());
		try {
			for (Field field : fields) {
				field.setAccessible(true);
	            if (field.isAnnotationPresent(FixefidField.class)) {
	            	FixefidField fixefidField = field.getAnnotation(FixefidField.class);
					if (FieldType.CMP.equals(fixefidField.fieldType()) && fieldName.startsWith(field.getName() + CMP_FIELD_NAME_SEP)) {
						result = fieldAndBeanForName(fieldName.substring(fieldName.indexOf(CMP_FIELD_NAME_SEP) + 1), field.get(bean));
						break;
					} else if (fieldName.equals(field.getName())) {
						result = new Object[] {field, bean};
						break;
					}
	            }
			}
		} catch (Exception e) {
	    	throw new RecordException(ErrorCode.RE5, e);
	    }
		
		if (result == null) {
			throw new RecordException(ErrorCode.RE17, "Not found field with name " + fieldName);
		}
		
		return result;
	}
}
