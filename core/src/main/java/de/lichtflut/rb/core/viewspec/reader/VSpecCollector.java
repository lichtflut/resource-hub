package de.lichtflut.rb.core.viewspec.reader;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetAction;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import de.lichtflut.rb.core.viewspec.impl.SNSelection;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetAction;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.SimpleNamespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(VSpecCollector.class);

    private static final Pattern INT_LABEL = Pattern.compile("label\\[([a-zA-Z]+)\\]");

    // ----------------------------------------------------

    private final NamespaceMap nsMap = new NamespaceMap();

    private final Stack<Perspective> perspectiveStack = new Stack<Perspective>();

    private WidgetSpec currentWidget;

    private ViewPort currentPort;

    private Perspective currentPerspective;

    private WidgetAction currentAction;

    // ----------------------------------------------------

    public VSpecCollector() {
    }

    // ----------------------------------------------------

    public List<Perspective> getPerspectives() {
        return perspectiveStack;
    }

    // ----------------------------------------------------

    public WidgetSpec currentWidget() {
        if (currentWidget == null) {
            currentWidget = new SNWidgetSpec();
            LOGGER.debug("new widget created: {}", currentWidget().getID());
            currentPort().addWidget(currentWidget);
        }
        return currentWidget;
    }

    public WidgetAction currentAction() {
        if (currentAction == null) {
            currentAction = new SNWidgetAction();
            LOGGER.debug("new action created: {}", currentWidget().getID());
            currentWidget().addAction(currentAction);
        }
        return currentAction;
    }


    public ViewPort currentPort() {
        if (currentPort == null) {
            currentPort = currentPerspective.addViewPort();
        }
        return currentPort;
    }

    public Perspective currentPerspective() {
        if (currentPerspective == null) {
            currentPerspective = new SNPerspective();
        }
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
        LOGGER.debug("Setting implementing class: {}", value);
        assure(currentWidget(), WDGT.IS_IMPLEMENTED_BY_CLASS, value);
        setWidgetType(WDGT.PREDEFINED);
    }

    public void setSelectionQuery(String queryString) {
        SNSelection selection = SNSelection.byQuery(queryString);
        currentWidget().setSelection(selection);
    }

    public void setSelectionQueryByType(String type) {
        SNSelection selection = SNSelection.byType(id(qualify(type)));
        currentWidget().setSelection(selection);
    }

    public void setSelectionQueryByValue(String value) {
        SNSelection selection = SNSelection.byValue(value);
        currentWidget().setSelection(selection);
    }

    public void setSelectionQueryByRef(String ref) {
        SNSelection selection = SNSelection.byRelation(id(qualify(ref)));
        currentWidget().setSelection(selection);
    }

    public void newQueryParam(String field, String value) {
        LOGGER.debug("Query param {} = {}", field, value);
    }

    public void currentActionProperty(String key, String value) {
        if ("label".equals(key)) {
            currentAction().setLabel(value);
        } else if ("create".equals(key)){
            LOGGER.debug("Making a create action for {}", value);
            currentAction().setActionType(WDGT.ACTION_INSTANTIATE);
            SNOPS.assure(currentAction(), WDGT.CREATE_INSTANCE_OF, id(qualify(value)));
        } else {
            Matcher matcher = INT_LABEL.matcher(value);
            if (!matcher.matches()) {
                throw new IllegalStateException("Unsupported property for widgets: " + key);
            }
            String locale = matcher.group(1);
            throw new IllegalStateException("Found label for locale " + locale + "; But not yet supported.");
        }
    }

    // ----------------------------------------------------

    public void perspectiveFinished() {
        perspectiveStack.push(currentPerspective);
        currentPerspective = null;
    }

    public void portFinished() {
        currentPort = null;
    }

    public void widgetFinished() {
        currentWidget = null;
    }

    public void actionFinished() {
        currentAction = null;
    }


}
