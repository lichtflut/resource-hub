/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
grammar ResourceSchema;

options {
 language = Java;
 output = AST;
 ASTLabelType = CommonTree;
}

@header {
    package de.lichtflut.rb.core.schema.parser;
}

@lexer::header {
    package de.lichtflut.rb.core.schema.parser;
}

@members {
    public static void main(String[] args) throws Exception {
    }
}


/*------------------------------------------------------------------
* PARSER RULES
*------------------------------------------------------------------*/

dsl: (property | resource)+;

property : PROPERTY_DEC IDENT '('! propertyDeclaration* ')'!;

propertyDeclaration :  ( typeDeclaration | regexDeclaration) EOL;

typeDeclaration :  (WS)* 'type is'  (WS)* DATATYPE  (WS)* ;

regexDeclaration : (WS)* 'regex'  (WS)* '"' IDENT '"' (WS)* ;

resource : RESOURCE_DEC IDENT '('! resourceDeclaration+ ')'!;

resourceDeclaration : cardinality (IDENT referenceDeclaration? | property);

referenceDeclaration : 'references' IDENT;

cardinality : cardinalityDeclaration ('and'! cardinalityDeclaration)*;

cardinalityDeclaration : CARDINALITY INT;


/*------------------------------------------------------------------
* LEXER RULES
*------------------------------------------------------------------*/

//Define Tokens
//ToDo: Move tokens and lexer rules to a separate file

PROPERTY_DEC : (PROPERTY_DEC_UPPER | PROPERTY_DEC_LOWER);
PROPERTY_DEC_UPPER : 'PROPERTY';
PROPERTY_DEC_LOWER : 'property';
RESOURCE_DEC : (RESOURCE_DEC_UPPER | RESOURCE_DEC_LOWER);
RESOURCE_DEC_UPPER : 'RESOURCE';
RESOURCE_DEC_LOWER : 'resource';
NUMERIC_UPPER : 'NUMERIC';
NUMERIC_LOWER : 'numeric';
TEXT_UPPER : 'TEXT';
TEXT_LOWER : 'text';
LOGICAL_UPPER : 'LOGICAL';
LOGICAL_LOWER : 'logical';
EOL 	:	 '\n';


CARDINALITY : ('has' | 'hasMin' | 'hasMax');
DATATYPE : (NUMERIC | TEXT );
NUMERIC : (NUMERIC_UPPER | NUMERIC_LOWER);
TEXT : (TEXT_UPPER | TEXT_LOWER);
LOGICAL : (LOGICAL_UPPER | LOGICAL_LOWER);
INT : ('0' .. '9')+;
IDENT : ('a' .. 'z' | 'A' .. 'Z')('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*;
STRING : '"'('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^')+'"';
WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+     { $channel = HIDDEN; } ;




