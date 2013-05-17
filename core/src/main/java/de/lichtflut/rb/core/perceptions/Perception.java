/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.perceptions;

import java.util.ArrayList;
import java.util.List;

import de.lichtflut.rb.core.RBSystem;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.RB;

/**
 * <p>
 *  Representation of a perception of some subject.
 * </p>
 *
 * <p>
 *  Created 16.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class Perception extends ResourceView {

	public static Perception from(final SemanticNode node) {
		if (node instanceof Perception) {
			return (Perception) node;
		} else if (node instanceof ResourceNode) {
			return new Perception((ResourceNode) node);
		} else {
			return null;
		}
	}

	// ----------------------------------------------------

	/**
	 * Create a new perception.
	 */
	public Perception() {
		addAssociation(RDF.TYPE, RBSystem.PERCEPTION);
		checkSerialNumber();
	}

	/**
	 * Create a new perception.
	 */
	public Perception(final QualifiedName qn) {
		super(qn);
		addAssociation(RDF.TYPE, RBSystem.PERCEPTION);
		checkSerialNumber();
	}

	/**
	 * Create a new perception object wrapping given resource.
	 * @param resource The perception resource.
	 */
	public Perception(final ResourceNode resource) {
		super(resource);
		checkSerialNumber();
	}

	// ----------------------------------------------------

	public List<PerceptionItem> getItems() {
		final List<PerceptionItem> result = new ArrayList<PerceptionItem>();
		for (Statement assoc : getAssociations()) {
			if (assoc.getPredicate().equals(RBSystem.CONTAINS_PERCEPTION_ITEM)) {
				result.add(PerceptionItem.from(assoc.getObject()));
			}
		}
		return result;
	}

	public void addItem(final PerceptionItem item) {
		addAssociation(RBSystem.CONTAINS_PERCEPTION_ITEM, item);
	}

	public List<PerceptionItem> getTreeRootItems() {
		final List<PerceptionItem> result = new ArrayList<PerceptionItem>();
		for (Statement assoc : getAssociations()) {
			if (assoc.getPredicate().equals(RBSystem.CONTAINS_TREE_ROOT_ITEM) && assoc.getObject().isResourceNode()) {
				result.add(PerceptionItem.from(assoc.getObject()));
			}
		}
		return result;
	}

	public void addTreeRootItem(final PerceptionItem item) {
		addAssociation(RBSystem.CONTAINS_TREE_ROOT_ITEM, item);
	}

	// ----------------------------------------------------

	public String getID() {
		return stringValue(RB.HAS_ID);
	}

	public void setID(final String id) {
		setValue(RB.HAS_ID, id);
	}

	public String getName() {
		return stringValue(RB.HAS_NAME);
	}

	public void setName(final String name) {
		setValue(RB.HAS_NAME, name);
	}

	public String getDescription() {
		return stringValue(RB.HAS_DESCRIPTION);
	}

	public void setDescription(final String name) {
		setValue(RB.HAS_DESCRIPTION, name);
	}

	public Perception getBasePerception() {
		return Perception.from(SNOPS.fetchObject(this, RBSystem.BASED_ON));
	}

	public void setBasePerception(final Perception base) {
		setValue(RBSystem.BASED_ON, base);
	}

	public void setType(final ResourceID type){
		setValue(RDF.TYPE, type);
	}

	public ResourceID getType(){
		return resourceValue(RDF.TYPE);
	}

	public String getColor(){
		return stringValue(RBSystem.HAS_COLOR);
	}

	public void setColor(final String color){
		setValue(RBSystem.HAS_COLOR, color);
	}

	public String getImagePath(){
		return stringValue(RBSystem.HAS_IMAGE);
	}

	public void setImagePath(final String path){
		setValue(RBSystem.HAS_IMAGE, path);
	}

	public ResourceID getOwner(){
		return resourceValue(RB.HAS_OWNER);
	}

	public void setOwner(final ResourceID owner){
		setValue(RB.HAS_OWNER, owner);
	}

	public ResourceID getPersonResponsible(){
		return resourceValue(RB.HAS_RESPONSIBLE);
	}

	public void setPersonResponsible(final ResourceID person){
		setValue(RB.HAS_RESPONSIBLE, person);
	}

	// ----------------------------------------------------

	@Override
	public String toString() {
		return "Perception[" + getID() + "]";
	}

	public String printHierarchy() {
		StringBuilder sb = new StringBuilder("Hierarchy of ").append(this);
		for (PerceptionItem current : getTreeRootItems()) {
			sb.append("\n");
			sb.append(current.printHierarchy("  "));
		}
		return sb.toString();
	}

	// ------------------------------------------------------

	private void checkSerialNumber() {
		if(null == SNOPS.singleObject(this, Aras.HAS_SERIAL_NUMBER)){
			addAssociation(Aras.HAS_SERIAL_NUMBER, new SNValue(ElementaryDataType.INTEGER, System.currentTimeMillis()));
		}
	}
}
