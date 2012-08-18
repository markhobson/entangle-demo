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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: Address.java 97901 2012-01-19 14:58:48Z mark@IIZUKA.CO.UK $
 */
public class Address
{
	// constants --------------------------------------------------------------
	
	public static final String STREET = "street";
	
	public static final String TOWN = "town";

	// fields -----------------------------------------------------------------
	
	private final PropertyChangeSupport support;
	
	private String street;
	
	private String town;
	
	// constructors -----------------------------------------------------------
	
	public Address()
	{
		support = new PropertyChangeSupport(this);
	}
	
	// public methods ---------------------------------------------------------

	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		support.removePropertyChangeListener(listener);
	}
	
	public String getStreet()
	{
		return street;
	}
	
	public void setStreet(String street)
	{
		String oldStreet = this.street;
		this.street = street;
		support.firePropertyChange(STREET, oldStreet, this.street);
	}
	
	public String getTown()
	{
		return town;
	}
	
	public void setTown(String town)
	{
		String oldTown = this.town;
		this.town = town;
		support.firePropertyChange(TOWN, oldTown, this.town);
	}
	
	// Object methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getClass().getName()
			+ "[street=" + street
			+ ",town=" + town
			+ "]";
	}
}
