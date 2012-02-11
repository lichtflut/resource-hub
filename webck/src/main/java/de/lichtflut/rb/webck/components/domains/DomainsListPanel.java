/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.domains;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.security.Domain;
import org.arastreju.sge.security.impl.SNDomain;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.ListAction;
import de.lichtflut.rb.webck.components.listview.ResourceListPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 *  List of {@link Domain}s.
 * </p>
 *
 * <p>
 * 	Created Jan 12, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class DomainsListPanel extends ResourceListPanel {
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id The ID.
	 * @param dataModel The model providing the data to display..
	 */
	public DomainsListPanel(final String id, final IModel<List<ResourceNode>> dataModel) {
		this(id, dataModel, defaultColumns());
	}
	
	/**
	 * Constructor.
	 * @param id The ID.
	 * @param dataModel The model providing the data to display..
	 * @param config The configuration of the table and it's columns.
	 */
	public DomainsListPanel(final String id, final IModel<List<ResourceNode>> dataModel, final IModel<ColumnConfiguration> config) {
		super(id, dataModel, config);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onDelete(ResourceNode node, AjaxRequestTarget target) {
		provider.getDomainOrganizer().deleteDomain(new SNDomain(node));
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.DOMAINS));
	}
	
	// ----------------------------------------------------
	
	public static IModel<ColumnConfiguration> defaultColumns() {
		final ColumnConfiguration config = new ColumnConfiguration(ListAction.VIEW, ListAction.EDIT, ListAction.DELETE);
		config.addColumnByPredicate(Aras.HAS_UNIQUE_NAME);
		config.addColumnByPredicate(Aras.HAS_TITLE);
		config.addColumnByPredicate(Aras.HAS_DESCRIPTION);
		return new Model<ColumnConfiguration>(config);
	}

}
