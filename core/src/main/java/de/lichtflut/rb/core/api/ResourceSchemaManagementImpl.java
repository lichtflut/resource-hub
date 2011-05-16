/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;

import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.RSParsingUnit;
import de.lichtflut.rb.core.schema.parser.RSErrorLevel;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultErrorReporter;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultImpl;
import de.lichtflut.rb.core.schema.persistence.RBSchemaStore;

/**
 * Reference impl of {@link ResourceSchemaManagement}
 * This impl will take the default context (rbschema#context)
 * 
 * Created: Apr 19, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
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
		RSParsingResultImpl result = new RSParsingResultImpl();
		RSParsingUnit pUnit = getFormat().getParsingUnit();
		pUnit.setErrorReporter(new RSParsingResultErrorReporter(result));
		Collection<ResourceSchemaType> resultTypes;
		try {
			resultTypes = pUnit.parse(is);
			result.merge(convertToParsingResult(resultTypes));
		} catch (de.lichtflut.rb.core.schema.parser.exception.RSMissingErrorReporterException e) {
			result.addErrorMessage(e.getMessage(),RSErrorLevel.SYSTEM);
		}
		return result;
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
		return generateSchemaModelThrough(new ByteArrayInputStream(s.getBytes()));
	}
	
	// -----------------------------------------------------
	
	/**
	 *{@inheritDoc}
	 */
	public RSParsingResult generateAndResolveSchemaModelThrough(InputStream is) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.merge(generateSchemaModelThrough(is));
		result.setErrorLevel(RSErrorLevel.INTERPRETER);
		
		//Load all persisted Properties
		Collection<PropertyDeclaration> persistedPDecs = store.loadAllPropertyDeclarations(null);
		Collection<PropertyDeclaration> globalPDecs = result.getPropertyDeclarations();
		//build hashMaps for those to get some better access functionalities
		HashMap<String, PropertyDeclaration> persistedPDecsHash = new HashMap<String, PropertyDeclaration>();
		HashMap<String, PropertyDeclaration> globalPDecsHash = new HashMap<String, PropertyDeclaration>();
		//TODO: Improve this, some points to optimize
		//Fill those hashmaps
		for (PropertyDeclaration pDec : persistedPDecs) persistedPDecsHash.put(pDec.getIdentifierString(),pDec);
		for (PropertyDeclaration pDec : globalPDecs) globalPDecsHash.put(pDec.getIdentifierString(),pDec);
		
		//Merge them together, the newer global ones will override the older persisted ones
		for (PropertyDeclaration pDec : globalPDecsHash.values()) {
			persistedPDecsHash.put(pDec.getIdentifierString(), pDec);
		}
		
		//Iterate over assertions:
		for (ResourceSchema rSchema : result.getResourceSchemas()) {
			for (PropertyAssertion assertion : rSchema.getPropertyAssertions()) {
				PropertyDeclaration assertionPDec = assertion.getPropertyDeclaration();
				if(assertionPDec==null){
					//Get the pDec from given identifier
					assertionPDec = persistedPDecsHash.get(assertion.getPropertyIdentifier());
					//if assertionPDec is still null
					result.addErrorMessage("Wasnt able to resolve PropertyDeclaration for " + assertionPDec.getIdentifierString());
					continue;
				}
				PropertyDeclaration persistedPDec = persistedPDecsHash.get(assertion.getPropertyDeclaration().getIdentifierString());
				//This must be a new or inherited property
				if(persistedPDec == null){
					//check if it's inhertied
					persistedPDec = persistedPDecsHash.get(assertion.getPropertyIdentifier());
					//if persistedPDec is still null, it must be a new property
					if(persistedPDec != null){
						//Resolve and merge assertion
						mergePropertyDecs(assertionPDec,persistedPDec);
					}
				}else{
					mergePropertyDecs(assertionPDec,persistedPDec);
				}
				persistedPDecsHash.put(assertionPDec.getIdentifierString(), assertionPDec);
			}//End of Inner for
		}
		
		result.setPropertyDeclarations(persistedPDecsHash.values());
		return result;
	}

	private void mergePropertyDecs(PropertyDeclaration newOne, PropertyDeclaration oldOne){
		newOne.getConstraints().addAll(oldOne.getConstraints());
		if((newOne.getElementaryDataType()==ElementaryDataType.UNDEFINED)){
			newOne.setElementaryDataType(oldOne.getElementaryDataType());
		}
	}
	
	
	// -----------------------------------------------------
	
	public RSParsingResult generateAndResolveSchemaModelThrough(File f) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		try {
			result.merge(generateAndResolveSchemaModelThrough(new FileInputStream(f)));
		} catch (FileNotFoundException e) {
			result.addErrorMessage("File " + f.getAbsolutePath() + " was not found", RSErrorLevel.SYSTEM);
		}
		return result;
	}

	// -----------------------------------------------------
	
	public RSParsingResult generateAndResolveSchemaModelThrough(String s) {
		return generateAndResolveSchemaModelThrough(new ByteArrayInputStream(s.getBytes()));
	}
	
	// -----------------------------------------------------
	
	private RSParsingResult convertToParsingResult(Collection<ResourceSchemaType> types){
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.setErrorLevel(RSErrorLevel.INTERPRETER);
		if(types==null) return result;
		for (ResourceSchemaType type : types) {
			if(type instanceof ResourceSchema) result.addResourceSchema((ResourceSchema) type);
			if(type instanceof PropertyDeclaration) result.addPropertyDeclaration((PropertyDeclaration) type);
		}
		return result;
	}
	
	// -----------------------------------------------------

	public ResourceSchema getResourceSchemaForResourceType(ResourceID id) {
		return store.convertResourceSchema(store.loadSchemaForResourceType(id,null));
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
		if(declaration!=null) store.store(declaration,null);
	}
	
	// -----------------------------------------------------

	public void storeOrOverridePropertyDeclaration(Collection<PropertyDeclaration> declarations) {
		for (PropertyDeclaration propertyDeclaration : declarations) {
			storeOrOverridePropertyDeclaration(propertyDeclaration);
		}
	}

	// -----------------------------------------------------
	
	public void storeOrOverrideResourceSchema(ResourceSchema schema) {
		if(schema!=null) store.store(schema,null);
	}
	
	// -----------------------------------------------------

	public void storeOrOverrideResourceSchema(Collection<ResourceSchema> schemas) {
		for (ResourceSchema schema : schemas) {
			storeOrOverrideResourceSchema(schema);
		}
	}

	// -----------------------------------------------------
	
	public Collection<PropertyDeclaration> getAllPropertyDeclarations() {
		return this.store.loadAllPropertyDeclarations(null);
	}

	// -----------------------------------------------------
	
	public Collection<ResourceSchema> getAllResourceSchemas() {
		return this.store.loadAllResourceSchemas(null);
	}
	
	// -----------------------------------------------------

}
