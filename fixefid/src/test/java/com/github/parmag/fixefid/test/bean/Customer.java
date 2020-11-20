package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class Customer extends Person {
	@FixefidField(fieldOrdinal = 11, fieldLen = 10, fieldType = FieldType.N)
	private Long customerId;
	
	@FixefidField(fieldOrdinal = 12, fieldSubOrdinal = 1, fieldLen = 50, fieldType = FieldType.AN)
	private String email;
	
	@FixefidField(fieldOrdinal = 12, fieldSubOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String phone;
}
