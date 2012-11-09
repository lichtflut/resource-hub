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
