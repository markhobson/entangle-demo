/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/Converters.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example;

import org.hobsoft.entangle.Converter;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: Converters.java 97393 2011-12-29 10:29:43Z mark@IIZUKA.CO.UK $
 */
public final class Converters
{
	// TODO: move to common-binding?
	
	// constants --------------------------------------------------------------

	private static final Converter<Object, String> TO_STRING = new Converter<Object, String>()
	{
		public String convert(Object object)
		{
			return String.valueOf(object);
		}
		
		public Object unconvert(String target)
		{
			throw new UnsupportedOperationException();
		}
	};

	// constructors -----------------------------------------------------------

	private Converters()
	{
		throw new AssertionError();
	}

	// public methods ---------------------------------------------------------

	public static <T> Converter<T, String> toStringConverter()
	{
		// safe
		@SuppressWarnings("unchecked")
		Converter<T, String> converter = (Converter<T, String>) TO_STRING;
		
		return converter;
	}
}
