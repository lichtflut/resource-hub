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
	ACTION_PROPERTY;
	COLUMN_DECL;
	COLUMN_PROPERTY;
	QUERY_BY_TYPE_DECL;
	QUERY_BY_VALUE_DECL;
	QUERY_BY_REF_DECL;
	QUERY_DECL;
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
					widget_decl *
				'}'
				-> ^(PORT_DECL widget_decl*)	;

// Definition of a widget declaration
widget_decl : WIDGET '{'
                    ( action_decl | selection_decl | widget_property | column_decl )+
                '}'
                -> ^(WIDGET_DECL widget_property* action_decl? selection_decl? column_decl*)	;

// Definition of widget_properties
widget_property : k=widget_property_key COLON v=value -> ^(WIDGET_PROPERTY $k $v);

// Definition of an widget's properties' keys
widget_property_key :
      TITLE
	| IMPLEMENTING_CLASS
	| DISPLAY
	;

// Definition of a widget's action declaration
action_decl : ACTION '{'
                    action_property +
                '}'
                -> ^(ACTION_DECL action_property+ );

// Definition of action properties
action_property : k=action_property_key COLON v=value -> ^(ACTION_PROPERTY $k $v);

// Definition of an action's properties' keys
action_property_key :
      LABEL
    | INT_LABEL
	| CREATE
;

// Definition of a widget's column declaration
column_decl : COLUMN '{'
                    column_property +
                '}'
                -> ^(COLUMN_DECL column_property+ );

// Definition of column properties
column_property : k=column_property_key COLON v=value -> ^(COLUMN_PROPERTY $k $v);

// Definition of a column's properties' keys
column_property_key :
      LABEL
    | PROPERTY
;



// Definition of a widget's selection declaration
selection_decl :    SELECTION '{' QUERY COLON query_string=STRING '}' -> ^(QUERY_DECL $query_string) |
                    SELECTION '{' QUERY_BY_TYPE COLON type=STRING '}' -> ^(QUERY_BY_TYPE $type) |
                    SELECTION '{' QUERY_BY_VALUE COLON val=STRING '}' -> ^(QUERY_BY_VALUE $val) |
                    SELECTION '{' QUERY_BY_REF COLON ref=STRING '}' -> ^(QUERY_BY_REF $ref)
;

// TOKENS

value : STRING ;

NS : 'namespace';

PREFIX : 'prefix';

NAME : 'name';

PERSPECTIVE: 'perspective';

PORT: 'port';

WIDGET: 'widget';

SELECTION: 'selection';

ACTION : 'action';

COLUMN : 'column';

TITLE : 'title';

LABEL : 'label';

INT_LABEL : 'label[' (('a' .. 'z' | 'A' .. 'Z')+ )']'*;

PROPERTY : 'property';

DISPLAY : 'display';

QUERY : 'query';

QUERY_BY_TYPE : 'by-type';

QUERY_BY_VALUE : 'by-value';

QUERY_BY_REF : 'by-reference';

IMPLEMENTING_CLASS : 'implementing-class';

CREATE : 'create';

COLON : ':';

COMMA : ',';

STRING 	: '"' .* '"';

SQ_STRING 	: '\'' .* '\'';

WS: (' '|'\n'|'\r'|'\t')+ {$channel=HIDDEN;} ;

fragment ALPHANUMERIC
    :    (DIGIT | LETTER)
    ;

fragment DIGIT
    :    '0'..'9'
    ;

fragment LETTER
    :    ('a'..'z' | 'A'..'Z')
    ;
