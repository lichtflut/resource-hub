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
package de.lichtflut.rb.core.data.schema;

import java.util.ArrayList;
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
 * This class provides {@link Constraint}s for testing purposes.
 * </p>
 * Created: May 8, 2012
 *
 * @author Ravi Knox
 */
public class ConstraintsFactory {

	public static Constraint buildPublicEmailConstraint(){
		QualifiedName qn = QualifiedName.from(RB.COMMON_NAMESPACE_URI, "EmailAddress");
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

	public static Constraint buildPrivateLiteralConstraint(){
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.setLiteralConstraint(".*@.*");
		return constraint;
	}

	public static Constraint buildPublicPersonConstraint(){
		ResourceID resource = new SimpleResourceID(RB.COMMON_NAMESPACE_URI + "PersonConstraint");
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.setName("Person-Constraint");
		constraint.setTypeConstraint(resource);
		return constraint;
	}


	public static Constraint buildTypeConstraint(final ResourceID type){
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.buildReferenceConstraint(type, false);
		return constraint;
	}

	public static Constraint buildPrivatePersonConstraint(){
		ResourceID resource = new SimpleResourceID(RB.COMMON_NAMESPACE_URI + "PersonConstraint");
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.setTypeConstraint(resource);
		return constraint;
	}
}
