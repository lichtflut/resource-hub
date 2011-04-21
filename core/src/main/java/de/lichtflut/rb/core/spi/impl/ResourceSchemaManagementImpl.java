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
import org.arastreju.sge.model.ResourceID;

import com.sun.java.swing.plaf.nimbus.RadioButtonPainter;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.impl.RBCaseControlStream;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultImpl;
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
	
	public RSParsingResult generateSchemaModelThrough(InputStream is){
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder bufferedInput = new StringBuilder();
		String line;
		try {
			while((line = reader.readLine())!=null) bufferedInput.append(line).append("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generateSchemaModelThrough(bufferedInput.toString());
	}

	public RSParsingResult generateSchemaModelThrough(File file){
		try {
			return generateSchemaModelThrough(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			RSParsingResultImpl result = new RSParsingResultImpl();
			result.addErrorMessage("File " + file.getAbsolutePath() + " was not found");
			return result;
		}
	}

	public RSParsingResult generateSchemaModelThrough(String s) {
		RBCaseControlStream stream = new RBCaseControlStream(s);
		//Ignore Case, this is really important
		stream.setCaseSensitive(false);
		ResourceSchemaLexer lexer = new ResourceSchemaLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		ResourceSchemaParser parser = new ResourceSchemaParser(tokens);
		dsl_return result;
		//result = parser.dsl();
		return null;
	}
	
	public RSParsingResult generateSchemaModelThrough(ResourceID id){
		return null;
	}

	public RSParsingResult generateAndResolveSchemaModelThrough(InputStream is) {
		// TODO Auto-generated method stub
		return null;
	}

	public RSParsingResult generateAndResolveSchemaModelThrough(File f) {
		// TODO Auto-generated method stub
		return null;
	}

	public RSParsingResult generateAndResolveSchemaModelThrough(String s) {
		// TODO Auto-generated method stub
		return null;
	}

}
