/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.apriori;

import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Implementations of this interface may add information to the model at startup, 
 *  that needs to be present a priori. 
 * </p>
 *
 * <p>
 * 	Created Sep 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface AprioriModeller {
	
	void run(ServiceProvider sp);

}
