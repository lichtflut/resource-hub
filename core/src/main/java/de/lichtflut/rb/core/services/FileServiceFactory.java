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
package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.config.FileServiceConfiguration;
import de.lichtflut.rb.core.config.RBConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 *  Factory for file services.
 * </p>
 *
 * <p>
 *  Created 09.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class FileServiceFactory {

    private FileServiceConfiguration fsConfiguration;

    private RBConfig rbConfig;

    // ----------------------------------------------------

    public FileServiceFactory(RBConfig rbConfig) {
        this(rbConfig, rbConfig.getFileServiceConfiguration());
    }

    public FileServiceFactory(RBConfig rbConfig, FileServiceConfiguration fsConfiguration) {
        this.fsConfiguration = fsConfiguration;
        this.rbConfig = rbConfig;
    }

    // ----------------------------------------------------

    public FileService create() {
        try {
            Class<FileServiceConfiguration> implementingClass = fsConfiguration.getImplementingClass();
            Constructor<FileServiceConfiguration> constructor = implementingClass.getConstructor(
                    new Class[] { RBConfig.class, FileServiceConfiguration.class} );
            return (FileService) constructor.newInstance(rbConfig, fsConfiguration);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
