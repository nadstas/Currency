package com.st.currency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.currency.databinding.FragmentConvertCurrencyBinding;
import com.st.currency.utils.Router;
import com.st.currency.viewmodels.CurrencyViewModel;
import com.st.domain.interactors.CurrencyInteractor;

import static com.st.currency.CurrencyApplication.getCurrencyRepository;

/**
 * Created by nsv on 12.08.17.
 */

public
class ConvertCurrencyFragment
		extends Fragment
{
	private FragmentConvertCurrencyBinding mBinding;

	@Nullable
	@Override
	public
	View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
		mBinding = FragmentConvertCurrencyBinding.inflate(inflater);
		return mBinding.getRoot();
	}

	@Override
	public
	void onStart() {
		super.onStart();
		mBinding.setVm(new CurrencyViewModel(
						new CurrencyInteractor(
								new Router(getContext()), getCurrencyRepository())
				)
		);
	}
}
