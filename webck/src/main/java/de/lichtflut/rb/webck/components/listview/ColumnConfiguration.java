/**
 * 
 */
package de.lichtflut.rb.webck.components.listview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.listview.ColumnHeader.ColumnType;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Configuration of a column of a entity or resource lists.
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
	
	private final List<ResourceID> predicates = new ArrayList<ResourceID>();
	
	private final Map<ResourceID, PropertyDeclaration> declMap = new HashMap<ResourceID, PropertyDeclaration>();
	
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
	
	public String[] getActions() {
		return actions.toArray(new String[actions.size()]);
	}
	
	/**
	 * @return the predicates
	 */
	public List<ResourceID> getPredicatesToDisplay() {
		return predicates;
	}
	
	// ----------------------------------------------------
	
	public IModel<List<ColumnHeader>> getHeaderModel() {
		return new AbstractLoadableDetachableModel<List<ColumnHeader>>() {
			@Override
			public List<ColumnHeader> load() {
				return getHeaders();
			}
		};
	}
	
	public List<ColumnHeader> getHeaders() {
		final Locale locale = RequestCycle.get().getRequest().getLocale();
		final List<ColumnHeader> headers = new ArrayList<ColumnHeader>();
		for (ResourceID predicate : predicates) {
			final String label = getLabel(predicate, locale);
			headers.add(new SimpleColumnHeader(label, predicate, ColumnType.DATA));
		}
		for (@SuppressWarnings("unused") String action : getActions()) {
			headers.add(new SimpleColumnHeader("", null, ColumnType.ACTION));
		}
		return headers;
	}
	
	public String getLabel(ResourceID predicate, Locale locale) {
		if (declMap.containsKey(predicate)) {
			return declMap.get(predicate).getFieldLabelDefinition().getLabel(locale);
		} else {
			return ResourceLabelBuilder.getInstance().getFieldLabel(predicate, locale);
		}
	}

	// ----------------------------------------------------
	
	/**
	 * Add a custom actin.
	 * @param action The action name.
	 * @return This.
	 */
	public ColumnConfiguration addCustomAction(final String action) {
		this.actions.add(action);
		return this;
	}
	
	/**
	 * Add all PropertyDeclarationsn from schema to column config.
	 * @param schema The Schema.
	 * @return This.
	 */
	public ColumnConfiguration addColumnsFromSchema(final ResourceSchema schema) {
		if (schema != null) {
			for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
				predicates.add(decl.getPropertyDescriptor());
				declMap.put(decl.getPropertyDescriptor(), decl);
			}
		}
		return this;
	}
	
	/**
	 * Just fetch the label definition from the schema, but don't add all
	 * PropertyDeclarations.
	 * @param schema The Schema.
	 * @return This.
	 */
	public ColumnConfiguration fetchFieldLabels(final ResourceSchema schema) {
		if (schema != null) {
			for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
				declMap.put(decl.getPropertyDescriptor(), decl);
			}
		}
		return this;
	}
	
	/**
	 * Add a column for a predicate.
	 * @param predicate The predicate.
	 * @return This.
	 */
	public ColumnConfiguration addColumnByPredicate(final ResourceID predicate) {
		predicates.add(predicate);
		return this;
	}
	
}
