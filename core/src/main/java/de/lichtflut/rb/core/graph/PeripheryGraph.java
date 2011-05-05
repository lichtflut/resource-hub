/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.graph;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Feb 4, 2011
 * </p>
 *
 * @author Oliver Tigges
 */

public class PeripheryGraph {
	
	@SuppressWarnings("unchecked")
	private Vertex root;
	
	// -----------------------------------------------------
	
	/**
	 * @return the root
	 */
	@SuppressWarnings("unchecked")
	public Vertex getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	@SuppressWarnings("unchecked")
	public void setRoot(Vertex root) {
		this.root = root;
	}
	
	public void autoRoot() {
		
	}
	
}
