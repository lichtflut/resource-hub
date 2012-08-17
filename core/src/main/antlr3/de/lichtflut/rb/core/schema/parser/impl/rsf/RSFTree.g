tree grammar RSFTree;

options {
  tokenVocab = RSF;
  ASTLabelType = CommonTree;
}

@header{
/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.NamespaceHandle;
import org.arastreju.sge.model.nodes.SNResource;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultImpl;
import de.lichtflut.rb.core.schema.parser.impl.VisualizationBuilder;

import java.util.Locale;
import java.util.HashMap;

}
@members{

	private static final String ID_CONST = "id";
	private static final String NAME_CONST = "name";
	private static final String APPLICABLE_DATATYPES_CONST = "applicable-datatypes";
	private static final String DATATYPE_CONST = "datatype";
	private static final String LITERAL_CONSTRAINT_CONST = "literal-constraint";
	private static final String REFERENCE_CONSTRAINT_CONST = "reference-constraint";
	private static final String RESOURCE_CONSTRAINT_CONST = "resource-constraint";
	private static final String FIELD_LABEL_CONST = "field-label";
	private static final String FIELD_LABEL_INT_CONST = "field-label\\[..\\]";


	private VisualizationBuilder visBuilder = new VisualizationBuilder();
	private List<ResourceSchema> schemaList = new ArrayList<ResourceSchema>();
	private List<Constraint> publicConstraints = new ArrayList<Constraint>();
	private HashMap<String, NamespaceHandle> nsMap = new HashMap<String, NamespaceHandle>();
	private RSErrorReporter errorReporter;

	public void setErrorReporter(RSErrorReporter errorReporter) {
       	this.errorReporter = errorReporter;
    }
    
	private String removeAll(String s, String remove){
		s = s.replaceAll(remove, "");
		return s;
	}
	
	private String extract(String s, String delim, int pos){
		String[] array = s.split(delim);
		return array[pos];
	}
	
	private void buildDeclProperties(String key, String value){
		PropertyDeclarationImpl pDec = $property_decl::pDec;
		if(DATATYPE_CONST.equals(key)){
			pDec.setDatatype(Datatype.valueOf(value.toUpperCase()));
		}
		if(LITERAL_CONSTRAINT_CONST.equals(key)){
			ConstraintImpl constraint = new ConstraintImpl();
			constraint.buildLiteralConstraint(value);
			pDec.setConstraint(constraint);
		}
		if(RESOURCE_CONSTRAINT_CONST.equals(key) || REFERENCE_CONSTRAINT_CONST.equals(key)){
			ConstraintImpl constraint = new ConstraintImpl();
			constraint.buildReferenceConstraint(toResourceID(value), REFERENCE_CONSTRAINT_CONST.equals(key));
			pDec.setConstraint(constraint);
		}
		if(FIELD_LABEL_CONST.equals(key)){
			if(pDec.getFieldLabelDefinition() == null) {
				pDec.setFieldLabelDefinition(new FieldLabelDefinitionImpl(value));
			} else {
				pDec.getFieldLabelDefinition().setDefaultLabel(value);
			}
		}
		if (key.matches(FIELD_LABEL_INT_CONST)) {
			if(pDec.getFieldLabelDefinition() == null){
				pDec.setFieldLabelDefinition(new FieldLabelDefinitionImpl(value));
			}
			String locale = key.substring(12, 14);
			pDec.getFieldLabelDefinition().setLabel(new Locale(locale), value);
		}
	}

	private void addVisualizationInfo(String key, String value) {
        PropertyDeclarationImpl pDec = $property_decl::pDec;
        visBuilder.add(pDec, key, value);
	}
	
	public void buildPublicConstraint(String key, String value){
		ConstraintImpl constraint = $public_constraint::constraint;
		
		if(NAME_CONST.equals(key)){
			constraint.setName(value);
		}
		if(APPLICABLE_DATATYPES_CONST.equals(key)){
			List<Datatype> list = new ArrayList<Datatype>();
			String[] array = value.split(",");
			for(String s : array){
				list.add(Datatype.valueOf(s.trim().toUpperCase()));
			}
			constraint.setApplicableDatatypes(list);
		}
		if(LITERAL_CONSTRAINT_CONST.equals(key)){
			constraint.setLiteralConstraint(value);
		}
	}
	
	public ResourceID toResourceID(final String name) {
		if (QualifiedName.isUri(name)) {
			return new SimpleResourceID(name); 
		} else if (!QualifiedName.isQname(name)) {
			throw new IllegalArgumentException("Name is neither URI nor QName: " + name);
		}
		final String prefix = QualifiedName.getPrefix(name);
		final String simpleName = QualifiedName.getSimpleName(name);
		if (nsMap.containsKey(prefix)) {
			return new SimpleResourceID(nsMap.get(prefix).getUri(), simpleName);
		} else {
			final NamespaceHandle handle = new NamespaceHandle(null, prefix);
			nsMap.put(prefix, handle);
			return new SimpleResourceID(handle.getUri(), simpleName);	
		}
	}
}
// Input contains 1 or more statements
statements returns [ RSParsingResult parsed ] scope{
	RSParsingResultImpl elements;
}
@init {
    	$statements::elements = new RSParsingResultImpl();
    }
	:   ^(STATEMENTS statement +) {
									$parsed = $statements::elements;
									//$elements.addSchemas(schemaList);
									//$elements.addConstraints(publicConstraints);
								 };
	
// A statement is either a namespace, a public constraint or a schema
statement : 	namespace_decl 
			|	public_constraint
			| 	schema_decl 
			;

// Definition of a namespace
namespace_decl: ^(NAMESPACE (ns=STRING qn=STRING
	{
		String prefix = removeAll($qn.text, "\"");
		String uri = removeAll($ns.text, "\"");
		nsMap.put(prefix, new NamespaceHandle(uri, prefix));
		}
		
)) ;

//Declaration of public constraints
public_constraint scope{
	ConstraintImpl constraint;
}
				: ^(PUBLIC_CONSTRAINT
					(id=STRING {
						String cleaned = removeAll($id.text, "\"");
						NamespaceHandle currentNs = nsMap.get(extract(cleaned, ":", 0));
						String described = extract(cleaned, ":", 1);
						SNResource snResource = new SNResource(new QualifiedName(currentNs.getUri() + described));
						$public_constraint::constraint = new ConstraintImpl(snResource);
						$public_constraint::constraint.isPublic(true);
						//publicConstraints.add($public_constraint::constraint);
						$statements::elements.addConstraint($public_constraint::constraint);
					})
						constraint + );

constraint: ^(PROPERTY_ASSIGNMENT ( property_key value {
							buildPublicConstraint($property_key.text, removeAll($value.text, "\""));
						}));
						
// Definition of a schema
schema_decl scope{
	ResourceSchemaImpl schema;
	NamespaceHandle currentNS;
}
	 		:  ^(SCHEMA 
			(id=STRING {
				String cleaned = removeAll($id.text, "\"");
				$schema_decl::currentNS = nsMap.get(extract(cleaned, ":", 0));
				String described = extract(cleaned, ":", 1);
				$schema_decl::schema = new ResourceSchemaImpl(new SimpleResourceID($schema_decl::currentNS.getUri() + described));
				//schemaList.add($schema_decl::schema);
				$statements::elements.addResourceSchema($schema_decl::schema);
			})
	 		decl + )
	 			 ;

//Definition of a declaration within a schema
decl : 		label_decl
		|	property_decl
		;

//Definition of a label-declaration
label_decl:  ^(LABEL (rule=STRING{
						String cleaned = removeAll($rule.text, "\"");
						try{
						$schema_decl::schema.setLabelBuilder(new ExpressionBasedLabelBuilder(cleaned, nsMap));
						}catch (LabelExpressionParseException e) {
							emitErrorMessage(e.getMessage());
						}
}));

// Definition of a property-declaration
property_decl scope{
PropertyDeclarationImpl pDec;
} 
@init{
	$property_decl::pDec = new PropertyDeclarationImpl();

}: ^(PROPERTY (s=STRING cardinal_decl assignment+ visualization? {
					String cleaned = removeAll($s.text, "\"");
					ResourceID sid = toResourceID(cleaned);
					$property_decl::pDec.setPropertyDescriptor(sid);
					$property_decl::pDec.setCardinality(CardinalityBuilder.extractFromString($cardinal_decl.text));
					$schema_decl::schema.addPropertyDeclaration($property_decl::pDec);
				}
			)) ;

// Definition of a cardinality-declaration
cardinal_decl returns [String s]
@init{
	$s = $cardinal_decl.text;
} :  ^(CARDINALITY CARDINALITY_DECL) ;

//Definition of an assignment within a property-declaration
assignment : ^(PROPERTY_ASSIGNMENT ( property_key value {
					buildDeclProperties($property_key.text, removeAll($value.text, "\""));
				}));

// Definition of an assignments' key
property_key returns [String s]
@init{
	$s = $property_key.text;
}
	: FIELD_LABEL 
	| INT_LABEL 
	| DATATYPE 
	| RESOURCE_CONSTRAINT
	| REFERENCE_CONSTRAINT
	| NAME
	| APPLICABLE_DATATYPES
	| LITERAL_CONSTRAINT
	;

//Definition of a visualization within a property-declaration
visualization :
    ^(VISUALIZATION (vis_assignment+));

//Definition of a visualization assignment within a property-declaration's visualization
vis_assignment : ^(VIS_ASSIGNMENT ( visualization_key value {
                    addVisualizationInfo($visualization_key.text, $value.text);
				}));

// Definition of an visualization assignment's key
visualization_key returns [String s]
@init { $s = $visualization_key.text; }
:  EMBEDDED |
   FLOATING |
   STYLE
;

//Definition of a value within a property-declaration
value returns [String s] : STRING {$s = $STRING.text;};