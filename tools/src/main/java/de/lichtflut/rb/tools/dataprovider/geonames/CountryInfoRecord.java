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

import java.math.BigInteger;

/**
 * Record representing country info from geonames.
 * 
 * Created: 22.05.2009
 *
 * @author Oliver Tigges
 */
public class CountryInfoRecord implements NamedRecord {
	
	private Long geonameid;
	private String iso2;
	private String iso3;
	private Integer isoNumeric;
	private String name;
	private String capital;
	private String continent;
	private BigInteger population;
	
	// -----------------------------------------------------
	
	public CountryInfoRecord() {
	}
	
	// -----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see de.lichtflut.ontology.dataprovider.geonames.GeonameRecord#getGeonameid()
	 */
	public Long getGeonameid() {
		return geonameid;
	}
	
	/**
	 * @param geonameid
	 * @return
	 */
	public CountryInfoRecord setGeonameid(String geonameid) {
		this.geonameid = Long.valueOf(geonameid);
		return this;
	}
	
	public String getIso2() {
		return iso2;
	}

	public CountryInfoRecord setIso2(String iso2) {
		this.iso2 = iso2;
		return this;
	}

	public String getIso3() {
		return iso3;
	}

	public CountryInfoRecord setIso3(String iso3) {
		this.iso3 = iso3;
		return this;
	}

	public Integer getIsoNumeric() {
		return isoNumeric;
	}

	public NamedRecord setIsoNumeric(Integer isoNumeric) {
		this.isoNumeric = isoNumeric;
		return this;
	}
	
	public CountryInfoRecord setIsoNumeric(String isoNumeric) {
		this.isoNumeric = Integer.parseInt(isoNumeric);
		return this;
	}
	
	public CountryInfoRecord setPopulation(String population) {
		this.population = new BigInteger(population);
		return this;
	}
	
	public BigInteger getPopulation() {
		return population;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.ontology.dataprovider.geonames.GeonameRecord#getName()
	 */
	public String getName() {
		return name;
	}

	public CountryInfoRecord setName(String name) {
		this.name = name;
		return this;
	}

	public String getCapital() {
		return capital;
	}

	public CountryInfoRecord setCapital(String capital) {
		this.capital = capital;
		return this;
	}

	public String getContinent() {
		return continent;
	}

	public CountryInfoRecord setContinent(String continent) {
		this.continent = continent;
		return this;
	}
	
	// -----------------------------------------------------

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(iso2 + "\t");
		sb.append(iso3 + "\t");
		sb.append(isoNumeric + "\t");
		sb.append(name + "\t");
		sb.append(capital + "\t");
		return sb.toString();
	}

	public String toDebugString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Iso2: " + iso2 + ", ");
		sb.append("Name: " + name + ", ");
		sb.append("Capital: " + capital + ", ");
		return sb.toString();
	}

}
