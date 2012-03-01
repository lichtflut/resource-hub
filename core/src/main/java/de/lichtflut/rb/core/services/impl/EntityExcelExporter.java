/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Set;

import jxl.CellView;
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

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.services.ExcelExporter;

/**
 * <p>
 *  Implementation of {@link ExcelExporter} to export a single entity.
 * </p>
 *
 * <p>
 * 	Created Feb 22, 2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class EntityExcelExporter implements ExcelExporter {

	private final ResourceNode entity;
	private Set<ResourceID> predicates;
	private final Locale locale;
	
	private final ResourceLabelBuilder labelBuilder;
	
	private WritableCellFormat labelFormat;
	private WritableCellFormat contentFormat;

	// -----------------------------------------------------

	/**
	 * Constructor.
	 */
	public EntityExcelExporter(final ResourceNode entity, Locale locale) {
		this.entity = entity;
		this.locale = locale;
		
		this.predicates = SNOPS.predicates(entity.getAssociations());
		this.labelBuilder = ResourceLabelBuilder.getInstance();
	}
	
	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 * @throws IOException 
	 */
	@Override
	public void export(final OutputStream out) throws IOException {

		WritableWorkbook workbook = Workbook.createWorkbook(out);
		
		workbook.createSheet("Resource", 0);
		WritableSheet excelSheet = workbook.getSheet(0);

		prepare();
		
		try {
			createLabels(excelSheet);
			createContent(excelSheet);
			
			autosizeColumns(excelSheet);
	
			workbook.write();
			workbook.close();
		} catch (WriteException e) {
			throw new IOException("Could not write Excel Sheet", e);
		}
	}

	/**
	 * Get prepared.
	 */
	private void prepare() {
		WritableFont arial10pt = new WritableFont(WritableFont.ARIAL, 10);
		contentFormat = new WritableCellFormat(arial10pt);

		WritableFont arial10ptBold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		labelFormat = new WritableCellFormat(arial10ptBold);
	}
	
	/**
	 * Create the labels.
	 * @param sheet
	 * @throws WriteException
	 */
	private void createLabels(WritableSheet sheet) throws WriteException {
		int row = 0;
		int column = 0;
		
		for(ResourceID predicate : predicates) {
			String string = labelBuilder.getFieldLabel(predicate, locale); 
			Label label = new Label(column, row, string, labelFormat);
			sheet.addCell(label);
			row++;
		}
	}
	
	/**
	 * Create the content.
	 * @param sheet
	 * @throws WriteException
	 */
	private void createContent(WritableSheet sheet) throws WriteException {
		int row = 0;
		int column = 1;
		
		for(ResourceID predicate : predicates) {
			String string = "";
			Set<SemanticNode> objects = SNOPS.objects(entity, predicate);
			int objectsLeft = objects.size();
			for (SemanticNode object : objects) {
				objectsLeft--;
				if(object != null) {
					if(object.isValueNode()) {
						string += object.asValue().getStringValue();
					} else {
						string += labelBuilder.getLabel(object.asResource(), locale);
					}
				}
				if(objectsLeft > 0) {
					string += "; ";
				}
			}
			Label label = new Label(column, row, string, contentFormat);
			sheet.addCell(label);
			row++;
		}
	}
	
	/**
	 * Autosize the columns to fit the contents.
	 * @param sheet
	 */
	private void autosizeColumns(WritableSheet sheet) {
		for(int i=0; i < sheet.getColumns(); i++)
		{
		    CellView cellView = sheet.getColumnView(i);
		    cellView.setAutosize(true);
		    sheet.setColumnView(i, cellView);
		}
	}
	
}