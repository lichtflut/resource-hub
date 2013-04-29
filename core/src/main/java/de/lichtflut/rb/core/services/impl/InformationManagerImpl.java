/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.services.impl;

import java.io.IOException;
import java.io.InputStream;

import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.io.StatementContainer;
import org.arastreju.sge.persistence.TransactionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.io.ReportingStatementImporter;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.InformationManager;

/**
 * <p>
 *  Implementation of InformationManager.
 * </p>
 *
 * <p>
 *  Created 27.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class InformationManagerImpl implements InformationManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(InformationManagerImpl.class);

	private final ArastrejuResourceFactory factory;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public InformationManagerImpl(final ArastrejuResourceFactory factory) {
		this.factory = factory;
	}

	// ----------------------------------------------------

	@Override
	public IOReport importInformation(final InputStream in, final Context targetContext, final String reportContext) {

		Conversation conversation = factory.getConversation(targetContext);

		final IOReport report = new IOReport();
		final SemanticGraphIO io = new RdfXmlBinding();
		final TransactionControl tx = conversation.beginTransaction();
		final ReportingStatementImporter importer = new ReportingStatementImporter(conversation);
		try {
			io.read(in, importer);
			report.merge(importer.createReport());
			tx.success();
			report.success();
		} catch (IOException e) {
			tx.fail();
			LOGGER.error("Import failed.", e);
			report.setAdditionalInfo("[" + reportContext +"] " +e.getMessage());
			report.error();
		} catch (SemanticIOException e) {
			tx.fail();
			LOGGER.error("Import failed.", e);
			report.setAdditionalInfo("[" + reportContext + "] " + e.getMessage());
			report.error();
		} finally {
			tx.finish();
		}

		return report;

	}

	@Override
	public StatementContainer exportInformation(final Context ctx) {
		return factory.getOrganizer().getStatements(ctx);
	}

}
