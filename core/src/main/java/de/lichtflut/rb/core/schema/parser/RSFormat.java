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
	private AbstractRSParsingUnit unit = null;

	/**
	 * Constructor.
	 * @param name -
	 * @param unit -
	 */
	private RSFormat(final String name, final AbstractRSParsingUnit unit){
		this.name= name;
		this.unit = unit;
	}

	// -----------------------------------------------------

	/**
	 * Returns ParsingUnit.
	 * @return {@link AbstractRSParsingUnit}
	 */
	public AbstractRSParsingUnit getParsingUnit(){
		return unit;
	}

	// -----------------------------------------------------

	/**
	 * Returns format name.
	 * @return String
	 */
	public String getFormatName(){
		return this.toString();
	}

	// -----------------------------------------------------

	/**
	 * Overriding toString method.
	 * @return String
	 */
	public String toString(){
		return name;
	}
}
