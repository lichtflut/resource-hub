/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.net.URL;

import org.apache.wicket.model.IModel;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import de.lichtflut.rb.core.eh.RBException;

/**
 * <p>
 *  This model provides a safe way to handle Strings that might contain HTML or Javascript.
 *  <br>
 *  If an Exception is thrown while attempting to initialize the HTML-Sanitizer, it can be dealed with
 *  in the handleException() method.
 * </p>
 *
 * <p>
 * 	Created Feb 8, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class HTMLSafeModel implements IModel<String> {

	private final static String RTE_POLICY_LOCATION = "antisamy/antisamy-tinymce-1.4.4.xml";

	private IModel<String> model;
	
	// ---------------- Constructor -------------------------

	public HTMLSafeModel(IModel<String> model){
		this.model = model;
	}
	
	// ------------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getObject() {
		return parseContent(model.getObject());
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(String object) {
			this.model.setObject(parseContent(object));
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {}
	
	// ------------------------------------------------------
	
	/**
	 * @param content
	 * @param url
	 * @return
	 */
	private String parseContent(String content) {
		Policy policy;
		CleanResults cr;
		if(content == null) {
			return content;
		}
		try {
			policy = getPolicy();
			cr = new AntiSamy().scan(content.toString(), policy);
			return cr.getCleanHTML();
		} catch (PolicyException e) {
			throwRBExepction("RichTextEditor Policy cannot be found");
		} catch (ScanException e) {
			throwRBExepction("Error while scanning content of RichTextEditor");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return
	 * @throws PolicyException 
	 */
	private Policy getPolicy() throws PolicyException {
		URL url = Thread.currentThread().getContextClassLoader().getResource(RTE_POLICY_LOCATION);
		return Policy.getInstance(url);
	}
	
	/**
	 * @throws RBException
	 */
	private void throwRBExepction(String msg) {
		try {
			throw new RBException(msg);
		} catch (RBException e) {
			handleException(e);
		}
	}

	/**
	 * A possible Exception can be handled here.
	 * @param e
	 */
	protected void handleException(RBException e) {
		e.printStackTrace();
	}

}
