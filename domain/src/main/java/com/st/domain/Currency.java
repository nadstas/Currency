package com.st.domain;

public
class Currency
{
	private final int mCode;
	private final String mCharCode;
	private final int mNominal;
	private final String mName;
	private final double mValue;

	public
	Currency(final int code, final String charCode, final int nominal, final String name, final double value) {
		mCode = code;
		mCharCode = charCode;
		mNominal = nominal;
		mName = name;
		mValue = value;
	}

	public
	int getCode() {
		return mCode;
	}

	public
	int getNominal() {
		return mNominal;
	}

	public
	double getValue() {
		return mValue;
	}

	public
	String getCharCode() {
		return mCharCode;
	}

	public
	String getName() {
		return mName;
	}
}
