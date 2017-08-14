package com.st.domain.interactors;

import com.st.domain.Currency;
import com.st.domain.CurrencyRouter;
import com.st.domain.functions.Consumer;
import com.st.domain.repository.CurrencyRepo;

import java.util.List;

public
class CurrencyInteractor
{
	private final CurrencyRouter mRouter;
	private final CurrencyRepo mRepository;
	private Consumer<Currency> mFirstCurrencyChangeListener;
	private Consumer<Currency> mSecondCurrencyChangeListener;

	public
	CurrencyInteractor(final CurrencyRouter router,
	                   final CurrencyRepo repository) {
		mRouter = router;
		mRepository = repository;
	}

	public
	void onPrimaryCurrencySelect() {
		mRepository.getAllCurrencies(currencies -> {
			final String[] names = new String[currencies.size()];
			for (int i = 0; i < currencies.size(); i++) {
				names[i] = currencies.get(i).getCharCode();
			}

			mRouter.showCurrencyPiker(names, name -> {
				final Currency currency = find(currencies, name);
				mRepository.setPrimaryCurrency(currency);
				mFirstCurrencyChangeListener.apply(currency);
			});
		});
	}

	public
	void onSecondaryCurrencySelect() {
		mRepository.getAllCurrencies(currencies -> {
			final String[] names = new String[currencies.size()];
			for (int i = 0; i < currencies.size(); i++) {
				names[i] = currencies.get(i).getCharCode();
			}

			mRouter.showCurrencyPiker(names, name -> {
				final Currency currency = find(currencies, name);
				mRepository.setSecondaryCurrency(currency);
				mSecondCurrencyChangeListener.apply(currency);
			});
		});
	}

	public
	void getPrimaryCurrency(final Consumer<Currency> currencyConsumer, final Runnable onError) {
		mFirstCurrencyChangeListener = currencyConsumer;
		mRepository.getPrimaryCurrency(currency -> {
			if (currency == null) {
				loadAllCurrenies(currencyConsumer, onError);
			} else {
				currencyConsumer.apply(currency);
			}
		});
	}

	public
	void getSecondaryCurrency(final Consumer<Currency> currencyConsumer, final Runnable onError) {
		mSecondCurrencyChangeListener = currencyConsumer;
		mRepository.getSecondaryCurrency(currency -> {
			if (currency == null) {
				loadAllCurrenies(currencyConsumer, onError);
			} else {
				currencyConsumer.apply(currency);
			}
		});
	}

	public
	void getLastValue(final Consumer<Double> consumer) {
		mRepository.getLastValue(consumer);
	}

	private
	void loadAllCurrenies(final Consumer<Currency> currencyConsumer, final Runnable onError) {
		mRepository.loadAllCurrencies(currencies -> {
			if (currencies == null || currencies.isEmpty()) {
				onError.run();
			} else {
				currencyConsumer.apply(currencies.get(0));
			}
		});
	}

	public
	void convertCurrency(final double amount, final Consumer<Double> result) {
		mRepository.getPrimaryCurrency(primary ->
				mRepository.getSecondaryCurrency(secondary -> {
					mRepository.setLastValue(amount);
					result.apply(convertCurrency(primary, secondary, amount));
				}));
	}

	public
	double convertCurrency(final Currency sourceCurrency, final Currency destCurrency, final double amount) {
		return getRealValue(sourceCurrency) / getRealValue(destCurrency) * amount;
	}

	private
	double getRealValue(final Currency currency) {
		return currency.getValue() / currency.getNominal();
	}

	private
	Currency find(final List<Currency> currencies, final String name) {
		for (final Currency currency : currencies) {
			if (currency.getCharCode().equals(name)) {
				return currency;
			}
		}
		throw new IllegalArgumentException();
	}
}
