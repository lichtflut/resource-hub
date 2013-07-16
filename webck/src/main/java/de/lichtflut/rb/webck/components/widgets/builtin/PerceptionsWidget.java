package de.lichtflut.rb.webck.components.widgets.builtin;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.perceptions.PerceptionManagementPanel;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.perceptions.PerceptionModel;

/**
 * <p>
 * Builtin widget for display and managment of perceptions.
 * </p>
 *
 * <p>
 *  Created 16.07.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerceptionsWidget extends PredefinedWidget {

    public PerceptionsWidget(String id, WidgetSpec spec, ConditionalModel<Boolean> perspectiveInConfigMode) {
        super(id, spec, perspectiveInConfigMode);

        add(new PerceptionManagementPanel("perceptions", new PerceptionModel()));

    }

}
