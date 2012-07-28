package de.lichtflut.rb.core.services.impl;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.io.ReportingStatementImporter;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.InformationManager;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.io.StatementContainer;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.persistence.TransactionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    private ArastrejuResourceFactory factory;

    // ----------------------------------------------------

    /**
     * Constructor.
     */
    public InformationManagerImpl(ArastrejuResourceFactory factory) {
        this.factory = factory;
    }

    // ----------------------------------------------------

    @Override
    public IOReport importInformation(InputStream in, Context targetContext, String reportContext) {

        ModelingConversation conversation = factory.startConversation(targetContext);

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

        return importer.createReport();

    }

    @Override
    public StatementContainer exportInformation(Context ctx) {
        ModelingConversation conversation = factory.startConversation();
        conversation.getConversationContext().setReadContexts(ctx);

        return factory.getOrganizer().getStatements(ctx);
    }

}
