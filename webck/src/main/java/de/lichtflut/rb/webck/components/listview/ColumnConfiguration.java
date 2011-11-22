/**
 * 
 */
package de.lichtflut.rb.webck.components.listview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.FieldLabelBuilder;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.listview.ColumnHeader.ColumnType;

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
	
	private final List<PropertyDeclaration> decls = new ArrayList<PropertyDeclaration>();
	
	private final List<ResourceID> predicates = new ArrayList<ResourceID>();
	
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
	
	public IModel<List<ColumnHeader>> getHeaderModel() {
		final Locale locale = RequestCycle.get().getRequest().getLocale();
		final List<ColumnHeader> headers = new ArrayList<ColumnHeader>();
		for (PropertyDeclaration decl : decls) {
			final String label = decl.getFieldLabelDefinition().getLabel(locale);
			headers.add(new SimpleColumnHeader(label, decl.getPropertyDescriptor(), ColumnType.DATA));
		}
		for (ResourceID predicate : predicates) {
			final String label = FieldLabelBuilder.getInstance().getLabel(predicate, locale);
			headers.add(new SimpleColumnHeader(label, predicate, ColumnType.DATA));
		}
		for (@SuppressWarnings("unused") String action : getActions()) {
			headers.add(new SimpleColumnHeader("", null, ColumnType.ACTION));
		}
		return new ListModel<ColumnHeader>(headers);
	}
	
	// ----------------------------------------------------
	
	public ColumnConfiguration addCustomAction(final String action) {
		this.actions.add(action);
		return this;
	}
	
	public ColumnConfiguration addColumnsFromSchema(final ResourceSchema schema) {
		decls.addAll(schema.getPropertyDeclarations());
		return this;
	}

}
