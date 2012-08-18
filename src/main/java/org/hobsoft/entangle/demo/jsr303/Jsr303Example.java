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
public final class Jsr303Example
{
	// constructors -----------------------------------------------------------
	
	private Jsr303Example()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static void main(String[] args)
	{
		// turn off hibernate-validator info
		Logger.getLogger("org.hibernate.validator").setLevel(Level.WARNING);
		
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
		
		Jsr303Person model = new Jsr303Person();
		
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
		
		Binder<ConstraintViolation<?>> binder = Binders.newBinder();
		
		binder.bind(bean(model).string(Person.NAME))
			.checking(Jsr303Validators.property(Jsr303Person.class, Person.NAME))
			.to(component(name).text());
		
		binder.bind(binder)
			.using(violationsToString())
			.to(component(message).text());

		binder.bind(bean(model))
			.using(Converters.<Jsr303Person>toStringConverter())
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
