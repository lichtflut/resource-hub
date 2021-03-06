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
package de.lichtflut.rb.application.scripts;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;

import java.util.Collections;

/**
 * <p>
 *  Script support for JQuery and JQuery UI.
 * </p>
 *
 * <p>
 *  Created May 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class JQueryUiSupport implements ScriptSupport {

    public static final ResourceReference JQUERY_UI =
            new JavaScriptResourceReference(JQueryUiSupport.class, "jquery-ui-1.10.3.js") {
                @Override
                public Iterable<? extends HeaderItem> getDependencies() {
                    return Collections.<HeaderItem>singleton(
                            JavaScriptHeaderItem.forReference(JQueryResourceReference.get()));
                }
            };

    // ----------------------------------------------------

    @Override
    public void addScripts(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem.forReference(JQUERY_UI));
    }
}
