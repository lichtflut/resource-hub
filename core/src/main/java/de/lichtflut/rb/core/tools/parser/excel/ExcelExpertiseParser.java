/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.tools.parser.excel;

import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
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
 *  Schema importer from EXCEL format.
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
							graph.addStatement(resource.addAssociation(RB.HAS_PARENT_NODE, parentResources[col - 1]));
							graph.addStatement(parentResources[col - 1].addAssociation(RB.HAS_CHILD_NODE, resource));
						}
						
					}
				}
			}
		} catch (BiffException e) {
			throw new IOException("Could not parse Excel Sheet", e);
		}
	}
}
