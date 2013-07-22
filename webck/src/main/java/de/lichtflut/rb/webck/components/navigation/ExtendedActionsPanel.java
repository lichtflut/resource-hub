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
package de.lichtflut.rb.webck.components.navigation;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.common.EntityType;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.components.dialogs.EntityExcelExportDialog;
import de.lichtflut.rb.webck.components.dialogs.ResourceListExportDialog;
import de.lichtflut.rb.webck.components.dialogs.VCardExportDialog;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.config.DefaultPathBuilder;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.event.Broadcast;
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
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.QueryResult;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNotNull;
import static de.lichtflut.rb.webck.models.ConditionalModel.isTrue;

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

    @SpringBean
    private ServiceContext serviceContext;

    // ----------------------------------------------------

	public ExtendedActionsPanel(String id, IModel<ResourceNode> entityModel) {
		super(id);
		init(entityModel, null, null);
	}
	
	public ExtendedActionsPanel(final String id, final IModel<QueryResult> dataModel,
			final IModel<ColumnConfiguration> configModel) {
		super(id);
		init(null, dataModel, configModel);
	}

	// ----------------------------------------------------

	/**
	 * General ExtendedActionsPanel initialization.
	 */
	private void init(IModel<ResourceNode> entityModel, IModel<QueryResult> dataModel, IModel<ColumnConfiguration> configModel) {
		ContextMenu ctxMenu = new ContextMenu("extendedActionsContainer");
		add(ctxMenu);
		
		final Label openMenuLink = new Label("showExtendedActionsLink", new ResourceModel("link.extended-actions"));
		openMenuLink.add(ctxMenu.createToggleBehavior("onclick"));
		add(openMenuLink);

		openMenuLink.add(visibleIf(isTrue(new HasVisibleLinksModel(new Model<MarkupContainer>(ctxMenu)))));
		
		// adding the action links
        ctxMenu.add(createDeleteEntityLink(entityModel));
        ctxMenu.add(createExportEntityLink(entityModel));
        ctxMenu.add(createExportVCardLink(entityModel));
        ctxMenu.add(createExportExcelEntityLink(entityModel));
        ctxMenu.add(createExportExcelListLink(dataModel, configModel));
	}

    // -- LINK_CREATOR_METHODS ----------------------------
	
	private Component createExportVCardLink(final IModel<ResourceNode> model) {
		@SuppressWarnings("rawtypes")
		final Link link = new AjaxFallbackLink("exportVCardLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    hoster.openDialog(new VCardExportDialog(hoster.getDialogID(), model));
                closeContextMenu();
			}
		};
		link.add(visibleIf(areEqual(new EntityTypeModel(model), Model.of(RB.PERSON))));
		return link;
	}
	
	private Component createDeleteEntityLink(final IModel<ResourceNode> model) {
		@SuppressWarnings("rawtypes")
		final Link deleteEntityLink = new AjaxFallbackLink("deleteEntityLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    ConfirmationDialog confirmDialog = new ConfirmationDialog(hoster.getDialogID(),
			    		new Model<String>(getString("global.message.delete-confirmation"))) {
							@Override
							public void onConfirm() {
								entityManager.delete(model.getObject());
								RBWebSession.get().getHistory().back();
								send(getPage(), Broadcast.BREADTH, new ModelChangeEvent(ModelChangeEvent.ENTITY));
							}
			    };
			    hoster.openDialog(confirmDialog);
                closeContextMenu();
			}
		};
		deleteEntityLink.add(visibleIf(isNotNull(model)));
		return deleteEntityLink;
	}
	
	private Component createExportExcelEntityLink(final IModel<ResourceNode> model) {
		@SuppressWarnings("rawtypes")
		final Link exportExcelEntityLink = new AjaxFallbackLink("exportExcelEntityLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    hoster.openDialog(new EntityExcelExportDialog(hoster.getDialogID(), model));
                closeContextMenu();
			}
		};
		exportExcelEntityLink.add(visibleIf(isNotNull(model)));
		return exportExcelEntityLink;
	}
	
	private Component createExportExcelListLink(final IModel<QueryResult> dataModel, final IModel<ColumnConfiguration> configModel) {
		@SuppressWarnings("rawtypes")
		final Link link = new AjaxFallbackLink("exportExcelListLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			    DialogHoster hoster = findParent(DialogHoster.class);
			    hoster.openDialog(new ResourceListExportDialog(hoster.getDialogID(), dataModel, configModel));
                closeContextMenu();
			}
		};
		link.add(visibleIf(and(isNotNull(dataModel), isNotNull(configModel))));
		return link;
	}

    private Component createExportEntityLink(IModel<ResourceNode> model) {
        CrossLink link = new CrossLink("exportEntityLink", new DerivedDetachableModel<String, ResourceNode>(model) {
            @Override
            protected String derive(ResourceNode node) {
                return new DefaultPathBuilder().createDownloadPath(serviceContext.getDomain())
                        .entity().withQN(node.getQualifiedName()).toURI();
            }
        });
        link.add(visibleIf(isNotNull(model)));
        return link;
    }
	
	// ----------------------------------------------------
	
	private final class EntityTypeModel extends DerivedModel<ResourceID, ResourceNode> {
		private EntityTypeModel(IModel<ResourceNode> original) {
			super(original);
		}
		@Override
		protected ResourceID derive(ResourceNode node) {
			return EntityType.of(node);
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

    // ----------------------------------------------------

    private void closeContextMenu() {
        RBAjaxTarget.add(get("extendedActionsContainer"));
    }

}
