/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import org.arastreju.sge.model.ResourceID;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.impl.simplersf.RSFormat;
/**
 * <p>
 * This is the RB's ResourceSchemaManagement "HighLevel" interface.
 * Whatever you want to manage, this service tries to give you the tools to do that.
 * No additional knowledge-base about infrastructure, technology stack e.g. should be required/necessary.
 * </p>
 * 
 * Try to make this interface as flexible as you can.
 * 
 * Please note, that this is not yet ready
 * 
 * Created: Apr 19, 2011
 *
 * @author Nils Bleisch
 */
public interface ResourceSchemaManagement {
	

	/**
	 * Set the RSF  you just want to work with
	 * This is used for parsing and converting a given input into a ResourceSchema 
	 * @param {@link RSFormat}
	 */
	public void setFormat(RSFormat format);
	
	/**
	 * Get the {@link RSFormat} 
	 * @return The assigned {@link RSFormat}
	 */
	public RSFormat getFormat();

	// -----------------------------------------------------
	
	/**
	  * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	  * What does "resolve" in this context mean? It's the primary process of interpreting and parsing the result,
	  * connecting ResourceSchmema to PropertyDeclarations over PropertyAssertion, check for consistency,
	  * converting given constraints or human readable semantics into system a system readable style and check the availability of requested resource-types.
	 */
	public RSParsingResult generateAndResolveSchemaModelThrough(InputStream is);
	
	// -----------------------------------------------------
	
	/**
	  * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	  * What does "resolve" in this context mean? It's the primary process of interpreting and parsing the result,
	  * connecting ResourceSchmema to PropertyDeclarations over PropertyAssertion, check for consistency,
	  * converting given constraints or human readable semantics into system a system readable style and check the availability of requested resource-types.
	 */
	public RSParsingResult generateAndResolveSchemaModelThrough(File f);
	
	// -----------------------------------------------------
	
	/**
	  * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	  * What does "resolve" in this context mean? It's the primary process of interpreting and parsing the result,
	  * connecting ResourceSchmema to PropertyDeclarations over PropertyAssertion, check for consistency,
	  * converting given constraints or human readable semantics into system a system readable style and check the availability of requested resource-types.
	 */
	public RSParsingResult generateAndResolveSchemaModelThrough(String s);
	
	// -----------------------------------------------------
	
	/**
	 * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	 * There will be with the exclusion of some grammar-syntax errors no further resolution and reasoning/interpreting-processes.
	 */
	public RSParsingResult generateSchemaModelThrough(InputStream is);
	
	// -----------------------------------------------------
	
	/**
	 * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	 * There will be with the exclusion of some grammar-syntax errors no further resolution and reasoning/interpreting-processes.
	 */
	public RSParsingResult generateSchemaModelThrough(File file);
	
	// -----------------------------------------------------
	
	/**
	 * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	 * There will be with the exclusion of some grammar-syntax errors no further resolution and reasoning/interpreting-processes.
	 */
	public RSParsingResult generateSchemaModelThrough(String s);
	
	// -----------------------------------------------------
	
	/**
	 * returns the ResourceSchema for a given ResourceID
	 */
	public ResourceSchema getResourceSchemaFor(ResourceID id);
	
	// -----------------------------------------------------
	
	/**
	 * Stores or overrides the given ResourceSchema
	 */
	public void storeOrOverrideResourceSchema(ResourceSchema schema);
	
	// -----------------------------------------------------
	
	/**
	 * Stores or overrides the given PropertyDeclaration 
	 */
	public void storeOrOverridePropertyDeclaration(PropertyDeclaration declaration);
	
	// -----------------------------------------------------
	
	/**
	 * Stores or overrides the given ResourceSchema's
	 */
	public void storeOrOverrideResourceSchema(Collection<ResourceSchema> schema);
	
	// -----------------------------------------------------
	
	/**
	 * Stores or overrides the given PropertyDeclaration's
	 */
	public void storeOrOverridePropertyDeclaration(Collection<PropertyDeclaration> declaration);
		
}
