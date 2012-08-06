package de.lichtflut.rb.core.services;

import java.io.InputStream;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.io.StatementContainer;

import de.lichtflut.rb.core.io.IOReport;

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

	StatementContainer exportInformation(Context srcContexts);
}
