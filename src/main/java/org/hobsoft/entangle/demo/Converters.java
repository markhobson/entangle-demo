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
package org.hobsoft.entangle.demo;

import org.hobsoft.entangle.Converter;

/**
 * 
 * 
 * @author Mark Hobson
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
