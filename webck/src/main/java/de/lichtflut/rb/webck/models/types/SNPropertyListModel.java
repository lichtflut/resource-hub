/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.types;

import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.views.SNProperty;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *  Loadable list model for Properties.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPropertyListModel extends AbstractLoadableDetachableModel<List<SNProperty>> {

	@SpringBean
	private TypeManager typeManager;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public SNPropertyListModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<SNProperty> load() {
		final List<SNProperty> properties = typeManager.findAllProperties();
		Collections.sort(properties, new Comparator<SNProperty>() {
			@Override
			public int compare(SNProperty t1, SNProperty t2) {
				return t1.getQualifiedName().getSimpleName().compareTo(t2.getQualifiedName().getSimpleName());
			}
		});
		return properties;
	}
	
}
