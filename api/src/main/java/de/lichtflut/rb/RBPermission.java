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
package de.lichtflut.rb;

/**
 * <p>
 *  Enumeration of all permissions in Glasnost.
 * </p>
 *
 * <p>
 * 	Created Dec 23, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public enum RBPermission {
	
	LOGIN,
	
	ENTER_ADMIN_AREA,
	
	SEE_USERS,
	CREATE_USERS,
	DELETE_USERS,
    GRANT_ADMIN_ACCESS,
    GRANT_USER_ACCESS,
	
	SEE_NOTES,
	CREATE_NOTE,
	EDIT_NOTE,
	DELETE_NOTE,
	
	MANAGE_DOMAINS

}
