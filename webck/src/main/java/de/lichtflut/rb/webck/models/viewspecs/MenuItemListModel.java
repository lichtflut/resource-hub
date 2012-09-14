/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.viewspecs;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * <p>
 *  Model providing the current users menu items.
 * </p>
 *
 * <p>
 * 	Created Feb 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class MenuItemListModel extends LoadableDetachableModel<List<MenuItem>> {

	@SpringBean
	private ViewSpecificationService viewSpecificationService;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor. 
	 */
	public MenuItemListModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	@Override
	public List<MenuItem> load() {
		return viewSpecificationService.getUsersMenuItems();
	}
	
}
