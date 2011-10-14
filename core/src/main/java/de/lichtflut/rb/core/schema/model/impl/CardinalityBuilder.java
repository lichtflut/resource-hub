/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.model.impl;

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

public final class CardinalityBuilder{

	//Let's instance of this class be a Singleton

	private static final CardinalityBuilder INSTANCE =  new CardinalityBuilder();

	/**
	 * For future uses.
	 * @return the singleton-instance of this builder
	 */
	public static CardinalityBuilder getInstance(){
		return INSTANCE;
	}

	// -----------------------------------------------------

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


    // -----------CONSTRUCTOR--------------------------------------

    /**
     * Try to hide the constructor, to make this instance not directly accessible.
     */
    private CardinalityBuilder(){}


    /**
     * Default implementation of Cardinality.
     * if max == -1 it will be handled as unbounded.
     */
    private static final class SimpleCardinalityImpl implements Cardinality{

		private static final long serialVersionUID = -8837248407635938888L;

		/**
		 * Default Constructor.
		 */
		public SimpleCardinalityImpl(){
			//Default constructor, do nothing
		}

		/**
		 * Max should be greater than Min, otherwise, an {@link IllegalArgumentException} will be raised.
		 * @param min - a negative value is interpreted as a positive one.
		 * @param max - unbound could be set up with -1.
		 */
		public SimpleCardinalityImpl(final int min,final int max){
			int minimum = min;
			minimum = Math.abs(min);
			if((max < min) && (max!=-1)){
				throw new IllegalArgumentException(
						"The minimum count of " + minimum + " must be less than the maximum count of " + max);
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
		 * @return true if is single cardinality
		 */
		public boolean isSingle() {
			return (getMax()==1);
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
			return ("Min: " + getMinOccurs()
					+  ", Max: "
					+ ((getMaxOccurs()==Integer.MAX_VALUE) ? "unlimited" : getMaxOccurs()));
		}

	}//End of inner class CardinalityImpl

}
