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
