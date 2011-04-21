/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
<<<<<<< HEAD:core/src/main/java/de/lichtflut/rb/core/spi/impl/ResourceSchemaManagementImpl.java

import de.lichtflut.rb.core.schema.model.PropertyAssertion;
=======
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
>>>>>>> b0945ca571b2b8fc7053851e9e4af3a57c5d1573:core/src/main/java/de/lichtflut/rb/core/spi/impl/ResourceSchemaManagementImpl.java
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.model.impl.CardinalityFactory;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.impl.RBCaseControlStream;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultImpl;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaLexer;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser.dsl_return;
import de.lichtflut.rb.core.spi.ResourceSchemaManagement;

/**
 * [TODO Insert description here.]
 * 
 * Created: Apr 19, 2011
 *
 * @author [SPECIFY USER: Window-> Preferences] 
 */
public class ResourceSchemaManagementImpl implements ResourceSchemaManagement {

	public ResourceSchemaManagementImpl(){
		
	}
	
	public RSParsingResult generateSchemaModelThrough(InputStream is){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder bufferedInput = new StringBuilder();
			String line;
			while((line = reader.readLine())!=null) bufferedInput.append(line).append("\n");
			return generateSchemaModelThrough(bufferedInput.toString());
		} catch (IOException e) {
			RSParsingResultImpl result = new RSParsingResultImpl();
			result.addErrorMessage("The following I/O-Error has been occured: " + e.getMessage());
			return result;
		}
	}

	public RSParsingResult generateSchemaModelThrough(File file){
		try {
			return generateSchemaModelThrough(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			RSParsingResultImpl result = new RSParsingResultImpl();
			result.addErrorMessage("File " + file.getAbsolutePath() + " was not found");
			return result;
		}
	}

	public RSParsingResult generateSchemaModelThrough(String s) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		try {
			Set<ResourceSchemaType> resultTypes = parseDSL(s);
			result.merge(convertToParsingResult(resultTypes));
		} catch (RecognitionException e) {
			result.addErrorMessage("The following Parsing-Error(s) has been occured: " + e.getMessage());
		}
		return result;
	}
	
	public RSParsingResult generateAndResolveSchemaModelThrough(InputStream is) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.merge(generateSchemaModelThrough(is));
		
		return result;
	}

	public RSParsingResult generateAndResolveSchemaModelThrough(File f) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.merge(generateSchemaModelThrough(f));
		
		return result;
	}

	public RSParsingResult generateAndResolveSchemaModelThrough(String s) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.merge(generateSchemaModelThrough(s));
		
		Collection<ResourceSchema> DSLSchemas = result.getResourceSchemas();
		HashMap<String, PropertyDeclaration> propertiesHash = new HashMap<String, PropertyDeclaration>();
		HashMap<String, PropertyDeclaration> dSLPropertiesHash = new HashMap<String, PropertyDeclaration>();
		
		for(PropertyDeclaration declaration: result.getPropertyDeclarations())
			dSLPropertiesHash.put(declaration.getName(), declaration);
		
		for(ResourceSchema schema: DSLSchemas){
			Collection<PropertyAssertion> assertions = schema.getPropertyAssertions();
			for(PropertyAssertion assertion : assertions){
				propertiesHash.put(assertion.getPropertyIdentifier(), null);
			}
		}
		
		for(String assertionName : propertiesHash.keySet()){
			if(dSLPropertiesHash.containsKey(assertionName)){
				propertiesHash.put(assertionName, dSLPropertiesHash.get(assertionName));
			}
			else if(false){
				//TODO try to get property from store.
			}
			else{
				result.addErrorMessage("Property "+ assertionName + " not found!");
			}
		}
		
		result.setPropertyDeclarations(propertiesHash.values());
		
		return result;
	}
	
	
	private RSParsingResult convertToParsingResult(Set<ResourceSchemaType> types){
		RSParsingResultImpl result = new RSParsingResultImpl();
		for (ResourceSchemaType type : types) {
			if(type instanceof ResourceSchema) result.addResourceSchema((ResourceSchema) type);
			if(type instanceof PropertyDeclaration) result.addPropertyDeclaration((PropertyDeclaration) type);
		}
		return result;
	}
	
	private Set<ResourceSchemaType> parseDSL(final String input) throws RecognitionException{
		RBCaseControlStream stream = new RBCaseControlStream(input);
		//Ignore Case, this is really important
		stream.setCaseSensitive(false);
		ResourceSchemaLexer lexer = new ResourceSchemaLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		ResourceSchemaParser parser = new ResourceSchemaParser(tokens);
		dsl_return result = parser.dsl();
		
		return result.types;
	}

	
	/**
	 * TODO: This is a mocked-response, please to the implementation asap
	 */
	public ResourceSchema getResourceSchemaFor(ResourceID id) {
		ResourceSchemaImpl schema = new ResourceSchemaImpl("http://lichtflut.de#","personschema");
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl(); 
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl(); 
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl(); 
		p1.setName("http://lichtflut.de/#geburtsdatum");
		p2.setName("http://lichtflut.de/#email");
		p3.setName("http://lichtflut.de/#alter");
		
		p1.setElementaryDataType(ElementaryDataType.DATE);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);
		
		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));
		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatGeburtstag"), p1);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatAlter"), p3);
		pa1.setCardinality(CardinalityFactory.hasExcactlyOne());
		pa2.setCardinality(CardinalityFactory.hasAtLeastOneUpTo(3));
		pa3.setCardinality(CardinalityFactory.hasExcactlyOne());
		
		schema.addPropertyAssertion(pa1);
		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);
		
		return schema;
	}

}
