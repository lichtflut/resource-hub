/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.general.excel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
	private ExcelParserMetaData metaData;
	private Sheet sheet;

	// ------------- SetUp & tearDown -----------------------

	@Before
	public void setUp() throws InvalidFormatException, IOException {
		File file = new File("src/test/resources/TestITCatalog.xlsx");
		Workbook wb = WorkbookFactory.create(file);
		sheet = wb.getSheet(CONFIG_SHEET);
		metaData = new ExcelParserMetaData(sheet);
	}

	// ------------------------------------------------------

	/**
	 * Test method for {@link de.lichtflut.rb.tools.dataprovider.general.excel.ExcelParserMetaData#getNameSpace()}.
	 */
	@Test
	public void testGetNameSpace() throws InvalidFormatException, IOException {
		String nameSpace = metaData.getNameSpace();

		assertThat(nameSpace, equalTo("http://rb.lichtflut.de/devops#"));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.tools.dataprovider.general.excel.ExcelParserMetaData#isForeignKey(String, String)}.
	 */
	@Test
	public void testIsForeignKey(){
		boolean foreignKey = metaData.isForeignKey("Products", "Cat-ID");

		assertThat(foreignKey, is(true));
	}

	@Test
	public  void testGetNamespaceFor() throws Exception {
		String rb = metaData.getNamespaceFor("rb-system");
		assertThat(rb, equalTo("http://rb.lichtflut.de/system#"));

		String rdf = metaData.getNamespaceFor("rdfs");
		assertThat(rdf, equalTo("http://www.w3.org/2000/01/rdf-schema#"));

	}

}
