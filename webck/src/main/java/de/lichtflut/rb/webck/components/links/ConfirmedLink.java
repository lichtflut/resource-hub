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
package de.lichtflut.rb.webck.components.links;

import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Standard button
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ConfirmedLink<T> extends AjaxLink<T> {

	private final IModel<String> confirmationMessage;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public ConfirmedLink(String id, IModel<String> confirmationMessage) {
		super(id);
		this.confirmationMessage = confirmationMessage;
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onClick(AjaxRequestTarget target) {
		requestConfirmation();
	}
	
	/**
	 * Called when the form was successfully submitted and all preconditions were met.
	 * @param target The ajax request target.
	 */
	protected abstract void applyActions(AjaxRequestTarget target);
	
	// ----------------------------------------------------
	
	private void requestConfirmation() {
		final DialogHoster hoster = findParent(DialogHoster.class); 
		hoster.openDialog(new ConfirmationDialog(hoster.getDialogID(), confirmationMessage) {
			@Override
			public void onConfirm() {
				applyActions(AjaxRequestTarget.get());
			}
		});
	}

}
