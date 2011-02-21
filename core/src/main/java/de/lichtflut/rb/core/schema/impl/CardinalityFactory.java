/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.impl;

import de.lichtflut.rb.core.schema.Cardinality;

/**
 * <p>
 *  Typical factory to generate some specific instances of {@link Cardinality} via through class members.
 *  Building instances or subclasses from this class is not allowed.  
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
	
	private static CardinalityFactory instance;
	
	static{
		instance = new CardinalityFactory();
	}
	
	public static CardinalityFactory getInstance(){
		return instance;
	}
	
	//---------------------------------------------
	
    public static Cardinality getSingleCardinality(boolean optional){
    	return new UnboundedCardinalityImpl(1, optional ? 0 : 1 );
    }
    
    public static Cardinality getSingleCardinality(){
    	 return getSingleCardinality(true);
    }
	
    public static Cardinality getUnboundedCardinality(){
    	return new UnboundedCardinalityImpl();
    }
	
    public static Cardinality getUnboundedCardinality(int min){
    	return new UnboundedCardinalityImpl(-1,min);
    }
	
    public static Cardinality getAbsoluteCardinality(int max, int min) throws IllegalArgumentException{
    	return new UnboundedCardinalityImpl(max, min);
    }
    
	
    //Try to hide the constructor, to make this class not accessable
    private CardinalityFactory(){}
    
    
	static private final class UnboundedCardinalityImpl implements Cardinality{
		
		public UnboundedCardinalityImpl(){
			//Default constructor, do nothing
		}
		
		public UnboundedCardinalityImpl(int max, int min) throws IllegalArgumentException{
			if(max > min) throw new NumberFormatException(
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
