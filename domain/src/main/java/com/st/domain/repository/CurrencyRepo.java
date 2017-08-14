package com.st.domain.repository;

import com.st.domain.Currency;
import com.st.domain.functions.Consumer;

import java.util.List;

public
interface CurrencyRepo
{
	void getAllCurrencies(Consumer<List<Currency>> consumer);
	void loadAllCurrencies(Consumer<List<Currency>> consumer);

	void getPrimaryCurrency(Consumer<Currency> consumer);
	void getSecondaryCurrency(Consumer<Currency> consumer);

	void setPrimaryCurrency(Currency currency);
	void setSecondaryCurrency(Currency currency);

	void setLastValue(double lastValue);
	void getLastValue(Consumer<Double> consumer);
}
