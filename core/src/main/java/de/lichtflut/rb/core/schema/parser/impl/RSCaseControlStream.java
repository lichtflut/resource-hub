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
package de.lichtflut.rb.core.schema.parser.impl;

import org.antlr.runtime.ANTLRStringStream;

/**
 *
 * @author Nils Bleisch
 * When CaseSensitive is set to false, all the input will be converted to UpperCase
 */
public class RSCaseControlStream extends ANTLRStringStream {

	/**
	 *
	 */
  private boolean caseSensitive = true;

  /**
   * Copy data from string to a local char array.
   * @param input String to be converted to char[]
   */
  public RSCaseControlStream(final String input) {
    super();
    this.data = input.toCharArray();
    this.n = input.length();
  }

  /**
   * This is the preferred constructor as no data is actually copied.
   * @param data -
   * @param numberOfActualCharsInArray -
   */
  public RSCaseControlStream(final char[] data, final int numberOfActualCharsInArray) {
    super();
    this.data = data;
    this.n = numberOfActualCharsInArray;
  }

  @Override
  public int LA(final int i) {
    int c = super.LA(i);
    return (!isCaseSensitive()) ? Character.toUpperCase(c) : c;
  }

  /**
   * Set case sensitivity.
   * @param caseSensitive -
   */
  public void setCaseSensitive(final boolean caseSensitive) {
	this.caseSensitive = caseSensitive;
  }

  /**
   * Returns whether case sensitivtity is true or false.
   * @return true if it is case sensitive, false if not
   */
  public boolean isCaseSensitive() {
	return caseSensitive;
  }
}
