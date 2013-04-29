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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;
import de.lichtflut.rb.webck.common.RBWebSession;

/**
 * <p>
 * Customized Feedbackpanel for RBEntity validation.
 * </p>
 * Created: Apr 4, 2013
 * 
 * @author Ravi Knox
 */
// This is basically a copy of {@link FeedbackPanel}, which cannot be extended due to final methods.
public class RBEntityFeedbackPanel extends Panel implements IFeedback {

	@SpringBean
	private EntityManager entityManager;

	private final MessageListView messageListView;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id Component id
	 */
	public RBEntityFeedbackPanel(final String id) {
		this(id, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param id Component id
	 * @param filter Filter for this FeedbackPanel
	 */
	public RBEntityFeedbackPanel(final String id, final IFeedbackMessageFilter filter) {
		super(id);

		WebMarkupContainer messagesContainer = new WebMarkupContainer("feedbackul") {
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

	/**
	 * Gets the currently collected messages for this panel.
	 * 
	 * @return the currently collected messages for this panel, possibly empty
	 */
	protected final List<FeedbackMessage> getCurrentMessages() {
		return getFeedbackMessagesAsList();
	}

	// ------------------------------------------------------

	/**
	 * Retrieve Validation-Errors of the current Entity in the {@link BrowsingHistory}.
	 * 
	 * @return a list of Feedbackmessages
	 */
	private List<FeedbackMessage> getFeedbackMessagesAsList() {
		List<FeedbackMessage> list = new ArrayList<FeedbackMessage>();
		RBEntity entity = getCurrentEntity();
		if (entity == null) {
			return list;
		}
		Map<Integer, List<RBField>> errors = entityManager.validate(entity);
		for (Integer errorCode : errors.keySet()) {
			addErrorFor(errorCode, errors, list);
		}
		return list;
	}

	private RBEntity getCurrentEntity() {
		EntityHandle handle = getHandle();
		if (null == handle) {
			return null;
		}
		RBEntity entity = entityManager.find(handle.getId());
		return entity;
	}

	private EntityHandle getHandle() {
		EntityBrowsingStep step = RBWebSession.get().getHistory().getCurrentStep();
		if (step == null) {
			return null;
		}
		EntityHandle handle = step.getHandle();
		if (!handle.hasId()) {
			return null;
		}
		return handle;
	}

	private void addErrorFor(final Integer errorCode, final Map<Integer, List<RBField>> errors,
			final List<FeedbackMessage> list) {
		if (ErrorCodes.CARDINALITY_EXCEPTION == errorCode) {
			List<RBField> fields = errors.get(ErrorCodes.CARDINALITY_EXCEPTION);
			for (RBField field : fields) {
				FeedbackMessage message = createCardinalityErrorMessage(field);
				list.add(message);
			}
		}
	}

	private FeedbackMessage createCardinalityErrorMessage(final RBField field) {
		String cardinalityAsString = CardinalityBuilder.getCardinalityAsString(field.getCardinality());
		Object[] parameter = { field.getLabel(getLocale()), cardinalityAsString };
		String resourceString = new StringResourceModel("error.cardinality", this, new Model<String>(), parameter)
		.getString();
		FeedbackMessage message = new FeedbackMessage(getReporter(), resourceString, FeedbackMessage.ERROR);
		return message;
	}

	// -------------- COPY OF {@link FeedbackPanel} ---------

	/**
	 * List for messages.
	 */
	private final class MessageListView extends ListView<FeedbackMessage> {
		private static final long serialVersionUID = 1L;

		public MessageListView(final String id) {
			super(id, getFeedbackMessagesAsList());
			setDefaultModel(newFeedbackMessagesModel());
		}

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
