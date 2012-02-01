/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.builtin;

import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.editor.EntityPanel;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ThatsMeWidget extends PredefinedWidget {
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param spec The specification.
	 */
	public ThatsMeWidget(String id, WidgetSpec spec) {
		super(id, new ResourceModel("global.widget.title.thats-me"));
		
		add(new EntityPanel("entity", new UserPersonModel()));
		
	}
	
	// ----------------------------------------------------
	
	private class UserPersonModel extends AbstractLoadableDetachableModel<RBEntity> {

		@Override
		public RBEntity load() {
			User currentUser = CurrentUserModel.currentUser();
			if (currentUser == null || currentUser.getAssociatedResource() == null) {
				return null;
			}
			ResourceNode userNode = provider.getResourceResolver().resolve(currentUser.getAssociatedResource());
			SemanticNode person = SNOPS.fetchObject(userNode, RBSystem.IS_RESPRESENTED_BY);
			if (person != null && person.isResourceNode()) {
				return provider.getEntityManager().find(person.asResource());
			} else {
				return null;
			}
		}
		
	}

}
