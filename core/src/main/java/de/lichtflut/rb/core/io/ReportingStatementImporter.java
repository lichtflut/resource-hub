/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.io;

import org.arastreju.sge.ModelingConversation;
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
	
	private final ModelingConversation mc;
	
	private int stmtCounter = 0;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param mc The modeling conversation.
	 */
	public ReportingStatementImporter(final ModelingConversation mc) {
		this.mc = mc;
	}

	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
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
