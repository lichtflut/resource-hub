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
package de.lichtflut.rb.core.schema.parser;


/**
 * This is to report and log occurred errors during parsing.
 *
 * Created: Apr 21, 2011
 *
 * @author Nils Bleisch
 */
public interface RSErrorReporter {
	/**
	 * No use to describe what this method should do ;) .
	 * @param error to report
	 */
	void reportError(String error);

	/**
	 * Checks whether error has been reported.
	 * @return true if error has been reported, false if not
	 */
	boolean hasErrorReported();
}
