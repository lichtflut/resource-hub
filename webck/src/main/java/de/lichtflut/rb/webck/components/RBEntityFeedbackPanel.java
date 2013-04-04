/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;

/**
 * <p>
 * Custom extension of {@link FeedbackPanel} for RBEntity validation.
 * </p>
 * Created: Apr 4, 2013
 *
 * @author Ravi Knox
 */
public class RBEntityFeedbackPanel extends FeedbackPanel {

	private final IModel<Map<Integer, List<RBField>>> model;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param model
	 */
	public RBEntityFeedbackPanel(final String id, final IModel<Map<Integer, List<RBField>>> model) {
		super(id);
		this.model = model;
	}

	@Override
	protected void onConfigure() {
		if(model == null || model.getObject() == null){
			error("Detected RBEntity validation Errors");
		}
	}

	// ------------------------------------------------------

	@Override
	protected FeedbackMessagesModel newFeedbackMessagesModel() {
		FeedbackMessagesModel model = new FeedbackMessagesModel(this);
		model.setObject(getFeedbackMessagesAsList());
		return model;
	}

	protected Component getReporter(){
		return getParent();
	}

	// ------------------------------------------------------

	private List<FeedbackMessage> getFeedbackMessagesAsList() {
		if(model == null || model.getObject() == null){
			return new ArrayList<FeedbackMessage>();
		}
		List<FeedbackMessage> list = new ArrayList<FeedbackMessage>();
		for (Integer errorCode : model.getObject().keySet()) {
			addErrorFor(errorCode, list);
		}
		return list;
	}

	private void addErrorFor(final Integer errorCode, final List<FeedbackMessage> list) {
		if(ErrorCodes.CARDINALITY_EXCEPTION == errorCode){
			List<RBField> fields = model.getObject().get(ErrorCodes.CARDINALITY_EXCEPTION);
			for (RBField field : fields) {
				String cardinalityAsString = CardinalityBuilder.getCardinalityAsString(field.getCardinality());
				FeedbackMessage message = new FeedbackMessage(getReporter(), new StringResourceModel("error.cardinality", new Model<String>(), field.getLabel(getLocale()), cardinalityAsString), FeedbackMessage.ERROR);
				list.add(message);
			}
		}

	}

	private String buildFeedbackMessage(final Map<Integer, List<RBField>> errors) {
		StringBuilder sb = new StringBuilder();
		sb.append(getString("error.validation"));
		sb.append("<ul>");
		for (Integer errorCode : errors.keySet()) {
			if(ErrorCodes.CARDINALITY_EXCEPTION == errorCode){
				sb.append("Cardinality is not as defined: ");
				List<RBField> fields = errors.get(ErrorCodes.CARDINALITY_EXCEPTION);
				for (RBField field : fields) {
					sb.append("<li>");
					sb.append("Cardinality of \"" + field.getLabel(getLocale()) + "\" is definened as: " + CardinalityBuilder.getCardinalityAsString(field.getCardinality()));
					sb.append("</li>");
				}
			}
		}
		sb.append("</ul>");
		return sb.toString();
	}

}
