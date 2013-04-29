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
package de.lichtflut.rb.application.admin.infomgmt;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

import de.lichtflut.rb.application.admin.AdminBasePage;
import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.infomanagement.InformationIOPanel;
import de.lichtflut.rb.webck.components.typesystem.SchemaIOPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 *  Page presenting the components for management of type system.
 * </p>
 *
 * <p>
 * 	Created Sep 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class InformationManagementPage extends AdminBasePage {

	private final Model<String> model = new Model<String>();
	private AjaxSelfUpdatingTimerBehavior timerBehavior;
	private final WebMarkupContainer messageContainer;

    // ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public InformationManagementPage() {

		add(new InformationIOPanel("infoIO"));
		
		add(new SchemaIOPanel("schemaIO"));
		
		messageContainer = new WebMarkupContainer("messageContainer");
		
		Label reportLabel = new Label("ioreport", model);
		reportLabel.setEscapeModelStrings(false);
		
		messageContainer.add(reportLabel);
		messageContainer.setOutputMarkupId(true);
		
		add(messageContainer);
	}

    // ----------------------------------------------------
	
	@Override
	public void onEvent(IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		
		if (mce.isAbout(ModelChangeEvent.START_TIMER_BEHAVIOR)) {
			// avoids that the stopped behavior from previous export stays at the component
			if(timerBehavior != null) {
				messageContainer.remove(timerBehavior);
			}
			timerBehavior = new AjaxSelfUpdatingTimerBehavior(Duration.milliseconds(500));
			messageContainer.add(timerBehavior);
			RBAjaxTarget.add(messageContainer);
		}
		
		if (mce.isAbout(ModelChangeEvent.IO_REPORT)) {
			model.setObject(((IOReport)mce.getPayload()).toHTML());
			// updating behavior can be stopped when report has arrived
			if(timerBehavior != null) {
				timerBehavior.stop();
			}
			RBAjaxTarget.add(messageContainer);
		}
	}
	
}
