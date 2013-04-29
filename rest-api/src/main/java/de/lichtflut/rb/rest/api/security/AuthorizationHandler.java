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
package de.lichtflut.rb.rest.api.security;


import de.lichtflut.rb.core.security.RBUser;

/**
 * <p>
 * Basic interface to describe an AuthorizationHandler. The concrete
 * auth-mechanics, including ruleset, algorithms, ex- or internal configuration
 * stuff are implementation-specific
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created May 9, 2012
 */
public interface AuthorizationHandler {

	/**
	 * <p>
	 * This methods is very similar to
	 * {@link #isAuthorized(User user, String domainID, OperationTypes.TYPE type)}
	 * and does a check if the given user is authorized to process an operation
	 * on a specified domain with the exception, that there has to be no
	 * {@link OperationTypes.Type} explicitly given. This type can be annotated
	 * through an {@link RBOperation}. If you do so, please note that the
	 * annotated method has to call this method directly without doing some
	 * encapsulation stuff.
	 * </p>
	 * 
	 * @param user
	 * @param domainID
	 * @return true if the user is authorized or false if not. if the user or
	 *         the domain is null, also, false will be returned
	 * @throws MissingOperationTypeException
	 *             if no {@link OperationTypes.Type} is annotated
	 */
	@Deprecated
	public boolean isAuthorized(RBUser user, String domainID)
			throws MissingOperationTypeException;

	/**
	 * <p>
	 * This methods checks if a given user is authorized to process an operation
	 * on a specified domain
	 * </p>
	 * 
	 * @param user
	 * @param domainID
	 * @param type
	 * @return true if the user is authorized or false if not. if the user or
	 *         the domain is null, also, false will be returned
	 * @throws MissingOperationTypeException
	 *             if no {@link OperationTypes.Type} is given
	 */
	public boolean isAuthorized(RBUser user, String domainID,
			OperationTypes.TYPE type) throws MissingOperationTypeException;
}
