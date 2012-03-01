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
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.query.QueryResult;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
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
	private ServiceProvider provider;
	
	private IModel<Boolean> isOpen = new Model<Boolean>(false);
	private MarkupContainer linkContainer;

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
	 * General ExtendedActionsPanel initialization.
	 */
	private void init() {
		linkContainer = new WebMarkupContainer("extendedActionsContainer");
		linkContainer.add(visibleIf(isTrue(isOpen)));
		linkContainer.setOutputMarkupId(true);
		add(linkContainer);
		
		@SuppressWarnings("rawtypes")
		Link openMenuLink = new AjaxFallbackLink("showExtendedActionsLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				toggleContainerVisibility(linkContainer);
			}
		};
		add(openMenuLink);

		setOutputMarkupId(true);
		
		/*TODO: RB-7 - Sichtbarkeitssteuerung des Links funktioniert nicht! Der Link ist IMMER sichtbar, auch wenn sich im
		 * 		Container keine	sichtbaren Links befinden!! */		
		openMenuLink.add(visibleIf(isTrue(new HasVisibleLinksModel(new Model<MarkupContainer>(linkContainer)))));
		
		// adding the action links
		addDeleteEntityLink();
		addExportVCardLink();
		
		addExportExcelListLink();
	}

	private void toggleContainerVisibility(final MarkupContainer linkContainer) {
		isOpen.setObject(!isOpen.getObject());
		RBAjaxTarget.add(this);
	}

	
	// -- LINK_CREATOR_METHODS ----------------------------
	
	private void addExportVCardLink() {
		@SuppressWarnings("rawtypes")
		final Link exportVCardLink = new AjaxFallbackLink("exportVCardLink") {
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    hoster.openDialog(new VCardExportDialog(hoster.getDialogID(), entityModel));
			    toggleContainerVisibility(linkContainer);
			}
		};
		exportVCardLink.add(visibleIf(areEqual(new EntityTypeModel(entityModel), Model.of(RB.PERSON))));
		linkContainer.add(exportVCardLink);
	}
	
	private void addDeleteEntityLink() {
		@SuppressWarnings("rawtypes")
		final Link deleteEntityLink = new AjaxFallbackLink("deleteEntityLink") {
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    ConfirmationDialog confirmDialog = new ConfirmationDialog(hoster.getDialogID(),
			    		new Model<String>(getString("global.message.delete-confirmation"))) {
							@Override
							public void onConfirm() {
								provider.getEntityManager().delete(entityModel.getObject().getID());
								RBWebSession.get().getHistory().back();
								send(getPage(), Broadcast.BREADTH, new ModelChangeEvent(ModelChangeEvent.ENTITY));
							}
			    };
			    hoster.openDialog(confirmDialog);
			    toggleContainerVisibility(linkContainer);
			}
		};
		deleteEntityLink.add(visibleIf(isNotNull(entityModel)));
		linkContainer.add(deleteEntityLink);
	}
	
	private void addExportExcelListLink() {
		@SuppressWarnings("rawtypes")
		final Link exportExcelListLink = new AjaxFallbackLink("exportExcelListLink") {
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);

			    hoster.openDialog(new ResourceListExportDialog(hoster.getDialogID(), dataModel, configModel));
			    
			    toggleContainerVisibility(linkContainer);
			}
		};
		exportExcelListLink.add(visibleIf(and(isNotNull(dataModel), isNotNull(configModel))));
		linkContainer.add(exportExcelListLink);
		
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
