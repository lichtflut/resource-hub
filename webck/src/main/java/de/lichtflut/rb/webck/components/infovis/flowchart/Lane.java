/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import de.lichtflut.infra.Infra;

/**
 * <p>
 *  A lane in a flow chart.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class Lane {
	
	private String title;
	
	private String id;
	
	// ----------------------------------------------------

	/**
	 * @param title
	 * @param id
	 */
	public Lane(String title, String id) {
		this.title = title;
		this.id = id;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the id
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Lane) {
			Lane other = (Lane) obj;
			return Infra.equals(id, other.getID());
		} else {
			return super.equals(obj);
		}
	}

}
