package com.st.domain;

import com.st.domain.functions.Consumer;
import com.st.domain.interactors.CurrencyInteractor;
import com.st.domain.repository.CurrencyRepo;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public
class CurrencyInteractorTest
{
	private static final int RUB_RATE = 1;
	private static final double AMD_RATE = 12.5245;
	private static final double BYN_RATE = 30.7017;

	private CurrencyInteractor mInteractor;

	@Before
	public
	void setUp() {
		mInteractor = new CurrencyInteractor(mockRouter(), mockRepository());
	}

	@Test
	public
	void convertTest() {
		final Currency rub = new Currency(1, "RUB", 1, "Rouble", RUB_RATE);
		final Currency amd = new Currency(1, "AMD", 100, "Армянских драмов", AMD_RATE);

		final double result = mInteractor.convertCurrency(rub, amd, 100);
		assertThat(result).isEqualTo(798.4350672681544);
	}

	@Test
	public
	void equalCurrencyConvertTest() {
		final Currency rub1 = new Currency(1, "RUB", 1, "Rouble", RUB_RATE);
		final Currency rub2 = new Currency(1, "RUB", 1, "Rouble", RUB_RATE);

		final double result = mInteractor.convertCurrency(rub1, rub2, 1);
		assertThat(result).isEqualTo(1);
	}

	@Test
	public
	void differentCurrenciesConvertTest() {
		final Currency byn = new Currency(1, "BYN", 1, "Белорусский рубль", BYN_RATE);
		final Currency amd = new Currency(1, "AMD", 100, "Армянских драмов", AMD_RATE);

		final double result = mInteractor.convertCurrency(byn, amd, 20);
		assertThat(result).isEqualTo(4902.662780949339);
	}

	private
	CurrencyRouter mockRouter() {
		return (list, onClick) -> {
		};
	}

	private
	CurrencyRepo mockRepository() {
		return new CurrencyRepo()
		{
			@Override
			public
			void getAllCurrencies(final Consumer<List<Currency>> consumer) {

			}

			@Override
			public
			void loadAllCurrencies(final Consumer<List<Currency>> consumer) {

			}

			@Override
			public
			void getPrimaryCurrency(final Consumer<Currency> consumer) {

			}

			@Override
			public
			void getSecondaryCurrency(final Consumer<Currency> consumer) {

			}

			@Override
			public
			void setPrimaryCurrency(final Currency currency) {

			}

			@Override
			public
			void setSecondaryCurrency(final Currency currency) {

			}

			@Override
			public
			void setLastValue(final double lastValue) {

			}

			@Override
			public
			void getLastValue(final Consumer<Double> consumer) {

			}
		};
	}
}
