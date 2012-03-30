grammar RSF;

options {
  output = AST;
  ASTLabelType = CommonTree;
}

tokens {
	NAMESPACE;
	SCHEMA;
	LABEL;
	CARDINALITY;
	ASSIGMENT;
	PROPERTY;
	STATEMENTS;
}

@header{
/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.parser.impl.rsf;

}
@lexer::header{
/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.parser.impl.rsf;
}

// Input contains 1 or more statements
statements : statement + -> ^(STATEMENTS statement+);

// A statement is either a namespace or a schema
statement : 	namespace_decl
			| 	schema_decl 
			;

// Definition of a namespace
namespace_decl: NS e=STRING PREFIX f=STRING -> ^(NAMESPACE $e $f) ;

// Definition of a schema
schema_decl : SCHEMA_FOR s=STRING '{'
					decl+
			'}'
			-> ^(SCHEMA $s decl+);

//Definition of a declaration within a schema
decl : 		label_decl
		|	property_decl 
		;

//Definition of a label-declaration
label_decl: LABEL_RULE COLON e=STRING -> ^(LABEL $e);

// Definition of a property-declaration
property_decl : PROPERTY_DECL id=STRING cardinal_decl '{'
					assigment+
				'}'
				-> ^(PROPERTY $id cardinal_decl assigment+)	;

// Definition of a cardinality-declaration
cardinal_decl : e=CARDINALITY_DECL	-> ^(CARDINALITY $e);

//Definition of an assigment within a preoperty-declaration
assigment : k=key COLON v=value -> ^(ASSIGMENT $k $v);

// Definition of an assigments' key
key : FIELD_LABEL
	| INT_LABEL 
	| TYPE_DEF 
	| RESOURCE_CONSTRAINT 
	;

value : STRING ;

NS : 'namespace';

SCHEMA_FOR : 'schema for';

LABEL_RULE : 'label-rule';

PROPERTY_DECL : 'property';

FIELD_LABEL : 'field-label';

TYPE_DEF:'type-definition';

RESOURCE_CONSTRAINT:'resource-constraint';

CONSTRAINT:'literal-constraint';
	
PREFIX : 'prefix';

COLON : ':';

INT_LABEL : 'field-label[' (('a' .. 'z' | 'A' .. 'Z')+ )']'*;

CARDINALITY_DECL  : '['('a' .. 'z' | 'A' .. 'Z' | '0' .. '9')+'..'('a' .. 'z' | 'A' .. 'Z' | '0' .. '9')+']';

STRING 	: '"' ('a' .. 'z' | 'A' .. 'Z' | '0' .. '9')('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ' ' | '_' | '-' | '#' | '/' | ':' | '.' )+ '"';

WS: (' '|'\n'|'\r'|'\t')+ {$channel=HIDDEN;} ;