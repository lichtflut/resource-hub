/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Provider for a container for feedback messages.
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
public interface IFeedbackContainerProvider {

	/**
	 * @return The container for feedback.
	 */
	FeedbackPanel getFeedbackContainer();

}
