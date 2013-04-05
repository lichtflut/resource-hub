package de.lichtflut.rb.core.config.domainstatus;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.SimpleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  Implementation of DomainInfoContainer storing information in file system.
 * <p/>
 *
 * <p>
 *  Created 29.12.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class LocalFileBasedDomainInfoContainer implements DomainInfoContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileBasedDomainInfoContainer.class);

    private final Map<String, DomainInfo> domainMap = new HashMap<String, DomainInfo>();

    private final File infoFile;

    // ----------------------------------------------------

    public LocalFileBasedDomainInfoContainer(String workDir) throws DomainInfoException {
        this(new File(workDir));
    }

    public LocalFileBasedDomainInfoContainer(File workDir) throws DomainInfoException {
        infoFile = new File(workDir, "domain-info.json");

        if (!infoFile.exists()) {
            LOGGER.info("No domain info file exists yet.");
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, DomainInfo> read = mapper.readValue(infoFile,
                    MapType.construct(Map.class, SimpleType.construct(String.class),
                                     SimpleType.construct(DomainInfo.class)));
            LOGGER.info("Read domain info: " + read);
            domainMap.putAll(read);
        } catch (IOException e) {
            throw new DomainInfoException(e);
        }
    }

    // ----------------------------------------------------

    @Override
    public DomainInfo getInfo(String domain) {
        return domainMap.get(domain);
    }

    @Override
    public DomainInfo registerDomain(String domain) throws DomainInfoException {
        DomainInfo domainInfo = new DomainInfo();
        domainInfo.setName(domain);
        domainInfo.setStatus(DomainStatus.NEW);
        domainInfo.setVersion("0");
        domainMap.put(domain, domainInfo);
        onChange();
        return domainInfo;
    }

    @Override
    public void removeDomain(String domain) throws DomainInfoException {
        domainMap.remove(domain);
        onChange();
    }

    @Override
    public void unregisterDomain(String domain) throws DomainInfoException {
        DomainInfo domainInfo = domainMap.get(domain);
        domainInfo.setStatus(DomainStatus.DELETED);
        onChange();
    }

    @Override
    public void updateDomain(DomainInfo info) throws DomainInfoException {
        domainMap.put(info.getName(), info);
        onChange();
    }

    // ----------------------------------------------------

    private void onChange() throws DomainInfoException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(infoFile, domainMap);
        } catch (IOException e) {
            throw new DomainInfoException(e);
        }
    }
}
