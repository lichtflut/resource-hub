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
package de.lichtflut.rb.webck.components.typesystem;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.SchemaExportDialog;
import de.lichtflut.rb.webck.components.dialogs.SchemaImportDialog;

/**
 * <p>
 *  IO Panel for schemas.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaIOPanel extends Panel {

	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public SchemaIOPanel(final String id) {
		super(id);

		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new SchemaExportDialog(hoster.getDialogID()));
			}
		};
		add(exportLink);

		final Link importLink = new AjaxFallbackLink("importLink") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new SchemaImportDialog(hoster.getDialogID()));
			}
		};
		add(importLink);
	}
}
