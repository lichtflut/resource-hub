/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.general.excel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
		File file = new File("src/test/resources/TestITCatalog.xlsx");

		ExcelParser parser = new ExcelParser(file);

		assertThat(parser, is(notNullValue()));

		SemanticGraph graph = parser.read();
		System.out.println(graph);
	}
}
