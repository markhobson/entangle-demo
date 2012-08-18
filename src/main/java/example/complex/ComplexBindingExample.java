/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/complex/ComplexBindingExample.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example.complex;

import static org.hobsoft.entangle.Observables.bean;
import static org.hobsoft.entangle.swing.SwingObservables.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import example.Converters;
import example.Person;

import org.hobsoft.entangle.Binder;
import org.hobsoft.entangle.Binders;
import org.hobsoft.entangle.Binding.Phase;
import org.hobsoft.entangle.Converter;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: ComplexBindingExample.java 97901 2012-01-19 14:58:48Z mark@IIZUKA.CO.UK $
 */
public final class ComplexBindingExample
{
	// constructors -----------------------------------------------------------
	
	private ComplexBindingExample()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static void main(String[] args)
	{
		// create model
		
		Person model = new Person();
		
		JPanel modelPanel = new JPanel(new BorderLayout(4, 4));
		modelPanel.setBorder(BorderFactory.createTitledBorder("Model"));
		
		JTextArea modelArea = new JTextArea(model.toString());
		modelArea.setEditable(false);
		modelArea.setLineWrap(true);
		modelPanel.add(modelArea, BorderLayout.CENTER);
		
		// create view
		
		JLabel message = new JLabel(" ");
		message.setForeground(Color.RED);
		message.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		final PersonEditor editor = new PersonEditor();
		editor.setModel(model);
		editor.getBinder().bindUpTo(Phase.SET);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				editor.pull();
			}
		});
		okButton.setEnabled(false);
		JButton cancelButton = new JButton("Cancel");
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		JPanel viewPanel = new JPanel(new BorderLayout(4, 4));
		viewPanel.setBorder(BorderFactory.createTitledBorder("View"));
		viewPanel.add(message, BorderLayout.PAGE_START);
		viewPanel.add(editor, BorderLayout.CENTER);
		viewPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		// create frame
		
		JPanel framePanel = new JPanel(new BorderLayout(4, 4));
		framePanel.add(viewPanel, BorderLayout.PAGE_START);
		framePanel.add(modelPanel, BorderLayout.CENTER);
		
		final JFrame frame = new JFrame("Common Binding Example");
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(framePanel);
		frame.setSize(new Dimension(600, 400));
		
		// bind view to model
		
		Binder<String> binder = Binders.newBinder();
		
		// bind first editor violation to message
		binder.bind(editor.getBinder()).using(firstString()).to(component(message).text());
		binder.bind(editor.getBinder()).using(collectionToEmpty()).to(component(okButton).enabled());

		// bind model to model area
		// TODO: need to update model area when object properties change too, e.g. homeAddress
		binder.bind(bean(model)).using(Converters.<Person>toStringConverter()).to(component(modelArea).text());
		
		binder.bind();
		
		// show view
		
		frame.setVisible(true);
	}
	
	// private methods --------------------------------------------------------
	
	private static Converter<Collection<String>, String> firstString()
	{
		return new Converter<Collection<String>, String>()
		{
			@Override
			public String convert(Collection<String> strings)
			{
				return strings.isEmpty() ? " " : strings.iterator().next();
			}
			
			@Override
			public Collection<String> unconvert(String string)
			{
				throw new UnsupportedOperationException();
			}
		};
	}
	
	private static Converter<Collection<String>, Boolean> collectionToEmpty()
	{
		return new Converter<Collection<String>, Boolean>()
		{
			@Override
			public Boolean convert(Collection<String> collection)
			{
				return collection.isEmpty();
			}
			
			@Override
			public Collection<String> unconvert(Boolean empty)
			{
				throw new UnsupportedOperationException();
			}
		};
	}
}
