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

package de.lichtflut.rb.core.services;

import org.arastreju.sge.ArastrejuGate;

/**
 * <p>
 *  Initializer and validator of a domain.
 * </p>
 *
 * @author Oliver Tigges
 */
public interface DomainValidator {

    /**
     * Called before a newly created domain is used the first time.
     * @param gate The gate.
     * @param domainName The name of the domain.
     */
    void initializeDomain(ArastrejuGate gate, String domainName);

    /**
     * Called before the first usage of a domain after each application restart.
     * @param gate The gate.
     * @param domainName The name of the domain.
     */
    void validateDomain(ArastrejuGate gate, String domainName);
}
