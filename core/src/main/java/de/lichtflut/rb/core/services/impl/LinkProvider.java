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
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 * This class provides some methods to create URIs of {@link RBEntity}s.
 * </p>
 * Created: Aug 15, 2012
 *
 * @author Ravi Knox
 */
public class LinkProvider {

	// ------------------------------------------------------

	public static String buildRepositoryStructureFor(final RBEntity entity, final QualifiedName rbField, final String fileName) {
		StringBuilder path = new StringBuilder();
		path.append(entity.getType().toURI());
		path.append("/" + entity.getID().toURI());
		path.append("/" + rbField);

		String s = path.toString();
		s = s.replace("http://", "").replace(".", "/").replace("#", "/");
		s = s.concat("/" + fileName);
		return s;
	}

	/**
	 * Returns only the part after the last "/" of a id.
	 * @param path
	 * @return
	 */
	public static String getSimpleNameFor(final String path){
		if(null == path || path.isEmpty()){
			return "";
		}
		String[] strings = path.split("/");
		if(null == strings || strings.length == 0){
			return path;
		}
		return strings[strings.length-1];
	}

}
