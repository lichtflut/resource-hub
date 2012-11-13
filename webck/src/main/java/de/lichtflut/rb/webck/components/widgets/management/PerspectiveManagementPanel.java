/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.management;

import de.lichtflut.rb.core.common.Accessibility;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.viewspecs.PerspectiveListModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

/**
 * <p>
 *  Panel for management of perspectives.
 * </p>
 *
 * <p>
 * 	Created Feb 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectiveManagementPanel extends Panel {
	
	@SpringBean
	private ViewSpecificationService viewSpecificationService;

    @SpringBean
    private AuthModule authModule;
	
	// ----------------------------------------------------

	/**
     * Constructor.
	 * @param id The component ID.
	 */
	@SuppressWarnings("rawtypes")
	public PerspectiveManagementPanel(String id) {
		super(id);
		
		setOutputMarkupId(true);
		
		final PerspectiveListModel perspectives = new PerspectiveListModel();
		final ListView<Perspective> view = createListView(perspectives);
		add(view);
		
		final AjaxLink link = new AjaxLink("create") {
			@Override
			public void onClick(AjaxRequestTarget target) {
                final Perspective perspective = viewSpecificationService.initializePerspective(new SimpleResourceID());
                final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreatePerspectiveDialog(hoster.getDialogID(), perspective));
			}
		};
		add(link);
		
	}

    private ListView<Perspective> createListView(final PerspectiveListModel perspectives) {
        return new ListView<Perspective>("list", perspectives) {
            @Override
            protected void populateItem(final ListItem<Perspective> item) {
                final Perspective perspective = item.getModelObject();
                item.add(new Label("name", perspective.getName()));
                item.add(new Label("title", perspective.getTitle()));
                item.add(new EnumLabel<Accessibility>("visibility", perspective.getVisibility()));
                item.add(new Label("owner", new OwnerModel(item.getModel())));
                item.add(new AjaxLink("edit") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        final DialogHoster hoster = findParent(DialogHoster.class);
                        hoster.openDialog(new EditPerspectiveDialog(hoster.getDialogID(), item.getModel()));
                    }
                });
                item.add(new AjaxLink("delete") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        delete(item.getModelObject());
                        perspectives.detach();
                    }
                });
            }
        };
    }

    // ----------------------------------------------------
	
	@Override
	public void onEvent(IEvent<?> event) {
		if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.VIEW_SPEC)) {
			RBAjaxTarget.add(this);
		}
	}
	
	// ----------------------------------------------------

	private void delete(Perspective perspective) {
		viewSpecificationService.remove(perspective);
		RBAjaxTarget.add(this);
	}

    private class OwnerModel extends DerivedDetachableModel<String, Perspective> {

        private OwnerModel(IModel<Perspective> original) {
            super(original);
        }

        @Override
        protected String derive(Perspective perspective) {
            ResourceID owner = perspective.getOwner();
            if (owner != null) {
                RBUser user = authModule.getUserManagement().findUser(owner.toURI());
                if (user != null) {
                    return user.getName();
                }
            }
            return "-";
        }
    }

}
