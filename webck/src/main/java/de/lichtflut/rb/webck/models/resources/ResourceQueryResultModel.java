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
package de.lichtflut.rb.webck.models.resources;

import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.basic.PageableModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.QueryResult;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  Model of a list of resource nodes derived from a query result.
 * </p>
 *
 * <p>
 * 	Created Feb 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceQueryResultModel extends DerivedDetachableModel<List<ResourceNode>, QueryResult> implements PageableModel<ResourceNode> {

	private IModel<Integer> resultSize;

	private IModel<Integer> pagesize;

	private IModel<Integer> offset;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * Displays given QueryResult with a maximum of {@link Integer#MAX_VALUE} per page and start at its first entry.
	 * @param queryResult
	 */
	public ResourceQueryResultModel(final IModel<QueryResult> queryResult) {
		this(queryResult, new Model<Integer>(Integer.MAX_VALUE));
	}

	/**
	 * Constructor.
	 * Displays given QueryResult with a given maximum per page and start at its first entry.
	 * @param queryResult
	 * @param pagesize Maximum results per page
	 */
	public ResourceQueryResultModel(final IModel<QueryResult> queryResult, final IModel<Integer> pagesize) {
		this(queryResult, pagesize, Model.of(0));
	}


	/**
	 * Constructor.
	 * Displays given QueryResult with a given maximum per page and start at a given entry.
	 * @param queryResult
	 * @param pagesize Maximum results per page
	 * @param offset
	 */
	public ResourceQueryResultModel(final IModel<QueryResult> queryResult, final IModel<Integer> pagesize, final IModel<Integer> offset) {
		super(queryResult);
		this.pagesize = pagesize;
		this.offset = offset;
		this.resultSize = new DerivedModel<Integer, QueryResult>(queryResult) {
			@Override
			protected Integer derive(final QueryResult result) {
				return result.size();
			}
			@Override
			public Integer getDefault() {
				return 0;
			}
		};
	}

	// ----------------------------------------------------

	@Override
	public List<ResourceNode> derive(final QueryResult result) {
		int max = Math.min(result.size(), pagesize.getObject());
		return result.toList(offset.getObject(), max);
	}

	@Override
	public List<ResourceNode> getDefault() {
		return Collections.emptyList();
	}

	// -- PAGING ------------------------------------------

	@Override
	public IModel<Integer> getResultSize() {
		return resultSize;
	}

	@Override
	public IModel<Integer> getPageSize() {
		return pagesize;
	}

	@Override
	public IModel<Integer> getOffset() {
		return offset;
	}

	// ----------------------------------------------------

	@Override
	public void rewind() {
		offset.setObject(0);
	}

	@Override
	public void back() {
		int newOffset = Math.max(offset.getObject() - pagesize.getObject(), 0);
		offset.setObject(newOffset);
	}

	@Override
	public void next() {
		int newOffset = offset.getObject() + pagesize.getObject();
		if (newOffset <= resultSize.getObject()) {
			offset.setObject(newOffset);
		}
	}

}
