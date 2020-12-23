package com.github.parmag.fixefid.test.csv;

import java.util.ArrayList;
import java.util.List;

import com.github.parmag.fixefid.record.csv.FixefidCSVField;
import com.github.parmag.fixefid.record.csv.FixefidCSVRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidCSVRecord
public class FlagCSV {
	@FixefidCSVField(fieldOrdinal = 1, fieldType = FieldType.AN)
	private String name;
	@FixefidCSVField(fieldOrdinal = 2, fieldType = FieldType.LIST, fieldOccurs = 10)
	private List<String> flags = new ArrayList<String>();
	@FixefidCSVField(fieldOrdinal = 3, fieldType = FieldType.AN)
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
