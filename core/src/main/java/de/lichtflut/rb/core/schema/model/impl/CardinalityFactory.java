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

public final class CardinalityFactory{

	//Let's instance of this class be a Singleton
	
	private static CardinalityFactory instance =  new CardinalityFactory();
	
	public static CardinalityFactory getInstance(){
		return instance;
	}
	
	//---------------------------------------------
		
	public static Cardinality hasExcactlyOne(){
		return hasExactly(1);
	}
	
	//---------------------------------------------
	
	public static Cardinality hasOptionalOne(){
		return hasOptionalOneUpTo(1);
	}

	//---------------------------------------------
	
	public static Cardinality hasAtLeastOneToMany(){
		return getAbsoluteCardinality(-1,1);
   }
	
	//---------------------------------------------
	
	public static Cardinality hasOptionalOneToMany(){
       return new UnboundedCardinalityImpl();
	}
	
	public static Cardinality hasAtLeastOneUpTo(int max){
	  return getAbsoluteCardinality(Math.abs(max),1);
   }
	
	//---------------------------------------------
	
	public static Cardinality hasOptionalOneUpTo(int max){
	  return getAbsoluteCardinality(Math.abs(max),0);
	}

	//---------------------------------------------
	
	public static Cardinality hasExactly(int value){
	     return getAbsoluteCardinality(Math.abs(value),Math.abs(value));
	}
	
	//---------------------------------------------
	
    public static Cardinality getAbsoluteCardinality(int max, int min) throws IllegalArgumentException{
    	return new UnboundedCardinalityImpl(max, min);
    }
    
    
  //---------------------------------------------
    
	
    //Try to hide the constructor, to make this class not accessable
    private CardinalityFactory(){}
    
    
	static private final class UnboundedCardinalityImpl implements Cardinality{
		
		public UnboundedCardinalityImpl(){
			//Default constructor, do nothing
		}
		
		/**
		 * Max should be greater than Min, otherwise, an {@link IllegalArgumentException} will be raised
		 * @param max, int, unbound could be set up with -1 
		 * @param min, int, a negative value is interpreted as a positive one 
		 * @throws IllegalArgumentException
		 */
		public UnboundedCardinalityImpl(int max, int min) throws IllegalArgumentException{
			min = Math.abs(min);
			if((max < min) && (max!=-1)) throw new IllegalArgumentException(
								"The minimum count of " + min + " must be less than the maximum count of " + max);
			setMax(max);
			setMin(min);
		}
		

		//Define inner members and fields
		private int max=-1, min=0;
		
		public int getMaxOccurs(){
			return getMax()==-1 ? Integer.MAX_VALUE : getMax();
		}

		public int getMinOccurs() {
			return Math.abs(getMin());
		}

		public boolean isSingle() {
			return (getMax()==1);
		}

		public boolean isUnbound() {
			return (getMax()==-1);
		}
		
		
		//Getters and Setters
		
		public int getMax() {
			return max;
		}

		private void setMax(int max) {
			this.max = max;
		}

		public int getMin() {
			return min;
		}

		private void setMin(int min) {
			this.min = min;
		}
 
		//------------------------------------
		
	}//End of inner class CardinalityImpl

}
