/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.management;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

/**
 * <p>
 *  Panel for editing of a perspective specification.
 * </p>
 *
 * <p>
 * 	Created Feb 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class PerspectiveEditPanel extends TypedPanel<Perspective> {
	
	@SpringBean
	private ViewSpecificationService viewSpecificationService;
	
	private final IModel<DisplayMode> mode;

	// ----------------------------------------------------

	/**
     * Constructor.
	 * @param id The component ID.
     * @param model The model of the prespective.
     * @param mode The display mode - edit or create.
	 */
	public PerspectiveEditPanel(final String id, final IModel<Perspective> model, final IModel<DisplayMode> mode) {
		super(id, model);
		this.mode = mode;
		
		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));
		
		form.add(new TextField<String>("name", new PropertyModel<String>(model, "name"))
				.setRequired(true));
		form.add(new TextField<String>("title", new PropertyModel<String>(model, "title"))
				.setRequired(true));
		
		form.add(createSaveButton(model));
		form.add(createCancelButton(model));
		form.add(createCreateButton(model));
		
		add(form);
	}
	
	// ----------------------------------------------------
	
	protected void onCancel(AjaxRequestTarget target) { }
	
	protected void onSuccess(AjaxRequestTarget target) { }

	// -- BUTTONS -----------------------------------------
	
	protected Button createSaveButton(final IModel<Perspective> model) {
		final AjaxButton save = new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
                store(target, form, model.getObject());
			}
		};
		save.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return save;
	}
	
	protected Button createCreateButton(final IModel<Perspective> model) {
		final Button edit = new RBDefaultButton("create") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
                store(target, form, model.getObject());
			}
        };
		edit.add(visibleIf(areEqual(mode, DisplayMode.CREATE)));
		return edit;
	}
	
	protected Button createCancelButton(final IModel<Perspective> model) {
		final AjaxButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onCancel(target);
				RBAjaxTarget.add(form);
			}
		};
		return cancel;
	}

    // ----------------------------------------------------

    private void store(AjaxRequestTarget target, Form<?> form, Perspective perspective) {
        viewSpecificationService.store(perspective);
        send(getPage(), Broadcast.BREADTH, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
        RBAjaxTarget.add(form);
        onSuccess(target);
    }
	
}
