/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.viewspecs;

import de.lichtflut.rb.core.services.ViewSpecificationService;
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
 *  Model for loading of all available perspectives.
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
    private ViewSpecificationService viewSpecificationService;

    // ----------------------------------------------------

    /**
     * Constructor.
     */
    public PerspectiveListModel() {
        Injector.get().inject(this);
    }

    // ----------------------------------------------------

    @Override
    public List<Perspective> load() {
        return viewSpecificationService.findPerspectives();
    }

}
