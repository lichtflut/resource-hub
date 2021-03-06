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
package de.lichtflut.rb.core.viewspec.writer;

import de.lichtflut.rb.core.io.writers.CommonFormatWriter;
import de.lichtflut.rb.core.viewspec.Perspective;
import org.arastreju.sge.io.NamespaceMap;

/**
 * <p>
 *  Interface for writers of perspectives.
 * </p>
 *
 * <p>
 *  Created June 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PerspectiveWriter {

    void write(Perspective perspective, NamespaceMap nsMap, CommonFormatWriter out);

}
