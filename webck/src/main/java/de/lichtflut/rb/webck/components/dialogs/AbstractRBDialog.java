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

import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.IMarkupSourcingStrategy;
import org.apache.wicket.markup.html.panel.PanelMarkupSourcingStrategy;
import org.odlabs.wiquery.ui.dialog.Dialog;

/**
 * <p>
 *  Abstract base dialog.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class AbstractRBDialog extends Dialog {
	
	public static final String CONTENT = "content";
	
	// ----------------------------------------------------

	/**
     * Constructor.
	 * @param id The component ID.
	 */
	public AbstractRBDialog(final String id) {
		super(id);
		
		setModal(true);

        add(CssModifier.appendStyle("visibility:hidden"));

	}

    // ----------------------------------------------------

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderOnLoadJavaScript("jQuery('#" + getMarkupId() + "').css('visibility', 'visible');");
    }

    // ----------------------------------------------------
	
	@Override
	protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
		return new PanelMarkupSourcingStrategy(false);
	}

    protected void closeDialog() {
        close(RBAjaxTarget.getAjaxTarget());
    }



}
