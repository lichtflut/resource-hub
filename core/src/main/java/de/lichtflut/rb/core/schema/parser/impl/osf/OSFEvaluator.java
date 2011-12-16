package de.lichtflut.rb.core.schema.parser.impl.osf;

import java.util.List;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;

/**
 * Evaluates type value params from a given PropertyDeclaration and report errors to an {@link OSFTree}.
 *
 * Created: May 3, 2011
 *
 * @author Nils Bleisch
 */
public final class OSFEvaluator {

	/**
	 * Constructor.
	 */
	private OSFEvaluator(){}

    /**
     * Please note: The type:resource has to be in a valid uri format.
     * @param pDec -
     * @param type -
     * @param value -
     * @param tree -
     */
	public static void evaluateGlobalPropertyDec(final TypeDefinition pDec, final String type, final Object value,
				final OSFTree tree) {
		String errorMessage = "Property#" + pDec.getName()+ ": ";
		String typeLower = type.toLowerCase();
		if(typeLower.contains("type")){
			if(value instanceof ElementaryDataType){
				pDec.setElementaryDataType((Datatype) value);
			}else{
				if(value instanceof String){
					//This might be a resource
					pDec.setElementaryDataType(Datatype.RESOURCE);
					//Check if the string can be a valid resource reference
					String resource = value.toString();
					if(!(QualifiedName.isQname(resource) || QualifiedName.isUri(resource))){
						tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid resource URI for "
								+ type + " expecting on of " + "xyz://namespace#resource_name");
					}else{
						pDec.addConstraint(ConstraintBuilder.
								buildConstraint(new SimpleResourceID(new QualifiedName(resource))));
					}
					return;
				}
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for "
						+ type + " expecting on of " + ElementaryDataType.values().toString());
			}
		}else if(typeLower.contains("regex")){
			if(value instanceof String){
				pDec.addConstraint(ConstraintBuilder.buildConstraint((String) value));
			}else{
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for "
						+ type + " expecting a String");
			}
		}else{
			tree.emitErrorMessage(errorMessage + type + " is not a known option for a global property");
		}
	}

	//-------------------------------------------------------------------------

    /**
     * @param assertion -
     * @param schema -
     * @param type -
     * @param value -
     * @param tree -
     */
	public static void evaluateLocalPropertyDec(final PropertyDeclarationImpl assertion,final ResourceSchema schema, final String type,
			final Object value, final OSFTree tree) {
    	ResourceID descriptor = assertion.getPropertyDescriptor();
		if(!(descriptor.getQualifiedName().getSimpleName().startsWith("has"))){
			assertion.setPropertyDescriptor(new SimpleResourceID(new QualifiedName(descriptor.getQualifiedName().getNamespace().getUri(),"has"
					+ descriptor.getQualifiedName().getSimpleName())));
		}
    	TypeDefinition pDec = assertion.getTypeDefinition();
    	String errorMessage = "Inner Property#" + pDec.getName()+ ": ";
		String typeLower = type.toLowerCase();

		if(typeLower.contains("max")){
			Cardinality c;
			if(value instanceof Integer){
				c = assertion.getCardinality();
				assertion.setCardinality(CardinalityBuilder.between(c.getMinOccurs(),(Integer) value));
			}else{
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for " + type
						+ " expecting a single numerical value");
			}
		}else if(typeLower.contains("min")){
			Cardinality c;
			if(value instanceof Integer){
				c = assertion.getCardinality();
				assertion.setCardinality(CardinalityBuilder.between((Integer) value, c.getMaxOccurs()));
			}else{
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for " + type
						+ " expecting a single numerical value");
			}
		}else if(typeLower.contains("cardinality")){
			if(value instanceof List){
				@SuppressWarnings("rawtypes")
				List values = (List) value;
				if(values.size()!=2){
					tree.emitErrorMessage(errorMessage +  "The number of arguments has to be exactly 2 for " + type);
					return;
				}
				try{
					assertion.setCardinality(CardinalityBuilder.between((Integer) values.get(0),(Integer)values
									.get(1)));
				}catch(Exception any) {
					tree.emitErrorMessage(errorMessage + values.toString() + " is not a valid argument for " + type
							+ " expecting an array of 2 numerical values");
					return;
				}
			}else{
				tree.emitErrorMessage(errorMessage + value.toString() + " is not a valid argument for " + type
						+ " expecting an array of 2 numerical values");
				return;
			}
		}else{
			//If some global values will be overridden, this property must be a subproperty of X

			String newIdentifierString = schema.getDescribedType().getQualifiedName().toURI();
			String oldIdentifierString = pDec.getIdentifierString();
			if(!oldIdentifierString.contains(newIdentifierString)){
				pDec.setIdentifier(newIdentifierString + "#" + pDec.getName());
			}
			evaluateGlobalPropertyDec(pDec,type, value, tree);
    	}
	}
}
