/*
 * Copyright 2013 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.io;

import org.arastreju.sge.Conversation;
import org.arastreju.sge.io.ReadStatementListener;
import org.arastreju.sge.model.Statement;

/**
 * <p>
 *  Listener that stores the statements directly.
 * </p>
 *
 * <p>
 * 	Created Feb 25, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ReportingStatementImporter implements ReadStatementListener {
	
	private final Conversation mc;
	
	private int stmtCounter = 0;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param mc The modeling conversation.
	 */
	public ReportingStatementImporter(final Conversation mc) {
		this.mc = mc;
	}

	// ----------------------------------------------------

	@Override
	public void onNewStatement(final Statement stmt) {
		mc.addStatement(stmt);
		stmtCounter++;
	}
	
	
	/**
	 * @return the report
	 */
	public IOReport createReport() {
		final IOReport report = new IOReport();
		report.add("Statements", stmtCounter);
		return report;
	}

}
