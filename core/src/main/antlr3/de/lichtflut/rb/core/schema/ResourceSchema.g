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
   /*
    * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
   */
    package de.lichtflut.rb.core.schema.parser.impl;
	import de.lichtflut.rb.core.schema.model.Cardinality;
	import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
	import de.lichtflut.rb.core.schema.model.ResourceSchema;
	import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
	import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
	import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
	import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
	import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
	import org.arastreju.sge.model.ElementaryDataType;
	import java.util.HashSet;
	import java.util.Set;
}

@lexer::header {
    package de.lichtflut.rb.core.schema.parser.impl;
}

@members {
	private PropertyDeclaration property = null;
	private ResourceSchema resource = null;
}


/*------------------------------------------------------------------
* PARSER RULES
*------------------------------------------------------------------*/

dsl returns [Set<ResourceSchemaType> types]
	: {$types = new HashSet<ResourceSchemaType>();}
	 (
	 property{$types.add($property.property);}
	 |
	 resource{$types.add($resource.resource);}
	 )+
	 ;

/*-------------------------------------------------------------------*/


property returns [PropertyDeclaration property]
	@init{this.property = new PropertyDeclarationImpl();}
	@after{this.property = null;}
	 :  {$property = this.property;}
	 PROPERTY_DEC IDENT {$property.setName($IDENT.text);}
	  BRACKET_OPEN (propertyDeclaration)* BRACKET_CLOSED;

/*-------------------------------------------------------------------*/

propertyDeclaration returns [PropertyDeclaration property] 
	: {$property = this.property;}
	(
	typeDeclaration
	|
	regexDeclaration
	)
	;

/*-------------------------------------------------------------------*/


typeDeclaration :  TYPE_DEC
				   '"'?
				   datatype {this.property.setElementaryDataType($datatype.type);}
				   '"'?
				   ;

/*-------------------------------------------------------------------*/


regexDeclaration : 	REGEX_DEC
					'"' 
					IDENT {this.property.addConstraint(ConstraintFactory.buildConstraint($IDENT.text));}
					'"' 
					;

/*-------------------------------------------------------------------*/

resource returns [ResourceSchema resource]
	@after{this.resource = null;}
	:
	RESOURCE_DEC
	IDENT {this.resource = new ResourceSchemaImpl($IDENT.getText()); $resource = this.resource;} 
	BRACKET_OPEN resourceDeclaration BRACKET_CLOSED;

/*-------------------------------------------------------------------*/

resourceDeclaration : (cardinality{Cardinality cardinality = $cardinality.cardinality;}
					  IDENT
					  {this.resource.addPropertyAssertion(new PropertyAssertionImpl($IDENT.getText(),cardinality));}
					  )*;

/*-------------------------------------------------------------------*/

referenceDeclaration : 'references' IDENT;

/*-------------------------------------------------------------------*/

cardinality returns [Cardinality cardinality]
					: {RBEvaluator eval = null;}
					c1=cardinalityDeclaration{eval = $c1.evaluator;}
					('AND'!  cn=cardinalityDeclaration {eval.evaluate($cn.evaluator.getResult());})*
					{$cardinality = eval.getResult();}
					;

/*-------------------------------------------------------------------*/

cardinalityDeclaration returns [RBEvaluator<Cardinality> evaluator]:
CARDINALITY  INT{$evaluator = new RBCardinalityEvaluator($CARDINALITY,Integer.parseInt($INT.getText()));};

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

TYPE_DEC : ('TYPE IS'|'TYPE:' |'TYPE'|'TYPE IS:');
REGEX_DEC : ('LIKE' | 'REGEX' | 'LOOKS LIKE')DELIM?;
DELIM : (':');
PROPERTY_DEC : ('PROPERTY');
RESOURCE_DEC : ('RESOURCE');
NUMERIC : ('NUMERIC');
TEXT : ('TEXT');
LOGICAL : ('LOGICAL');
BRACKET_OPEN : '(';
BRACKET_CLOSED : ')';
CARDINALITY : ('HAS' | 'HAS_MIN' | 'HAS_MAX' | 'HASMIN' | 'HASMAX')DELIM?;

INT : ('0' .. '9')+;
IDENT :  ('a' .. 'z' | 'A' .. 'Z')('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*;
STRING : '"'('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^')+'"';
WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+     { skip();  $channel = HIDDEN; } ;




