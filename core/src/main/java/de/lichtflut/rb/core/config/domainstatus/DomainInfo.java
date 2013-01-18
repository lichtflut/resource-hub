package de.lichtflut.rb.core.config.domainstatus;

/**
 * <p>
 *  Containing info about a domains state.
 * </p>
 *
 * <p>
 *  Created 29.12.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class DomainInfo {

    private String name;

    private String version;

    private DomainStatus status;

    private boolean virtual;

    // ----------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DomainStatus getStatus() {
        return status;
    }

    public void setStatus(DomainStatus status) {
        this.status = status;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    // ----------------------------------------------------

    @Override
    public String toString() {
        return "DomainInfo{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", status=" + status +
                '}';
    }

}
