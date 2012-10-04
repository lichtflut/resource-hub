/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.core.services.ContentService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.components.rteditor.RichTextBehavior;
import de.lichtflut.rb.webck.components.rteditor.RichTextEditor;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;

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
    private ContentService contentService;

	// ----------------------------------------------------

	/**
	 * Constrcutor.
	 * @param id The component ID.
	 * @param model The model containing the node.
	 */
	@SuppressWarnings("rawtypes")
	public EditNoteDialog(final String id, final IModel<ContentItem> model) {
		super(id);

		final Form form = new Form("form");
        form.add(new RichTextEditor("editor", model, RichTextBehavior.Type.SIMPLE) {
            @Override
            public void onSave() {
                final ContentItem item = model.getObject();
                if (item.getCreateDate() == null) {
                    item.setCreateDate(new Date());
                }
                item.setUpdateDate(new Date());
                item.setCreator(getCurrentUserName());
                contentService.store(item);
                EditNoteDialog.this.onSave(item);
                EditNoteDialog.this.closeDialog();
            }

            @Override
            public void onCancel() {
                EditNoteDialog.this.onCancel();
                EditNoteDialog.this.closeDialog();
            }

            @Override
            protected Component createTitleField(String componentID, IModel<String> titleModel) {
                return new WebComponent(componentID).setVisible(false);
            }
        });

		add(form);

		setModal(true);
		setWidth(600);
	}

	// ----------------------------------------------------


	protected void onSave(final ContentItem item) {
	}

	protected void onCancel() {
	}

    // ----------------------------------------------------

    private String getCurrentUserName() {
        if (context.isAuthenticated()) {
            return context.getUser().getName();
        } else {
            return null;
        }
    }

}
