package de.lichtflut.rb.core.io.writers;

import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.SimpleNamespace;

import java.io.IOException;
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

    private static final String LVL3 = "\t\t\t";
    private static final String LVL2 = "\t\t";

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

    public CommonFormatWriter writeNamespaces() throws IOException {
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
                writer.append("\t");
                break;
            case 2:
                writer.append(LVL2);
                break;
            case 3:
                writer.append(LVL3);
                break;
            default:
                for(int i = 0; i < lvl; i++) {
                    writer.write("\t");
                }
        }
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
