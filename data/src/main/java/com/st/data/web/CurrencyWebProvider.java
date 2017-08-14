package com.st.data.web;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.st.data.entities.ValCurs;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;
import org.simpleframework.xml.transform.Transform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by nsv on 12.08.17.
 */

public
class CurrencyWebProvider
{
	private static final String URL = "http://www.cbr.ru/scripts/XML_daily.asp";

	@Nullable
	public
	ValCurs getCurrenciesValues() {
		try {
			final StringBuilder stringBuilder = new StringBuilder();
			final BufferedReader reader = bufferedReader(createConnection());

			String line = reader.readLine();

			while (line != null) {
				stringBuilder.append(line);
				line = reader.readLine();
			}

			return deserialize(stringBuilder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private
	URLConnection createConnection() throws IOException {
		final HttpURLConnection connection = ((HttpURLConnection) new URL(URL).openConnection());
		connection.setRequestMethod("GET");
		connection.setDoInput(true);
		connection.connect();
		return connection;
	}

	private
	BufferedReader bufferedReader(@NonNull final URLConnection urlConnection) throws IOException {
		return new BufferedReader(new InputStreamReader(createConnection().getInputStream()));
	}

	private
	ValCurs deserialize(@NonNull final String source) throws Exception {
		final RegistryMatcher matcher = new RegistryMatcher();
		matcher.bind(Double.class, new Transform()
		{
			@Override
			public
			Object read(final String value) throws Exception {
				return NumberFormat.getInstance(Locale.getDefault()).parse(value).doubleValue();
			}

			@Override
			public
			String write(final Object value) throws Exception {
				return value.toString();
			}
		});
		final Serializer serializer = new Persister(matcher);
		return serializer.read(ValCurs.class, source);
	}
}
