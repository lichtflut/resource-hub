/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.graph;

/**
 * <p>
 *  Represents a PeripheryGraph.
 * </p>
 *
 * <p>
 * 	Created Feb 4, 2011
 * </p>
 *
 * @author Oliver Tigges
 */

public class PeripheryGraph {


	@SuppressWarnings("rawtypes")
	private Vertex root;

	// -----------------------------------------------------

	/**
	 * @return the root
	 */
	@SuppressWarnings("rawtypes")
	public Vertex getRoot() {
		return root;
	}

	// -----------------------------------------------------

	/**
	 * @param root - the root to set
	 */
	@SuppressWarnings("rawtypes")
	public void setRoot(final Vertex root) {
		this.root = root;
	}

	// -----------------------------------------------------

	/**
	 *
	 */
	public void autoRoot(){

	}

}
