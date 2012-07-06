/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.admin.typesystem;

import org.apache.wicket.Page;

import de.lichtflut.rb.application.admin.AdminBasePage;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemPanelAggregator;

/**
 * <p>
 *  This {@link Page} allows the configuration of {@link ResourceSchema}s, {@link PropertyDeclaration}s
 *  and public {@link Constraint}s.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class TypeSystemPage extends AdminBasePage{
	
	// ---------------- Constructor -------------------------
	
	public TypeSystemPage(){
		add(new TypeSystemPanelAggregator("typeSystem"));
	}

}
