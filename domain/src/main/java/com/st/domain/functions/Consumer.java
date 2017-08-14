package com.st.domain.functions;

/**
 * Created by nsv on 13.08.17.
 */

public
interface Consumer <T>
{
	void apply(T value);
}
