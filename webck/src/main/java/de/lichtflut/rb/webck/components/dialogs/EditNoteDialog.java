/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import java.util.Date;

import de.lichtflut.rb.webck.components.rteditor.RichTextBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.DC;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.models.resources.ResourceTextPropertyModel;

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
public class EditNoteDialog extends AbstractRBDialog {

	@SpringBean
	private ServiceContext context;

	@SpringBean
	private SemanticNetworkService semanticNetwork;

	// ----------------------------------------------------

	/**
	 * Constrcutor.
	 * @param id The component ID.
	 * @param model The model containing the node.
	 */
	@SuppressWarnings("rawtypes")
	public EditNoteDialog(final String id, final IModel<ResourceNode> model) {
		super(id);

		final Form form = new Form("form");
		form.add(createRichTextEditor(model));
		form.add(new RBDefaultButton("save") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				final ResourceNode noteResource = model.getObject();
				SNOPS.assure(noteResource, DC.CREATED, new SNTimeSpec(new Date(), TimeMask.TIMESTAMP));
				SNOPS.assure(noteResource, DC.CREATOR, context.getUser().getName());
				semanticNetwork.attach(noteResource);
				onSave(noteResource);
				close(target);
			}
		});
		form.add(new RBCancelButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				onCancel();
				close(target);
			}
		});

		add(form);

		setModal(true);
		setWidth(600);
	}

	/**
	 * @param model The model containing the rich text.
	 * @return a {@link TextArea} with a {@link RichTextBehavior}
	 */
	private FormComponent<SNText> createRichTextEditor(final IModel<ResourceNode> model) {
		TextArea<SNText> textArea = new TextArea<SNText>("content", new ResourceTextPropertyModel(model, RBSystem.HAS_CONTENT));
        textArea.setOutputMarkupId(true);
        textArea.setOutputMarkupPlaceholderTag(true);
		textArea.setType(SNText.class);
		textArea.add(new RichTextBehavior(RichTextBehavior.Type.SIMPLE));
		return textArea;
	}

	// ----------------------------------------------------


	protected void onSave(final ResourceNode resourceNode) {
	}

	protected void onCancel() {
	}

}
