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

import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ReferenceConstraint;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;

import java.util.Locale;
import java.util.HashMap;

}
@members{

	private static final String DATATYPE_CONST = "datatype";
	private static final String LITERAL_CONSTRAINT_CONST = "literal-constraint";
	private static final String CONSTRAINT_REFERENCE_CONST = "reference-constraint";
	private static final String RESOURCE_CONSTRAINT_CONST = "resource-constraint";
	private static final String FIELD_LABEL_CONST = "field-label";
	private static final String FIELD_LABEL_INT_CONST = "field-label\\[..\\]";
	
	private List<ResourceSchemaImpl> schemaList = new ArrayList<ResourceSchemaImpl>();
	private HashMap<String, NamespaceHandle> nsMap = new HashMap<String, NamespaceHandle>();
	private RSErrorReporter errorReporter;

	public void setErrorReporter(RSErrorReporter errorReporter) {
       	this.errorReporter = errorReporter;
    }
    
    public void emitErrorMessage(String msg) {
       	errorReporter.reportError(msg);
    }
    
	private String removeAll(String s, String remove){
		s = s.replaceAll(remove, "");
		return s;
	}
	
	private String extract(String s, String delim, int pos){
		String[] array = s.split(delim);
		return array[pos];
	}
	
	private void buildTypeDef(String key, String value){
		String ns = $schema_decl::currentNS.getUri();
		PropertyDeclarationImpl pDec = $property_decl::pDec;
		if(DATATYPE_CONST.equals(key)){
			pDec.setDatatype(Datatype.valueOf(value.toUpperCase()));
		}
		if(LITERAL_CONSTRAINT_CONST.equals(key)){
			ReferenceConstraint constraint = new ReferenceConstraint();
			constraint.buildLiteralConstraint(value);
			pDec.setConstraint(constraint);
		}
		if(RESOURCE_CONSTRAINT_CONST.equals(key) || CONSTRAINT_REFERENCE_CONST.equals(key)){
			ReferenceConstraint constraint = new ReferenceConstraint();
			constraint.buildReferenceConstraint(toResourceID(value), CONSTRAINT_REFERENCE_CONST.equals(key));
			pDec.setConstraint(constraint);
		}
		if(FIELD_LABEL_CONST.equals(key)){
			if(pDec.getFieldLabelDefinition() == null){
				pDec.setFieldLabelDefinition(new FieldLabelDefinitionImpl(value));
			}else{
				pDec.getFieldLabelDefinition().setDefaultLabel(value);
			}
		}
		if(key.matches(FIELD_LABEL_INT_CONST)){
			if(pDec.getFieldLabelDefinition() == null){
				pDec.setFieldLabelDefinition(new FieldLabelDefinitionImpl(value));
			}
			String locale = key.substring(12, 14);
			pDec.getFieldLabelDefinition().setLabel(new Locale(locale), value);
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
// TODO: SWITCH TO  INTERFACES
// Input contains 1 or more statements
statements returns [ List<ResourceSchemaImpl> list ]
    @init
    {
    	$list = new ArrayList<ResourceSchemaImpl>();
    }
	:   ^(STATEMENTS statement +) {$list.addAll(schemaList); };
	
// A statement is either a namespace or a schema
statement : 	namespace_decl 
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
				schemaList.add($schema_decl::schema);
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

}: ^(PROPERTY (s=STRING cardinal_decl (assigment +) {
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

//Definition of an assigment within a property-declaration
assigment :
					^(ASSIGMENT (key value {
					buildTypeDef($key.text, removeAll($value.text, "\""));
				}));

// Definition of an assigments' key
key returns [String s] 
@init{
	$s = $key.text;
}
	: FIELD_LABEL 
	| INT_LABEL 
	| DATATYPE 
	| RESOURCE_CONSTRAINT
	| REFERENCE_CONSTRAINT
	
	;

//Definition of a value within a property-declaration
value returns [String s] : STRING {$s = $STRING.text;};
