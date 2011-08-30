/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.Iterator;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.web.models.ReferencedEntityModel;

/**
 * Component for searching and displaying of referenced resources.
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
public abstract class ResourcePicker extends CKComponent {

	private static final long serialVersionUID = 1L;
	private IModel<IRBEntity> entity;

	// ------------------------------------------------------------

	/**
	 * @param id The wicket ID.
	 * @param entity The exitisting entity to be displayed.
	 */
	public ResourcePicker(final String id, final IModel<IRBEntity> entity) {
		super(id);
		buildComponent();
	}

	// ------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes", "serial" })
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		initModel(entity);
		AutoCompleteTextField textfield = new AutoCompleteTextField("inputField") {
			@Override
			protected Iterator getChoices(final String input) {
				getNewServiceProvider().getRBEntityManagement().findAll(entity.getObject().getRBMetaInfo().getSchemaID());
				return null;
			}
		};
		add(textfield);
	}

	// ------------------------------------------------------------

	/**
	 * Initializes the Component's DefaultModel.
	 * @param entity - instance of {@link IRBEntity}
	 */
	@SuppressWarnings("serial")
	private void initModel(final IModel<IRBEntity> entity) {
		setDefaultModel(new ReferencedEntityModel(entity) {
			@Override
			public IRBEntity resolve(final ResourceID id) {
				return getNewServiceProvider().getRBEntityManagement().find(id);
			}
		});
	}

}
