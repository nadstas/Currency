package com.st.data.async;

import android.support.annotation.NonNull;

import com.st.domain.functions.Consumer;
import com.st.domain.functions.Provider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nsv on 13.08.17.
 */

public
class Action <T>
{
	@NonNull
	private final AsyncExecutor mAsyncExecutor;
	@NonNull
	private Runnable mEndAction = () -> {
	};

	@NonNull
	private final List<WeakReference<Consumer<T>>> mObservers = new ArrayList<>();

	public
	Action(@NonNull final AsyncExecutor asyncExecutor) {
		mAsyncExecutor = asyncExecutor;
	}

	public
	void addSubscriber(final Consumer<T> consumer) {
		mObservers.add(new WeakReference<>(consumer));
	}

	public
	Action<T> execute(@NonNull final Provider<T> provider) {
		mAsyncExecutor.execute(provider, value -> {
			final ArrayList<WeakReference<Consumer<T>>> observers = new ArrayList<>(mObservers);
			for (final WeakReference<Consumer<T>> observer : observers) {
				final Consumer<T> consumer = observer.get();
				if (consumer != null) {
					consumer.apply(value);
				}
			}
			mEndAction.run();
		});
		return this;
	}

	public
	Action<T> withEndAction(@NonNull final Runnable runnable) {
		mEndAction = runnable;
		return this;
	}
}
