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
package de.lichtflut.rb.application.styles.jqueryui;

import de.lichtflut.rb.application.styles.Style;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 *  The frugal default style of RB applications.
 * </p>
 *
 * <p>
 * Created 13.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class JQueryUiStyle implements Style {

    public static ResourceReference JQUERY_UI_CSS =
            new JavaScriptResourceReference(JQueryUiStyle.class, "jquery-ui-1.10.3.css");

    // ----------------------------------------------------

    @Override
    public void addStyle(IHeaderResponse response) {
        response.renderCSSReference(JQUERY_UI_CSS);
    }
}
