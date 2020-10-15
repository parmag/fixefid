package com.github.parmag.fixefid.record;

/**
 * Thrown to indicate that a <code>Record</code> has no valid status
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.0
 *
 */
public class RecordException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private ErrorCode errorCode;
	
	/**
     * Constructs an <code>RecordException</code> with the 
     * specified detail message. 
     *
     * @param   errorCode   the error code.
     * @param   message   the detail message.
     */
	public RecordException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
     * Constructs an <code>RecordException</code> with the 
     * specified cause. 
     *
     * @param   errorCode   the error code.
     * @param   cause   the specified cause.
     */
	public RecordException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	/**
     * Constructs an <code>RecordException</code> with the 
     * specified detail message and cause
     *
     * @param   errorCode   the error code.
     * @param   message   the detail message.
     * @param   cause   the specified cause.
     */
	public RecordException(ErrorCode errorCode, String message, Throwable cause) {
		super(message, cause);
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
}
