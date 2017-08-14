package com.st.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.st.domain.Currency;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nsv on 12.08.17.
 */

public
class CurrencyPersistentStorage
{
	private static final String PRIMARY_CURRENCY_KEY = "A";
	private static final String SECONDARY_CURRENCY_KEY = "B";
	private static final String CURRENCIES_LIST = "C";
	private static final String LAST_DATE_KEY = "D";
	private static final String LAST_VALUE_KEY = "E";

	private static final String CURRENCY_CODE = "code";
	private static final String CURRENCY_CHAR_CODE = "charCode";
	private static final String CURRENCY_NOMINAL = "nominal";
	private static final String CURRENCY_NAME = "name";
	private static final String CURRENCY_VALUE = "value";

	@NonNull
	private final SharedPreferences mSharedPreferences;

	public
	CurrencyPersistentStorage(@NonNull final Context context) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Nullable
	public
	List<Currency> getAllCurrencies() {
		final Set<String> stringSet = mSharedPreferences.getStringSet(CURRENCIES_LIST, Collections.emptySet());
		final List<Currency> currencies = new ArrayList<>();
		for (final String s : stringSet) {
			final Currency currency = fromString(s);
			if (currency != null) {
				currencies.add(currency);
			}
		}
		return currencies;
	}

	public
	void setCurrenciesList(@NonNull final List<Currency> currencies) {
		final HashSet<String> set = new HashSet<>();
		for (final Currency currency : currencies) {
			set.add(toString(currency));
		}
		mSharedPreferences.edit().putStringSet(CURRENCIES_LIST, set).apply();
	}

	@Nullable
	public
	Currency getPrimaryCurrency() {
		final String string = mSharedPreferences.getString(PRIMARY_CURRENCY_KEY, "");
		return fromString(string);
	}

	@Nullable
	public
	Currency getSecondaryCurrency() {
		final String string = mSharedPreferences.getString(SECONDARY_CURRENCY_KEY, "");
		return fromString(string);
	}

	@NonNull
	public
	String getLastDate() {
		return mSharedPreferences.getString(LAST_DATE_KEY, "");
	}

	public
	void setPrimaryCurrency(@NonNull final Currency currency) {
		mSharedPreferences.edit().putString(PRIMARY_CURRENCY_KEY, toString(currency)).apply();
	}

	public
	void setSecondaryCurrency(@NonNull final Currency currency) {
		mSharedPreferences.edit().putString(SECONDARY_CURRENCY_KEY, toString(currency)).apply();
	}

	public
	void setLastDate(@NonNull final String lastDate) {
		mSharedPreferences.edit().putString(LAST_DATE_KEY, lastDate).apply();
	}

	public
	double getLastValue() {
		return mSharedPreferences.getFloat(LAST_VALUE_KEY, 1);
	}

	public
	void setLastValue(final double lastValue) {
		mSharedPreferences.edit().putFloat(LAST_VALUE_KEY, (float) lastValue).apply();
	}

	@NonNull
	private
	String toString(@NonNull final Currency currency) {
		try {
			return new JSONObject()
					.put(CURRENCY_CODE, currency.getCode())
					.put(CURRENCY_CHAR_CODE, currency.getCharCode())
					.put(CURRENCY_NOMINAL, currency.getNominal())
					.put(CURRENCY_NAME, currency.getName())
					.put(CURRENCY_VALUE, currency.getValue())
					.toString();
		} catch (JSONException e) {
			throw new IllegalArgumentException();
		}
	}

	@Nullable
	private
	Currency fromString(@NonNull final String str) {
		try {
			final JSONObject json = new JSONObject(str);
			return new Currency(
					json.getInt(CURRENCY_CODE),
					json.getString(CURRENCY_CHAR_CODE),
					json.getInt(CURRENCY_NOMINAL),
					json.getString(CURRENCY_NAME),
					json.getDouble(CURRENCY_VALUE)
			);
		} catch (JSONException e) {
			return null;
		}
	}
}
