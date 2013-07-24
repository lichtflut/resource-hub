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
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.webck.components.typesystem.CreateResourcePanel;

/**
 * <p>
 *  Basic dialog for creating of new Resources.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractCreateResourceDialog extends RBDialog {

	/**
	 * @param id
	 */
	public AbstractCreateResourceDialog(final String id) {
		super(id);

		add(new CreateResourcePanel(CONTENT) {
			@Override
			public void onCreate(final QualifiedName qn, final AjaxRequestTarget target) {
				AbstractCreateResourceDialog.this.onCreate(qn, target);
				close(target);
			}
		});

		setModal(true);
		setWidth(600);
	}

	// ----------------------------------------------------

	public abstract void onCreate(QualifiedName qn, AjaxRequestTarget target);

}
