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

import org.arastreju.sge.ArastrejuGate;
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
 *
 *  This class is not thread safe.
 *
 * </p>
 *
 * <p>
 *  Created 18.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class FileSystemBulkLoader extends AbstractBulkLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemBulkLoader.class);

    // ----------------------------------------------------

    private final File baseDirectory;

    // ----------------------------------------------------

    public FileSystemBulkLoader(ArastrejuGate gate, File baseDirectory, String domain) {
        super(gate, domain);
        this.baseDirectory = baseDirectory;
    }

    // ----------------------------------------------------

    public void load() {
        LOGGER.info("Starting bulk load in directory {}", baseDirectory.getAbsolutePath());
        File[] files = list(baseDirectory);
        Arrays.sort(files, new DirectorySorter());
        for (File file : files) {
            if (file.isDirectory()) {
                setContext(file.getName());
                loadDirectory(file);
                unsetContext();
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
        if (file.getName().endsWith(AbstractBulkLoader.RDF_XML)) {
            doImportRDF(file);
        }  else if (file.getName().endsWith(AbstractBulkLoader.RSF)) {
            doImportRSF(file);
        }  else if (file.getName().endsWith(AbstractBulkLoader.VSPEC)) {
            doImportVSpec(file);
        } else {
            LOGGER.info("Ignoring file: {}", file.getAbsolutePath());
        }
    }

    // ----------------------------------------------------

    protected void doImportRDF(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            doImportRDF(in, file.getAbsolutePath());
            in.close();
        } catch (IOException e) {
            LOGGER.error("File could not be imported: " + file.getAbsolutePath(), e);
        }
    }

    protected void doImportRSF(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            doImportRSF(in, file.getAbsolutePath());
            in.close();
        } catch (IOException e) {
            LOGGER.error("File could not be imported: " + file.getAbsolutePath(), e);
        }
    }

    protected void doImportVSpec(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            doImportVSpec(in, file.getAbsolutePath());
            in.close();
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
            if (AbstractBulkLoader.METAMODEL.equals(a.getName())) {
               return -1;
            } else if (AbstractBulkLoader.DATA.equals(a.getName())) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
