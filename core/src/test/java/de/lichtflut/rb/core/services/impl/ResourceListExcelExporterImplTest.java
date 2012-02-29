/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import de.lichtflut.rb.core.services.ResourceListExcelExporter;

/**
 * <p>
 *  Test-class for {@link ResourceListExcelExporterImpl}.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class ResourceListExcelExporterImplTest {

	@Test
	public void testExport() {
		List<ResourceNode> data = new ArrayList<ResourceNode>();
		List<ResourceID> predicates = new ArrayList<ResourceID>();
		
		ResourceNode node1 = new SNResource(new QualifiedName("qn#resourceNode_1"));
		ResourceNode node2 = new SNResource(new QualifiedName("qn#resourceNode_2"));
		
		ResourceID pred1 = new SimpleResourceID(new QualifiedName("qn#hasPredicate_1"));
		ResourceID pred2 = new SimpleResourceID(new QualifiedName("qn#hasPredicate_2"));
		ResourceID pred3 = new SimpleResourceID(new QualifiedName("qn#hasPredicate_3"));
		ResourceID pred4 = new SimpleResourceID(new QualifiedName("qn#hasPredicate_4"));
		
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
		
		ResourceListExcelExporter exporter = new ResourceListExcelExporterImpl(data, predicates, Locale.ENGLISH);
		
		try {
			OutputStream out = new FileOutputStream("src/test/resources/ResourceListExportTest.xls");
			exporter.export(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}