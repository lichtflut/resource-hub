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
package de.lichtflut.rb.tools.dataprovider.expertises;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.logging.StopWatch;
import de.lichtflut.rb.core.RB;

/**
 * <p>
 *  Imports Expertises from an EXCEL file.
 * </p>
 *
 * <p>
 * 	Created 15.02.2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class ExcelExpertiseParser {

	private final Logger logger = LoggerFactory.getLogger(ExcelExpertiseParser.class);
	
	private final SemanticGraph graph = new DefaultSemanticGraph();
	
	private ResourceID type;
	private int startRow = 0;
	private int startColumn = 0;
	
	private boolean closed; 
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param type The ResourceType described in the excel-file.
	 * @param startRow The row where the data part begins (0 is first row)
	 * @param startColumn The column where the data part begins (0 is first column)
	 */
	public ExcelExpertiseParser(ResourceID type, int startRow, int startColumn) {
		this.type = type;
		this.startRow = startRow;
		this.startColumn = startColumn;
	}

	// -----------------------------------------------------
	
	/** 
	 * Parse excel to graph
	 */
	public SemanticGraph parse(InputStream in) throws IOException {
		if (closed) {
			throw new IllegalStateException("Parser already closed.");
		} else {
			closed = true;
		}
		final StopWatch sw = new StopWatch();
		
		read(in);
		
		logger.debug("parsed {} in {} micros", new Object[] {graph, sw.getTime()});
		return graph;
	}
	
	// -----------------------------------------------------
	
	private void read(InputStream in) throws IOException  {
		try {
			Workbook w = Workbook.getWorkbook(in);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			
			SNResource[] parentResources = new SNResource[sheet.getColumns()];
			
			for (int row = startRow; row < sheet.getRows(); row++) {
				Cell[] cells = sheet.getRow(row);
				for (int col = startColumn; col < cells.length; col++) {
					Cell cell = cells[col];
					if(!cell.getContents().isEmpty()) {
						SNResource resource = new SNResource();
						graph.addStatement(resource.addAssociation(RB.HAS_NAME,
								new SNValue(ElementaryDataType.STRING, cell.getContents())));
						graph.addStatement(resource.addAssociation(RDFS.LABEL,
								new SNValue(ElementaryDataType.STRING, cell.getContents())));
						
						graph.addStatement(resource.addAssociation(RDF.TYPE, type));
						parentResources[col] = resource;
						
						if(col != startColumn && parentResources[col - 1] != null) {
							graph.addStatement(resource.addAssociation(Aras.HAS_PARENT_NODE, parentResources[col - 1]));
							graph.addStatement(parentResources[col - 1].addAssociation(Aras.HAS_CHILD_NODE, resource));
						}
						
					}
				}
			}
		} catch (BiffException e) {
			throw new IOException("Could not parse Excel Sheet", e);
		}
	}
	
	public static void main(String[] args) {
		int startRow = 1;
		int startColumn = 1;
		
		final InputStream in = 
				Thread.currentThread().getContextClassLoader().getResourceAsStream("expertise-taxonomy.xls");
		final ExcelExpertiseParser parser = new ExcelExpertiseParser(RB.EXPERTISE, startRow, startColumn);
		SemanticGraph graph;
		try {
			graph = parser.parse(in);
			OutputStream out = new FileOutputStream("src/main/resources/expertise-taxonomy.rdf.xml");
			new RdfXmlBinding().write(graph, out);
			System.out.println("SUCCESS: " +graph.getSubjects().size()
					+" Expertises imported from excel-file and exported to rdf-file.");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SemanticIOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
