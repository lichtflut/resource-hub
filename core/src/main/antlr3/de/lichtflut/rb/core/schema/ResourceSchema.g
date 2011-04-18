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
    package de.lichtflut.rb.core.schema.parser.impl;
	import org.arastreju.sge.model.ElementaryDataType;
	import de.lichtflut.rb.core.schema.model.*;
	import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
	import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
}

@lexer::header {
    package de.lichtflut.rb.core.schema.parser;
}

@members {
	private PropertyDeclaration property = null;
}


/*------------------------------------------------------------------
* PARSER RULES
*------------------------------------------------------------------*/

dsl returns [Set<ResourceSchemaType> types]
	: {$types = new HashSet<ResourceSchemaType>();}
	 (
	 property{$types.add($property.property);}
	 |
	 resource{$types.add(null);}
	 )+
	 ;

/*-------------------------------------------------------------------*/


property returns [PropertyDeclaration property]
	@init{this.property = new PropertyDeclarationImpl();}
	@after{this.property = null;}
	 :  {$property = this.property;}
	 PROPERTY_DEC IDENT {$property.setName($IDENT.text);}
	  '('! (propertyDeclaration)* ')'!;

/*-------------------------------------------------------------------*/

propertyDeclaration returns [PropertyDeclaration property] 
	:
	(
	typeDeclaration
	|
	regexDeclaration
	)
	EOL;

/*-------------------------------------------------------------------*/


typeDeclaration :  (WS)*
				   'type is'
				   (WS)*
				   datatype {this.property.setElementaryDataType($datatype.type);}
				   (WS)* ;

/*-------------------------------------------------------------------*/


regexDeclaration : 	(WS)* 
					'regex'
					(WS)*
					'"'
					IDENT {this.property.addConstraint(ConstraintFactory.buildConstraint($IDENT.text));}
					'"'
					(WS)*
					;

/*-------------------------------------------------------------------*/

resource : RESOURCE_DEC IDENT '('! resourceDeclaration+ ')'!;

/*-------------------------------------------------------------------*/

resourceDeclaration : cardinality (IDENT referenceDeclaration? | property) EOL;

/*-------------------------------------------------------------------*/

referenceDeclaration : 'references' IDENT;

/*-------------------------------------------------------------------*/

cardinality : cardinalityDeclaration ('and'! cardinalityDeclaration)*;

/*-------------------------------------------------------------------*/

cardinalityDeclaration : CARDINALITY INT;

/*-------------------------------------------------------------------*/

datatype returns [ElementaryDataType type]
	:
	 (
	 NUMERIC {$type = ElementaryDataType.INTEGER;}
	 |
	 TEXT {$type = ElementaryDataType.STRING;}
	 |
	 LOGICAL {$type = ElementaryDataType.BOOLEAN;}
	 );

/*------------------------------------------------------------------
* LEXER RULES
*------------------------------------------------------------------*/

//Define Tokens
//ToDo: Move tokens and lexer rules to a separate file

PROPERTY_DEC : ('PROPERTY' | 'property');
RESOURCE_DEC : ('RESOURCE' | 'resource');
NUMERIC : ('NUMERIC' | 'numeric');
TEXT : ('TEXT' | 'text');
LOGICAL : ('LOGICAL' | 'logical');
EOL 	:	 '\n';
CARDINALITY : ('has' | 'hasMin' | 'hasMax');

INT : ('0' .. '9')+;
IDENT : ('a' .. 'z' | 'A' .. 'Z')('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*;
STRING : '"'('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^')+'"';
WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+     { $channel = HIDDEN; } ;




