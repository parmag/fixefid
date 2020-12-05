package com.github.parmag.fixefid.record.format;

public class SimpleBooleanFormat implements BooleanFormat {

	private String trueValue;
	private String falseValue;

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

	public String getTrueValue() {
		return trueValue;
	}

	public void setTrueValue(String trueValue) {
		this.trueValue = trueValue;
	}

	public String getFalseValue() {
		return falseValue;
	}

	public void setFalseValue(String falseValue) {
		this.falseValue = falseValue;
	}

}
