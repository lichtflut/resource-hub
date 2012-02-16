/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.tools.parser.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.Assert;

import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.SemanticGraph;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.rb.core.RB;

/**
 * <p>
 *  Test Cases for ExcelExpertiseParser.
 * </p>
 *
 * <p>
 * 	Created Feb 16, 2012 (Weiberfastnacht)
 * </p>
 *
 * @author Erik Aderhold
 */
public class ExcelExpertiseParserTest {
	
	private final int startRow = 1;
	private final int startColumn = 1;
	
	// -----------------------------------------------------
	
	@Test
	public void testExcelImport() throws IOException {
		final InputStream in = 
				Thread.currentThread().getContextClassLoader().getResourceAsStream("expertise-taxonomy.xls");
		final ExcelExpertiseParser parser = new ExcelExpertiseParser(RB.EXPERTISE, startRow, startColumn);
		final SemanticGraph result = parser.parse(in);
		Assert.assertEquals(1405, result.getNodes().size());
		
		// Anzahl einzelner Expertisen !!!
		Assert.assertEquals(709, result.getSubjects().size());
	}

	// -----------------------------------------------------
	
	@Ignore
	@Test
	public void exportToRDF() throws IOException, SemanticIOException {
		final InputStream in = 
				Thread.currentThread().getContextClassLoader().getResourceAsStream("expertise-taxonomy.xls");
		final ExcelExpertiseParser parser = new ExcelExpertiseParser(RB.EXPERTISE, startRow, startColumn);
		final SemanticGraph graph = parser.parse(in);
		
		OutputStream out = new FileOutputStream("src/test/resources/expertise-taxonomy.rdf.xml");
		
		new RdfXmlBinding().write(graph, out);
	}
	
}
