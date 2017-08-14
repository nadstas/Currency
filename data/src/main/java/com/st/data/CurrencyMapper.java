package com.st.data;

import android.support.annotation.NonNull;

import com.st.data.entities.Valute;
import com.st.domain.Currency;

/**
 * Created by nsv on 12.08.17.
 */

public
class CurrencyMapper
{
	public
	Currency map(@NonNull final Valute valute) {
		return new Currency(
				valute.getNumCode(),
				valute.getCharCode(),
				valute.getNominal(),
				valute.getName(),
				valute.getValue()
		);
	}
}
