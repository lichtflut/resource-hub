/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.models.resources.ResourcePropertyModel;

/**
 * <p>
 *  Dialog for editing of a note.
 * </p>
 *
 * <p>
 * 	Created Jan 3, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class EditNoteDialog extends AbstractRBDialog {

	/**
	 * Constrcutor.
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public EditNoteDialog(final String id, final IModel<ResourceNode> model) {
		super(id);
		
		final Form form = new Form("form");
		form.add(new TextArea<SNText>("content", new ResourcePropertyModel(model, RB.HAS_CONTENT)).setType(SNText.class));
		form.add(new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				final ModelingConversation mc = getServiceProvider().getArastejuGate().startConversation();
				mc.attach(model.getObject());
				onSave(model.getObject());
				mc.close();
				close(target);
			}
		});
		form.add(new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onCancel();
				close(target);
			}
		});
		
		add(form);
	}

	// ----------------------------------------------------
	
	public abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	protected void onSave(ResourceNode resourceNode) {
	}

	protected void onCancel() {
	}

}
