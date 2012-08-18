/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/complex/Validators.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example.complex;

import java.util.Collection;
import java.util.Collections;

import uk.co.iizuka.common.binding.Converter;
import uk.co.iizuka.common.binding.Validator;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: Validators.java 97901 2012-01-19 14:58:48Z mark@IIZUKA.CO.UK $
 */
public final class Validators
{
	// constructors -----------------------------------------------------------
	
	private Validators()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static Validator<Object, String> isNotNull(final String message)
	{
		return new Validator<Object, String>()
		{
			@Override
			public Collection<String> validate(Object value)
			{
				return (value == null) ? Collections.singleton(message) : null;
			}
		};
	}
	
	public static Validator<String, String> isMinLength(final int length, final String message)
	{
		return new Validator<String, String>()
		{
			@Override
			public Collection<String> validate(String string)
			{
				return (string != null && string.length() < length) ? Collections.singleton(message) : null;
			}
		};
	}
	
	public static <T> Validator<T, String> canUnconvert(final Converter<?, T> converter, final String message)
	{
		return new Validator<T, String>()
		{
			@Override
			public Collection<String> validate(T target)
			{
				try
				{
					converter.unconvert(target);
				}
				catch (RuntimeException exception)
				{
					return Collections.singleton(message);
				}
				
				return null;
			}
		};
	}
}
