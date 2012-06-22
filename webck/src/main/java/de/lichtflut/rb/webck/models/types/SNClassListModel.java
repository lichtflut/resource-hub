/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.types;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Loadable list model for rb:Types.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNClassListModel extends AbstractLoadableDetachableModel<List<SNClass>> {

	@SpringBean
	private TypeManager typeManager;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public SNClassListModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<SNClass> load() {
		final List<SNClass> types = typeManager.findAllTypes();
		Collections.sort(types, new Comparator<SNClass>() {
			@Override
			public int compare(SNClass t1, SNClass t2) {
				return t1.getQualifiedName().getSimpleName().compareTo(t2.getQualifiedName().getSimpleName());
			}
		});
		return types;
	}
	
}
