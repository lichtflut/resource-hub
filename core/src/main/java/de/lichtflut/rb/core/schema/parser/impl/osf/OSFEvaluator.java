package de.lichtflut.rb.core.schema.parser.impl.osf;

import java.util.List;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityFactory;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;

/**
 * 
 * [TODO Insert description here.]
 * 
 * Created: May 3, 2011
 *
 * @author Nils Bleisch
 */
public final class OSFEvaluator {

	private OSFEvaluator(){}
	
	
    /**
     * 
     * @param pDec
     * @param type
     * @param value
     * @param tree
     */
	public static void evaluateGlobalPropertyDec(final PropertyDeclaration pDec, final String type, final Object value, OSFTree tree) {
		String errorMessage = "Property#" + pDec.getName()+ ": ";
		String typeLower = type.toLowerCase();
		if(typeLower.contains("type")){
			if(value instanceof ElementaryDataType)
				pDec.setElementaryDataType((ElementaryDataType) value);
			else{
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for " + type + " expecting on of " + ElementaryDataType.values().toString());
			}
		}else if(typeLower.contains("regex")){
			if(value instanceof String)
				pDec.addConstraint(ConstraintFactory.buildConstraint((String) value));
			else{
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for " + type + " expecting a String");
			}
		}else{
			tree.emitErrorMessage(errorMessage + type + " is not a known option for a global property");
		}
	}

	//-------------------------------------------------------------------------
	
    /**
     * @param assertion
     * @param pDec
     * @param type
     * @param value
     * @param tree
     */
    @SuppressWarnings("unchecked")
	public static void evaluateLocalPropertyDec(final PropertyAssertionImpl assertion,final ResourceSchema schema, final String type, final Object value, OSFTree tree) {
    	ResourceID descriptor = assertion.getPropertyDescriptor();
		if(!(descriptor.getQualifiedName().getSimpleName().startsWith("has"))){
			assertion.setPropertyDescriptor(new SimpleResourceID(new QualifiedName(descriptor.getNamespace().getUri(),"has" + descriptor.getQualifiedName().getSimpleName())));
		}
    	PropertyDeclaration pDec = assertion.getPropertyDeclaration();
    	String errorMessage = "Inner Property#" + pDec.getName()+ ": ";
		String typeLower = type.toLowerCase();
		
		if(typeLower.contains("max")){
			Cardinality c;
			if(value instanceof Integer){
				c = assertion.getCardinality();
				assertion.setCardinality(CardinalityFactory.getAbsoluteCardinality((Integer) value,c.getMinOccurs()));
			}
			else{
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for " + type + " expecting a single numerical value");
			}
		}else if(typeLower.contains("min")){
			Cardinality c;
			if(value instanceof Integer){
				c = assertion.getCardinality();
				assertion.setCardinality(CardinalityFactory.getAbsoluteCardinality(c.getMaxOccurs(), (Integer) value));
			}
			else{
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for " + type + " expecting a single numerical value");
			}
		}else if(typeLower.contains("cardinality")){
			if(value instanceof List){
				List values = (List) value;
				if(values.size()!=2){
					tree.emitErrorMessage(errorMessage +  "The number of arguments has to be exactly 2 for " + type);
					return;
				}
				try{
					assertion.setCardinality(CardinalityFactory.getAbsoluteCardinality((Integer)values.get(1),(Integer) values.get(0)));
				}catch(Exception any) {
					tree.emitErrorMessage(errorMessage + values.toString() + " is not a valid argument for " + type + " expecting an array of 2 numerical values");
					return;
				}
			}
			else{
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for " + type + " expecting an array of 2 numerical values");
				return;
			}
		}else{
			//If some global values will be overridden, this property must be a subproperty of X
			
			String newIdentifierString = schema.getDescribedResourceID().getQualifiedName().toURI();
			String oldIdentifierString = pDec.getIdentifierString();
			if(!oldIdentifierString.contains(newIdentifierString)){
				pDec.setIdentifier(newIdentifierString + "#" + pDec.getName());
			}
			evaluateGlobalPropertyDec(pDec,type, value, tree);
    	}
	}
	
	
}
