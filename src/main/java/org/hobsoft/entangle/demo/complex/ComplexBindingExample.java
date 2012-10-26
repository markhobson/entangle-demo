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
package org.hobsoft.entangle.demo.complex;

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

import org.hobsoft.entangle.Binder;
import org.hobsoft.entangle.Binders;
import org.hobsoft.entangle.Binding.Phase;
import org.hobsoft.entangle.Converter;
import org.hobsoft.entangle.demo.Converters;
import org.hobsoft.entangle.demo.Person;

import static org.hobsoft.entangle.Observables.bean;
import static org.hobsoft.entangle.swing.SwingObservables.component;

/**
 * 
 * 
 * @author Mark Hobson
 */
public class ComplexBindingExample extends JFrame
{
	// fields -----------------------------------------------------------------
	
	private Person model;
	
	private JLabel message;
	
	private PersonEditor editor;
	
	private JButton okButton;
	
	private JTextArea modelArea;
	
	// constructors -----------------------------------------------------------
	
	public ComplexBindingExample()
	{
		setTitle("Entangle Demo");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(createFramePanel());
		setSize(new Dimension(600, 400));
		
		// create model
		
		model = new Person();
		
		// bind view to model
		
		Binder<String> binder = Binders.newBinder();
		
		// bind first editor violation to message
		binder.bind(editor.getBinder()).using(firstString()).to(component(message).text());
		binder.bind(editor.getBinder()).using(collectionToEmpty()).to(component(okButton).enabled());

		// bind model to model area
		// TODO: need to update model area when object properties change too, e.g. homeAddress
		binder.bind(bean(model)).using(Converters.<Person>toStringConverter()).to(component(modelArea).text());
		
		binder.bind();
	}
	
	// private methods --------------------------------------------------------
	
	private Component createFramePanel()
	{
		JPanel framePanel = new JPanel(new BorderLayout(4, 4));
		framePanel.add(createViewPanel(), BorderLayout.PAGE_START);
		framePanel.add(createModelPanel(), BorderLayout.CENTER);
		
		return framePanel;
	}

	private Component createModelPanel()
	{
		JPanel modelPanel = new JPanel(new BorderLayout(4, 4));
		modelPanel.setBorder(BorderFactory.createTitledBorder("Model"));
		
		modelArea = new JTextArea();
		modelArea.setEditable(false);
		modelArea.setLineWrap(true);
		modelPanel.add(modelArea, BorderLayout.CENTER);
		
		return modelPanel;
	}

	private Component createViewPanel()
	{
		message = new JLabel(" ");
		message.setForeground(Color.RED);
		message.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		editor = new PersonEditor();
		editor.setModel(model);
		editor.getBinder().bindUpTo(Phase.SET);
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
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
		
		return viewPanel;
	}

	private static Converter<Collection<String>, String> firstString()
	{
		return new Converter<Collection<String>, String>()
		{
			public String convert(Collection<String> strings)
			{
				return strings.isEmpty() ? " " : strings.iterator().next();
			}
			
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
			public Boolean convert(Collection<String> collection)
			{
				return collection.isEmpty();
			}
			
			public Collection<String> unconvert(Boolean empty)
			{
				throw new UnsupportedOperationException();
			}
		};
	}
	
	// main -------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		new ComplexBindingExample().setVisible(true);
	}
}
