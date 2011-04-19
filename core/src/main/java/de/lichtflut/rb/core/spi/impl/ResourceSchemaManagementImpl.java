/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.impl.RBCaseControlStream;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaLexer;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser.dsl_return;
import de.lichtflut.rb.core.spi.ResourceSchemaManagement;

/**
 * [TODO Insert description here.]
 * 
 * Created: Apr 19, 2011
 *
 * @author [SPECIFY USER: Window-> Preferences] 
 */
public class ResourceSchemaManagementImpl implements ResourceSchemaManagement {

	public ResourceSchemaManagementImpl(){
		
	}
	
	public Set<ResourceSchemaType> generateSchemaModelThrough(InputStream is) throws IOException, RecognitionException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder bufferedInput = new StringBuilder();
		String line;
		while((line = reader.readLine())!=null) bufferedInput.append(line).append("\n");
		return generateSchemaModelThrough(bufferedInput.toString());
	}

	public Set<ResourceSchemaType> generateSchemaModelThrough(File file) throws FileNotFoundException, IOException, RecognitionException {
		return generateSchemaModelThrough(new FileInputStream(file));
	}

	public Set<ResourceSchemaType> generateSchemaModelThrough(String s) throws RecognitionException {
		RBCaseControlStream stream = new RBCaseControlStream(s);
		//Ignore Case, this is really important
		stream.setCaseSensitive(false);
		ResourceSchemaLexer lexer = new ResourceSchemaLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		ResourceSchemaParser parser = new ResourceSchemaParser(tokens);
		dsl_return result;
		result = parser.dsl();
		return result.types;
	}

}
