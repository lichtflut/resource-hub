/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.devops;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.SemanticGraph;

import de.lichtflut.rb.tools.dataprovider.general.excel.ExcelParser;

/**
 * <p>
 * Generates rdf files based on Excel data.
 * </p>
 * Created: Mar 13, 2013
 *
 * @author Ravi Knox
 */
public class DevOpsITCatalog {

	public static void main(final String[] args) throws SemanticIOException, IOException, InvalidFormatException {
		File file = new File("src/main/resources/ITCatalog.xlsx");
		SemanticGraph graph = new ExcelParser(file).read();

		File targetDir = new File("target", "generated-rdf");
		targetDir.mkdirs();

		RdfXmlBinding binding = new RdfXmlBinding();
		binding.write(graph, new FileOutputStream(new File(targetDir, "ITCatalog.rdf.xml")));

		graph = new ExcelParser(new File("src/main/resources/ITCatalogPrototypes.xlsx")).read();
		binding.write(graph, new FileOutputStream(new File(targetDir, "ITCatalogPrototypes.rdf.xml")));
	}
}
