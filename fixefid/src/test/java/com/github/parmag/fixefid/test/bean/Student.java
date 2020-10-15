package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class Student extends Person {
	@FixefidField(fieldOrdinal = 11, fieldLen = 10, fieldType = FieldType.N)
	private long studentId;
	
	@FixefidField(fieldOrdinal = 12, fieldLen = 2, fieldType = FieldType.N)
	private int level;
	
	@FixefidField(fieldOrdinal = 13, fieldLen = 1, fieldType = FieldType.AN)
	private boolean active;

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
