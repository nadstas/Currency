package com.st.data;

import com.st.domain.Currency;
import com.st.domain.repository.CurrencyRepo;

import java.util.List;

public
class CurrencyRepository
		implements CurrencyRepo
{
	@Override
	public
	Currency getCurrencyByName(final String name) {
		return null;
	}

	@Override
	public
	List<Currency> getAllCurrencies() {
		return null;
	}
}
