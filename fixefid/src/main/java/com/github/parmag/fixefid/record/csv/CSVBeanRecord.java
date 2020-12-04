package com.github.parmag.fixefid.record.csv;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.github.parmag.fixefid.record.BeanRecord;
import com.github.parmag.fixefid.record.ErrorCode;
import com.github.parmag.fixefid.record.RecordException;
import com.github.parmag.fixefid.record.RecordWay;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldType;

/**
 * The <code>CSVBeanRecord</code> represents a CSV fields formatted text backed by a java bean.
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
public class CSVBeanRecord extends BeanRecord {
	private CSVSep recordSep;
	private String recordOtherSep;
	private CSVEnc recordEnclosing;
	private boolean recordEncloseAllFields;

	/**
	 * Constructs a new <code>CSVBeanRecord</code> that represents the CSV fields indicated by the <code>bean</code> parameter.
	 * The lenght of this record is calculated with the sum of the lenght of every
	 * field. The value of every field is initialized with a default value (if present)
	 * 
	 * @param bean the <code>bean</code> of this <code>CSVBeanRecord</code>
	 */
	public CSVBeanRecord(Object bean) {
		this(bean, null, null, null, null);
	}

	/**
	 * Constructs a new <code>CSVBeanRecord</code> that represents the CSV fields indicated by the <code>bean</code> parameter.
	 * The lenght of this record is calculated with the sum of the lenght of every
	 * field. The value of every field is initialized with the formatted value present in the relative position of the <code>csvRecord</code> 
	 * parameter
	 * 
	 * @param bean the <code>bean</code> of this <code>CSVBeanRecord</code>
	 * @param csvRecord the CSV string of this <code>CSVBeanRecord</code>
	 */
	public CSVBeanRecord(Object bean, String csvRecord) {
		this(bean, csvRecord, null, null, null);
	}

	/**
	 * Constructs a new <code>CSVBeanRecord</code> that represents the CSV fields indicated by the <code>bean</code> parameter.
	 * The value of every field is initialized with the formatted value present in the
	 * relative position of the <code>csvRecord</code> parameter. To every field of the record are applied the extended properties
	 * present in the <code>fieldExtendedProperties</code> parameter. Only the following properties are permitted at record level:
	 * <ul>
	 * <li><code>FieldExtendedPropertyType.LPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.RPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.VALIDATOR</code></li>
	 * </ul>
	 * 
	 * @param bean the <code>bean</code> of this <code>CSVBeanRecord</code>
	 * @param csvRecord the CSV string of this <code>CSVBeanRecord</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 */
	public CSVBeanRecord(Object bean, String csvRecord, List<FieldExtendedProperty> fieldExtendedProperties) {
		this(bean, csvRecord, fieldExtendedProperties, null, null);
	}

	/**
	 * Constructs a new <code>CSVBeanRecord</code> that represents the CSV fields indicated by the <code>bean</code> parameter.
	 * The value of every field is initialized with the formatted value present in the
	 * relative position of the <code>csvRecord</code> parameter. To every field of the record are applied the extended properties
	 * present in the <code>fieldExtendedProperties</code> parameter. Only the following properties are permitted at record level:
	 * <ul>
	 * <li><code>FieldExtendedPropertyType.LPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.RPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.VALIDATOR</code></li>
	 * </ul>
	 * The extended property of every field can be overridden with the extended property present in the <code>mapFieldExtendedProperties</code>
	 * 
	 * @param bean the <code>bean</code> of this <code>CSVBeanRecord</code>
	 * @param csvRecord the CSV string of this <code>CSVBeanRecord</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 * @param mapFieldExtendedProperties the extended properties of fields to override the relative property at record level
	 */
	public CSVBeanRecord(Object bean, String csvRecord, List<FieldExtendedProperty> fieldExtendedProperties,
			Map<String, List<FieldExtendedProperty>> mapFieldExtendedProperties) {
		this(bean, csvRecord, fieldExtendedProperties, mapFieldExtendedProperties, null);
	}

	/**
	 * Constructs a new <code>CSVBeanRecord</code> that represents the CSV fields indicated by the <code>bean</code> parameter.
	 * The value of every field is initialized with the formatted value present in the
	 * relative position of the <code>csvRecord</code> parameter. To every field of the record are applied the extended properties
	 * present in the <code>fieldExtendedProperties</code> parameter. Only the following properties are permitted at record level:
	 * <ul>
	 * <li><code>FieldExtendedPropertyType.LPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.RPAD</code></li>
	 * <li><code>FieldExtendedPropertyType.VALIDATOR</code></li>
	 * </ul>
	 * The extended property of every field can be overridden with the extended property present in the <code>mapFieldExtendedProperties</code>
	 * 
	 * @param bean the <code>bean</code> of this <code>CSVBeanRecord</code>
	 * @param csvRecord the formatted string of this <code>CSVBeanRecord</code>
	 * @param fieldExtendedProperties the extended properties of field applied to every fields of the record
	 * @param mapFieldExtendedProperties the extended properties of fields to override the relative property at record level
	 * @param recordWay the record way. This overrides what's defined at annotation level (see FixefidRecord annotation)
	 */
	public CSVBeanRecord(Object bean, String csvRecord, List<FieldExtendedProperty> fieldExtendedProperties,
			Map<String, List<FieldExtendedProperty>> mapFieldExtendedProperties, RecordWay recordWay) {
		super(bean, csvRecord, fieldExtendedProperties, mapFieldExtendedProperties, recordWay);
	}
	
	/**
	 * Init the record bean
	 * 
	 * @param bean the bean
	 * @param record the record
	 * @param recordWay the record way
	 */
	protected void initBean(Object bean, String record, RecordWay recordWay) {
		Class<?> clazz = bean.getClass();
		checkBeanAnnotation(clazz);
		
		FixefidCSVRecord fixefidCSVRecord = clazz.getAnnotation(FixefidCSVRecord.class);
		recordSep = fixefidCSVRecord.recordSep();
		recordOtherSep = fixefidCSVRecord.recordOtherSep();
		recordEnclosing = fixefidCSVRecord.recordEnc();
		recordEncloseAllFields = fixefidCSVRecord.encloseAllFields();
		
		super.initBean(bean, record, recordWay); 
	}
	
	/**
	 * Check if the bean is annotated with <code>FixefidCSVRecord.class</code>
	 * 
	 * @param clazz the class of the bean
	 * @throws RecordException if the bean is not annotated with <code>FixefidCSVRecord.class</code>
	 */
	protected void checkBeanAnnotation(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(FixefidCSVRecord.class)) {
            throw new RecordException(ErrorCode.RE24, "The class " + clazz.getSimpleName() + " is not annotated with FixefidCSVRecord");
        } 
	}
	
	/**
	 * CSV Record has initial len equals to zero
	 * 
	 * @param clazz the class of the bean
	 */
	protected void initRecordLen(Class<?> clazz) {
		this.recordLen = 0;
	}

	/**
	 * init the record way 
	 * 
	 * @param clazz the class of the bean
	 * @param recordWay the record way
	 */
	protected void initRecordWay(Class<?> clazz, RecordWay recordWay) {
		FixefidCSVRecord fixefidCSVRecord = clazz.getAnnotation(FixefidCSVRecord.class);
		this.recordWay = recordWay != null ? recordWay : fixefidCSVRecord.recordWay();
	}
	
	/**
	 * CSV Bean Record has not final filler
	 */
	protected void addFiller() {
		// NOT FOR CSV RECORD
	}
	
	/**
	 * The ordinal of the bean field param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the ordinal of the <code>f</code> param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 */
	protected int ordinalForBeanField(Field f) {
		int ordinal = 0;
		
		FixefidCSVField a = f.getAnnotation(FixefidCSVField.class);
		if (a != null) {
			ordinal = a.fieldOrdinal();
		}
		
		return ordinal;
	}
	
	/**
	 * The sub-ordinal of the bean field param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the sub-ordinal of the <code>f</code> param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 */
	protected int subOrdinalForBeanField(Field f) {
		int subOrdinal = 0;
		
		FixefidCSVField a = f.getAnnotation(FixefidCSVField.class);
		if (a != null) {
			subOrdinal = a.fieldSubOrdinal();
		}
		
		return subOrdinal;
	}
	
	/**
	 * Returns true if the bena field param is annotated with the <code>FixefidCSVField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return true if the bena field param is annotated with the <code>FixefidCSVField.class</code> annotation
	 */
	protected boolean isAnnotationPresentForBeanField(Field f) {
		return f.isAnnotationPresent(FixefidCSVField.class);
	}
	
	/**
	 * The field type of the bean field param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the field type of the <code>f</code> param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 */
	protected FieldType typeForBeanField(Field f) {
		FieldType fieldType = null;
		FixefidCSVField a = f.getAnnotation(FixefidCSVField.class);
		if (a != null) {
			fieldType = a.fieldType();
		}
		
		return fieldType;
	}
	
	/**
	 * The len of the bean field param, retrieved from its <code>FixefidCSVField.class</code> annotation.
	 * Cause the <code>FixefidCSVField.class</code> annotation doesn't have the len property, this method returns 
     * the len of the default value, from the <code>FixefidCSVField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the len of the default value of <code>f</code> param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 */
	protected int lenForBeanField(Field f) {
		return defaultValueForBeanField(f).length();
	}
	
	/**
	 * The field mandatory of the bean field param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the field mandatory of the <code>f</code> param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 */
	protected FieldMandatory mandatoryForBeanField(Field f) {
		FieldMandatory fieldMandatory = null;
		FixefidCSVField a = f.getAnnotation(FixefidCSVField.class);
		if (a != null) {
			fieldMandatory = a.fieldMandatory();
		}
		
		return fieldMandatory;
	}
	
	/**
	 * The default value of the bean field param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 * 
	 * @param f the bean field
	 * @return the default value of the <code>f</code> param, retrieved from its <code>FixefidCSVField.class</code> annotation
	 */
	protected String defaultValueForBeanField(Field f) {
		String defaultValue = "";
		FixefidCSVField a = f.getAnnotation(FixefidCSVField.class);
		if (a != null) {
			defaultValue = a.fieldDefaultValue();
		}
		
		return defaultValue;
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
}
