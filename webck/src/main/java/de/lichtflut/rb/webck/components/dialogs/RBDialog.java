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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.IMarkupSourcingStrategy;
import org.apache.wicket.markup.html.panel.PanelMarkupSourcingStrategy;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;

import java.util.HashMap;
import java.util.Map;

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
public class RBDialog extends WebMarkupContainer {
	
	public static final String CONTENT = "content";

    private final Map<String, Object> options = new HashMap<String, Object>();
	
	// ----------------------------------------------------

	/**
     * Constructor.
	 * @param id The component ID.
	 */
	public RBDialog(final String id) {
		super(id);
		
        add(CssModifier.appendStyle("visibility:hidden"));
        setOutputMarkupPlaceholderTag(true);

        setAutoOpen(false);
        setModal(true);
	}

    // ----------------------------------------------------

    public RBDialog setWidth(int width) {
        options.put("height", "" + width);
        return this;
    }

    public RBDialog setHeight(int height) {
        options.put("height", "" + height);
        return this;
    }

    public RBDialog setModal(boolean modal) {
        options.put("modal", modal);
        return this;
    }

    public RBDialog setTitle(IModel<String> titleModel) {
        options.put("title", assignModel(titleModel));
        return this;
    }

    public RBDialog setAutoOpen(boolean autoOpen) {
        options.put("autoOpen", autoOpen);
        return this;
    }

    // ----------------------------------------------------

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderOnLoadJavaScript("jQuery('#" + getMarkupId() + "').css('visibility', 'visible');");
        response.renderOnLoadJavaScript("jQuery('#" + getMarkupId() + "').dialog(" + optionsAsJson() + ");");
    }

    // ----------------------------------------------------
	
	@Override
	protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
		return new PanelMarkupSourcingStrategy(false);
	}

    // ----------------------------------------------------

    public void open() {
        AjaxRequestTarget target = RBAjaxTarget.getAjaxTarget();
        if (target != null) {
            open(target);
        } else {
            setVisible(true);
            setAutoOpen(true);
        }
    }

    public void open(AjaxRequestTarget target) {
        target.appendJavaScript("jQuery('#" + getMarkupId() + "').dialog('open');");
        target.add(this);
        setVisible(true);
    }

    public void close() {
        AjaxRequestTarget target = RBAjaxTarget.getAjaxTarget();
        if (target != null) {
            close(target);
        } else {
            setVisible(false);
            setAutoOpen(false);
        }
    }

    public void close(AjaxRequestTarget target) {
        target.prependJavaScript("jQuery('#" + getMarkupId() + "').dialog('close');");
        target.add(this);
        setVisible(false);
        setAutoOpen(false);
    }

    // ----------------------------------------------------

    private String optionsAsJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (String key : options.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            String value = toString(options.get(key));
            if (value != null) {
                sb.append(key).append(" : '").append(value).append("'");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private String toString(Object object) {
        if (object instanceof IModel) {
            return toString(((IModel)object).getObject());
        } else if (object == null) {
            return null;
        } else {
            return object.toString();
        }
    }

    public IModel assignModel(IModel model)
    {
        if (model instanceof IComponentAssignedModel) {
            IComponentAssignedModel caModel = (IComponentAssignedModel) model;
            return caModel.wrapOnAssignment(this);
        }
        return model;
    }

}
