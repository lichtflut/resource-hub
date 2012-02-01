/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.navigation;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.behaviors.TitleModifier.title;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.editor.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.entity.EntityHistoryModel;

/**
 * <p>
 *  Navigation bar showing the last entities viewed or edited.
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class BreadCrumbsBar extends Panel {
	
	@SpringBean
	private ServiceProvider provider;
	
	@SpringBean
	private ResourceLinkProvider resourceLinkProvider;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param max The maximum number of steps to show.
	 */
	public BreadCrumbsBar(String id, int max) {
		super(id);

		// the links for the last entries - excluding the current
		final ListView<EntityBrowsingStep> view = new ListView<EntityBrowsingStep>("linkList", new EntityHistoryModel(max -1, true)) {
			@Override
			protected void populateItem(ListItem<EntityBrowsingStep> item) {
				final EntityBrowsingStep step = item.getModelObject();
				final EntityHandle handle = item.getModelObject().getHandle();
				final ResourceID id = resolve(handle.getId());
				final CrossLink link = new CrossLink("link", getUrlTo(id).toString());
				switch (step.getState()) {
				case VIEW:
				case EDIT:
					link.add(new Label("label", getCroppedLabel(id, 40)));
					link.add(title(ResourceLabelBuilder.getInstance().getLabel(id, getLocale())));
					break;
				case CREATE:
				case CREATE_REFERENCE:
					link.add(new Label("label", new ResourceModel("label.node-in-creation")));
					link.setEnabled(false);
					break;
				default: throw new IllegalStateException("Unknown state: " + step.getState());
				}
				item.add(link);
			}
		};
		view.setReuseItems(true);
		add(view);
		
		
		// special link for the current entry
		add(createCurrentEntityLink());
	}
	
	// ----------------------------------------------------

	protected CrossLink createCurrentEntityLink() {
		final IModel<ResourceID> current = BrowsingContextModel.currentEntityModel();
		
		final IModel<String> currentURL = new DerivedModel<String, ResourceID>(current) {
			@Override
			protected String derive(ResourceID original) {
				return getUrlTo(original).toString();
			}
		};
		final IModel<String> currentLabel = new DerivedModel<String, ResourceID>(current) {
			@Override
			protected String derive(ResourceID original) {
				return getCroppedLabel(original, 40);
			}
		};
		final IModel<String> currentTitle = new DerivedModel<String, ResourceID>(current) {
			@Override
			protected String derive(ResourceID original) {
				final ResourceID id = resolve(original);
				return ResourceLabelBuilder.getInstance().getLabel(id, getLocale());
			}
		};
		
		final CrossLink link = new CrossLink("currentEntityLink", currentURL);
		link.add(new Label("label", currentLabel));
		link.add(title(currentTitle));
		link.add(visibleIf(ConditionalModel.not(BrowsingContextModel.isInCreateMode())));
		return link;
	}
	
	// ----------------------------------------------------
	
	protected CharSequence getUrlTo(ResourceID ref) {
		return resourceLinkProvider.getUrlToResource(ref, VisualizationMode.DETAILS, DisplayMode.VIEW);
	}
	
	private ResourceID resolve(ResourceID rid) {
		if (rid.asResource().isAttached()) {
			return rid;
		}
		return provider.getResourceResolver().resolve(rid);
	}
	
	private final String getCroppedLabel(ResourceID id, int max) {
		String label = ResourceLabelBuilder.getInstance().getLabel(resolve(id), getLocale());
		if (label.length() > max) {
			label = label.substring(0, max -3);
			int softLimit = max -10;
			if (label.indexOf("(") > softLimit) {
				label = label.substring(0, label.indexOf("(") -1);
			} else if (label.indexOf(" ") > softLimit) {
				label = label.substring(0, label.indexOf(" ") -1);
			}
			label += "...";
		}
		return label;
	}

}
