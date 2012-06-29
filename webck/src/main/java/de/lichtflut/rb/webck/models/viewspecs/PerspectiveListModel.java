/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.viewspecs;

import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Model for loading of perspectives by their ID.
 * </p>
 *
 * <p>
 * 	Created Feb 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectiveListModel extends LoadableDetachableModel<List<Perspective>> {
	
	@SpringBean
	private ModelingConversation conversation;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor. 
	 */
	public PerspectiveListModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<Perspective> load() {
		final Query query = conversation.createQuery();
		query.addField(RDF.TYPE, WDGT.PERSPECTIVE);
		final QueryResult result = query.getResult();
		final List<Perspective> perspectives = new ArrayList<Perspective>(result.size());
		for (ResourceNode node : result) {
			perspectives.add(new SNPerspective(node));
		}
		return perspectives;
	}

}
