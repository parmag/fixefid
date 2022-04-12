package com.github.parmag.fixefid.record.field;

import com.github.parmag.fixefid.record.ErrorCode;

/**
 * Thrown to indicate that a <code>Field</code> has no valid status
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.0
 *
 */
public class FieldException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private ErrorCode errorCode;
	private String fieldName;
	
	/**
     * Constructs an <code>FieldException</code> with the 
     * specified message. 
     *
     * @param   errorCode   the error code.
     * @param   message  the  message.
     */
	public FieldException(String fieldName, ErrorCode errorCode, String message) {
		super(message);
		this.setFieldName(fieldName); 
		this.errorCode = errorCode;
	}

	/**
     * Constructs an <code>FieldException</code> with the 
     * specified cause. 
     *
     * @param   errorCode   the error code.
     * @param   cause   the specified cause.
     */
	public FieldException(String fieldName, ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.fieldName = fieldName;
		this.errorCode = errorCode;
	}

	/**
     * Constructs an <code>FieldException</code> with the 
     * specified message and cause
     *
     * @param   errorCode   the error code.
     * @param   message   the message.
     * @param   cause   the specified cause.
     */
	public FieldException(String fieldName, ErrorCode errorCode, String message, Throwable cause) {
		super(message, cause);
		this.fieldName = fieldName;
		this.errorCode = errorCode; 
	}
	
	@Override
	public String getLocalizedMessage() {
		return getErrorCode() + " - " + super.getLocalizedMessage();
	}
	
	@Override
	public String getMessage() {
		return getErrorCode() + " - " + super.getMessage();
	}

	/**
	 * @return the error code
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the error code
	 */
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
