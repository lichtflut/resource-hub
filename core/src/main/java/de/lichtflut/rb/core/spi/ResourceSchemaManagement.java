/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import org.arastreju.sge.model.ResourceID;
import de.lichtflut.rb.core.schema.parser.RSParsingUnit.RSFormat;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
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
public interface ResourceSchemaManagement {
	

	
	public RSFormat getFormat();

	// -----------------------------------------------------
	
	public RSParsingResult generateAndResolveSchemaModelThrough(InputStream is);
	
	// -----------------------------------------------------
	
	public RSParsingResult generateAndResolveSchemaModelThrough(File f);
	
	// -----------------------------------------------------
	
	public RSParsingResult generateAndResolveSchemaModelThrough(String s);
	
	// -----------------------------------------------------
	
	public RSParsingResult generateSchemaModelThrough(InputStream is);
	
	// -----------------------------------------------------
	
	public RSParsingResult generateSchemaModelThrough(File file);
	
	// -----------------------------------------------------
	
	public RSParsingResult generateSchemaModelThrough(String s);
	
	// -----------------------------------------------------
	
	public ResourceSchema getResourceSchemaFor(ResourceID id);
	
	// -----------------------------------------------------
	
	
	public void storeOrOverrideResourceSchema(ResourceSchema schema);
	
	// -----------------------------------------------------
	
	
	public void storeOrOverridePropertyDeclaration(PropertyDeclaration declaration);
	
	// -----------------------------------------------------
	
	
	public void storeOrOverrideResourceSchema(Collection<ResourceSchema> schema);
	
	// -----------------------------------------------------
	
	
	public void storeOrOverridePropertyDeclaration(Collection<PropertyDeclaration> declaration);
		
}
