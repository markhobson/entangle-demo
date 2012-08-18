/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/Address.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example;

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
