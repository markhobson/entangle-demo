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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hobsoft.entangle.Binder;
import org.hobsoft.entangle.Binders;

import static org.hobsoft.entangle.Observables.bean;
import static org.hobsoft.entangle.swing.SwingObservables.component;

/**
 * 
 * 
 * @author Mark Hobson
 */
public class BindingExample extends JFrame
{
	// fields -----------------------------------------------------------------
	
	private Person model;
	
	private JTextField nameField;
	
	private JTextArea modelArea;
	
	// constructors -----------------------------------------------------------
	
	public BindingExample()
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		panel.add(new JLabel("Name"));
		nameField = new JTextField(20);
		panel.add(nameField);
		
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
	
	private void bind()
	{
		Binder<Void> binder = Binders.newBinder();
		binder.bind(bean(model).string(Person.NAME)).to(component(nameField).text());
		binder.bind(bean(model)).using(Converters.<Person>toStringConverter()).to(component(modelArea).text());
		binder.bind();
	}

	// main -------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		new BindingExample().setVisible(true);
	}
}
