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
                applyActions(RBAjaxTarget.getAjaxTarget(), form);
            }
        });
    }
}
