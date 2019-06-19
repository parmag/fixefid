package com.github.parmag.fixefid.record.field;

/**
 * The field validation info. It's composed by a status, a code and a message
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.0
 */
public class FieldValidationInfo {
	
	/**
	 * The enum that represents the field validation status
	 * 
	 * @author Giancarlo Parma
	 *
	 * @since 1.0
	 */
	public static enum RecordFieldValidationStatus {
		/**
		 * The field validation status INFO
		 */
		INFO,
		/**
		 * The field validation status WARN
		 */
		WARN,
		/**
		 * The field validation status ERROR
		 */
		ERROR;
	}
	
	private RecordFieldValidationStatus validationStatus;
	private String validationMessage;
	private int validationCode;
	
	/**
	 * Constructs a new <code>FieldValidationInfo</code> with the validation status INFO, an empty message and code equals zero
	 */
	public FieldValidationInfo() {
		this(RecordFieldValidationStatus.INFO, "");
	}

	/**
	 * Constructs a new <code>FieldValidationInfo</code> with the <code>validationStatus</code> param, 
	 * the <code>validationMessage</code> param and code equals zero
	 * 
	 * @param validationStatus the validation status
	 * @param validationMessage the validation message
	 */
	public FieldValidationInfo(RecordFieldValidationStatus validationStatus, String validationMessage) {
		this(validationStatus, validationMessage, 0);
	}
	
	/**
	 * Constructs a new <code>FieldValidationInfo</code> with the <code>validationStatus</code> param, 
	 * the <code>validationMessage</code> param and the <code>validationCode</code> param
	 * 
	 * @param validationStatus the validation status
	 * @param validationMessage the validation message
	 * @param validationCode the validation code
	 */
	public FieldValidationInfo(RecordFieldValidationStatus validationStatus, String validationMessage, int validationCode) {
		this.validationCode = validationCode; 
		this.setValidationStatus(validationStatus);
		this.setValidationMessage(validationMessage);
	}

	/**
	 * @return the validation status of this validation info
	 */
	public RecordFieldValidationStatus getValidationStatus() {
		return validationStatus;
	}

	/**
	 * Apply the <code>validationStatus</code> param to this validation info
	 * 
	 * @param validationStatus the validation status param
	 */
	public void setValidationStatus(RecordFieldValidationStatus validationStatus) {
		this.validationStatus = validationStatus;
	}

	/**
	 * @return the validation message of this validation info
	 */
	public String getValidationMessage() {
		return validationMessage;
	}

	/**
	 * Apply the <code>validationMessage</code> param to this validation info
	 * 
	 * @param validationMessage the validation message param
	 */
	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	
	/**
	 * @return the validation code of this validation info
	 */
	public int getValidationCode() {
		return validationCode;
	}

	/**
	 * Apply the <code>validationCode</code> param to this validation info
	 * 
	 * @param validationCode the validation code param
	 */
	public void setValidationCode(int validationCode) {
		this.validationCode = validationCode;
	}
	
	/**
     * The string rapresentation of this validation info, composed with
     * 
     * <pre>status + code + message</pre>
     *
     * @return  the string itself.
     */
	@Override
	public String toString() {
		return validationStatus.name() + " " + validationCode + " " + validationMessage;
	}

}
