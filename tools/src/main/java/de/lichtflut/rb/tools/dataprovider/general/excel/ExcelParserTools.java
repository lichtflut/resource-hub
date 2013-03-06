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
//TODO reduce memory footprint: http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
public class ExcelParserTools {


	public static String findValueFor(final String key, final Sheet sheet) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				String value = cell.getStringCellValue().trim();
				if(key.equalsIgnoreCase(value) || key.equalsIgnoreCase(value + ":")){
					Cell string = row.getCell(cell.getColumnIndex()+1);
					return string.getStringCellValue();
				}
			}
		}
		return null;
	}


	public static int getRowIndexFor(final String identifier, final Sheet sheet) {
		int index = -1;
		for (Row row : sheet) {
			if(0 <= row.getFirstCellNum()){
				Cell cell = row.getCell(row.getFirstCellNum());
				if(identifier.equalsIgnoreCase(cell.getStringCellValue())){
					index = cell.getRowIndex();
					break;
				}
			}
		}
		return index;
	}
}
