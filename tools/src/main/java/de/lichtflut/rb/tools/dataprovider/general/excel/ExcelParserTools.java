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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
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
// TODO ignore comments in excel sheet
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
		if(Cell.CELL_TYPE_STRING == cell.getCellType()){
			return cell.getStringCellValue();
		}
		if(HSSFDateUtil.isCellDateFormatted(cell)){
			Date date = cell.getDateCellValue();
			String string = "";
			if(null != date){
				string = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date);
			}
			return string;
		}
		if(Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
			double cellValue = cell.getNumericCellValue();
			if((int)cellValue == cellValue){
				return String.valueOf((int)cellValue);
			}
			return String.valueOf(cellValue);
		}
		return "";
	}
}
