/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Reader for {@link AlternateNameRecord}s.
 * 
 * Created: 25.05.2009
 *
 * @author Oliver Tigges
 */
public class AlternateNamesReader {

	public static final String FIELD_DELIM = "\t";

	private LineReader lineReader;

	private final String file;

	// -----------------------------------------------------

	public AlternateNamesReader(String file) throws IOException {
		this.file = file;
		reset();
	}

	// -----------------------------------------------------
	
	public Map<Long, AlternateNameRecord> mapForLanguage(String language) throws IOException{
		Map<Long, AlternateNameRecord> map = new HashMap<Long, AlternateNameRecord>();
		while (hasNext()){
			AlternateNameRecord record = next();
			if (language.equals(record.getLanguage())){
				map.put(record.getGeonameid(), record);
			}
		}
		return map;
	}
	
	public void reset() throws IOException{
		this.lineReader = new LineReader(file, "UTF-8");
	}
	
	public boolean hasNext() throws IOException{
		return lineReader.hasNext();
	}

	/**
	 * 1375153 3214034 de      Werouzach
	 * 
	 * @return
	 */
	public AlternateNameRecord next() {
		String line = lineReader.nextLine();
		
		String[] fields = line.split(FIELD_DELIM);
		//Log.debug(this, "line: " + line);
		int i = 0;
		String alternateid = fields[i++];
		String geonameid = fields[i++];
		String language = fields[i++];
		String name = fields[i++];
		return new AlternateNameRecord(alternateid, geonameid, language, name);
	}

	// -----------------------------------------------------

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LineReader in = new LineReader("/Users/otigges/temp/alternateNames/alternateNames.txt", "UTF-8");
			FileWriter out = new FileWriter("/Users/otigges/temp/alternateNames/alternateNames-filtered.txt");
			while (in.hasNext()) {
				String line = in.nextLine();
				String[] fields = line.split(FIELD_DELIM);
				String language = fields[2];
				if ("en".equals(language) || "de".equals(language)) {
					out.write(line + "\n");
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
