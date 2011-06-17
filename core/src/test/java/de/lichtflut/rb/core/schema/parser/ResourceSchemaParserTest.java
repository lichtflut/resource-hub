/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;
import java.io.IOException;
import java.util.Collection;
import junit.framework.Assert;
import org.antlr.runtime.RecognitionException;
import org.arastreju.sge.model.ElementaryDataType;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;

/**
 * <p>
 *  Some tests to proof and specify the ResourceSchemaParser.
 * </p>
 *
 *  <p>
 * 	 Created Apr. 14, 2011
 *  </p>
 *
 * @author Nils Bleisch
 *
 */
public class ResourceSchemaParserTest {
	/**
	 *
	 * @throws IOException for
	 * @throws RecognitionException when something went wrong during parsing
	 */
	public final void testParsingAndConstructingModelFromTestSimpleRSFFile1() throws IOException, RecognitionException{
		ResourceSchemaManagement rManagement = RBServiceProviderFactory.getDefaultServiceProvider().getResourceSchemaManagement();

		//Get ResourceSchemaTypes
		RSParsingResult result = rManagement.generateSchemaModelThrough(
				getClass().getClassLoader().getResourceAsStream("ResourceSchemaDSL1.rsf"));
		Assert.assertFalse(result.isErrorOccured());
	}

	//---------------------------------------------------------------------------

	/**
	 *
	 * @throws IOException when the specified file does not exists
	 * @throws RecognitionException when something went wrong during parsing
	 */
	public final void testParsingAndConstructingModelFromTestOSFFile2() throws IOException, RecognitionException{
		ResourceSchemaManagement rManagement = RBServiceProviderFactory.getDefaultServiceProvider().getResourceSchemaManagement();
		//Set parsing format to OSF
		rManagement.setFormat(RSFormat.OSF);
		//Get ResourceSchemaTypes
		RSParsingResult result = rManagement.generateAndResolveSchemaModelThrough(
				getClass().getClassLoader().getResourceAsStream("ResourceSchemaDSL2.osf"));
		Assert.assertFalse(result.isErrorOccured());
		boolean pAssertion=false;
		for (ResourceSchema rs : result.getResourceSchemas()) {

			//--Search for a given property which must should exists on http://lichtflut.de#testResource2"
			//-----------------------------
			if(rs.getDescribedResourceID().getQualifiedName().toURI().equals("http://lichtflut.de#testResource2")){
				Collection<PropertyAssertion> assertions = rs.getPropertyAssertions();
				for (PropertyAssertion propertyAssertion : assertions) {
					if(propertyAssertion.getPropertyDeclaration().getIdentifier().getQualifiedName().toURI().equals(
							"http://lichtflut.de#Date")){
						Assert.assertTrue(propertyAssertion.getPropertyDeclaration().getElementaryDataType()
								== ElementaryDataType.DATE);
						pAssertion=true;
					}
				}
			}
			//-----------------------------

		}
		//Check if the "http://lichtflut.de#Data"-Properties type of "http://lichtflut.de#testResource2" is Date
		Assert.assertTrue(pAssertion);
		final int size = 6;
		Assert.assertTrue(result.getPropertyDeclarations().size() == size);
	}

}
