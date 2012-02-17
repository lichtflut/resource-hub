/*
 * Copyright 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.geonames;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * record representing one geoname: www.geonames.org
 * 
 * The main 'geoname' table has the following fields :
 * --------------------------------------------------- geonameid : integer id of
 * record in geonames database name : name of geographical point (utf8)
 * varchar(200) asciiname : name of geographical point in plain ascii
 * characters, varchar(200) alternatenames : alternatenames, comma separated
 * varchar(4000) (varchar(5000) for SQL Server) latitude : latitude in decimal
 * degrees (wgs84) longitude : longitude in decimal degrees (wgs84) feature
 * class : see http://www.geonames.org/export/codes.html, char(1) feature code :
 * see http://www.geonames.org/export/codes.html, varchar(10) country code :
 * ISO-3166 2-letter country code, 2 characters cc2 : alternate country codes,
 * comma separated, ISO-3166 2-letter country code, 60 characters admin1 code :
 * fipscode (subject to change to iso code), isocode for the us and ch, see file
 * admin1Codes.txt for display names of this code; varchar(20) admin2 code :
 * code for the second administrative division, a county in the US, see file
 * admin2Codes.txt; varchar(80) admin3 code : code for third level
 * administrative division, varchar(20) admin4 code : code for fourth level
 * administrative division, varchar(20) population : bigint (4 byte int)
 * elevation : in meters, integer gtopo30 : average elevation of 30'x30' (ca
 * 900mx900m) area in meters, integer timezone : the timezone id (see file
 * timeZone.txt) modification date : date of last modification in yyyy-MM-dd
 * format
 * 
 * Created: 22.05.2009
 * 
 * @author Oliver Tigges
 */
public class GeonamesRecord implements NamedRecord {

	private Long geonameid;
	private String name;
	private String normalized;
	private String countryCode;
	private BigInteger population;
	private Set<String> alternateNames = new HashSet<String>();

	// -----------------------------------------------------
	
	public GeonamesRecord() {
	}

	// -----------------------------------------------------

	public Long getGeonameid() {
		return geonameid;
	}

	public GeonamesRecord setGeonameid(Long geonameid) {
		this.geonameid = geonameid;
		return this;
	}

	public GeonamesRecord setGeonameid(String geonameid) {
		this.geonameid = Long.parseLong(geonameid);
		return this;
	}

	public String getName() {
		return name;
	}

	public GeonamesRecord setName(String name) {
		this.name = name;
		return this;
	}
	
	public GeonamesRecord setNormalized(String normalized) {
		this.normalized = normalized;
		return this;
	}

	public String getNormalized() {
		return normalized;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public GeonamesRecord setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		return this;
	}
	
	/**
	 * @param population the population to set
	 */
	public GeonamesRecord setPopulation(String populationString) {
		this.population = new BigInteger(populationString);
		return this;
	}
	
	/**
	 * @return the population
	 */
	public BigInteger getPopulation() {
		return population;
	}

	public Set<String> getAlternateNames() {
		return alternateNames;
	}
	
	public String getAlternateNamesCommaSeperated() {
		StringBuffer sb = new StringBuffer();
		Iterator<String> iter = alternateNames.iterator();
		while (iter.hasNext()){
			sb.append(iter.next());
			if (iter.hasNext()){
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public GeonamesRecord setAlternateNames(String alternateNames) {
		String[] names = alternateNames.split(",");
		this.alternateNames.addAll(Arrays.asList(names));
		return this;
	}
	
	// -----------------------------------------------------
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(geonameid + "\t");
		sb.append(name + "\t");
		sb.append(normalized + "\t");
		sb.append(getAlternateNamesCommaSeperated() + "\t");
		sb.append(getCountryCode() + "\t");
		return sb.toString();
	}

	public String toDebugString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GeonameID: " + geonameid + ", ");
		sb.append("Name: " + name + ", ");
		sb.append("NormalizedName: " + name + ", ");
		sb.append("AlternateNames: " + getAlternateNamesCommaSeperated() + ", ");
		sb.append("Country-Code: " + getCountryCode() + ", ");
		return sb.toString();
	}

}
