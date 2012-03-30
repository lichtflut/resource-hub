grammar RSF_Old;



options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
}

tokens {
	STRING; INTEGER; DECIMAL; BOOLEAN; TEXT; DATE;
	NAMESPACE; PREFIX;
	SCHEMA; FOR; LABEL_RULE; PROPERTY; FIELD_LABEL; TYPE_DEFINITION; CONSTRAINT;
}

@header {
/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import de.lichtflut.rb.core.schema.model.*;
import de.lichtflut.rb.core.schema.model.impl.*;

import de.lichtflut.rb.core.schema.parser.RSErrorReporter;

}

@lexer::header {
/*
  * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
*/
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import de.lichtflut.rb.core.schema.parser.RSErrorReporter;

}

@lexer::members {
 private RSErrorReporter errorReporter = null;
 public void setErrorReporter(RSErrorReporter errorReporter) {
     this.errorReporter = errorReporter;
 }
 public void emitErrorMessage(String msg) {
     errorReporter.reportError(msg);
 }
}


@members {

	private RSErrorReporter errorReporter = null;
    public void setErrorReporter(RSErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }
    public void emitErrorMessage(String msg) {
        errorReporter.reportError(msg);
    }

}

declarations 	:	decl+ EOF;


decl 	:
	namespace_decl -> ^(NAMESPACE namespace_decl)
	| schema_decl -> ^(SCHEMA schema_decl)
	; 
	
namespace_decl 
			:	NAMESPACE string PREFIX string;

schema_decl 
		:	SCHEMA FOR string
			'{'
			property_decl*
			'}';

property_decl 
	:	PROPERTY
		string
		cardinality
		'{'
			assignment
		'}';
	
	
cardinality

	: '[' string ']';	
	
assignment
	: (key ':' value)* ;

key 
	:	FIELD_LABEL -> FIELD_LABEL
	|	TYPE_DEFINITION  -> TYPE_DEFINITION
	|	CONSTRAINT -> CONSTRAINT;

value

	: string;


string 	: String
	  -> ^(STRING String)
	;

number	: Number -> ^(NUMBER Number)
	;

// Simple, but more permissive than the RFC allows. See number above for a validity check.
Number	: '-'? Digit+ ( '.' Digit+)?;


SCHEMA  :   'schema';
FOR     :   'for';

PROPERTY:	'property';
TYPE_DEFINITION : 'type-definition';
CONSTRAINT : 'constraint';
FIELD_LABEL: 'field-label'; 
LABEL_RULE : 'label-rule';
NAMESPACE : 'namespace';
PREFIX  :   'prefix';

TEXT 	:	'text';
DATE 	:   'date';
BOOLEAN :	'boolean';
NUMBER 	:	'number';




String	: '"' ('a' .. 'z' | 'A' .. 'Z' | '0' .. '9')('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ' ' | '_' | '-' | '#' | '/' | ':' | '.' )+ '"';


WS: (' '|'\n'|'\r'|'\t')+ {$channel=HIDDEN;} ; // ignore whitespace

fragment EscapeSequence
    	:   '\\' (UnicodeEscape |'b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    	;

fragment UnicodeEscape
	: 'u' HexDigit HexDigit HexDigit HexDigit
	;

fragment HexDigit
	: '0'..'9' | 'A'..'F' | 'a'..'f'
	;

fragment Digit
	: '0'..'9'
	;


