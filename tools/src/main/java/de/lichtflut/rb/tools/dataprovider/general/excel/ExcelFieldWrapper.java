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