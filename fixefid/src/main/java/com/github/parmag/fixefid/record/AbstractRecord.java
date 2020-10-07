package com.github.parmag.fixefid.record;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.parmag.fixefid.record.field.Field;
import com.github.parmag.fixefid.record.field.FieldException;
import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldExtendedPropertyType;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldType;
import com.github.parmag.fixefid.record.field.FieldValidationInfo;

public abstract class AbstractRecord {
	protected static final String FINAL_FILLER_NAME = "finalFiller";
	protected static final String NO_VALIDATION_INFO = "NO VALIDATION INFO";
	
	protected final Map<String, Field> fieldsMap = new LinkedHashMap<String, Field>();
	protected RecordWay recordWay; 
	protected int recordLen;
	protected List<FieldExtendedProperty> fieldExtendedProperties;
	
	abstract protected void initFieldsMap() throws RecordException, FieldException;
	
	protected void initFieldExtendedProperties(List<FieldExtendedProperty> fieldExtendedProperties) {
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
	}
	
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
			fieldsMap.put(FINAL_FILLER_NAME, new Field(FINAL_FILLER_NAME, fieldsMap.size() + 1,
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
			throw new RecordException("Input record=[" + record + "] diff from toString record=[" + toStringRecord + "]");
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
			throw new RecordException("Unknown fieldName=[" + fieldName + "]");
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
}
