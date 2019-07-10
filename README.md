# Fixefid
Fixefid is a Java library for working with flat fixed formatted text files. 
It differs from other tools focusing on:
<ul>
  <li>Easy-to-use</li>
  <li>Numeric and Alphanumeric data types</li>
  <li>Custom formatters</li>
  <li>Custom validators</li>
  <li>In/Out fields mandatory awareness</li>
  <li>Fields normalization</li>
  <li>LPad/RPad fields</li>
  <li>Automatic record filler</li>
  <li>Default fields values</li>
  <li>Record status</li>
</ul>


## Getting started
The JDK compliance level is 1.6 or greater. To include jar library in your java project, download or add dependency from <a href="https://mvnrepository.com/artifact/com.github.parmag/fixefid" target="_blank">MVN Repository</a>.

To include maven dependency of fixefid version 1.0.0 in your pom.xml, add this:

```
<dependency>
    <groupId>com.github.parmag</groupId>
    <artifactId>fixefid</artifactId>
    <version>1.0.0</version>
</dependency>
```

To define a simple person record with three fixed fields, first name, last name and age, create an enum like this:

```
import java.util.List;

import com.github.parmag.fixefid.record.field.FieldExtendedProperty;
import com.github.parmag.fixefid.record.field.FieldMandatory;
import com.github.parmag.fixefid.record.field.FieldProperty;
import com.github.parmag.fixefid.record.field.FieldType;

public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN),
	lastName(25, FieldType.AN),
	age(3, FieldType.N);
	
	private int fieldLen; 
	private FieldType fieldType;
	
	private PersonRecordField(int fieldLen, FieldType fieldType) {
		this.fieldLen = fieldLen;
		this.fieldType = fieldType; 
	}

	@Override
	public int fieldLen() {
		return fieldLen;
	}

	@Override
	public FieldType fieldType() {
		return fieldType;
	}

	@Override
	public FieldMandatory fieldMandatory() {
		return FieldMandatory.INOUT;
	}

	@Override
	public String fieldDefaultValue() {
		return null;
	}

	@Override
	public List<FieldExtendedProperty> fieldExtendedProperties() {
		return null;
	}
}
```

then create a new empty record, fill the fields and get the string representation:

```
Record<PersonRecordField> record = new Record<PersonRecordField>(PersonRecordField.class);
record.setValue(PersonRecordField.firstName, "Paul");
record.setValue(PersonRecordField.lastName, "Robinson");
record.setValue(PersonRecordField.age, 51);
String recordAsString = record.toString();
```

The system out of the recordAsString is as follow:

```
Paul                     Robinson                 051
```

by default the alphanumeric fields are right padded with spaces, whereas the numeric fields are left padded with zeroes. This default behavior can be changed with the extended properties.

If you have the record as string (read for example from a file), create a record with the string e read the fields:

```
Record<PersonRecordField> record = new Record<PersonRecordField>(recordAsString, PersonRecordField.class);
String firstName = record.getValueAsString(PersonRecordField.firstName);
String lastName = record.getValueAsString(PersonRecordField.lastName);
Integer age = record.getValueAsInteger(PersonRecordField.age);
```

this is a very simple example for getting started. 

You can create more complex records with formatters for decimal, date, boolean. You can create custom formatters, change the default behavior for the pad fields, create custom validators and many others features.

## Record len
The default record len is the sum of every field len. For example for the person record above, the len is 25 + 25 + 3 = 53. If the record len must be greater than the default fields len sum, you can set it using the constructor like this:
```
Record<PersonRecordField> record = new Record<PersonRecordField>(PersonRecordField.class, 100);
```
in this case a default filler of 47 space will be added at the end of the record. If you set the value of a field with a len greater than the field len, an exception is thrown.

## Field mandatory
The field mandatory can be set with the enum FieldMandatory. In the PersonRecordField example above, all the fields are FieldMandatory.INOUT. To set it field by field, change the enum like this:

```
public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN, FieldMandatory.IN),
	lastName(25, FieldType.AN, FieldMandatory.OUT),
	age(3, FieldType.N, FieldMandatory.NO);
	
	private int fieldLen; 
	private FieldType fieldType;
	private FieldMandatory fieldMandatory;
	
	private PersonRecordField(int fieldLen, FieldType fieldType, FieldMandatory fieldMandatory) {
		this.fieldLen = fieldLen;
		this.fieldType = fieldType; 
		this.fieldMandatory = fieldMandatory;
	}
	
	...............................
	
	@Override
	public FieldMandatory fieldMandatory() {
		return fieldMandatory;
	}
	
	.................
```

The field mandatory is related to the record way. By default the record way is set to RecordWay.IN. That's when the record is read from an input source. When the record is write to an output source, the record way should be set to RecordWay.OUT. The record way can be set by record constructor like this:

```
Record<PersonRecordField> record = new Record<PersonRecordField>(RecordWay.IN, PersonRecordField.class);
```
 or by the setter method 
 
 ```
 record.setRecordWay(RecordWay.IN);
 ```

This is usefull when a field is optional when you read the record but mandatory when you write the record (that's an output field). In general:

<ul>
	<li>FieldMandatory.IN => the field is mandatory if the record way is RecordWay.IN</li>
	<li>FieldMandatory.OUT => the field is mandatory if the record way is RecordWay.OUT</li>
	<li>FieldMandatory.INOUT => the field is always mandatory</li>
	<li>FieldMandatory.NO => the field is never mandatory</li>
</ul>

if the toString method is invoked but a field mandatory has no value, an exception is thrown.

## Field default value
Every field can have a default value. In the PersonRecordField example above, all the fields don't have the default value. To set it field by field, change the enum like this:

```
public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN, "Unknown"),
	lastName(25, FieldType.AN, "Unknown"),
	age(3, FieldType.N, "999");
	
	private int fieldLen; 
	private FieldType fieldType;
	private String defaultValue;
	
	private PersonRecordField(int fieldLen, FieldType fieldType, String defaultValue) {
		this.fieldLen = fieldLen;
		this.fieldType = fieldType; 
		this.defaultValue = defaultValue;
	}
	
	...............................
	
	@Override
	public String fieldDefaultValue() {
		return defaultValue;
	}
	
	.................
```

## Alphanumeric data type with Date and Boolean formatters
The alphanumeric data type FieldType.AN can be managed like a Java Date or Boolean. For instance, to add two fields to the PersonRecordField example above, birth date and vip, change the enum like this:

```
public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN, null),
	lastName(25, FieldType.AN, null),
	age(3, FieldType.N, null),
	birthDate(8, FieldType.AN,  Arrays.asList(
		new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH)))),
	vip(1, FieldType.AN, Arrays.asList(
		new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new BooleanFormat() {
			@Override
			public String format(Boolean value) {
				return (value != null && value.booleanValue()) ? "Y" : "N";
			}

			@Override
			public Boolean parse(String value) {
				return "Y".equals(value) ? true : false;
			}
		})));
	
	private int fieldLen; 
	private FieldType fieldType;
	private List<FieldExtendedProperty> fieldExtendedProperties;
	
	private PersonRecordField(int fieldLen, FieldType fieldType, List<FieldExtendedProperty> fieldExtendedProperties) {
		this.fieldLen = fieldLen;
		this.fieldType = fieldType; 
		this.fieldExtendedProperties = fieldExtendedProperties;
	}
	
	...............................
	
	@Override
	public List<FieldExtendedProperty> fieldExtendedProperties() {
		return fieldExtendedProperties;
	}
	
	.................
```

now birth date and vip can be managed like Date and Boolean. To set the values: 

```
Record<PersonRecordField> record = new Record<PersonRecordField>(PersonRecordField.class);
record.setValue(PersonRecordField.firstName, "Paul");
record.setValue(PersonRecordField.lastName, "Robinson");
record.setValue(PersonRecordField.age, 51);
record.setValue(PersonRecordField.birthDate, new Date());
record.setValue(PersonRecordField.vip, true);
String recordAsString = record.toString();
```

The system out of the recordAsString is as follow:

```
Paul                     Robinson                 05109072019Y
```
 and get the values:
 
```
Record<PersonRecordField> record = new Record<PersonRecordField>(recordAsString, PersonRecordField.class);
Date birthDate = record.getValueAsDate(PersonRecordField.birthDate);
Boolean vip = record.getValueAsBoolean(PersonRecordField.vip);
```

## Alphanumeric data type with Custom Formatter
The alphanumeric data type FieldType.AN can be used with a custom formatter. For instance, to create a custom formatter for the last name field, change the PersonRecordField enum like this:

```
public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN, null),
	lastName(25, FieldType.AN, Arrays.asList(
		new FieldExtendedProperty(FieldExtendedPropertyType.CUSTOM_FORMAT, new CustomFormat() {
			@Override
			public String format(String value) {	
				// do something
				return value;
			}

			@Override
			public String parse(String value) {
				// do something
				return value;
			}
		}))),
	age(3, FieldType.N, null);
	
	private int fieldLen; 
	private FieldType fieldType;
	private List<FieldExtendedProperty> fieldExtendedProperties;
	
	private PersonRecordField(int fieldLen, FieldType fieldType, List<FieldExtendedProperty> fieldExtendedProperties) {
		this.fieldLen = fieldLen;
		this.fieldType = fieldType; 
		this.fieldExtendedProperties = fieldExtendedProperties;
	}
	
	...............................
	
	@Override
	public List<FieldExtendedProperty> fieldExtendedProperties() {
		return fieldExtendedProperties;
	}
	
	.................
```

## Numeric data type
The numeric data type FieldType.N can be managed like a Java Integer, Long, Float, Double and BigDecimal. Fot the last three, a DecimalFormat must be used. All can be positive or negative. With the DecimalFormat, can be used the extended property REMOVE_DECIMAL_SEPARATOR to be compliant with the COBOL numeric data type. 

The len of the field and the DecimalFormat determines the Java type:

<ul>
	<li>Len < 10 and no DecimalFormat: Integer</li>
	<li>Len >= 10 and no DecimalFormat: Long</li>
	<li>Len < 10 and DecimalFormat: Float</li>
	<li>Len >= 10 and DecimalFormat: Double</li>	
</ul>

moreover if the DecimalFormat is present, the field can always be managed like a BigDecimal, no matter its len.

For instance, to add the field amount to the PersonRecordField example above, change the enum like this:

```
public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN, null),
	lastName(25, FieldType.AN, null),
	age(3, FieldType.N, null),
	amount(10, FieldType.N, Arrays.asList(
		new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, 
			new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)))));
	
	private int fieldLen; 
	private FieldType fieldType;
	private List<FieldExtendedProperty> fieldExtendedProperties;
	
	private PersonRecordField(int fieldLen, FieldType fieldType, List<FieldExtendedProperty> fieldExtendedProperties) {
		this.fieldLen = fieldLen;
		this.fieldType = fieldType; 
		this.fieldExtendedProperties = fieldExtendedProperties;
	}
	
	...............................
	
	@Override
	public List<FieldExtendedProperty> fieldExtendedProperties() {
		return fieldExtendedProperties;
	}
	
	.................
```
now amount can be managed like Double. To set the values: 

```
Record<PersonRecordField> record = new Record<PersonRecordField>(PersonRecordField.class);
record.setValue(PersonRecordField.firstName, "Paul");
record.setValue(PersonRecordField.lastName, "Robinson");
record.setValue(PersonRecordField.age, 51);
record.setValue(PersonRecordField.amount, 1500.99);
```

The system out of the recordAsString is as follow:

```
Paul                     Robinson                 0510001500.99
```
 and get the value:
 
```
Record<PersonRecordField> record = new Record<PersonRecordField>(recordAsString, PersonRecordField.class);
Double amount = record.getValueAsDouble(PersonRecordField.amount);
```

if the extended proporty REMOVE_DECIMAL_SEPARATOR is added to the field amount like this:
```
...................
amount(10, FieldType.N, Arrays.asList(
                new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, true),
		new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, 
			new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)))));
...................
```
the result is:
 ```
Paul                     Robinson                 0510000150099
```

## Field padding
The field padding can be managed with the relative extended property. By default the alphanumeric fields are right padded with spaces, whereas the numeric fields are left padded with zeroes. For instance to change the default padding behavior of the field lastName of the PersonRecordField example above, change the enum like this:

```
.................
	lastName(25, FieldType.AN, Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " "))),	
.................
```
the result is:
 ```
Paul                                      Robinson0510000150099
```

moreover the padding behavior can be set at Record level like this:

```
Record<PersonRecordField> record = new Record<PersonRecordField>(RecordWay.IN, null, PersonRecordField.class, null, Arrays.asList(
	new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " ")));
```
in this case all the fields of the record are left padded with spaces. Anyway, the extended property set by field level, always win vs the same extended property set by record level.

## Javadoc
Here the <a href="./fixefid/doc" target="_blank">Javadoc</a>
