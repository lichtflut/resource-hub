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
package de.lichtflut.rb.webck.components.typesystem.constraints;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.CreatePublicConstraintDialog;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemHelpPanel;
import de.lichtflut.rb.webck.models.types.PublicConstraintsListModel;

/**
 * <p>
 * This is an aggregator {@link Panel} that contains everything necessary to manage public
 * constraints.
 * </p>
 * Created: Apr 23, 2012
 * 
 * @author Ravi Knox
 */
public class PublicConstraintsEditorPanelAggregator extends Panel {

	@SpringBean
	private SchemaManager schemaManager;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id - wicket:id
	 */
	public PublicConstraintsEditorPanelAggregator(final String id) {
		super(id);
		add(new TypeSystemHelpPanel("editor").setOutputMarkupId(true));
		//		add(new Label("searchbox", Model.of("Search...")));
		add(new ConstraintsBrowserPanel("publicConstraintsBrowser", new PublicConstraintsListModel()) {
			@Override
			public void onCreateConstraint(final AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreatePublicConstraintDialog(hoster.getDialogID()));
			}

			@Override
			public void onConstraintSelected(final Constraint constraint, final AjaxRequestTarget target) {
				displayConstraintEditor(constraint);
			}
		});
	}

	// ------------------------------------------------------

	protected void displayConstraintEditor(final Constraint constraint) {
		final Constraint reloaded = schemaManager.findConstraint(constraint.getQualifiedName());
		final Panel editor = new PublicConstraintsDetailPanel("editor", Model.of(reloaded));
		replace(editor);
		RBAjaxTarget.add(editor);
	}

}
