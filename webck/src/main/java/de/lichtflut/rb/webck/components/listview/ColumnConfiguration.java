/**
 * 
 */
package de.lichtflut.rb.webck.components.listview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Configuration of a column of a {@link EntityListPanel}.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ColumnConfiguration implements Serializable {
	
	private final List<String> actions = new ArrayList<String>();
	
	// ----------------------------------------------------
	
	/**
	 * Creates a default configuration with 'view', 'edit' and 'delete' actions.
	 * @return A new default configuration object.
	 */
	public static ColumnConfiguration defaultConfig() {
		return new ColumnConfiguration(ListAction.VIEW, ListAction.EDIT, ListAction.DELETE);
	}
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param supportedActions The supported standard actions.
	 */
	public ColumnConfiguration(final ListAction... supportedActions) {
		for (ListAction current : supportedActions) {
			this.actions.add(current.name());
		}
	}
	
	// ----------------------------------------------------
	
	public ColumnConfiguration addCustomAction(final String action) {
		this.actions.add(action);
		return this;
	}
	
	public String[] getActions() {
		return actions.toArray(new String[actions.size()]);
	}

}
