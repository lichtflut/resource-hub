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
	PROPERTY_ASSIGNMENT;
	PROPERTY;
	STATEMENTS;
	VISUALIZATION;
	VIS_ASSIGNMENT;
	QUICK_INFO;
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
						assignment +
					'}'
					-> ^(PUBLIC_CONSTRAINT $s assignment+);

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
					qInfo_string (COMMA qInfo_string)*
			'}'
			-> ^(QUICK_INFO qInfo_string+);

qInfo_string : s=PLAIN_STRING -> ^(PROPERTY $s);


// Definition of a property-declaration
property_decl : PROPERTY_DECL id=STRING cardinal_decl '{'
					( assignment | visualization )+
				'}'
				-> ^(PROPERTY $id cardinal_decl assignment+ visualization?)	;

// Definition of a cardinality-declaration
cardinal_decl : e=CARDINALITY_DECL	-> ^(CARDINALITY $e);

// Definition of an assignment within a property-declaration
assignment : k=property_key COLON v=value -> ^(PROPERTY_ASSIGNMENT $k $v);

// Definition of visualization rules for a property declaration
visualization :
    VISUALIZE '{' (vis_assignment)+ '}'
    -> ^(VISUALIZATION vis_assignment+ )
;

// Definition of a visualization assignment
vis_assignment : k=visualization_key COLON v=value -> ^(VIS_ASSIGNMENT $k $v);


// Definition of an assignments' key
property_key : FIELD_LABEL
	| INT_LABEL 
	| DATATYPE 
	| RESOURCE_CONSTRAINT
	| REFERENCE_CONSTRAINT
	| LITERAL_CONSTRAINT
	| APPLICABLE_DATATYPES
	| NAME
	;

// Definition of a visualization' key
visualization_key :
  EMBEDDED |
  FLOATING |
  STYLE
;

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

VISUALIZE : 'visualize';

EMBEDDED : 'embedded';

FLOATING : 'floating';

STYLE : 'style';

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
