grammar VSpec;

options {
  output = AST;
  ASTLabelType = CommonTree;
}

tokens {
    STATEMENTS;
	NAMESPACE;
	PERSPECTIVE_DECL;
	PERSPECTIVE_TITLE_DECL;
    PORT_DECL;
	WIDGET_DECL;
	WIDGET_PROPERTY;
	ACTION_DECL;
	QUERY_DECL;
	QUERY_BY_TYPE_DECL;
	QUERY_BY_VALUE_DECL;
	QUERY_BY_REF_DECL;
}

@header{
/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.viewspec.parser;

}
@lexer::header{
/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.viewspec.parser;
}

// Input contains 1 or more statements
statements : statement + -> ^(STATEMENTS statement+);

// A statement is either a namespace or a perspective
statement : 	namespace_decl
			|	perspective_decl
			;

// Definition of a namespace
namespace_decl: NS e=STRING PREFIX f=STRING -> ^(NAMESPACE $e $f) ;

// Definition of a perspective
perspective_decl : PERSPECTIVE s=STRING '{'
					perspective_title_decl?
					port_decl*
			'}'
			-> ^(PERSPECTIVE $s perspective_title_decl? port_decl*);

// Definition of a title declaration
perspective_title_decl: TITLE COLON t=STRING -> ^(PERSPECTIVE_TITLE_DECL $t);

// Definition of a port declaration
port_decl : PORT '{'
					widget_decl*
				'}'
				-> ^(PORT_DECL widget_decl*)	;

// Definition of a widget declaration
widget_decl : WIDGET '{'
                    ( action_decl | selection_decl | widget_property )+
                '}'
                -> ^(WIDGET_DECL widget_property* action_decl? selection_decl? )	;

// Definition of widget_properties
widget_property : k=widget_property_key COLON v=value -> ^(WIDGET_PROPERTY $k $v);

// Definition of an widget's properties' keys
widget_property_key :
      TITLE
	| IMPLEMENTING_CLASS
	| DISPLAY
	;

// Definition of a widget's action declaration
action_decl : ACTION s=STRING '{'
                '}'
                -> ^(ACTION_DECL $s);

// Definition of a widget's selection declaration
selection_decl :    SELECTION '{' QUERY COLON s=STRING '}' -> ^(QUERY_DECL $s) |
                    SELECTION '{' QUERY_BY_TYPE COLON type=STRING '}' -> ^(QUERY_BY_TYPE $type) |
                    SELECTION '{' QUERY_BY_VALUE COLON val=STRING '}' -> ^(QUERY_BY_VALUE $val) |
                    SELECTION '{' QUERY_BY_REF COLON ref=STRING '}' -> ^(QUERY_BY_REF $ref)
;

value : STRING ;

NS : 'namespace';

PREFIX : 'prefix';

NAME : 'name';

PERSPECTIVE: 'perspective';

PORT: 'port';

WIDGET: 'widget';

SELECTION: 'selection';

ACTION : 'action';

TITLE : 'title';

DISPLAY : 'display';

QUERY : 'query';

QUERY_BY_TYPE : 'by-type';

QUERY_BY_VALUE : 'by-value';

QUERY_BY_REF : 'by-reference';

IMPLEMENTING_CLASS : 'implementing-class';

COLON : ':';

COMMA : ',';

INT_LABEL : 'field-label[' (('a' .. 'z' | 'A' .. 'Z')+ )']'*;

CARDINALITY_DECL  : '['('a' .. 'z' | 'A' .. 'Z' | '0' .. '9')+'..'('a' .. 'z' | 'A' .. 'Z' | '0' .. '9')+']';

STRING 	: '"' .* '"';

// TODO: refactor - remove quotations from STRING
PLAIN_STRING : ('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '/' | ':' )+;

WS: (' '|'\n'|'\r'|'\t')+ {$channel=HIDDEN;} ;
