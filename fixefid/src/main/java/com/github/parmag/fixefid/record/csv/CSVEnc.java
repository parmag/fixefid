package com.github.parmag.fixefid.record.csv;

/**
 * The enum which represents the fields enclosing char of a CSV Record
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
public enum CSVEnc {
	/**
	 * The double-quote """ enclosing char
	 */
	DOUBLE_QUOTE ("\""),
	/**
	 * The single-quote "'" enclosing char
	 */
	SINGLE_QUOTE ("'");
	
	private String enc;

	private CSVEnc(String enc) {
		this.enc = enc;
	}
	
	public String getEnc() {
		return enc;
	}
}
