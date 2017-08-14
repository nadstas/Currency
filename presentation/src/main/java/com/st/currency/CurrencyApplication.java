package com.st.currency;

import android.app.Application;

import com.st.data.CurrencyPersistentStorage;
import com.st.data.CurrencyRepository;
import com.st.domain.repository.CurrencyRepo;

import java.util.concurrent.Executors;

public
class CurrencyApplication
		extends Application
{
	private static CurrencyRepo sCurrencyRepository;

	@Override
	public
	void onCreate() {
		super.onCreate();
		sCurrencyRepository = new CurrencyRepository(
				Executors.newSingleThreadExecutor(),
				new CurrencyPersistentStorage(this)
		);
	}

	public static
	CurrencyRepo getCurrencyRepository() {
		return sCurrencyRepository;
	}
}
