/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.builtin;

import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.editor.EntityPanel;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.CurrentPersonModel;

/**
 * <p>
 *  Widget showing the person associated with the current user.
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
	public ThatsMeWidget(String id, WidgetSpec spec, ConditionalModel<Boolean> perspectiveInConfigMode) {
		super(id, Model.of(spec), perspectiveInConfigMode);
		
		add(new EntityPanel("entity", new CurrentPersonModel() {
			@Override
			public ServiceProvider getServiceProvider() {
				return provider;
			}
		}));
	}
	
}
