package com.github.parmag.fixefid.test.csv;

import java.math.BigDecimal;
import java.util.Date;

import com.github.parmag.fixefid.record.csv.FixefidCSVField;
import com.github.parmag.fixefid.record.csv.FixefidCSVRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidCSVRecord
public class Checkup {

	@FixefidCSVField(fieldOrdinal = 0, fieldType = FieldType.AN)
	private Date checkupDate;
	@FixefidCSVField(fieldOrdinal = 1, fieldType = FieldType.N)
	private BigDecimal cost;
	@FixefidCSVField(fieldOrdinal = 2, fieldType = FieldType.AN)
	private String description;
	
	public Date getCheckupDate() {
		return checkupDate;
	}
	public void setCheckupDate(Date checkupDate) {
		this.checkupDate = checkupDate;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
