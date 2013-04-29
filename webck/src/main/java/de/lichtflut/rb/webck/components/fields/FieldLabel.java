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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Feb 10, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class FieldLabel extends Label {

	/**
	 * @param id
	 */
	public FieldLabel(String id, FormComponent<?> fc) {
		super(id, fc.getLabel());
	}

	/**
	 * @param id
	 * @param model
	 */
	public FieldLabel(FormComponent<?> fc) {
		super(getFieldIdFor(fc), fc.getLabel());
	}
	
	// ----------------------------------------------------
	
	public static String getFieldIdFor(FormComponent<?> fc) {
		return fc.getId() + "Label";
	}

}
