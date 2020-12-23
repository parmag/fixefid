package com.github.parmag.fixefid.test.csv;

import com.github.parmag.fixefid.record.csv.FixefidCSVField;
import com.github.parmag.fixefid.record.csv.FixefidCSVRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidCSVRecord
public class CarWithCheckup extends Car {

	@FixefidCSVField(fieldOrdinal = 9, fieldType = FieldType.CMP)
	private Checkup checkup = new Checkup();

	public Checkup getCheckup() {
		return checkup;
	}

	public void setCheckup(Checkup checkup) {
		this.checkup = checkup;
	}

}
