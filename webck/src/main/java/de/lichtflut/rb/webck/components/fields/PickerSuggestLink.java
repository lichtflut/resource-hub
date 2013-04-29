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
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.odlabs.wiquery.ui.autocomplete.Autocomplete;

/**
 * <p>
 *  A suggest link/button for data picker fields. Only to be used together with {@link DataPickerField}s.
 * </p>
 *
 * <p>
 * 	Created Dec 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class PickerSuggestLink extends Label {

	/**
	 * @param id
	 */
	protected PickerSuggestLink(String id, Autocomplete field) {
		super(id);
		add(new AttributeModifier("onclick", "LFRB.Datapicker.toggle('#"+ field.getMarkupId() + "')"));
	}
	
}
