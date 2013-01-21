package de.lichtflut.rb.core.system;

import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.config.domainstatus.DomainInfo;
import de.lichtflut.rb.core.config.domainstatus.DomainInfoContainer;
import de.lichtflut.rb.core.config.domainstatus.DomainInfoException;
import de.lichtflut.rb.core.config.domainstatus.DomainStatus;
import de.lichtflut.rb.core.config.domainstatus.LocalFileBasedDomainInfoContainer;
import de.lichtflut.rb.core.eh.ConfigurationException;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.services.DomainValidator;
import org.arastreju.sge.ArastrejuGate;

/**
 * <p>
 * Supervisor for status of domains. Responsible for initialization and
 * validation of domains.
 * </p>
 * 
 * <p>
 * Created 18.01.13
 * </p>
 * 
 * @author Oliver Tigges
 */
public class DomainSupervisor {

	private DomainValidator domainValidator;

	private DomainInfoContainer domainInfoContainer;
	private RBConfig config;

	// ----------------------------------------------------

	public DomainSupervisor() {
	}

	// ----------------------------------------------------

	public void onOpen(ArastrejuGate gate, String domain) {
		DomainInfo info = getDomainInfo(domain);
		switch (info.getStatus()) {
		case NEW:
			initializeDomain(gate, domain, info);
			break;
		case INITIALIZED:
			domainValidator.validateDomain(gate, domain);
			break;
		case DELETED:
			throw new IllegalStateException("Domain " + domain
					+ " has been deleted.");
		default:
			throw new IllegalStateException("Unexpected status: "
					+ info.getStatus());
		}
	}

	// ----------------------------------------------------

	public void init(RBConfig config) throws ConfigurationException {
		this.config = config;
		if (domainInfoContainer == null) {
			domainInfoContainer = defaultInfoContainer();
		}
		if (domainValidator == null) {
			domainValidator = defaultValidator();
		}
	}

	// ----------------------------------------------------

	public DomainValidator getDomainValidator() {
		return domainValidator;
	}

	public void setDomainValidator(DomainValidator domainValidator) {
		this.domainValidator = domainValidator;
	}

	public DomainInfoContainer getDomainInfoContainer() {
		if(domainInfoContainer==null){
			try {
				domainInfoContainer = defaultInfoContainer();
			} catch (ConfigurationException e) {
				throw new RuntimeException(e);
			}
		}
		return domainInfoContainer;
	}

	public void setDomainInfoContainer(DomainInfoContainer domainInfoContainer) {
		this.domainInfoContainer = domainInfoContainer;
	}

	// ----------------------------------------------------

	private void initializeDomain(ArastrejuGate gate, String domain,
			DomainInfo info) {
		domainValidator.initializeDomain(gate, domain);
		info.setStatus(DomainStatus.INITIALIZED);
		try {
			getDomainInfoContainer().updateDomain(info);
		} catch (DomainInfoException e) {
			throw new IllegalStateException(e);
		}
	}

	private DomainInfo getDomainInfo(String domain) {
		DomainInfo info = getDomainInfoContainer().getInfo(domain);
		if (info == null) {
			try {
				info = getDomainInfoContainer().registerDomain(domain);
			} catch (DomainInfoException e) {
				throw new RuntimeException(e);
			}
		}
		return info;
	}

	private DomainInfoContainer defaultInfoContainer()
			throws ConfigurationException {
		try {
			return new LocalFileBasedDomainInfoContainer(
					config.getWorkDirectory());
		} catch (DomainInfoException e) {
			throw new ConfigurationException(
					ErrorCodes.DOMAIN_INFO_COULD_NOT_BE_READ,
					"Domain info container failed.", e);
		}
	}

	private DomainValidator defaultValidator() {
		return new DefaultDomainValidator();
	}

}
