/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.notes;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.query.Query;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.services.ServiceProvider;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.*;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.EditNoteDialog;
import static de.lichtflut.rb.webck.models.ConditionalModel.*;
import de.lichtflut.rb.webck.models.QueryResultModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.basic.LoadableModel;

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
public abstract class NotePadPanel extends TypedPanel<ResourceID> {

	/**
	 * Constructor.
	 * @param id The ID
	 * @param resource The target for the notes.
	 */
	@SuppressWarnings("rawtypes")
	public NotePadPanel(final String id, final IModel<ResourceID> resource) {
		super(id, resource);
		
		setOutputMarkupId(true);
		
		final LoadableModel<List<ResourceNode>> listModel = new QueryResultModel(new QueryModel(resource));
		
		final ListView<ResourceNode> view = new ListView<ResourceNode>("notesList", listModel) {
			@Override
			protected void populateItem(ListItem<ResourceNode> item) {
				item.add(new MezzlePanel("mezzle", item.getModel()){
					@Override
					public void edit(IModel<ResourceNode> mezzle) {
						editNote(mezzle);
					}
					@Override
					public void delete(IModel<ResourceNode> mezzle) {
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
			public void onClick(AjaxRequestTarget target) {
				createNote(resource);
			}
		}.add(visibleIf(isEmpty(listModel))));
		
		add(new AjaxLink("createAnother") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				createNote(resource);
			}
		}.add(visibleIf(not(isEmpty(listModel)))));
	}
	
	// ----------------------------------------------------

	protected void createNote(final IModel<ResourceID> resource) {
		final IModel<ResourceNode> mezzleNode = new Model<ResourceNode>(new SNResource());
		final DialogHoster hoster = findParent(DialogHoster.class);
		hoster.openDialog(new CreateNoteDialog(hoster.getDialogID(), mezzleNode, resource));
	}
	
	protected void editNote(IModel<ResourceNode> mezzle) {
		final DialogHoster hoster = findParent(DialogHoster.class);
		hoster.openDialog(new ExtendedEditNoteDialog(hoster.getDialogID(), mezzle));
	}
	
	protected void deleteNote(IModel<ResourceNode> mezzle) {
		ModelingConversation mc = getServiceProvider().getArastejuGate().startConversation();
		mc.remove(mezzle.getObject(), false);
		mc.close();
		RBAjaxTarget.add(this);
	}
	
	// ----------------------------------------------------
	
	public abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	private final class CreateNoteDialog extends EditNoteDialog {

		private final IModel<ResourceID> target;

		private CreateNoteDialog(String id, IModel<ResourceNode> note, IModel<ResourceID> target) {
			super(id, note);
			this.target = target;
			setTitle(new ResourceModel("global.dialogs.create-note.title"));
		}

		@Override
		public ServiceProvider getServiceProvider() {
			return NotePadPanel.this.getServiceProvider();
		}
		
		/** 
		* {@inheritDoc}
		*/
		@Override
		protected void onSave(ResourceNode note) {
			SNOPS.assure(note, RB.IS_ATTACHED_TO, target.getObject());
			ModelingConversation mc = getServiceProvider().getArastejuGate().startConversation();
			mc.attach(note);
			mc.close();
			RBAjaxTarget.add(NotePadPanel.this);
		}
	}
	
	private final class ExtendedEditNoteDialog extends EditNoteDialog {

		private ExtendedEditNoteDialog(String id, IModel<ResourceNode> note) {
			super(id, note);
			setTitle(new ResourceModel("global.dialogs.edit-note.title"));
		}

		@Override
		public ServiceProvider getServiceProvider() {
			return NotePadPanel.this.getServiceProvider();
		}
		
		/** 
		* {@inheritDoc}
		*/
		@Override
		protected void onSave(ResourceNode note) {
			RBAjaxTarget.add(NotePadPanel.this);
		}
	}

	private class QueryModel extends DerivedModel<Query, ResourceID> {

		/**
		 * @param original
		 */
		public QueryModel(IModel<ResourceID> original) {
			super(original);
		}

		/** 
		* {@inheritDoc}
		*/
		@Override
		protected Query derive(ResourceID original) {
			final Query query = getServiceProvider().getArastejuGate().createQueryManager().buildQuery();
			query.addField(RB.IS_ATTACHED_TO, original.getQualifiedName().toURI());
			return query;
		}
		
	}
	
}
