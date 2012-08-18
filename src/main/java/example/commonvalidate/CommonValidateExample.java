/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/commonvalidate/CommonValidateExample.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example.commonvalidate;

import static uk.co.iizuka.common.binding.Observables.bean;
import static uk.co.iizuka.common.binding.swing.SwingObservables.component;
import static uk.co.iizuka.common.binding.validate.CommonValidateConverters.violationsToString;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import example.Converters;
import example.Person;

import uk.co.iizuka.common.binding.Binder;
import uk.co.iizuka.common.binding.Binders;
import uk.co.iizuka.common.binding.validate.CommonValidateValidators;
import uk.co.iizuka.common.validate.PropertyViolation;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: CommonValidateExample.java 101061 2012-05-03 13:40:58Z mark@IIZUKA.CO.UK $
 */
public final class CommonValidateExample
{
	// constructors -----------------------------------------------------------
	
	private CommonValidateExample()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static void main(String[] args)
	{
		// create view
		
		JPanel viewPanel = new JPanel();
		viewPanel.setBorder(BorderFactory.createTitledBorder("View"));
		viewPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		
		viewPanel.add(new JLabel("Name"), constraints);
		
		JTextField name = new JTextField(20);
		viewPanel.add(name, constraints);
		
		JLabel message = createLabel(300);
		message.setForeground(Color.RED);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		viewPanel.add(message, constraints);
		
		// create model
		
		CommonValidatePerson model = new CommonValidatePerson();
		
		JPanel modelPanel = new JPanel();
		modelPanel.setBorder(BorderFactory.createTitledBorder("Model"));
		modelPanel.setLayout(new BoxLayout(modelPanel, BoxLayout.LINE_AXIS));
		
		JTextArea modelArea = new JTextArea(model.toString());
		modelArea.setEditable(false);
		modelPanel.add(modelArea);
		
		// create frame
		
		JPanel framePanel = new JPanel();
		framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.PAGE_AXIS));
		framePanel.add(viewPanel);
		framePanel.add(modelPanel);
		
		JFrame frame = new JFrame("Common Binding Example");
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(framePanel);
		frame.pack();

		// bind view to model
		
		Binder<PropertyViolation> binder = Binders.newBinder();
		
		binder.bind(bean(model).string(Person.NAME))
			.checking(CommonValidateValidators.<String>property(CommonValidatePerson.class, Person.NAME))
			.to(component(name).text());
		
		binder.bind(binder)
			.using(violationsToString())
			.to(component(message).text());

		binder.bind(bean(model))
			.using(Converters.<CommonValidatePerson>toStringConverter())
			.to(component(modelArea).text());
		
		binder.bind();
		
		// show view
		
		frame.setVisible(true);
	}
	
	// private methods --------------------------------------------------------
	
	private static JLabel createLabel(final int preferredWidth)
	{
		return new JLabel()
		{
			@Override
			public Dimension getPreferredSize()
			{
				Dimension size = super.getPreferredSize();
				size.width = preferredWidth;
				return size;
			}
		};
	}
}
