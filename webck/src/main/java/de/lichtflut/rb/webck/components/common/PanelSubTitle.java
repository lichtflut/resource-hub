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
package de.lichtflut.rb.webck.components.common;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 * This panel is the standard component for component Subtitles (second lvl title).
 * </p>
 * Created: Feb 21, 2013
 *
 * @author Ravi Knox
 */
public class PanelSubTitle extends Panel {

	/**
	 * Constructor.
	 * @param id Component id
	 * @param model Contains the title text.
	 */
	public PanelSubTitle(final String id, final IModel<String> model) {
		super(id, model);
		add(new Label("title", model));
	}
}
