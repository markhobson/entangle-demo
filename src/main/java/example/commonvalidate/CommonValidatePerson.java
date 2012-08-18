/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/commonvalidate/CommonValidatePerson.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example.commonvalidate;

import example.Person;

import uk.co.iizuka.common.validate.constraint.MinLength;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: CommonValidatePerson.java 88839 2011-06-09 11:09:49Z mark@IIZUKA.CO.UK $
 */
public class CommonValidatePerson extends Person
{
	// Person methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@MinLength(3)
	@Override
	public void setName(String name)
	{
		super.setName(name);
	}
}
