/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.crypt.Base64;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

import de.lichtflut.rb.webck.application.ResourceQueryServlet;

/**
 * <p>
 * 	Picker field for rdf:Properties.
 * </p>
 *
 * <p>
 * 	Created Dec 14, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ClassPickerField extends DataPickerField<ResourceID> {
	
	public static final String BASE_URI = "/internal/query" + ResourceQueryServlet.QUERY_SUB_CLASS;
	
	private final IModel<ResourceID> superClass;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public ClassPickerField(final String id, final IModel<ResourceID> model) {
		this(id, model, new Model<ResourceID>());
	}
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public ClassPickerField(final String id, final IModel<ResourceID> model, final IModel<ResourceID> superClass) {
		super(id, model, findSubClasses(null));
		this.superClass = superClass;
		setType(ResourceID.class);
	}
	
	// -----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void onConfigure() {
		super.onConfigure();
		setSource(findSubClasses(superClass.getObject()));
	}
	
	// ----------------------------------------------------
	
	public static AutocompleteSource findSubClasses(ResourceID superClass) {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		final StringBuilder sb = new StringBuilder(ctx + BASE_URI);
		if (superClass != null) {
			sb.append("?type=");
			sb.append(Base64.encodeBase64URLSafeString(superClass.getQualifiedName().toURI().getBytes()));
		}
		return new AutocompleteSource(sb.toString());
	}
	
}
