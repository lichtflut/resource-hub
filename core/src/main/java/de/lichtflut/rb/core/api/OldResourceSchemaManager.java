/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;

import org.arastreju.sge.model.ResourceID;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
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
public interface OldResourceSchemaManager extends Serializable{


	/**
	 * <p>
	 * Set the RSF  you just want to work with
	 * This is used for parsing and converting a given input into a ResourceSchema.
	 * </p>
	 * @param format - {@link RSFormat}
	 */
	void setFormat(RSFormat format);

	/**
	 * Get the {@link RSFormat}.
	 * @return The assigned {@link RSFormat}
	 */
	RSFormat getFormat();


	// -----------------------------------------------------


	/**
	 * Stores the schemaRepresentation for a given format.
	 * @param representation - the textstyle schema representation
	 * @param format - {@link RSFormat} to describe the format of the representation
	 */
	void storeSchemaRepresentation(String representation, RSFormat format);


	/**
	 * Returns a textual schema-reprenation of the given format.
	 * @param format - {@link RSFormat} to describe the format of the representation you want to load
	 * @return null if the format is null or unknown or if there is no representation for this schema available
	 */
	String loadSchemaRepresenation(RSFormat format);


	// -----------------------------------------------------

	/**
	  * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	  * What does "resolve" in this context mean? It's the primary process of interpreting and parsing the result,
	  * connecting ResourceSchmema to PropertyDeclarations over PropertyAssertion, check for consistency,
	  * converting the given constraints or human readable semantics into
	  * a system readable style and check the availability of requested resource-types.
	  * @param is - the {@link InputStream} which contains the ResourceSchema in the given {@link RSFormat}
	  * @return the {@link RSParsingResult}
	 */
	 RSParsingResult generateAndResolveSchemaModelThrough(InputStream is);

	// -----------------------------------------------------

	/**
	  * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	  * What does "resolve" in this context mean? It's the primary process of interpreting and parsing the result,
	  * connecting ResourceSchmema to PropertyDeclarations over PropertyAssertion, check for consistency,
	  * converting the given constraints or human readable semantics into
	  * a system readable style and check the availability of requested resource-types.
	  * @param file - the {@link File} which contains the ResourceSchema in the given {@link RSFormat}
	  * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateAndResolveSchemaModelThrough(File file);

	// -----------------------------------------------------

	/**
	  * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	  * What does "resolve" in this context mean? It's the primary process of interpreting and parsing the result,
	  * connecting ResourceSchmema to PropertyDeclarations over PropertyAssertion, check for consistency,
	  * converting the given constraints or human readable semantics into
	  * a system readable style and check the availability of requested resource-types.
	  * @param s - the {@link String} which contains the ResourceSchema in the given {@link RSFormat}
	  * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateAndResolveSchemaModelThrough(String s);

	// -----------------------------------------------------

	/**
	 * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	 * There will be with the exclusion of some grammar-syntax errors no further resolution and reasoning/interpreting-processes.
	 * @param is - the {@link InputStream} which contains the ResourceSchema in the given {@link RSFormat}
	 * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateSchemaModelThrough(InputStream is);

	// -----------------------------------------------------

	/**
	 * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	 * There will be with the exclusion of some grammar-syntax errors no further resolution and reasoning/interpreting-processes.
	 * @param file - the {@link File} which contains the ResourceSchema in the given {@link RSFormat}
	 * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateSchemaModelThrough(File file);

	// -----------------------------------------------------

	/**
	 * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	 * There will be with the exclusion of some grammar-syntax errors no further resolution and reasoning/interpreting-processes.
	 * @param s - the {@link String} which contains the ResourceSchema in the given {@link RSFormat}
	 * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateSchemaModelThrough(String s);

	// -----------------------------------------------------

	/**
	 * Returns a {@link ResourceSchema} for a given ResourceType.
	 * @param id - the {@link ResourceID} of the ResourceType
	 * @return the ResourceSchema for the given {@link ResourceID}
	 */
	ResourceSchema getResourceSchemaForResourceType(ResourceID id);

	// -----------------------------------------------------

	/**
	 * @return all the system-known ResourceSchemas
	 */
	Collection<ResourceSchema> getAllResourceSchemas();

	// -----------------------------------------------------

	/**
	 * @return all the system-known PropertyDeclarations
	 */
	Collection<PropertyDeclaration> getAllPropertyDeclarations();

	// -----------------------------------------------------



	/**
	 * Stores or overrides the given ResourceSchema with the given one.
	 * @param schema - the {@link ResourceSchema}
	 */
	void storeOrOverrideResourceSchema(ResourceSchema schema);

	// -----------------------------------------------------
	/**
	 * Stores or overrides the given ResourceSchema with the given one.
	 * @param declaration - the {@link PropertyDeclaration}
	 */
	void storeOrOverridePropertyDeclaration(PropertyDeclaration declaration);

	// -----------------------------------------------------

	/**
	 * Stores or overrides the given ResourceSchema's.
	 * @param schema - the {@link Collection} of {@link ResourceSchema}'s
	 */
	void storeOrOverrideResourceSchema(Collection<ResourceSchema> schema);

	// -----------------------------------------------------

	/**
	 * Stores or overrides the given PropertyDeclarations's.
	 * @param declarations - the {@link Collection} of {@link PropertyDeclaration}'s
	 */
	void storeOrOverridePropertyDeclaration(Collection<PropertyDeclaration> declarations);

}
