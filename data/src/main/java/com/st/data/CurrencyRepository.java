package com.st.data;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

import com.st.data.async.Action;
import com.st.data.async.AsyncExecutor;
import com.st.data.entities.ValCurs;
import com.st.data.entities.Valute;
import com.st.data.web.CurrencyWebProvider;
import com.st.domain.Currency;
import com.st.domain.functions.Consumer;
import com.st.domain.functions.Provider;
import com.st.domain.repository.CurrencyRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public
class CurrencyRepository
		implements CurrencyRepo
{
	private static final int GET_PRIMARY_CURRENCY_ACTION_KEY = 1;
	private static final int GET_SECONDARY_CURRENCY_ACTION_KEY = 2;
	private static final int GET_CURRENCY_LIST_WEB_ACTION_KEY = 3;
	private static final int GET_CURRENCY_LIST_STORAGE_ACTION_KEY = 4;
	private static final int GET_LAST_VALUE_ACTION_KEY = 5;

	@NonNull
	private final CurrencyPersistentStorage mStorage;
	@NonNull
	private final CurrencyWebProvider mWebProvider = new CurrencyWebProvider();
	@NonNull
	private final CurrencyMapper mMapper = new CurrencyMapper();
	@NonNull
	private final AsyncExecutor mExecutor;

	@NonNull
	private final SparseArrayCompat<Action<?>> mActions = new SparseArrayCompat<>();

	public
	CurrencyRepository(@NonNull final Executor executor, @NonNull final CurrencyPersistentStorage storage) {
		mStorage = storage;
		mExecutor = new AsyncExecutor(executor, new Handler());
		getCurrenciesFromWeb(value -> {
		});
	}


	@Override
	public
	void getAllCurrencies(final Consumer<List<Currency>> consumer) {
		getCurrenciesFromStorage(list -> {
			if (list == null || list.isEmpty()) {
				getCurrenciesFromWeb(consumer);
			} else {
				consumer.apply(list);
			}
		});
	}

	@Override
	public
	void loadAllCurrencies(final Consumer<List<Currency>> consumer) {
		getCurrenciesFromStorage(consumer);
		getCurrenciesFromWeb(consumer);
	}

	private
	void getCurrenciesFromStorage(final Consumer<List<Currency>> consumer) {
		execute(GET_CURRENCY_LIST_STORAGE_ACTION_KEY, mStorage::getAllCurrencies, consumer);
	}

	private
	void getCurrenciesFromWeb(final Consumer<List<Currency>> consumer) {
		execute(GET_CURRENCY_LIST_WEB_ACTION_KEY, () -> {
			final ValCurs valCurs = mWebProvider.getCurrenciesValues();

			if (valCurs == null) {
				return new ArrayList<>();
			}

			final List<Valute> valuteList = valCurs.getValuteList();
			final List<Currency> currencyList = new ArrayList<>(valuteList.size());

			for (final Valute valute : valuteList) {
				currencyList.add(mMapper.map(valute));
			}

			mStorage.setLastDate(valCurs.getDate());
			mStorage.setCurrenciesList(currencyList);

			return currencyList;
		}, consumer);
	}

	@Override
	public
	void getPrimaryCurrency(final Consumer<Currency> consumer) {
		execute(GET_PRIMARY_CURRENCY_ACTION_KEY, mStorage::getPrimaryCurrency, consumer);
	}

	@Override
	public
	void getSecondaryCurrency(final Consumer<Currency> consumer) {
		execute(GET_SECONDARY_CURRENCY_ACTION_KEY, mStorage::getSecondaryCurrency, consumer);
	}

	@Override
	public
	void setPrimaryCurrency(final Currency currency) {
		mExecutor.execute(() -> mStorage.setPrimaryCurrency(currency));
	}

	@Override
	public
	void setSecondaryCurrency(final Currency currency) {
		mExecutor.execute(() -> mStorage.setSecondaryCurrency(currency));
	}

	@Override
	public
	void setLastValue(final double lastValue) {
		mExecutor.execute(() -> mStorage.setLastValue(lastValue));
	}

	@Override
	public
	void getLastValue(final Consumer<Double> consumer) {
		execute(GET_LAST_VALUE_ACTION_KEY, mStorage::getLastValue, consumer);
	}

	private
	<T>
	void execute(final int key, final Provider<T> provider, final Consumer<T> consumer) {
		getAction(key, action(key, provider)).addSubscriber(consumer);
	}

	private
	<T>
	Action<T> getAction(final int key, @NonNull final Action<T> defaultAction) {
		//noinspection unchecked
		return (Action<T>) mActions.get(key, defaultAction);
	}

	private
	<T> Action<T> action(final int key, @NonNull final Provider<T> provider) {
		return new Action<T>(mExecutor)
				.withEndAction(() -> mActions.remove(key))
				.execute(provider);
	}
}
