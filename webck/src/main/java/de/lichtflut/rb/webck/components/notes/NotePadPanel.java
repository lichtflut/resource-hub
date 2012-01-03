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
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.query.Query;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.EditNoteDialog;
import de.lichtflut.rb.webck.models.QueryResultModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

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
	 * @param model The target for the notes.
	 */
	@SuppressWarnings("rawtypes")
	public NotePadPanel(final String id, final IModel<ResourceID> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		final IModel<List<ResourceNode>> listModel = new QueryResultModel(new QueryModel(model));
		
		final ListView<ResourceNode> view = new ListView<ResourceNode>("notesList", listModel) {
			@Override
			protected void populateItem(ListItem<ResourceNode> item) {
				item.add(new MezzlePanel("mezzle", item.getModel()));
			}
		}; 
		add(view);
		
		add(new AjaxLink("create") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				final IModel<ResourceNode> mezzleNode = new Model<ResourceNode>(new SNResource());
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreateNoteDialog(hoster.getDialogID(), mezzleNode, model));
			}
		});
	}
	
	// ----------------------------------------------------
	
	public abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	private final class CreateNoteDialog extends EditNoteDialog {

		private final IModel<ResourceID> target;

		private CreateNoteDialog(String id, IModel<ResourceNode> note, IModel<ResourceID> target) {
			super(id, note);
			this.target = target;
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
