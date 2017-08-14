package com.st.currency.utils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.support.annotation.NonNull;
import android.widget.EditText;

/**
 * Created by nsv on 12.08.17.
 */

public
class DataBindingUtils
{
	@BindingAdapter("android:text")
	public static
	void bindIntegerInText(@NonNull final EditText editText, final double value) {
		if (getIntegerFromBinding(editText) != value) {
			editText.setText(String.valueOf(value));
		}
	}

	@InverseBindingAdapter(attribute = "android:text")
	public static
	double getIntegerFromBinding(@NonNull final EditText view) {
		try {
			return Double.parseDouble(view.getText().toString());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
