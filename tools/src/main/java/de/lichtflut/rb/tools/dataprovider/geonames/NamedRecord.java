/*
 * Copyright 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.geonames;

/**
 * common record with ID and name.
 * 
 * Created: 26.05.2009
 *
 * @author Oliver Tigges
 */
public interface NamedRecord {

	/**
	 * @return the geonameid
	 */
	public abstract Long getGeonameid();

	public abstract String getName();

}