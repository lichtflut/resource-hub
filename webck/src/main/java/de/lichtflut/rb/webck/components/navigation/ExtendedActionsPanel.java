/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.navigation;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNotNull;
import static de.lichtflut.rb.webck.models.ConditionalModel.isTrue;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.query.QueryResult;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.components.dialogs.EntityExcelExportDialog;
import de.lichtflut.rb.webck.components.dialogs.ResourceListExportDialog;
import de.lichtflut.rb.webck.components.dialogs.VCardExportDialog;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 *  Panel that "opens" on click to hold extended action links.
 * </p>
 *
 * <p>
 * 	Created 06.02.2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class ExtendedActionsPanel extends Panel {
	
	@SpringBean
	private EntityManager entityManager;
	
	private ContextMenu ctxMenu;

	private IModel<RBEntity> entityModel = null;
	private IModel<QueryResult> dataModel = null;
	private IModel<ColumnConfiguration> configModel = null;
	
	// ----------------------------------------------------
	
	public ExtendedActionsPanel(final String id, final IModel<RBEntity> entityModel) {
		super(id);
		this.entityModel = entityModel;
		init();
	}
	
	public ExtendedActionsPanel(final String id, final IModel<QueryResult> dataModel,
			final IModel<ColumnConfiguration> configModel) {
		super(id);
		this.dataModel = dataModel;
		this.configModel = configModel;
		init();
	}

	// ----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		//response.renderJavaScriptReference(AutocompleteJavaScriptResourceReference.get());
	}
	
	// ----------------------------------------------------

	/**
	 * General ExtendedActionsPanel initialization.
	 */
	private void init() {
		ctxMenu = new ContextMenu("extendedActionsContainer");
		add(ctxMenu);
		
		final Label openMenuLink = new Label("showExtendedActionsLink", new ResourceModel("link.extended-actions"));
		openMenuLink.add(ctxMenu.createToggleBehavior("onclick"));
		add(openMenuLink);

		/*TODO: RB-7 - Sichtbarkeitssteuerung des Links funktioniert nicht! Der Link ist IMMER sichtbar, auch wenn sich im
		 * 		Container keine	sichtbaren Links befinden!! */		
		openMenuLink.add(visibleIf(isTrue(new HasVisibleLinksModel(new Model<MarkupContainer>(ctxMenu)))));
		
		// adding the action links
		addDeleteEntityLink();
		addExportExcelEntityLink();
		addExportVCardLink();
		
		addExportExcelListLink();
	}

	// -- LINK_CREATOR_METHODS ----------------------------
	
	private void addExportVCardLink() {
		@SuppressWarnings("rawtypes")
		final Link exportVCardLink = new AjaxFallbackLink("exportVCardLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    hoster.openDialog(new VCardExportDialog(hoster.getDialogID(), entityModel));
			    ctxMenu.close(target);
			}
		};
		exportVCardLink.add(visibleIf(areEqual(new EntityTypeModel(entityModel), Model.of(RB.PERSON))));
		ctxMenu.add(exportVCardLink);
	}
	
	private void addDeleteEntityLink() {
		@SuppressWarnings("rawtypes")
		final Link deleteEntityLink = new AjaxFallbackLink("deleteEntityLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    ConfirmationDialog confirmDialog = new ConfirmationDialog(hoster.getDialogID(),
			    		new Model<String>(getString("global.message.delete-confirmation"))) {
							@Override
							public void onConfirm() {
								entityManager.delete(entityModel.getObject().getID());
								RBWebSession.get().getHistory().back();
								send(getPage(), Broadcast.BREADTH, new ModelChangeEvent(ModelChangeEvent.ENTITY));
							}
			    };
			    hoster.openDialog(confirmDialog);
			    ctxMenu.close(target);
			}
		};
		deleteEntityLink.add(visibleIf(isNotNull(entityModel)));
		ctxMenu.add(deleteEntityLink);
	}
	
	private void addExportExcelEntityLink() {
		@SuppressWarnings("rawtypes")
		final Link exportExcelEntityLink = new AjaxFallbackLink("exportExcelEntityLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    hoster.openDialog(new EntityExcelExportDialog(hoster.getDialogID(), entityModel));
			    ctxMenu.close(target);
			}
		};
		exportExcelEntityLink.add(visibleIf(isNotNull(entityModel)));
		ctxMenu.add(exportExcelEntityLink);
	}
	
	private void addExportExcelListLink() {
		@SuppressWarnings("rawtypes")
		final Link exportExcelListLink = new AjaxFallbackLink("exportExcelListLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    hoster.openDialog(new ResourceListExportDialog(hoster.getDialogID(), dataModel, configModel));
			    ctxMenu.close(target);
			}
		};
		exportExcelListLink.add(visibleIf(and(isNotNull(dataModel), isNotNull(configModel))));
		ctxMenu.add(exportExcelListLink);
	}
	
	// ----------------------------------------------------
	
	private final class EntityTypeModel extends DerivedModel<ResourceID, RBEntity> {
		private EntityTypeModel(IModel<RBEntity> original) {
			super(original);
		}
		@Override
		protected ResourceID derive(RBEntity original) {
			return original.getType();
		}
	}
	
	private final class HasVisibleLinksModel extends AbstractLoadableDetachableModel<Boolean> {
		IModel<MarkupContainer> containerModel;
		private HasVisibleLinksModel(IModel<MarkupContainer> original) {
			this.containerModel = original;
		}
		@Override
		public Boolean load() {
			final IModel<Boolean> retValModel = new Model<Boolean>(false);
			containerModel.getObject().visitChildren(new IVisitor<Component, Void>() {
				@Override
				public void component(final Component component, final IVisit<Void> visit) {
					if (component instanceof Link) {
						if(component.isVisible()) {
							retValModel.setObject(true);
							visit.stop();
						} else {
							visit.dontGoDeeper();
						}
					} 
				}
			});
			return retValModel.getObject();
		}
	}
	
}
