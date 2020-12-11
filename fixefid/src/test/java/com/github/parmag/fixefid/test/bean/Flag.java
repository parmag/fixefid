package com.github.parmag.fixefid.test.bean;

import java.util.ArrayList;
import java.util.List;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class Flag {
	@FixefidField(fieldOrdinal = 1, fieldLen = 20, fieldType = FieldType.AN)
	private String name;
	@FixefidField(fieldOrdinal = 2, fieldLen = 1, fieldType = FieldType.LIST, fieldOccurs = 10)
	private List<String> flags = new ArrayList<String>();
	@FixefidField(fieldOrdinal = 3, fieldLen = 10, fieldType = FieldType.AN)
	private String program;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getFlags() {
		return flags;
	}
	public void setFlags(List<String> flags) {
		this.flags = flags;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}

}
