/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.notes;

import java.text.DateFormat;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.DC;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.models.HTMLSafeModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.resources.ResourcePropertyModel;

/**
 * <p>
 *  Panel for display and editing of a 'mezzle' a 'memo-frazzle'.
 * </p>
 *
 * <p>
 * 	Created Jan 3, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class MezzlePanel extends TypedPanel<ResourceNode> {
	
	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model containing the mezzle node.
	 */
	@SuppressWarnings("rawtypes")
	public MezzlePanel(final String id, final IModel<ResourceNode> model) {
		super(id, model);
		
		add(new Label("content", toHtmlSafeContentModel(model)).setEscapeModelStrings(false));
		
		add(new Label("creator", new DerivedModel<String, SemanticNode>(
				new ResourcePropertyModel<SemanticNode>(model, DC.CREATOR)) {
			@Override
			protected String derive(SemanticNode userNode) {
				return userNode.asValue().getStringValue();
			}
		}));
		
		add(new Label("created", new DerivedModel<String, SemanticNode>(
				new ResourcePropertyModel<SemanticNode>(model, DC.CREATED)) {
			@Override
			protected String derive(SemanticNode original) {
				return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, getLocale()).format(
						original.asValue().getTimeValue());
			}
		}));
		
		add(new AjaxLink("edit") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				edit(model);
			}
		});
		
		add(new AjaxLink("delete") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				delete(model);
			}
		});
	}
	
	// ----------------------------------------------------

    private IModel<String> toHtmlSafeContentModel(IModel<ResourceNode> model) {
        return new HTMLSafeModel(new DerivedModel<String, ResourceNode>(model) {
            @Override
            protected String derive(ResourceNode mezzle) {
                final SemanticNode node = SNOPS.singleObject(mezzle, RBSystem.HAS_CONTENT);
                if (node != null) {
                    return node.asValue().getStringValue();
                } else {
                    return null;
                }
            }
        });
    }

	
	/**
	 * Hook for editing of notes.
	 * @param mezzle The node representing the mezzle.
	 */
	public abstract void edit(final IModel<ResourceNode> mezzle);
	
	/**
	 * Hook for deleting of notes.
	 * @param mezzle The node representing the mezzle.
	 */
	public abstract void delete(final IModel<ResourceNode> mezzle);
	
}
