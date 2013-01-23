/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lichtflut.rb.core.config.RBConfig;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 9, 2013
 */


public class CustomizedSpringClassRunner extends SpringJUnit4ClassRunner{

	static {
		System.setProperty(RBConfig.DOMAIN_WORK_DIRECTORY,
				"target/test/storage/workdir");
	}

	/**
	 * @param clazz
	 * @throws InitializationError
	 */
	public CustomizedSpringClassRunner(Class<?> clazz)
			throws InitializationError {
		super(clazz);
	}

}
