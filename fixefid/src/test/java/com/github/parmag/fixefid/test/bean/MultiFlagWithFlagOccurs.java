package com.github.parmag.fixefid.test.bean;

import java.util.ArrayList;
import java.util.List;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class MultiFlagWithFlagOccurs {
	@FixefidField(fieldOrdinal = 1, fieldLen = 19, fieldType = FieldType.N)
	private Long id;
	@FixefidField(fieldOrdinal = 2, fieldLen = 40, fieldOccurs = 2, fieldType = FieldType.LIST, fieldTypeList = FieldType.CMP)
	private List<Flag> flags = new ArrayList<Flag>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Flag> getFlag() {
		return flags;
	}
	public void setFlag(List<Flag> flags) {
		this.flags = flags;
	}
	

}
