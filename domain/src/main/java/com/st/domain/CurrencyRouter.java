package com.st.domain;

import com.st.domain.functions.Consumer;

/**
 * Created by nsv on 12.08.17.
 */

public
interface CurrencyRouter
{
	void showCurrencyPiker(String[] list, Consumer<String> onClick);
}
