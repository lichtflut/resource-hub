/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 *  Exporter of ResourceList to excel format.
 * </p>
 *
 * <p>
 * 	Created Feb 22, 2012
 * </p>
 *
 * @author Erik Aderhold
 */
public interface ExcelExporter {
	
	/**
	 * Export to excel format.
	 * @param out
	 * @throws IOException
	 */
	void export(OutputStream out) throws IOException;
	
}
