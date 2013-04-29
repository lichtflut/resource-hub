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
