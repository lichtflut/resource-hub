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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.Infra;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.apriori.Geonames;

/**
 * <p>
 * 	Imports geonames into arastreju ontology.
 * <p>
 * 
 * <p>
 *	 Created: 22.05.2009
 * </p>
 *
 * @author Oliver Tigges
 */
public class GeoNamesProvider {
	
	public static final int MIN_POPULATION = 200000;
	public static final int MIN_POPULATION_GERMANY = 1000;
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(GeoNamesProvider.class);
	
	private Map<Long, AlternateNameRecord> nameMapDE = Collections.emptyMap();
	private Map<Long, AlternateNameRecord> nameMapEN = Collections.emptyMap();
	
	private Map<String, ResourceNode> countryMap = new HashMap<String, ResourceNode>();
	
	// -----------------------------------------------------

	/**
	 * Constructor.
	 */
	public GeoNamesProvider() { }
	
	// -----------------------------------------------------
	
	public void readAlternateNames(final String file) throws IOException{
		AlternateNamesReader reader = new AlternateNamesReader(file);
		nameMapDE = reader.mapForLanguage("de");
		reader.reset();
		nameMapEN = reader.mapForLanguage("en");
		logger.debug("builded nameMap for DE");
	}
	
	public SemanticGraph readCountries(final InputStream in) throws IOException{
		SemanticGraph graph = new DefaultSemanticGraph();
		CountryInfoReader reader = new CountryInfoReader(in);
		while (reader.hasNext()){
			CountryInfoRecord record = reader.next();
			ResourceNode country = findCountryByIsoCode(record.getIso2());
			if (country == null){
				QualifiedName qn = QualifiedName.from(Geonames.GEO_NAMESPACE_URI,
						Infra.toCamelCaseName(record.getName() + "_" + record.getGeonameid()));
				country = new SNResource(qn);
			}
			SNOPS.assure(country, RB.HAS_NAME, record.getName());
			addLabel(country, record.getName());
			SNOPS.associate(country, RDF.TYPE, RB.COUNTRY);
			SNOPS.associate(country, RDF.TYPE, RBSystem.ENTITY);
			SNOPS.assure(country, RB.HAS_ISO_ALPHA2_CODE, new SNText(record.getIso2()));
			SNOPS.assure(country, RB.HAS_ISO_ALPHA3_CODE, new SNText(record.getIso3()));
			SNOPS.assure(country, RB.IS_IN_CONTINENT, getContinent(record));
			SNOPS.assure(country, RB.HAS_POPULATION_SIZE, new SNScalar(record.getPopulation()));
			addAlternateNames(record, country);
			logger.info("created country: " + country);
			graph.addStatements(country.getAssociations());
			countryMap.put(record.getIso2(), country);
		}
		return graph;
	}

	public SemanticGraph readCities(final InputStream in) throws IOException {
		SemanticGraph graph = new DefaultSemanticGraph();
		GeoNamesReader reader = new GeoNamesReader(in);
		while (reader.hasNext()){
			GeonamesRecord record = reader.next();
			long population = record.getPopulation().longValue();
			boolean isGerman = "DE".equals(record.getCountryCode());
			if (population > MIN_POPULATION || (isGerman && population > MIN_POPULATION_GERMANY)){
				final String name = Infra.toCamelCaseName(record.getNormalized() + "_" + record.getGeonameid());
				QualifiedName qn = QualifiedName.from(Geonames.GEO_NAMESPACE_URI, name);
				ResourceNode city = new SNResource(qn);
				addLabel(city, record.getName());
				SNOPS.assure(city, RB.HAS_NAME, record.getName());
				SNOPS.associate(city, RDF.TYPE, RB.CITY);
				SNOPS.associate(city, RDF.TYPE, RBSystem.ENTITY);
				city.addAssociation(RDF.TYPE, RB.CITY);
				city.addAssociation(RB.HAS_POPULATION_SIZE, new SNScalar(record.getPopulation()));
				
				ResourceNode country = findCountryByIsoCode(record.getCountryCode());
				if (country != null){
					city.addAssociation(RB.IS_IN_COUNTRY, country);
				}
				addAlternateNames(record, city);
				graph.addStatements(city.getAssociations());
				logger.info("created city: " + city);
			}
		}
		return graph;
	}

	// -----------------------------------------------------
	
	protected ResourceNode resolveCountry(String isoAlpha2Code) {
		return null;
	}
	
	// ----------------------------------------------------
	
	protected ResourceNode findCountryByIsoCode(String isoAlpha2Code){
		if (countryMap.containsKey(isoAlpha2Code)) {
			return countryMap.get(isoAlpha2Code);
		}
		ResourceNode node = resolveCountry(isoAlpha2Code);
		if (node != null) {
			countryMap.put(isoAlpha2Code, node);
		}
		return node;
	}
	
	private void addAlternateNames(NamedRecord record, ResourceNode entity) {
		if (nameMapDE.containsKey(record.getGeonameid())){
			AlternateNameRecord alternateDE = nameMapDE.get(record.getGeonameid());
			if (!alternateDE.getName().equals(record.getName())){
				addLabel(entity, alternateDE.getName(), Locale.GERMAN);
			}
		}
		if (nameMapEN.containsKey(record.getGeonameid())){
			AlternateNameRecord alternateEN = nameMapEN.get(record.getGeonameid());
			if (!alternateEN.getName().equals(record.getName())){
				addLabel(entity, alternateEN.getName(), Locale.ENGLISH);
			}
		}
	}
	
	protected void addLabel(ResourceNode entity, String name) {
		SNOPS.associate(entity, RDFS.LABEL, new SNText(name));
	}
	
	protected void addLabel(ResourceNode entity, String name, Locale locale) {
		SNOPS.associate(entity, RDFS.LABEL, new SNText(name, locale));
	}

	private ResourceID getContinent(final CountryInfoRecord record) {
		if ("EU".equals(record.getContinent())){
			return Geonames.EUROPE;
		} else if ("NA".equals(record.getContinent())){
			return Geonames.NORTH_AMERICA;
		} else if ("SA".equals(record.getContinent())){
			return Geonames.SOUTH_AMERICA;
		} else if ("AF".equals(record.getContinent())){
			return Geonames.AFRICA;
		} else if ("AS".equals(record.getContinent())){
			return Geonames.ASIA;
		} else if ("OC".equals(record.getContinent())){
			return Geonames.OCEANIA;
		} else if ("AN".equals(record.getContinent())){
			return Geonames.ANTARCTICA;
		} else {
			throw new IllegalArgumentException("Unknown continent: " + record.getContinent());
		}
	}
	
	// -----------------------------------------------------
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeoNamesProvider importer = new GeoNamesProvider();
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			importer.readAlternateNames("/Users/otigges/temp/alternateNames/alternateNames-filtered.txt");
			SemanticGraph countries = importer.readCountries(cl.getResourceAsStream("countries.txt"));
			SemanticGraph cities = importer.readCities(cl.getResourceAsStream("cities1000.txt"));

			File targetDir = new File("target", "generated-rdf");
			targetDir.mkdirs();
			
			RdfXmlBinding binding = new RdfXmlBinding();
			binding.write(countries, new FileOutputStream(new File(targetDir, "Countries.rdf.xml")));
			binding.write(cities, new FileOutputStream(new File(targetDir, "Cities.rdf.xml")));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SemanticIOException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}

}
