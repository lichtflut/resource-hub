/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBField;

/**
 * <p>
 * [TODO Insert description here.]
 * </p>
 * Created: Apr 4, 2013
 *
 * @author Ravi Knox
 */
public class RBEntityFeedbackMessagesModel extends FeedbackMessagesModel {

	/**
	 * @param component
	 */
	public RBEntityFeedbackMessagesModel(Component component) {
		super(component);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param page
	 * @param filter
	 */
	public RBEntityFeedbackMessagesModel(Page page, IFeedbackMessageFilter filter) {
		super(page, filter);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setObject(final Map<Integer, List<RBField>> object) {
		// TODO Auto-generated method stub
		super.setObject(object);
	}

}
