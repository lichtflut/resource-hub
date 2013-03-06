/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.general.excel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.DetachedStatement;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.logging.StopWatch;

/**
 * <p>
 * This class provides a Excel-Parser for general purposes. It converts the Excel sheets to a
 * {@link SemanticGraph}. <br/>
 * ATTENTION: Works only with sheets > XML-based Excel sheets (MS Excel > 2002)
 * </p>
 * Created: Mar 1, 2013
 * 
 * @author Ravi Knox
 */
// TODO refactor all sheet based operation to ExcelParserTools
public class ExcelParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelParser.class);

	private static final String EXCEL_CONFIG = "rb-parser-config";
	private static final String PREFIX = "has";

	private final Workbook workbook;
	private final ExcelParserMetaData metaData;
	private Map<String, ResourceID> foreignKeys;
	private SemanticGraph graph;

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
	 * Read the given sheet and convert it into a graph.
	 * 
	 * @return a {@link SemanticGraph}
	 */
	public SemanticGraph read() {
		graph = new DefaultSemanticGraph();
		foreignKeys = new HashMap<String, ResourceID>();
		// Excel version > 2002 specific cast. For lower versions use HSSFWorkbook
		int numberOfSheets = ((XSSFWorkbook) workbook).getNumberOfSheets();
		StopWatch watch = new StopWatch();
		for (int pos = 0; pos < numberOfSheets; pos++) {
			// clear cache of foreign keys each time to reduce the chance of naming-conflicts
			foreignKeys.clear();
			Sheet sheet = workbook.getSheetAt(pos);
			if (!ExcelParser.EXCEL_CONFIG.equals(sheet.getSheetName())) {
				watch.reset();
				insertIntoGraph(sheet);
				LOGGER.info("Parsed Excel Sheet \"{}\" in {}ms", sheet.getSheetName(), watch.getTime());
			}
		}
		return graph;
	}

	// ------------------------------------------------------

	private void insertIntoGraph(final Sheet sheet) {
		Map<String, ResourceID> predicates = getPredicates(sheet);
		// Start with the second row, since the first one is used for predicate declaration.
		Row row;
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			insertRow(row, predicates);
		}
	}

	private void insertRow(final Row row, final Map<String, ResourceID> predicates) {
		ResourceNode node = createResourceNode();
		List<String> columns = new LinkedList<String>(predicates.keySet());
		for (String column : columns) {
			String value = ExcelParserTools.getStringValueFor(row, columns.indexOf(column));
			if (null != value) {
				Statement stmt = null;
				if (metaData.isForeignKey(row.getSheet().getSheetName(), column)) {
					addKeyToCache(value);
					stmt = new DetachedStatement(node, predicates.get(column), getForeignKey(value));
				} else {
					stmt = new DetachedStatement(node, predicates.get(column), new SNValue(ElementaryDataType.STRING,
							value));
				}
				graph.addStatement(stmt);
			}
		}
	}

	/**
	 * @return
	 */
	private ResourceNode createResourceNode() {
		ResourceNode node = new SNResource();
		return node;
	}

	/**
	 * Get the value for a given key
	 * 
	 * @param key
	 * @return The {@link ResourceID} for mathing to the given key
	 */
	private ResourceID getForeignKey(final String key) {
		ResourceID resourceID = foreignKeys.get(key);
		return resourceID;
	}

	/**
	 * Add a key to the foreign key cache.
	 * 
	 * @param key The key
	 */
	private void addKeyToCache(final String key) {
		if (!isCached(key)) {
			foreignKeys.put(key, new SimpleResourceID());
		}
	}

	/**
	 * Check wheather a foreign key is already cached.
	 * 
	 * @param value The keys' identifier
	 * @return true if it is chached, false if not
	 */
	private boolean isCached(final String value) {
		return foreignKeys.containsKey(value);
	}

	private void addStatements(final SemanticGraph graph, final Map<String, ResourceID> predicates, final Sheet sheet) {
		Map<String, ResourceID> foreignKeys = new HashMap<String, ResourceID>();
		String namespace = metaData.getNameSpace();
		int emptyRow = 0;
		Row row;
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			ResourceNode node = null;
			int index = 0;
			ResourceID subject = null;
			SemanticNode object;
			for (String cellHeader : predicates.keySet()) {
				if (null != row.getCell(index) && null != row.getCell(index).getStringCellValue()) {
					emptyRow = 0;
					if (null == node) {
						Cell cell = row.getCell(index);
						node = new SNResource(new QualifiedName(cell.getStringCellValue()));
						subject = node;
					}
					if (foreignKeys.containsKey(row.getCell(index).getStringCellValue())) {
						object = foreignKeys.get(row.getCell(index).getStringCellValue());
					} else if (metaData.isForeignKey(sheet.getSheetName(), cellHeader)) {
						ResourceID id = new SimpleResourceID(namespace, row.getCell(index).getStringCellValue());
						foreignKeys.put(row.getCell(index).getStringCellValue(), id);
						object = id;
					} else {
						object = new SNValue(ElementaryDataType.STRING, row.getCell(index).getStringCellValue());
					}
					graph.addStatement(new DetachedStatement(subject, predicates.get(cellHeader), object));
				} else {
					emptyRow++;
				}
				index++;
				if (emptyRow >= 30) {
					break;
				}
			}
		}

	}

	private Map<String, ResourceID> getPredicates(final Sheet sheet) {
		Map<String, ResourceID> predicates = new LinkedHashMap<String, ResourceID>();
		String nameSpace = metaData.getNameSpace();
		for (Cell cell : sheet.getRow(0)) {
			String value = buildPredicate(nameSpace, PREFIX, cell.getStringCellValue());
			predicates.put(cell.getStringCellValue(), new SimpleResourceID(value));
		}
		return predicates;
	}

	private String buildPredicate(final String nameSpace, final String prefix, final String value) {
		StringBuilder sb = new StringBuilder(nameSpace);
		sb.append(prefix);
		sb.append(normalize(value));
		return sb.toString();
	}

	private String normalize(String value) {
		value = value.trim();
		int index = value.indexOf(" ");
		while (index >= 0) {
			char letter = value.charAt(index + 1);
			value.replace(" " + letter, Character.toString(letter).toUpperCase());
			index = value.indexOf(" ");
		}
		return value;
	}

}
