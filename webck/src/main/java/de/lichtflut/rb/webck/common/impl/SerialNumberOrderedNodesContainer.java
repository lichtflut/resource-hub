/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common.impl;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.structure.OrderBySerialNumber;

import de.lichtflut.rb.webck.common.OrderedNodesContainer;

/**
 * <p>
 *  Implementation of {@link OrderedNodesContainer} for resource nodes ordered by a serial number.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SerialNumberOrderedNodesContainer implements OrderedNodesContainer {

	private final IModel<List<? extends ResourceNode>> model;
	
	// ----------------------------------------------------
	
	/**
	 * @param model
	 */
	public SerialNumberOrderedNodesContainer(IModel<List<? extends ResourceNode>> model) {
		this.model = model;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void moveUp(ResourceNode node, int positions) {
		final List<ResourceNode> list = getList();
		int currentIndex = list.indexOf(node);
		int newIndex = currentIndex - positions;
		if (newIndex < 0) {
			newIndex = 0;
		}
		swap(node, list.get(newIndex));
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void moveDown(ResourceNode node, int positions) {
		final List<ResourceNode> list = getList();
		int currentIndex = list.indexOf(node);
		int newIndex = currentIndex + positions;
		if (newIndex >= list.size()) {
			newIndex = list.size() - 1;
		}
		swap(node, list.get(newIndex));
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void swap(ResourceNode a, ResourceNode b) {
		int aSN = getSerialNumber(a);
		int bSN = getSerialNumber(b);
		setSerialNumnber(a, bSN);
		setSerialNumnber(b, aSN);
		ensureCorrectness(getList());
	};
	
	// ----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	private List<ResourceNode> getList() {
		return (List<ResourceNode>) model.getObject();
	}
	
	private void setSerialNumnber(ResourceNode n, int sn) {
		SNOPS.assure(n, Aras.HAS_SERIAL_NUMBER, sn);
	}
	
	private int getSerialNumber(ResourceNode n) {
		SemanticNode sn = SNOPS.fetchObject(n, Aras.HAS_SERIAL_NUMBER);
		if (sn != null) {
			return sn.asValue().getIntegerValue().intValue();
		} else {
			return -1;
		}
	}
	
	private void ensureCorrectness(List<ResourceNode> list) {
		Collections.sort(list, new OrderBySerialNumber());
		int idx = 1;
		for (ResourceNode node : list) {
			SNOPS.assure(node, Aras.HAS_SERIAL_NUMBER, idx++);
		}
	}

}
