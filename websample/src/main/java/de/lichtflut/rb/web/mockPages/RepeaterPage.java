/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.webck.components.IFeedbackContainerProvider;

/**
 * TestPage for Repeaters...
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class RepeaterPage extends WebPage implements IFeedbackContainerProvider {

	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public RepeaterPage(){
		RBEntityImpl entity = MockNewRBEntityFactory.createPerson();
		add(new FeedbackPanel("feedbackPanel").setOutputMarkupPlaceholderTag(true));
		RepeatingView view = new RepeatingView("container");
		for (RBField field : entity.getAllFields()) {
			view.add(new FieldSet(view.newChildId(), field));
		}
		Form form = new Form("form");
		form.add(view);
		add(form);
	}

	@Override
	public FeedbackPanel getFeedbackContainer() {
		return (FeedbackPanel) get("feedbackPanel");
	}
}
