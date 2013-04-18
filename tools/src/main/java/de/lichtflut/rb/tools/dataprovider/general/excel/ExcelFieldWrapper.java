/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.general.excel;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;

/**
 * <p>
 * Simple wrapper class...
 * </p>
 * 
 * Created: Mar 18, 2013
 * 
 * @author Ravi Knox
 */
public class ExcelFieldWrapper {

	private Workbook workbook;
	private ExcelParserMetaData metaData;
	private Map<String, ResourceID> foreignKeys;
	private SemanticGraph graph;

	// ---------------- Constructor -------------------------

	public ExcelFieldWrapper() {

	}

	// ------------------------------------------------------

	/**
	 * @return the workbook
	 */
	public Workbook getWorkbook() {
		return workbook;
	}

	/**
	 * @param workbook the workbook to set
	 */
	public void setWorkbook(final Workbook workbook) {
		this.workbook = workbook;
	}

	/**
	 * @return the metaData
	 */
	public ExcelParserMetaData getMetaData() {
		return metaData;
	}

	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(final ExcelParserMetaData metaData) {
		this.metaData = metaData;
	}

	/**
	 * @return the foreignKeys
	 */
	public Map<String, ResourceID> getForeignKeys() {
		return foreignKeys;
	}

	/**
	 * @param foreignKeys the foreignKeys to set
	 */
	public void setForeignKeys(final Map<String, ResourceID> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	/**
	 * @return the graph
	 */
	public SemanticGraph getGraph() {
		return graph;
	}

	/**
	 * @param graph the graph to set
	 */
	public void setGraph(final SemanticGraph graph) {
		this.graph = graph;
	}
}