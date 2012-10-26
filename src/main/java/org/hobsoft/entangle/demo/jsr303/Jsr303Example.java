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
package org.hobsoft.entangle.demo.jsr303;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.validation.ConstraintViolation;

import org.hobsoft.entangle.Binder;
import org.hobsoft.entangle.Binders;
import org.hobsoft.entangle.demo.Converters;
import org.hobsoft.entangle.demo.Person;
import org.hobsoft.entangle.jsr303.Jsr303Validators;

import static org.hobsoft.entangle.Observables.bean;
import static org.hobsoft.entangle.jsr303.Jsr303Converters.violationsToString;
import static org.hobsoft.entangle.swing.SwingObservables.component;

/**
 * 
 * 
 * @author Mark Hobson
 */
public class Jsr303Example extends JFrame
{
	// fields -----------------------------------------------------------------
	
	private Jsr303Person model;
	
	private JTextField nameField;
	
	private JLabel messageLabel;
	
	private JTextArea modelArea;
	
	// constructors -----------------------------------------------------------
	
	public Jsr303Example()
	{
		setTitle("Entangle Demo");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(createFramePanel());
		pack();
		
		// create model
		
		model = new Jsr303Person();
		
		// bind view to model
		
		Binder<ConstraintViolation<?>> binder = Binders.newBinder();
		
		binder.bind(bean(model).string(Person.NAME))
			.checking(Jsr303Validators.property(Jsr303Person.class, Person.NAME))
			.to(component(nameField).text());
		
		binder.bind(binder)
			.using(violationsToString())
			.to(component(messageLabel).text());

		binder.bind(bean(model))
			.using(Converters.<Jsr303Person>toStringConverter())
			.to(component(modelArea).text());
		
		binder.bind();
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
		
		modelArea = new JTextArea();
		modelArea.setEditable(false);
		panel.add(modelArea);
		
		return panel;
	}

	// main -------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		// turn off hibernate-validator info
		Logger.getLogger("org.hibernate.validator").setLevel(Level.WARNING);
		
		new Jsr303Example().setVisible(true);
	}
}
