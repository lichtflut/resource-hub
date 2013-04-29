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
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.odlabs.wiquery.core.behavior.WiQueryAbstractBehavior;
import org.odlabs.wiquery.core.javascript.JsStatement;

/**
 * <p>
 *  Let's a component request the focus.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class FocusBehavior extends WiQueryAbstractBehavior {
	
	private boolean temporary = true;
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public JsStatement statement() {
		return new JsStatement().$(getComponent()).append(".focus()");
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public boolean isTemporary(Component component) {
		return temporary;
	}
	
}
