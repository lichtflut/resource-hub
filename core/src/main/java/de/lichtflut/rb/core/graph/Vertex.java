/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.graph;

import java.util.ArrayList;
import java.util.List;

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
public class Vertex<T> {

	private final T data;
	
	private final List<Edge> edges = new ArrayList<Edge>(); 

	// -----------------------------------------------------
	
	public Vertex(final T data) {
		this.data = data;
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * @return the edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}
	
}
