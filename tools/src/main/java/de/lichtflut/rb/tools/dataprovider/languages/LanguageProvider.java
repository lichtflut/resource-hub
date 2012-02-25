/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.languages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.apriori.Languages;

/**
 * <p>
 *  Provider for languages, based on java.util.Locale.
 * </p>
 *
 * <p>
 * 	Created Feb 23, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class LanguageProvider {
	
	public SemanticGraph getLanguages() {
		final SemanticGraph graph = new DefaultSemanticGraph();
		
		final Map<String, Locale> languageMap = new HashMap<String, Locale>();
		
		for (Locale locale : Locale.getAvailableLocales()) {
			if (locale.getCountry().isEmpty()) {
				languageMap.put(locale.getLanguage(), locale);
			}
		}
		
		for (String key : languageMap.keySet()) {
			final Locale locale = languageMap.get(key);
			final SNResource language = createResource(locale);
			
			graph.addStatements(language.getAssociations());
		}
		return graph;
	}

	protected SNResource createResource(final Locale locale) {
		final String name = locale.getDisplayLanguage();
		final QualifiedName qn = new QualifiedName(Languages.LANGUAGE_NAMESPACE_URI, name);
		final SNResource language = new SNResource(qn);
		language.addAssociation(RDF.TYPE, RB.LANGUAGE);
		
		addLabel(language, locale, Locale.ENGLISH);
		addLabel(language, locale, Locale.GERMAN);
		addLabel(language, locale, Locale.FRENCH);
		addLabel(language, locale, Locale.ITALY);
		addLabel(language, locale, Locale.JAPANESE);
		addLabel(language, locale, Locale.CHINESE);
		addLabel(language, locale, new Locale("es"));
		return language;
	}
	
	private void addLabel(SNResource language, Locale locale, Locale labelLanguage) {
		language.addAssociation(RDFS.LABEL, new SNText(locale.getDisplayName(labelLanguage), labelLanguage));
	}
	
	// ----------------------------------------------------
	
	public static void main(String[] args) throws SemanticIOException, IOException {
		SemanticGraph languages = new LanguageProvider().getLanguages();
		
		File targetDir = new File("target", "generated-rdf");
		targetDir.mkdirs();
		
		RdfXmlBinding binding = new RdfXmlBinding();
		binding.write(languages, new FileOutputStream(new File(targetDir, "Languages.rdf.xml")));
		
	}

}
