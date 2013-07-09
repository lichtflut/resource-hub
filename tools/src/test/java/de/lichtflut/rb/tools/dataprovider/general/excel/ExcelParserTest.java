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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.arastreju.sge.model.SemanticGraph;
import org.junit.Test;


/**
 * <p>
 * Testclass for {@link ExcelParser}.
 * </p>
 * Created: Mar 1, 2013
 *
 * @author Ravi Knox
 */
public class ExcelParserTest {

	@Test
	public void testExcelParser() throws InvalidFormatException, IOException{
        URL url = Thread.currentThread().getContextClassLoader().getResource("TestITCatalog.xlsx");
        assertNotNull("Didn't find test Excel file.", url);

		ExcelParser parser = new ExcelParser(new File(url.getFile()));

		assertThat(parser, is(notNullValue()));

		SemanticGraph graph = parser.read();
		System.out.println(graph);
	}
}
