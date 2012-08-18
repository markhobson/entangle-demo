/*
 * $HeadURL: https://svn.iizuka.co.uk/people/mark/common-binding-example/tags/1.0.0-beta-1/src/main/java/example/complex/EditorPanel.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package example.complex;

import javax.swing.JPanel;

import uk.co.iizuka.common.binding.Binder;
import uk.co.iizuka.common.binding.Binders;
import uk.co.iizuka.common.binding.Observables;
import uk.co.iizuka.common.binding.Observables.BeanObservables;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: EditorPanel.java 97901 2012-01-19 14:58:48Z mark@IIZUKA.CO.UK $
 * @param <T>
 *            the model type
 * @param <V> 
 *            the type of violation that this editor can produce
 */
public abstract class EditorPanel<T, V> extends JPanel
{
	// constants --------------------------------------------------------------
	
	public static final String MODEL = "model";
	
	// fields -----------------------------------------------------------------
	
	private final Binder<V> binder;
	
	private T model;
	
	// constructors -----------------------------------------------------------
	
	public EditorPanel()
	{
		binder = Binders.<V>newBinder();
	}
	
	// public methods ---------------------------------------------------------
	
	public T getModel()
	{
		return model;
	}
	
	public void setModel(T model)
	{
		if (model == null)
		{
			model = createModel();
		}
		
		T oldModel = this.model;
		this.model = model;
		firePropertyChange(MODEL, oldModel, this.model);
	}
	
	public BeanObservables<T> model()
	{
		return (BeanObservables<T>) Observables.bean(this).property(MODEL);
	}
	
	public Binder<V> getBinder()
	{
		return binder;
	}
	
	public void push()
	{
		binder.push();
	}
	
	public void pull()
	{
		binder.pull();
	}
	
	// protected methods ------------------------------------------------------
	
	protected abstract T createModel();
}
