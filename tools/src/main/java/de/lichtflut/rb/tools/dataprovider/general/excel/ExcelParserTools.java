/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.general.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * <p>
 * Some helper for excel parsing operations...
 * </p>
 * Created: Mar 1, 2013
 * 
 * @author Ravi Knox
 */
// TODO reduce memory footprint: http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
// TODO ignore comments
public class ExcelParserTools {

	public static String findValueFor(final String key, final Sheet sheet) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				String value = cell.getStringCellValue().trim();
				if (key.equalsIgnoreCase(value) || key.equalsIgnoreCase(value + ":")) {
					Cell string = row.getCell(cell.getColumnIndex() + 1);
					return string.getStringCellValue();
				}
			}
		}
		return null;
	}

	public static int getRowIndexFor(final String identifier, final Sheet sheet) {
		int index = -1;
		for (Row row : sheet) {
			if (0 <= row.getFirstCellNum()) {
				Cell cell = row.getCell(row.getFirstCellNum());
				if (identifier.equalsIgnoreCase(cell.getStringCellValue())) {
					index = cell.getRowIndex();
					break;
				}
			}
		}
		return index;
	}

	/**
	 * Returns the String value of a row at a given index.
	 * 
	 * @param row The row to get the value from
	 * @param index The index to look for
	 * @return A String representig the value
	 */
	public static String getStringValueFor(final Row row, final int index) {
		String value = null;
		if (index < row.getLastCellNum()) {
			Cell cell = row.getCell(index);
			if(null != cell){
				value = getStringValueFor(cell);
			}
		}

		return value;
	}

	/**
	 * Returns the String value fo this cell.
	 * 
	 * @param cell The cell to extract the value from
	 * @return a String representation of the cell's value
	 */
	public static String getStringValueFor(final Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				double numeric = cell.getNumericCellValue();
				value = String.valueOf(numeric);
				break;
			case Cell.CELL_TYPE_STRING:
			case Cell.CELL_TYPE_BLANK:
				value = cell.getStringCellValue();
				break;
			default:
				break;
		}
		return value;
	}
}
