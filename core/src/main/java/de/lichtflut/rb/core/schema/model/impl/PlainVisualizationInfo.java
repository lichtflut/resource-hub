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
package de.lichtflut.rb.core.schema.model.impl;

import java.io.Serializable;

import de.lichtflut.rb.core.schema.model.VisualizationInfo;

/**
 * <p>
 *  Simple implementation of a field's visualization information.
 * </p>
 *
 * <p>
 *  Created 10.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PlainVisualizationInfo implements VisualizationInfo, Serializable {

	private boolean embedded = false;
	private boolean floating = false;
	private String style = "";

	// ----------------------------------------------------

	/**
	 * Default constructor. Creates a non embedded, not floating info.
	 */
	public PlainVisualizationInfo() {
	}

	/**
	 * Copy constructor.
	 */
	public PlainVisualizationInfo(final VisualizationInfo other) {
		this.embedded = other.isEmbedded();
		this.floating = other.isFloating();
		this.style = other.getStyle();
	}

	// ----------------------------------------------------

	@Override
	public boolean isEmbedded() {
		return embedded;
	}

	@Override
	public boolean isFloating() {
		return floating;
	}

	@Override
	public String getStyle() {
		return style;
	}

	// ----------------------------------------------------


	public void setEmbedded(final boolean embedded) {
		this.embedded = embedded;
	}

	public void setFloating(final boolean floating) {
		this.floating = floating;
	}

	public void setStyle(final String style) {
		this.style = style;
	}

	// ----------------------------------------------------

	@Override
	public String toString() {
		return "visualize {" +
				"embedded=" + embedded +
				", floating=" + floating +
				", style='" + style + '\'' +
				'}';
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (embedded ? 1231 : 1237);
		result = prime * result + (floating ? 1231 : 1237);
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PlainVisualizationInfo)) {
			return false;
		}
		PlainVisualizationInfo other = (PlainVisualizationInfo) obj;
		if (embedded != other.embedded) {
			return false;
		}
		if (floating != other.floating) {
			return false;
		}
		if (style == null) {
			if (other.style != null) {
				return false;
			}
		} else if (!style.equals(other.style)) {
			return false;
		}
		return true;
	}

}
