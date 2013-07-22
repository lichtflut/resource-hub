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
package de.lichtflut.rb.core.security;

import java.io.Serializable;

import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Representation of an RB domain.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBDomain implements Serializable {

	private final QualifiedName qn;

	private String name;

	private String title;

	private String description;

	// ----------------------------------------------------

	/**
	 * Creates a new domain.
	 */
	public RBDomain() {
		this(new SimpleResourceID().getQualifiedName());
	}

	/**
	 * Creates a new domain.
	 * @param qn The unique ID/URI of the domain.
	 */
	public RBDomain(final QualifiedName qn) {
		this.qn = qn;
	}

	// ----------------------------------------------------

	/**
	 * @return the QualifiedName.
	 */
	public QualifiedName getQualifiedName() {
		return qn;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	// ----------------------------------------------------

	@Override
	public String toString() {
		return "RBDomain [qn=" + qn + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return qn.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof RBDomain) {
			RBDomain other = (RBDomain) obj;
			return this.qn.equals(other.qn);
		}
		return super.equals(obj);
	}

}
