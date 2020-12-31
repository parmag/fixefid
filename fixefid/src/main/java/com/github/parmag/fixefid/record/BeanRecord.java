package com.github.parmag.fixefid.record;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.eps.FieldExtendedPropertyFactory;
import com.github.parmag.fixefid.record.eps.FixefidDecimalFormat;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldMandatory;
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
	private static final String JAVA_UTIL_LIST = "java.util.List";
	
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
	 * @param bean the <code>bean</code> of this <code>BeanRecord</code>
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
	 * The extended property of every field can be overridden with the extended property present in the <code>mapFieldExtendedProperties</code>
	 * 
	 * @param bean the <code>bean</code> of this <code>BeanRecord</code>
	 * @param record the formatted string of this <code>BeanRecord</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 * @param mapFieldExtendedProperties the extended properties of fields to override the relative property at record level
	 */
	public BeanRecord(Object bean, String record, List<FieldExtendedProperty> fieldExtendedProperties, 
			Map<String, List<FieldExtendedProperty>> mapFieldExtendedProperties) {
		this(bean, record, fieldExtendedProperties, mapFieldExtendedProperties, null);
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
	 * The extended property of every field can be overridden with the extended property present in the <code>mapFieldExtendedProperties</code>
	 * 
	 * @param bean the <code>bean</code> of this <code>BeanRecord</code>
	 * @param record the formatted string of this <code>BeanRecord</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 * @param mapFieldExtendedProperties the extended properties of fields to override the relative property at record level
	 * @param recordWay the record way. This overrides what's defined at annotation level (see FixefidRecord annotation)
	 */
	public BeanRecord(Object bean, String record, List<FieldExtendedProperty> fieldExtendedProperties, 
			Map<String, List<FieldExtendedProperty>> mapFieldExtendedProperties, RecordWay recordWay) {
		if (mapFieldExtendedProperties != null) {
			this.mapFieldExtendedProperties = new HashMap<String, List<FieldExtendedProperty>>();
			this.mapFieldExtendedProperties.putAll(mapFieldExtendedProperties);
		}
		
		initMapFieldExtendedProperties(bean);
		initFieldExtendedProperties(fieldExtendedProperties); 
		initBean(bean, record, recordWay);
	}
	
	/**
	 * Init the map field extended properties
	 * 
	 * @param bean the <code>bean</code> of this <code>BeanRecord</code>
	 */
	protected void initMapFieldExtendedProperties(Object bean) {
		Class<?> clazz = bean.getClass();
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), clazz);
		for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            List<FieldExtendedProperty> fieldExtendedPropertyList = new ArrayList<FieldExtendedProperty>();
            if (isAnnotationPresentForBeanField(field)) {
            	if (field.isAnnotationPresent(FixefidDecimalFormat.class)) {
            		FixefidDecimalFormat a = field.getAnnotation(FixefidDecimalFormat.class);
            		fieldExtendedPropertyList.add(FieldExtendedPropertyFactory.createDecimalFormat(a.pattern(), new Locale(a.locale())));
            		fieldExtendedPropertyList.add(FieldExtendedPropertyFactory.createRemoveDecimalSeparator(Boolean.valueOf(a.removeDecimalSeparator())));
            	}
            }
            
            if (!fieldExtendedPropertyList.isEmpty()) {
            	if (mapFieldExtendedProperties == null) {
            		mapFieldExtendedProperties = new HashMap<String, List<FieldExtendedProperty>>();
            	}
            	
            	if (mapFieldExtendedProperties.containsKey(fieldName)) {
            		List<FieldExtendedProperty> feps = mapFieldExtendedProperties.get(fieldName);
            		feps.forEach(fep -> {
            			fieldExtendedPropertyList.forEach(fep1 -> {
            				if (fep.getType().equals(fep1.getType())) {
            					throw new RecordException(ErrorCode.RE37, "Found for the field " + fieldName + " extended property of type " + 
            							fep.getType() + " set via annotation and via hash map");
            				}
            			});
            		});
            		
            		feps.addAll(fieldExtendedPropertyList);
            		mapFieldExtendedProperties.put(fieldName, feps);
            	} else {
            		mapFieldExtendedProperties.put(fieldName, fieldExtendedPropertyList);
            	}
            }
		}
	}

	/**
	 * Init the record bean
	 * 
	 * @param bean the bean
	 * @param record the record
	 * @param recordWay the record way
	 */
	protected void initBean(Object bean, String record, RecordWay recordWay) {
		if (bean == null) {
            throw new RecordException(ErrorCode.RE12, "Can't create record with a null bean");
        }
		
		this.bean = bean;

        Class<?> clazz = bean.getClass();
        checkBeanAnnotation(clazz);
        initProperties(clazz);
    	initRecordLen(clazz);
    	initRecordWay(clazz, recordWay);
    	initFieldsMap();
    	addFiller();
    	if (record != null) {
			initRecord(record);
		}
	}
	
	/**
	 * init the record properties type, name and description
	 * 
	 * @param clazz the class of the bean
	 */
	protected void initProperties(Class<?> clazz) {
		FixefidRecord fixefidRecord = clazz.getAnnotation(FixefidRecord.class);
		setType(fixefidRecord.recordType());
		setName(fixefidRecord.recordName());
		setDescription(fixefidRecord.recordDescription());
	}

	/**
	 * Check if the bean is annotated with <code>FixefidRecord.class</code>
	 * 
	 * @param clazz the class of the bean
	 * @throws RecordException if the bean is not annotated with <code>FixefidRecord.class</code>
	 */
	protected void checkBeanAnnotation(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(FixefidRecord.class)) {
            throw new RecordException(ErrorCode.RE13, "The class " + clazz.getSimpleName() + " is not annotated with FixefidRecord");
        } 
	}
	
	/**
	 * init the record len
	 * 
	 * @param clazz the class of the bean
	 */
	protected void initRecordLen(Class<?> clazz) {
		FixefidRecord fixefidRecord = clazz.getAnnotation(FixefidRecord.class);
		int recordLen = fixefidRecord.recordLen();
    	if (recordLen == 0) {
    		for (Field field : retrieveAllFields(new ArrayList<Field>(), clazz)) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(FixefidField.class)) {
                    FixefidField fixefidField = field.getAnnotation(FixefidField.class);
                    int fieldLen = fixefidField.fieldLen();
                    int fieldOccurs = fixefidField.fieldOccurs();
					recordLen += (fieldLen * fieldOccurs);
                }
            }
    	}
    	
    	this.recordLen = recordLen;
	}
	
	/**
	 * init the record way 
	 * 
	 * @param clazz the class of the bean
	 * @param recordWay the record way
	 */
	protected void initRecordWay(Class<?> clazz, RecordWay recordWay) {
		FixefidRecord fixefidRecord = clazz.getAnnotation(FixefidRecord.class);
		this.recordWay = recordWay != null ? recordWay : fixefidRecord.recordWay();
	}
	
	/**
	 * Init the fields map
	 * 
	 * @throws RecordException if a field has the name equals to "finalFiller" or some reflection field access problem
	 * @throws FieldException if the properties of the fields are in some non valid status
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initFieldsMap() throws RecordException, FieldException {
		Class<?> clazz = bean.getClass();
		
		List<Integer> ordinals = new ArrayList<Integer>();
		List<String> names = new ArrayList<String>();
		Map<Integer, List<Integer>> subOrdinalsMap = new HashMap<Integer, List<Integer>>();
		
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), clazz);
		Collections.sort(fields, new Comparator<Field>() {
			@Override
			public int compare(Field f1, Field f2) {
				int fieldOrdinal1 = ordinalForBeanField(f1);
				int fieldOrdinal2 = ordinalForBeanField(f2);
				
				
				if (fieldOrdinal1 == fieldOrdinal2) {
					fieldOrdinal1 = subOrdinalForBeanField(f1);
					fieldOrdinal2 = subOrdinalForBeanField(f2);
				}
				
				return fieldOrdinal1 - fieldOrdinal2;
			}
		});
		
		for (Field field : fields) {
            field.setAccessible(true);
            if (isAnnotationPresentForBeanField(field)) {
                String fieldName = field.getName();
                int fieldOrdinal = ordinalForBeanField(field);
                int fieldSubOrdinal = subOrdinalForBeanField(field);
                
                // check ordinals => must be unique
                if (ordinals.contains(fieldOrdinal)) {
                	List<Integer> subOrdinals = subOrdinalsMap.get(fieldOrdinal);
                	if (subOrdinals.contains(fieldSubOrdinal)) {
                		throw new RecordException(ErrorCode.RE14, "The ordinal " + fieldOrdinal + " + subOrdinal " + fieldSubOrdinal + " must be unique for the type (and super type) " + clazz.getName());
                	} else {
                		subOrdinals.add(fieldSubOrdinal);
                	}
                } else {
                	ordinals.add(fieldOrdinal);
                	
                	List<Integer> subOrdinals = new ArrayList<Integer>();
                	subOrdinals.add(fieldSubOrdinal);
                	subOrdinalsMap.put(fieldOrdinal, subOrdinals); 
                }
                
                // check names => must be unique and diff respect FINAL_FILLER_NAME
                if (FINAL_FILLER_NAME.equals(fieldName)) {
    				throw new RecordException(ErrorCode.RE15, "The field name=[" + FINAL_FILLER_NAME + "] is reserved");
    			} else  {
    				if (names.contains(fieldName)) {
    					throw new RecordException(ErrorCode.RE19, "The field name=[" + fieldName + "] must be unique for the type (and super type) " + clazz.getName());
    				} else {
    					names.add(fieldName);
    				}
    			} 
                
				FieldType fieldType = typeForBeanField(field);
				FieldType fieldTypeList = typeListForBeanField(field);
				if (FieldType.CMP.equals(fieldType) || (FieldType.LIST.equals(fieldType) && FieldType.CMP.equals(fieldTypeList))) {
					Map<String, List<FieldExtendedProperty>> mapCmpFieldExtendedProperties = null;
					if (mapFieldExtendedProperties != null) {
						mapCmpFieldExtendedProperties = new HashMap<String, List<FieldExtendedProperty>>();
						for (String key : mapFieldExtendedProperties.keySet()) { 
							 if (key.startsWith(fieldName + CMP_FIELD_NAME_SEP)) { 
								 mapCmpFieldExtendedProperties.put(key.substring(key.indexOf(CMP_FIELD_NAME_SEP) + 1), mapFieldExtendedProperties.get(key));
							 }
						}
					}
					
					int fieldOccurs = occursForBeanField(field);
					if (fieldOccurs != 1 && FieldType.CMP.equals(fieldType)) {
						throw new RecordException(ErrorCode.RE29, "The occurs of the bean field " + field.getName() + " of type " + 
								fieldType.name() + " must be equals to 1");
					} else if (fieldOccurs < 1 && FieldType.LIST.equals(fieldType)) {
						throw new RecordException(ErrorCode.RE33, "The occurs of the bean field " + field.getName() + " of type " + 
								fieldType.name() + " must be greater then zero");
					}
					
					try {
						Object value = field.get(bean);
						List list = null;
						if (FieldType.LIST.equals(fieldType)) {
							if (JAVA_UTIL_LIST.equals(field.getType().getName())) {
								list = (List) value;
								
								ParameterizedType genericType = (ParameterizedType) field.getGenericType();
								String typeArgumentName = genericType.getActualTypeArguments()[0].getTypeName();
								Class<?> typeArgumentClazz = Class.forName(typeArgumentName);
								int endIndex = fieldOccurs - list.size();
					    		for (int i = 0; i < endIndex; i++) {
									list.add(typeArgumentClazz.newInstance());
								}
							} else {
								throw new RecordException(ErrorCode.RE34, "The field " + field.getName() + " of type " + 
										fieldType.name() + " must be an instance of " + JAVA_UTIL_LIST);
							}
						} else {
							list = new ArrayList(); 
							list.add(value);
						}
						for (int fieldOccur = 1; fieldOccur <= fieldOccurs; fieldOccur++) {
							Map<String, com.github.parmag.fixefid.record.field.Field> cmpFieldsMap = 
								createBeanRecord(list.get(fieldOccur - 1), null, fieldExtendedProperties, mapCmpFieldExtendedProperties).getFieldsMap();
							for (String cmpFieldName : cmpFieldsMap.keySet()) {
								com.github.parmag.fixefid.record.field.Field cmpField = cmpFieldsMap.get(cmpFieldName);
								cmpField.setOccurIndex(fieldOccur); 
								fieldsMap.put(keyForFieldNameAndFieldOccur(fieldName, fieldOccur) + CMP_FIELD_NAME_SEP + cmpFieldName, cmpField);
							}
						}
					} catch (Exception e) {
						throw new RecordException(ErrorCode.RE1, e);
					}
				} else if (FieldType.LIST.equals(fieldType)) {
					List<FieldExtendedProperty> eps = normalizeFieldExtendedProperties(
	                		mapFieldExtendedProperties != null ? mapFieldExtendedProperties.get(fieldName) : null);
					
					int fieldOccurs = occursForBeanField(field);
					if (fieldOccurs < 1) {
						throw new RecordException(ErrorCode.RE33, "The occurs of the bean field " + field.getName() + " of type " + 
								fieldType.name() + " must be greater then zero");
					}
					
					for (int fieldOccur = 1; fieldOccur <= fieldOccurs; fieldOccur++) {
						fieldsMap.put(keyForFieldNameAndFieldOccur(fieldName, fieldOccur), new com.github.parmag.fixefid.record.field.Field (
							fieldName, fieldOrdinal, fieldSubOrdinal, fieldOccur, fieldTypeList, lenForBeanField(field), 
							mandatoryForBeanField(field), recordWay, defaultValueForBeanField(field), eps, 
							displayNameForBeanField(field), descriptionForBeanField(field)));
						
						syncValueFromBeanFieldToRecordField(null, field, bean, fieldsMap, fieldOccur);
					}
				} else {
					List<FieldExtendedProperty> eps = normalizeFieldExtendedProperties(
	                		mapFieldExtendedProperties != null ? mapFieldExtendedProperties.get(fieldName) : null);
					
					int fieldOccurs = occursForBeanField(field);
					if (fieldOccurs != 1) {
						throw new RecordException(ErrorCode.RE30, "The occurs of the bean field " + field.getName() + " of type " + 
								fieldType.name() + " must be equals to 1");
					}
					
					fieldsMap.put(keyForFieldNameAndFieldOccur(fieldName, DEF_OCCUR), new com.github.parmag.fixefid.record.field.Field (
						fieldName, fieldOrdinal, fieldSubOrdinal, DEF_OCCUR, fieldType, lenForBeanField(field), 
						mandatoryForBeanField(field), recordWay, defaultValueForBeanField(field), eps, 
						displayNameForBeanField(field), descriptionForBeanField(field)));
					
					syncValueFromBeanFieldToRecordField(null, field, bean, fieldsMap, DEF_OCCUR);
				}
				
            }
        }
		
	}
	
	/**
	 * Create a new instance of <code>BeanRecord</code>
	 * 
	 * @param bean the <code>bean</code> of the new <code>BeanRecord</code>
	 * @param record the formatted string of the new <code>BeanRecord</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the the new
	 * @param mapFieldExtendedProperties the extended properties of fields to override the relative property at record level
	 * 
	 * @return a new instance of <code>BeanRecord</code>
	 */
	protected BeanRecord createBeanRecord(Object bean, String record, List<FieldExtendedProperty> fieldExtendedProperties, 
			Map<String, List<FieldExtendedProperty>> mapFieldExtendedProperties) {
		return new BeanRecord(bean, record, fieldExtendedProperties, mapFieldExtendedProperties);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 */
	public void setValue(String fieldName, String value) {
		setValue(fieldName, value, defaultFieldOccursForFieldName(fieldName)); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> and <code>fieldOccur</code> params.
	 * If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain  the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain  2 integers
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 */
	public void setValue(String fieldName, String value, int... fieldOccur) {
		super.setValue(fieldName, value, fieldOccur); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap, fieldOccur);
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
		setValue(fieldName, value, truncate, defaultFieldOccursForFieldName(fieldName));
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> and <code>fieldOccur</code> params. 
	 *If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain  the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain  2 integers
	 * 
	 * @param fieldName the field proerty of the field to set the specified value
	 * @param value the value to set
	 * @param truncate If the <code>truncate</code> param is <code>true</code> and the len of the specified value is greater than the len of the 
	 * field, the specified value will be truncated at the len od the field. 
	 * @param fieldOccur the field occur to get the field
	 * @throws RecordException If the <code>truncate</code> param is <code>false</code> and the len of the specified value is 
	 * greater than the len of the field
	 */
	public void setValue(String fieldName, String value, boolean truncate, int... fieldOccur) throws RecordException {
		super.setValue(fieldName, value, truncate, fieldOccur);
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap, fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(String fieldName, Long value) throws FieldException {
		setValue(fieldName, value, defaultFieldOccursForFieldName(fieldName)); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> and <code>fieldOccur</code> params.
	 * If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain  the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain  2 integers
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(String fieldName, Long value, int... fieldOccur) throws FieldException {
		super.setValue(fieldName, value, fieldOccur); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap, fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(String fieldName, Integer value) throws FieldException {
		setValue(fieldName, value, defaultFieldOccursForFieldName(fieldName));
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> and <code>fieldOccur</code> params.
	 * If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain  the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain  2 integers
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(String fieldName, Integer value, int... fieldOccur) throws FieldException {
		super.setValue(fieldName, value, fieldOccur);
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap, fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(String fieldName, Double value) throws FieldException {
		setValue(fieldName, value, defaultFieldOccursForFieldName(fieldName)); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> and <code>fieldOccur</code> params.
	 * If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain  the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain  2 integers
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(String fieldName, Double value, int... fieldOccur) throws FieldException {
		super.setValue(fieldName, value, fieldOccur); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap, fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(String fieldName, Float value) throws FieldException {
		setValue(fieldName, value, defaultFieldOccursForFieldName(fieldName)); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> and <code>fieldOccur</code> params.
	 * If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain  the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain  2 integers
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(String fieldName, Float value, int... fieldOccur) throws FieldException {
		super.setValue(fieldName, value, fieldOccur); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap, fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(String fieldName, BigDecimal value) throws FieldException {
		setValue(fieldName, value, defaultFieldOccursForFieldName(fieldName));
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> and <code>fieldOccur</code> params.
	 *If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain  the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain  2 integers
	 * 
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(String fieldName, BigDecimal value, int... fieldOccur) throws FieldException {
		super.setValue(fieldName, value, fieldOccur);
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap, fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(String fieldName, Date value) throws FieldException {
		setValue(fieldName, value, defaultFieldOccursForFieldName(fieldName)); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> and <code>fieldOccur</code> params.
	 * If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain  the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain  2 integers
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(String fieldName, Date value, int... fieldOccur) throws FieldException {
		super.setValue(fieldName, value, fieldOccur); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap, fieldOccur);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(String fieldName, Boolean value) throws FieldException {
		setValue(fieldName, value, defaultFieldOccursForFieldName(fieldName)); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> and <code>fieldOccur</code> params.
	 * If the <code>fieldName</code> is composite, the <code>fieldOccur</code> must
	 * contain  the same number of components of the given <code>fieldName</code>. 
	 * For instance, if the <code>fieldName</code> is <code>addresses.location</code>, <code>fieldOccur</code> must contain  2 integers
	 * 
	 * @param fieldName the field proerty of the field to set the value
	 * @param value the value to set
	 * @param fieldOccur the field occur to get the field
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(String fieldName, Boolean value, int... fieldOccur) throws FieldException {
		super.setValue(fieldName, value, fieldOccur); 
		syncValueFromRecordFieldToBeanField(fieldName, bean, fieldsMap, fieldOccur);
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
		for (String key : fieldsMap.keySet()) {
		    int[] fieldOccurs = fieldOccursForKey(key);
			syncValueFromRecordFieldToBeanField(fieldNameForKey(key, fieldOccurs), bean, fieldsMap, fieldOccurs);
		} 
	}
	
	/**
	 * update the value of every fields from the backed bean the the record
	 */
	@SuppressWarnings("rawtypes")
	public void syncValuesFromBeanToRecord() {
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), bean.getClass());
		for (Field field : fields) {
			if (JAVA_UTIL_LIST.equals(field.getType().getName())) {
				try {
					field.setAccessible(true);
					List list = (List) field.get(bean);
					for (int i = 0; i < list.size(); i++) {
						syncValueFromBeanFieldToRecordField(null, field, bean, fieldsMap, i + 1);
					}
				} catch (Exception e) {
					throw new RecordException(ErrorCode.RE4, e);
				} 
			} else {
				syncValueFromBeanFieldToRecordField(null, field, bean, fieldsMap, DEF_OCCUR);
			}
		} 
	}
	
	/**
	 * The ordinal of the bean field param, retrieved from its <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the ordinal of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotation
	 */
	protected int ordinalForBeanField(Field f) {
		int ordinal = 0;
		
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			ordinal = a.fieldOrdinal();
		}
		
		return ordinal;
	}
	
	/**
	 * The sub-ordinal of the bean field param, retrieved from its <code>FixefidField.class<c/ode> annotation
	 * 
	 * @param f the bean field
	 * @return the sub-ordinal of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotation
	 */
	protected int subOrdinalForBeanField(Field f) {
		int subOrdinal = 0;
		
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			subOrdinal = a.fieldSubOrdinal();
		}
		
		return subOrdinal;
	}
	
	/**
	 * Returns true if the bena field param is annotated with the <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return true if the bena field param is annotated with the <code>FixefidField.class</code> annotation
	 */
	protected boolean isAnnotationPresentForBeanField(Field f) {
		return f.isAnnotationPresent(FixefidField.class);
	}
	
	/**
	 * The field type of the bean field param, retrieved from its <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the field type of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotation
	 */
	protected FieldType typeForBeanField(Field f) {
		FieldType fieldType = null;
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			fieldType = a.fieldType();
		}
		
		return fieldType;
	}
	
	/**
	 * The len of the bean field param, retrieved from its <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the len of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotation
	 */
	protected int lenForBeanField(Field f) {
		int len = 0;
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			len = a.fieldLen();
			if (len == 0) {
				throw new RecordException(ErrorCode.RE25, "The len of the field " + f.getName() + " must be greater than zero");
			}
		}
		
		return len;
	}
	
	/**
	 * The field mandatory of the bean field param, retrieved from its <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the field mandatory of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotation
	 */
	protected FieldMandatory mandatoryForBeanField(Field f) {
		FieldMandatory fieldMandatory = null;
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			fieldMandatory = a.fieldMandatory();
		}
		
		return fieldMandatory;
	}

	/**
	 * The default value of the bean field param, retrieved from its <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the default value of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotation
	 */
	protected String defaultValueForBeanField(Field f) {
		String defaultValue = "";
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			defaultValue = a.fieldDefaultValue();
		}
		
		return defaultValue;
	}
	
	/**
	 * The field occurs of the bean field param, retrieved from its <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the field occurs of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotation
	 */
	protected int occursForBeanField(Field f) {
		int occurs = 1;
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			occurs = a.fieldOccurs();
		}
		
		return occurs;
	}
	
	/**
	 * The field type list of the bean field param, retrieved from its <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the field type list of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotationn
	 */
	protected FieldType typeListForBeanField(Field f) {
		FieldType fieldTypeList = null;
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			fieldTypeList = a.fieldTypeList();
		}
		
		return fieldTypeList;
	}
	
	/**
	 * The display name of the bean field param, retrieved from its <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the display name of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotationn
	 */
	protected String displayNameForBeanField(Field f) {
		String fieldDisplayName = null;
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			fieldDisplayName = a.fieldDisplayName();
		}
		
		return fieldDisplayName;
	}
	
	/**
	 * The description of the bean field param, retrieved from its <code>FixefidField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the descriptiont of the <code>f</code> param, retrieved from its <code>FixefidField.class</code> annotationn
	 */
	protected String descriptionForBeanField(Field f) {
		String fieldDescription = null;
		FixefidField a = f.getAnnotation(FixefidField.class);
		if (a != null) {
			fieldDescription = a.fieldDescritption();
		}
		
		return fieldDescription;
	}
	
	@SuppressWarnings("rawtypes")
	private void syncValueFromBeanFieldToRecordField(String parentFieldName, Field field, Object bean,
			Map<String, com.github.parmag.fixefid.record.field.Field> fieldsMap, int... fieldOccur) {
		field.setAccessible(true);
		String fieldName = parentFieldName != null ? parentFieldName + CMP_FIELD_NAME_SEP + field.getName() : field.getName();
		int fieldOccurIndex = fieldName.split("\\" + CMP_FIELD_NAME_SEP).length - 1;
	    try {
	    	Object value = field.get(bean);
			if (isAnnotationPresentForBeanField(field) && value != null) {
				FieldType fieldType = typeForBeanField(field);
				FieldType fieldTypeList = typeListForBeanField(field);
				if (FieldType.CMP.equals(fieldType) || (FieldType.LIST.equals(fieldType) && FieldType.CMP.equals(fieldTypeList))) {
					if (FieldType.LIST.equals(fieldType)) {
						List list = null;
						if (JAVA_UTIL_LIST.equals(field.getType().getName())) {
							list = (List) value;
						} else {
							throw new RecordException(ErrorCode.RE34, "The field " + field.getName() + " of type " + 
									fieldType.name() + " must be an instance of " + JAVA_UTIL_LIST);
						}
						
						value = list.get(fieldOccur[fieldOccurIndex] - 1);
					}
					
					List<Field> cmpFields = retrieveAllFields(new ArrayList<Field>(), value.getClass());
					int[] nextFieldOccur = new int[fieldOccur.length + 1];
					for (int i = 0; i < fieldOccur.length; i++) {
						nextFieldOccur[i] = fieldOccur[i];
					}
					for (Field cmpField : cmpFields) {
						if (JAVA_UTIL_LIST.equals(cmpField.getType().getName())) {
							try {
								field.setAccessible(true);
								List list = (List) cmpField.get(value);
								for (int i = 0; i < list.size(); i++) {
									nextFieldOccur[nextFieldOccur.length - 1] = i + 1;
									syncValueFromBeanFieldToRecordField(fieldName, cmpField, value, fieldsMap, nextFieldOccur);
								}
							} catch (Exception e) {
								throw new RecordException(ErrorCode.RE4, e);
							} 
						} else {
							nextFieldOccur[nextFieldOccur.length - 1] = DEF_OCCUR;
							syncValueFromBeanFieldToRecordField(fieldName, cmpField, value, fieldsMap, nextFieldOccur);
						}
					}
				} else if (FieldType.LIST.equals(fieldType)) {
					List list = null;
					if (JAVA_UTIL_LIST.equals(field.getType().getName())) {
						list = (List) value;
					} else {
						throw new RecordException(ErrorCode.RE34, "The field " + field.getName() + " of type " + 
								fieldType.name() + " must be an instance of " + JAVA_UTIL_LIST);
					}
					
					if (list.size() >= fieldOccur[fieldOccurIndex]) {
						Object listValue = list.get(fieldOccur[fieldOccurIndex] - 1);
						syncValueFromBeanFieldToRecordField(fieldName, listValue.getClass().getName(), listValue, fieldsMap, fieldOccur);
					}
				} else {
				    syncValueFromBeanFieldToRecordField(fieldName, field.getType().getName(), value, fieldsMap, fieldOccur);
				}
			}
	    } catch (Exception e) {
	    	throw new RecordException(ErrorCode.RE4, e);
	    }
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void syncValueFromRecordFieldToBeanField(String fieldName, Object bean,
			Map<String, com.github.parmag.fixefid.record.field.Field> fieldsMap, int... fieldOccur) {
		if (FINAL_FILLER_NAME.equals(fieldName)) {
			return;
		}
		
		Object[] fieldAndBean = fieldAndBeanForName(fieldName, bean, fieldOccur); 
		Field field = (Field) fieldAndBean[0];
		bean = fieldAndBean[1];
		field.setAccessible(true);
		if (isAnnotationPresentForBeanField(field)) {
		    com.github.parmag.fixefid.record.field.Field rf = fieldsMap.get(keyForFieldNameAndFieldOccur(fieldName, fieldOccur));
		    String typeName = field.getType().getName();
		    Object value = valueForTypeName(field, rf, typeName);
		    
		    try {
		    	if (JAVA_UTIL_LIST.equals(typeName)) {
		    		List list = (List) field.get(bean);
		    		int rightFieldOccur = fieldOccur[fieldOccur.length -1];
		    		int endIndex = rightFieldOccur - list.size();
		    		for (int i = 0; i < endIndex; i++) {
						list.add(null);
					}
		    		list.set(rightFieldOccur - 1, value);
		    	} else {
		    		field.set(bean, value);
		    	}
	    	} catch (Exception e) {
				throw new RecordException(ErrorCode.RE3, e);
			} 
		}
	}
	
	private void syncValueFromBeanFieldToRecordField(String fieldName, String typeName, Object value, 
			Map<String, com.github.parmag.fixefid.record.field.Field> fieldsMap, int... fieldOccur) {
		boolean error = false;
	    
	    com.github.parmag.fixefid.record.field.Field rf = fieldsMap.get(keyForFieldNameAndFieldOccur(fieldName, fieldOccur));
	    
	    if (JAVA_LANG_STRING.equals(typeName)) {
	    	if (rf.isString()) {
	    		rf.setValue((String) value, true);
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
	    	if (rf.isDouble() || rf.isLenNormalized()) {
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
	    	if (rf.isLong() || rf.isLenNormalized()) {
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
	
	protected static List<Field> retrieveAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        retrieveAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
	
	@SuppressWarnings({"rawtypes" })
	private Object[] fieldAndBeanForName(String fieldName, Object bean, int... fieldOccur) {
		Object[] result = null;
		List<Field> fields = retrieveAllFields(new ArrayList<Field>(), bean.getClass());
		try {
			for (Field field : fields) {
				field.setAccessible(true);
	            if (isAnnotationPresentForBeanField(field)) {
					FieldType fieldType = typeForBeanField(field);
					FieldType fieldTypeList = typeListForBeanField(field);
					if (FieldType.CMP.equals(fieldType) && fieldName.startsWith(field.getName() + CMP_FIELD_NAME_SEP)) {
						int[] nextFieldOccur = Arrays.copyOfRange(fieldOccur, 1, fieldOccur.length);
						result = fieldAndBeanForName(fieldName.substring(fieldName.indexOf(CMP_FIELD_NAME_SEP) + 1), field.get(bean), nextFieldOccur);
						break;
					} else if (FieldType.LIST.equals(fieldType) && FieldType.CMP.equals(fieldTypeList) && fieldName.startsWith(field.getName() + CMP_FIELD_NAME_SEP)) {
						List list = (List) field.get(bean);
						int[] nextFieldOccur = Arrays.copyOfRange(fieldOccur, 1, fieldOccur.length);
						result = fieldAndBeanForName(fieldName.substring(fieldName.indexOf(CMP_FIELD_NAME_SEP) + 1), list.get(fieldOccur[0] - 1), nextFieldOccur);
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
	
	private Object valueForTypeName(Field field, com.github.parmag.fixefid.record.field.Field rf, String typeName) {
		boolean error = false;
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
	    	if (rf.isDouble() || rf.isLenNormalized()) {
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
	    	if (rf.isLong() || rf.isLenNormalized()) {
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
	    } else if (JAVA_UTIL_LIST.equals(typeName)) {
	    	try {
				ParameterizedType genericType = (ParameterizedType) field.getGenericType();
				String typeArgumentName = genericType.getActualTypeArguments()[0].getTypeName();
				value = valueForTypeName(field, rf, typeArgumentName);
	    	} catch (Exception e) {
				throw new RecordException(ErrorCode.RE3, e);
			}
	    } else {
	    	error = true;
	    }
	    
	    if (error) {
	    	throw new RecordException(ErrorCode.RE2, "Cannot retrieve value for the given java type name " + typeName + " from " + rf.toString());
	    } 
	    
	    return value;
	}

	/**
	 * @return true if the instance of this record is a CSV record
	 */
	@Override
	protected boolean isCSVRecord() {
		return false;
	}
}
