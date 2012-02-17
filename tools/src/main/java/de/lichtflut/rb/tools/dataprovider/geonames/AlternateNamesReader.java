/**
 * Copyright 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.tools.dataprovider.geonames;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.lichtflut.infra.io.LineReader;

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
			AlternateNamesReader gnr = new AlternateNamesReader("input/temp/alternateNames.txt");
			int counter = 0;
			while (gnr.hasNext()){
				AlternateNameRecord record = gnr.next();
				if ("de".equals(record.getLanguage())){
					System.out.println(record.toDebugString());
					counter++;
				}
			}
			System.out.println(counter);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
