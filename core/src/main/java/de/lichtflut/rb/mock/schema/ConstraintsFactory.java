/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock.schema;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.mock.RBMock;

/**
 * <p>
 * This class provides {@link Constraint}s for testing purposes.
 * </p>
 * Created: May 8, 2012
 *
 * @author Ravi Knox
 */
public class ConstraintsFactory {

	public static Constraint buildPublicEmailConstraint(){
		ResourceID id = new SimpleResourceID(RBMock.COMMON_NAMESPACE_URI + "EmailAddress");
		List<Datatype> list = new ArrayList<Datatype>();
		list.add(Datatype.STRING);
		list.add(Datatype.TEXT);
		list.add(Datatype.RICH_TEXT);
		return ConstraintBuilder.buildPublicLiteralConstraint(id, "Email-Address", ".*@.*", list);
	}
	
	public static Constraint buildPrivateLiteralConstraint(){
		return ConstraintBuilder.buildLiteralConstraint(".*@.*");
	}
	
	public static Constraint buildPublicPersonConstraint(){
		ResourceID id = new SimpleResourceID();
		ResourceID resource = new SimpleResourceID(RBMock.COMMON_NAMESPACE_URI + "PersonConstraint");
		return ConstraintBuilder.buildPublicResourceConstraint(id, "Person-Constraint", resource);
	}
	
	public static Constraint buildPrivatePersonConstraint(){
		ResourceID resource = new SimpleResourceID(RBMock.COMMON_NAMESPACE_URI + "PersonConstraint");
		return ConstraintBuilder.buildResourceConstraint(resource);
	}
}
