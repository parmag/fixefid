# Fixefid
Fixefid is a Java library for working with flat fixed formatted text files. 
It differs from other tools focusing on:
<ul>
  <li>Easy-to-use</li>
  <li>Numeric and Alphanumeric data types</li>
  <li>Custom formatters</li>  
  <li>In/Out fields mandatory awareness</li>
  <li>Fields normalization</li>
  <li>LPad/RPad fields</li>
  <li>Automatic record filler</li>
  <li>Default fields values</li>
  <li>Record status and custom validators</li>
  <li>Record pretty print</li>
</ul>

## What's new 1.1.0

The version 1.1.0 includes:

<ul>
	<li>Record defined by Java Bean</li>
	<li>Error codes</li>
	<li>JUnit version 4.13.1</li>
	<li>Minor bug fixes</li>
</ul>

## Getting started
The JDK compliance level is 1.6 or greater. To include jar library in your java project, download or add dependency from <a href="https://mvnrepository.com/artifact/com.github.parmag/fixefid" target="_blank">MVN Repository</a>.

To include maven dependency of fixefid version 1.1.0 in your pom.xml, add this:

```
<dependency>
    <groupId>com.github.parmag</groupId>
    <artifactId>fixefid</artifactId>
    <version>1.1.0</version>
</dependency>
```
Every record can be defined by Enum or Java Bean. 

## Getting started by examples

Here some <a href="https://github.com/parmag/fixefid-examples" target="_blank">Examples</a> about how using the library:
<ul>
	<li><a href="https://github.com/parmag/fixefid-examples/tree/main/fixefidbean" target="_blank">Fixefidbean</a>: An example about how using fixefid to define a record with a Java Bean</li>
</ul>

## Getting started with Enum

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

## Getting started with Java Bean

To define a simple person record with three fixed fields, first name, last name and age, create a java bean like this:

```
import java.math.BigDecimal;
import java.util.Date;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;
import com.github.parmag.fixefid.record.field.FieldType;

@FixefidRecord
public class Person {
	@FixefidField(fieldOrdinal = 1, fieldLen = 25, fieldType = FieldType.AN)
	private String firstName;
	
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastName;
	
	@FixefidField(fieldOrdinal = 3, fieldLen = 3, fieldType = FieldType.N)
	private Integer age;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
```

then create a new empty record, fill the fields and get the string representation:

```
BeanRecord record = new BeanRecord(new Person());
record.setValue("firstName", "Paul");
record.setValue("lastName", "Robinson");
record.setValue("age", 51);
String recordAsString = record.toString();
```

or you can fill the fields directly to java bean:

```
Person person = new Person();
person.setFirstName("Paul");
person.setLastName("Robinson");
person.setAge(51);
BeanRecord record = new BeanRecord(person);
String recordAsString = record.toString();
```

The system out of the recordAsString is as follow:

```
Paul                     Robinson                 051
```

by default the alphanumeric fields are right padded with spaces, whereas the numeric fields are left padded with zeroes. This default behavior can be changed with the extended properties.

If you have the record as string (read for example from a file), create a record with the string e read the fields:

```
BeanRecord record = new BeanRecord(new Person(), recordAsString);
String firstName = record.getValueAsString("firstName");
String lastName = record.getValueAsString("lastName");
Integer age = record.getValueAsInteger("age");
```

or you can get data directly from java bean:

```
Person person = new Person();
BeanRecord record = new BeanRecord(person, recordAsString);
String firstName = person.getFirstName();
String lastName = person.getLastName();
Integer age = person.getAge();
```

this is a very simple example for getting started. 

You can create more complex records with formatters for decimal, date, boolean. You can create custom formatters, change the default behavior for the pad fields, create custom validators and many others features.

The advantage respect enum style is the java inheritance, composition, etc etc... that you can use with java bean, that's mean a more compact record representation. But bear in mind that every fixed field record can be represented by enum or java bean.

For example you can add the Address bean

```
@FixefidRecord
public class Address {
	@FixefidField(fieldOrdinal = 1, fieldLen = 25, fieldType = FieldType.AN)
	private String location;
	@FixefidField(fieldOrdinal = 2, fieldLen = 5, fieldType = FieldType.AN)
	private String postalCode;
	@FixefidField(fieldOrdinal = 3, fieldLen = 2, fieldType = FieldType.AN)
	private String district;
	@FixefidField(fieldOrdinal = 4, fieldLen = 3, fieldType = FieldType.AN)
	private String nationIso3;
	@FixefidField(fieldOrdinal = 5, fieldLen = 30, fieldType = FieldType.AN)
	private String address;
	@FixefidField(fieldOrdinal = 6, fieldLen = 10, fieldType = FieldType.AN)
	private String num;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	....
```

to the Person bean above, 

```

@FixefidRecord
public class Person {
	@FixefidField(fieldOrdinal = 1, fieldLen = 25, fieldType = FieldType.AN)
	private String firstName;
	
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastName;
	
	@FixefidField(fieldOrdinal = 3, fieldLen = 3, fieldType = FieldType.N)
	private Integer age;
	
	@FixefidField(fieldOrdinal = 4, fieldType = FieldType.CMP, fieldLen = 75)
	private Address address = new Address();

```

and crete a Student bean wich extends the Person bean

```
@FixefidRecord
public class Student extends Person {
	@FixefidField(fieldOrdinal = 5, fieldLen = 10, fieldType = FieldType.N)
	private long studentId;
	
	@FixefidField(fieldOrdinal = 6, fieldLen = 2, fieldType = FieldType.N)
	private int level;
	
	@FixefidField(fieldOrdinal = 7, fieldLen = 1, fieldType = FieldType.AN)
	private boolean active;
```

and create a Student record

```
BeanRecord record = new BeanRecord(new Student());
record.setValue("firstName", "Peter");
record.setValue("lastName", "Rossi");
record.setValue("age", 20);
record.setValue("address.location", "jacksonville");
record.setValue("address.postalCode", "40128");
record.setValue("studentId", 123456789);
record.setValue("level", 1);
record.setValue("active", true);
String recordAsString = record.toString();
```

the fieldOrdinal must be unique for inheritance. For composition is not mandatory.

Here a complete <a href="https://github.com/parmag/fixefid-examples/tree/main/fixefidbean" target="_blank">Example</a> about how getting started with records defined by Java Bean. 

## Record len
The default record len is the sum of every field len. For example for the person record above, the len is 25 + 25 + 3 = 53. If the record len must be greater than the default fields len sum, you can set it using the constructor like this:
```
Record<PersonRecordField> record = new Record<PersonRecordField>(PersonRecordField.class, 100);
```
or for java bean

```
@FixefidRecord(recordLen = 100)
public class Person {

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

or the java bean like this:

```
@FixefidRecord
public class Person {
	@FixefidField(fieldOrdinal = 1, fieldLen = 25, fieldType = FieldType.AN, fieldMandatory = FieldMandatory.IN)
	private String firstName;

```

The field mandatory is related to the record way. By default the record way is set to RecordWay.IN. That's when the record is read from an input source. When the record is write to an output source, the record way should be set to RecordWay.OUT. The record way can be set by record constructor like this:

```
Record<PersonRecordField> record = new Record<PersonRecordField>(RecordWay.IN, PersonRecordField.class);
```
or for java bean
```
@FixefidRecord(recordWay = RecordWay.IN)
public class Person {

```
or by the setter method 
 
 ```
 record.setRecordWay(RecordWay.IN);
 ```

This is useful when a field is optional when you read the record but mandatory when you write the record (that's an output field). In general:

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

or for java bean

```
 public class Person {
	@FixefidField(fieldOrdinal = 1, fieldLen = 25, fieldType = FieldType.AN, fieldDefaultValue = "Unknown")
	private String firstName;
 ```

## Alphanumeric data type with Date and Boolean formatters
The alphanumeric data type FieldType.AN can be managed like a Java Date or Boolean. To do that you need a date and a boolean formatter like these:

```
List<FieldExtendedProperty> birthDateFieldExtendedProperties = Arrays.asList(
		new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH)));
List<FieldExtendedProperty> vipFieldExtendedProperties = Arrays.asList(
		new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new BooleanFormat() {
			@Override
			public String format(Boolean value) {
				return (value != null && value.booleanValue()) ? "Y" : "N";
			}

			@Override
			public Boolean parse(String value) {
				return "Y".equals(value) ? true : false;
			}
		}));

```

For instance, to add two fields to the PersonRecordField example above, birth date and vip, change the enum like this:

```
public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN, null),
	lastName(25, FieldType.AN, null),
	age(3, FieldType.N, null),
	birthDate(8, FieldType.AN,  birthDateFieldExtendedProperties),
	vip(1, FieldType.AN, vipFieldExtendedProperties);
	
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

for the java bean:

```
@FixefidRecord
public class Person {
	.....
	
	@FixefidField(fieldOrdinal = 4, fieldLen = 8, fieldType = FieldType.AN)
	private Date birthDate;
	
	@FixefidField(fieldOrdinal = 5, fieldLen = 1, fieldType = FieldType.AN)
	private Boolean vip;
```

and create the record

```
Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = 
			new HashMap<String, List<FieldExtendedProperty>>();
MAP_FIELD_EXTENDED_PROPERTIES.put("birthDate", birthDateFieldExtendedProperties);
MAP_FIELD_EXTENDED_PROPERTIES.put("vip", vipFieldExtendedProperties);

BeanRecord record = new BeanRecord(person, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
record.setValue("firstName", "Peter");
record.setValue("lastName", "Rossi");
...
record.setValue("birthDate", new Date());
record.setValue("vip", true);
```

## Alphanumeric data type with Custom Formatter
The alphanumeric data type FieldType.AN can be used with a custom formatter:

```
List<FieldExtendedProperty> lastNameFieldExtendedProperties = Arrays.asList(
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
		}));

```

For instance, to create a custom formatter for the last name field, change the PersonRecordField enum like this:

```
public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN, null),
	lastName(25, FieldType.AN, lastNameFieldExtendedProperties),
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

for the java bean:

```
@FixefidRecord
public class Person {
	.....
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastName;
	....
```
ans create the record:
```
Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = new HashMap<String, List<FieldExtendedProperty>>();
MAP_FIELD_EXTENDED_PROPERTIES.put("lastName", lastNameFieldExtendedProperties);

BeanRecord record = new BeanRecord(person, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
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

To define an extended property for a Decimal Format, you can do like this:

```
List<FieldExtendedProperty> amountFieldExtendedProperties = Arrays.asList(
	new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH))));

```

For instance, to add the field amount to the PersonRecordField example above, change the enum like this:

```
public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN, null),
	lastName(25, FieldType.AN, null),
	age(3, FieldType.N, null),
	amount(10, FieldType.N, amountFieldExtendedProperties);
	
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
List<FieldExtendedProperty> amountFieldExtendedProperties = Arrays.asList(
	new FieldExtendedProperty(FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR, true),
	new FieldExtendedProperty(FieldExtendedPropertyType.DECIMAL_FORMAT, new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)))
);
```
the result is:
 ```
Paul                     Robinson                 0510000150099
```

for the java bean:

```
@FixefidRecord
public class Person {
	.....
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastname;
	....
	@FixefidField(fieldOrdinal = 8, fieldLen = 10, fieldType = FieldType.N)
	private String amount;
```
ans create the record:
```
Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = new HashMap<String, List<FieldExtendedProperty>>();
MAP_FIELD_EXTENDED_PROPERTIES.put("amount", amountFieldExtendedProperties);

BeanRecord record = new BeanRecord(person, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
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
Record<PersonRecordField> record = new Record<PersonRecordField>(RecordWay.IN, null, PersonRecordField.class, null, );
```
in this case all the fields of the record are left padded with spaces. Anyway, the extended property set by field level, always win vs the same extended property set by record level.

The same for java bean, the padding behavior can be set at Record level like this:
```
BeanRecord record = new BeanRecord(person, null, Arrays.asList(
	new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " ")), MAP_FIELD_EXTENDED_PROPERTIES);
```

## Field normalization
There are three types of normalization to apply at field or record level:
<ul>
	<li>ascii encoding: no ascii char is replaced with a question mark. The method is record.toAscii</li>
	<li>to upper case: lower case char is replaced with the relative upper case char. The method is record.toUpperCase</li>
	<li>accentes removing: a char with accent is replaced with the relative char without accent. The method is record.toRemoveAccents</li>
</ul>

all those normalizations can be applyed with the method record.toNormalize. For instance, to apply normalization to the lastName field, this method can be used:

```
 record.toNormalize(PersonRecordField.lastName);
 ```

if the value is "àx@°§12", after normalization is "AX@??12"

## Record status and custom validator
A custom validator can be added at field or record level. For instance to apply a custom validator to the lastName field of the PersonRecordField example above, change the enum like this:

```
List<FieldExtendedProperty> lastNameFieldExtendedProperties = Arrays.asList(
	new FieldExtendedProperty(FieldExtendedPropertyType.VALIDATOR, new FieldValidator() {
		@Override
		public FieldValidationInfo valid(String name, int index, FieldType type, FieldMandatory mandatory, String value,
				List<FieldExtendedProperty> fieldExtendedProperties) {
			if (value.contains("-")) {
				return new FieldValidationInfo(RecordFieldValidationStatus.ERROR, "lastName cannot contains -");
			} else {
				return new FieldValidationInfo();
			}
		}
	}));

```

```
public enum PersonRecordField implements FieldProperty {
	firstName(25, FieldType.AN, null),
	lastName(25, FieldType.AN, lastNameFieldExtendedProperties),
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
the same for java bean
```
Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = new HashMap<String, List<FieldExtendedProperty>>();
MAP_FIELD_EXTENDED_PROPERTIES.put("lastName", lastNameFieldExtendedProperties);

BeanRecord record = new BeanRecord(person, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
```

if the last name contains "-", the custom validator returns an ERROR validation info and if you try to get the value, an exception is thrown. The field or record validation info can be tested before to get the exception with the method isErrorStatus:
```
boolean isError = record.isErrorStatus(PersonRecordField.lastName);
 ```

moreover the validation info can be retrieved like this:

```
FieldValidationInfo validInfo = record.getRecordFieldValidationInfo(PersonRecordField.lastName);
 ```

## Record pretty print
When there are lot of fields, various record pretty prints can give informations of fields like index, offset, name and others. For instance the system out of this pretty print:

```
String prettyPrint = record.prettyPrint();
 ```
is formatted like this (for every field of the record):

```
name=[index][offset][len][value][validation status][validation msg (if present)]
 ```

## Javadoc
Here the <a href="./fixefid/doc" target="_blank">Javadoc</a>
