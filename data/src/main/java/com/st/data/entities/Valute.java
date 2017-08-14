package com.st.data.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by nsv on 12.08.17.
 */
@Root(strict = false)
public
class Valute
{
	@Element(name = "NumCode")
	private int mNumCode;
	@Element(name = "CharCode")
	private String mCharCode;
	@Element(name = "Nominal")
	private int mNominal;
	@Element(name = "Name")
	private String mName;
	@Element(name = "Value")
	private Double mValue;

	public
	int getNumCode() {
		return mNumCode;
	}

	public
	String getCharCode() {
		return mCharCode;
	}

	public
	int getNominal() {
		return mNominal;
	}

	public
	String getName() {
		return mName;
	}

	public
	double getValue() {
		return mValue;
	}
}
