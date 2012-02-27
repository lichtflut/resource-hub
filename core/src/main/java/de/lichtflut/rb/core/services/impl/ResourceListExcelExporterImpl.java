/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.services.ResourceListExcelExporter;

/**
 * <p>
 *  Implementation of {@link ResourceListExcelExporter}.
 * </p>
 *
 * <p>
 * 	Created Feb 22, 2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class ResourceListExcelExporterImpl implements ResourceListExcelExporter {

	private final List<ResourceNode> data;
	private final List<ResourceID> predicates;
	
	private WritableCellFormat labelFormat;
	private WritableCellFormat contentFormat;

	// -----------------------------------------------------

	/**
	 * Constructor.
	 */
	public ResourceListExcelExporterImpl(List<ResourceNode> data, List<ResourceID> predicates) {
		this.data = data;
		this.predicates = predicates;
	}
	
	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 * @throws IOException 
	 */
	@Override
	public void export(final OutputStream out) throws IOException {

		WritableWorkbook workbook = Workbook.createWorkbook(out);
		
		workbook.createSheet("ResourceList", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		
		try {
			prepare();
			
			createLabels(excelSheet);
			createContent(excelSheet);
	
			workbook.write();
			workbook.close();
		} catch (WriteException e) {
			throw new IOException("Could not write Excel Sheet", e);
		}
	}

	/**
	 * Get prepared.
	 * @throws WriteException
	 */
	private void prepare() throws WriteException {
		WritableFont arial10pt = new WritableFont(WritableFont.ARIAL, 10);
		contentFormat = new WritableCellFormat(arial10pt);
		contentFormat.setWrap(true);

		WritableFont arial10ptBold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		labelFormat = new WritableCellFormat(arial10ptBold);
		labelFormat.setWrap(true);
	}
	
	private void createLabels(WritableSheet sheet) throws WriteException {
		int row = 0;
		int column = 0;
		
		for(ResourceID predicate : predicates) {
			String string = predicate.getQualifiedName().getSimpleName();
			Label label = new Label(column, row, string, labelFormat);
			sheet.addCell(label);
			column++;
		}
	}
	
	private void createContent(WritableSheet sheet) throws WriteException {
		int row = 1;
		int column = 0;
		
		for(ResourceNode node : data) {
			for(ResourceID predicate : predicates) {
				String string = "";
				SemanticNode object = SNOPS.fetchObject(node, predicate);
				if(object != null) {
					if(object.isValueNode()) {
						string = object.asValue().getStringValue();
					} else {
						if(object.isResourceNode()) {
							string = object.asResource().getQualifiedName().getSimpleName();
						}
					}
				}
				Label label = new Label(column, row, string, contentFormat);
				sheet.addCell(label);
				column++;
			}
			row++;
			column = 0;
		}
	}
	
}