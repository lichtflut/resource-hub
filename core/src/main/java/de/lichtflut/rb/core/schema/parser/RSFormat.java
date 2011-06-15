/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import de.lichtflut.rb.core.schema.parser.impl.osf.OSFParsingUnit;
import de.lichtflut.rb.core.schema.parser.impl.simplersf.SimpleRSFParsingUnit;

/**
 *
 * An enum which represents and containts all the system registered types of RSF's
 *
 * Created: Apr 28, 2011
 *
 * <p>
 *  TODO: Move to base package?
 * </p>
 *
 * @author Nils Bleisch
 */
public enum RSFormat {


	//If you have a RSParsingUnit for a new RSF, register it here!
	/**
	 * RS-format SIMPLE_RSF.
	 */
	SIMPLE_RSF("simple-rdf", new SimpleRSFParsingUnit()),
	/**
	 * RS-format OSF.
	 */
	OSF("Oliver Tigges Simple Format", new OSFParsingUnit());

	private String name = "undefined";
	private RSParsingUnitFactory unit = null;
	private RSFormat(String name, RSParsingUnitFactory unit){
		this.name= name;
		this.unit = unit;
	}

	// -----------------------------------------------------

	public RSParsingUnitFactory getParsingUnit(){
		return unit;
	}

	// -----------------------------------------------------

	public String getFormatName(){
		return this.toString();
	}

	// -----------------------------------------------------

	public String toString(){
		return name;
	}
}
