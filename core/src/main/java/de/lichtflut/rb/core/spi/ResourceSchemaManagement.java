/*
 * Copyright (C) 2010 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
