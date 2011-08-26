/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;

import de.lichtflut.rb.core.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.impl.NewRBEntity;
import de.lichtflut.rb.web.components.IFeedbackContainerProvider;

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
	public RepeaterPage(){
		NewRBEntity entity = MockNewRBEntityFactory.createNewRBEntity();
		add(new FeedbackPanel("feedbackPanel").setOutputMarkupPlaceholderTag(true));
		RepeatingView view = new RepeatingView("container");
		for (IRBField field : entity.getAllFields()) {
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
