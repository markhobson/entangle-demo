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
package example.complex;

import static example.complex.Converters.compose;
import static example.complex.Converters.dateToString;
import static example.complex.Converters.emptyStringToNull;
import static example.complex.Converters.violationsToColor;
import static example.complex.GridBagUtils.addRow;
import static example.complex.Validators.canUnconvert;
import static example.complex.Validators.isMinLength;
import static org.hobsoft.entangle.swing.SwingObservables.component;

import java.awt.GridBagLayout;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTextField;

import example.Address;
import example.Person;

import org.hobsoft.entangle.Binder;
import org.hobsoft.entangle.Binders;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: PersonEditor.java 97901 2012-01-19 14:58:48Z mark@IIZUKA.CO.UK $
 */
public class PersonEditor extends EditorPanel<Person, String>
{
	// fields -----------------------------------------------------------------
	
	private final JTextField name;
	
	private final JTextField dateOfBirth;
	
	private final AddressEditor homeAddress;
	
	private final AddressEditor workAddress;
	
	// constructors -----------------------------------------------------------
	
	public PersonEditor()
	{
		setLayout(new GridBagLayout());

		Binder<String> binder = getBinder();
		Binder<String> messageBinder = Binders.newBinder();
		
		name = new JTextField(20);
		addRow(this, new JLabel("Name"), name);
		messageBinder.bind(
			binder.bind(model().string(Person.NAME))
				.checking(isMinLength(3, "Name is less than 3 characters long"))
//				.checking(Validators.isNotNull("Name is empty"))
				.using(emptyStringToNull())
				.to(component(name).text())
		).using(violationsToColor()).to(component(name).background());

		dateOfBirth = new JTextField(10);
		addRow(this, new JLabel("Date of birth"), dateOfBirth);
		messageBinder.bind(
			binder.bind(model().property(Person.DATE_OF_BIRTH, Date.class))
				.using(compose(dateToString("dd/MM/yyyy"), emptyStringToNull()))
				// TODO: remove need for converter check
				.checking(canUnconvert(compose(dateToString("dd/MM/yyyy"), emptyStringToNull()),
					"Date of birth is not dd/mm/yyyy"))
				.to(component(dateOfBirth).text())
		).using(violationsToColor()).to(component(dateOfBirth).background());
		
		homeAddress = new AddressEditor();
		addRow(this, new JLabel("Home address"), homeAddress);
		binder.bind(model().property(Person.HOME_ADDRESS, Address.class)).to(homeAddress.model());
		binder.add(homeAddress.getBinder());
		
		workAddress = new AddressEditor();
		addRow(this, new JLabel("Work address"), workAddress);
		binder.bind(model().property(Person.WORK_ADDRESS, Address.class)).to(workAddress.model());
		binder.add(workAddress.getBinder());
	
		messageBinder.bind();
	}
	
	// EditorPanel methods ----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Person createModel()
	{
		return new Person();
	}
}
