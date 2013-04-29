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

import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.components.organizer.contexts.CreateContextPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * <p>
 *  Dialog for creating a new Context.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreateContextDialog extends AbstractRBDialog {
	
	@SpringBean
	protected DomainOrganizer domainOrganizer;
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	public CreateContextDialog(final String id) {
		super(id);
		
		add(new CreateContextPanel(CONTENT) {
			@Override
			public void onCancel(AjaxRequestTarget target) {
				close(target);
			}
			
			@Override
			public void onSuccess(AjaxRequestTarget target) {
				close(target);
			}
		});
		
		setModal(true);
		setWidth(600);
	}
	
}
