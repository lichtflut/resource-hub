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
package de.lichtflut.rb.webck.components.listview;


import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.components.links.LabeledLink;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.IAjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Action link.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ActionLink extends LabeledLink implements IAjaxLink {
	
	private IModel<String> confirmationMessage;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param label The link's label.
	 */
	public ActionLink(final String id, final IModel<String> label) {
		super(id);
		@SuppressWarnings("rawtypes")
		final AjaxFallbackLink link = new AjaxFallbackLink(LINK_ID) {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				if (confirmationMessage != null) {
					requestConfirmation();
				} else {
					ActionLink.this.onClick(target);
				}
			}
		};
		link.add(new Label(LABEL_ID, label));
		link.add(TitleModifier.title(label));
		add(link);
	}
	
	// ----------------------------------------------------

	@Override
	public abstract void onClick(AjaxRequestTarget target);
	
	public ActionLink needsConfirmation(IModel<String> message) {
		this.confirmationMessage = message;
		return this;
	}
	
	// ----------------------------------------------------
	
	private void requestConfirmation() {
		final DialogHoster hoster = findParent(DialogHoster.class); 
		hoster.openDialog(new ConfirmationDialog(hoster.getDialogID(), confirmationMessage) {
			@Override
			public void onConfirm() {
				onClick(RBAjaxTarget.getAjaxTarget());
			}
		});
	}
	
}
