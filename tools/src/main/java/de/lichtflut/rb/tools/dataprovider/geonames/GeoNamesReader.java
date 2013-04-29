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

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 	Reader for geonames.
 * </p>
 * 
 * Created: 22.05.2009
 * 
 * @author Oliver Tigges
 */
public class GeoNamesReader {

	public static final String FIELD_DELIM = "\t";

	private LineReader lineReader;

	// -----------------------------------------------------

	public GeoNamesReader(String file) throws IOException {
		this.lineReader = new LineReader(file, "UTF-8");
	}
	
	public GeoNamesReader(InputStream in) throws IOException {
		this.lineReader = new LineReader(in, "UTF-8");
	}

	// -----------------------------------------------------
	
	public boolean hasNext() throws IOException{
		return lineReader.hasNext();
	}

	/**
	 * EXAMPLE of cities15000.txt:
	 * 
	 * 2950438	Bergheim	Bergheim	Bergheim an der Erft	50.9557234753678	6.63986206054688	P	PPL	DE		07	DE.NW.KL			63558		72	Europe/Berlin	2008-01-17
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public GeonamesRecord next() {
		String line = lineReader.nextLine();
		
		String[] fields = line.split(FIELD_DELIM);
		int i = 0;
		String geonameid = fields[i++];
		String name = fields[i++];
		String asciiname = fields[i++];
		String alternatenames = fields[i++];
		String latitude = fields[i++];
		String longitude = fields[i++];
		String featureClass = fields[i++];
		String featureCode = fields[i++];
		String countryCode = fields[i++];
		String cc2 = fields[i++];
		String admin1Code = fields[i++];
		String admin2Code = fields[i++];
		String admin3Code = fields[i++];
		String admin4Code = fields[i++];
		String population = fields[i++];
		String elevation = fields[i++];
		String gtopo30 = fields[i++];
		String timezone = fields[i++];
		String modificationDate = fields[i++];
		return new GeonamesRecord().setGeonameid(geonameid).setPopulation(population).
			setName(name).setNormalized(asciiname).setCountryCode(countryCode).setAlternateNames(alternatenames);
	}

	// -----------------------------------------------------

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GeoNamesReader gnr = new GeoNamesReader("input/cities15000.txt");
			while (gnr.hasNext()){
				System.out.println(gnr.next().toDebugString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
