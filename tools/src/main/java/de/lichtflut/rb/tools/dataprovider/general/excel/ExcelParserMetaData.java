/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.general.excel;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * <p>
 * This class exctracts meta-data for {@link ExcelParser}.
 * The data will be extracted from a sheet named: "rb-parser-config"
 * </p>
 * Created: Mar 1, 2013
 *
 * @author Ravi Knox
 */
// TODO reduce memory footprint: http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
public class ExcelParserMetaData {

	public static final String DELIMETER = ".";
	public static final String NAMESPACE = "Namespace";
	public static final String FOREIGN_KEY_START = "<ForeignKeys>";
	public static final String FOREIGN_KEY_END = "</ForeignKeys>";
	public static final String PRIMARY_KEY_START = "<PrimaryKeys>";
	public static final String PRIMARY_KEY_END = "</PrimaryKeys>";
	public static final String NAMESPACE_KEY_START = "<Namespaces>";
	public static final String NAMESPACE_KEY_END = "</Namespaces>";

	private final Sheet sheet;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public ExcelParserMetaData(final Sheet sheet) {
		this.sheet = sheet;
	}

	// ------------------------------------------------------

	public String getNameSpace(){
		return ExcelParserTools.findValueFor(NAMESPACE, sheet);
	}

	/**
	 * Checks weather a column is marked as a foreignkey.
	 * @param sheetName Name of the sheet
	 * @param identifier The cell's value (columnheader)
	 * @return <code>true</code> if it is a foreignkey column, <code>false</code> if not.
	 */
	public boolean isForeignKey(final String sheetName, final String identifier) {
		String key = sheetName + DELIMETER + identifier;
		return checkForKey(key, FOREIGN_KEY_START, FOREIGN_KEY_END);
	}

	/**
	 * Checks weather a column is marked as a primary key.
	 * @param sheetName Name of the sheet
	 * @param identifier The column name (columnheader)
	 * @return <code>true</code> if it is a primary column, <code>false</code> if not.
	 */
	public boolean isPrimaryKey(final String sheetName, final String identifier) {
		String key = sheetName + DELIMETER + identifier;
		return checkForKey(key, PRIMARY_KEY_START, PRIMARY_KEY_END);
	}

	/**
	 * Looks for the namespace for a given string.
	 * @param prefix The prefix for an existing namespace
	 * @return the namespace
	 */
	public String getNamespaceFor(final String prefix){
		String namespace = null;
		int index = ExcelParserTools.getRowIndexFor(NAMESPACE_KEY_START, sheet);
		while(index < ExcelParserTools.getRowIndexFor(NAMESPACE_KEY_END, sheet)) {
			Row row = sheet.getRow(index++);
			Cell cell = row.getCell(row.getFirstCellNum());
			if(prefix.equals(cell.getStringCellValue())){
				Cell nsCell = row.getCell(row.getFirstCellNum()+1);
				namespace = nsCell.getStringCellValue();
				break;
			}
		}
		return namespace;
	}

	// ------------------------------------------------------

	private boolean checkForKey(final String key, final String startMarker, final String endMarker) {
		int index = ExcelParserTools.getRowIndexFor(startMarker, sheet);
		boolean flag = true;
		boolean isPrimaryKey = false;
		do {
			Row row = sheet.getRow(index+1);
			Cell cell = row.getCell(row.getFirstCellNum());
			if(endMarker.equals(cell.getStringCellValue())){
				flag = false;
			}else if (key.equals(cell.getStringCellValue())) {
				isPrimaryKey = true;
				flag = false;
			}
			index++;
		} while (flag);
		return isPrimaryKey;
	}

}
