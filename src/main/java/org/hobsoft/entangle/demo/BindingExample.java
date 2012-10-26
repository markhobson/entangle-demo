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
	
	private JTextField name;
	
	private JTextArea modelArea;
	
	// constructors -----------------------------------------------------------
	
	public BindingExample()
	{
		setTitle("Entangle Demo");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(createFramePanel());
		pack();
		
		// create model
		
		model = new Person();
		
		// bind view to model
		
		Binder<Void> binder = Binders.newBinder();
		binder.bind(bean(model).string(Person.NAME)).to(component(name).text());
		binder.bind(bean(model)).using(Converters.<Person>toStringConverter()).to(component(modelArea).text());
		binder.bind();
	}
	
	// private methods --------------------------------------------------------
	
	private JPanel createFramePanel()
	{
		JPanel framePanel = new JPanel();
		framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.PAGE_AXIS));
		framePanel.add(createViewPanel());
		framePanel.add(createModelPanel());
		
		return framePanel;
	}
	
	private JPanel createViewPanel()
	{
		JPanel viewPanel = new JPanel();
		viewPanel.setBorder(BorderFactory.createTitledBorder("View"));
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.LINE_AXIS));
		
		viewPanel.add(new JLabel("Name"));
		name = new JTextField(20);
		viewPanel.add(name);
		
		return viewPanel;
	}
	
	private JPanel createModelPanel()
	{
		JPanel modelPanel = new JPanel();
		modelPanel.setBorder(BorderFactory.createTitledBorder("Model"));
		modelPanel.setLayout(new BoxLayout(modelPanel, BoxLayout.LINE_AXIS));
		
		modelArea = new JTextArea();
		modelArea.setEditable(false);
		modelPanel.add(modelArea);
		
		return modelPanel;
	}
	
	// main -------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		new BindingExample().setVisible(true);
	}
}
