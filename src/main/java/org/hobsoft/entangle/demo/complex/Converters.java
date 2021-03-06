/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hobsoft.entangle.demo.complex;

import java.awt.Color;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.hobsoft.entangle.Converter;

/**
 * 
 * 
 * @author Mark Hobson
 */
final class Converters
{
	// constants --------------------------------------------------------------
	
	private static final Converter<String, String> EMPTY_STRING_TO_NULL = new Converter<String, String>()
	{
		public String convert(String string)
		{
			return (string != null && string.length() == 0) ? null : string;
		}
		
		public String unconvert(String string)
		{
			return convert(string);
		}
	};

	// constructors -----------------------------------------------------------
	
	private Converters()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static <S, T, U> Converter<S, U> compose(final Converter<S, T> converter1, final Converter<T, U> converter2)
	{
		return new Converter<S, U>()
		{
			public U convert(S object)
			{
				return converter2.convert(converter1.convert(object));
			}
			
			public S unconvert(U object)
			{
				return converter1.unconvert(converter2.unconvert(object));
			}
		};
	}
	
	public static Converter<String, String> emptyStringToNull()
	{
		// safe
		@SuppressWarnings("cast")
		Converter<String, String> converter = (Converter<String, String>) EMPTY_STRING_TO_NULL;
		
		return converter;
	}
	
	public static Converter<Date, String> dateToString(String pattern)
	{
		return dateToString(createDateFormat(pattern));
	}
	
	public static Converter<Date, String> dateToString(final DateFormat format)
	{
		return new Converter<Date, String>()
		{
			public String convert(Date date)
			{
				return (date != null) ? format.format(date) : null;
			}
			
			public Date unconvert(String string)
			{
				try
				{
					return (string != null) ? format.parse(string) : null;
				}
				catch (ParseException exception)
				{
					// TODO: handle exception better
					throw new RuntimeException(exception);
				}
			}
		};
	}
	
	public static Converter<Collection<String>, Color> violationsToColor()
	{
		return new Converter<Collection<String>, Color>()
		{
			public Color convert(Collection<String> violations)
			{
				return violations.isEmpty() ? Color.WHITE : new Color(255, 224, 224);
			}
			
			public Collection<String> unconvert(Color target)
			{
				throw new UnsupportedOperationException();
			}
		};
	}
	
	// private methods --------------------------------------------------------
	
	private static DateFormat createDateFormat(String pattern)
	{
		DateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);
		return format;
	}
}
