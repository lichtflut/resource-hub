package de.lichtflut.rb.core.viewspec.reader;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import de.lichtflut.rb.core.viewspec.impl.SNSelection;
import de.lichtflut.rb.core.viewspec.impl.SNSelectionParameter;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.SimpleNamespace;

import java.util.List;
import java.util.Stack;

import static org.arastreju.sge.SNOPS.assure;
import static org.arastreju.sge.SNOPS.id;
import static org.arastreju.sge.SNOPS.qualify;

/**
 * <p>
 *  Collector for results during parsing process.
 * </p>
 *
 * <p>
 *  Created July 5, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class VSpecCollector {

    private final NamespaceMap nsMap = new NamespaceMap();

    private final Stack<WidgetSpec> widgetStack = new Stack<WidgetSpec>();

    private final Stack<Perspective> perspectiveStack = new Stack<Perspective>();

    private WidgetSpec currentWidget;

    private ViewPort currentPort;

    private Perspective currentPerspective;

    // ----------------------------------------------------

    public VSpecCollector() {
    }

    // ----------------------------------------------------

    public WidgetSpec currentWidget() {
        if (currentWidget == null) {
            currentWidget = new SNWidgetSpec();
        }
        return currentWidget;
    }

    public ViewPort currentPort() {
        if (currentPort == null) {
            currentPort = currentPerspective.addViewPort();
        }
        return currentPort;
    }

    public Perspective currentPerspective() {
        return currentPerspective;
    }

    // ----------------------------------------------------

    public void addNamespace(String uri, String prefix) {
        nsMap.addNamespace(new SimpleNamespace(uri, prefix));
    }

    public void newPerspective(String name) {
        QualifiedName qn = QualifiedName.from(RBSystem.PERSPECTIVES_NAMESPACE_URI, name);
        this.currentPerspective = new SNPerspective(qn);
    }

    public void currentWidgetProperty(String key, String value) {
        if ("implementing-class".equals(key)) {
            setImplementingClass(value);
        } else if ("title".equals(key)){
            setWidgetTitle(value);
        } else if ("display".equals(key)){
            setDisplayType(value);
        } else {
            throw new IllegalStateException("Unsupported property for widgets: " + key);
        }
    }

    public void setDisplayType(String value) {
        if ("list".equals(value)) {
            setWidgetType(WDGT.ENTITY_LIST);
        } else if ("details".equals(value)) {
            setWidgetType(WDGT.ENTITY_DETAILS);
        } else if ("infovis".equals(value)) {
            setWidgetType(WDGT.INFOVIS);
        } else if ("predefined".equals(value)) {
            setWidgetType(WDGT.PREDEFINED);
        }
    }

    public void setWidgetTitle(String value) {
        currentWidget().setTitle(value);
    }

    public void setWidgetType(ResourceID type) {
        assure(currentWidget(), RDF.TYPE, type);
    }

    public void setImplementingClass(String value) {
        assure(currentWidget(), WDGT.IS_IMPLEMENTED_BY_CLASS, value);
        setWidgetType(WDGT.PREDEFINED);
    }

    public void setSelectionQuery(String queryString) {
        SNSelection selection = new SNSelection();
        currentWidget().setSelection(selection);
    }

    public void setSelectionQueryByType(String type) {
        SNSelection selection = SNSelection.forType(id(qualify(type)));
        currentWidget().setSelection(selection);
    }

    public void setSelectionQueryByValue(String value) {
        SNSelection selection = new SNSelection();
        selection.addParameter(new SNSelectionParameter().setTerm(new SNText(value)));
        currentWidget().setSelection(selection);
    }

    public void setSelectionQueryByRef(String ref) {
        SNSelection selection = new SNSelection();
        selection.addParameter(new SNSelectionParameter().setTerm(id(qualify(ref))));
        currentWidget().setSelection(selection);
    }

    // ----------------------------------------------------

    public void perspectiveFinished() {
        perspectiveStack.push(currentPerspective);
        currentPerspective = new SNPerspective();
    }

    public void portFinished() {
        for (WidgetSpec widget : widgetStack) {
            currentPort().addWidget(widget);
        }
        widgetStack.clear();
        currentPort = null;
    }

    public void widgetFinished() {
        widgetStack.push(currentWidget);
        currentWidget = new SNWidgetSpec();
    }

    public List<Perspective> getPerspectives() {
        return perspectiveStack;
    }
}
