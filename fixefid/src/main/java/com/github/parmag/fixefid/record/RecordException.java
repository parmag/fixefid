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
	
	/**
     * Constructs an <code>RecordException</code> with the 
     * specified detail message. 
     *
     * @param   message   the detail message.
     */
	public RecordException(String message) {
		super(message);
	}

	/**
     * Constructs an <code>RecordException</code> with the 
     * specified cause. 
     *
     * @param   cause   the specified cause.
     */
	public RecordException(Throwable cause) {
		super(cause);
	}

	/**
     * Constructs an <code>RecordException</code> with the 
     * specified detail message and cause
     *
     * @param   message   the detail message.
     * @param   cause   the specified cause.
     */
	public RecordException(String message, Throwable cause) {
		super(message, cause);
	}
}
