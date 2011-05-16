/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.models;

import org.apache.wicket.Component;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import static org.arastreju.sge.SNOPS.*;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.infra.exceptions.NotYetImplementedException;

/**
 * <p>
 *  This model represents a single value of a resource. This value is the Object of
 *  a {@link ResourceNode}'s Statement with a given Predicate.
 * </p>
 * 
 * <p>
 * 	This model will search for a {@link ResourceNodeModel} up in the component hierarchy.
 * </p>
 *
 * <p>
 * 	Created May 11, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SemanticAttributeModel<T extends SemanticNode> implements IComponentAssignedModel<T>, IModel<T> {

	private final ResourceID predicate;
	
	// -----------------------------------------------------
	
	/**
	 * Creates a new model for the object with given predicate.s 
	 * @param predicate The predicate.
	 */
	public SemanticAttributeModel(final ResourceID predicate) {
		this.predicate = predicate;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IModel#getObject()
	 */
	public T getObject() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
	 */
	public void setObject(final T object) {
		throw new NotYetImplementedException();
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IDetachable#detach()
	 */
	public void detach() {
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IComponentAssignedModel#wrapOnAssignment(org.apache.wicket.Component)
	 */
	public IWrapModel<T> wrapOnAssignment(final Component component) {
		return new AssignmentWrapper(component);
	}
	
	
	// -----------------------------------------------------
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private class AssignmentWrapper implements IWrapModel<T> {

		private final Component component;
		private ResourceNodeModel<ResourceNode> rnModel = null;
		
		/**
		 * Constructor.
		 * @param component The assigned component.
		 */
		public AssignmentWrapper(final Component component)	{
			this.component = component;
		}

		public void detach() {
			SemanticAttributeModel.this.detach();
		}

		public T getObject() {
			ResourceNode resource = findResource(); 
			if (resource == null) {
				return null;
			}
			return (T) resource.getSingleAssociationClient(predicate);
		}

		public void setObject(final T object) {		
			replace(rnModel.getObject(), predicate, object);
		}

		public IModel<T> getWrappedModel() {
			return SemanticAttributeModel.this;
		}
		
		private ResourceNode findResource() {
			if (rnModel != null) {
				return rnModel.getObject();
			}
			Component current = component;
			while (current != null && rnModel == null) {
				final IModel cmodel = current.getDefaultModel();
				if (cmodel instanceof ResourceNodeModel) {
					rnModel = (ResourceNodeModel) cmodel;
				}
				current = current.getParent();
			}
			if (rnModel == null) {
				throw new RuntimeException("Didn't find a ResourceNodeModel");
			}
			return rnModel.getObject();
		}
	}

}
