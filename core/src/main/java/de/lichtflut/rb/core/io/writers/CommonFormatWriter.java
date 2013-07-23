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
package de.lichtflut.rb.core.io.writers;

import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.SimpleNamespace;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * <p>
 *  Abstract writer for textual output of resources.
 * </p>
 *
 * <p>
 *  Created June 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class CommonFormatWriter {

    private static final String LVL3 = "      ";
    private static final String LVL2 = "    ";
    private static final String LVL1 = "  ";

    protected final PrintWriter writer;
    protected final NamespaceMap nsMap;
    protected int scope = 0;

    // ----------------------------------------------------

    public CommonFormatWriter(NamespaceMap nameSpaceMap, PrintWriter writer) {
        this.nsMap = nameSpaceMap;
        this.writer = writer;
    }

    public CommonFormatWriter(NamespaceMap nameSpaceMap, OutputStream out) {
        this(nameSpaceMap, new PrintWriter(out));
    }

    // ----------------------------------------------------

    public String toQName(final ResourceID id) {
        String uri = id.toURI();
        String namespace = QualifiedName.getNamespace(uri);
        String simpleName = QualifiedName.getSimpleName(uri);
        String prefix = nsMap.getPrefix(new SimpleNamespace(namespace));

        return prefix + ":" + simpleName;
    }

    // ----------------------------------------------------

    public CommonFormatWriter writeNamespaces() {
        for(String prefix : nsMap.getPrefixes()) {
            Namespace namespace = nsMap.getNamespace(prefix);
            writer.write("namespace \"" + namespace.getUri() + "\" prefix \"" + prefix + "\"\n");
        }
        newLine();
        return this;
    }

    public CommonFormatWriter newLine() {
        writer.append("\n");
        return this;
    }

    public CommonFormatWriter openScope(final String expression) {
        indent(scope);
        writer.write(expression);
        writer.write(" {\n");
        scope++;
        return this;

    }

    public CommonFormatWriter closeScope() {
        scope--;
        indent(scope);
        writer.append("}\n");
        return this;
    }

    public CommonFormatWriter indent(final int lvl) {
        switch (lvl) {
            case 0:
                break;
            case 1:
                writer.append(LVL1);
                break;
            case 2:
                writer.append(LVL2);
                break;
            case 3:
                writer.append(LVL3);
                break;
            default:
                for(int i = 0; i < lvl; i++) {
                    writer.write(LVL1);
                }
        }
        return this;
    }

    public CommonFormatWriter indent() {
        indent(scope);
        return this;
    }

    public CommonFormatWriter writeField(final String field, final String value) {
        indent(scope);
        writer.write(field);
        writer.write(" : ");
        writer.write("\"");
        if (value != null) {
            writer.write(value);
        }
        writer.write("\"\n");
        return this;
    }

    public CommonFormatWriter writeRaw(final String raw) {
        writer.write(raw);
        return this;
    }

    public CommonFormatWriter writeFieldIfNotNull(final String field, final String value) {
        if (value != null) {
            writeField(field, value);
        }
        return this;
    }

    // ----------------------------------------------------

    public CommonFormatWriter flush() {
        writer.flush();
        return this;
    }
}
