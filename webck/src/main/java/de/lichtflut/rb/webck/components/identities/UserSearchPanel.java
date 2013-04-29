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
package de.lichtflut.rb.webck.components.identities;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.webck.components.fields.UserPickerField;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Panel for listing of users.
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class UserSearchPanel extends Panel {
	
	private static final Logger logger = LoggerFactory.getLogger(UserSearchPanel.class);
	
	@SpringBean
	private AuthModule authModule;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id The wicket ID.
	 */
	@SuppressWarnings("rawtypes")
	public UserSearchPanel(String id) {
		super(id);
		
		final IModel<QualifiedName> model = new Model<QualifiedName>();
		
		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));
		
		final UserPickerField picker = new UserPickerField("userpicker", model);
		picker.setRequired(true);
		
		form.add(picker);
		
		form.add(new RBDefaultButton("select") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				RBUser user = authModule.getUserManagement().findUser(model.getObject().toURI());
				model.setObject(null);
				target.add(form);
				logger.info("Selected user {} for import.", user.getName());
				userSelected(user);
			}
		});
		
		add(form);
	}
	
	// ----------------------------------------------------
	
	public abstract void userSelected(RBUser user);
	

}
