/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/Person.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: Person.java 97901 2012-01-19 14:58:48Z mark@IIZUKA.CO.UK $
 */
public class Person
{
	// constants --------------------------------------------------------------
	
	public static final String NAME = "name";
	
	public static final String DATE_OF_BIRTH = "dateOfBirth";

	public static final String HOME_ADDRESS = "homeAddress";
	
	public static final String WORK_ADDRESS = "workAddress";
	
	// fields -----------------------------------------------------------------
	
	private final PropertyChangeSupport support;
	
	private String name;
	
	private Date dateOfBirth;
	
	private Address homeAddress;
	
	private Address workAddress;

	// constructors -----------------------------------------------------------
	
	public Person()
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
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		String oldName = this.name;
		this.name = name;
		support.firePropertyChange(NAME, oldName, this.name);
	}
	
	public Date getDateOfBirth()
	{
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth)
	{
		Date oldDateOfBirth = this.dateOfBirth;
		this.dateOfBirth = dateOfBirth;
		support.firePropertyChange(DATE_OF_BIRTH, oldDateOfBirth, this.dateOfBirth);
	}
	
	public Address getHomeAddress()
	{
		return homeAddress;
	}
	
	public void setHomeAddress(Address homeAddress)
	{
		Address oldHomeAddress = this.homeAddress;
		this.homeAddress = homeAddress;
		support.firePropertyChange(HOME_ADDRESS, oldHomeAddress, this.homeAddress);
	}
	
	public Address getWorkAddress()
	{
		return workAddress;
	}
	
	public void setWorkAddress(Address workAddress)
	{
		Address oldWorkAddress = this.workAddress;
		this.workAddress = workAddress;
		support.firePropertyChange(WORK_ADDRESS, oldWorkAddress, this.workAddress);
	}
	
	// Object methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getClass().getName()
			+ "[name=" + name
			+ ",dateOfBirth=" + dateOfBirth
			+ ",homeAddress=" + homeAddress
			+ ",workAddress=" + workAddress
			+ "]";
	}
}
