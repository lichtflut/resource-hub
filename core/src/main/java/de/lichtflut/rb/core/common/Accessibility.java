package de.lichtflut.rb.core.common;

import de.lichtflut.rb.core.RBSystem;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Enumeration of the accessibility of an information.
 * </p>
 *
 * <p>
 *  Created 09.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public enum Accessibility {

    PRIVATE(RBSystem.PRIVATE_ACCESS.getQualifiedName()),
    PROTECTED(RBSystem.PROTECTED_ACCESS.getQualifiedName()),
    PUBLIC(RBSystem.PUBLIC_ACCESS.getQualifiedName());

    // ----------------------------------------------------

    private QualifiedName qn;

    private Accessibility(QualifiedName qn) {
        this.qn = qn;
    }

    // ----------------------------------------------------

    public QualifiedName getQualifiedName() {
        return qn;
    }

    // ----------------------------------------------------

    public static Accessibility getByQualifiedName(QualifiedName qn) {
        if (RBSystem.PRIVATE_ACCESS.getQualifiedName().equals(qn)) {
            return PRIVATE;
        } else if (RBSystem.PROTECTED_ACCESS.getQualifiedName().equals(qn)) {
            return PROTECTED;
        } else if (RBSystem.PUBLIC_ACCESS.getQualifiedName().equals(qn)) {
            return PUBLIC;
        } else {
            throw new IllegalArgumentException("Can not map to Accessibility enum: " + qn);
        }
    }

}
