/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.SemanticGraph;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.ViewSpecTraverser;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.InformationExportDialog;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.components.widgets.ConfigurableWidget;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class AbstractWidgetConfigPanel extends TypedPanel<WidgetSpec> {
	
	@SpringBean
	protected ServiceProvider provider;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public AbstractWidgetConfigPanel(final String id, final IModel<WidgetSpec>  model) {
		super(id, model);
		
		final IModel<SemanticGraph> exportModel = new DerivedModel<SemanticGraph, WidgetSpec>(model) {
			@Override
			protected SemanticGraph derive(WidgetSpec spec) {
				return new ViewSpecTraverser().toGraph(spec);
			}
		};
		
		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new InformationExportDialog(hoster.getDialogID(), exportModel));
			}
		};
		add(exportLink);
		
		final Form form = new Form("form");
		form.add(new FeedbackPanel("feedback"));
		
		form.add(new TextField<String>("title", new PropertyModel<String>(model, "title")));
		
		form.add(new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				save(model.getObject());
				findParent(ConfigurableWidget.class).switchToDisplay();
			}
		});

		form.add(new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				cancel(model.getObject());
				findParent(ConfigurableWidget.class).switchToDisplay();
			}
		});
		
		add(form);
	}
	
	// ----------------------------------------------------
	
	protected final void save(WidgetSpec spec) {
		onSave(spec);
		provider.getArastejuGate().startConversation().attach(spec);
	}
	
	/**
	 * @param object
	 */
	protected void cancel(WidgetSpec spec) {
		provider.getArastejuGate().startConversation().reset(spec);
	}
	
	/**
	 * @param object
	 */
	protected void onSave(WidgetSpec spec) {
	}
	
	// ----------------------------------------------------

	protected Form<?> getForm() {
		return (Form<?>) get("form");
	}

}