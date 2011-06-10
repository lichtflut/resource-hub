/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import junit.framework.TestCase;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
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
        	
            SchemaFactory factory =  SchemaFactory.newInstance("xhtml");
            Schema schema = factory.newSchema(new StreamSource(getClass().getClassLoader().getResourceAsStream("xhtml1-strict.xsd")));
            Validator validator = schema.newValidator();
            Source source = new StreamSource(body);
            validator.validate(source);
            System.out.println(body + " is valid.");
        }
        catch (SAXException ex) {
            System.out.println(body + " is not valid because ");
            System.out.println(ex.getMessage());
        } catch (IOException e) {
			
			e.printStackTrace();
		}  


	}
}
