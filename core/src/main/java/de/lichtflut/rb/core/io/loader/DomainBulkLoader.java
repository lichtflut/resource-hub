package de.lichtflut.rb.core.io.loader;

import de.lichtflut.rb.core.schema.parser.impl.rsf.RsfSchemaParser;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.impl.SchemaImporterImpl;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.SemanticGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>
 *  Loads and imports a complete directory structure into a domain.
 *
 *  The directory should contain *.rdf.xml and *.rsf files in these directories:
 *  <ul>
 *      <li>metamodel (always loaded first)</li>
 *      <li>schemas</li>
 *      <li>viewspecs</li>
 *      <li>data (loaded last)</li>
 *  </ul>
 * </p>
 *
 * <p/>
 *  <p>
 * Created 18.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class DomainBulkLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainBulkLoader.class);

    private static final String METAMODEL = "metamodel";

    private static final String DATA = "data";

    private static final String RDF_XML = ".rdf.xml";

    private static final String RSF = ".rsf";

    // ----------------------------------------------------

    private final ArastrejuGate gate;

    private final File baseDirectory;

    // ----------------------------------------------------

    public DomainBulkLoader(ArastrejuGate gate, File baseDirectory) {
        this.baseDirectory = baseDirectory;
        this.gate = gate;
    }

    // ----------------------------------------------------

    public void load() {
        LOGGER.info("Starting buld load in directory {}", baseDirectory.getAbsolutePath());
        File[] files = list(baseDirectory);
        Arrays.sort(files, new DirectorySorter());
        for (File file : files) {
            if (file.isDirectory()) {
                loadDirectory(file);
            } else {
                loadFile(file);
            }
        }
    }

    public void loadDirectory(File dir) {
        if (dir == null || !dir.isDirectory()) {
            LOGGER.info("Not a directory: {}", dir);
            throw new IllegalArgumentException("Not a directory: " + dir);
        }
        LOGGER.info("Loading directory {}", dir.getAbsolutePath());
        for (File file :  list(dir)) {
            if (file.isDirectory()) {
                loadDirectory(file);
            } else {
                loadFile(file);
            }
        }
    }

    public void loadFile(File file) {
        if (file.getName().endsWith(RDF_XML)) {
            doImportRDF(file);
        }  else if (file.getName().endsWith(RSF)) {
            doImportRSF(file);
        } else {
            LOGGER.info("Ignoring file: {}", file.getAbsolutePath());
        }
    }

    // ----------------------------------------------------

    protected void doImportRDF(File file) {
        try {

            Conversation conversation = gate.startConversation();
            FileInputStream in = new FileInputStream(file);
            SemanticGraphIO io = new RdfXmlBinding();
            SemanticGraph graph = io.read(in);
            conversation.attach(graph);
            conversation.close();

            LOGGER.info("Imported RDF file: {}", file.getAbsolutePath());

        } catch (IOException e) {
            LOGGER.error("File could not be imported: " + file.getAbsolutePath(), e);
        } catch (SemanticIOException e) {
            LOGGER.error("File could not be imported: " + file.getAbsolutePath(), e);
        }
    }

    protected void doImportRSF(File file) {
        try {

            Conversation conversation = gate.startConversation();
            FileInputStream in = new FileInputStream(file);
            conversation.close();

            SchemaManagerImpl schemaManager = new SchemaManagerImpl(new ArastrejuResourceFactory(gate));
            SchemaImporterImpl importer = new SchemaImporterImpl(schemaManager, conversation, new RsfSchemaParser());

            importer.read(in);
            conversation.close();

            LOGGER.info("Imported RSF file: {}", file.getAbsolutePath());

        } catch (IOException e) {
            LOGGER.error("File could not be imported: " + file.getAbsolutePath(), e);
        }
    }

    // ----------------------------------------------------

    private File[] list(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            return files;
        }  else {
            return new File[0];
        }
    }

    // ----------------------------------------------------

    private static class DirectorySorter implements Comparator<File> {
        @Override
        public int compare(File a, File b) {
            if (METAMODEL.equals(a.getName())) {
               return -1;
            } else if (DATA.equals(a.getName())) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
