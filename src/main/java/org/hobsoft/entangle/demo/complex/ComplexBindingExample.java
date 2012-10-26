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
	
	private JLabel messageLabel;
	
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
		
		model = new Person();
		
		bind();
	}

	// private methods --------------------------------------------------------
	
	private Component createFramePanel()
	{
		JPanel panel = new JPanel(new BorderLayout(4, 4));
		panel.add(createViewPanel(), BorderLayout.PAGE_START);
		panel.add(createModelPanel(), BorderLayout.CENTER);
		
		return panel;
	}

	private Component createModelPanel()
	{
		JPanel panel = new JPanel(new BorderLayout(4, 4));
		panel.setBorder(BorderFactory.createTitledBorder("Model"));
		
		modelArea = new JTextArea();
		modelArea.setEditable(false);
		modelArea.setLineWrap(true);
		panel.add(modelArea, BorderLayout.CENTER);
		
		return panel;
	}

	private Component createViewPanel()
	{
		messageLabel = new JLabel(" ");
		messageLabel.setForeground(Color.RED);
		messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
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
		
		JPanel panel = new JPanel(new BorderLayout(4, 4));
		panel.setBorder(BorderFactory.createTitledBorder("View"));
		panel.add(messageLabel, BorderLayout.PAGE_START);
		panel.add(editor, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.PAGE_END);
		
		return panel;
	}

	private void bind()
	{
		Binder<String> binder = Binders.newBinder();
		
		// bind violations to label
		binder.bind(editor.getBinder())
			.using(firstString())
			.to(component(messageLabel).text());
		
		// bind violations to OK button
		binder.bind(editor.getBinder())
			.using(collectionToEmpty())
			.to(component(okButton).enabled());

		// bind model to area
		// TODO: need to update model area when object properties change too, e.g. homeAddress
		binder.bind(bean(model))
			.using(Converters.<Person>toStringConverter())
			.to(component(modelArea).text());
		
		binder.bind();
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
