package de.lichtflut.rb.rest.api;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.DomainInitializer;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.Organizer;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.DC;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.spi.GateContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Oliver Tigges
 */
public class TestDomainInitializer implements DomainInitializer {

    @Autowired
    private AuthModule module;

    // ----------------------------------------------------

    @Override
    public void initializeDomain(ArastrejuGate gate, String domainName) {

        final Organizer organizer = gate.getOrganizer();
        organizer.registerContext(RB.DOMAIN_CONTEXT.getQualifiedName());
        organizer.registerContext(RB.SCHEMA_CONTEXT.getQualifiedName());
        organizer.registerContext(RB.PRIVATE_CONTEXT.getQualifiedName());
        organizer.registerContext(RB.PUBLIC_CONTEXT.getQualifiedName());
        organizer.registerNamespace(RDF.NAMESPACE_URI, "rdf");
        organizer.registerNamespace(RDFS.NAMESPACE_URI, "rdfs");
        organizer.registerNamespace(Aras.NAMESPACE_URI, "aras");
        organizer.registerNamespace(DC.NAMESPACE_URI, "dc");
        organizer.registerNamespace(RBSystem.SYS_NAMESPACE_URI, "rb");
        organizer.registerNamespace(RB.COMMON_NAMESPACE_URI, "common");


        //If there is no master domain, create one
        if (module.getDomainManager().findDomain(GateContext.MASTER_DOMAIN)==null) {
            RBDomain domain = new RBDomain();
            domain.setName(GateContext.MASTER_DOMAIN);
            module.getDomainManager().registerDomain(domain);
        }

        RBUser root = module.getUserManagement().findUser("root");
        if (root == null) {
            root = new RBUser(new SimpleResourceID().getQualifiedName());
            root.setUsername("root");
            root.setEmail("root@root.de");
            try {
                module.getUserManagement().registerUser(root,
                        RBCrypt.encrypt("root"), GateContext.MASTER_DOMAIN);
            } catch (RBAuthException e) {
                e.printStackTrace();
            }
        }
        // module.getUserManagement().setUserRoles(root, null, roles);

    }
}
