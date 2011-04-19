/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
/**
 * 
 * [TODO Insert description here.]
 * 
 * Created: Apr 19, 2011
 *
 * @author [SPECIFY USER: Window-> Preferences]
 */
public interface ResourceSchemaManagement {

	public ResourceSchema generateSchemaModelThrough(InputStream is) throws IOException;
	public ResourceSchema generateSchemaModelThrough(File file) throws FileNotFoundException, IOException;
	public ResourceSchema generateSchemaModelThrough(String s);
	
	/**
	 * TODO: There shall be some more overloaded methods to be more flexible
	 */
	
	
}
