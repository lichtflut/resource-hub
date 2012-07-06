/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.admin.organizer;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.naming.Namespace;
import org.odlabs.wiquery.ui.dialog.Dialog;

import de.lichtflut.rb.application.admin.AdminBasePage;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.dialogs.CreateContextDialog;
import de.lichtflut.rb.webck.components.dialogs.CreateNamespaceDialog;
import de.lichtflut.rb.webck.components.organizer.ContextOverviewPanel;
import de.lichtflut.rb.webck.components.organizer.NamespaceOverviewPanel;
import de.lichtflut.rb.webck.components.organizer.SetDomainOrganizationPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.DomainOrganizationModel;

/**
 * <p>
 *  Page for organization of the domain, the contexts and the namespaces.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class OrganizerPage extends AdminBasePage {

	@SpringBean
	private DomainOrganizer domainOrganizer;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public OrganizerPage(final PageParameters params) {
		super(params);
		
		// Domain Organization
		add(new SetDomainOrganizationPanel("domainOrg", new DomainOrganizationModel()));
		
		// Contexts
		final Dialog ctxDialog = new CreateContextDialog("contextDialog");
		add(ctxDialog);
		add(new ContextOverviewPanel("contextsView", contextsModel()) {
			@Override
			public void createContext(AjaxRequestTarget target) {
				ctxDialog.open(target);
			}
		});
		
		// Namespaces
		final Dialog nsDialog = new CreateNamespaceDialog("namespaceDialog");
		add(nsDialog);
		add(new NamespaceOverviewPanel("namespacesView", namespacesModel()) {
			@Override
			public void createNamespace(AjaxRequestTarget target) {
				nsDialog.open(target);
			}
		});
	}
	
	// ----------------------------------------------------
	
	protected IModel<List<Context>> contextsModel() {
		return new AbstractReadOnlyModel<List<Context>>() {
			@Override
			public List<Context> getObject() {
				return domainOrganizer.getContexts();
			}
			
		};
	}
	
	protected IModel<List<Namespace>> namespacesModel() {
		return new AbstractReadOnlyModel<List<Namespace>>() {
			@Override
			public List<Namespace> getObject() {
				return domainOrganizer.getNamespaces();
			}
			
		};
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.NAMESPACE)) {
			RBAjaxTarget.add(get("namespacesView"));
		} 
		if (mce.isAbout(ModelChangeEvent.CONTEXT)) {
			RBAjaxTarget.add(get("contextsView"));
		}
	}
	
}
