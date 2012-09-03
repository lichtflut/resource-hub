/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import de.lichtflut.infra.exceptions.NoLongerSupportedException;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 * Implementation of {@link Constraint}.
 * </p>
 * <p>
 * All but public constraints should be constructed by using the appropriate <code>buildXXX</code>
 * -methods to ensure integrity
 * </p>
 * <p>
 * Public-constraints must set the following properties:
 * <ul>
 * <li>
 * Name</li>
 * <li>
 * literal constraint</li>
 * <li>
 * setPublic</li>
 * <li>
 * applicable Datatypes</li>
 * </ul>
 * </p>
 * 
 * 
 * </p> Created: May 25, 2012
 * 
 * @author Ravi Knox
 */
public class ConstraintImpl implements Constraint {

    private QualifiedName qn;

    private String name;

    private boolean isPublic;

    private String literalConstraint;

    private ResourceID resourceConstraint;

    private List<Datatype> applicableDatatypes;

	// ---------------- Constructor -------------------------

    @Deprecated
	public ConstraintImpl(final ResourceNode node) {
        throw new NoLongerSupportedException();
	}

    /**
     * Constructor.
     */
    public ConstraintImpl(QualifiedName qn) {
        this.qn = qn;
    }

    /**
     * Default constructor for new constraints.
     */
	public ConstraintImpl() {
		this(new QualifiedName(Namespace.UUID, UUID.randomUUID().toString()));
	}

	// ------------------------------------------------------

	public void buildReferenceConstraint(final ResourceID reference, final boolean isLiteralReference) {
		this.setPublic(false);
		this.setTypeConstraint(reference);
	}

	public void buildLiteralConstraint(final String pattern) {
		this.setPublic(false);
		this.setLiteralConstraint(pattern);
	}

	// ------------------------------------------------------


    @Override
    public QualifiedName getQualifiedName() {
        return qn;
    }

	@Override
	public String getName() {
        if (name != null) {
            return name;
        } else {
            return "<unnamed>";
        }
	}

	public void setName(final String name) {
		this.name = name;
	}

    @Override
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(final boolean isPublic) {
        this.isPublic = isPublic;
    }

    // ----------------------------------------------------

    @Override
    public boolean isLiteral() {
        return literalConstraint != null;
    }

	@Override
	public String getLiteralConstraint() {
		return literalConstraint;
	}

	public void setLiteralConstraint(final String pattern) {
		this.literalConstraint = pattern;
	}

	@Override
	public ResourceID getTypeConstraint() {
        return resourceConstraint;
	}

	public void setTypeConstraint(final ResourceID resourceConstraint) {
		this.resourceConstraint = resourceConstraint;
	}

	@Override
	public List<Datatype> getApplicableDatatypes() {
        if (applicableDatatypes != null) {
            return applicableDatatypes;
        } else {
            return Collections.emptyList();
        }
    }

	public void setApplicableDatatypes(final List<Datatype> datatypes) {
		this.applicableDatatypes = datatypes;
	}

	// ------------------------------------------------------

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Constraint id: " + qn);
		sb.append(" name: " + getName());
		sb.append(" is public: " + isPublic());
		sb.append(" is literal: " + isLiteral());
		return sb.toString();
	}
}
