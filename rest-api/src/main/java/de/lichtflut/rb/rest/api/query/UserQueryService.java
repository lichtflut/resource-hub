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
package de.lichtflut.rb.rest.api.query;

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.SearchResult;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Resource for querying of users.
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("query/domains/{domain}/users")
public class UserQueryService extends RBServiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserQueryService.class);

    // ----------------------------------------------------
		
	public UserQueryService(){}

    // ----------------------------------------------------
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ResultItemRVO> search(
            @QueryParam(value="term") String term,
            @CookieParam(value=AuthModule.COOKIE_SESSION_AUTH) String token) throws UnauthenticatedUserException {

        RBUser user = authenticateUser(token);

        LOGGER.info("{} searched users {}.", user, term);

		final SearchResult<RBUser> searchResult = authModule.getUserManagement().searchUsers(term);
		final List<RBUser> subList = searchResult.toList(20);
		final List<ResultItemRVO> rvoList = new ArrayList<ResultItemRVO>(subList.size());
		for (RBUser rbUser : subList) {
			final ResultItemRVO item = new ResultItemRVO();
			item.setId(rbUser.getQualifiedName().toURI());
			item.setLabel(rbUser.getName());
			item.setInfo(rbUser.getName());
			rvoList.add(item);
		}
		return rvoList;
	}
	
}
