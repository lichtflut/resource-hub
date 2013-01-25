package de.lichtflut.rb.core.io.loader;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RsfSchemaParser;
import de.lichtflut.rb.core.services.impl.SchemaImporterImpl;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import de.lichtflut.rb.core.services.impl.SingleGateConversationFactory;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.SimpleContextID;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.naming.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class AbstractBulkLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBulkLoader.class);

    static final String METAMODEL = "metamodel";
    static final String DATA = "data";
    static final String SCHEMAS = "schemas";
    static final String VIEWSPECS = "viewspecs";

    static final String RDF_XML = ".rdf.xml";
    static final String RSF = ".rsf";

    // ----------------------------------------------------

    private final ArastrejuGate gate;

    private final Context domainCtx;

    private Context context;

    // ----------------------------------------------------

    public AbstractBulkLoader(ArastrejuGate gate, String domain) {
        this.gate = gate;
        this.domainCtx = new SimpleContextID(Namespace.LOCAL_CONTEXTS, domain);
        this.context = domainCtx;
    }

    // ----------------------------------------------------

    protected void doImportRDF(InputStream in, String fileInfo) {
        try {
            Conversation conversation = gate.startConversation(context);
            SemanticGraphIO io = new RdfXmlBinding();
            SemanticGraph graph = io.read(in);
            conversation.attach(graph);
            conversation.close();

            LOGGER.info("Imported RDF file: {}", fileInfo);

        } catch (IOException e) {
            LOGGER.error("File could not be imported: " + fileInfo, e);
        } catch (SemanticIOException e) {
            LOGGER.error("File could not be imported: " + fileInfo, e);
        }
    }

    protected void doImportRSF(InputStream in, String fileInfo) {
        try {
            SingleGateConversationFactory conversationFactory = new SingleGateConversationFactory(gate);
            Conversation conversation = conversationFactory.startConversation();
            SchemaManagerImpl schemaManager = new SchemaManagerImpl(conversationFactory);
            SchemaImporterImpl importer = new SchemaImporterImpl(schemaManager, conversation, new RsfSchemaParser());

            importer.read(in);

            conversation.close();
            conversationFactory.closeConversations();

            LOGGER.info("Imported RSF file: {}", fileInfo);

        } catch (IOException e) {
            LOGGER.error("File could not be imported: " + fileInfo, e);
        }
    }

    protected void setContext(String fileName) {
        if (AbstractBulkLoader.METAMODEL.equals(fileName) || AbstractBulkLoader.SCHEMAS.equals(fileName)) {
            context = RBSystem.TYPE_SYSTEM_CTX;
        } else if (AbstractBulkLoader.VIEWSPECS.equals(fileName)) {
            context = RBSystem.VIEW_SPEC_CTX;
        } else {
            context = domainCtx;
        }
    }

    protected void unsetContext() {
        context = domainCtx;
    }

}