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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hobsoft.entangle.Binder;
import org.hobsoft.entangle.Binders;
import org.hobsoft.entangle.Converter;
import org.hobsoft.entangle.Validator;

import static org.hobsoft.entangle.Observables.bean;
import static org.hobsoft.entangle.swing.SwingObservables.component;

/**
 * 
 * 
 * @author Mark Hobson
 */
public class ValidatingBindingExample extends JFrame
{
	// fields -----------------------------------------------------------------
	
	private Person model;
	
	private JTextField nameField;
	
	private JLabel messageLabel;
	
	private JTextArea modelArea;
	
	// constructors -----------------------------------------------------------
	
	public ValidatingBindingExample()
	{
		setTitle("Entangle Demo");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(createFramePanel());
		pack();
		
		model = new Person();
		
		bind();
	}

	// private methods --------------------------------------------------------
	
	private JPanel createFramePanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(createViewPanel());
		panel.add(createModelPanel());
		
		return panel;
	}
	
	private JPanel createViewPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("View"));
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		
		panel.add(new JLabel("Name"), constraints);
		
		nameField = new JTextField(20);
		panel.add(nameField, constraints);
		
		messageLabel = createLabel(300);
		messageLabel.setForeground(Color.RED);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(messageLabel, constraints);
		
		return panel;
	}

	private JPanel createModelPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Model"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		modelArea = new JTextArea(5, 40);
		modelArea.setEditable(false);
		modelArea.setLineWrap(true);
		panel.add(modelArea);
		
		return panel;
	}

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
	
	private void bind()
	{
		Binder<String> binder = Binders.newBinder();
		
		// bind model to view
		binder.bind(bean(model).string(Person.NAME))
			.checking(minLength())
			.to(component(nameField).text());
		
		// bind violations to label
		binder.bind(binder)
			.using(violationsToString())
			.to(component(messageLabel).text());

		// bind model to area
		binder.bind(bean(model))
			.using(Converters.<Person>toStringConverter())
			.to(component(modelArea).text());
		
		binder.bind();
	}

	private static Validator<String, String> minLength()
	{
		return new Validator<String, String>()
		{
			public Collection<String> validate(String string)
			{
				return (string != null && string.length() < 3)
					? Collections.singleton("* Name must be 3 characters or more") : null;
			}
		};
	}
	
	private static Converter<Collection<String>, String> violationsToString()
	{
		return new Converter<Collection<String>, String>()
		{
			public String convert(Collection<String> strings)
			{
				StringBuilder builder = new StringBuilder();
				
				for (String string : strings)
				{
					if (builder.length() > 0)
					{
						builder.append("; ");
					}
					
					builder.append(string);
				}
				
				return builder.toString();
			}
			
			public Collection<String> unconvert(String string)
			{
				throw new UnsupportedOperationException();
			}
		};
	}
	
	// main -------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		new ValidatingBindingExample().setVisible(true);
	}
}
