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
package de.lichtflut.rb.core.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Test;

import de.lichtflut.rb.core.services.ExcelExporter;

/**
 * <p>
 *  Test-class for {@link ResourceListExcelExporter}.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class ResourceListExcelExporterTest {

	@Test
	public void testExport() {
		List<ResourceNode> data = new ArrayList<ResourceNode>();
		List<ResourceID> predicates = new ArrayList<ResourceID>();
		
		ResourceNode node1 = new SNResource(QualifiedName.from("qn#resourceNode_1"));
		ResourceNode node2 = new SNResource(QualifiedName.from("qn#resourceNode_2"));
		
		ResourceID pred1 = new SimpleResourceID(QualifiedName.from("qn#hasPredicate_1"));
		ResourceID pred2 = new SimpleResourceID(QualifiedName.from("qn#hasPredicate_2"));
		ResourceID pred3 = new SimpleResourceID(QualifiedName.from("qn#hasPredicate_3"));
		ResourceID pred4 = new SimpleResourceID(QualifiedName.from("qn#hasPredicate_4"));
		
		// ----- node1 -----
		SNOPS.associate(node1, pred1, new SNValue(ElementaryDataType.STRING, "A String-Value"));
		// NO pred2 -> shouldn't have it!
		SNOPS.associate(node1, pred3, new SNValue(ElementaryDataType.BOOLEAN, true));
		SNOPS.associate(node1, pred4, new SNValue(ElementaryDataType.STRING, "HOPE NOT VISIBLE??!"));
		
		// ----- node2 -----
		SNOPS.associate(node2, pred1, new SNValue(ElementaryDataType.STRING, "Another String-Value"));
		SNOPS.associate(node2, pred1, new SNValue(ElementaryDataType.STRING, "Another Value, Same Predicate"));
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 1, 29, 9, 45, 30);
		SNOPS.associate(node2, pred2, new SNValue(ElementaryDataType.DATE, calendar.getTime()));
		SNOPS.associate(node2, pred3, node1);
		SNOPS.associate(node2, pred4, new SNValue(ElementaryDataType.STRING, "HOPE NOT VISIBLE??!"));
		
		data.add(node1);
		data.add(node2);
		
		predicates.add(pred1);
		predicates.add(pred2);
		predicates.add(pred3);
		// NO pred4 --> shouldn't be exported!
		
		ExcelExporter exporter = new ResourceListExcelExporter(data, predicates, Locale.ENGLISH);
		
		try {
			// ByteArrayOutputStream to not produce a new file every time the test runs -> prevents GIT-SPAMMING !!
//			OutputStream out = new FileOutputStream("src/test/resources/ResourceListExportTest.xls");
			OutputStream out = new ByteArrayOutputStream();
			exporter.export(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}