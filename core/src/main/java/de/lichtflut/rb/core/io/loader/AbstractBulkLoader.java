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
package de.lichtflut.rb.core.io.loader;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RsfSchemaParser;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.services.impl.SchemaImporterImpl;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import de.lichtflut.rb.core.services.impl.SingleGateConversationFactory;
import de.lichtflut.rb.core.services.impl.ViewSpecificationServiceImpl;
import de.lichtflut.rb.core.viewspec.reader.VSpecImporter;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.ContextID;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.SemanticGraph;
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
    static final String VSPEC = ".vspec";

    // ----------------------------------------------------

    private final ArastrejuGate gate;

    private Context context;

    // ----------------------------------------------------

    public AbstractBulkLoader(ArastrejuGate gate, String domain) {
        this.gate = gate;
        this.context = RBSystem.DOMAIN_CTX;
    }

    // ----------------------------------------------------

    protected void doImportRDF(InputStream in, String fileInfo) {
        try {
            LOGGER.info("Starting import of RDF file {} in context {}", fileInfo, context);
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

    protected void doImportVSpec(InputStream in, String fileInfo) {
        try {
            SingleGateConversationFactory conversationFactory = new SingleGateConversationFactory(gate);

            Conversation conversation = conversationFactory.startConversation();
            ViewSpecificationService service = new ViewSpecificationServiceImpl(conversationFactory);
            VSpecImporter importer = new VSpecImporter(service);

            importer.doImport(in);

            conversation.close();
            conversationFactory.closeConversations();

            LOGGER.info("Imported VSpec file: {}", fileInfo);

        } catch (Exception e) {
            LOGGER.error("File could not be imported: " + fileInfo, e);
        }
    }

    protected void setContext(String fileName) {
        if (AbstractBulkLoader.METAMODEL.equals(fileName) || AbstractBulkLoader.SCHEMAS.equals(fileName)) {
            context = RBSystem.TYPE_SYSTEM_CTX;
        } else if (AbstractBulkLoader.VIEWSPECS.equals(fileName)) {
            context = RBSystem.VIEW_SPEC_CTX;
        } else {
            context = RBSystem.DOMAIN_CTX;
        }
    }

    protected void unsetContext() {
        context = RBSystem.DOMAIN_CTX;
    }

}