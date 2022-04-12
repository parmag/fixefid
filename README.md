# Fixefid
Fixefid is a Java library for working with fixed fields formatted text files and CSV files. 
It differs from other tools focusing on:
<ul>
  <li>Easy-to-use</li>
  <li>Record definition by Java Bean</li>
  <li>Numeric and Alphanumeric data types</li>
  <li>Custom formatters</li>  
  <li>In/Out fields mandatory awareness</li>
  <li>Fields normalization</li>
  <li>LPad/RPad fields</li>
  <li>Automatic record filler</li>
  <li>Default fields values</li>
  <li>Valid field values list</li>
  <li>[Record validation](./README.md#record-validation)</li>
  <li>Record status and custom validators</li>
  <li>Record pretty print</li>
</ul>

## What's new 3.0.0

The version 3.0.0 includes:

<ul>
	<li>Record validation improvement in terms of the possibility to obtain validation status for all fields</li>
	<li>Fixed values accepted list in field annotation</li>
	<li>Deprecated record representation by Java Enum (class Record and CSVRecord)</li>
	<li>Minor bug fixes and enhancements</li>
</ul>

## What's new 2.0.0

The version 2.0.0 includes:

<ul>
	<li>Java 8</li>
	<li>CSV Record</li>
	<li>Annotations for record and field extended properties</li>
	<li>Fields Occurrences and subOrdinal</li>
	<li>Simple Boolean Format</li>
	<li>Logging</li>
	<li>Minor bug fixes and enhancements</li>
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
The JDK compliance level is 1.8 or greater. To include jar library in your java project, download or add dependency from <a href="https://mvnrepository.com/artifact/com.github.parmag/fixefid" target="_blank">MVN Repository</a>.

To include maven dependency of fixefid version 3.0.0 in your pom.xml, add this:

```
<dependency>
    <groupId>com.github.parmag</groupId>
    <artifactId>fixefid</artifactId>
    <version>3.0.0</version>
</dependency>
```
Every record can be defined by a Java Bean. The most simple way to define a flat record by Java Bean is like this:
```
@FixefidRecord
public class Person {
	@FixefidField(fieldOrdinal = 1, fieldLen = 20, fieldType = FieldType.AN)
	private String fiscalCode;
	
	@FixefidField(fieldOrdinal = 2, fieldLen = 50, fieldType = FieldType.AN)
	private String firstName;
	
	@FixefidField(fieldOrdinal = 3, fieldLen = 50, fieldType = FieldType.AN)
	private String lastName;
	
	@FixefidDateFormat(pattern = "yyyy-MM-dd")
	@FixefidField(fieldOrdinal = 4, fieldLen = 10, fieldType = FieldType.AN)
	private Date birthDate;
	
	@FixefidDecimalFormat(pattern = "0.00", removeDecimalSeparator = true)
	@FixefidField(fieldOrdinal = 5, fieldLen = 10, fieldType = FieldType.N)
	private Double amount;
	
	@FixefidBooleanFormat(trueValue = "Y", falseValue = "N")
	@FixefidField(fieldOrdinal = 6, fieldLen = 1, fieldType = FieldType.AN)
	private Boolean vip;
	
	@FixefidField(fieldOrdinal = 7, fieldLen = 3, fieldType = FieldType.N)
	private Integer age;

	public String getFiscalCode() {
		return fiscalCode;
	}
	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
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
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Boolean getVip() {
		return vip;
	}
	public void setVip(Boolean vip) {
		this.vip = vip;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
```
then you can set the fields values and create a bean record to obtain the string representation of the bean:
```
Person person = new Person();
person.setFirstName("Paul");
person.setLastName("Robinson");
person.setAge(23);
person.setAmount(50000.00);
person.setBirthDate(birthDate); 
person.setFiscalCode("FISCALE_CODE");
person.setVip(true);
		
BeanRecord record = new BeanRecord(person);
if (!record.isErrorStatus()) {
	String recordAsString = record.toString();
}
```
where the system out of recordAsString is like:
```
FISCALE_CODE        Paul                                              Robinson                                          1967-08-040005000000Y023
```
or if you have the recordAsString, you can create the bean record with the string and obtain the single fields value:
```
Person person = new Person();
BeanRecord record = new BeanRecord(person, recordAsString);
if (!record.isErrorStatus()) {
	Sring firstName = person.getFirstName();
}
```

if you want to represent a CSV record, the most simple way is always by Java Bean:

```
@FixefidCSVRecord
public class Person {
	@FixefidCSVField(fieldOrdinal = 1, fieldType = FieldType.AN)
	private String fiscalCode;
	
	@FixefidCSVField(fieldOrdinal = 2, fieldType = FieldType.AN)
	private String firstName;
	
	@FixefidCSVField(fieldOrdinal = 3, fieldType = FieldType.AN)
	private String lastName;
	
	@FixefidDateFormat(pattern = "yyyy-MM-dd")
	@FixefidCSVField(fieldOrdinal = 4, fieldType = FieldType.AN)
	private Date birthDate;
	
	@FixefidDecimalFormat(pattern = "0.00", removeDecimalSeparator = true)
	@FixefidCSVField(fieldOrdinal = 5, fieldType = FieldType.N)
	private Double amount;
	
	@FixefidBooleanFormat(trueValue = "Y", falseValue = "N")
	@FixefidCSVField(fieldOrdinal = 6, fieldType = FieldType.AN)
	private Boolean vip;
	
	@FixefidCSVField(fieldOrdinal = 7, fieldType = FieldType.N)
	private Integer age;

	public String getFiscalCode() {
		return fiscalCode;
	}
	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
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
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Boolean getVip() {
		return vip;
	}
	public void setVip(Boolean vip) {
		this.vip = vip;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
```
then you can set the fields values and create a bean CSV record to obtain the string representation of the bean:
```
Person person = new Person();
person.setFirstName("Paul");
person.setLastName("Robinson");
person.setAge(23);
person.setAmount(50000.00);
person.setBirthDate(birthDate); 
person.setFiscalCode("FISCALE_CODE");
person.setVip(true);

CSVBeanRecord record = new CSVBeanRecord(person);
if (!record.isErrorStatus()) {
	String recordAsString = record.toString();
}
```
where the system out of recordAsString is like:
```
FISCALE_CODE,Paul,Robinson,1967-08-04,5000000,Y,23
```
or if you have the recordAsString, you can create the bean CSV record with the string and obtain the single fields value:
```
Person person = new Person();
CSVBeanRecord record = new CSVBeanRecord(person, recordAsString);
if (!record.isErrorStatus()) {
	Sring firstName = person.getFirstName();
}
```
but let's going on the details.

## Getting started by examples

Here some java projects <a href="https://github.com/parmag/fixefid-examples" target="_blank">Examples</a> about how using the library:
<ul>
	<li><a href="https://github.com/parmag/fixefid-examples/tree/main/fixefidbean" target="_blank">Fixefidbean</a>: define a record with Java Bean</li>
	<li><a href="https://github.com/parmag/fixefid-examples/tree/main/fixefidjpa" target="_blank">Fixefidjpa</a>: define a record with JPA Entity</li>
	<li><a href="https://github.com/parmag/fixefid-examples/tree/main/fixefidrest" target="_blank">Fixefidrest</a>: convert a record to JSON with a REST Service</li>
</ul>

Here some video tutorial:
<ul>
	<li><a href="https://www.youtube.com/watch?v=5fBsCyPSFOk&t" target="_blank">Fixefidbean</a>: define a record with Java Bean</li>
	<li><a href="https://www.youtube.com/watch?v=7yoBbqGE9gU" target="_blank">Fixefidjpa</a>: define a record with JPA Entity</li>
</ul>

Here some articles:
<ul>
	<li><a href="https://giancarloparma.com/2020/10/26/howto-deal-with-fixed-fields-text-record-without-substring/" target="_blank">Howto deal with fixed fields text record without substring</a></li>
	<li><a href="https://giancarloparma.com/2020/11/06/howto-deal-with-fixed-fields-text-record-and-jpa-entity/" target="_blank">Howto deal with fixed fields text record and JPA Entity</a></li>
	<li><a href="https://giancarloparma.com/2020/11/15/converting-fixed-fields-text-record-to-json/" target="_blank">Converting fixed fields text record to json</a></li>
</ul>

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
if (!record.isErrorStatus()) {
	String recordAsString = record.toString();
}
```

or you can fill the fields directly to java bean:

```
Person person = new Person();
person.setFirstName("Paul");
person.setLastName("Robinson");
person.setAge(51);
BeanRecord record = new BeanRecord(person);
if (!record.isErrorStatus()) {
	String recordAsString = record.toString();
}
```

The system out of the recordAsString is as follow:

```
Paul                     Robinson                 051
```

by default the alphanumeric fields are right padded with spaces, whereas the numeric fields are left padded with zeroes. This default behavior can be changed with the extended properties.

If you have the record as string (read for example from a file), create a record with the string e read the fields:

```
BeanRecord record = new BeanRecord(new Person(), recordAsString);
if (!record.isErrorStatus()) {
	String firstName = record.getValueAsString("firstName");
	String lastName = record.getValueAsString("lastName");
	Integer age = record.getValueAsInteger("age");
}
```

or you can get data directly from java bean:

```
Person person = new Person();
BeanRecord record = new BeanRecord(person, recordAsString);
if (!record.isErrorStatus()) {
	String firstName = person.getFirstName();
	String lastName = person.getLastName();
	Integer age = person.getAge();
}
```

this is a very simple example for getting started. 

You can create more complex records with formatters for decimal, date, boolean. You can create custom formatters, change the default behavior for the pad fields, create custom validators and many others features.

You can use Java inheritance. For example you can add the Address bean

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
if (!record.isErrorStatus()) {
	String recordAsString = record.toString();
}
```

the fieldOrdinal must be unique for inheritance. For composition is not mandatory.

Here a complete <a href="https://github.com/parmag/fixefid-examples/tree/main/fixefidbean" target="_blank">Example</a> about how getting started with records defined by Java Bean. 

## Record len
The default record len is the sum of every field len. For example for the person record above, the len is 25 + 25 + 3 = 53. If the record len must be greater than the default fields len sum, you can set it like this:

```
@FixefidRecord(recordLen = 100)
public class Person {

```

in this case a default filler of 47 space will be added at the end of the record. If you set the value of a field with a len greater than the field len, an exception is thrown.

## Field mandatory
The field mandatory can be set with the enum FieldMandatory. In the Person example above, all the fields are FieldMandatory.INOUT. To set it field by field, change the bean like this:

```
@FixefidRecord
public class Person {
	@FixefidField(fieldOrdinal = 1, fieldLen = 25, fieldType = FieldType.AN, fieldMandatory = FieldMandatory.IN)
	private String firstName;

```

The field mandatory is related to the record way. By default the record way is set to RecordWay.IN. That's when the record is read from an input source. When the record is write to an output source, the record way should be set to RecordWay.OUT. The record way can be set by record constructor like this:

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
Every field can have a default value. In the Person example above, all the fields don't have the default value. To set it field by field, change the bean like this:

```
 public class Person {
	@FixefidField(fieldOrdinal = 1, fieldLen = 25, fieldType = FieldType.AN, fieldDefaultValue = "Unknown")
	private String firstName;
 ```
## Field valid values list
Every field can have a list of valid values. In the Person example above, for example, we can accept only three names:

```
 public class Person {
	@FixefidField(fieldOrdinal = 1, fieldLen = 25, fieldType = FieldType.AN, fieldDefaultValue = "Unknown", fieldFixedValues = "Peter|Jack|Oliver")
	private String firstName;
 ```
if the first name contains "Jacob", the custom validator returns an ERROR validation info and if you try to get the value, an exception is thrown. The field or record validation info can be tested before to get the exception with the method isErrorStatus:
```
boolean isError = record.isErrorStatus("firstName");
 ```
or for all fields:
```
boolean isError = record.isErrorStatus();
 ```

moreover the validation info can be retrieved like this:

```
FieldValidationInfo validInfo = record.getRecordFieldValidationInfo("firstName");
 ```
 
## Alphanumeric data type with Date and Boolean formatters
The alphanumeric data type FieldType.AN can be managed like a Java Date or Boolean. To do that you need a date and a boolean formatter like these:

```
List<FieldExtendedProperty> birthDateFieldExtendedProperties = Arrays.asList(
		new FieldExtendedProperty(FieldExtendedPropertyType.DATE_FORMAT, new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH)));
List<FieldExtendedProperty> vipFieldExtendedProperties = Arrays.asList(
		new FieldExtendedProperty(FieldExtendedPropertyType.BOOLEAN_FORMAT, new SimpleBooleanFormat("Y", "N")));

```
and add to java bean:

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

Another way to using extended properties are annotations:

```
@FixefidRecord
public class Person {
	.....
	
	@FixefidDateFormat(pattern = "ddMMyyyy", locale = "en")
	@FixefidField(fieldOrdinal = 4, fieldLen = 8, fieldType = FieldType.AN)
	private Date birthDate;
	
	@FixefidBooleanFormat(trueValue = "Y", falseValue = "N")
	@FixefidField(fieldOrdinal = 5, fieldLen = 1, fieldType = FieldType.AN)
	private Boolean vip;
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

for the java bean:

```
@FixefidRecord
public class Person {
	.....
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastName;
	....
```
and create the record:
```
Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = new HashMap<String, List<FieldExtendedProperty>>();
MAP_FIELD_EXTENDED_PROPERTIES.put("lastName", lastNameFieldExtendedProperties);

BeanRecord record = new BeanRecord(person, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
```

or using annotation:
```
public class UpperLowerCustomFormat implements CustomFormat {

	public UpperLowerCustomFormat() {
	}

	@Override
	public String format(String value) {
		return value.toUpperCase();
	}

	@Override
	public String parse(String value) {
		return value.toLowerCase();
	}
}
```
```
@FixefidRecord
public class Person {
	.....
	@FixefidCustomFormat(className = "com.github.parmag.fixefid.test.format.UpperLowerCustomFormat")
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastName;
	....
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

and the result is:
```
Paul                     Robinson                 0510001500.99
```
you can add the extended proporty REMOVE_DECIMAL_SEPARATOR to the field amount like this:
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
and create the record:
```
Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = new HashMap<String, List<FieldExtendedProperty>>();
MAP_FIELD_EXTENDED_PROPERTIES.put("amount", amountFieldExtendedProperties);

BeanRecord record = new BeanRecord(person, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
```

or using annotation:

```
@FixefidRecord
public class Person {
	.....
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastname;
	....
	@FixefidDecimalFormat(pattern = "0.00", locale = "en", removeDecimalSeparator = false)
	@FixefidField(fieldOrdinal = 8, fieldLen = 10, fieldType = FieldType.N)
	private Long amount;
```

## Field padding
The field padding can be managed with the relative extended property. By default the alphanumeric fields are right padded with spaces, whereas the numeric fields are left padded with zeroes. For instance to change the default padding behavior of the field lastName of the Person example above, do like this:
```
Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = new HashMap<String, List<FieldExtendedProperty>>();
MAP_FIELD_EXTENDED_PROPERTIES.put("lastName", Arrays.asList(new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " ")));

BeanRecord record = new BeanRecord(person, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
```
the result is:
 ```
Paul                                      Robinson0510000150099
```

moreover the padding behavior can be set at Record level like this:

```
BeanRecord record = new BeanRecord(person, null, Arrays.asList(
	new FieldExtendedProperty(FieldExtendedPropertyType.LPAD, " ")), MAP_FIELD_EXTENDED_PROPERTIES);
```

the same result can be obtained via annotations. For example for the lastName field:
```
@FixefidRecord
public class Person {
	.....
	@FixefidLPAD(padChar = " ")
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastname;
	....
	@FixefidDecimalFormat(pattern = "0.00", locale = "en", removeDecimalSeparator = false)
	@FixefidField(fieldOrdinal = 8, fieldLen = 10, fieldType = FieldType.N)
	private Long amount;
```
or at record level:
```
@FixefidLPAD(padChar = " ")
@FixefidRecord
public class Person {
	.....
	@FixefidField(fieldOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
	private String lastname;
	....
	@FixefidDecimalFormat(pattern = "0.00", locale = "en", removeDecimalSeparator = false)
	@FixefidField(fieldOrdinal = 8, fieldLen = 10, fieldType = FieldType.N)
	private Long amount;
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

The same for all record fields:
```
 record.toNormalize();
 ```

## Field occurrences
For java bean fields, we can specify the relative occurrence:

```
 @FixefidRecord
public class Flag {
	@FixefidField(fieldOrdinal = 1, fieldLen = 20, fieldType = FieldType.AN)
	private String name;
	@FixefidField(fieldOrdinal = 2, fieldLen = 20, fieldType = FieldType.LIST, fieldOccurs = 10)
	private List<String> colors = new ArrayList<String>();
	@FixefidField(fieldOrdinal = 3, fieldLen = 10, fieldType = FieldType.AN)
	private String nation;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getColors() {
		return colors;
	}
	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}

}
 ```

it's a convenience method to avoid the declarations of n fields (in this case 10 color fields...). We can manage the field occurs in this way:

```
BeanRecord br = new BeanRecord(new Flag());
br.setValue("colors", "red", 3); 
....
String thirdColor = br.getValueAsString("colors", 3)

 ```
## Field subOrdinal

For a java bean with several fields, subOrdinal is usefull if during a refactoring you have the need to add a field without re-indexing the fields ordinal. The subOrdinal can be used like this:

```
@FixefidField(fieldOrdinal = 12, fieldSubOrdinal = 1, fieldLen = 50, fieldType = FieldType.AN)
private String email;

@FixefidField(fieldOrdinal = 12, fieldSubOrdinal = 2, fieldLen = 25, fieldType = FieldType.AN)
private String phone;
```
in the case above, we have added the phone field after the email field without modifying the fieldOrdinal, but using the field subOrdinal

## Record validation
A record validation can be obtained in various ways. For instance, before to generate the output representation of the record via toString method, we can check the record status like this:
```
OutputRecord or = new OutputRecord();
or.setReservedData("XXXXXXXXXXXXXXXXXXXX"); 
or.setData("YYYYYYYYYYYYY");
BeanRecord br = new BeanRecord(or);
boolean isRecordError = br.isErrorStatus();
```
or we can check the error status for a particular field:
```
boolean isReservedDataFieldError = br.isErrorStatus("reservedData")
```
or obtained the field validation info for a particolar field:
```
FieldValidationInfo functionUserIdValidationInfo = br.getRecordFieldValidationInfo("functionUserId");
RecordFieldValidationStatus validationStatus = functionUserIdValidationInfo.getValidationStatus();
String validationMessage = functionUserIdValidationInfo.getValidationMessage();
```
or obtain the field validation info for all fields:
```
Map<String, FieldValidationInfo> fieldvalidationInfoMap = br.getRecordFieldValidationInfo();
```
or obtain the field validation info for all fields with error status:
```
Map<String, FieldValidationInfo> fieldvalidationInfoMap = br.getRecordFieldErrorValidationInfo();
```
where the key of the two maps above is the field name.

Another way is the pretty print of all fields with error status:
```
br.prettyPrintErrorValidationInfo();
```
This is the pretty print:
```
key-1=[ERROR][key=[           ] not valid (is mandatory)]
prg-1=[ERROR][prg=[      ] not valid (is mandatory)]
data-1=[ERROR][data=[YYYYYYYYYYYYY] not valid lenght. Expected lenght=[10]
reservedData-1=[ERROR][reservedData=[XXXXXXXXXXXXXXXXXXXX] not valid lenght. Expected lenght=[17].]
```

if the record status is in error and we do the toString, a RecordException is thrown with all errors present in the record. This is the print of the record exception message:
```
RE10 - Record has Error status. Cause: key-1=[ERROR][key=[           ] not valid (is mandatory)]
prg-1=[ERROR][prg=[      ] not valid (is mandatory)]
data-1=[ERROR][data=[YYYYYYYYYYYYY] not valid lenght. Expected lenght=[10].]
reservedData-1=[ERROR][reservedData=[XXXXXXXXXXXXXXXXXXXX] not valid lenght. Expected lenght=[17].]
```

If we are reading from a string, we have to check the error status of the record before to get a field value. For instance:
```
OutputHeaderRecord ohr = new OutputHeaderRecord();
BeanRecord br = new BeanRecord(ohr, recordAsString);
if (!br.isErrorStatus()) {
	String name = ohr.getName();
}
```

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
for java bean
```
Map<String, List<FieldExtendedProperty>> MAP_FIELD_EXTENDED_PROPERTIES = new HashMap<String, List<FieldExtendedProperty>>();
MAP_FIELD_EXTENDED_PROPERTIES.put("lastName", lastNameFieldExtendedProperties);

BeanRecord record = new BeanRecord(person, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
```

or you can use the @FixefidValidator annotation like this
```
public class NameValidator implements FieldValidator {

	@Override
	public FieldValidationInfo valid(String name, int index, FieldType type, FieldMandatory mandatory, String value,
			List<FieldExtendedProperty> fieldExtendedProperties) {
		if (value.contains("-") || value.contains("_")) {
			return new FieldValidationInfo(RecordFieldValidationStatus.ERROR, "The field " + name + " with value=[" + value + "] cannot contains - or _");
		} else {
			return new FieldValidationInfo();
		}
	}

}
```
```
@FixefidValidator(className = "com.github.example.NameValidator")
@FixefidField(fieldOrdinal = 3, fieldLen = 50, fieldType = FieldType.AN) private String lastName;
```
where com.github.example.NameValidator must be an instance of FieldValidator.

if the last name contains "-", the custom validator returns an ERROR validation info and if you try to get the value, an exception is thrown. The field or record validation info can be tested before to get the exception with the method isErrorStatus:
```
boolean isError = record.isErrorStatus("lastName");
 ```
or for all fields:
```
boolean isError = record.isErrorStatus();
 ```

moreover the validation info can be retrieved like this:

```
FieldValidationInfo validInfo = record.getRecordFieldValidationInfo("lastName");
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

## CSV record with Java Bean

To define a CSV record with a java bean, create a java bean like this:

```
@FixefidCSVRecord
public class Car {
	@FixefidCSVField(fieldOrdinal = 0, fieldType = FieldType.AN)
	private String name;
	@FixefidCSVField(fieldOrdinal = 1, fieldType = FieldType.AN)
	private String model;
	@FixefidCSVField(fieldOrdinal = 2, fieldType = FieldType.AN)
	private Date productionDate;
	
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
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
}
```

then you can create a csv record like this:

```
Car car = new Car();
CSVBeanRecord csvBeanRecord = new CSVBeanRecord(car, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
csvBeanRecord.setValue("name", "Citroen");
csvBeanRecord.setValue("model", "C3 Picasso");
csvBeanRecord.setValue("productionDate", new Date());
if (!csvBeanRecord.isErrorStatus()) {
	String recordAsString = carCsvRecord.toString();
}
```
or you can fill the fields directly to java bean:

```
Car car = new Car();
car.setName("Citroen");
car.setModel("C3 Picasso");
car.setproductionDate(new Date());
CSVBeanRecord csvBeanRecord = new CSVBeanRecord(car, null, null, MAP_FIELD_EXTENDED_PROPERTIES);
if (!csvBeanRecord.isErrorStatus()) {
	String recordAsString = carCsvRecord.toString();
}
```

The system out of the recordAsString is as follow:

```
Citroen,C3 Picasso,07102002
```

If you have the record as string (read for example from a file), create a record with the string and read the fields:

```
CSVBeanRecord csvBeanRecord = new CSVBeanRecord(car, recordAsString, null, MAP_FIELD_EXTENDED_PROPERTIES);
if (!csvBeanRecord.isErrorStatus()) {
	String name = csvBeanRecord.getValueAsString("name")
}
```

or you can get data directly from java bean:

```
String name = car.getName();
```

this is a very simple example for getting started.

You can create more complex records with formatters for decimal, date, boolean. You can create custom formatters, change the default behavior for the field separator, create custom validators and many others features (see above sections).


## Javadoc
Here the <a href="./fixefid/doc" target="_blank">Javadoc</a>
