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
package de.lichtflut.rb.webck.models.viewspecs;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.MenuItem;

/**
 * <p>
 *  Model providing the menu items to be displayed for current context.
 *  Supports authenticated and unauthenticated users.
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
		return viewSpecificationService.getMenuItemsForDisplay();
	}

}
