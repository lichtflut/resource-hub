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
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.common.Accessibility;
import de.lichtflut.rb.core.common.SerialNumberOrderedNodesContainer;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import de.lichtflut.rb.core.viewspec.impl.SNViewPort;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.ViewSpecTraverser;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.query.SimpleQueryResult;
import org.arastreju.sge.query.SortCriteria;
import org.arastreju.sge.query.script.QueryScriptEngine;
import org.arastreju.sge.query.script.QueryScriptException;
import org.arastreju.sge.query.script.ScriptEngineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.arastreju.sge.SNOPS.string;

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
public class ViewSpecificationServiceImpl implements ViewSpecificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewSpecificationServiceImpl.class);

	private ServiceContext context;

	private ConversationFactory conversationFactory;

	// ----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public ViewSpecificationServiceImpl() { }

    /**
     * Constructor.
     * @param arasFactory The Arastreju resource factory providing conversations.
     */
    public ViewSpecificationServiceImpl(final ConversationFactory arasFactory) {
        this.context = null;
        this.conversationFactory = arasFactory;
    }

	/**
     * Constructor.
     * @param context The service context.
     * @param arasFactory The Arastreju resource factory providing conversations.
     */
    public ViewSpecificationServiceImpl(final ServiceContext context, final ConversationFactory arasFactory) {
        this.context = context;
        this.conversationFactory = arasFactory;
    }

	// -- PERSPECTIVES ------------------------------------

	@Override
	public Perspective findPerspective(QualifiedName qn) {
		final ResourceNode existing = vSpecConversation().findResource(qn);
		if (existing != null) {
			return new SNPerspective(existing);
		} else {
			return null;
		}
	}

	@Override
	public Perspective initializePerspective(final ResourceID id) {
		final ResourceNode existing = vSpecConversation().findResource(id.getQualifiedName());
		if (existing != null) {
			return new SNPerspective(existing);
		} else {
			SNPerspective perspective = new SNPerspective(id.getQualifiedName());
			if (context.isAuthenticated()) {
				perspective.setOwner(currentUser());
			}
			perspective.setVisibility(Accessibility.PRIVATE);
			// add two default view ports.
			perspective.addViewPort();
			perspective.addViewPort();
			return perspective;
		}
	}

	@Override
	public List<Perspective> findPerspectives() {
		final Query query = vSpecConversation().createQuery();
		query.addField(RDF.TYPE, WDGT.PERSPECTIVE);
		final QueryResult result = query.getResult();
		final List<Perspective> perspectives = new ArrayList<Perspective>(result.size());
		for (ResourceNode node : result) {
			perspectives.add(new SNPerspective(node));
		}
		return perspectives;
	}

	@Override
	public void store(final Perspective perspective) {
		vSpecConversation().attach(perspective);
	}

	@Override
	public void remove(QualifiedName qn) {
        final Perspective perspective = findPerspective(qn);
        if (perspective != null) {
            final SemanticGraph graph = new ViewSpecTraverser().toGraph(perspective);
            final Conversation mc = vSpecConversation();
            final TransactionControl tx = mc.beginTransaction();
            try {
                for (Statement stmt : graph.getStatements()) {
                    mc.removeStatement(stmt);
                }
                tx.success();
            } finally {
                tx.finish();
            }
        }
	}

	// -- WIDGETS -----------------------------------------

	@Override
	public WidgetSpec findWidgetSpec(final ResourceID id) {
		final ResourceNode existing = vSpecConversation().findResource(id.getQualifiedName());
		if (existing != null) {
			return new SNWidgetSpec(existing);
		} else {
			return null;
		}
	}

	@Override
	public void store(final WidgetSpec widgetSpec) {
		vSpecConversation().attach(widgetSpec);
	}

	@Override
	public void movePositionUp(final ViewPort port, final WidgetSpec widgetSpec) {
		final ResourceID portID = port.getID();
		vSpecConversation().attach(widgetSpec);
		new SerialNumberOrderedNodesContainer() {
			@Override
			protected List<? extends ResourceNode> getList() {
				return findPort(portID).getWidgets();
			}
		}.moveUp(widgetSpec, 1);
	}

	@Override
	public void movePositionDown(final ViewPort port, final WidgetSpec widgetSpec) {
		vSpecConversation().attach(port);
		vSpecConversation().attach(widgetSpec);
		new SerialNumberOrderedNodesContainer() {
			@Override
			protected List<? extends ResourceNode> getList() {
				return port.getWidgets();
			}
		}.moveDown(widgetSpec, 1);
	}

	@Override
	public void removeWidget(final ViewPort port, final WidgetSpec widgetSpec) {
		final Conversation conversation = vSpecConversation();
		conversation.attach(port);
		port.removeWidget(widgetSpec);
		conversation.remove(widgetSpec.getID());
	}

	// ----------------------------------------------------

	@Override
	public ViewPort findPort(final ResourceID id) {
		final ResourceNode existing = vSpecConversation().findResource(id.getQualifiedName());
		if (existing != null) {
			return new SNViewPort(existing);
		} else {
			return null;
		}
	}

	@Override
	public void store(ViewPort viewPort) {
		vSpecConversation().attach(viewPort);
	}

    // ----------------------------------------------------

    @Override
    public QueryResult load(WidgetSpec widget) {
        Selection selection = widget.getSelection();
        if (selection == null || !selection.isDefined()) {
            return SimpleQueryResult.EMPTY;
        } else if (Selection.SelectionType.BY_SCRIPT.equals(selection.getType())) {
            return selectByScript(selection);
        } else {
            return selectByQuery(selection, widget);
        }
    }

    // ----------------------------------------------------

    protected ResourceNode currentUser() {
        if (context == null || context.getUser() == null) {
            throw new IllegalStateException("No user context set.");
        } else {
            return vSpecConversation().findResource(context.getUser().getQualifiedName());
        }
    }

    protected String[] getSortColumns(WidgetSpec spec) {
        List<String> columns = new ArrayList<String>();
        for (ColumnDef def : spec.getColumns()) {
            final ResourceID predicate = def.getProperty();
            if (predicate != null) {
                columns.add(predicate.toURI());
            }
        }
        return columns.toArray(new String[columns.size()]);
    }

    private QueryResult selectByQuery(Selection selection, WidgetSpec widget) {
        Query query = conversation().createQuery();
        selection.adapt(query);
        query.setSortCriteria(new SortCriteria(getSortColumns(widget)));
        return query.getResult();
    }

    private QueryResult selectByScript(Selection selection) {
        ScriptEngineContext ctx = new ScriptEngineContext(conversation());
        try {
            QueryScriptEngine engine = new QueryScriptEngine(ctx);
            engine.execute(string(selection.getQueryExpression()));
            return ctx.getQueryResult();
        } catch (QueryScriptException e) {
            throw new RuntimeException(e);
        }
    }

	// ----------------------------------------------------

	private Conversation vSpecConversation() {
		return conversationFactory.getConversation(RBSystem.VIEW_SPEC_CTX);
	}

    private Conversation conversation() {
        return conversationFactory.getConversation();
    }

}
