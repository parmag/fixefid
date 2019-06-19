package com.github.parmag.fixefid.record.field;

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
	
	private int code;
	
	/**
     * Constructs an <code>FieldException</code> with the 
     * specified message. 
     *
     * @param   message  the  message.
     */
	public FieldException(String message) {
		super(message);
	}

	/**
     * Constructs an <code>FieldException</code> with the 
     * specified cause. 
     *
     * @param   cause   the specified cause.
     */
	public FieldException(Throwable cause) {
		super(cause);
	}

	/**
     * Constructs an <code>FieldException</code> with the 
     * specified message and cause
     *
     * @param   message   the message.
     * @param   cause   the specified cause.
     */
	public FieldException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
     * Constructs an <code>FieldException</code> with the 
     * specified code and message
     *
     * @param   code   the specified code.
     * @param   message   the detail message.
     */
	public FieldException(int code, String message) {
		super(message);
		this.code = code;
	}

	/**
     * Constructs an <code>FieldException</code> with the 
     * specified code and cause
     *
     * @param   code   the detail code.
     * @param   cause   the specified cause.
     */
	public FieldException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	/**
     * Constructs an <code>FieldException</code> with the 
     * specified code, message and cause
     *
     * @param   code   the detail code.
     * @param   message   the detail message.
     * @param   cause   the specified cause.
     */
	public FieldException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	/**
	 * @return the code of this <code>FieldException</code>
	 */
	public int getCode() {
		return code;
	}
}
