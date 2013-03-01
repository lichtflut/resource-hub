/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.general.excel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * [TODO Insert description here.]
 * </p>
 * Created: Mar 1, 2013
 *
 * @author Ravi Knox
 */
public class ExcelParserMetaDataTest {

	private static final String CONFIG_SHEET = "rb-parser-config";
	private Sheet sheet;

	// ------------- SetUp & tearDown -----------------------

	@Before
	public void setUp() throws InvalidFormatException, IOException {
		File file = new File("src/test/resources/ITCatalog.xlsx");
		Workbook wb = WorkbookFactory.create(file);
		sheet = wb.getSheet(CONFIG_SHEET);
	}

	// ------------------------------------------------------

	/**
	 * Test method for {@link de.lichtflut.rb.tools.dataprovider.general.excel.ExcelParserMetaData#getNameSpace()}.
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	@Test
	public void testGetNameSpace() throws InvalidFormatException, IOException {
		ExcelParserMetaData metaData = new ExcelParserMetaData(sheet);
		QualifiedName nameSpace = metaData.getNameSpace();

		assertThat(nameSpace.toURI(), equalTo("http://rb.lichtflut.de/definitions/"));
	}

}
