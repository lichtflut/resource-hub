/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public class EditNoteDialog extends RBDialog {

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
                item.setModificationDate(new Date());
                item.setCreator(getCurrentUserName());
                contentService.store(item);
                EditNoteDialog.this.onSave(item);
                EditNoteDialog.this.close();
            }

            @Override
            public void onCancel() {
                EditNoteDialog.this.onCancel();
                EditNoteDialog.this.close();
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
