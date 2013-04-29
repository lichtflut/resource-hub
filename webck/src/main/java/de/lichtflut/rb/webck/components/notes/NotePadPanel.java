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
package de.lichtflut.rb.webck.components.notes;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.core.content.SNContentItem;
import de.lichtflut.rb.core.services.ContentService;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.EditNoteDialog;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  Panel containing the notes for an {@link ResourceNode}.
 * </p>
 *
 * <p>
 * 	Created Jan 3, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class NotePadPanel extends TypedPanel<ResourceID> {

	@SpringBean
	private ContentService contentService;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The ID
	 * @param resource The target for the notes.
	 */
	@SuppressWarnings("rawtypes")
	public NotePadPanel(final String id, final IModel<ResourceID> resource) {
		super(id, resource);

		setOutputMarkupId(true);

		final AttachmentsModel listModel = new AttachmentsModel(resource);

		final ListView<ContentItem> view = new ListView<ContentItem>("notesList", listModel) {
			@Override
			protected void populateItem(final ListItem<ContentItem> item) {
				item.add(new MezzlePanel("mezzle", item.getModel()){
					@Override
					public void edit(final IModel<ContentItem> mezzle) {
						editNote(mezzle);
					}
					@Override
					public void delete(final IModel<ContentItem> mezzle) {
						deleteNote(mezzle);
						removeAll();
						listModel.reset();
					}
				});
			}
		};
		view.setReuseItems(true);
		add(view);

		add(new AjaxLink("createFirst") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				createNote(resource);
			}
		}.add(visibleIf(isEmpty(listModel))));

		add(new AjaxLink("createAnother") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				createNote(resource);
			}
		}.add(visibleIf(not(isEmpty(listModel)))));
	}

	// ----------------------------------------------------

	protected void createNote(final IModel<ResourceID> resource) {
		final IModel<ContentItem> mezzleNode = new Model<ContentItem>(new SNContentItem());
		final DialogHoster hoster = findParent(DialogHoster.class);
		hoster.openDialog(new CreateNoteDialog(hoster.getDialogID(), mezzleNode, resource));
	}

	protected void editNote(final IModel<ContentItem> mezzle) {
		final DialogHoster hoster = findParent(DialogHoster.class);
		hoster.openDialog(new ExtendedEditNoteDialog(hoster.getDialogID(), mezzle));
	}

	protected void deleteNote(final IModel<ContentItem> mezzle) {
		contentService.remove(mezzle.getObject().getID());
		RBAjaxTarget.add(this);
	}

	// ----------------------------------------------------

	private final class CreateNoteDialog extends EditNoteDialog {

		private final IModel<ResourceID> target;

		private CreateNoteDialog(final String id, final IModel<ContentItem> note, final IModel<ResourceID> target) {
			super(id, note);
			this.target = target;
			setTitle(new ResourceModel("global.dialogs.create-note.title"));
		}

		@Override
		protected void onSave(final ContentItem note) {
			contentService.attachToResource(note, target.getObject());
			RBAjaxTarget.add(NotePadPanel.this);
		}
	}

	private class AttachmentsModel extends DerivedDetachableModel<List<ContentItem>, ResourceID> {

		public AttachmentsModel(final IModel<ResourceID> resourceID) {
			super(resourceID);
		}

		@Override
		protected List<ContentItem> derive(final ResourceID resourceID) {
			return contentService.getAttachedItems(resourceID);
		}
	}

	private final class ExtendedEditNoteDialog extends EditNoteDialog {

		private ExtendedEditNoteDialog(final String id, final IModel<ContentItem> note) {
			super(id, note);
			setTitle(new ResourceModel("global.dialogs.edit-note.title"));
		}

		@Override
		protected void onSave(final ContentItem note) {
			RBAjaxTarget.add(NotePadPanel.this);
		}
	}

}
