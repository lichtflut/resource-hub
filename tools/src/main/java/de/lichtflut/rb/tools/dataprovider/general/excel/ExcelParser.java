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
package de.lichtflut.rb.tools.dataprovider.general.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.DefaultSemanticGraph;
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
public class ExcelParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelParser.class);

	private static final String URI = "^(http?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private static final String QNAME = "^[A-z]*:[A-z0-9-]*";
	private static final String EXCEL_CONFIG = "rb-parser-config";
	private static final String VERSIONING = "rb-versioning";
	private String PREFIX = "has";

	private final ExcelFieldWrapper data = new ExcelFieldWrapper();


	private boolean isFirstCellInRow;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param file The excel sheet
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public ExcelParser(final File file) throws InvalidFormatException, IOException {
		data.setWorkbook(WorkbookFactory.create(file));
		data.setMetaData(new ExcelParserMetaData(data.getWorkbook().getSheet(EXCEL_CONFIG)));
		data.setGraph(new DefaultSemanticGraph());
		data.setForeignKeys(new HashMap<String, ResourceID>());
		isFirstCellInRow = true;
	}

	// ------------------------------------------------------



	/**
	 * Read the given sheet and convert it into a graph.
	 * 
	 * @return a {@link SemanticGraph}
	 */
	public SemanticGraph read() {
		// Excel version > 2002 specific cast. For lower versions use HSSFWorkbook
		int numberOfSheets = ((XSSFWorkbook) data.getWorkbook()).getNumberOfSheets();
		StopWatch watch = new StopWatch();
		for (int pos = 0; pos < numberOfSheets; pos++) {
			Sheet sheet = data.getWorkbook().getSheetAt(pos);
			readSheet(watch, sheet);
		}
		return data.getGraph();
	}

	public ExcelFieldWrapper getMetadataFields(){
		return data;
	}

	public String getPrefix() {
		return PREFIX;
	}

	public void setPrefix(final String prefix){
		this.PREFIX = prefix;
	}

	// ------------------------------------------------------

	/**
	 * Overwrite for custom parsing rules.
	 * @param watch
	 * @param sheet
	 */
	protected void readSheet(final StopWatch watch, final Sheet sheet) {
		if (!ExcelParser.EXCEL_CONFIG.equals(sheet.getSheetName())) {
			watch.reset();
			insertIntoGraph(sheet);
			LOGGER.info("Parsed Excel Sheet \"{}\" in {}ms", sheet.getSheetName(), watch.getTime());
		}
	}

	protected Map<String, ResourceID> getPredicates(final Sheet sheet) {
		Map<String, ResourceID> predicates = new LinkedHashMap<String, ResourceID>();
		String nameSpace = data.getMetaData().getNameSpace();
		for (Cell cell : sheet.getRow(0)) {
			String value = null;
			String suffix = cell.getStringCellValue();
			if (isURI(suffix)) {
				value = suffix;
			} else if (isQname(suffix)) {
				value = convertQNameToURI(suffix);
			} else {
				value = buildPredicate(nameSpace, getPrefix(), suffix);
			}
			predicates.put(suffix, new SimpleResourceID(value));
		}
		return predicates;
	}

	protected boolean isQname(final String suffix) {
		return suffix.matches(QNAME);
	}

	protected ResourceNode genereateRowBasedNode(final Row row, final Map<String, ResourceID> predicates) {
		ResourceNode node = createResourceNode();
		List<String> columns = new LinkedList<String>(predicates.keySet());
		SemanticNode object = null;
		for (String columnHeader : columns) {
			String value = ExcelParserTools.getStringValueFor(row, columns.indexOf(columnHeader));
			if (null != value && !value.isEmpty()) {
				if (data.getMetaData().isPrimaryKey(row.getSheet().getSheetName(), columnHeader)) {
					addKeyToCache(value);
					// TODO: alwas set the ID manually
					// Primary ID will always be the first element. So we still can change the ID
					// without corrupting data
					node = replaceURIWithExisting(node, value);
					object = new SNValue(ElementaryDataType.STRING, value);
				} else if (data.getMetaData().isForeignKey(row.getSheet().getSheetName(), columnHeader)) {
					for (String string : value.split(",")) {
						value = string.trim();
						if (isURI(value)) {
							object = new SimpleResourceID(value);
						} else if (isQname(value)) {
							object = new SimpleResourceID(convertQNameToURI(value));
						} else {
							addKeyToCache(value);
							object = getForeignKey(value);
						}
						node.addAssociation(predicates.get(columnHeader), object);
						data.getGraph().addStatements(node.getAssociations());
					}
				} else {
					for (String string : value.split(",")) {
						value = string.trim();
						object = createValueNodeFor(columnHeader, value);
						node.addAssociation(predicates.get(columnHeader), object);
						data.getGraph().addStatements(node.getAssociations());
					}
				}
				node.addAssociation(predicates.get(columnHeader), object);
			}
		}
		return node;
	}

	protected boolean isURI(final String value) {
		return value.matches(URI);
	}

	protected SemanticNode createValueNodeFor(final String column, final String value) {
		SemanticNode object;
		if (isURI(value)) {
			object = new SimpleResourceID(value);
		} else if (isQname(value)) {
			object = new SimpleResourceID(convertQNameToURI(value));
		} else {
			object = new SNValue(ElementaryDataType.STRING, value);
		}
		return object;
	}

	protected ResourceNode createResourceNode() {
		ResourceNode node = new SNResource();
		return node;
	}

	protected int checkForEmptyLine(int emptyLines, final ResourceNode node) {
		if (node.getAssociations().isEmpty()) {
			emptyLines++;
		} else {
			emptyLines = 0;
		}
		return emptyLines;
	}

	protected ResourceNode replaceURIWithExisting(ResourceNode node, final String value) {
		ResourceNode copy = node;
		if (data.getForeignKeys().containsKey(value)) {
			node = new SNResource(data.getForeignKeys().get(value).getQualifiedName());
			for (Statement stmt : copy.getAssociations()) {
				node.addAssociation(stmt.getPredicate(), stmt.getObject());
			}
		}else{
			node = new SNResource(QualifiedName.from(data.getMetaData().getNameSpace(), value));
			for (Statement stmt : copy.getAssociations()) {
				node.addAssociation(stmt.getPredicate(), stmt.getObject());
			}
		}
		return node;
	}

	/**
	 * Get the value for a given key
	 * 
	 * @param key
	 * @return The {@link ResourceID} for mathing to the given key
	 */
	protected ResourceID getForeignKey(final String key) {
		ResourceID resourceID = data.getForeignKeys().get(key);
		return resourceID;
	}

	/**
	 * Add a key to the foreign key cache.
	 * 
	 * @param key The key
	 */
	protected void addKeyToCache(final String key) {
		if (!isCached(key)) {
			data.getForeignKeys().put(key, new SimpleResourceID(QualifiedName.from(data.getMetaData().getNameSpace(), key)));
		}
	}

	/**
	 * Check wheather a foreign key is already cached.
	 * 
	 * @param value The keys' identifier
	 * @return true if it is chached, false if not
	 */
	protected boolean isCached(final String value) {
		return data.getForeignKeys().containsKey(value);
	}

	protected String convertQNameToURI(final String suffix) {
		String value;
		StringBuilder sb = new StringBuilder();
		sb.append(data.getMetaData().getNamespaceFor(QualifiedName.getPrefix(suffix)));
		sb.append(QualifiedName.getSimpleName(suffix));
		value = sb.toString();
		return value;
	}

	protected ResourceNode findObjectInGraph(final ResourceNode subject) {
		for (ResourceNode node : data.getGraph().getSubjects()) {
			if(node.getQualifiedName().toURI().equals(subject.getQualifiedName().toURI())){
				return node;
			}
		}
		return null;
	}

	protected void insertIntoGraph(final Sheet sheet) {
		List<ResourceNode> nodes = generateNodesFromSheet(sheet);
		for (ResourceNode node : nodes) {
			data.getGraph().addStatements(node.getAssociations());
		}
	}

	// ------------------------------------------------------

	private List<ResourceNode> generateNodesFromSheet(final Sheet sheet) {
		List<ResourceNode> nodes = new ArrayList<ResourceNode>();
		Map<String, ResourceID> predicates = getPredicates(sheet);
		Row row;
		int emptyLines = 0;
		// Start with the second row, since the first one is used for predicate declaration.
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (VERSIONING.equals(sheet.getSheetName())) {
				addVersioning(row, predicates);
			}else{
				if(null != row){
					ResourceNode node = genereateRowBasedNode(row, predicates);
					nodes.add(node);
					emptyLines = checkForEmptyLine(emptyLines, node);
				}
			}
			// For performance optimization we stopp parsing after 10 consecutive empty lines
			if (emptyLines >= 10) {
				break;
			}
		}
		return nodes;
	}

	private void addVersioning(final Row row, final Map<String, ResourceID> predicates) {
		ResourceNode node = null;
		ResourceNode parent = null;
		isFirstCellInRow = true;
		for (int index = 0; index < predicates.size(); index++) {
			String value = ExcelParserTools.getStringValueFor(row, index);
			if (null == value) {
				if(0 == index){
					break;
				}
			}else if (!value.isEmpty()) {
				// If version is a/interpreted as a double number in excel
				if(value.contains(".")) {
					value = value.substring(0, value.indexOf("."));
				}
				if(0 == index){
					parent = findObjectInGraph(replaceURIWithExisting(new SNResource(), value));
				} else {
					if (1 == index) {
						node = new SNResource(createChildURI(parent, value));
						clone(parent, node);
						SNOPS.assure(node, RDFS.SUB_CLASS_OF, parent);
						SNOPS.assure(node, RDFS.LABEL, new SNValue(ElementaryDataType.STRING, createInheritedLabel(parent, value)));
						data.getGraph().addStatements(node.getAssociations());
						parent = node;
					}else if (2 == index) {
						node = new SNResource(createChildURI(parent, value));
						clone(parent, node);
						SNOPS.assure(node, RDFS.SUB_CLASS_OF, parent);
						SNOPS.assure(node, RDFS.LABEL, new SNValue(ElementaryDataType.STRING, createInheritedLabel(parent, value)));
						data.getGraph().addStatements(node.getAssociations());
						parent = node;
					} else if (3 == index) {
						for (String version : value.split(",")) {
							node = new SNResource(createChildURI(parent, version));
							clone(parent, node);
							SNOPS.assure(node, RDFS.SUB_CLASS_OF, parent);
							SNOPS.assure(node, RDFS.LABEL, new SNValue(ElementaryDataType.STRING, createInheritedLabel(parent, version)));
							data.getGraph().addStatements(node.getAssociations());
						}
					}
				}
			}
		}
	}

	private String createInheritedLabel(final ResourceNode parent, final String value){
		String label = "";
		if(isFirstCellInRow){
			label  =SNOPS.fetchObject(parent, RDFS.LABEL).asValue().getStringValue() + " " + value.trim();
			isFirstCellInRow = false;
		}else{
			label = SNOPS.fetchObject(parent, RDFS.LABEL).asValue().getStringValue() + "." + value.trim();
		}
		return label;
	}

	private QualifiedName createChildURI(final ResourceNode parent, final String value) {
		return QualifiedName.fromURI(parent.getQualifiedName() + "-" + value.trim());
	}

	private void clone(final ResourceNode original, final ResourceNode clone) {
		for (Statement stmt : original.getAssociations()) {
			clone.addAssociation(stmt.getPredicate(), stmt.getObject());
		}
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
