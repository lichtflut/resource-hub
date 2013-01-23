/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.data.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;

/**
 * <p>
 * This class provides {@link de.lichtflut.rb.core.schema.model.Constraint}s for testing purposes.
 * </p>
 * Created: May 8, 2012
 *
 * @author Ravi Knox
 */
public class ConstraintsFactory {

	/**
	 * Creates a constraint consisting of:
	 * <ul>
	 * <li>
	 * QN: {@link RB}.COMMON_NAMESPACE_URI#EmailAddress
	 * </li>
	 * <li>
	 * Applicable Datatypes:
	 * <ul>
	 * 	<li>
	 * 		String
	 *  </li><li>
	 * 		Text
	 *  </li><li>
	 * 		Rich_Text
	 *  </li>
	 * </ul>
	 * 	<li>
	 * 	Name: Email-Address
	 *  </li><li>
	 *   LiteralConstraint: ".*@.*"
	 *  </li>
	 *  <li>
	 *   Public: true
	 *   </li>
	 * </li>
	 * </ul>
	 * @return public constraint
	 */
	public static Constraint buildPublicEmailConstraint(){
		QualifiedName qn = new QualifiedName(RB.COMMON_NAMESPACE_URI, "EmailAddress");
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

	/**
	 * Creates a constraint consisting of:
	 * <ul>
	 * <li>
	 * QN: {@link RB}.COMMON_NAMESPACE_URI#URLConstraint
	 * </li>
	 * <li>
	 * Applicable Datatypes:
	 * <ul>
	 * 	<li>
	 * 		String
	 *  </li><li>
	 * 		Text
	 *  </li><li>
	 * 		Rich_Text
	 *  </li>
	 * </ul>
	 * 	<li>
	 * 	Name: URL-Constraint
	 *  </li><li>
	 *   LiteralConstraint: "^http\://[a-zA-Z0-9\-\.]+\.[a-zA-Z]{2,3}(/\S*)?$"
	 *  </li>
	 *  <li>
	 *   Public: true
	 *   </li>
	 * </li>
	 * </ul>
	 * @return public constraint
	 */
	public static Constraint buildPublicURLConstraint(){
		QualifiedName qn = new QualifiedName(RB.COMMON_NAMESPACE_URI, "URLConstraint");
		List<Datatype> datatypes = new ArrayList<Datatype>();
		datatypes.add(Datatype.STRING);
		datatypes.add(Datatype.TEXT);
		datatypes.add(Datatype.RICH_TEXT);
		ConstraintImpl constraint= new ConstraintImpl(qn);
		constraint.setApplicableDatatypes(datatypes);
		constraint.setName("URL-Constraint");
		constraint.setLiteralConstraint("^http\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$");
		constraint.setPublic(true);
		return constraint;
	}

	public static Constraint buildPublicPersonConstraint(){
		ResourceID resource = new SimpleResourceID(RB.COMMON_NAMESPACE_URI + "PersonConstraint");
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.setName("Person-Constraint");
		constraint.setTypeConstraint(resource);
		constraint.setApplicableDatatypes(Arrays.asList(Datatype.RESOURCE));
		return constraint;
	}


	public static Constraint buildTypeConstraint(final ResourceID type){
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.buildReferenceConstraint(type, false);
		return constraint;
	}

}
