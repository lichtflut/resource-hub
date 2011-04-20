/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.antlr.runtime.RecognitionException;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
/**
 * 
 * [TODO Insert description here.]
 * 
 * Created: Apr 19, 2011
 *
 * @author [SPECIFY USER: Window-> Preferences]
 */
public interface ResourceSchemaManagement {

	public Set<ResourceSchemaType> generateSchemaModelThrough(InputStream is) throws  IOException, RecognitionException ;
	public Set<ResourceSchemaType> generateSchemaModelThrough(File file) throws FileNotFoundException, IOException,RecognitionException ;
	public Set<ResourceSchemaType> generateSchemaModelThrough(String s) throws RecognitionException ;
	
	/**
	 * TODO: There shall be some more overloaded methods to be more flexible
	 */
	
	
}
