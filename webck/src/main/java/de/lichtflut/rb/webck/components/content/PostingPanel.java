package de.lichtflut.rb.webck.components.content;

import java.text.DateFormat;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  This panel display a single posting.
 * </p>
 * <p>
 *  A posting can be one of
 *  <ul>
 *      <li>News</li>
 *      <li>Blog post</li>
 *      <li>Forum entry</li>
 *      <li>Article</li>
 *  </ul>
 *
 * </p>
 *
 * <p>
 *  Created 04.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PostingPanel extends TypedPanel<ContentItem> {

	public PostingPanel(final String id, final IModel<ContentItem> model) {
		super(id, model);

		setOutputMarkupId(true);

		add(new Label("title", getTitleModel()));
		add(new Label("author", getAuthorModel()));
		add(new Label("created", getCreationModel()));
		add(new Label("content", getContentDisplayModel(model)).setEscapeModelStrings(false));

		add(new AjaxLink<Void>("edit") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				onEdit(model.getObject());
			}
		});
	}

	// -- CALLBACKS----------------------------------------

	public void onEdit(final ContentItem item) {
	}

	// ----------------------------------------------------

	private ContentItem getContentItem() {
		return getModelObject();
	}


	protected IModel<String> getAuthorModel() {
		return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
			@Override
			protected String derive(final ContentItem item) {
				return item.getCreatorName();
			}
		};
	}

	protected IModel<String> getCreationModel() {
		return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
			@Override
			protected String derive(final ContentItem item) {
				Date date = item.getCreateDate();
				if (date != null) {
					return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, getLocale())
							.format(date);
				} else {
					return "";
				}
			}
		};
	}

	protected IModel<String> getTitleModel() {
		return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
			@Override
			protected String derive(final ContentItem item) {
				return item.getTitle();
			}
		};
	}

	private IModel<String> getContentDisplayModel(final IModel<ContentItem> model) {
		return new DerivedDetachableModel<String, ContentItem>(model) {
			@Override
			protected String derive(final ContentItem item) {
				return item.getContentAsString();
			}
		};
	}

}
