/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created May 9, 2012
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface RBOperation{

	OperationTypes.TYPE type();
}
