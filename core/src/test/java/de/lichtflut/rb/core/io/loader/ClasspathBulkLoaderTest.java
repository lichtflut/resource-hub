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
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.persistence.TransactionControl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * <p>
 *  Test case for class path bulk loader.
 * </p>
 *
 * <p>
 *  Created 25.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
@RunWith(MockitoJUnitRunner.class)
public class ClasspathBulkLoaderTest {

    @Mock
    private ArastrejuGate gate;

    @Mock
    private Conversation conversation;

    @Mock
    private TransactionControl tx;

    // ----------------------------------------------------

    @Before
    public void setUpGate() {
        Mockito.when(gate.startConversation()).thenReturn(conversation);
        Mockito.when(gate.startConversation(Mockito.any(Context.class))).thenReturn(conversation);
        Mockito.when(conversation.beginTransaction()).thenReturn(tx);
    }

    // ----------------------------------------------------

    @Test
    public void shouldImportResources() {
        ClasspathBulkLoader loader = new ClasspathBulkLoader(gate, "bulk_import_test", "JUnit");
        loader.load();
    }

}
