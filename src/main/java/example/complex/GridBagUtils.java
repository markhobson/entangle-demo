/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/complex/GridBagUtils.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example.complex;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: GridBagUtils.java 97901 2012-01-19 14:58:48Z mark@IIZUKA.CO.UK $
 */
final class GridBagUtils
{
	// constructors -----------------------------------------------------------
	
	private GridBagUtils()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static void addRow(Container container, Component... components)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(0, 0, 4, 4);

		for (int i = 0; i < components.length - 1; i++)
		{
			container.add(components[i], constraints);
		}
		
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 1;

		container.add(components[components.length - 1], constraints);
	}
}
