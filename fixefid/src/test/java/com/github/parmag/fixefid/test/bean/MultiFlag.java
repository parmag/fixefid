package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class MultiFlag {
	@FixefidField(fieldOrdinal = 1, fieldLen = 19, fieldType = FieldType.N)
	private Long id;
	@FixefidField(fieldOrdinal = 2, fieldLen = 40, fieldType = FieldType.CMP)
	private Flag flag = new Flag();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Flag getFlag() {
		return flag;
	}
	public void setFlag(Flag flag) {
		this.flag = flag;
	}
	

}
