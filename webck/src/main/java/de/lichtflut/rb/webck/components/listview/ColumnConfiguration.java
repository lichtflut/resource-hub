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
/**
 * 
 */
package de.lichtflut.rb.webck.components.listview;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.listview.ColumnHeader.ColumnType;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.arastreju.sge.model.ResourceID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 *  Configuration of a column of a entity or resource lists.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ColumnConfiguration implements Serializable {
	
	private final List<String> actions = new ArrayList<String>();
	
	private final List<ResourceID> predicates = new ArrayList<ResourceID>();
	
	private final Map<ResourceID, String> labelMap = new HashMap<ResourceID, String>();
	
	// ----------------------------------------------------
	
	/**
	 * Creates a default configuration with 'view', 'edit' and 'delete' actions.
	 * @return A new default configuration object.
	 */
	public static ColumnConfiguration defaultConfig() {
		return new ColumnConfiguration(ListAction.VIEW, ListAction.EDIT, ListAction.DELETE);
	}
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param supportedActions The supported standard actions.
	 */
	public ColumnConfiguration(final ListAction... supportedActions) {
		for (ListAction current : supportedActions) {
			this.actions.add(current.name());
		}
	}
	
	// ----------------------------------------------------

    /**
     * Add a column for a predicate.
     * @param predicate The predicate.
     * @return This.
     */
    public ColumnConfiguration addColumn(ResourceID predicate) {
        predicates.add(predicate);
        return this;
    }

    /**
     * Add a column for a predicate with a fixed label.
     * @param predicate The predicate.
     * @param label The label.
     * @return This.
     */
    public ColumnConfiguration addColumn(ResourceID predicate, String label) {
        predicates.add(predicate);
        labelMap.put(predicate, label);
        return this;
    }

    // ----------------------------------------------------
	
	public String[] getActions() {
		return actions.toArray(new String[actions.size()]);
	}
	
	/**
	 * @return the predicates
	 */
	public List<ResourceID> getPredicatesToDisplay() {
		return predicates;
	}
	
	// ----------------------------------------------------
	
	public List<ColumnHeader> getHeaders() {
		final Locale locale = RequestCycle.get().getRequest().getLocale();
		final List<ColumnHeader> headers = new ArrayList<ColumnHeader>();
		for (ResourceID predicate : predicates) {
			final String label = getLabel(predicate, locale);
			headers.add(new SimpleColumnHeader(label, predicate, ColumnType.DATA));
		}
		for (@SuppressWarnings("unused") String action : getActions()) {
			headers.add(new SimpleColumnHeader("", null, ColumnType.ACTION));
		}
		return headers;
	}
	
	public String getLabel(ResourceID predicate, Locale locale) {
		if (labelMap.containsKey(predicate)) {
			return labelMap.get(predicate);
		} else {
			return ResourceLabelBuilder.getInstance().getFieldLabel(predicate, locale);
		}
	}

}
