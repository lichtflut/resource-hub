package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.io.IOReport;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.SemanticGraph;

import java.io.InputStream;

/**
 * <p>
 *  Manager for semantic information.
 * </p>
 *
 * <p>
 *  Created 27.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface InformationManager {

    IOReport importInformation(InputStream in, Context targetContext, String reportContext);

    SemanticGraph exportInformation(Context... srcContexts);
}
