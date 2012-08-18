/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/jsr303/Jsr303Person.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example.jsr303;

import javax.validation.constraints.Size;

import example.Person;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: Jsr303Person.java 88840 2011-06-09 11:33:29Z mark@IIZUKA.CO.UK $
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
