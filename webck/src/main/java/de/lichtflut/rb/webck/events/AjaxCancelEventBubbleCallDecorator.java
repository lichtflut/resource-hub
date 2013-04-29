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
package de.lichtflut.rb.webck.events;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxPostprocessingCallDecorator;

/**
 * <p>
 * Stop component from propagating an event to its parents.
 * </p>
 * Created: Feb 21, 2013
 *
 * @author Ravi Knox
 */
public class AjaxCancelEventBubbleCallDecorator extends AjaxPostprocessingCallDecorator {

	// ---------------- Constructor -------------------------

	public AjaxCancelEventBubbleCallDecorator()
	{
		this(null);
	}

	public AjaxCancelEventBubbleCallDecorator(final IAjaxCallDecorator delegate)
	{
		super(delegate);
	}

	// ------------------------------------------------------

	@Override
	public CharSequence postDecorateScript(final Component component, final CharSequence script) {
		return "e = event; if(e.stopPropagation) {e.stopPropagation();}else{e.cancelBubble = true;}" + script;
	}

}
