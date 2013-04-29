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
package de.lichtflut.rb.tools.dataprovider.geonames;

/**
 * Record representing alternate name.
 * 
 * Created: 25.05.2009
 *
 * @author Oliver Tigges 
 */
public class AlternateNameRecord {
	
	private Long geonameid;
	private Long alternateid;
	private String language;
	private String name;
	
	// ------------------------------------------------------
	
	public AlternateNameRecord(String alternateid, String geonameid, String language, String name) {
		this.geonameid = Long.parseLong(geonameid);
		this.alternateid = Long.parseLong(alternateid);
		this.language = language;
		this.name = name;
	}
	
	// ------------------------------------------------------
	
	public Long getGeonameid() {
		return geonameid;
	}

	public AlternateNameRecord setGeonameid(Long geonameid) {
		this.geonameid = geonameid;
		return this;
	}

	public AlternateNameRecord setGeonameid(String geonameid) {
		this.geonameid = Long.parseLong(geonameid);
		return this;
	}

	/**
	 * @return the alternateid
	 */
	public Long getAlternateid() {
		return alternateid;
	}
	
	/**
	 * @param alternateid the alternateid to set
	 */
	public AlternateNameRecord setAlternateid(Long alternateid) {
		this.alternateid = alternateid;
		return this;
	}
	
	/**
	 * @param alternateid the alternateid to set
	 */
	public AlternateNameRecord setAlternateid(String alternateid) {
		this.alternateid = Long.valueOf(alternateid);
		return this;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public AlternateNameRecord setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * @param language the language to set
	 */
	public AlternateNameRecord setLanguage(String language) {
		this.language = language;
		return this;
	}

	// ------------------------------------------------------
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(geonameid + "\t");
		sb.append(alternateid + "\t");
		sb.append(language + "\t");
		sb.append(name + "\t");
		return sb.toString();
	}

	public String toDebugString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GeoId: " + geonameid + ", ");
		sb.append("Name: " + name + ", ");
		sb.append("Language: " + language);
		return sb.toString();
	}

}
