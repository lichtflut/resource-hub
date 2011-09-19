/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;

/**
 * <p>
 *  This is the root of the {@CKComponent}-family.
 *  This class is abstract and describes how to use or interact with a {@CKComponent},
 *  how should they look and act like and and what should they do in several defined cases.
 * </p>
 *
 * Created: Jun 20, 2011
 *
 * @author Ravi Knox
 */
public abstract class CKComponent extends Panel {

	private static final long serialVersionUID = 1L;

	//Behavior-Keys
	/**
	 * <p>
	 * This behavior is called, when the css-style is set during the initialization progress.
	 * If not set, no special style assertions will be made.
	 * </p>
	 * <p>
	 * The expected return values are a {@link File} or {@link String} referencing the path of the css-file
	 * or a simple {@link String} which should contain embedded css assertions. If the value does not match the expected types
	 * or if the value is not a valid existing file e.g., no special style assertion will be made.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 * 	<li>{@link Class}, the ck-components-class</li>
	 * </ol>
	 * TODO: CKValueWrapper still needed?
	 * </p>
	 */
	public static final String STYLE_BEHAVIOR = "de.lichtflut.web.ck.behavior.style";

	private Map<String, CKBehavior> behaviorRegistry = new HashMap<String, CKBehavior>();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Component view modes.
	 * TODO: DESCRIPTION.
	 */
	public enum ViewMode{
		WRITE,
		READ_WRITE,
		READ
	}
	// ------------- Constructor ---------------------------
	/**
	 * Constructor.
	 * @param id - wicket:id
	 */
	public CKComponent(final String id){
		super(id, new CKValueWrapperModel());
	}

	// -----------------------------------------------------

	/**
	 * @return the wrapped model.
	 */
	@SuppressWarnings("rawtypes")
	protected CKValueWrapperModel getModel(){
		IModel model = this.getDefaultModel();
		if(model instanceof CKValueWrapperModel){
			return (CKValueWrapperModel) model;
		}
		return null;
	}

	// -----------------------------------------------------

	/**
	 * @return the logger.
	 */
	protected Logger getLogger(){
		return this.logger;
	}

	// -----------------------------------------------------

	/**
	 * This method removes all exsiting components,
	 * is initalizing the style, dependend on the style behavior and
	 * calls the internal initComponent - method to rebuild the components-tree.
	 */
	public final void refreshComponent(){
		buildComponent();
	}

	// -----------------------------------------------------


	/**
	 * This method removes all exsiting components,
	 * is initalizing the style, dependend on the style behavior and
	 * calls the internal initComponent - method to rebuild the components-tree.
	 */
	public final void buildComponent(){
		this.removeAll();
		try{
			initComponent(getModel());
		}catch(Exception any){
			getLogger().error("Something went wrong during initComponent", any);
		}
		initStyle();
	}


	// -----------------------------------------------------

	/**
	 * This method should be implemented with the components specific initialization-procedure.
	 * @param model - the {@link CKValueWrapperModel} which might contain the component relevant members/value
	 */
	protected abstract void initComponent(final CKValueWrapperModel model);

	// -----------------------------------------------------

	/**
	 * This method is initializing the custom style-sheets,
	 * delivered by the STYLE_BEHAVIOR-assignd behavior.
	 * TODO: This method is not yet ready.
	 */
	@SuppressWarnings("unused")
	private void initStyle(){
		CKBehavior behavior = getBehavior(STYLE_BEHAVIOR);
		if(behavior==null){
			return;
		}
		Object css_output = behavior.execute(new Object[]{this.getClass()});
		if(css_output==null){
			return;
		}
		if(css_output instanceof File){
			if(!((File) css_output).exists()){
				return;
			}
			ResourceReference css_reference = new CssResourceReference(this.getClass(), css_output.toString());
		}else if(css_output instanceof String){
			//Check if the string might be a File
			if(new File(css_output.toString()).exists()){
				return;
			}else{ //The string might be an embedded css-string
				String embedded_css = css_output.toString();
			}
		}
	}

	// -----------------------------------------------------

	/**
	 * Returns ServiceProvider.
	 * This method musst be overridden.
	 * @return An instance of {@link ServiceProvider}
	 */
	public abstract ServiceProvider getServiceProvider();

	// -----------------------------------------------------

	/**
	 * Sets the view mode for the component. This method must be overridden.
	 * If the ViewMode is not supported, an {@link UnsupportedOperationException} is raised.
	 * @param mode -
	 * @return -
	 */
	public abstract CKComponent setViewMode(final ViewMode mode);

	// -----------------------------------------------------

	/**
	 * Adds a behavior to a component.
	 * @param key - Name of the behavior
	 * @param behavior - Desired behavior
	 * @return {@link CKComponent}
	 */
	public final CKComponent addBehavior(final String key,final CKBehavior behavior){
		behaviorRegistry.put(key, behavior);
		buildComponent();
		return this;
	}

	// -----------------------------------------------------

	/**
	 * Returns the behavior for the passed Key.
	 * @param key - Name of the behavior
	 * @return {@link CKBehavior}
	 */
	public final CKBehavior getBehavior(final String key){
		return behaviorRegistry.get(key);
	}

	// -----------------------------------------------------

	/**
	 * Removes a behavior for this component.
	 * @param key - Name of the behavior which is to be removed
	 * @return {@link CKBehavior}
	 */
	public final CKBehavior removeBehavior(final String key){
		return behaviorRegistry.remove(key);
	}

	// -----------------------------------------------------

	/**
	 * Clears all behaviors for this component.
	 */
	public final void resetBehaviors(){
		behaviorRegistry.clear();
	}

	// -----------------------------------------------------

	/**
	 *
	 */
	 public static final class CKValueWrapperModel implements IModel<Map<String, Serializable>>{

		private static final long serialVersionUID = 7096039978739520112L;
		private Map<String,Serializable> obj = new HashMap<String, Serializable>();

		@Override
		public void detach() {
			this.obj=null;
		}

		// -----------------------------------------------------

		/**
		 *
		 * @param key -
		 * @param value -
		 */
		public void addValue(final String key,final  Serializable value){
			this.obj.put(key, value);
		}

		// -----------------------------------------------------

		/**
		 *
		 * @param key -
		 * @return the {@link Object} as associtated values
		 */
		public Serializable getValue(final String key){
			return this.obj.get(key);
		}

		// -----------------------------------------------------

		@Override
		public Map<String, Serializable> getObject() {
			return obj;
		}

		// -----------------------------------------------------

		@Override
		public void setObject(final Map<String,Serializable> map) {
			this.obj = map;
		}



	}
}
