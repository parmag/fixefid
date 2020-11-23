package com.github.parmag.fixefid.record.csv;

/**
 * The enum which represents the fields separator of a CSV Record
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
public enum CSVSep {
	/**
	 * The comma "," separator
	 */
	COMMA (","),
	/**
	 * The semicolon ";" separator
	 */
	SEMICOLON (";"),
	/**
	 * The space " " separator
	 */
	SPACE (" "),
	/**
	 * The tab "\t" separator
	 */
	TAB ("\t"),
	/**
	 * The separator can be specified with a char
	 */
	OTHER (null);
	
	private String sep;

	private CSVSep(String sep) {
		this.sep = sep;
	}
	
	public String getSep() {
		return sep;
	}
}
