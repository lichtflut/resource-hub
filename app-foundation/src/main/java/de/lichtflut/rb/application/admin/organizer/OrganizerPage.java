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
package de.lichtflut.rb.application.admin.organizer;

import de.lichtflut.rb.application.admin.AdminBasePage;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.dialogs.RBDialog;
import de.lichtflut.rb.webck.components.dialogs.CreateContextDialog;
import de.lichtflut.rb.webck.components.dialogs.CreateNamespaceDialog;
import de.lichtflut.rb.webck.components.organizer.NamespaceOverviewPanel;
import de.lichtflut.rb.webck.components.organizer.SetDomainOrganizationPanel;
import de.lichtflut.rb.webck.components.organizer.contexts.ContextOverviewPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.DomainOrganizationModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.naming.Namespace;

import java.util.List;

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
		final RBDialog ctxDialog = new CreateContextDialog("contextDialog");
		add(ctxDialog);
		add(new ContextOverviewPanel("contextsView", contextsModel()) {
			@Override
			public void createContext(AjaxRequestTarget target) {
				ctxDialog.open(target);
			}
		});
		
		// Namespaces
		final RBDialog nsDialog = new CreateNamespaceDialog("namespaceDialog");
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
