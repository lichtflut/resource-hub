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
                ", status='" + status +
                "'}";
    }

}
