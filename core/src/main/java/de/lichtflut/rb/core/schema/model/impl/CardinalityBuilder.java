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
package de.lichtflut.rb.core.schema.model.impl;

import java.io.Serializable;

import de.lichtflut.rb.core.schema.model.Cardinality;

/**
 * <p>
 *  Typical factory to generate some specific instances of {@link Cardinality} through class members.
 *  Building instances or subclasses from this class is not allowed.
 *  Therefore: This factory is described as final and every constructor is affected to the private modifier
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2011
 * </p>
 *
 * @author Nils Bleisch
 */

public final class CardinalityBuilder implements Serializable {

	/**
	 * @return a 1:1-Cardinality
	 */
	public static Cardinality hasExcactlyOne(){
		return hasExactly(1);
	}

	// -----------------------------------------------------

	/**
	 * @return a 0:1-Cardinality
	 */
	public static Cardinality hasOptionalOne(){
		return hasOptionalOneUpTo(1);
	}

	// -----------------------------------------------------

	/**
	 * @return a 1:N-Cardinality
	 */
	public static Cardinality hasAtLeastOneToMany(){
		return between(1,-1);
	}

	// -----------------------------------------------------

	/**
	 * @param least -
	 * @return a least:N-Cardinality
	 */
	public static Cardinality hasAtLeast(final int least){
		return between(Math.abs(least), -1);
	}

	// -----------------------------------------------------

	/**
	 * @return a 0:N-Cardinality
	 */
	public static Cardinality hasOptionalOneToMany(){
		return new SimpleCardinalityImpl();
	}

	// -----------------------------------------------------

	/**
	 * @param max -
	 * @return a 1:max-Cardinality
	 */
	public static Cardinality hasAtLeastOneUpTo(final int max){
		return between(1,Math.abs(max));
	}

	// -----------------------------------------------------

	/**
	 * @param max -
	 * @return a 0:max-Cardinality
	 */
	public static Cardinality hasOptionalOneUpTo(final int max){
		return between(0,Math.abs(max));
	}

	// -----------------------------------------------------

	/**
	 * @param value -
	 * @return a value:value-Cardinality
	 */
	public static Cardinality hasExactly(final int value){
		return between(Math.abs(value),Math.abs(value));
	}

	// -----------------------------------------------------

	/**
	 * Throws an {@link IllegalArgumentException} if max < min.
	 * @param min -
	 * @param max -
	 * @return a min:max-Cardinality
	 */
	public static Cardinality between(final int min, final int max){
		return new SimpleCardinalityImpl(min, max);
	}

	// ------------------------------------------------------

	/**
	 * Get the cardinality as a String like <code>[1..n]</code>
	 */
	public static String getCardinalityAsString(final Cardinality cardinality) {
		int min = cardinality.getMinOccurs();
		int max = cardinality.getMaxOccurs();
		String s = "[";
		if (min == 0) {
			if(max == 1){
				s+= "0..";
			}else{
				s += "n..";
			}
		} else {
			s += String.valueOf(min) + "..";
		}
		if (max == Integer.MAX_VALUE) {
			s += "n";
		} else {
			s += String.valueOf(max);
		}
		s += "]";
		return s;
	}

	/**
	 * Extracts the cardinality from a String like:
	 * <code>
	 * [1..n]
	 * </code>
	 * <br>As a fallback <code>hasOptionalOneToMany()</code> will be used.
	 * @param string
	 */
	public static Cardinality extractFromString(final String string){
		// Check RegEx and null String
		if(((string == null) || (!string.matches("\\[.*\\.\\..*\\]")))){
			return hasOptionalOneToMany();
		}
		int min, max;
		String clean = string.replace("[", "").replace("]", "");
		String[] temp = clean.split("\\.\\.");
		min = convertToInt(temp[0].trim(), false);
		max = convertToInt(temp[1].trim(), true);
		return between(min, max);
	}

	/**
	 * Converts a String into int.
	 * @param s - string
	 * @param flag - if true and s is not an integer, the maximum value will be assigned, minimum if false.
	 */
	private static int convertToInt(final String s, final boolean flag) {
		int num;
		if(Character.isDigit(s.charAt(0))){
			num = Integer.parseInt(s);
		}else{
			if(flag){
				num = -1;
			}else{
				num = 0;
			}
		}
		return num;
	}

	// -----------CONSTRUCTOR--------------------------------------

	/**
	 * Try to hide the constructor, to make this instance not directly accessible.
	 */
	private CardinalityBuilder(){}

	// ------------------------------------------------------

	/**
	 * Default implementation of Cardinality.
	 * if max == -1 it will be handled as unbounded.
	 */
	private static final class SimpleCardinalityImpl implements Cardinality{

		/**
		 * Default Constructor.
		 */
		public SimpleCardinalityImpl(){
			//Default constructor, do nothing
		}

		/**
		 * Max should be greater than Min, otherwise, an
		 * {@link IllegalArgumentException} will be raised.
		 * 
		 * @param min
		 * @param max
		 */
		public SimpleCardinalityImpl(final int min, final int max) {
			int minimum = min;
			if (min < 0) {
				throw new IllegalArgumentException("The minimum count of " + minimum + " must notbe negativ");
			}
			if ((max < min) && (max != -1)) {
				throw new IllegalArgumentException("The minimum count of " + minimum
						+ " must be less than the maximum count of " + max);
			}
			setMax(max);
			setMin(minimum);
		}

		//Define inner members and fields
		private int max=-1, min=0;

		/**
		 * @return the maximal occurence, if unbounded Integer.MAX_VALUE is returned
		 */
		public int getMaxOccurs(){
			return getMax()==-1 ? Integer.MAX_VALUE : getMax();
		}

		// -----------------------------------------------------

		/**
		 * @return the minimal occurence
		 */
		public int getMinOccurs() {
			return Math.abs(getMin());
		}

		// -----------------------------------------------------

		/**
		 * @return true if is unbounded cardinality
		 */
		public boolean isUnbound() {
			return (getMax()==-1);
		}

		// -----------------------------------------------------

		//Getters and Setters
		/**
		 * @return the maximum
		 */
		public int getMax() {
			return max;
		}

		// -----------------------------------------------------

		/**
		 * @param max -
		 */
		private void setMax(final int max) {
			this.max = max;
		}

		// -----------------------------------------------------

		/**
		 * @return the minimum
		 */
		public int getMin() {
			return min;
		}

		// -----------------------------------------------------

		/**
		 * @param min -
		 */
		private void setMin(final int min) {
			this.min = min;
		}

		// -----------------------------------------------------
		@Override
		public String toString(){
			return "[" + getMinOccurs()
					+  " .. "
					+ (isUnbound() ? "unbound" : getMaxOccurs())
					+ "]";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + max;
			result = prime * result + min;
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof SimpleCardinalityImpl)) {
				return false;
			}
			SimpleCardinalityImpl other = (SimpleCardinalityImpl) obj;
			if (max != other.max) {
				return false;
			}
			if (min != other.min) {
				return false;
			}
			return true;
		}

	}//End of inner class CardinalityImpl

}
