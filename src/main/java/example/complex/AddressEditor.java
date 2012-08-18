/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/complex/AddressEditor.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example.complex;

import static example.complex.Converters.emptyStringToNull;
import static example.complex.Converters.violationsToColor;
import static example.complex.GridBagUtils.addRow;
import static example.complex.Validators.isNotNull;
import static uk.co.iizuka.common.binding.swing.SwingObservables.component;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import example.Address;

import uk.co.iizuka.common.binding.Binder;
import uk.co.iizuka.common.binding.Binders;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: AddressEditor.java 97901 2012-01-19 14:58:48Z mark@IIZUKA.CO.UK $
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
