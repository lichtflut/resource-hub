/*
 * Copyright 2013 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.defaultButtonIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.hasSchema;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  The standard button bar to be used when not in "create reference" mode.
 * </p>
 *
 * <p>
 * 	Created Dec 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class LocalButtonBar extends Panel {

	private final ConditionalModel viewMode = BrowsingContextModel.isInViewMode();

	@SpringBean
	private EntityManager entityManager;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model of the current entity.
	 */
	public LocalButtonBar(final String id, final IModel<RBEntity> model) {
		super(id);

		add(createSaveButton(model));
		add(createCancelButton(model));
		add(createEditButton(model));

		add(visibleIf(and(hasSchema(model), not(BrowsingContextModel.isInCreateReferenceMode()))));

	}

	// ------------------------------------------------------

	protected void onSave(final IModel<RBEntity> model, final AjaxRequestTarget target, final Form<?> form) {
	}

	/**
	 * Is called when entity can not be validated.
	 * @param errors A List containing errorcodes and their corresponding RBField
	 */
	protected void onError(final Map<Integer, List<RBField>> errors) {
	}

	protected void onCancel(final AjaxRequestTarget target, final Form<?> form){
	}

	// -- BUTTONS -----------------------------------------

	protected Component createSaveButton(final IModel<RBEntity> model) {
		final RBStandardButton save = new RBStandardButton("save") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				Map<Integer, List<RBField>> errors = entityManager.validate(model.getObject());
				if(errors.isEmpty()){
					LocalButtonBar.this.onSave(model,target, form);
				} else{
					LocalButtonBar.this.onError(errors);
				}
			}
		};
		save.add(visibleIf(not(viewMode)));
		save.add(defaultButtonIf(not(viewMode)));
		return save;
	}

	protected Component createCancelButton(final IModel<RBEntity> model) {
		final RBCancelButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				LocalButtonBar.this.onCancel(target, form);
			}
		};
		cancel.add(visibleIf(not(viewMode)));
		return cancel;
	}

	protected Component createEditButton(final IModel<RBEntity> model) {
		final RBStandardButton edit = new RBStandardButton("edit") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				RBWebSession.get().getHistory().edit(EntityHandle.forID(model.getObject().getID()));
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
			}
		};
		edit.add(visibleIf(not(not(viewMode))));
		return edit;
	}

}
