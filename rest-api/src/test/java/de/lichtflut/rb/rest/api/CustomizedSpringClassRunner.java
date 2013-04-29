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
