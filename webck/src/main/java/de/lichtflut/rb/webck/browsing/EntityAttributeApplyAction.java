/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.services.EntityManager;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Action setting an entities attribute to a given value.
 * </p>
 *
 * <p>
 * 	Created Dec 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityAttributeApplyAction implements ReferenceReceiveAction<Object> {
	
	private final ResourceID predicate;
	
	private final RBEntity subject;
	
	@SpringBean
	private EntityManager entityManager;
	
	// ----------------------------------------------------
	
	/**
	 * @param predicate
	 */
	public EntityAttributeApplyAction(final RBEntity subject, final ResourceID predicate) {
		this.subject = subject;
		this.predicate = predicate;
		Injector.get().inject(this);
	}

	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void execute(final ModelingConversation sp, final RBEntity createdEntity) {
		final RBField field = subject.getField(predicate);
		if (field.getValues().isEmpty()) {
			field.setValue(0, createdEntity.getID());
		} else {
			field.addValue(createdEntity.getID());
		}
		entityManager.store(subject);
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public String toString() {
		return "Action(" + subject + " --> " + predicate + ")";
	}

}
