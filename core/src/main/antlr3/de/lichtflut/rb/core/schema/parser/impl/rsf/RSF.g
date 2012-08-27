grammar RSF;

options {
  output = AST;
  ASTLabelType = CommonTree;
}

tokens {
	NAMESPACE;
	PUBLIC_CONSTRAINT;
	SCHEMA;
	LABEL;
	CARDINALITY;
	ASSIGMENT;
	PROPERTY;
	STATEMENTS;
	QUICK_INFO;
	TEXT;
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
			|	public_constraint
			| 	schema_decl 
			;

// Definition of a namespace
namespace_decl: NS e=STRING PREFIX f=STRING -> ^(NAMESPACE $e $f) ;

//Definition of a public constraint
public_constraint : CONSTRAINT_FOR s=STRING '{'
						assigment +
					'}'
					-> ^(PUBLIC_CONSTRAINT $s assigment+);

// Definition of a schema
schema_decl : SCHEMA_FOR s=STRING '{'
					decl+
			'}'
			-> ^(SCHEMA $s decl+);

//Definition of a declaration within a schema
decl : 		label_decl
		|	quick_info
		|	property_decl 
		;

//Definition of a label-declaration
label_decl: LABEL_RULE COLON e=STRING -> ^(LABEL $e);

// Definition of schema-quick-info
quick_info: QUICK_INFO_DECL '{'
				plain_string COMMA *
				plain_string
			'}'
			-> ^(QUICK_INFO plain_string+);

// Definition of a property-declaration
property_decl : PROPERTY_DECL id=STRING cardinal_decl '{'
					assigment+
				'}'
				-> ^(PROPERTY $id cardinal_decl assigment+)	;

// Definition of a cardinality-declaration
cardinal_decl : e=CARDINALITY_DECL	-> ^(CARDINALITY $e);

//Definition of an assigment within a property-declaration
assigment : k=key COLON v=value -> ^(ASSIGMENT $k $v);

// Definition of an assigments' key
key : FIELD_LABEL
	| INT_LABEL 
	| DATATYPE 
	| RESOURCE_CONSTRAINT
	| REFERENCE_CONSTRAINT
	| LITERAL_CONSTRAINT
	| APPLICABLE_DATATYPES
	| NAME
	;

plain_string : t=PLAIN_STRING -> ^(TEXT $t);

value : STRING ;

NS : 'namespace';

CONSTRAINT_FOR : 'constraint definition for';

SCHEMA_FOR : 'schema for';

LABEL_RULE : 'label-rule';

QUICK_INFO_DECL : 'quick-info';

PROPERTY_DECL : 'property';

FIELD_LABEL : 'field-label';

DATATYPE :'datatype';

RESOURCE_CONSTRAINT : 'resource-constraint';

REFERENCE_CONSTRAINT : 'reference-constraint';

LITERAL_CONSTRAINT : 'literal-constraint';

APPLICABLE_DATATYPES : 'applicable-datatypes';

PREFIX : 'prefix';

NAME : 'name';

COLON : ':';

COMMA : ',';

INT_LABEL : 'field-label[' (('a' .. 'z' | 'A' .. 'Z')+ )']'*;

CARDINALITY_DECL  : '['('a' .. 'z' | 'A' .. 'Z' | '0' .. '9')+'..'('a' .. 'z' | 'A' .. 'Z' | '0' .. '9')+']';

STRING 	: '"' .* '"';

// TODO: refactor - remove quotations from STRING
PLAIN_STRING : ('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '/' | ':' )+;

WS: (' '|'\n'|'\r'|'\t')+ {$channel=HIDDEN;} ;
