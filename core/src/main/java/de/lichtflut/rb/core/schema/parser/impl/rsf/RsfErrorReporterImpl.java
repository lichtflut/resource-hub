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
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;

/**
 * <p>
 *  Error reporter for RSF parsing.
 * </p>
 *
 * <p>
 * Created 19.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class RsfErrorReporterImpl implements RSErrorReporter {

    private ParsedElements result;

    // ----------------------------------------------------

    public RsfErrorReporterImpl(ParsedElements result) {
        this.result = result;
    }

    // ----------------------------------------------------

    @Override
    public void reportError(String error) {
        result.addError(error);
    }

    @Override
    public boolean hasErrorReported() {
        return !result.getErrorMessages().isEmpty();
    }
}
