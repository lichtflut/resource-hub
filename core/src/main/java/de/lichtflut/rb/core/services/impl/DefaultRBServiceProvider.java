/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ArastrejuProfile;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 * Default implementation of {@link ServiceProvider}.
 * TODO: The rootContext of ArastrejuGate is used, this should be changed
 * </p>
 *
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 */
public class DefaultRBServiceProvider extends AbstractServiceProvider implements ServiceProvider {

    private final ArastrejuGate gate;
	private final DomainOrganizerImpl organizer;
    

    // --CONSTRUCTOR----------------------------------------

    /**
     * Constructor.
     * @param config The RBConfig.
     */
    public DefaultRBServiceProvider(final RBConfig config) {
    	super(new ServiceContext(config));
        final ArastrejuProfile profile = config.getArastrejuConfiguration();
        gate = Arastreju.getInstance(profile).rootContext();
        organizer = new DomainOrganizerImpl(this);
    }

    // -----------------------------------------------------

    /**
     *{@inheritDoc}
     */
    @Override
    public ArastrejuGate getArastejuGate() {
        return gate;
    }
    
    /** 
     * {@inheritDoc}
     */
    @Override
    public DomainOrganizer getDomainOrganizer() {
    	return organizer;
    }

}
