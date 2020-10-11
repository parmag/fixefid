package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class Student extends Person {
	@FixefidField(fieldOrdinal = 11, fieldLen = 10, fieldType = FieldType.N)
	private Long studentId;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
}
