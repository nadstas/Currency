package com.st.data.async;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.st.domain.functions.Consumer;
import com.st.domain.functions.Provider;

import java.util.concurrent.Executor;

/**
 * Created by nsv on 13.08.17.
 */

public
class AsyncExecutor
{
	@NonNull
	private final Executor mWorkExecutor;
	@NonNull
	private final Handler mAnswerHandler;

	public
	AsyncExecutor(@NonNull final Executor workExecutor,
	              @NonNull final Handler answerHandler) {
		mWorkExecutor = workExecutor;
		mAnswerHandler = answerHandler;
	}

	public
	void execute(@NonNull final Runnable runnable) {
		mWorkExecutor.execute(runnable);
	}

	public
	<Type>
	void execute(final Provider<Type> provider, final Consumer<Type> consumer) {
		mWorkExecutor.execute(() -> {
			final Type value = provider.get();
			mAnswerHandler.post(() -> consumer.apply(value));
		});
	}
}
