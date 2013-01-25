package de.lichtflut.rb.core.io.loader;

import de.lichtflut.rb.core.system.BulkLoadingDomainInitializer;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.ModelingConversation;
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
    private ModelingConversation conversation;

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
