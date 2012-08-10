/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.custom;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNotNull;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNull;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.query.SortCriteria;

import de.lichtflut.rb.application.base.RBBasePage;
import de.lichtflut.rb.application.extensions.RBResourceListPanel;
import de.lichtflut.rb.application.resourceview.EntityDetailPage;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.common.TermSearcher;
import de.lichtflut.rb.core.common.TermSearcher.Mode;
import de.lichtflut.rb.webck.browsing.JumpTarget;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.fields.SearchField;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.ListPagerPanel;
import de.lichtflut.rb.webck.components.listview.ResourceListPanel;
import de.lichtflut.rb.webck.components.typesystem.TypeBrowserPanel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.PageableModel;
import de.lichtflut.rb.webck.models.resources.ResourceQueryResultModel;
import de.lichtflut.rb.webck.models.types.SNClassListModel;

/**
 * <p>
 *  Page for Browsing and Searching.
 * </p>
 *
 * <p>
 * 	Created Dec 16, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class BrowseAndSearchPage extends RBBasePage {

	public static final String PARAM_TERM = "term";

	public static final int MAX_RESULTS = 20;

	// ----------------------------------------------------

	@SpringBean
	private ModelingConversation conversation;

	// ----------------------------------------------------

	private final IModel<ResourceID> typeModel = new Model<ResourceID>();

	private final IModel<String> termModel = new Model<String>();

	private final IModel<QueryResult> queryModel = new QueryModel();

	private final PageableModel<ResourceNode> resultModel;

	// ----------------------------------------------------

	/**
	 * @param parameters
	 */
	public BrowseAndSearchPage(final PageParameters parameters) {
		super(parameters);

		RBWebSession.get().getHistory().clear(new JumpTarget(getPageClass(), parameters));

		resultModel = new ResourceQueryResultModel(queryModel, new Model<Integer>(MAX_RESULTS));

		final StringValue termParam = parameters.get(PARAM_TERM);
		if (!termParam.isEmpty()) {
			search(termParam.toString());
		}

		final WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);

		container.add(createForm());

		container.add(createResultList());

		container.add(createPager());

		container.add(createCreateLink());

		add(container);

		add(new TypeBrowserPanel("typeBrowser", new SNClassListModel()) {
			@Override
			public void onTypeSelected(final SNClass type, final AjaxRequestTarget target) {
				search(type);
			}

			@Override
			protected boolean isCreateLinkVisible(){
				return false;
			}
		});

	}

	// ----------------------------------------------------

	@SuppressWarnings("rawtypes")
	protected Form<?> createForm() {
		final IModel<String> searchModel = new Model<String>();
		final Form<?> form = new Form("form") {
			@Override
			protected void onSubmit() {
				final PageParameters params = new PageParameters();
				params.add(PARAM_TERM, searchModel.getObject());
				setResponsePage(BrowseAndSearchPage.class, params);
			}
		};
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));
		form.add(new SearchField("search", searchModel).setRequired(true));
		form.add(new Button("submit"));
		return form;
	}

	@SuppressWarnings("rawtypes")
	protected AjaxLink<?> createCreateLink() {
		final AjaxLink createLink = new AjaxLink("createLink") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				final PageParameters linkParams = new PageParameters();
				linkParams.add(EntityDetailPage.PARAM_RESOURCE_TYPE, typeModel.getObject().getQualifiedName().toURI());
				linkParams.set(DisplayMode.PARAMETER, DisplayMode.EDIT);
				setResponsePage(EntityDetailPage.class, linkParams);
			}
		};
		createLink.add(visibleIf(isNotNull(typeModel)));
		return createLink;
	}

	protected ResourceListPanel createResultList() {
		final ColumnConfiguration config = ColumnConfiguration.defaultConfig();
		addResolved(config, RDFS.LABEL);
		addResolved(config, RDF.TYPE);

		final ResourceListPanel resultList = new RBResourceListPanel("result", resultModel, Model.of(config));
		resultList.add(visibleIf(not(isEmpty(resultModel))));

		return resultList;
	}

	protected ListPagerPanel createPager() {
		final ListPagerPanel pager = new ListPagerPanel("pager", resultModel) {
			@Override
			public void onPage() {
				updatePage();
			}
		};
		pager.add(visibleIf(not(and(isNull(typeModel), isNull(termModel)))));
		return pager;
	}

	// ----------------------------------------------------

	protected void search(final String term) {
		termModel.setObject(term);
		typeModel.setObject(null);
		resultModel.rewind();
		updatePage();
	}

	protected void search(final ResourceID type) {
		typeModel.setObject(type);
		termModel.setObject(null);
		resultModel.rewind();
		updatePage();
	}

	protected void addResolved(final ColumnConfiguration config, final ResourceID predicate) {
		config.addColumnByPredicate(conversation.resolve(predicate));
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		typeModel.detach();
		termModel.detach();
		queryModel.detach();
	}

	private void updatePage() {
		RBAjaxTarget.add(get("container"));
	}

	// ----------------------------------------------------

	private class QueryModel extends AbstractLoadableDetachableModel<QueryResult> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public QueryResult load() {
			Query query = null;

			if (typeModel.getObject() != null) {
				query = conversation.createQuery();
				query.addField(RDF.TYPE, typeModel.getObject());
			} else if (termModel.getObject() != null) {
				query = new TermSearcher().prepareQuery(conversation.createQuery(),
						termModel.getObject(), Mode.ENTITY, RBSystem.ENTITY.toURI());
			}

			if (query != null) {
				query.setSortCriteria(new SortCriteria(RDFS.LABEL.toURI()));
				return query.getResult();
			} else {
				return null;
			}
		}

	}

}
