/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.ArastrejuGate;


/**
 * <p>
 *    Preinitializer, acts as a hook 
 * </p>
 *
 * @author Nils Bleisch
 */
public interface Preinitializer {

    void init(ArastrejuGate gate);
    
}
