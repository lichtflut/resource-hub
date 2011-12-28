/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.common;

import org.odlabs.wiquery.ui.dialog.Dialog;

/**
 * <p>
 *  Interface for modal dialog hosters.
 * </p>
 *
 * <p>
 * 	Created Dec 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface DialogHoster {

	void closeDialog(Dialog dialog);

	void openDialog(Dialog dialog);

	String getDialogID();

}