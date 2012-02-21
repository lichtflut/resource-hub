/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.listview;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.enableIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.greaterThan;
import static de.lichtflut.rb.webck.models.ConditionalModel.*;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;

import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.basic.PageableModel;

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
public class ListPagerPanel extends Panel {

	private static final Model<Integer> ZERO = Model.of(0);
	
	private final IModel<Integer> found;
	
	private final IModel<Integer> offset;
	
	private final IModel<Integer> pagesize;

	private final IModel<Integer> last;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public ListPagerPanel(final String id, PageableModel<?> model) {
		this(id, model.getResultSize(), model.getOffset(), model.getPageSize());
	}
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model containing the query result.
	 * @param offset The offset.
	 * @param pagesize The size of one page.
	 */
	@SuppressWarnings("rawtypes")
	public ListPagerPanel(final String id, final IModel<Integer> found, final IModel<Integer> offset, final IModel<Integer> pagesize) {
		super(id);
		
		this.found = found;
		this.offset = offset;
		this.pagesize = pagesize;
		
		this.last = new DerivedDetachableModel<Integer, Integer>(found) {
			@Override
			protected Integer derive(Integer found) {
				return Math.min(offset.getObject() + pagesize.getObject(), found);
			}
		};
		
		add(new Label("info", new StringResourceModel("label.display-info.start", new Model(), last, found))
				.add(visibleIf(and(greaterThan(found, ZERO), areEqual(offset, ZERO)))));
		
		add(new Label("range", new StringResourceModel("label.display-info.range", new Model(), offset, last, found))
				.add(visibleIf(greaterThan(offset, ZERO))));

		
		add(new Label("empty", new ResourceModel("label.display-info.empty"))
				.add(visibleIf(areEqual(found, ZERO))));
		
		final WebMarkupContainer pager = new WebMarkupContainer("pager");
		pager.add(visibleIf(lessThan(last, found)));
		
		pager.add(new AjaxLink("previous") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				int newOffset = Math.max(0, offset.getObject() - pagesize.getObject());
				offset.setObject(newOffset);
				onPage();
			}
		}.add(enableIf(greaterThan(offset, ZERO))));
		
		pager.add(new AjaxLink("next") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				int newOffset = offset.getObject() + pagesize.getObject();
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
		this.pagesize.detach();
		this.found.detach();
		this.last.detach();
	}
	
	// ----------------------------------------------------
	
	private class ProceedConditional extends ConditionalModel<Integer> {
		@Override
		public boolean isFulfilled() {
			return (offset.getObject() + pagesize.getObject()) < found.getObject();
		}
	}
	
}
