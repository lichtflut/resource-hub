/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
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
public class RBEntityFeedbackPanel extends Panel implements IFeedback {

	private final IModel<Map<Integer, List<RBField>>> model;

	private final MessageListView messageListView;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param model
	 */
	public RBEntityFeedbackPanel(final String id, final IModel<Map<Integer, List<RBField>>> model) {
		this(id, model, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param model
	 */
	public RBEntityFeedbackPanel(final String id, final IModel<Map<Integer, List<RBField>>> model,
			final IFeedbackMessageFilter filter) {
		super(id);
		this.model = model;

		WebMarkupContainer messagesContainer = new WebMarkupContainer("feedbackul") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(anyMessage());
			}
		};
		add(messagesContainer);
		messageListView = new MessageListView("messages");
		messageListView.setVersioned(false);
		messagesContainer.add(messageListView);

		if (filter != null) {
			setFilter(filter);
		}
	}

	// ------------------------------------------------------

	protected IModel<List<FeedbackMessage>> newFeedbackMessagesModel() {
		return new LoadableDetachableModel<List<FeedbackMessage>>() {

			@Override
			protected List<FeedbackMessage> load() {
				return getFeedbackMessagesAsList();
			}
		};

	}

	protected Component getReporter() {
		return getParent();
	}

	// ------------------------------------------------------

	private List<FeedbackMessage> getFeedbackMessagesAsList() {
		if (model == null || model.getObject() == null) {
			return new ArrayList<FeedbackMessage>();
		}
		List<FeedbackMessage> list = new ArrayList<FeedbackMessage>();
		for (Integer errorCode : model.getObject().keySet()) {
			addErrorFor(errorCode, list);
		}
		return list;
	}

	private void addErrorFor(final Integer errorCode, final List<FeedbackMessage> list) {
		if (ErrorCodes.CARDINALITY_EXCEPTION == errorCode) {
			List<RBField> fields = model.getObject().get(ErrorCodes.CARDINALITY_EXCEPTION);
			for (RBField field : fields) {
				String cardinalityAsString = CardinalityBuilder.getCardinalityAsString(field.getCardinality());
				Object[] parameter = {field.getLabel(getLocale()), cardinalityAsString};
				String resourceString = new StringResourceModel("error.cardinality", this, new Model<String>(), parameter).getString();
				FeedbackMessage message = new FeedbackMessage(getReporter(), resourceString, FeedbackMessage.ERROR);
				list.add(message);
			}
		}

	}

	// ------------------------------------------------------

	/**
	 * List for messages.
	 */
	private final class MessageListView extends ListView<FeedbackMessage> {
		private static final long serialVersionUID = 1L;

		/**
		 * @see org.apache.wicket.Component#Component(String)
		 */
		public MessageListView(final String id) {
			super(id, getFeedbackMessagesAsList());
			setDefaultModel(newFeedbackMessagesModel());
		}

		//		@Override
		//		protected IModel<FeedbackMessage> getListItemModel(final IModel<? extends List<FeedbackMessage>> listViewModel,
		//				final int index) {
		//			return new AbstractReadOnlyModel<FeedbackMessage>() {
		//				private static final long serialVersionUID = 1L;
		//
		//				/**
		//				 * WICKET-4258 Feedback messages might be cleared already.
		//				 *
		//				 * @see WebSession#cleanupFeedbackMessages()
		//				 */
		//				@Override
		//				public FeedbackMessage getObject() {
		//					//					if (model == null || model.getObject() == null) {
		//					//						return null;
		//					//					}
		//					if (index >= listViewModel.getObject().size()) {
		//						return null;
		//					} else {
		//						return getFeedbackMessagesAsList().get(index);
		//					}
		//				}
		//			};
		//		}

		@Override
		protected void populateItem(final ListItem<FeedbackMessage> listItem) {
			final IModel<String> replacementModel = new Model<String>() {
				private static final long serialVersionUID = 1L;

				/**
				 * Returns feedbackPanel + the message level, eg 'feedbackPanelERROR'. This is used
				 * as the class of the li / span elements.
				 * 
				 * @see org.apache.wicket.model.IModel#getObject()
				 */
				@Override
				public String getObject() {
					return getCSSClass(listItem.getModelObject());
				}
			};

			final FeedbackMessage message = listItem.getModelObject();
			message.markRendered();
			final Component label = newMessageDisplayComponent("message", message);
			final AttributeModifier levelModifier = new AttributeModifier("class", replacementModel);
			label.add(levelModifier);
			listItem.add(levelModifier);
			listItem.add(label);
		}
	}

	/**
	 * Search messages that this panel will render, and see if there is any message of level ERROR
	 * or up. This is a convenience method; same as calling 'anyMessage(FeedbackMessage.ERROR)'.
	 * 
	 * @return whether there is any message for this panel of level ERROR or up
	 */
	public final boolean anyErrorMessage() {
		return anyMessage(FeedbackMessage.ERROR);
	}

	/**
	 * Search messages that this panel will render, and see if there is any message.
	 * 
	 * @return whether there is any message for this panel
	 */
	public final boolean anyMessage() {
		return anyMessage(FeedbackMessage.UNDEFINED);
	}

	/**
	 * Search messages that this panel will render, and see if there is any message of the given
	 * level.
	 * 
	 * @param level the level, see FeedbackMessage
	 * @return whether there is any message for this panel of the given level
	 */
	public final boolean anyMessage(final int level) {
		List<FeedbackMessage> msgs = getCurrentMessages();

		for (FeedbackMessage msg : msgs) {
			if (msg.isLevel(level)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @return Model for feedback messages on which you can install filters and other properties
	 */
	public final FeedbackMessagesModel getFeedbackMessagesModel() {
		return (FeedbackMessagesModel) messageListView.getDefaultModel();
	}

	/**
	 * @return The current message filter
	 */
	public final IFeedbackMessageFilter getFilter() {
		return getFeedbackMessagesModel().getFilter();
	}

	/**
	 * @return The current sorting comparator
	 */
	public final Comparator<FeedbackMessage> getSortingComparator() {
		return getFeedbackMessagesModel().getSortingComparator();
	}

	/**
	 * @see org.apache.wicket.Component#isVersioned()
	 */
	@Override
	public boolean isVersioned() {
		return false;
	}

	/**
	 * Sets a filter to use on the feedback messages model
	 * 
	 * @param filter The message filter to install on the feedback messages model
	 * 
	 * @return FeedbackPanel this.
	 */
	public final RBEntityFeedbackPanel setFilter(final IFeedbackMessageFilter filter) {
		getFeedbackMessagesModel().setFilter(filter);
		return this;
	}

	/**
	 * @param maxMessages The maximum number of feedback messages that this feedback panel should
	 *        show at one time
	 * 
	 * @return FeedbackPanel this.
	 */
	public final RBEntityFeedbackPanel setMaxMessages(final int maxMessages) {
		messageListView.setViewSize(maxMessages);
		return this;
	}

	/**
	 * Sets the comparator used for sorting the messages.
	 * 
	 * @param sortingComparator comparator used for sorting the messages.
	 * 
	 * @return FeedbackPanel this.
	 */
	public final RBEntityFeedbackPanel setSortingComparator(final Comparator<FeedbackMessage> sortingComparator) {
		getFeedbackMessagesModel().setSortingComparator(sortingComparator);
		return this;
	}

	/**
	 * Gets the css class for the given message.
	 * 
	 * @param message the message
	 * @return the css class; by default, this returns feedbackPanel + the message level, eg
	 *         'feedbackPanelERROR', but you can override this method to provide your own
	 */
	protected String getCSSClass(final FeedbackMessage message) {
		return "feedbackPanel" + message.getLevelAsString();
	}

	/**
	 * Gets the currently collected messages for this panel.
	 * 
	 * @return the currently collected messages for this panel, possibly empty
	 */
	protected final List<FeedbackMessage> getCurrentMessages() {
		return getFeedbackMessagesAsList();
	}

	/**
	 * Generates a component that is used to display the message inside the feedback panel. This
	 * component must handle being attached to <code>span</code> tags.
	 * 
	 * By default a {@link Label} is used.
	 * 
	 * Note that the created component is expected to respect feedback panel's
	 * {@link #getEscapeModelStrings()} value
	 * 
	 * @param id parent id
	 * @param message feedback message
	 * @return component used to display the message
	 */
	protected Component newMessageDisplayComponent(final String id, final FeedbackMessage message) {
		Serializable serializable = message.getMessage();
		Label label = new Label(id, (serializable == null) ? "" : serializable.toString());
		label.setEscapeModelStrings(RBEntityFeedbackPanel.this.getEscapeModelStrings());
		return label;
	}

}
