package com.github.parmag.fixefid.test.csv;

import java.util.Date;

import com.github.parmag.fixefid.record.csv.FixefidCSVField;
import com.github.parmag.fixefid.record.csv.FixefidCSVRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidCSVRecord
public class Car {
	@FixefidCSVField(fieldOrdinal = 0, fieldType = FieldType.AN)
	private String name;
	@FixefidCSVField(fieldOrdinal = 1, fieldType = FieldType.AN)
	private String model;
	@FixefidCSVField(fieldOrdinal = 2, fieldType = FieldType.N)
	private Double weight;
	@FixefidCSVField(fieldOrdinal = 3, fieldType = FieldType.N)
	private Integer length;
	@FixefidCSVField(fieldOrdinal = 4, fieldType = FieldType.N)
	private Integer width;
	@FixefidCSVField(fieldOrdinal = 5, fieldType = FieldType.N)
	private Integer height;
	@FixefidCSVField(fieldOrdinal = 6, fieldType = FieldType.N, fieldDisplayName = "The Car's speed", fieldDescritption = "The Car's speed must be minor of 200 KM/H")
	private Integer speed;
	@FixefidCSVField(fieldOrdinal = 7, fieldType = FieldType.AN)
	private Date productionDate;
	@FixefidCSVField(fieldOrdinal = 8, fieldType = FieldType.AN)
	private Boolean used;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public Boolean getUsed() {
		return used;
	}
	public void setUsed(Boolean used) {
		this.used = used;
	}
}
