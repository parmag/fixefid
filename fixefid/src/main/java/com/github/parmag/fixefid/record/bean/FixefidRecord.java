package com.github.parmag.fixefid.record.bean;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.parmag.fixefid.record.RecordWay;

@Retention(RUNTIME)
@Target(TYPE)
public @interface FixefidRecord {
	public int recordLen() default 0;
	public RecordWay recordWay() default RecordWay.IN;
}
