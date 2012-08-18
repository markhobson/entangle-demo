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

import static org.hobsoft.entangle.demo.complex.Converters.emptyStringToNull;
import static org.hobsoft.entangle.demo.complex.Converters.violationsToColor;
import static org.hobsoft.entangle.demo.complex.GridBagUtils.addRow;
import static org.hobsoft.entangle.demo.complex.Validators.isNotNull;
import static org.hobsoft.entangle.swing.SwingObservables.component;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;


import org.hobsoft.entangle.Binder;
import org.hobsoft.entangle.Binders;
import org.hobsoft.entangle.demo.Address;

/**
 * 
 * 
 * @author Mark Hobson
 */
public class AddressEditor extends EditorPanel<Address, String>
{
	// fields -----------------------------------------------------------------
	
	private final JTextField street;
	
	private final JTextField town;
	
	// constructors -----------------------------------------------------------
	
	public AddressEditor()
	{
		setLayout(new GridBagLayout());
		
		Binder<String> binder = getBinder();
		Binder<String> messageBinder = Binders.newBinder();
		
		street = new JTextField(20);
		addRow(this, new JLabel("Street"), street);
		messageBinder.bind(
			binder.bind(model().string(Address.STREET))
				.checking(isNotNull("Street is empty"))
				.using(emptyStringToNull())
				.to(component(street).text())
		).using(violationsToColor()).to(component(street).background());
		
		town = new JTextField(20);
		addRow(this, new JLabel("Town"), town);
		binder.bind(model().string(Address.TOWN)).to(component(town).text());
		
		messageBinder.bind();
	}
	
	// EditorPanel methods ----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Address createModel()
	{
		return new Address();
	}
}
