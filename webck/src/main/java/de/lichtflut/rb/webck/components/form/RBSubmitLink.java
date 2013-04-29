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
package de.lichtflut.rb.webck.components.form;

import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  RB extension of AjaxSubmitLink.
 * </p>
 *
 * <p>
 *  Created 01.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBSubmitLink extends AjaxSubmitLink {

    private IModel<String> confirmationMessage;

    // ----------------------------------------------------

    /**
     * Constructor.
     * @param id The componentn ID.
     */
    public RBSubmitLink(String id) {
        super(id);
    }

    // ----------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        RBAjaxTarget.add(form);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void onSubmit(AjaxRequestTarget target, Form<?> form) {
        if (confirmationMessage != null) {
            requestConfirmation(form);
        } else {
            applyActions(target, form);
        }
    }

    /**
     * Called when the form was successfully submitted and all preconditions were met.
     * @param target The ajax request target.
     * @param form The submitted form.
     */
    protected void applyActions(AjaxRequestTarget target, Form<?> form) {
    }

    // ----------------------------------------------------

    public void needsConfirmation(IModel<String> message) {
        this.confirmationMessage = message;
    }

    // ----------------------------------------------------

    private void requestConfirmation(final Form<?> form) {
        final DialogHoster hoster = findParent(DialogHoster.class);
        hoster.openDialog(new ConfirmationDialog(hoster.getDialogID(), confirmationMessage) {
            @Override
            public void onConfirm() {
                applyActions(AjaxRequestTarget.get(), form);
            }
        });
    }
}
