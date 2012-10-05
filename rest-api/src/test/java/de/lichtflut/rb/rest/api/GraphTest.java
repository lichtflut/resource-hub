///*
// * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
// */
//package de.lichtflut.rb.rest.api;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.List;
//import java.util.Set;
//
//import javax.ws.rs.core.MediaType;
//
//import org.arastreju.sge.ModelingConversation;
//import org.arastreju.sge.SNOPS;
//import org.arastreju.sge.apriori.RDF;
//import org.arastreju.sge.io.RdfXmlBinding;
//import org.arastreju.sge.io.SemanticGraphIO;
//import org.arastreju.sge.io.SemanticIOException;
//import org.arastreju.sge.model.DefaultSemanticGraph;
//import org.arastreju.sge.model.ElementaryDataType;
//import org.arastreju.sge.model.SemanticGraph;
//import org.arastreju.sge.model.SimpleResourceID;
//import org.arastreju.sge.model.Statement;
//import org.arastreju.sge.model.nodes.ResourceNode;
//import org.arastreju.sge.model.nodes.SNResource;
//import org.arastreju.sge.model.nodes.SNValue;
//import org.arastreju.sge.model.nodes.views.SNClass;
//import org.arastreju.sge.naming.QualifiedName;
//import org.junit.Test;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Component;
//
//import com.sun.jersey.api.client.WebResource;
//
//import de.lichtflut.rb.core.RB;
//import de.lichtflut.rb.rest.api.models.generate.SystemDomain;
//import de.lichtflut.rb.rest.api.models.generate.SystemIdentity;
//
///**
// * <p>
// * TODO: To document
// * </p>
// * 
// * @author Nils Bleisch (nbleisch@lichtflut.de)
// * @created May 10, 2012
// */
//@Component
//public class GraphTest extends TestBase {
//
//	private static final SystemIdentity TEST_USER = new SystemIdentity();
//	private static final SystemDomain TEST_DOMAIN = new SystemDomain();
//
//	
//	// Testcase initializing
//	static {
//		// Init test-user identity
//		TEST_USER.setLoginID("test@test.de");
//		TEST_USER.setPassword("test");
//		TEST_USER.setUsername("test");
//		
//		
//		TEST_DOMAIN.setDescription("test");
//		TEST_DOMAIN.setDomainIdentifier("test");
//		TEST_DOMAIN.setTitle("test");
//		TEST_DOMAIN.setDomainAdministrator(TEST_USER);
//	}
//	
//	@Override
//	public void initTestCase(){
//		registerDomain(TEST_DOMAIN);
//		setCurrentSystemDomain(TEST_DOMAIN);
//		//Register user on current system domain "test"
//		registerSystemUser(TEST_USER);
//		setCurrentSystemUser(TEST_USER);
//	}
//
//
//	@Test
//	public void testGetWholeRBSystemTypeGraph() {
//		final String PATH = "graph/test";
//		//Lets get a webResource connector without a auth-token
//		WebResource webResource = getWebResource().id(PATH);
//		//Check if authentication and authorization mechanism does work as specified
//		assertTrue("Current System user should be sucessfully authenticated and authorized at the " +
//				"given service endpoint", doesTokenAuthWorksAsIntended(webResource, HttpMethod.GET));
//		
//		//Lets get a webResource connector with auth-token and retrieve a response via HTTP-GET
//		webResource = getWebResource(true).id(PATH);
//		String responseMsg = webResource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(String.class);
//		assertNotNull(responseMsg);
//		final SemanticGraphIO io = new RdfXmlBinding();
//		try {
//			
//			SemanticGraph graph = io.read(new ByteArrayInputStream(responseMsg.getBytes()));
//
//			// Compare if the graph retrieved from the response is  equal to the rb - internally provided graph
//			final List<SNClass> types = getProvider().getTypeManager().findAllTypes();
//			final SemanticGraph specifiedGraph = new DefaultSemanticGraph();
//			for (SNClass type : types) {
//				final List<ResourceNode> entities = findResourcesByType(getProvider().getConversation(), type);
//				for (ResourceNode entity : entities) {
//					specifiedGraph.merge(new DefaultSemanticGraph(entity));
//				}
//			}
//			// Proof that they're equal
//			assertEquals(graph.getStatements(), specifiedGraph.getStatements());
//
//		} catch (SemanticIOException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//
//	}
//	
//	
//	
//	@Test
//	public void testGetGraphNode() {
//		
//		//Try to find OrganizationResource Type node
//		//URl of Nils Bleisch graph entity description
//		String url = "http://arasteju.org/uuid#53298afa-f16d-4b7c-8d26-2145d96a2995";
//		
////		<rdf:Description rdf:about="http://arasteju.org/uuid#53298afa-f16d-4b7c-8d26-2145d96a2995">
////		<ns3:hasEmail>nbleisch@lichtflut.de</ns3:hasEmail>
////		<ns3:hasFirstname>Nils</ns3:hasFirstname>
////		<ns3:hasLastname>Bleisch</ns3:hasLastname>
////		<ns3:isEmployedBy rdf:resource="http://arasteju.org/uuid#4c95d586-3718-4138-9511-2032cc8cd1aa"/>
////		<rdf:type rdf:resource="http://rb.lichtflut.de/common#Person"/>
////		<rdf:type rdf:resource="http://rb.lichtflut.de/system#Entity"/>
////		<rdfs:label>Nils Bleisch</rdfs:label>
////		</rdf:Description>
//		
//		
//		SNClass node = getProvider().getTypeManager().findType(new SimpleResourceID(url));
//		//This node is expected to pass this test
//		assertNotNull(node);
//		String encNodeURL = URLEncoder.encode(node.getQualifiedName().toString());
//		
//		final String PATH = "graph/test/node";
//		//Lets get a webResource connector without a auth-token
//		WebResource webResource = getWebResource().id(PATH).queryParam("ID", encNodeURL);
//		//Check if authentication and authorization mechanism does work as specified
//		assertTrue("Current System user should be sucessfully authenticated and authorized at the " +
//				"given service endpoint", doesTokenAuthWorksAsIntended(webResource, HttpMethod.GET));
//		
//		//Lets get a webResource connector with auth-token and retrieve a response via HTTP-GET
//		webResource = getWebResource(true).id(PATH).queryParam("ID", encNodeURL);
//		String responseMsg = webResource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(String.class);
//		assertNotNull(responseMsg);
//		final SemanticGraphIO io = new RdfXmlBinding();
//		try {
//			SemanticGraph graph = io.read(new ByteArrayInputStream(responseMsg.getBytes()));
//			assertEquals(1, graph.getSubjects().size());
//			assertEquals(7, graph.getStatements().size());
//
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//
//	}
//	
//	
//	@Test
//	public void testGetAndImportGraphNode(){
//		final String PATH = "graph/test";
//		//Lets get a webResource connector without a auth-token
//		WebResource webResource = getWebResource().id(PATH);
//		//Check if authentication and authorization mechanism does work as specified
//		assertTrue("Current System user should be sucessfully authenticated and authorized at the " +
//		"given service endpoint", doesTokenAuthWorksAsIntended(webResource, HttpMethod.PUT));
//		
//		
//		String ns = "http://test.lichtflut.de";
//		ResourceNode node1 = new SNResource(new QualifiedName(ns + "#testCity"));			
//		DefaultSemanticGraph graph = new DefaultSemanticGraph();
//		graph.addStatement(SNOPS.associate(node1, RDF.TYPE, RB.CITY));
//		graph.addStatement(SNOPS.associate(node1, RB.HAS_DESCRIPTION, new SNValue(ElementaryDataType.STRING, "Just a test city")));
//		
//		final SemanticGraphIO io = new RdfXmlBinding();
//		ByteArrayOutputStream ouputStream = new ByteArrayOutputStream();
//		
//		try {
//			io.write(graph, ouputStream);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		
//		//Check that this graph is not persisted in store
//		ModelingConversation mc = getProvider().getConversation();
//		ResourceNode rs1 = mc.findResource(node1.getQualifiedName());
//		assertNull(rs1);
//		
//		String rdfXml = ouputStream.toString();
//		getWebResource(true).id(PATH).type(MediaType.APPLICATION_XML).entity(rdfXml, MediaType.APPLICATION_XML).put(String.class);
//		
//		//Check that the updated graph is persisted in store
//		rs1 = mc.findResource(node1.getQualifiedName());
//		assertNotNull(rs1);
//		Set<Statement> assocs = rs1.getAssociations();
//		assertTrue(assocs.size() == node1.getAssociations().size());
//		for( Statement assoc : node1.getAssociations()){
//			assertFalse(rs1.getAssociations(assoc.getPredicate()).isEmpty());
//		}
//		
//	}
//
//}
