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
package org.hobsoft.entangle.demo.jsr303;

import javax.validation.constraints.Size;

import org.hobsoft.entangle.demo.Person;

/**
 * 
 * 
 * @author Mark Hobson
 */
public class Jsr303Person extends Person
{
	// Person methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Size(min = 3)
	@Override
	public String getName()
	{
		return super.getName();
	}
}
