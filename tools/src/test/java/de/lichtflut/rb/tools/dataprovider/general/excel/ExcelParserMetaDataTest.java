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
