package com.github.parmag.fixefid.test.bean;

import com.github.parmag.fixefid.record.bean.FixefidField;
import com.github.parmag.fixefid.record.bean.FixefidRecord;

@FixefidRecord
public class PersonWithFakeAddress extends Person {
	@FixefidField(fieldOrdinal = 11)
	private FakeAddress fakeAddress = new FakeAddress();

	public FakeAddress getFakeAddress() {
		return fakeAddress;
	}

	public void setFakeAddress(FakeAddress fakeAddress) {
		this.fakeAddress = fakeAddress;
	}
}
