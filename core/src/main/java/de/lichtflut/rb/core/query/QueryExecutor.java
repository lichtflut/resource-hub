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
package de.lichtflut.rb.core.query;

import org.apache.commons.lang3.time.StopWatch;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.query.script.QueryScriptEngine;
import org.arastreju.sge.query.script.QueryScriptException;
import org.arastreju.sge.query.script.ScriptEngineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;

/**
 * <p>
 *  Executor for queries and query script.
 * </p>
 *
 * <p>
 *  Created July 18, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class QueryExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryExecutor.class);

    private Conversation conversation;
    private QueryContext qc;

    // ----------------------------------------------------

    public QueryExecutor(Conversation conversation) {
        this(conversation, new QueryContext());

    }

    public QueryExecutor(Conversation conversation, QueryContext qc) {
        this.conversation = conversation;
        this.qc = qc;
    }

    // ----------------------------------------------------

    public QueryResult execute(String script) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ScriptEngineContext ctx = new ScriptEngineContext(conversation);
        try {

            QueryScriptEngine engine = new QueryScriptEngine(ctx);
            String finalScript = qc.substitute(script, conversation);
            LOGGER.info("Executing query script {}", finalScript);
            engine.execute(finalScript);

            stopWatch.stop();
            LOGGER.info("Execution needed {}", NumberFormat.getIntegerInstance().format(stopWatch.getNanoTime()));

            return ctx.getQueryResult();
        } catch (QueryScriptException e) {
            throw new RuntimeException(e);
        }
    }
}
