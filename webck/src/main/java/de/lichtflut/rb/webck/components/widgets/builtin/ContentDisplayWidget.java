package de.lichtflut.rb.webck.components.widgets.builtin;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.traverse.Walker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.rteditor.RichTextEditor;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  This a build-in widget for display of content.
 * </p>
 *
 * <p>
 *  Created 07.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class ContentDisplayWidget extends PredefinedWidget {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentDisplayWidget.class);

	private final IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);

	@SpringBean
	private SemanticNetworkService semanticNetworkService;

    @SpringBean
    private ViewSpecificationService viewSpecService;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 *
	 * @param id   The component ID.
	 * @param spec The specification.
	 * @param perspectiveInConfigMode Conditional model for checking if this widget is in config mode.
	 */
	public ContentDisplayWidget(final String id, final WidgetSpec spec, final ConditionalModel<Boolean> perspectiveInConfigMode) {
		super(id, spec, perspectiveInConfigMode);

		setOutputMarkupId(true);

        ResourceID contentItem = getContentItem(spec);

		final IModel<String> contentModel = new ContentModel(spec);

		final WebMarkupContainer display = new WebMarkupContainer("display");
		display.add(new Label("content", contentModel).setEscapeModelStrings(false));
		display.add(new AjaxLink<Void>("edit") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				mode.setObject(DisplayMode.EDIT);
				target.add(ContentDisplayWidget.this);
			}
		});
		display.add(visibleIf(areEqual(mode, DisplayMode.VIEW)));
		add(display);

		final Form<?> form = new Form<Void>("form");
		form.add(new FeedbackPanel("feedback"));

		final RichTextEditor editor = new RichTextEditor("editor", contentModel) {
			@Override
			public void onSave() {
				setContent(spec, contentModel.getObject());
				mode.setObject(DisplayMode.VIEW);
				RBAjaxTarget.add(ContentDisplayWidget.this);
			}
			@Override
			public void onCancel() {
				mode.setObject(DisplayMode.VIEW);
				RBAjaxTarget.add(ContentDisplayWidget.this);
			}
		};
		editor.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		form.add(editor);

		add(form);

	}

	// ----------------------------------------------------

	@Override
	protected IModel<String> getTitleModel() {
		return Model.of("Here will be a title soon...");
	}

	// ----------------------------------------------------

	private String getContent(final WidgetSpec spec) {
		final ResourceNode attached = semanticNetworkService.resolve(spec);
		final SemanticNode content = Walker.start(attached)
				.walk(WDGT.DISPLAYS_CONTENT_ITEM)
				.walk(RBSystem.HAS_CONTENT)
				.getSingle();
		return SNOPS.string(content);
	}

	private void setContent(final WidgetSpec spec, final String content) {
		ResourceNode attached = semanticNetworkService.resolve(spec);
		SemanticNode existing = SNOPS.fetchObject(attached, WDGT.DISPLAYS_CONTENT_ITEM);

		if (existing == null) {
			LOGGER.debug("Creating content of widget {} : {}", spec, content);
			ResourceNode contentItem = new SNResource();
			SNOPS.assure(attached, WDGT.DISPLAYS_CONTENT_ITEM, contentItem);
			SNOPS.assure(contentItem, RBSystem.HAS_CONTENT, content);
		} else if (existing.isResourceNode()) {
			LOGGER.debug("Updating content of widget {} : {}", spec, content);
			SNOPS.assure(existing.asResource(), RBSystem.HAS_CONTENT, content);
		} else {
			throw new IllegalStateException("Unexpected content item for content widget: " + existing);
		}
	}

    private ResourceID getContentItem(WidgetSpec spec) {
        SemanticNode node = SNOPS.fetchObject(spec, WDGT.DISPLAYS_CONTENT_ITEM);
        if (node != null && node.isResourceNode()) {
            return SNOPS.id(node.asResource());
        } else {
            return null;
        }
    }

	private class ContentModel implements IModel<String> {

		private final WidgetSpec spec;
		private String content;

		public ContentModel(final WidgetSpec spec) {
			this.spec = spec;
		}

		@Override
		public String getObject() {
			if (content == null) {
				content = getContent(spec);
			}
			return content;
		}

		@Override
		public void setObject(final String content) {
			this.content = content;
		}

		@Override
		public void detach() {
			this.content = null;
		}

	}
}
