/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.QueryManager;
import de.lichtflut.rb.core.api.RBEntityManagement;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.RBEntityFactory;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.persistence.RBSchemaStore;
import de.lichtflut.rb.core.schema.persistence.SNResourceSchema;

/**
 * <p>
 * Reference implementation of {@ResourceTypeManagement} for a lucence based neo4j backend.
 * </p>
 *
 * Created: May 18, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class RBEntityManagementImpl implements RBEntityManagement{

	private ArastrejuGate gate;

	// -- CONSTRUCTOR---------------------------------------

	/**
	 * <p>
	 * This is the standard constructor.
	 * </p>
	 * @param gate the ArastrejuGate instance which is necessary to store, update and resolve ResourceTypeInstances
	*/
	public RBEntityManagementImpl(final ArastrejuGate gate){
		this.gate = gate;
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public boolean createOrUpdateRTInstance(final RBEntityFactory<Object> instance) {
		try{
			ModelingConversation mc = gate.startConversation();
			instance.createAssociations(null);
			mc.attach(instance);
		}catch(Exception any){
			return false;
		}
		return true;
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@SuppressWarnings("rawtypes")
	@Override
	public Collection<RBEntityFactory> loadAllResourceTypeInstancesForSchema(
			final ResourceSchema schema, final String filter, final SearchContext ctx) {
		SearchContext context = ctx;
		String filterString = filter;
		if(filter==null || filter.equals("")){
			return loadAllResourceTypeInstancesForSchema(schema);
		}
		if(ctx==null){
			context = SearchContext.CONJUNCT_MULTIPLE_KEYWORDS;
		}
		filterString = getConvertedFilterBySearchContext(filterString, context);
		Collection<RBEntityFactory> output = new ArrayList<RBEntityFactory>();
		QueryManager qManager = gate.startConversation().createQueryManager();
		List<ResourceNode> nodes = qManager.findByTag(filterString);
		for (ResourceNode resourceNode : nodes) {
			//Check if this Instance depends on a ResourceSchema
			Set<SemanticNode> clients = resourceNode.getAssociationClients(RDF.TYPE);
			for (SemanticNode semanticNode : clients) {
				if(semanticNode.isResourceNode()){
					ResourceNode rNode = semanticNode.asResource();
					if(rNode.getQualifiedName().equals(schema.getDescribedResourceID().getQualifiedName())){
						if(rNode.asResource().getSingleAssociationClient(RBSchema.DESCRIBED_BY) != null){
							output.add(new RBEntityImpl(schema,resourceNode));
							break;
						}
					}
				}
			}
		}
		return output;
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@SuppressWarnings("rawtypes")
	@Override
	public Collection<RBEntityFactory> loadAllResourceTypeInstancesForSchema(final ResourceSchema schema) {
	      Collection<RBEntityFactory> output = new ArrayList<RBEntityFactory>();
          ModelingConversation mc = gate.startConversation();
          Set<Statement> statements = mc.createQueryManager().findIncomingStatements(schema.getDescribedResourceID());
          for (Statement statement : statements) {
             if(statement.getPredicate().equals(RDF.TYPE)){
            	 RBEntityFactory  instance = new RBEntityImpl(schema, statement.getSubject().asResource());
                 output.add(instance);
             }
          }
         return output;
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@SuppressWarnings("rawtypes")
	@Override
	public Collection<RBEntityFactory> loadAllResourceTypeInstancesForSchema(final Collection<ResourceSchema> schemas) {
		Collection<RBEntityFactory> output = new ArrayList<RBEntityFactory>();
	    for (ResourceSchema resourceSchema : schemas) {
			output.addAll(loadAllResourceTypeInstancesForSchema(resourceSchema));
		}
	    return output;
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@SuppressWarnings("rawtypes")
	@Override
	public Collection<RBEntityFactory> loadAllResourceTypeInstancesForSchema(
			final Collection<ResourceSchema> schemas, final String filter,  final SearchContext ctx) {
		SearchContext context = ctx;
		String filterString = filter;
		if(filter==null || filter.equals("")){
			return loadAllResourceTypeInstancesForSchema(schemas);
		}
		if(ctx==null){
			context = SearchContext.CONJUNCT_MULTIPLE_KEYWORDS;
		}
		Collection<RBEntityFactory> output = new ArrayList<RBEntityFactory>();
		for (ResourceSchema schema : schemas) {
			output.addAll(loadAllResourceTypeInstancesForSchema(schema, filterString,context));
		}
		return output;
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@SuppressWarnings("rawtypes")
	@Override
	public RBEntityFactory loadRBEntity(final QualifiedName qn) {
		if(qn==null){ return null;}
		ModelingConversation mc = this.gate.startConversation();
		ResourceNode node = mc.findResource(qn);
		if(node==null){ return null;}
		//Try to find out the related RT's and Schemas
		Set<Association> type_assocs = node.getAssociations(RDF.TYPE);

		for (Association association : type_assocs) {
			if(association.getObject().isResourceNode()){
				Set<Association> potential_schemas =
					association.getObject().asResource().getAssociations(RBSchema.DESCRIBED_BY);
				if(potential_schemas.size()<=0){ return null;}
				//There might be a resource schema, get 'dem!
				ResourceNode schemaNode = (ResourceNode) new ArrayList<Association>(potential_schemas).get(0).getObject();
				ResourceSchema schema =
					(new RBSchemaStore(this.gate).convertResourceSchema(new SNResourceSchema(schemaNode)));
				//Build the RBEntity based on node and extracted schema
				RBEntityFactory entity = new RBEntityImpl(schema, node);
				return entity;
			}
		}
		return null;
	}

	// -----------------------------------------------------


	/** {@inheritDoc} */
	@SuppressWarnings("rawtypes")
	@Override
	public RBEntityFactory loadRBEntity(final String nodeIdentifier) {
		return loadRBEntity(new QualifiedName(nodeIdentifier));
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@SuppressWarnings("rawtypes")
	@Override
	public RBEntityFactory loadRBEntity(final ResourceNode node) {
		return loadRBEntity(node.getQualifiedName());
	}


	// --PRIVATE MEMBER---------------------------------------


	/**
	 * Helper method to convert the filter in a way that the underlying lucene engine can work with.
	 * @param filter - the search keywords
	 * @param ctx - the {@link SearchContxt} which describes your search mechanics and also might modify the keywords
	 * @return the based on the searchconxted modified filter-string
	 */
	private String getConvertedFilterBySearchContext(final String filter, final SearchContext ctx) {
		String filterString = filter;
		switch(ctx){
			// -----------------------------------------------------
			case CONJUNCT_MULTIPLE_KEYWORDS:
				filterString = filterString.replace("*", " ");
				filterString = filterString.replace("?", " ");
				String[] keywords = filterString.split(" ");
				StringBuilder convertedInput = new StringBuilder();
				for (int i = 0; i < keywords.length; i++) {
					if(keywords[i].length()>0){
						convertedInput.append(keywords[i]+"*" + ((i < (keywords.length-1)) ? "AND" : "") + " ");
					}else{
						convertedInput.append(keywords[i]);
					}
				}
				filterString=  convertedInput.toString();
				break;
				// -----------------------------------------------------
			default: break;
		}
		return filterString;
	}


}
