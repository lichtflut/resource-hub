/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;

import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.model.ResourceID;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.RSParsingUnit;
import de.lichtflut.rb.core.schema.parser.RSParsingResult.ErrorLevel;
import de.lichtflut.rb.core.schema.parser.RSParsingUnit.RSMissingErrorReporterException;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultErrorReporter;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultImpl;
import de.lichtflut.rb.core.schema.parser.impl.simplersf.RSFormat;
import de.lichtflut.rb.core.schema.persistence.RBSchemaStore;
import de.lichtflut.rb.core.spi.ResourceSchemaManagement;

/**
 * Reference impl of {@link ResourceSchemaManagement}
 * 
 * Created: Apr 19, 2011
 *
 * @author Nils Bleisch
 */
public class ResourceSchemaManagementImpl implements ResourceSchemaManagement {

	private ArastrejuGate gate = null;
	private RBSchemaStore store = null;
	private RSFormat format = null;
	
	public ResourceSchemaManagementImpl(ArastrejuGate gate) {
		//Trigger a NullPointerException
		gate.toString();
		this.gate = gate;
		store = new RBSchemaStore(this.gate);
		//Set SimpleRSF as default format and parsing unit
		setFormat(RSFormat.SIMPLE_RSF);
	}

	// -----------------------------------------------------
	
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

	// -----------------------------------------------------
	
	public RSParsingResult generateSchemaModelThrough(File file){
		try {
			return generateSchemaModelThrough(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			RSParsingResultImpl result = new RSParsingResultImpl();
			result.addErrorMessage("File " + file.getAbsolutePath() + " was not found");
			return result;
		}
	}

	// -----------------------------------------------------
	
	public RSParsingResult generateSchemaModelThrough(String s) {
		return generateAndResolveSchemaModelThrough(new ByteArrayInputStream(s.getBytes()));
	}
	
	// -----------------------------------------------------
	
	public RSParsingResult generateAndResolveSchemaModelThrough(InputStream is) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		RSParsingUnit pUnit = getFormat().getParsingUnit();
		pUnit.setErrorReporter(new RSParsingResultErrorReporter(result));
		Collection<ResourceSchemaType> resultTypes;
		try {
			resultTypes = pUnit.parse(is);
			result.merge(convertToParsingResult(resultTypes));
		} catch (RSMissingErrorReporterException e) {
			result.addErrorMessage(e.getMessage(),ErrorLevel.SYSTEM);
		}
		return result;
	}

	// -----------------------------------------------------
	
	public RSParsingResult generateAndResolveSchemaModelThrough(File f) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.merge(generateSchemaModelThrough(f));
		return result;
	}

	// -----------------------------------------------------
	
	public RSParsingResult generateAndResolveSchemaModelThrough(String s) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.setErrorLevel(ErrorLevel.INTERPRETER);
		result.merge(generateSchemaModelThrough(s));
		
		Collection<ResourceSchema> DSLSchemas = result.getResourceSchemas();
		HashMap<String, PropertyDeclaration> propertiesHash = new HashMap<String, PropertyDeclaration>();
		HashMap<String, PropertyDeclaration> dSLPropertiesHash = new HashMap<String, PropertyDeclaration>();
		
		//Put the properties into a hash with the name as identifier
		for(PropertyDeclaration declaration: result.getPropertyDeclarations())
			dSLPropertiesHash.put(declaration.getIdentifier().getQualifiedName().toURI(), declaration);
		
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
	
	// -----------------------------------------------------
	
	private RSParsingResult convertToParsingResult(Collection<ResourceSchemaType> types){
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.setErrorLevel(ErrorLevel.INTERPRETER);
		for (ResourceSchemaType type : types) {
			if(type instanceof ResourceSchema) result.addResourceSchema((ResourceSchema) type);
			if(type instanceof PropertyDeclaration) result.addPropertyDeclaration((PropertyDeclaration) type);
		}
		return result;
	}
	
	// -----------------------------------------------------

	public ResourceSchema getResourceSchemaFor(ResourceID id) {
		return store.convert(store.loadSchemaForResource(id));
	}

	
	public void setFormat(RSFormat format){
		this.format = format;
	}
	
	// -----------------------------------------------------
	
	public RSFormat getFormat() {
		return this.format;
	}
	
	// -----------------------------------------------------

	public void storeOrOverridePropertyDeclaration(PropertyDeclaration declaration) {
		if(declaration!=null) store.store(declaration);
	}
	
	// -----------------------------------------------------

	public void storeOrOverridePropertyDeclaration(Collection<PropertyDeclaration> declarations) {
		for (PropertyDeclaration propertyDeclaration : declarations) {
			storeOrOverridePropertyDeclaration(propertyDeclaration);
		}
	}

	// -----------------------------------------------------
	
	public void storeOrOverrideResourceSchema(ResourceSchema schema) {
		if(schema!=null) store.store(schema);
	}
	
	// -----------------------------------------------------

	public void storeOrOverrideResourceSchema(Collection<ResourceSchema> schemas) {
		for (ResourceSchema schema : schemas) {
			storeOrOverrideResourceSchema(schema);
		}
	}
	
	// -----------------------------------------------------

}
