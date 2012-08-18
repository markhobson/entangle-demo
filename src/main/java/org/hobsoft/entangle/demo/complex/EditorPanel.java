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

import javax.swing.JPanel;

import org.hobsoft.entangle.Binder;
import org.hobsoft.entangle.Binders;
import org.hobsoft.entangle.Observables;
import org.hobsoft.entangle.Observables.BeanObservables;

/**
 * 
 * 
 * @author Mark Hobson
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
