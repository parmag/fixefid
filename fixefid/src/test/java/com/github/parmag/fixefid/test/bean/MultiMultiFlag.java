package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class MultiMultiFlag {
	@FixefidField(fieldOrdinal = 1, fieldLen = 20, fieldType = FieldType.AN)
	private String multiMultiName;
	@FixefidField(fieldOrdinal = 2, fieldLen = 59, fieldType = FieldType.CMP)
	private MultiFlag multiFlag = new MultiFlag();

	public String getMultiMultiName() {
		return multiMultiName;
	}

	public void setMultiMultiName(String multiMultiName) {
		this.multiMultiName = multiMultiName;
	}

	public MultiFlag getMultiFlag() {
		return multiFlag;
	}

	public void setMultiFlag(MultiFlag multiFlag) {
		this.multiFlag = multiFlag;
	}

}
