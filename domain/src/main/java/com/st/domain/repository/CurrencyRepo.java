package com.st.domain.repository;

import com.st.domain.Currency;

import java.util.List;

public
interface CurrencyRepo
{
	Currency getCurrencyByName(String name);
	List<Currency> getAllCurrencies();
}
