/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.listview;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.enableIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.greaterThan;
import static de.lichtflut.rb.webck.models.ConditionalModel.lessThan;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.arastreju.sge.query.QueryResult;

import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  Panel that shows meta info about a list view and allows to page the results.
 * </p>
 *
 * <p>
 * 	Created Feb 16, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ListPagerPanel extends TypedPanel<QueryResult> {

	private final DerivedDetachableModel<Integer, QueryResult> found;
	
	private final IModel<Integer> offset;
	
	private final IModel<Integer> viewsize;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public ListPagerPanel(final String id, final IModel<QueryResult> model, final IModel<Integer> offset, final IModel<Integer> viewsize) {
		super(id, model);
		this.offset = offset;
		this.viewsize = viewsize;
		
		found = new DerivedDetachableModel<Integer, QueryResult>(model) {
			@Override
			protected Integer derive(QueryResult result) {
				return result.size();
			}
		};
		
		final DerivedDetachableModel<Integer, QueryResult> showing = new DerivedDetachableModel<Integer, QueryResult>(model) {
			@Override
			protected Integer derive(QueryResult result) {
				return Math.min(viewsize.getObject(), result.size());
			}
		};
		
		add(new Label("info", new StringResourceModel("label.display-info", model, showing, found))
				.add(visibleIf(greaterThan(found, Model.of(0)))));
		
		add(new Label("empty", new ResourceModel("label.display-info.empty"))
				.add(visibleIf(lessThan(found, Model.of(1)))));
		
		final WebMarkupContainer pager = new WebMarkupContainer("pager");
		pager.add(visibleIf(lessThan(showing, found)));
		
		pager.add(new AjaxLink("previous") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				int newOffset = Math.max(0, offset.getObject() - viewsize.getObject());
				offset.setObject(newOffset);
				onPage();
			}
		}.add(enableIf(greaterThan(offset, Model.of(0)))));
		
		pager.add(new AjaxLink("next") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				int newOffset = offset.getObject() + viewsize.getObject();
				if (newOffset < found.getObject()) {
					offset.setObject(newOffset);
					onPage();
				}
			}
		}.add(enableIf(new ProceedConditional())));
		
		add(pager);
	}
	
	// ----------------------------------------------------
	
	public void onPage() { }
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		this.offset.detach();
		this.viewsize.detach();
		this.found.detach();
	}
	
	// ----------------------------------------------------
	
	private class ProceedConditional extends ConditionalModel<Integer> {
		@Override
		public boolean isFulfilled() {
			return (offset.getObject() + viewsize.getObject()) < found.getObject();
		}
	}
	
}
