package com.github.parmag.fixefid.test.format;

import com.github.parmag.fixefid.record.format.CustomFormat;

public class UpperLowerCustomFormat implements CustomFormat {

	public UpperLowerCustomFormat() {
	}

	@Override
	public String format(String value) {
		return value.toUpperCase();
	}

	@Override
	public String parse(String value) {
		return value.toLowerCase();
	}
}
