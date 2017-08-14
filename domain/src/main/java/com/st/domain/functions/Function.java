package com.st.domain.functions;

/**
 * Created by nsv on 13.08.17.
 */

public
interface Function <I, O>
{
	O call(I value);
}
