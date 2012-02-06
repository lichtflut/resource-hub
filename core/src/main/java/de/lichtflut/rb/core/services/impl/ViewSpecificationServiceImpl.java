/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.ViewSpecTraverser;

/**
 * <p>
 *  Implementation of {@link ViewSpecificationService}.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ViewSpecificationServiceImpl extends AbstractService implements ViewSpecificationService {

	/**
	 * @param provider
	 */
	public ViewSpecificationServiceImpl(final ServiceProvider provider) {
		super(provider);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Perspective findPerspective(ResourceID id) {
		final ModelingConversation mc = gate().startConversation();
		ResourceNode existing = mc.findResource(id.getQualifiedName());
		mc.close();
		if (existing != null) {
			return new SNPerspective(existing);
		} else {
			return null;
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Perspective perspective) {
		final ModelingConversation mc = gate().startConversation();
		final SemanticGraph graph = new ViewSpecTraverser().toGraph(perspective);
		for (Statement stmt : graph.getStatements()) {
			mc.removeStatement(stmt);
		}
		mc.close();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public WidgetSpec findWidgetSpec(ResourceID id) {
		final ModelingConversation mc = gate().startConversation();
		ResourceNode existing = mc.findResource(id.getQualifiedName());
		mc.close();
		if (existing != null) {
			return new SNWidgetSpec(existing);
		} else {
			return null;
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void store(WidgetSpec widgetSpec) {
		final ModelingConversation mc = gate().startConversation();
		mc.attach(widgetSpec);
		mc.close();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void store(ViewPort viewPort) {
		final ModelingConversation mc = gate().startConversation();
		mc.attach(viewPort);
		mc.close();
	}
	
}
