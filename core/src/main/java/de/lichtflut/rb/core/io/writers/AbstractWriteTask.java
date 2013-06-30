package de.lichtflut.rb.core.io.writers;

import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.SimpleNamespace;

import java.io.IOException;
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
public class AbstractWriteTask {

    private static final String LVL3 = "\t\t\t";
    private static final String LVL2 = "\t\t";

    protected final PrintWriter writer;
    protected final NamespaceMap nsMap;
    protected int scope = 0;

    // ----------------------------------------------------

    public AbstractWriteTask(NamespaceMap nameSpaceMap, PrintWriter writer) {
        this.nsMap = nameSpaceMap;
        this.writer = writer;
    }

    // ----------------------------------------------------

    public void writeNamespaces() throws IOException {
        for(String prefix : nsMap.getPrefixes()) {
            Namespace namespace = nsMap.getNamespace(prefix);
            writer.write("namespace \"" + namespace.getUri() + "\" prefix \"" + prefix + "\"\n");
        }
        newLine();
    }

    protected String toQName(final ResourceID id) {
        String uri = id.toURI();
        String namespace = QualifiedName.getNamespace(uri);
        String simpleName = QualifiedName.getSimpleName(uri);
        String prefix = nsMap.getPrefix(new SimpleNamespace(namespace));

        return prefix + ":" + simpleName;
    }

    protected void newLine() {
        writer.append("\n");
    }

    protected void openScope(final String expression) {
        indent(scope);
        writer.write(expression);
        writer.write(" {\n");
        scope++;

    }

    protected void closeScope() {
        scope--;
        indent(scope);
        writer.append("}\n");
    }

    protected void indent(final int lvl) {
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
    }
}
