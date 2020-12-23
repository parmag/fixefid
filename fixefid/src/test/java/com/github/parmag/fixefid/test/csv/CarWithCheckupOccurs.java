package com.github.parmag.fixefid.test.csv;

import java.util.ArrayList;
import java.util.List;

import com.github.parmag.fixefid.record.csv.FixefidCSVField;
import com.github.parmag.fixefid.record.csv.FixefidCSVRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidCSVRecord
public class CarWithCheckupOccurs extends Car {

	@FixefidCSVField(fieldOrdinal = 9, fieldType = FieldType.LIST, fieldOccurs = 3, fieldTypeList = FieldType.CMP)
	private List<Checkup> checkups = new ArrayList<Checkup>();

	public List<Checkup> getCheckups() {
		return checkups;
	}

	public void setCheckups(List<Checkup> checkups) {
		this.checkups = checkups;
	}

}
