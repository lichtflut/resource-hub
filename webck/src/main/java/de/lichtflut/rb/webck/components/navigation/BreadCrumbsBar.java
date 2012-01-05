/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.navigation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;
import de.lichtflut.rb.webck.components.editor.IBrowsingHandler;
import de.lichtflut.rb.webck.components.editor.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.models.CurrentEntityModel;
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
public abstract class BreadCrumbsBar extends Panel {
	
	/**
	 * @param id
	 */
	public BreadCrumbsBar(String id, int max) {
		super(id);
		
		final ListView<EntityBrowsingStep> view = new ListView<EntityBrowsingStep>("linkList", new EntityHistoryModel(max -1, true)) {
			@Override
			protected void populateItem(ListItem<EntityBrowsingStep> item) {
				final EntityHandle handle = item.getModelObject().getHandle();
				final ResourceID id = resolve(handle.getId());
				final CrossLink link = new CrossLink("link", getUrlTo(id).toString());
				link.add(new Label("label", getCroppedLabel(id, 40)));
				link.add(TitleModifier.title(ResourceLabelBuilder.getInstance().getLabel(id, getLocale())));
				item.add(link);
			}
		};
		view.setReuseItems(true);
		add(view);
		
		add(createCurrentEntityLink());
	}
	
	// ----------------------------------------------------

	protected CrossLink createCurrentEntityLink() {
		final IModel<ResourceID> current = new CurrentEntityModel();
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
		link.add(TitleModifier.title(currentTitle));
		return link;
	}
	
	// ----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	protected CharSequence getUrlTo(ResourceID ref) {
		final IBrowsingHandler handler = findParent(IBrowsingHandler.class);
		if (handler == null) {
			throw new IllegalStateException(getClass().getSimpleName() 
					+ " must be placed placed in a component/page that implements " 
					+ IBrowsingHandler.class);
		}
		final CharSequence url = handler.getUrlToResource(ref, VisualizationMode.DETAILS);
		return url;
	}
	
	private ResourceID resolve(ResourceID rid) {
		if (rid.asResource().isAttached()) {
			return rid;
		}
		return getServiceProvider().getResourceResolver().resolve(rid);
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
