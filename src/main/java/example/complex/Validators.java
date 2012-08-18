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
package example.complex;

import java.util.Collection;
import java.util.Collections;

import org.hobsoft.entangle.Converter;
import org.hobsoft.entangle.Validator;

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
