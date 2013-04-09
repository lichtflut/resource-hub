/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.navigation;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.behaviors.TitleModifier.title;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(BreadCrumbsBar.class);

	private static final String LINK_LIST = "linkList";

	@SpringBean
	private SemanticNetworkService service;

	@SpringBean
	private ResourceLinkProvider resourceLinkProvider;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param max The maximum number of steps to show.
	 */
	public BreadCrumbsBar(final String id, final int max) {
		super(id);

		setOutputMarkupId(true);

		// the links for the last entries - excluding the current
		final ListView<EntityBrowsingStep> view = new ListView<EntityBrowsingStep>(LINK_LIST, new EntityHistoryModel(max -1, true)) {
			@Override
			protected void populateItem(final ListItem<EntityBrowsingStep> item) {
				final EntityBrowsingStep step = item.getModelObject();
				final EntityHandle handle = item.getModelObject().getHandle();
				final ResourceID id = resolve(handle.getId());
				final Link<?> link = getBreadCrumbsBarLink(id);
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
		view.setReuseItems(false);
		add(view);

		// special link for the current entry
		IModel<EntityHandle> currentHandle = new CurrentHandleModel();
		add(createCurrentEntityLink(currentHandle));
		add(createUnderConstructionHint(currentHandle));
	}

	// ----------------------------------------------------

	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.ENTITY)) {
			ListView<?> view = (ListView<?>) get(LINK_LIST);
			view.removeAll();
			RBAjaxTarget.add(this);
		}
	}

	protected Link<?> getBreadCrumbsBarLink(final ResourceID id) {
		return null;

	}

	protected Link<?> getCurrentEntityLink(final IModel<EntityHandle> currentHandle) {
		return null;
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		LOGGER.debug("Current browsing stack: {} ", RBWebSession.get().getHistory());
	}

	// ----------------------------------------------------

	protected Component createCurrentEntityLink(final IModel<EntityHandle> currentHandle) {
		final Link<?> link = getCurrentEntityLink(currentHandle);
		link.add(new Label("label", new DerivedDetachableModel<String, EntityHandle>(currentHandle) {
			@Override
			protected String derive(final EntityHandle handle) {
				return getCroppedLabel(handle.getId(), 40);
			}
		}));
		link.add(title(new DerivedDetachableModel<String, EntityHandle>(currentHandle) {
			@Override
			protected String derive(final EntityHandle handle) {
				ResourceID resolved = resolve(handle.getId());
				return ResourceLabelBuilder.getInstance().getLabel(resolved, getLocale());
			}
		}));
		link.add(visibleIf(not(new IsOnCreationConditional(currentHandle))));
		return link;
	}


	protected Component createUnderConstructionHint(final IModel<EntityHandle> currentHandle) {
		Label label = new Label("underConstructionHint", new ResourceModel("label.node-in-creation"));
		label.add(visibleIf(new IsOnCreationConditional(currentHandle)));
		return label;
	}

	// ----------------------------------------------------

	private ResourceID resolve(final ResourceID rid) {
		if (rid.asResource().isAttached()) {
			return rid;
		}
		return service.resolve(rid);
	}

	private String getCroppedLabel(final ResourceID id, final int max) {
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

	// -- INNER CLASSES -----------------------------------

	private static class IsOnCreationConditional extends ConditionalModel<EntityHandle> {

		private IsOnCreationConditional(final IModel<EntityHandle> model) {
			super(model);
		}

		@Override
		public boolean isFulfilled() {
			return getObject().isOnCreation();
		}
	}

	private static class CurrentHandleModel extends AbstractLoadableDetachableModel<EntityHandle> {
		@Override
		public EntityHandle load() {
			EntityBrowsingStep step = RBWebSession.get().getHistory().getCurrentStep();
			return step.getHandle();
		}
	}
}
