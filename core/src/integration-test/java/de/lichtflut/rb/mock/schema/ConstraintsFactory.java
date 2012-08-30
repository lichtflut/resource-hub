/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock.schema;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.LiteralConstraint;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;
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
		QualifiedName qn = new QualifiedName(RBMock.COMMON_NAMESPACE_URI, "EmailAddress");
		List<Datatype> datatypes = new ArrayList<Datatype>();
		datatypes.add(Datatype.STRING);
		datatypes.add(Datatype.TEXT);
		datatypes.add(Datatype.RICH_TEXT);
		ConstraintImpl constraint= new ConstraintImpl(new SNResource(qn));
		constraint.setApplicableDatatypes(datatypes);
		constraint.setName("Email-Address");
		constraint.setLiteralConstraint(".*@.*");
		constraint.isPublic(true);
		return constraint;
	}
	
	public static Constraint buildPrivateLiteralConstraint(){
		LiteralConstraint constraint = new LiteralConstraint(new SNResource());
		constraint.setLiteralPattern(".*@.*");
		return constraint;
	}
	
	public static Constraint buildPublicPersonConstraint(){
		ResourceID resource = new SimpleResourceID(RBMock.COMMON_NAMESPACE_URI + "PersonConstraint");
		ConstraintImpl constraint = new ConstraintImpl((ResourceNode)new SNResource());
		constraint.setName("Person-Constraint");
		constraint.setReference(resource);
		return constraint;
	}
	
	
	public static Constraint buildTypeConstraint(ResourceID type){
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.buildReferenceConstraint(type, false);
		return constraint;
	}
	
	public static Constraint buildPrivatePersonConstraint(){
		ResourceID resource = new SimpleResourceID(RBMock.COMMON_NAMESPACE_URI + "PersonConstraint");
		ConstraintImpl constraint = new ConstraintImpl((ResourceNode)new SNResource());
		constraint.setReference(resource);
		return constraint;
	}
}
