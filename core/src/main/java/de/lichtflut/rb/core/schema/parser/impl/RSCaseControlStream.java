/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
