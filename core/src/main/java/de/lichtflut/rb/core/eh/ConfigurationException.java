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
package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Exception for configuration issues.
 * </p>
 *
 * <p>
 *  Created 02.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class ConfigurationException extends RBException {

    public ConfigurationException(final String msg) {
        super(msg);
    }

    public ConfigurationException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public ConfigurationException(int errorCode, String msg, Throwable e) {
        super(errorCode, msg, e);
    }
}
