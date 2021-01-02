package com.github.parmag.fixefid.record.format;

/**
 * A simple boolean format with the true and false values.
 * 
 * @author Giancarlo Parma
 * 
 * @since 2.0
 *
 */
public class SimpleBooleanFormat implements BooleanFormat {

	private String trueValue;
	private String falseValue;

	/**
	 * Constructs a simple boolean format
	 * 
	 * @param trueValue the true value
	 * @param falseValue the false value
	 */
	public SimpleBooleanFormat(String trueValue, String falseValue) {
		this.trueValue = trueValue;
		this.falseValue = falseValue;
	}

	@Override
	public String format(Boolean value) {
		return value != null && value.booleanValue() ? trueValue : falseValue;
	}

	@Override
	public Boolean parse(String value) {
		return trueValue.equals(value) ? true : false;
	}

	/**
	 * @return the true value
	 */
	public String getTrueValue() {
		return trueValue;
	}

	/**
	 * @param trueValue the true value
	 */
	public void setTrueValue(String trueValue) {
		this.trueValue = trueValue;
	}

	/**
	 * @return the false value
	 */
	public String getFalseValue() {
		return falseValue;
	}

	/**
	 * @param falseValue the false value
	 */
	public void setFalseValue(String falseValue) {
		this.falseValue = falseValue;
	}

}
