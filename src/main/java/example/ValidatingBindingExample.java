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
package example;

import static org.hobsoft.entangle.Observables.bean;
import static org.hobsoft.entangle.swing.SwingObservables.component;

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

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: ValidatingBindingExample.java 97901 2012-01-19 14:58:48Z mark@IIZUKA.CO.UK $
 */
public final class ValidatingBindingExample
{
	// constructors -----------------------------------------------------------
	
	private ValidatingBindingExample()
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
		
		Person model = new Person();
		
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
		
		Binder<String> binder = Binders.newBinder();
		
		Validator<String, String> validator = new Validator<String, String>()
		{
			public Collection<String> validate(String string)
			{
				return (string != null && string.length() < 3)
					? Collections.singleton("* Name must be 3 characters or more") : null;
			}
		};
		
		binder.bind(bean(model).string(Person.NAME)).checking(validator).to(component(name).text());
		
		Converter<Collection<String>, String> converter = new Converter<Collection<String>, String>()
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
		
		binder.bind(binder).using(converter).to(component(message).text());

		binder.bind(bean(model)).using(Converters.<Person>toStringConverter()).to(component(modelArea).text());
		
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
