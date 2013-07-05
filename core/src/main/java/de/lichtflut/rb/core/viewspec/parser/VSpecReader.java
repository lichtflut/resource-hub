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
package de.lichtflut.rb.core.viewspec.parser;

import de.lichtflut.rb.core.viewspec.Perspective;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  Reader of VSpecs.
 * </p>
 *
 * <p>
 *  Created July 5, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class VSpecReader {

    public List<Perspective> read(InputStream in) throws IOException, RecognitionException {
        CharStream input = new ANTLRInputStream(in, "UTF-8");
        VSpecLexer lexer = new VSpecLexer(input);
        VSpecParser parser = new VSpecParser(new CommonTokenStream(lexer));
        VSpecParser.statements_return root = parser.statements();
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(root.getTree());
        VSpecTree walker = new VSpecTree(nodes);
        walker.statements();
        return walker.getCollector().getPerspectives();
    }

}
