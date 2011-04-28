/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
grammar SimpleRSF;

options {
 language = Java;
 output = AST;
 ASTLabelType = CommonTree;
}

@header {
   /*
    * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
   */
    package de.lichtflut.rb.core.schema.parser.impl;

}

@lexer::header {
    package de.lichtflut.rb.core.schema.parser.impl;
}

@members {
	private PropertyDeclaration property = null;
	private ResourceSchema resource = null;
	private RSErrorReporter errorReporter = null;
    public void setErrorReporter(RSErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }
    public void emitErrorMessage(String msg) {
        errorReporter.reportError(msg, ErrorLevel.GRAMMAR);
    }
}


/*------------------------------------------------------------------
* PARSER RULES
*------------------------------------------------------------------*/

start : (property)* (resource)*;

property : 
'p' IDENT '{' pexpression ( ',' pexpression ) '}';

resource :
'r' IDENT '{' rexpression ( ',' rexpression) '}';

pexpression :
('type' = TEXT | LOGICAL | NUMERIC);

rexpression :
'has' IDENT;


/*------------------------------------------------------------------
* LEXER RULES
*------------------------------------------------------------------*/

NUMERIC : ('NUMERIC');
TEXT : ('TEXT');
LOGICAL : ('LOGICAL');

INT : ('0' .. '9')+;
IDENT :  ('a' .. 'z' | 'A' .. 'Z')('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*;
STRING : '"'('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^')+'"';
WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+     { skip();  $channel = HIDDEN; } ;




