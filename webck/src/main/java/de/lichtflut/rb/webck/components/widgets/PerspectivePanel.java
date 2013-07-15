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
package de.lichtflut.rb.webck.components.widgets;

import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.config.DefaultPathBuilder;
import de.lichtflut.rb.webck.config.DownloadPath;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.util.List;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isTrue;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

/**
 * <p>
 *  Panel displaying widgets based on a perspective specification.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectivePanel extends TypedPanel<Perspective> {

	private IModel<Boolean> isConfigMode = new Model<Boolean>(false);

	private ConditionalModel<Boolean> isConfigConditional = isTrue(isConfigMode);

    @SpringBean
    private ServiceContext serviceContext;

	// ----------------------------------------------------
	
	/**
     * Constructor.
	 * @param id The component ID.
     * @param spec The perspective specification.
	 */
	@SuppressWarnings("rawtypes")
	public PerspectivePanel(String id, IModel<Perspective> spec) {
		super(id, spec);

		setOutputMarkupId(true);
		
		add(new AjaxLink("configureLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				isConfigMode.setObject(true);
				target.add(PerspectivePanel.this);
			}
		}.add(visibleIf(not(isConfigConditional))));
		
		add(new AjaxLink("configDoneLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				isConfigMode.setObject(false);
				target.add(PerspectivePanel.this);
			}
		}.add(visibleIf(isConfigConditional)));
		
		add(createExportLink(spec));

		final WebMarkupContainer container = new WebMarkupContainer("viewPortsContainer");
		container.add(CssModifier.appendClass(new LayoutModel(spec)));
		
		container.add(new ListView<ViewPort>("portList", new PortListModel(spec)) {
			@Override
			protected void populateItem(ListItem<ViewPort> item) {
				item.add(new ViewPortPanel("port", item.getModel(), isConfigConditional));
			}
		});
		add(container);
		
	}
	
	// ----------------------------------------------------
	
	@Override
	public void onEvent(IEvent<?> event) {
		if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.VIEW_SPEC)) {
			getModel().detach();
			RBAjaxTarget.add(this);
		}
	}
	
	protected AbstractLink createExportLink(IModel<Perspective> model) {
        CrossLink link = new CrossLink("exportLink", new DerivedDetachableModel<String, Perspective>(model) {
            @Override
            protected String derive(Perspective perspective) {
                DownloadPath path = new DefaultPathBuilder().createDownloadPath(serviceContext.getDomain());
                return path.perspective().withQN(perspective.getQualifiedName()).toString();
            }
        });
		return link;
	}
	
	// ---------------------------------------------------- 
	
	private final class LayoutModel extends DerivedDetachableModel<String, Perspective> {

		private LayoutModel(IModel<Perspective> original) {
			super(original);
		}

		@Override
		protected String derive(Perspective perspective) {
			SemanticNode layout = SNOPS.fetchObject(perspective, WDGT.HAS_LAYOUT);
			if (layout != null && layout.isValueNode()) {
				return layout.asValue().getStringValue();
			} else {
				return WDGT.DEFAULT_LAYOUT;
			}
		}
	}

	class PortListModel extends DerivedDetachableModel<List<ViewPort>, Perspective> {

		/**
		 * @param original The original model.
		 */
		public PortListModel(IModel<Perspective> original) {
			super(original);
		}

		@Override
		protected List<ViewPort> derive(Perspective original) {
			return original.getViewPorts();
		}
		
	}
	
}
