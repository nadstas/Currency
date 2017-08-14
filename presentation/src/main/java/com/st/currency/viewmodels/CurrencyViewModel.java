package com.st.currency.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;

import com.st.domain.interactors.CurrencyInteractor;

public
class CurrencyViewModel
{
	private final ObservableField<String> mFirstCurrencyName = new ObservableField<>("RUB");
	private final ObservableField<String> mSecondCurrencyName = new ObservableField<>("RUB");
	private final ObservableDouble mSourceValue = new ObservableDouble(1);
	private final ObservableDouble mResult = new ObservableDouble(0);
	private final ObservableBoolean mControlsEnabled = new ObservableBoolean(false);

	private final CurrencyInteractor mInteractor;

	public
	CurrencyViewModel(final CurrencyInteractor interactor) {
		mInteractor = interactor;

		mInteractor.getLastValue(mSourceValue::set);

		mInteractor.getPrimaryCurrency(value -> {
			mControlsEnabled.set(true);
			mFirstCurrencyName.set(value.getCharCode());
		}, () -> mControlsEnabled.set(false));

		mInteractor.getSecondaryCurrency(value -> {
			mControlsEnabled.set(true);
			mSecondCurrencyName.set(value.getCharCode());
		}, () -> mControlsEnabled.set(false));
	}

	//region getters
	public
	ObservableField<String> getFirstCurrencyName() {
		return mFirstCurrencyName;
	}

	public
	ObservableField<String> getSecondCurrencyName() {
		return mSecondCurrencyName;
	}

	public
	ObservableDouble getResult() {
		return mResult;
	}

	public
	ObservableDouble getSourceValue() {
		return mSourceValue;
	}

	public
	ObservableBoolean getControlsEnabled() {
		return mControlsEnabled;
	}
	//endregion

	public
	void setSourceValue(final double sourceValue) {
		mSourceValue.set(sourceValue);
	}

	public
	void onConvertClicked() {
		mInteractor.convertCurrency(mSourceValue.get(), mResult::set);
	}

	public
	void onFirstCurrencyClicked() {
		mInteractor.onPrimaryCurrencySelect();
	}

	public
	void onSecondCurrencyClicked() {
		mInteractor.onSecondaryCurrencySelect();
	}
}
