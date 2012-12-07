package de.lichtflut.rb.core.schema.writer.rsf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Assert;
import org.junit.Test;

import de.lichtflut.rb.core.common.EntityLabelBuilder;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.writer.OutputElements;

/**
 * <p>
 *  Test case for RSF Writer.
 * </p>
 *
 * <p>
 *  Created 19.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class RsfWriterTest {

	private static final String TEST_NS = "http://example.org/test/";

	// ----------------------------------------------------

	@Test
	public void shouldExportSchema() throws IOException {
		OutputElements outputElements = new OutputElements();
		outputElements.registerNamespace(TEST_NS, "test");
		outputElements.addSchemas(Collections.singleton(createValidPersonSchema()));

		RsfWriter writer = new RsfWriter();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		writer.write(baos, outputElements);

		String export = new String(baos.toByteArray());

		Assert.assertTrue("Contains schema: " + export, export.contains("schema for \"test:Person\""));

		System.out.println(new String(baos.toByteArray()));

	}

	@Test
	public void shouldNotWriteLabelRuleIfEmpty() throws IOException {
		OutputElements outputElements = new OutputElements();
		outputElements.addSchemas(Collections.singleton(createSchemaWithEmptyLabelRule()));

		RsfWriter writer = new RsfWriter();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		writer.write(baos, outputElements);

		String export = new String(baos.toByteArray());

		Assert.assertFalse("Contains label-rule: " + export, export.contains("label-rule"));
	}

	// ----------------------------------------------------

	private ResourceSchema createValidPersonSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl();
		schema.setDescribedType(new SimpleResourceID(TEST_NS + "Person"));
		schema.setLabelBuilder(EntityLabelBuilder.DEFAULT);
		return schema;
	}

	private ResourceSchema createSchemaWithEmptyLabelRule() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl();
		schema.setDescribedType(new SimpleResourceID(TEST_NS + "SomeType"));
		schema.setLabelBuilder(EntityLabelBuilder.DEFAULT);
		return schema;
	}


}
