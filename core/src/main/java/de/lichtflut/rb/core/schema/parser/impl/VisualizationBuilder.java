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
package de.lichtflut.rb.core.schema.parser.impl;

import de.lichtflut.rb.core.schema.model.VisualizationInfo;
import de.lichtflut.rb.core.schema.model.impl.PlainVisualizationInfo;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Creator of VisualizationInfo's.
 * </p>
 *
 * <p>
 *  Created 17.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class VisualizationBuilder {

    public static final String EMBEDDED = "embedded";

    public static final String FLOATING = "floating";

    public static final String STYLE = "style";

    // ----------------------------------------------------

    private static final Logger LOGGER = LoggerFactory.getLogger(VisualizationBuilder.class);

    // ----------------------------------------------------

    public void add(PropertyDeclarationImpl decl, String key, String value) {
        LOGGER.debug("Setting visualization info {} = {} to decl " + decl.getPropertyDescriptor(), key, value);
        PlainVisualizationInfo plainVisInfo = getPlainVisInfo(decl);
        if (EMBEDDED.equals(key)) {
            plainVisInfo.setEmbedded(parseBoolean(value));
        } else if (FLOATING.equals(key)) {
            plainVisInfo.setFloating(parseBoolean(value));
        } else if (STYLE.equals(key)) {
            plainVisInfo.setStyle(removeQuotes(value));
        } else {
            throw new IllegalArgumentException("Unknown key for visualization: " + key);
        }
    }

    protected boolean parseBoolean(String in) {
        String plain = removeQuotes(in);
        return "true".equalsIgnoreCase(plain) || "yes".equalsIgnoreCase(plain);
    }

    protected String removeQuotes(String in) {
        if (in == null || in.length() == 0) {
            return "";
        }
        return in.replaceAll("^\"|\"$", "");
    }

    private PlainVisualizationInfo getPlainVisInfo(PropertyDeclarationImpl decl) {
        VisualizationInfo visualizationInfo = decl.getVisualizationInfo();
        if (visualizationInfo instanceof PlainVisualizationInfo) {
            return (PlainVisualizationInfo) visualizationInfo;
        } else if (visualizationInfo == null) {
            PlainVisualizationInfo plainInfo = new PlainVisualizationInfo();
            decl.setVisualizationInfo(plainInfo);
            return plainInfo;
        } else {
            PlainVisualizationInfo plainInfo = new PlainVisualizationInfo(visualizationInfo);
            decl.setVisualizationInfo(plainInfo);
            return plainInfo;
        }

    }

}
