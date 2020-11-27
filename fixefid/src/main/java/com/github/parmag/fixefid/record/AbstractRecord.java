package com.github.parmag.fixefid.record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import com.github.parmag.fixefid.record.csv.CSVEnc;
import com.github.parmag.fixefid.record.csv.CSVSep;
import com.github.parmag.fixefid.record.field.Field;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldType;
import com.github.parmag.fixefid.record.field.FieldValidationInfo;

/**
 * Abstract implementation of a fixed fields formatted text
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.1.0
 *
 */
public abstract class AbstractRecord {
	protected static final String FINAL_FILLER_NAME = "finalFiller";
	protected static final String NO_VALIDATION_INFO = "NO VALIDATION INFO";
	
	protected final Map<String, Field> fieldsMap = new LinkedHashMap<String, Field>();
	protected RecordWay recordWay; 
	protected int recordLen;
	protected List<FieldExtendedProperty> fieldExtendedProperties;
	
	abstract protected void initFieldsMap() throws RecordException, FieldException;
	
	/**
	 * check field extended properties at record level and set the list 
	 * 
	 * @param fieldExtendedProperties field extended properties at record level
	 */
	protected void initFieldExtendedProperties(List<FieldExtendedProperty> fieldExtendedProperties) {
		if (fieldExtendedProperties != null) {
			for (FieldExtendedProperty fep : fieldExtendedProperties) {
				FieldExtendedPropertyType repType = fep.getType();
				if (!(FieldExtendedPropertyType.LPAD.equals(repType) ||
						FieldExtendedPropertyType.RPAD.equals(repType) ||
						FieldExtendedPropertyType.VALIDATOR.equals(repType))) {
					throw new RecordException(ErrorCode.RE6, "Field Extended Property=[" + repType.name() + " not valid at record level.");
				}
			}
			
			this.fieldExtendedProperties = fieldExtendedProperties;
		} 
	}
	
	/**
	 * mix the list <code>eps</code> of field extended properties with the relative list at record level.
	 * If the same property is present at record and field level, the second one win
	 * 
	 * @param eps field extended properties at field level
	 * @return a list of field extended properties
	 */
	protected List<FieldExtendedProperty> normalizeFieldExtendedProperties(List<FieldExtendedProperty> eps) {
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
		
		return eps;
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
			fieldsMap.put(FINAL_FILLER_NAME, new Field(FINAL_FILLER_NAME, fieldsMap.size() + 1, 0,
				FieldType.AN, fillerLen, FieldMandatory.NO, recordWay, null, null));
		}
	}
	
	/**
	 * @return the record len
	 */
	public int getRecordLen() {
		return recordLen;
	}
	
	/**
	 * @return the RecordWay of this record
	 */
	public RecordWay getRecordWay() {
		return recordWay;
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
			throw new RecordException(ErrorCode.RE7, "Input record=[" + record + "] diff from toString record=[" + toStringRecord + "]");
		}
	}
	
	/**
	 * Apply to the fields of this record, the formatted values present in the <code>csvRecord</code> parameter.
	 * Every value must be separated with the <code>CSVSep.COMMA</code>.
     * A value must be enclosed with <code>CSVEnc.DOUBLE_QUOTE</code> if contains <code>CSVSep.COMMA</code>. 
     * Each of the embedded enclosing characters <code>CSVEnc.DOUBLE_QUOTE</code> must be represented by a pair of double-enclosing characters.
	 * 
	 * @param csvRecord the formatted csv string
	 * @throws RecordException if the the <code>csvRecord</code> is malformed or the formatted csv string obtained from <code>toStringCSV</code> method diff from the <code>csvRecord</code> parameter
	 */
	public void initRecordCSV(String csvRecord) throws RecordException {
		initRecordCSV(csvRecord, CSVSep.COMMA, null, CSVEnc.DOUBLE_QUOTE);
	}
	
	/**
	 * Apply to the fields of this record, the formatted values present in the <code>csvRecord</code> parameter.
	 * Every value must be separated with the <code>sep</code> param.
     * If the <code>sep</code> param is null, the default sep is <code>CSVSep.COMMA</code>. Every value can be (or not) enclosed with 
     * the <code>enclosing</code> param. If the <code>enclosing</code> param is null, the default enclosing is <code>CSVEnc.DOUBLE_QUOTE</code>. 
     * A value must be enclosed if contains the char sep. Inside an eclosed value,  each of the embedded enclosing characters must be represented by a pair of 
     * double-enclosing characters.
	 * 
	 * @param csvRecord the formatted csv string
	 * @param sep the field separator. If null the sep is <code>CSVSep.COMMA</code>
     * @param otherSep the field separator if <code>sep</code> param is <code>CSVSep.OTHER</code>. Size must be eq 1. If null the sep is <code>CSVSep.COMMA</code>
     * @param enclosing the enclosing char. If null the enclosing char is <code>CSVEnc.DOUBLE_QUOTE</code>
	 * @throws RecordException if the the <code>csvRecord</code> is malformed or the separator size not eq 1 or the formatted csv string obtained from <code>toStringCSV</code> method diff from the <code>csvRecord</code> parameter
	 */
	public void initRecordCSV(String csvRecord, CSVSep sep, String otherSep, CSVEnc enclosing) throws RecordException {
		sep = sep == null ? CSVSep.COMMA : sep;
		if (CSVSep.OTHER.equals(sep) && otherSep == null) {
			otherSep = CSVSep.COMMA.getSep();
		}
		String delimiter = CSVSep.OTHER.equals(sep) ? otherSep : sep.getSep();
		if (delimiter.length() != 1) {
			throw new RecordException(ErrorCode.RE21, "sep not valid: " + delimiter + ". Must be size eq 1");
		}
		
		enclosing = enclosing == null ? CSVEnc.DOUBLE_QUOTE : enclosing;
		String encString = enclosing.getEnc();
		
		// [0] => field opened without enclosing
		// [1] => field opened with enclosing
		boolean[] flags = {false, false};
		
		List<String> values = new ArrayList<String>(); 
		StringBuilder valueBuilder = new StringBuilder();
		
		csvRecord.codePoints()
			.mapToObj(c -> String.valueOf((char) c))
			.forEachOrdered(s -> {
				if (!flags[0] && !flags[1]) {
					// field not opened
					if (s.equals(" ")) {
						// space skipped
					} else if (s.equals(encString)) {
						// field opened with enclosing
						valueBuilder.append(s);
						flags[0] = false; // field opened without enclosing
						flags[1] = true; // field opened with enclosing
					} else if (s.equals(delimiter)) {
						// field closed, delimiter skipped and added empty value
						values.add("");
						flags[0] = false; // field opened without enclosing
						flags[1] = false; // field opened with enclosing
					} else {
						// field opened without enclosing
						valueBuilder.append(s);
						flags[0] = true; // field opened without enclosing
						flags[1] = false; // field opened with enclosing
					}
				} else if (flags[0]) {
					// field opened without enclosing
					if (s.equals(delimiter)) {
						// field closed, delimiter skipped and added value
						values.add(valueBuilder.toString());
						valueBuilder.delete(0, valueBuilder.length());
						flags[0] = false; // field opened without enclosing
						flags[1] = false; // field opened with enclosing
					} else {
						valueBuilder.append(s);
					}
				}  else if (flags[1]) {
					// field opened with enclosing
					if (s.equals(delimiter)) {
						int encStringCount = 0;
						int len = valueBuilder.length();
						while (len > 0 && valueBuilder.substring(len - 1, len).equals(encString)) {
							encStringCount++;
							len--;
						}
						
						if (encStringCount == valueBuilder.length()) {
							if (encStringCount % 2 == 0) {
								// field closed, delimiter skipped and added value
								values.add(valueBuilder.toString());
								valueBuilder.delete(0, valueBuilder.length());
								flags[0] = false; // field opened without enclosing
								flags[1] = false; // field opened with enclosing
							} else {
								valueBuilder.append(s);
							}
						} else if (encStringCount % 2 != 0) {
							// field closed, delimiter skipped and added value
							values.add(valueBuilder.toString());
							valueBuilder.delete(0, valueBuilder.length());
							flags[0] = false; // field opened without enclosing
							flags[1] = false; // field opened with enclosing
						} else {
							valueBuilder.append(s);
						}
					} else {
						valueBuilder.append(s);
					}
				}
			});
		
		// final value
		if (valueBuilder.length() > 0) {
			values.add(valueBuilder.toString());
		}
		
		int recordValuesSize = fieldsMap.size();
		if (fieldsMap.containsKey(FINAL_FILLER_NAME)) {
			recordValuesSize--;
		}
		if (values.size() != recordValuesSize) {
			throw new RecordException(ErrorCode.RE20, "the CSV record is malformed: found " + values.size() + " values but record must contains " + recordValuesSize + " values.");
		}
		
		int[] valueIndex = {0};
		fieldsMap.forEach((fieldName, field) -> {
			if (!FINAL_FILLER_NAME.equals(fieldName)) {
				String value = values.get(valueIndex[0]);
				
				if (value.startsWith(encString)) {
					value = value.substring(1, value.length());
					
					if (value.endsWith(encString)) {
						value = value.substring(0, value.length() - 1);
					} else {
						throw new RecordException(ErrorCode.RE22, "the CSV record is malformed: found value=[" + value + "] which starts with enclosing string " + encString + " but not end.");
					}
					
					// [0] => prev char was encString
					boolean encStringFlags[] = {false};
					if (value.contains(encString)) {
						value.codePoints()
							.mapToObj(c -> String.valueOf((char) c))
							.forEachOrdered(s -> {
								if (s.equals(encString)) {
									if (encStringFlags[0]) {
										// found double-enclosing characters => OK
										encStringFlags[0] = false;
									} else {
										encStringFlags[0] = true;
									}
								} else {
									if (encStringFlags[0]) {
										// found single-enclosing characters => KO
										throw new RecordException(ErrorCode.RE23, "the CSV record is malformed: found value=[" + 
												values.get(valueIndex[0]) + "]  with single-enclosing string " + encString + 
												". Expected double-enclosing string.");
									}
								}
							});
					}
					
					value = value.replace(encString + encString, encString);
				}
				
				field.setValue(value, false);
				valueIndex[0] = valueIndex[0] + 1;
			}
		});
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
			throw new RecordException(ErrorCode.RE8, "Not valid len=[" + len + "] for " + info + " record=[" + record + 
				"]. Expected len=[" + getRecordLen() + "]");
		}
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
			rf.setValue(record.substring(index, index += rf.getLen()), false); 
		}
	}
	
	/**
	 * Returns the field represented by the <code>fieldName</code> param
	 *  
	 * @param fieldName the field name to get the field
	 * @return the field represented by the <code>fieldName</code> param
	 * @throws RecordException if the <code>fieldName</code> param doesn't represent any field of the record
	 */
	public Field getRecordField(String fieldName) throws RecordException {
		if (!fieldsMap.containsKey(fieldName)) {
			throw new RecordException(ErrorCode.RE9, "Unknown fieldName=[" + fieldName + "]");
		}
		
		return fieldsMap.get(fieldName);
	}
	
	/**
	 * Returns the len of the record represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the property of the field to know the len
	 * @return the len of the record represented by the <code>fieldName</code> param
	 */
	public int getFieldLen(String fieldName) {
		return getRecordField(fieldName).getLen();
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
     * Returns a <code>String</code> object representing this record. The returned string is composed with the formatted
     * value of every field of this record
     *
     * @return  a string representation of this record
     * @throws RecordException it the status of this record is ERROR
     */
	@Override
	public String toString() throws RecordException {
		if (isErrorStatus()) {
			throw new RecordException(ErrorCode.RE10, "Record has Error status. Cause: " + prettyPrintErrorValidationInfo());
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
     * Returns a CSV <code>String</code> object representing this record. The returned string is composed with the formatted unpadded
     * value of every field of this record. Every value is separated with the <code>CSVSep.COMMA</code>.
     * The fields aren't enclosing, eccept those which contain the char <code>CSVSep.COMMA</code>. 
     * In this case the fields are enclosing with <code>CSVEnc.DOUBLE_QUOTE</code>. Each of the embedded enclosing <code>CSVEnc.DOUBLE_QUOTE</code> 
     * characters is represented by a pair of double-enclosing characters.
     *
     * @return  a CSV string representation of this record
     * @throws RecordException it the status of this record is ERROR
     */
	public String toStringCSV() throws RecordException {
		return toStringCSV(CSVSep.COMMA, null, CSVEnc.DOUBLE_QUOTE, false);
	}
	
	/**
     * Returns a CSV <code>String</code> object representing this record. The returned string is composed with the formatted
     * unpadded value of every field of this record. Every value is separated with the <code>sep</code> param.
     * If the <code>sep</code> param is null, the default sep is <code>CSVSep.COMMA</code>. If <code>encloseAllFields</code> is true,
     * every field of the record is enclosed with the <code>enclosing</code> param. If the <code>enclosing</code> param is null,
     * the default enclosing is <code>CSVEnc.DOUBLE_QUOTE</code>. If the <code>encloseAllFields</code> param is false the fields aren't enclosing, 
     * eccept they contain the char sep. Each of the embedded char enclosing characters is represented by a pair of 
     * double-enclosing characters.
     *
     * @param sep the field separator. If null the sep is <code>CSVSep.COMMA</code>
     * @param otherSep the field separator if <code>sep</code> param is <code>CSVSep.OTHER</code>. Size must be eq 1. If null the default sep is <code>CSVSep.COMMA</code>
     * @param enclosing the enclosing char. If null the enclosing char is <code>CSVEnc.DOUBLE_QUOTE</code>
     * @param encloseAllFields if true every field of the record is enclosed with the enclosing char, otherwise only field which contains sep or enclosing chars
     * 
     * @return  a CSV string representation of this record
     * @throws RecordException it the status of this record is ERROR or the separator size not eq 1
     */
	public String toStringCSV(CSVSep sep, String otherSep, CSVEnc enclosing, boolean encloseAllFields) throws RecordException {
		if (isErrorStatus()) {
			throw new RecordException(ErrorCode.RE10, "Record has Error status. Cause: " + prettyPrintErrorValidationInfo());
		}
		
		sep = sep == null ? CSVSep.COMMA : sep;
		if (CSVSep.OTHER.equals(sep) && otherSep == null) {
			otherSep = CSVSep.COMMA.getSep();
		}
		String delimiter = CSVSep.OTHER.equals(sep) ? otherSep : sep.getSep();
		if (delimiter.length() != 1) {
			throw new RecordException(ErrorCode.RE21, "sep not valid: " + delimiter + ". Must be size eq 1");
		}
		
		enclosing = enclosing == null ? CSVEnc.DOUBLE_QUOTE : enclosing;
		String encString = enclosing.getEnc();
		
		StringJoiner sj = new StringJoiner(delimiter);
		fieldsMap.forEach((fieldName, field) -> {
			if (!FINAL_FILLER_NAME.equals(fieldName)) {
				String value = field.getValueWithNoPAD();
				if (encloseAllFields || value.contains(delimiter)) {
					if (value.contains(encString)) {
						value = value.replace(encString, encString + encString);
					}
					
					value = encString + value + encString;
				}
				
				sj.add(value);
			}
		});
		
		return sj.toString();
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
	 * Returns a <code>String</code> object representing the pretty print of the field represented by the <code>fieldName</code> param.
	 * The pretty print is composed as following:
	 * <p>
	 * name=[index][offset][len][value][validation status][validation msg (if present)]
	 * 
	 * @return the pretty print of the field represented by the <code>fieldName</code> param.
	 * @throws RecordException if the <code>fieldName</code> param doesn't represent any field of the record
	 */
	public String prettyPrint(String fieldName) {
		if (!fieldsMap.containsKey(fieldName)) {
			throw new RecordException(ErrorCode.RE9, "Unknown fieldName=[" + fieldName + "]");
		}
		
		StringBuilder sb = new StringBuilder();
		int offset = 1;
		for (String key : fieldsMap.keySet()) {
			Field rf = fieldsMap.get(key);
			int len = rf.getLen(); 
			if (key.equals(fieldName)) {
				sb.append(key + "=[" + rf.getIndex() + "][" + offset + "][" + len + "][" + rf.getValue() + "]");
				FieldValidationInfo vi = rf.getValidationInfo();
				if (vi != null) {
					sb.append("[" + vi.getValidationStatus().name() + "][" + vi.getValidationMessage() + "]");
				} else {
					sb.append("[" + NO_VALIDATION_INFO + "]");
				}
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
	 * Apply the method {@link String#toUpperCase} to the value of every field of type <code>FieldType.AN</code> of this record
	 */
	public void toUpperCase() {
		for (String key : fieldsMap.keySet()) {
			fieldsMap.get(key).toUpperCase();
		}
	}
	
	/**
	 * Apply the method toRemoveAccents to the value of every field of type <code>FieldType.AN</code> of this record.
	 * <p>
	 * Every character with accent present in the value of a field of type <code>FieldType.AN</code>, is replaced with the relative
	 * character without accent. For example the character ï¿½ is replaced with the character a
	 */
	public void toRemoveAccents() {
		for (String key : fieldsMap.keySet()) {
			fieldsMap.get(key).toRemoveAccents();
		}
	}
	
	/**
	 * Apply the encoding with the Charset "US-ASCII" to the value of every field of type <code>FieldType.AN</code> of this record.
	 * <p>
	 * Every character out of the Charset "US-ASCII" present in the value of a field of type <code>FieldType.AN</code>, 
	 * is replaced with the character ?. For example the character ï¿½ is replaced with the character ?
	 */
	public void toAscii() {
		for (String key : fieldsMap.keySet()) {
			fieldsMap.get(key).toAscii();
		}
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
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &ge; 10</code>
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
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is not present and the <code>len &lt; 10</code>
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
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &ge; 10</code>
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
	 * the <code>FieldExtendedPropertyType.DECIMAL_FORMAT</code> is present and the <code>len &lt; 10</code>
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
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 */
	public void setValue(String fieldName, String value) {
		getRecordField(fieldName).setValue(value, true); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param. 
	 * 
	 * @param fieldName the field property of the field to set the specified value
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
				throw new RecordException(ErrorCode.RE11, "Cannot set value=[" + value + "] for field=[" + fieldName + "]: not valid len (expected" + fieldLen + ")");
			} 
		}
		
		setValue(fieldName, value);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(String fieldName, Long value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(String fieldName, Integer value) throws FieldException {
		getRecordField(fieldName).setValue(value);
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(String fieldName, Double value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to set the value
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
	 * @param fieldName the field property of the field to set the value
	 * @param value the value to set
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(String fieldName, Date value) throws FieldException {
		getRecordField(fieldName).setValue(value); 
	}
	
	/**
	 * Set the specified value to the field represented by the <code>fieldName</code> param
	 * 
	 * @param fieldName the field property of the field to set the value
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
		
		Field rf = getRecordField(fieldName);
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
		
		Field rf = getRecordField(fieldName);
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
	
	/**
	 * Returns the orderd map <code>fieldName, value</code> of this record's fields
	 * 
	 * @return the orderd map <code>fieldName, value</code> of this record's fields
	 */
	public Map<String, String> valuesMap() {
		Map<String, String> valuesMap = new LinkedHashMap<String, String>();
		fieldsMap.forEach((fieldName, field) -> valuesMap.put(fieldName, field.getValue()));
		return valuesMap;
	}
	
	/**
	 * Returns the orderd list of the values of this record's fields
	 * 
	 * @return the orderd list of the values of this record's fields
	 */
	public List<String> values() {
		List<String> values = new ArrayList<String>();
		fieldsMap.forEach((fieldName, field) -> values.add(field.getValue()));
		return values;
	}
	
	/**
	 * Returns the orderd list of the names of this record's fields
	 * 
	 * @return the orderd list of the names of this record's fields
	 */
	public List<String> names() {
		List<String> names = new ArrayList<String>();
		fieldsMap.forEach((fieldName, field) -> names.add(fieldName));
		return names;
	}
}
