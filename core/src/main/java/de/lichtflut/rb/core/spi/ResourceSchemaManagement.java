/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi;

import java.io.File;
import java.io.InputStream;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
/**
 * 
 * [TODO Insert description here.]
 * 
 * Created: Apr 19, 2011
 *
 * @author [SPECIFY USER: Window-> Preferences]
 */
public interface ResourceSchemaManagement {

	public RSParsingResult generateAndResolveSchemaModelThrough(InputStream is);
	
	public RSParsingResult generateAndResolveSchemaModelThrough(File f);
	
	public RSParsingResult generateAndResolveSchemaModelThrough(String s);
	
	public RSParsingResult generateSchemaModelThrough(InputStream is);
	
	public RSParsingResult generateSchemaModelThrough(File file);
	
	public RSParsingResult generateSchemaModelThrough(String s);
	
	
	/**
	 * TODO: There shall be some more overloaded methods to be more flexible
	 */
	
	
}
