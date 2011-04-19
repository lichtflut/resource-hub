package de.lichtflut.rb.core.schema.parser.impl;

import org.antlr.runtime.ANTLRStringStream;

/**
 *
 * @author Nils Bleisch
 * When CaseSensitive is set to false, all the input will be converted to UpperCase
 */
public class RBCaseControlStream extends ANTLRStringStream {

  private boolean caseSensitive = true; 

  /** Copy data from string to a local char array */
  public RBCaseControlStream(String input) {
    super();
    this.data = input.toCharArray();
    this.n = input.length();
  }

  /** This is the preferred constructor as no data is actually copied */
  public RBCaseControlStream(char[] data, int numberOfActualCharsInArray) {
    super();
    this.data = data;
    this.n = numberOfActualCharsInArray;
  }

  @Override
  public int LA(int i) {
    int c = super.LA(i);
    return (!isCaseSensitive()) ? Character.toUpperCase(c) : c;
  }


  public void setCaseSensitive(boolean caseSensitive) {
	this.caseSensitive = caseSensitive;
  }


  public boolean isCaseSensitive() {
	return caseSensitive;
  }
}
