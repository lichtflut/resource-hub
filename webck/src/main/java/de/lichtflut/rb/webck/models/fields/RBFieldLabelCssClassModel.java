package de.lichtflut.rb.webck.models.fields;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.model.IModel;

/**
* <p>
*   This model checks if a field is 'floating' or 'breaking' and returns the
 *   corresponding CSS class.
* </p>
*
* <p>
*   Created 19.10.12
* </p>
*
* @author Oliver Tigges
*/
public class RBFieldLabelCssClassModel extends DerivedDetachableModel<String, RBField> {

    public RBFieldLabelCssClassModel(IModel<RBField> original) {
        super(original);
    }

    @Override
    protected String derive(RBField original) {
        if (original.getVisualizationInfo().isFloating()) {
            return "floating";
        } else {
            return "breaking";
        }
    }
}
