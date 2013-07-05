package de.lichtflut.rb.core.viewspec.reader;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import org.antlr.runtime.RecognitionException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * DESCRITPION.
 * </p>
 * <p/>
 * <p>
 * Created 05.07.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class VSpecImporter {

    private final ViewSpecificationService service;

    // ----------------------------------------------------

    public VSpecImporter(ViewSpecificationService service) {
        this.service = service;
    }

    // ----------------------------------------------------

    public void doImport(InputStream in) {
        try {
            List<Perspective> perspectives = new VSpecReader().read(in);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }

}
