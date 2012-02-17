/*
 * Copyright 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.geonames;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;

import de.lichtflut.infra.Infra;
import de.lichtflut.infra.logging.StopWatch;
import de.lichtflut.rb.core.RB;
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
public class GeoNamesImporter {
	
	public static final int MIN_POPULATION = 900000;
	public static final int MIN_POPULATION_GERMANY = 90000;
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(GeoNamesImporter.class);
	
	private Map<Long, AlternateNameRecord> nameMapDE = Collections.emptyMap();
	private Map<Long, AlternateNameRecord> nameMapEN = Collections.emptyMap();
	
	private Map<String, ResourceNode> countryMap = new HashMap<String, ResourceNode>();
	
	// -----------------------------------------------------

	/**
	 * Constructor.
	 */
	public GeoNamesImporter() { }
	
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
				QualifiedName qn = new QualifiedName(Geonames.GEO_NAMESPACE_URI,
						Infra.toCamelCaseName(record.getName() + "_" + record.getGeonameid()));
				country = new SNResource(qn);
			}
			addName(country, record.getName());
			SNOPS.assure(country, RB.HAS_ISO_ALPHA2_CODE, new SNText(record.getIso2()));
			SNOPS.assure(country, RB.HAS_ISO_ALPHA3_CODE, new SNText(record.getIso3()));
			SNOPS.assure(country, RB.HAS_CONTINENT, getContinent(record));
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
				QualifiedName qn = new QualifiedName(Geonames.GEO_NAMESPACE_URI, name);
				ResourceNode city = new SNResource(qn);
				addName(city, record.getName());
				city.addAssociation(RDF.TYPE, RB.CITY);
				city.addAssociation(RB.HAS_POPULATION_SIZE, new SNScalar(record.getPopulation()));
				
				ResourceNode country = findCountryByIsoCode(record.getCountryCode());
				if (country != null){
					city.addAssociation(RB.HAS_COUNTRY, country);
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
				addName(entity, alternateDE.getName(), RB.LOCALE_GERMAN_CONTEXT);
			}
		}
		if (nameMapEN.containsKey(record.getGeonameid())){
			AlternateNameRecord alternateEN = nameMapEN.get(record.getGeonameid());
			if (!alternateEN.getName().equals(record.getName())){
				addName(entity, alternateEN.getName(), RB.LOCALE_ENGLISH_CONTEXT);
			}
		}
	}
	
	protected void addName(ResourceNode entity, String name, Context... language) {
		SNOPS.assure(entity, RB.HAS_NAME, name, language);
		SNOPS.associate(entity, RDFS.LABEL, new SNText(name), language);
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
		GeoNamesImporter importer = new GeoNamesImporter();
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			StopWatch sw = new StopWatch();
			//importer.readAlternateNames("input/temp/alternateNames.txt");
			SemanticGraph countries = importer.readCountries(cl.getResourceAsStream("countries.txt"));
			SemanticGraph cities = importer.readCities(cl.getResourceAsStream("cities15000.txt"));
			sw.displayNanoTime("Reading finished.");
			
			File targetDir = new File("target", "generated-rdf");
			targetDir.mkdirs();
			
			RdfXmlBinding binding = new RdfXmlBinding();
			binding.write(countries, new FileOutputStream(new File(targetDir, "Countries.rdf.xml")));
			binding.write(cities, new FileOutputStream(new File(targetDir, "Cities.rdf.xml")));
			sw.displayNanoTime("Writing finished.");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SemanticIOException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}

}
