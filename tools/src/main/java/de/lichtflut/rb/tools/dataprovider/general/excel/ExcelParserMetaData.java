/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.general.excel;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.arastreju.sge.naming.QualifiedName;

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

	public static final String NAMESPACE = "Namespace";

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

	public QualifiedName getNameSpace(){
		String namecspace = findValueFor(NAMESPACE, sheet);
		return new QualifiedName(namecspace);
	}

	// ------------------------------------------------------

	private String findValueFor(final String key, final Sheet sheet) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				String value = cell.getStringCellValue().trim();
				if(key.equals(value) || key.equals(value + ":")){
					Cell string = row.getCell(cell.getColumnIndex()+1);
					return string.getStringCellValue();
				}
			}
		}
		return null;
	}

}
