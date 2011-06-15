package de.lichtflut.rb.web.components;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * <p>
 * This Page should include all available RBComponents 
 * </p>
 * @author Nils Bleisch
 *
 */
@SuppressWarnings("serial")
public class TestComponentsPage extends WebPage{

	public TestComponentsPage(){
		super();
	}
	
	public TestComponentsPage(PageParameters params){
		super(params);
	}
	
	
}
