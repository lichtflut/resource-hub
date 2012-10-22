package de.lichtflut.rb.core.schema.parser.impl.rsf;

import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;

/**
 * <p>
 *  Error reporter for RSF parsing.
 * </p>
 *
 * <p>
 * Created 19.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class RsfErrorReporterImpl implements RSErrorReporter {

    private ParsedElements result;

    // ----------------------------------------------------

    public RsfErrorReporterImpl(ParsedElements result) {
        this.result = result;
    }

    // ----------------------------------------------------

    @Override
    public void reportError(String error) {
        result.addError(error);
    }

    @Override
    public boolean hasErrorReported() {
        return !result.getErrorMessages().isEmpty();
    }
}
