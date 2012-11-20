/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.admin.domains;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.application.admin.AdminBasePage;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.organizer.domains.DomainBrowserPanel;
import de.lichtflut.rb.webck.components.organizer.domains.DomainEditPanel;
import de.lichtflut.rb.webck.components.listview.ActionLink;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.domains.CurrentDomainModel;

/**
 * 
 * <p>
 * Page for the management of the domains. Not for the current domain.
 * </p>
 * 
 * <p>
 * Created Jan 11, 2012
 * </p>
 * 
 * @author Oliver Tigges
 */
public class DomainsManagementPage extends AdminBasePage {

	@SpringBean
	private AuthModule authModule;

	private final IModel<RBDomain> currentDomain = new Model<RBDomain>();

	private final IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);

	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public DomainsManagementPage(final PageParameters params) {
		super(params);

		final CurrentDomainModel currentDomainModel = new CurrentDomainModel();

		add(new DomainEditPanel("editor", currentDomain, mode));

		add(new ActionLink("showDomestic", CurrentDomainModel.displayNameModel()) {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				viewDomain(currentDomainModel);
			}
		});

		add(new DomainBrowserPanel("otherDomains", otherDomainsModel(currentDomainModel)) {
			@Override
			public void onDomainSelected(final IModel<RBDomain> domain) {
				viewDomain(domain);
			}
		});

		add(new AjaxLink("create") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				createDomain();
			}
		});
	}

	// ----------------------------------------------------

	protected void viewDomain(final IModel<RBDomain> domain) {
		currentDomain.setObject(domain.getObject());
		mode.setObject(DisplayMode.VIEW);
		send(this, Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.DOMAIN));
	}

	protected void createDomain() {
		currentDomain.setObject(new RBDomain());
		mode.setObject(DisplayMode.CREATE);
		send(this, Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.DOMAIN));
	}

	// ----------------------------------------------------

	protected IModel<List<RBDomain>> otherDomainsModel(final CurrentDomainModel currentDomainModel) {
		return new AbstractLoadableDetachableModel<List<RBDomain>>() {
			@Override
			public List<RBDomain> load() {
				final RBDomain domesticDomain = currentDomainModel.getObject();
				final List<RBDomain> result = new ArrayList<RBDomain>();
				Collection<RBDomain> allDomains = authModule.getDomainManager().getAllDomains();
				for (RBDomain current : allDomains) {
					if (!current.equals(domesticDomain)) {
						result.add(current);
					}
				}
				return result;
			};
		};
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.DOMAIN)) {
			RBAjaxTarget.add(get("otherDomains"));
		}
	}

}
