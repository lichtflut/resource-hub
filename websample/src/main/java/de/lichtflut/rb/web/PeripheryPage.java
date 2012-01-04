/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.io.JsonBinding;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.model.SemanticGraph;

/**
 * <p>
 *  [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created Feb 1, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class PeripheryPage extends WebPage {

	private final ArastrejuGate gate;

	// -----------------------------------------------------

	/**
	 * @param params /
	 */
	public PeripheryPage(final PageParameters params) {
		super(params);

		gate = Arastreju.getInstance().rootContext();

		SemanticGraph g = loadGraph();
		final StringBuilder jsonTree = new StringBuilder("graph = [ ");

		JsonBinding binding = new JsonBinding();
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			binding.write(g, os);
			jsonTree.append(os.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SemanticIOException e) {
			e.printStackTrace();
		}

		jsonTree.append("];");

		add(new Label("jsonTree", Model.of(jsonTree.toString())).setEscapeModelStrings(false));

	}

	// -----------------------------------------------------
	/**
	 *@return /
	 */
	protected SemanticGraph loadGraph(){
		final SemanticGraphIO io = new RdfXmlBinding();
		try {
			final SemanticGraph graph = io.read(getClass().getClassLoader().getResourceAsStream("n9.common.rdf"));
			gate.startConversation().attach(graph);
			return graph;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SemanticIOException e) {
			throw new RuntimeException(e);
		}
	}

}
