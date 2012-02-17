/*
 * Copyright (C) 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
package de.lichtflut.rb.tools.dataprovider.geonames;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Provides an input source line by line.
 * 
 * Created: 22.05.2009
 * 
 * @author Oliver Tigges
 */
public class LineReader {

	protected final BufferedReader reader;
	private String line;

	// -----------------------------------------------------

	public LineReader(InputStream in, String encoding) throws IOException {
		this.reader = new BufferedReader(new InputStreamReader(in, encoding));
	}
	
	public LineReader(String file, String encoding) throws IOException{
		this(new FileInputStream(file), encoding);
	}

	// -----------------------------------------------------

	public boolean hasNext() throws IOException {
		return ((line = reader.readLine()) != null);
	}

	public String nextLine() {
		return line;
	}
	
	public void close() throws IOException {
		reader.close();
	}

}