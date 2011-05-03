grammar OSF;



options {
	output = AST;
}

tokens {
	STRING; NUMBER;  ARRAY;BOOLEAN; TEXT;
	COMMA = ','; TYPE; MAX; MIN; REGEX; CARDINALITY;
	TRUE; FALSE; NULL; PROPERTY; RESOURCE; DESCRIPTIONS; PROPERTY_ASSERTION; PROPERTY_DEC;
}

@header {
/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.osf;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;

import org.arastreju.sge.model.ElementaryDataType;
import java.util.HashSet;
import java.util.Set;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

}

@lexer::header {
/*
  * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
*/
package de.lichtflut.rb.core.schema.parser.impl.osf;
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

osl 	:	descriptions  -> ^(DESCRIPTIONS descriptions?) ;


descriptions 
	: (description (COMMA! description)*)?;

description 	:
	( property_dec -> ^(PROPERTY property_dec)
	| resource_dec -> ^(RESOURCE resource_dec)
	)
	; 


property_dec 
	:	PROPERTY
		string
		('{'
		(p_assertion '=' value(COMMA p_assertion '=' value)*)? 
		'}')? -> ^(string PROPERTY_ASSERTION (^(p_assertion value))*);
		
resource_dec 
	:	RESOURCE
		string
		'{'
		(property_dec (',' property_dec)*)? 
		'}' -> ^(string PROPERTY_DEC (property_dec)*);



p_assertion 
	:	TYPE -> TYPE
	|	MAX  -> MAX
	|	MIN  -> MIN
	|	REGEX -> REGEX
	|	CARDINALITY -> CARDINALITY;
	

value

	: string
	| number
	| array
	| TEXT -> TEXT
	| BOOLEAN -> BOOLEAN
	| NUMBER -> NUMBER
	| TRUE -> TRUE
	| FALSE -> FALSE
	| NULL -> NULL
	;


string 	: String
	  -> ^(STRING String)
	;

number	: Number -> ^(NUMBER Number)
	;


array	: '[' elements ']' -> ^(ARRAY elements)
	;

elements: value (COMMA! value)*
	;


// Simple, but more permissive than the RFC allows. See number above for a validity check.
Number	: '-'? Digit+ ( '.' Digit+)?;


PROPERTY:	'PROPERTY';
RESOURCE:	'RESOURCE';
TEXT 	:	'TEXT';
BOOLEAN :	'BOOLEAN';
NUMBER 	:	'NUMBER';
TRUE 	:	'TRUE';
FALSE 	:	'FALSE';
NULL 	:	'NULL';
TYPE 	:	'TYPE';
MIN 	:	'MIN';
MAX 	:	'MAX';
CARDINALITY :	'CARDINALITY';
REGEX       : 'REGEX';


String	: '"' ('a' .. 'z' | 'A' .. 'Z')('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | '#' | '/' | ':' | '.' )+ '"';

//String 	:
//	 '"' ( EscapeSequence | ~('\u0000'..'\u001f' | '\\' | '\"' ) )* '"'
//	;

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


