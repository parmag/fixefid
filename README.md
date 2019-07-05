# fixefid
Fixefid is a Java library for working with flat fixed formatted text files. 
It differs from other tools focusing on:
<ul>
  <li>Easy-to-use</li>
  <li>Numeric and Alphanumeric data types</li>
  <li>Custom formatters</li>
  <li>In/Out fields mandatory awareness</li>
  <li>Fields normalization</li>
  <li>LPad/RPad</li>
</ul>


## Getting started
To include jar library in your java project, download or add dependency from <a href="https://mvnrepository.com/artifact/com.github.parmag/fixefid" target="_blank">MVN Repository</a>.

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
record.setValue(PersonRecordField.firstName, "Paolo");
record.setValue(PersonRecordField.lastName, "Rossi");
record.setValue(PersonRecordField.age, 51);
String recordAsString = record.toString();

```

or if you have the record as string (read for example from a file), create a record with the string e read the fields:

```
Record<PersonRecordField> record = new Record<PersonRecordField>(recordAsString, PersonRecordField.class);
String firstName = record.getValueAsString(PersonRecordField.firstName);
String lastName = record.getValueAsString(PersonRecordField.lastName);
Integer age = record.getValueAsInteger(PersonRecordField.age)
```

this is a very simple example for getting started. You can create complex records with formatters for decimal, date, boolean. You can create custom formatters, change the default behavior for the pad fields, create custom validators and many others features.

Here the <a href="./fixefid/doc" target="_blank">Javadoc</a>
