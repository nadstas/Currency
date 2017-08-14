package com.st.data.entities;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by nsv on 12.08.17.
 */
@Root(strict = false)
public
class ValCurs
{
	@Attribute(name = "Date")
	private String mDate;
	@ElementList(entry = "Valute", inline = true, type = Valute.class)
	private List<Valute> mValuteList;

	public
	String getDate() {
		return mDate;
	}

	public
	List<Valute> getValuteList() {
		return mValuteList;
	}
}
