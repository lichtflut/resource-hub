/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import junit.framework.TestCase;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

/**
 * <p>
 * 	This test is to make sure that RB-Components markup is xhtml-valid 
 * </p>
 * @author Nils Bleisch
 */
public class XHTMLValidationTest extends TestCase
{
	private WicketTester tester;
	private File htmlPage;

	@Override
	public void setUp()
	{
		tester = new WicketTester(new WebApplication(){
			protected void init(){
				getMarkupSettings().setStripWicketTags(true);
			}
			
			@Override
			public Class<? extends Page> getHomePage() {
				return TestComponentsPage.class;
			}
		});
	}

	//--------------------------------------
	
	public void testRenderMyPage()
	{
		//start and render the test page
		tester.startPage(TestComponentsPage.class);

		//assert rendered page class
		tester.assertRenderedPage(TestComponentsPage.class);

		
		String body = tester.getLastResponse().getDocument();

		//Try to validate the body against xhtml 1.0 strict
        try {
        	htmlPage = File.createTempFile("markup", ".test", new File("."));
        	FileWriter fw = new FileWriter(htmlPage);
        	fw.append(body).flush();

        	File schemaFile =  new File("src/test/resources/xhtml1-strict.xsd");
        	SchemaFactory factory =  SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        	
            Schema schema = factory.newSchema(schemaFile);

            
            
            
            Validator validator = schema.newValidator();
            validator.setResourceResolver(null);
            Source source = new StreamSource(htmlPage);
            
            validator.validate(source);
            
            assertTrue(true);
        }
        catch (SAXException ex) {
            System.out.println(body + " is not valid because ");
            System.out.println(ex.getMessage());
            assertTrue(false);
        } catch (IOException e) {	
			e.printStackTrace();
			assertTrue(false);
		}  
        finally{
        	htmlPage.delete();
        }

	}
}
