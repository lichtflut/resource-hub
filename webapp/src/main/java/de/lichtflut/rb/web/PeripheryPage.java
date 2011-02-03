/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.io.IOException;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.io.OntologyIOException;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.model.SemanticGraph;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Feb 1, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PeripheryPage extends WebPage {
	
	private final ArastrejuGate gate;
	
	// -----------------------------------------------------

	/**
	 * 
	 */
	public PeripheryPage(final PageParameters params) {
		super(params);
		
		gate = Arastreju.getInstance().rootContext();
		
	}
	
	// -----------------------------------------------------
	
	protected SemanticGraph loadGraph(){
		final SemanticGraphIO io = new RdfXmlBinding();
		try {
			final SemanticGraph graph = io.read(getClass().getClassLoader().getResourceAsStream("n9.common.rdf"));
			gate.startConversation().attach(graph);
			return graph;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (OntologyIOException e) {
			throw new RuntimeException(e);
		}
	}

}
