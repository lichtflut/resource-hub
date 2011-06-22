/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import de.lichtflut.rb.core.spi.RBServiceProvider;
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
	 * </p>
	 */
	public static final String STYLE_BEHAVIOR = "de.lichtflut.web.ck.behavior.style";

	private Map<String, CKBehavior> behaviorRegistry = new HashMap<String, CKBehavior>();

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
	 * @param id -
	 */
	public CKComponent(final String id){
		super(id);
	}

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
	 * @return An instance of {@link RBServiceProvider}
	 */
	public abstract RBServiceProvider getServiceProvider();

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
}
