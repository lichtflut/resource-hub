/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.general.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;

/**
 * <p>
 * This class provides a Excel-Parser for general purposes. It converts the Excel sheets to a
 * {@link SemanticGraph}.
 * <br/>ATTENTION: Works only with sheets > Excel 2002
 * </p>
 * Created: Mar 1, 2013
 * 
 * @author Ravi Knox
 */
public class ExcelParser {

	private static final String EXCEL_CONFIG = "rb-parser-config";

	private final Workbook workbook;
	private final ExcelParserMetaData metaData;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param file The excel sheet
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public ExcelParser(final File file) throws InvalidFormatException, IOException {
		workbook = WorkbookFactory.create(file);
		metaData = new ExcelParserMetaData(workbook.getSheet(EXCEL_CONFIG));
	}

	// ------------------------------------------------------

	/**
	 * Reads the given sheet and converts it into a graph.
	 * 
	 * @return a {@link SemanticGraph}
	 */
	public SemanticGraph read() {
		SemanticGraph graph = new DefaultSemanticGraph();
		// Excel version > 2002 specific cast. For lower versions use HSSFWorkbook
		int numberOfSheets = ((XSSFWorkbook) workbook).getNumberOfSheets();
		for (int pos = 0; pos < numberOfSheets; pos++) {
			insertIntoGraph(graph, workbook.getSheetAt(pos));
		}
		return graph;
	}

	// ------------------------------------------------------


	private void insertIntoGraph(final SemanticGraph graph, final Sheet sheet) {
		List<ResourceID> predicates = getPredicates(sheet);

	}

	/**
	 * @param sheet
	 * @return
	 */
	private List<ResourceID> getPredicates(final Sheet sheet) {
		// TODO Auto-generated method stub
		return null;
	}

}
