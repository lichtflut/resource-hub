/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock.schema;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;
import de.lichtflut.rb.mock.RBMock;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class provides {@link de.lichtflut.rb.core.schema.model.Constraint}s for testing purposes.
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
		ConstraintImpl constraint= new ConstraintImpl(qn);
		constraint.setApplicableDatatypes(datatypes);
		constraint.setName("Email-Address");
		constraint.setLiteralConstraint(".*@.*");
		constraint.setPublic(true);
		return constraint;
	}
	
	public static Constraint buildPublicPersonConstraint(){
		ResourceID resource = new SimpleResourceID(RBMock.COMMON_NAMESPACE_URI + "PersonConstraint");
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.setName("Person-Constraint");
		constraint.setTypeConstraint(resource);
		return constraint;
	}
	
	
	public static Constraint buildTypeConstraint(ResourceID type){
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.buildReferenceConstraint(type, false);
		return constraint;
	}

}
