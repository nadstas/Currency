package com.st.domain.interactors;

import com.st.domain.Currency;

public
class CurrencyInteractor
{
	public
	double convertCurrency(final Currency sourceCurrency, final Currency destCurrency, final double amount) {
		return getRealValue(sourceCurrency) / getRealValue(destCurrency) * amount;
	}

	private
	double getRealValue(final Currency currency) {
		return currency.getValue() / currency.getNominal();
	}
}
