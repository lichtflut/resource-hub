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
package de.lichtflut.rb.core.system;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.DomainValidator;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.organize.Organizer;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.DC;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  Default validator for domains. Assures namespaces and contexts set.
 * </p>
 * 
 * <p>
 *  Created Jan 10, 2012
 * </p>
 * 
 * @author Oliver Tigges
 */
public class DefaultDomainValidator implements DomainValidator {

	private final static Logger LOGGER = LoggerFactory.getLogger(DefaultDomainValidator.class);

    private final Set<String> validated = new HashSet<String>();

	// ----------------------------------------------------

    @Override
	public void initializeDomain(ArastrejuGate gate, String domainName) {
        validateDomain(gate, domainName);
	}

    @Override
    public void validateDomain(ArastrejuGate gate, String domainName) {
        if (validated.contains(domainName)) {
            // already validated since start of application
            return;
        }

        LOGGER.info("Running validation for domain " + domainName);
        final Organizer organizer = new Organizer(gate);

        // The protected context of this domain
        organizer.registerContext(new QualifiedName(Namespace.LOCAL_CONTEXTS, domainName));

        organizer.registerContext(RBSystem.VIEW_SPEC_CTX.getQualifiedName());

        organizer.registerContext(RBSystem.TYPE_SYSTEM_CTX.getQualifiedName());

        organizer.registerNamespace(RDF.NAMESPACE_URI, "rdf");
        organizer.registerNamespace(RDFS.NAMESPACE_URI, "rdfs");

        organizer.registerNamespace(Aras.NAMESPACE_URI, "aras");
        organizer.registerNamespace(DC.NAMESPACE_URI, "dc");
        organizer.registerNamespace(RBSystem.SYS_NAMESPACE_URI, "rb");
        organizer.registerNamespace(RB.COMMON_NAMESPACE_URI, "common");

        validated.add(domainName);
    }

}
