/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
