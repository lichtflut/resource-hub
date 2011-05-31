/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNContext;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.query.QueryManager;
import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityFactory;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.parser.RSFormat;

/**
 * <p>
 *  Store handling the persistence of {@link ResourceSchema}s.
 * </p>
 *
 * <p>
 * 	Created Apr 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBSchemaStore {
	
	private final ArastrejuGate gate;


	// -----------------------------------------------------
	
	/**
	 * Constructor. 
	 */
	public RBSchemaStore(final ArastrejuGate gate) {
		this.gate = gate;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Store the given schema.
	 * @param ctx The context.
	 * @return The corresponding persistence Resource Schema Node.
	 */
	public SNResourceSchema store(final ResourceSchema schema, Context ctx) {
		ctx = setUpContext(ctx);		
		/*
		 * How can we check if this schema does exists, and if so, replace it with the new one
		 * SNResourceSchema snSchema = loadSchemaForResource(schema.getDescribedResourceID());
		 * 
		 * 
		 */
		SNResourceSchema snSchema;
		snSchema = this.loadSchemaForResourceType(schema.getDescribedResourceID(), ctx);
		if(snSchema==null){
			if(schema.getResourceID()!=null){
				snSchema = new SNResourceSchema(schema.getResourceID().asResource());
			}else{
				snSchema = new SNResourceSchema(ctx);
			}
		}
		
		//TODO: This is not the right way to do it. Some Assocaitons (PAssertion -> PDEc) will still stay there
		//without any primary references
		//Remove all old associations
		Set<Association> assertions = snSchema.getAssociations(RBSchema.HAS_PROPERTY_ASSERT);
		for (Association assoc : assertions) {
			snSchema.remove(assoc);
		}
		
		//TODO: Resolve from store
		ResourceNode describedResource = schema.getDescribedResourceID().asResource();
		snSchema.setDescribedClass(describedResource, ctx);
		
		final ModelingConversation mc = gate.startConversation();
		for (PropertyAssertion assertion : schema.getPropertyAssertions()) {
			final SNPropertyAssertion snAssertion = new SNPropertyAssertion();
			snAssertion.setMinOccurs(toScalar(assertion.getCardinality().getMinOccurs()), ctx);
			snAssertion.setMaxOccurs(toScalar(assertion.getCardinality().getMaxOccurs()), ctx);
			snAssertion.setDescriptor(assertion.getPropertyDescriptor(), ctx);
			snSchema.addPropertyAssertion(snAssertion, ctx);
			addDeclaration(snAssertion, assertion.getPropertyDeclaration(), ctx);
		}
		mc.attach(snSchema);
		
		return snSchema;
	}
	

	// -----------------------------------------------------
	
	/**
	 * Loads all defined and persisted PropertyDeclarations from System
	 */
	public Collection<PropertyDeclaration> loadAllPropertyDeclarations(Context ctx){
		ctx = setUpContext(ctx);
		//Load all properties from store
		LinkedList<PropertyDeclaration> output = new LinkedList<PropertyDeclaration>();
		QueryManager qManager = this.gate.startConversation().createQueryManager();
		Collection<Statement> statements = qManager.findIncomingStatements(RBSchema.PROPERTY_DECL);
		for (Statement stmt : statements) {
			if(stmt==null) continue;
			output.add(convertPropertyDeclaration(new SNPropertyDeclaration((ResourceNode) stmt.getSubject())));
		}
		return output;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Loads all defined and persisted ResourceSchema'S from System
	 */
	public Collection<ResourceSchema> loadAllResourceSchemas(Context ctx){
		ctx = setUpContext(ctx);
		//Load all properties from store
		LinkedList<ResourceSchema> output = new LinkedList<ResourceSchema>();
		QueryManager qManager = this.gate.startConversation().createQueryManager();
		Collection<Statement> statements = qManager.findIncomingStatements(RBSchema.ACTIVITY_CLASS);
		for (Statement stmt : statements) {
			if(stmt==null) continue;
			output.add(convertResourceSchema(new SNResourceSchema((ResourceNode) stmt.getSubject())));
		}
		return output;
	}
	

	
	// -----------------------------------------------------
	
	public SNPropertyDeclaration store(final PropertyDeclaration decl, Context ctx){
		ctx = setUpContext(ctx);
		final ResourceNode existing = gate.startConversation().findResource(decl.getIdentifier().getQualifiedName());
		
		final SNPropertyDeclaration snDecl;
		if (existing != null) {
			snDecl = new SNPropertyDeclaration(existing);
		} else {
			snDecl = new SNPropertyDeclaration(ctx);
		}
		convertPropertyDeclaration(decl, snDecl, ctx);
		
		gate.startConversation().attach(snDecl);
		return snDecl;
	}
	
	// -----------------------------------------------------
	
	
	public SNResourceSchema loadSchemaForResourceType(final ResourceID type,Context ctx) {
		ctx = setUpContext(ctx);
		ModelingConversation mc = gate.startConversation();
		ResourceNode resourceType = mc.findResource(type.getQualifiedName());
		if(resourceType==null) return null;
		SemanticNode schema = resourceType.getSingleAssociationClient(RBSchema.DESCRIBED_BY);
		if(schema==null) return null;
		return  new SNResourceSchema(schema.asResource());
	}
	
	// -----------------------------------------------------
	
	public SNResourceSchema loadPropertyDeclaration(final ResourceID decl,Context ctx) {
		ctx = setUpContext(ctx);
		throw new NotYetImplementedException();
	}
		
	
	// -----------------------------------------------------
	
	private SNScalar toScalar(final int value) {
		return new SNScalar(BigInteger.valueOf(value));
	}
	
	// -----------------------------------------------------
	
	private Integer toInteger(final SNScalar value) {
		return value.getIntegerValue().intValue();
	}
	
	// -----------------------------------------------------
	
	/**
	 * If no special context is set, this method will return the default schema-context
	 * @param ctx
	 * @return
	 */
	private Context setUpContext(Context ctx) {
		if(ctx==null){
			ctx = new SNContext(RBSchema.CONTEXT.asResource());
		}
		return ctx;
	}

	// -- CONVERSION STUFF------------------------------------
	
	protected void convertConstraints(final SNPropertyDeclaration src, final PropertyDeclarationImpl target) {
		for (SNConstraint snConst : src.getConstraints()){
			if (snConst.isLiteralConstraint()){
				final String value = snConst.getConstraintValue().asValue().getStringValue();
				final Constraint constraint = ConstraintFactory.buildConstraint(value);
				target.addConstraint(constraint);
			} else if (snConst.isTypeConstraint()) {
				final ResourceID type = snConst.getConstraintValue().asResource();
				final Constraint constraint = ConstraintFactory.buildConstraint(type);
				target.addConstraint(constraint);
			} else {
				throw new IllegalStateException();
			}
			
		}
	}//End of method
	
	
	// -----------------------------------------------------
	
	protected void convertPropertyDeclaration(final PropertyDeclaration src, final SNPropertyDeclaration target, final Context ctx) {
		target.setDatatype(src.getElementaryDataType(), ctx);
		target.setIdentifier(src.getIdentifier(), ctx);
		for (Constraint constraint: src.getConstraints()){
			addConstraint(target, constraint, ctx);
		}
	}
	
	// -----------------------------------------------------
	
	public ResourceSchema convertResourceSchema(final SNResourceSchema snSchema) {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(snSchema);
		schema.setDescribedResourceID(snSchema.getDescribedClass());
		for (SNPropertyAssertion snAssertion : snSchema.getPropertyAssertions()){
			
			// create Property Declaration
			final SNPropertyDeclaration snDecl = snAssertion.getPropertyDeclaration();
			if(snDecl==null) continue;
			final PropertyDeclaration decl = convertPropertyDeclaration(snDecl);
			
			// create Property Assertion
			final PropertyAssertionImpl pa = new PropertyAssertionImpl(snAssertion.getDescriptor(), decl);
			int min = toInteger(snAssertion.getMinOccurs());
			int max = toInteger(snAssertion.getMaxOccurs());
			pa.setCardinality(CardinalityFactory.getAbsoluteCardinality(max, min));
			schema.addPropertyAssertion(pa);
		}
		
		return schema;
	}
	
	
	/**
	 * Stores the schemaRepresentation for a given format
	 * 
	 * TODO: Write tests to verify this behavior
	 *
	 */
	public void storeSchemaRepresentation(String representation, RSFormat format){
		ModelingConversation mc = gate.startConversation();
		ResourceNode rootNode = mc.findResource(RBSchema.ROOT_NODE.getQualifiedName());
		if(rootNode==null) rootNode = RBSchema.ROOT_NODE;
		Set<Association> assocs = rootNode.getAssociations(RBSchema.HAS_SCHEMA_REPRESENTATION);
		for (Association association : assocs) {
			rootNode.revoke(association);
		}
		assocs = rootNode.getAssociations(RBSchema.HAS_RS_FORMAT);
		for (Association association : assocs) {
			rootNode.revoke(association);
		}
		Association.create(rootNode,RBSchema.HAS_SCHEMA_REPRESENTATION,new SNValue(ElementaryDataType.STRING, representation),setUpContext(null));
		Association.create(rootNode,RBSchema.HAS_RS_FORMAT,new SNValue(ElementaryDataType.STRING, format.toString()),setUpContext(null));
		mc.attach(rootNode);
	}
	
	
	/**
	 * Returns a textual schema-reprenation of the given format.
	 * @returns null if the format is null or unknown or if there is no representation for this schema available
	 * TODO: Write tests to verify this behavior
	 *
	 */
	public String loadSchemaRepresenation(RSFormat format){
		try{
			ModelingConversation mc = gate.startConversation();
			ResourceNode rootNode = mc.findResource(RBSchema.ROOT_NODE.getQualifiedName());
			if(rootNode.getSingleAssociationClient(RBSchema.HAS_RS_FORMAT).asValue().getStringValue().equals(format.toString())){
				return rootNode.getSingleAssociationClient(RBSchema.HAS_SCHEMA_REPRESENTATION).asValue().getStringValue();
			}
		}catch(Exception any){
			return null;
		}
		return null;
	}
	
	
	// -----------------------------------------------------
	
	/**
	 * Converts a {@link SNPropertyDeclaration} to {@link PropertyDeclaration}
	 */
	protected PropertyDeclaration convertPropertyDeclaration(final SNPropertyDeclaration snDecl){
		
		PropertyDeclarationImpl pDec = new PropertyDeclarationImpl();
		
		pDec.setIdentifier(snDecl.getQualifiedName().toURI());
		pDec.setElementaryDataType(snDecl.getDatatype());
		convertConstraints(snDecl, pDec);
		return pDec;
	}
	
	
	// -----------------------------------------------------
	

	protected void addDeclaration(final SNPropertyAssertion assertion, PropertyDeclaration decl, final Context ctx) {
		final ResourceNode existing = gate.startConversation().findResource((decl.getIdentifier().getQualifiedName()));
		
		List<ResourceNode> found = gate.startConversation().createQueryManager().
										findByTag(decl.getIdentifier().getQualifiedName().toURI());
		found.size();
		
		if (existing != null) {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration(existing);
			assertion.setPropertyDeclaration(snDecl, ctx);
			convertPropertyDeclaration(decl, snDecl, ctx);
		} else {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration(ctx);
			snDecl.setName(decl.getName());
			snDecl.setNamespace(decl.getIdentifier().getNamespace());
			assertion.setPropertyDeclaration(snDecl, ctx);
			convertPropertyDeclaration(decl, snDecl, ctx);
		}
	}
	
	// -----------------------------------------------------
	
	protected void addConstraint(final SNPropertyDeclaration decl, final Constraint constraint, final Context ctx) {
		if (constraint.isLiteralConstraint()) {
			decl.addLiteralConstraint(constraint.getLiteralConstraint(), ctx);
		} else if (constraint.isResourceTypeConstraint()) {
			decl.addTypeConstraint(constraint.getResourceTypeConstraint(), ctx);
		} else {
			throw new IllegalStateException();
		}
	}
	
}
