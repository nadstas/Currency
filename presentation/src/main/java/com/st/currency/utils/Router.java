package com.st.currency.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.st.domain.CurrencyRouter;
import com.st.domain.functions.Consumer;

/**
 * Created by nsv on 14.08.17.
 */

public
class Router
		implements CurrencyRouter
{
	@NonNull
	private final Context mContext;

	public
	Router(@NonNull final Context context) {
		mContext = context;
	}

	@Override
	public
	void showCurrencyPiker(final String[] list, final Consumer<String> onClick) {
		new AlertDialog.Builder(mContext)
				.setItems(list, (dialogInterface, i) -> onClick.apply(list[i]))
				.create()
				.show();
	}
}
