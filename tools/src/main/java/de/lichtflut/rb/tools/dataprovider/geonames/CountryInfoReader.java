/*
 * Copyright 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.geonames;

import java.io.IOException;
import java.io.InputStream;

/**
 * Reader for country info.
 * 
 * Created: 22.05.2009
 * 
 * @author Oliver Tigges
 */
public class CountryInfoReader {

	public static final String FIELD_DELIM = "\t";

	private LineReader lineReader;

	// -----------------------------------------------------

	public CountryInfoReader(InputStream in) throws IOException {
		this.lineReader = new LineReader(in, "UTF-8");
	}

	// -----------------------------------------------------
	
	public boolean hasNext() throws IOException{
		if (lineReader.hasNext()){
			if (lineReader.nextLine().trim().startsWith("#")){
				return hasNext();
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * ISO	ISO3	ISO-Numeric	fips	Country	Capital	Area(in sq km)	Population	Continent	tld	CurrencyCode	CurrencyName	Phone	Postal Code Format	Postal Code Regex	Languages	geonameid	neighbours	EquivalentFipsCode
	 * @return
	 */
	@SuppressWarnings("unused")
	public CountryInfoRecord next() {
		String line = lineReader.nextLine();
		
		line = line.replaceAll(FIELD_DELIM, " " + FIELD_DELIM);
		String[] fields = line.split(FIELD_DELIM);
		int i = 0;
		String iso2 = fields[i++].trim();
		String iso3 = fields[i++].trim();
		String isoNumeric = fields[i++].trim();
		String fips = fields[i++].trim();
		String country = fields[i++].trim();
		String capital = fields[i++].trim();
		String area = fields[i++].trim();
		String population = fields[i++].trim();
		String continent = fields[i++].trim();
		String tld = fields[i++].trim();
		String currencyCode = fields[i++].trim();
		String currencyName = fields[i++].trim();
		String phone = fields[i++].trim();
		String postalCodeFormat = fields[i++].trim();
		String postalCodeRegex = fields[i++].trim();
		String languages = fields[i++].trim();
		String geonameid = fields[i++].trim();
		String neighbours = fields[i++].trim();
		return new CountryInfoRecord().setIso2(iso2).setIso3(iso3).setIsoNumeric(isoNumeric).setName(country)
			.setCapital(capital).setContinent(continent).setGeonameid(geonameid).setPopulation(population);
	}

}
