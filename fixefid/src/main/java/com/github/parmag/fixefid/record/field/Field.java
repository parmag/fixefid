package com.github.parmag.fixefid.record.field;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.parmag.fixefid.record.ErrorCode;
import com.github.parmag.fixefid.record.RecordWay;
import com.github.parmag.fixefid.record.field.FieldValidationInfo.RecordFieldValidationStatus;
import com.github.parmag.fixefid.record.format.BooleanFormat;
import com.github.parmag.fixefid.record.format.CustomFormat;

/**
 * The <code>Field</code> represents a fixed field formatted text.
 * 
 * @author Giancarlo Parma
 * 
 * @since 1.0
 */
public class Field {
	private static final Logger LOG = LoggerFactory.getLogger(Field.class);
	
	private static final String DEFAULT_AN_VALUE = "";
	private static final String DEFAULT_N_VALUE = "";
	private static final String DEFAULT_AN_PAD_VALUE = " ";
	private static final String DEFAULT_N_PAD_VALUE = "0";
	
	private String value;
	private final String name;
	private final FieldType type;
	private int len;
	private final FieldMandatory mandatory;
	private RecordWay recordWay;
	private DecimalFormat decimalFormat;
	private boolean removeDecimalSeparator;
	private DateFormat dateFormat; 
	private BooleanFormat booleanFormat; 
	private CustomFormat customFormat; 
	private int index;
	private int subIndex;
	private int occurIndex;
	private String defaultValue;
	private FieldValidator validator;
	private FieldValidationInfo validationInfo;
	private int padStrNum;
	private FieldExtendedPropertyType pad;
	private String padStr;
	private List<FieldExtendedProperty> fieldExtendedProperties;
	private boolean lenNormalized;
	private String displayName;
	private String description;
	
	/**
	 * Constructs a new <code>Field</code> that represents the fixed field formatted text
	 * 
	 * @param name the name of this <code>Field</code>
	 * @param index the index of this <code>Field</code>
	 * @param subIndex the sub-index of this <code>Field</code>
	 * @param occurIndex the occur index
	 * @param type the type of this <code>Field</code>
	 * @param len the len of this <code>Field</code>
	 * @param mandatory the mandatory type of this <code>Field</code>
	 * @param recordWay the record way type of this <code>Field</code>
	 * @param defaultValue the default value of this <code>Field</code>
	 * @param fieldExtendedProperties the field extended properties of this <code>Field</code>
	 * @param displayName the display name of this <code>Field</code>
	 * @param description the description of this <code>Field</code>
	 */
	public Field(String name, int index, int subIndex, int occurIndex, FieldType type, int len, FieldMandatory mandatory, RecordWay recordWay, 
			String defaultValue, List<FieldExtendedProperty> fieldExtendedProperties, String displayName, String description) {
		this.name = name;
		this.index = index; 
		this.subIndex = subIndex;
		this.occurIndex = occurIndex; 
		this.type = type;
		this.len = len;
		this.mandatory = mandatory; 
		this.recordWay = recordWay; 
		this.defaultValue = defaultValue;
		this.displayName = displayName;
		this.description = description;
		this.validationInfo = new FieldValidationInfo();
		this.padStrNum = 0;
		this.fieldExtendedProperties = fieldExtendedProperties;
		
		if (FieldType.N.equals(type)) {
			this.pad = FieldExtendedPropertyType.LPAD;
			padStr = DEFAULT_N_PAD_VALUE;
		} else {
			this.pad = FieldExtendedPropertyType.RPAD;
			padStr = DEFAULT_AN_PAD_VALUE;
		}
		
		if (FieldType.N.equals(type) && len > 19) {
			throw new FieldException(ErrorCode.FE12, "The field name=[" + name + "] type=[" + type.name() + "] has wrong length=[" + len + "]. Len <= 19 expected.");
		}
		
		if (defaultValue != null) {
			setValue(defaultValue, false);
		} else if (!isMandatory()) {
			setValue(FieldType.AN.equals(type) ? DEFAULT_AN_VALUE : DEFAULT_N_VALUE, false);
		}
		
		if (fieldExtendedProperties != null) {
			for (FieldExtendedProperty fieldExtendedProperty : fieldExtendedProperties) {
				if (FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR.equals(fieldExtendedProperty.getType())) {
					removeDecimalSeparator = Boolean.valueOf(fieldExtendedProperty.getValue().toString());
				} else if (FieldExtendedPropertyType.DECIMAL_FORMAT.equals(fieldExtendedProperty.getType())) {
					this.decimalFormat = (DecimalFormat) fieldExtendedProperty.getValue();
				} else if (FieldExtendedPropertyType.DATE_FORMAT.equals(fieldExtendedProperty.getType())) {
					this.dateFormat = (DateFormat) fieldExtendedProperty.getValue();
				} else if (FieldExtendedPropertyType.BOOLEAN_FORMAT.equals(fieldExtendedProperty.getType())) {
					this.booleanFormat = (BooleanFormat) fieldExtendedProperty.getValue();
				} else if (FieldExtendedPropertyType.CUSTOM_FORMAT.equals(fieldExtendedProperty.getType())) {
					this.customFormat = (CustomFormat) fieldExtendedProperty.getValue();
				} else if (FieldExtendedPropertyType.LPAD.equals(fieldExtendedProperty.getType())) {
					this.pad = FieldExtendedPropertyType.LPAD;
					this.padStr = (String) fieldExtendedProperty.getValue();
				} else if (FieldExtendedPropertyType.RPAD.equals(fieldExtendedProperty.getType())) {
					this.pad = FieldExtendedPropertyType.RPAD;
					this.padStr = (String) fieldExtendedProperty.getValue();
				} else if (FieldExtendedPropertyType.VALIDATOR.equals(fieldExtendedProperty.getType())) {
					this.validator = (FieldValidator) fieldExtendedProperty.getValue();
				}
			}
		}
		
		if (!FieldType.N.equals(type) && decimalFormat != null) {
			throw new FieldException(ErrorCode.FE13, "The field name=[" + name + "] type=[" + type.name() + " has wrong extended property decimal format. Type " + FieldType.N.name() + " expected.");
		}
		
		if (!FieldType.AN.equals(type) && dateFormat != null) {
			throw new FieldException(ErrorCode.FE14, "The field name=[" + name + "] type=[" + type.name() + " has wrong extended property date format. Type " + FieldType.AN.name() + " expected.");
		}
		
		if (!FieldType.AN.equals(type) && booleanFormat != null) {
			throw new FieldException(ErrorCode.FE15, "The field name=[" + name + "] type=[" + type.name() + " has wrong extended property boolean format. Type " + FieldType.AN.name() + " expected.");
		}
		
		if (!FieldType.AN.equals(type) && customFormat != null) {
			throw new FieldException(ErrorCode.FE16, "The field name=[" + name + "] type=[" + type.name() + " has wrong extended property custom format. Type " + FieldType.AN.name() + " expected.");
		}
		
		if ((dateFormat != null && booleanFormat != null) || (customFormat != null && booleanFormat != null)
				 || (customFormat != null && dateFormat != null) || (customFormat != null && dateFormat != null && booleanFormat != null)) {
			throw new FieldException(ErrorCode.FE17, "The field name=[" + name + "] type=[" + type.name() + " has wrong too much extended properties boolean format, date format and custom format. Only one is expected.");
		}
		
		if ((!FieldType.N.equals(type) || decimalFormat == null) && removeDecimalSeparator) {
			throw new FieldException(ErrorCode.FE18, "The field name=[" + name + "] type=[" + type.name() + " has wrong extended property=[" + 
					FieldExtendedPropertyType.REMOVE_DECIMAL_SEPARATOR + "]. Type " + FieldType.N.name() + " expected.");
		}
		
		if (padStr.length() != 1) {
			throw new FieldException(ErrorCode.FE19, "The field name=[" + name + "] type=[" + type.name() + " has extended property=[" + 
				FieldExtendedPropertyType.RPAD + " or " + FieldExtendedPropertyType.LPAD + "] with wrong pad string=[" + 
					padStr + "] . Pad String Len=[1} is expected.");
		}
		
		LOG.debug("Created field {}", toString());
	}

	/**
	 * Returns the formatted value of this <code>Field</code>
	 * 
	 * @return the formatted value of this <code>Field</code>
	 */
	public String getValue() {
		checkValidationInfo();
		
		return value;
	}
	
	/**
	 * Returns the formatted value of this <code>Field</code> with no PAD
	 * 
	 * @return the formatted value of this <code>Field</code> with no PAD
	 */
	public String getValueWithNoPAD() {
		checkValidationInfo();
		
		return undoPad(value);
	}
	
	/**
	 * Returns the parsed value as <code>String</code> of this <code>Field</code>
	 * 
	 * @return the parsed value as <code>String</code> of this <code>Field</code>
	 * @throws FieldException if the field is not a String
	 */
	public String getValueAsString() throws FieldException {
		checkValidationInfo();
		
		if (isString()) {
			String valueAsString = undoPad(value);
			if (customFormat != null) {
				valueAsString = customFormat.parse(valueAsString);
			}
			
			return valueAsString;
		} else {
			throw new FieldException(ErrorCode.FE20, parseTypeErrorMessage("String"));
		}
	}
	
	/**
	 * Returns the parsed value as <code>Long</code> of this <code>Field</code>
	 * 
	 * @return the parsed value as <code>Long</code> of this <code>Field</code>
	 * @throws FieldException if the field is not a Long
	 */
	public Long getValueAsLong() throws FieldException {
		checkValidationInfo();
		
		if (isNumericSpace(value)) {
			return null;
		} else {
			if (isLong() || isLenNormalized()) {
				try {
					String longValueAsString = undoPad(value);
					if (longValueAsString.isEmpty()) {
						return null;
					} else {
						return Long.valueOf(longValueAsString);
					}
				} catch (NumberFormatException nfe) {
					throw new FieldException(ErrorCode.FE21, parseTypeErrorMessage("Long"));
				}
			} else {
				throw new FieldException(ErrorCode.FE22, parseTypeErrorMessage("Long"));
			}
		}
	}
	
	/**
	 * Returns the parsed value as <code>Integer</code> of this <code>Field</code>
	 * 
	 * @return the parsed value as <code>Integer</code> of this <code>Field</code>
	 * @throws FieldException if the field is not an Integer
	 */
	public Integer getValueAsInteger() throws FieldException {
		checkValidationInfo();
		
		if (isNumericSpace(value)) {
			return null;
		} else {
			if (isInteger()) {
				try {
					String intValueAsString = undoPad(value);
					if (intValueAsString.isEmpty()) {
						return null;
					} else {
						return Integer.valueOf(intValueAsString);
					}
				} catch (NumberFormatException nfe) {
					throw new FieldException(ErrorCode.FE23, parseTypeErrorMessage("Integer"));
				}
			} else {
				throw new FieldException(ErrorCode.FE24, parseTypeErrorMessage("Integer"));
			}
		}
	}
	
	/**
	 * Returns the parsed value as <code>Double</code> of this <code>Field</code>
	 * 
	 * @return the parsed value as <code>Double</code> of this <code>Field</code>
	 * @throws FieldException if the field is not a Double
	 */
	public Double getValueAsDouble() throws FieldException {
		checkValidationInfo();
		
		if (isNumericSpace(value)) {
			return null;
		} else {
			if (isDouble() || isLenNormalized()) {
				String doubleValueAsString = undoPad(value);
				if (removeDecimalSeparator) {
					doubleValueAsString = redoDecimalSeparator(doubleValueAsString);
				} 
				
				if (doubleValueAsString.isEmpty()) {
					return null;
				}
				
				try { 
					String minusSign = Character.toString(decimalFormat.getDecimalFormatSymbols().getMinusSign());
					if (doubleValueAsString.contains(minusSign)) {
						return new Double(decimalFormat.parse(minusSign + doubleValueAsString.replace(minusSign, "")).doubleValue());
					} else {
						return new Double(decimalFormat.parse(doubleValueAsString).doubleValue());
					}
				} catch (ParseException e) {
					throw new FieldException(ErrorCode.FE25, parseTypeErrorMessage("Double"));
				}
			} else {
				throw new FieldException(ErrorCode.FE26, parseTypeErrorMessage("Double"));
			}
		}
	}
	
	/**
	 * Returns the parsed value as <code>Float</code> of this <code>Field</code>
	 * 
	 * @return the parsed value as <code>Float</code> of this <code>Field</code>
	 * @throws FieldException if the field is not a Float
	 */
	public Float getValueAsFloat() throws FieldException {
		checkValidationInfo();
		
		if (isNumericSpace(value)) {
			return null;
		} else {
			if (isFloat()) {
				String floatValueAsString = undoPad(value);
				if (removeDecimalSeparator) {
					floatValueAsString = redoDecimalSeparator(floatValueAsString);
				}
				
				if (floatValueAsString.isEmpty()) {
					return null;
				}
				
				try { 
					String minusSign = Character.toString(decimalFormat.getDecimalFormatSymbols().getMinusSign());
					if (floatValueAsString.contains(minusSign)) {
						return new Float(decimalFormat.parse(minusSign + floatValueAsString.replace(minusSign, "")).floatValue());
					} else {
						return new Float(decimalFormat.parse(floatValueAsString).floatValue());
					}
				} catch (ParseException e) {
					throw new FieldException(ErrorCode.FE27, parseTypeErrorMessage("Float"));
				}
			} else {
				throw new FieldException(ErrorCode.FE28, parseTypeErrorMessage("Float"));
			}
		}
	}
	
	/**
	 * Returns the parsed value as <code>BigDecimal</code> of this <code>Field</code>
	 * 
	 * @return the parsed value as <code>BigDecimal</code> of this <code>Field</code>
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public BigDecimal getValueAsBigDecimal() throws FieldException {
		checkValidationInfo();
		
		if (isNumericSpace(value)) {
			return null;
		} else {
			if (isBigDecimal()) {
				BigDecimal bigDecimal = null;
				String bigDecimalValueAsString = undoPad(value);
				if (removeDecimalSeparator) {
					bigDecimalValueAsString = redoDecimalSeparator(bigDecimalValueAsString);
				}
				
				if (bigDecimalValueAsString.isEmpty()) {
					return null;
				}
				
				try { 
					String minusSign = Character.toString(decimalFormat.getDecimalFormatSymbols().getMinusSign());
					if (bigDecimalValueAsString.contains(minusSign)) {
						bigDecimal = new BigDecimal(decimalFormat.parse(minusSign + bigDecimalValueAsString.replace(minusSign, "")).doubleValue());
					} else {
						bigDecimal = new BigDecimal(decimalFormat.parse(bigDecimalValueAsString).doubleValue());
					}
					
				} catch (ParseException e) {
					throw new FieldException(ErrorCode.FE29, parseTypeErrorMessage("BigDecimal"));
				}
				
				int fractionDigits = decimalFormat.getMaximumFractionDigits();
				bigDecimal = bigDecimal.setScale(fractionDigits, RoundingMode.HALF_DOWN);
				return bigDecimal;
			} else {
				throw new FieldException(ErrorCode.FE30, parseTypeErrorMessage("BigDecimal"));
			}
		}
	}
	 
	/**
	 * Returns the parsed value as <code>Date</code> of this <code>Field</code>
	 * 
	 * @return the parsed value as <code>Date</code> of this <code>Field</code>
	 * @throws FieldException if the field is not a Date
	 */
	public Date getValueAsDate() throws FieldException {
		checkValidationInfo();
		
		if (isDate()) {
			try {
				String dateAsString = undoPad(value);
				if (dateAsString.isEmpty()) {
					return null;
				} else {
					return dateFormat.parse(dateAsString);
				}
			} catch (ParseException e) { 
				throw new FieldException(ErrorCode.FE31, parseTypeErrorMessage("Date"));
			}
		} else {
			throw new FieldException(ErrorCode.FE32, parseTypeErrorMessage("Date"));
		}
	}
	
	/**
	 * Returns the parsed value as <code>Boolean</code> of this <code>Field</code>
	 * 
	 * @return the parsed value as <code>Boolean</code> of this <code>Field</code>
	 * @throws FieldException if the field is not a Boolean
	 */
	public Boolean getValueAsBoolean() throws FieldException {
		checkValidationInfo();
		
		if (isBoolean()) {
			return booleanFormat.parse(undoPad(value));
		} else {
			throw new FieldException(ErrorCode.FE33, parseTypeErrorMessage("Boolean"));
		}
	}

	/**
	 * Set the specified value to this <code>Field</code>
	 * 
	 * @param value the value to set
	 * @param doCustomFormat if true and the custom format is present, format the value with the custom format
	 */
	public void setValue(String value, boolean doCustomFormat) {
		if (value == null) {
			return;
		}
		
		if (customFormat != null && doCustomFormat) {
			value = customFormat.format(value);
		}
		
		if (isLenToNormalize()) {
			doLenNormalization(value);
		}
		
		this.value = doFormat(value);
		validValue();
	}
	
	/**
	 * Set the specified value to this <code>Field</code>
	 * 
	 * @param value the value to set
	 * @throws FieldException if the field is not a Long
	 */
	public void setValue(Long value) throws FieldException {
		if (value == null) {
			return;
		}
		
		String valueAsString = String.valueOf(value);
		if (isLenToNormalize()) {
			doLenNormalization(valueAsString);
		}
		
		if (isLong() || isLenNormalized()) {
			setValue(valueAsString, false);
		} else {
			throw new FieldException(ErrorCode.FE34, parseTypeErrorMessage("Long"));
		}
	}
	
	/**
	 * Set the specified value to this <code>Field</code>
	 * 
	 * @param value the value to set
	 * @throws FieldException if the field is not an Integer
	 */
	public void setValue(Integer value) throws FieldException {
		if (value == null) {
			return;
		}
		
		String valueAsString = String.valueOf(value);
		if (isLenToNormalize()) {
			doLenNormalization(valueAsString);
		}
		
		if (isInteger()) {
			setValue(valueAsString, false);
		} else {
			throw new FieldException(ErrorCode.FE35, parseTypeErrorMessage("Integer"));
		}
	}
	
	/**
	 * Set the specified value to this <code>Field</code>
	 * 
	 * @param value the value to set
	 * @throws FieldException if the field is not a Double
	 */
	public void setValue(Double value) throws FieldException {
		if (value == null) {
			return;
		}
		
		String valueAsString = null;
		if (removeDecimalSeparator && decimalFormat != null) {
			char decimalSeparator = decimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
			valueAsString = decimalFormat.format(value).replace(Character.toString(decimalSeparator), "");
		} else if (decimalFormat != null) {
			valueAsString = decimalFormat.format(value);
		}
		
		if (isLenToNormalize() && valueAsString != null) {
			doLenNormalization(valueAsString);
		}
		
		if (isDouble() || isLenNormalized()) {
			setValue(valueAsString, false);
		} else {
			throw new FieldException(ErrorCode.FE36, parseTypeErrorMessage("Double"));
		} 
	}
	
	/**
	 * Set the specified value to this <code>Field</code>
	 * 
	 * @param value the value to set
	 * @throws FieldException if the field is not a Float
	 */
	public void setValue(Float value) throws FieldException {
		if (value == null) {
			return;
		}
		
		String valueAsString = null;
		if (removeDecimalSeparator && decimalFormat != null) {
			char decimalSeparator = decimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
			valueAsString = decimalFormat.format(value).replace(Character.toString(decimalSeparator), "");
		} else if (decimalFormat != null) {
			valueAsString = decimalFormat.format(value);
		}
		
		if (isLenToNormalize() && valueAsString != null) {
			doLenNormalization(valueAsString);
		}
		
		if (isFloat()) {
			setValue(valueAsString, false);
		} else {
			throw new FieldException(ErrorCode.FE37, parseTypeErrorMessage("Float"));
		} 
	}
	
	/**
	 * Set the specified value to this <code>Field</code>
	 * 
	 * @param value the value to set
	 * @throws FieldException if the field is not a BigDecimal
	 */
	public void setValue(BigDecimal value) throws FieldException {
		if (value == null) {
			return;
		}
		
		String valueAsString = null;
		if (removeDecimalSeparator && decimalFormat != null) {
			char decimalSeparator = decimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
			valueAsString = decimalFormat.format(value).replace(Character.toString(decimalSeparator), "");
		} else if (decimalFormat != null) {
			valueAsString = decimalFormat.format(value);
		}
		
		if (isLenToNormalize() && valueAsString != null) {
			doLenNormalization(valueAsString);
		}
		
		if (isBigDecimal()) {
			setValue(valueAsString, false);
		} else {
			throw new FieldException(ErrorCode.FE38, parseTypeErrorMessage("BigDecimal"));
		} 
	}
	
	/**
	 * Set the specified value to this <code>Field</code>
	 * 
	 * @param value the value to set
	 * @throws FieldException if the field is not a Date
	 */
	public void setValue(Date value) throws FieldException {
		if (value == null) {
			return;
		}
		
		String valueAsString = null;
		if (dateFormat != null) {
			valueAsString = dateFormat.format(value);
		}
		
		if (isLenToNormalize() && valueAsString != null) {
			doLenNormalization(valueAsString);
		}
		
		if (isDate()) {
			setValue(valueAsString, false);
		} else {
			throw new FieldException(ErrorCode.FE39, parseTypeErrorMessage("Date"));
		}
	}
	
	/**
	 * Set the specified value to this <code>Field</code>
	 * 
	 * @param value the value to set
	 * @throws FieldException if the field is not a Boolean
	 */
	public void setValue(Boolean value) throws FieldException {
		if (value == null) {
			return;
		}
		
		String valueAsString = null;
		if (booleanFormat != null) {
			valueAsString = booleanFormat.format(value);
		}
		
		if (isLenToNormalize() && valueAsString != null) {
			doLenNormalization(valueAsString);
		}
		
		if (isBoolean()) {
			setValue(valueAsString, false);
		} else {
			throw new FieldException(ErrorCode.FE40, parseTypeErrorMessage("Boolean"));
		}
	}

	/**
	 * @return the name of this <code>Field</code>
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type of this <code>Field</code>
	 */
	public FieldType getType() {
		return type;
	}

	/**
	 * @return the len of this <code>Field</code>
	 */
	public int getLen() {
		return len;
	}
	
	/**
	 * Returns true if this <code>Field</code> is mandatory. This field is mandatory if
	 * <ul>
	 * <li>FieldMandatory.INOUT</li>
	 * <li>FieldMandatory.IN and RecordWay.IN.</li>
	 * <li>FieldMandatory.OUT and RecordWay.OUT</li>
	 * </ul>
	 * 
	 * @return true if this <code>Field</code> is mandatory 
	 */
	public boolean isMandatory() {
		boolean result = false;
		
		if (!FieldMandatory.NO.equals(mandatory)) {
			if ((FieldMandatory.IN.equals(mandatory) && RecordWay.IN.equals(recordWay)) ||
				FieldMandatory.OUT.equals(mandatory) && RecordWay.OUT.equals(recordWay) ||
				(FieldMandatory.INOUT.equals(mandatory))) {
				result = true;
			}
		}
		
		return result;
	}

	/**
	 * @return the record way of this field
	 */
	public RecordWay getRecordWay() {
		return recordWay;
	}
	
	/**
	 * Set the record way of this field
	 * 
	 * @param recordWay the record way to set
	 */
	public void setRecordWay(RecordWay recordWay) {
		this.recordWay = recordWay;
		validValue();
	}

	/**
	 * @return the extended properties of this field
	 */
	public List<FieldExtendedProperty> getFieldExtendedProperties() {
		return fieldExtendedProperties;
	}
	
	/**
	 * Returns true if this field is a String. This field is a String if
	 * <p>
	 * FieldType.AN
	 * 
	 * @return true if this field is a String. 
	 */
	public boolean isString() {
		return FieldType.AN.equals(type);
	}
	
	/**
	 * Returns true if this field is a Long. This field is a Long if
	 * <p>
	 * FieldType.N and decimal format is not present and <code>len &ge; 10</code>
	 * 
	 * @return true if this field is a Long. 
	 */
	public boolean isLong() {
		return FieldType.N.equals(type) && decimalFormat == null && len >= 10;
	}
	
	/**
	 * Returns true if this field is a Integer. This field is a Integer if
	 * <p>
	 * FieldType.N and decimal format is not present and <code>len &lt; 10</code>
	 * 
	 * @return true if this field is a Integer. 
	 */
	public boolean isInteger() {
		return FieldType.N.equals(type) && decimalFormat == null && len < 10;
	}
	
	/**
	 * Returns true if this field is a Date. This field is a Date if
	 * <p>
	 * FieldType.AN and date format is present
	 * 
	 * @return true if this field is a Date. 
	 */
	public boolean isDate() {
		return FieldType.AN.equals(type) && dateFormat != null;
	}
	
	/**
	 * Returns true if this field is a Boolean. This field is a Boolean if
	 * <p>
	 * FieldType.AN and boolean format is not present 
	 * 
	 * @return true if this field is a Boolean. 
	 */
	public boolean isBoolean() {
		return FieldType.AN.equals(type) && booleanFormat != null;
	}
	
	/**
	 * Returns true if this field is a Double. This field is a Double if
	 * <p>
	 * FieldType.N and decimal format is present and <code>len &ge; 10</code>
	 * 
	 * @return true if this field is a Double. 
	 */
	public boolean isDouble() {
		return FieldType.N.equals(type) && decimalFormat != null && len >= 10;
	}
	
	/**
	 * Returns true if this field is a Float. This field is a Float if
	 * <p>
	 * FieldType.N and decimal format is not present and <code>len &lt; 10</code>
	 * 
	 * @return true if this field is a Float. 
	 */
	public boolean isFloat() {
		return FieldType.N.equals(type) && decimalFormat != null && len < 10;
	}
	
	/**
	 * Returns true if this field is a BigDecimal. This field is a BigDecimal if
	 * <p>
	 * FieldType.N and decimal format is present
	 * 
	 * @return true if this field is a BigDecimal. 
	 */
	public boolean isBigDecimal() {
		return isDouble() || isFloat();
	}
	
	/**
	 * @return the idenx of this field
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Set the index of this field
	 * 
	 * @param index the index to set of this field
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * @return the sub-idenx of this field
	 */
	public int getSubIndex() {
		return subIndex;
	}

	/**
	 * Set the sub-index of this field
	 * 
	 * @param subIndex the sub-index to set of this field
	 */
	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}
	
	/**
	 * @return the occur index of this field
	 */
	public int getOccurIndex() {
		return occurIndex;
	}

	/**
	 * Set the occur index of this field
	 * 
	 * @param occurIndex the occur index of this field
	 */
	public void setOccurIndex(int occurIndex) {
		this.occurIndex = occurIndex;
	}
	
	/**
	 * @return the default value of this field
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Set the default value of this field
	 * 
	 * @param defaultValue the default value to set of this field
	 */
	void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	/**
	 * Format the value param
	 * 
	 * @param value the value to format
	 * @return the formatted value
	 */
	protected String doFormat(String value) {
		String result = null;
		padStrNum = 0;
		
		if (isNumericSpace(value)) { 
			result = String.format("%-" + getLen() + "s", value);
		} else {
			if (FieldType.AN.equals(type)) {
				if (value == null) {
					value = DEFAULT_AN_VALUE;
				}
				
				if (value.isEmpty() && len == 0) {
					result = value;
				} else {
					result = doPad(value);
				}
			} else if (FieldType.N.equals(type)) {
				if (value == null) {
					value = DEFAULT_N_VALUE;
				}
				
				result = doPad(value);
			}
		}
		
		return result;
	}
	
	/**
	 * Valid len and mandatory
	 */
	protected void validLenAnMandatory() {
		String notValidMsg = "";
		
		if (value == null) {
			notValidMsg = "Null value not valid for field=[" + name + "]"; 
		} else if (value.length() != len) {
			notValidMsg = name + "=[" + value + "] not valid lenght. Expected lenght=[" + len + "].";
		} else if (FieldType.N.equals(type)) {
			if (isNumericSpace(value) && isMandatory()) {
				notValidMsg = name + "=[" + value + "] not valid (is mandatory)";
			} else {
				String valueAsString = undoPad(value);
				if (valueAsString.isEmpty() && isMandatory()) {
					notValidMsg = name + "=[" + value + "] not valid (is mandatory)";
				}
			}
		} else if (FieldType.AN.equals(type)) {
			String valueAsString = undoPad(value);
			if (valueAsString.isEmpty() && isMandatory()) {
				notValidMsg = name + "=[" + value + "] not valid (is mandatory)";
			} 
		}
		
		if (notValidMsg.length() > 0) {
			validationInfo.setValidationStatus(RecordFieldValidationStatus.ERROR);
			validationInfo.setValidationMessage(notValidMsg);
		}
	}
	
	/**
	 * Valid value
	 */
	protected void validValue() {
		resetStatus();
		
		validLenAnMandatory();
		if (FieldValidationInfo.RecordFieldValidationStatus.ERROR.equals(validationInfo.getValidationStatus())) {
			return;
		}
		
		String notValidMsg = "";
		if (FieldType.N.equals(type)) {
			if (validator != null) {
				validationInfo = validator.valid(name, index, subIndex, occurIndex, type, mandatory, value, fieldExtendedProperties);
			} else {
				if (isDouble()) {
					try {
						getValueAsDouble();
					} catch (FieldException rfe) {
						notValidMsg = rfe.getMessage();
					}
				} else if (isFloat()) {
					try {
						getValueAsFloat();
					} catch (FieldException rfe) {
						notValidMsg = rfe.getMessage();
					}
				} else if (isLong()) {
					try {
						getValueAsLong();
					} catch (FieldException rfe) {
						notValidMsg = rfe.getMessage();
					}
				} else if (isInteger()) {
					try {
						getValueAsInteger();
					} catch (FieldException rfe) {
						notValidMsg = rfe.getMessage();
					}
				}
			}
		} else if (FieldType.AN.equals(type)) {
			if (validator != null) {
				validationInfo = validator.valid(name, index, subIndex, occurIndex, type, mandatory, value, fieldExtendedProperties);
			} else {
				if (isDate()) {
					try {
						getValueAsDate();
					} catch (FieldException rfe) {
						notValidMsg = rfe.getMessage();
					}
				} else if (isBoolean()) {
					try {
						getValueAsBoolean();
					} catch (FieldException rfe) {
						notValidMsg = rfe.getMessage();
					}
				}
			}
		}
		
		if (notValidMsg.length() > 0) {
			validationInfo.setValidationStatus(RecordFieldValidationStatus.ERROR);
			validationInfo.setValidationMessage(notValidMsg);
		}
	}
	
	/**
	 * Return true if the value param is a numeric space. That's
	 * <p>
	 * FieldType.N and <code>value.trim().isEmpty()</code> is true
	 * 
	 * @param value the value to check if is a numeric space
	 * @return true if the value param si a numeric space
	 */
	protected boolean isNumericSpace(String value) {
		return FieldType.N.equals(getType()) && value != null && value.trim().isEmpty() && len > 0;
	}
	
	/**
	 * Parse the error message according the <code>type</code> param
	 * 
	 * @param type the type param
	 * @return the error message according the <code>type</code> param 
	 */
	protected String parseTypeErrorMessage(String type) {
		if (value != null) {
			return "Field name=[" + name + "] value=[" + value + "] is not " + type;
		} else {
			return "Field name=[" + name + "] is not " + type;
		}
	}

	/**
     * Returns a <code>String</code> object representing this field. The returned string is composed with the params
     * value of this field
     *
     * @return  a string representation of this field
     */
	@Override
	public String toString() {
		return "RecordField [value=" + value + ", defaultValue=" + defaultValue + ", name=" + name + ", type="
				+ type + ", index=" + index + ", subIndex=" + subIndex + ", occurIndex=" + occurIndex + ", len=" 
				+ len + ", mandatory=" + mandatory + ", recordWay=" 
				+ recordWay + ", validationInfo=" + validationInfo + ", validator=" + validator + ", removeDecimalSeparator=" 
				+ removeDecimalSeparator + ", decimalFormat=" + decimalFormat + ", booleanFormat=" + booleanFormat + ", dateFormat=" 
				+ dateFormat + ", customFormat=" + customFormat + ", pad=" + pad + ", padStr=" + padStr + ", padStrNum=" + padStrNum + "]";
	}
	
	/**
	 * Apply the method {@link String#toUpperCase} to the value of this field if this field is a String
	 */
	public void toUpperCase() {
		if (isString() && value != null && !value.isEmpty()) {
			value = value.toUpperCase();
		}
	}
	
	/**
	 * Apply the method toRemoveAccents to the value of this field if this field is a String
	 * <p>
	 * Every character with accent present in the value of this field is replaced with the relative
	 * character without accent. For example the character � is replaced with the character a
	 */
	public void toRemoveAccents() {
		if (isString() && value != null && !value.isEmpty()) {
			value = java.text.Normalizer.normalize(value, java.text.Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
		}
	}
	
	/**
	 * Apply the encoding with the Charset "US-ASCII" to the value of this if this field is a String
	 * <p>
	 * Every character out of the Charset "US-ASCII" present in the value of this field
	 * is replaced with the character ?. For example the character � is replaced with the character ?
	 */
	public void toAscii() {
		if (isString() && value != null && !value.isEmpty()) {
			value = new String(value.getBytes(Charset.forName("US-ASCII")));
		}
	}
	
	/**
	 * Applay toUpperCase, toRemoveAccents and toAscii to the value of this field. 
	 */
	public void toNormalize() {
		toRemoveAccents();
		toAscii();
		toUpperCase(); 
	}

	/**
	 * @return the validation info of this field
	 */
	public FieldValidationInfo getValidationInfo() {
		return validationInfo;
	}
	
	/**
	 * check the validation info of this field
	 */
	protected void checkValidationInfo() {
		validLenAnMandatory();
		
		if (FieldValidationInfo.RecordFieldValidationStatus.ERROR.equals(validationInfo.getValidationStatus())) {
			throw new FieldException(ErrorCode.FE10, "Validation Code " + validationInfo.getValidationCode() +
					" - Field " + name + " has status " + validationInfo.getValidationStatus().name() + 
					". Cause: " + validationInfo.getValidationMessage());
		}
	}
	
	
	/**
	 * Reset the status of this field to RecordFieldValidationStatus.INFO, the code to zero and the message to empty string
	 */
	public void resetStatus() {
		validationInfo.setValidationStatus(RecordFieldValidationStatus.INFO);
		validationInfo.setValidationCode(0); 
		validationInfo.setValidationMessage("");
	}
	
	/**
	 * Apply the pad to the <code>value</code> param
	 * 
	 * @param value the value to pad
	 * @return the padded value
	 */
	protected String doPad(String value) {
		String result = null;
		
		if (FieldExtendedPropertyType.RPAD.equals(pad)) {
			int toRemove = 0;
			char[] charArray = value.toCharArray();
			//invert array
			for (int i = 0; i < charArray.length / 2; i++) {
		        char temp = charArray[i];
		        charArray[i] = charArray[charArray.length - 1 - i];
		        charArray[charArray.length - 1 - i] = temp;
		    }
			
			for (int i = 0; i < charArray.length; i++) {
				String c = Character.toString(charArray[i]);
				if (padStr.equals(c)) {
					toRemove++;
				} else {
					break;
				}
			}
			
			value = value.substring(0, value.length() - toRemove);
		} else {
			int toRemove = 0;
			char[] charArray = value.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				String c = Character.toString(charArray[i]);
				if (padStr.equals(c)) {
					toRemove++;
				} else {
					break;
				}
			}
			
			value = value.substring(toRemove);
		}
		
		if (len - value.length() > 0) {
			if (FieldExtendedPropertyType.RPAD.equals(pad)) {
				result = value + String.format("%0" + (padStrNum = len - value.length()) + "d", 0).replace("0", padStr);
			} else {
				result = String.format("%0" + (padStrNum = len - value.length()) + "d", 0).replace("0", padStr) + value;
			}
		} else {
			result = value;
		}
		
		return result;
	}
	
	/**
	 * Unpad the <code>value</code> param
	 * 
	 * @param value the value to unpad
	 * @return the unpadded value
	 */
	protected String undoPad(String value) {
		return FieldExtendedPropertyType.RPAD.equals(pad) ? value.substring(0, (len - padStrNum)) : value.substring(padStrNum);
	}
	
	/**
	 * Apply the decimal separator to the <code>value</code> param
	 * 
	 * @param value the value to apply the decimal separator
	 * @return the value with the decimal separator
	 */
	protected String redoDecimalSeparator(String value) {
		int fractionDigits = decimalFormat.getMaximumFractionDigits();
		return value.substring(0, value.length() - fractionDigits) + "." + value.substring(value.length() - fractionDigits);
	}
	
	private boolean isLenToNormalize() {
		return len < 1 || lenNormalized;
	}
	
	private void doLenNormalization(String v) {
		len = v.length();
		lenNormalized = true;
	}
	
	/**
	 * Returns true if the len has been normalized, that's the initial len was zero
	 * 
	 * @return true if the len has been normalized
	 */
	public boolean isLenNormalized() {
		return lenNormalized;
	}

	/**
	 * @return the display name of the field
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Set the display name of the field
	 * 
	 * @param displayName the display name of the field
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the description of the field
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description of the field
	 * 
	 * @param description the description of the field
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
