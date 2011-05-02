tree grammar OSFTree;

options {
	output = AST;
	tokenVocab=OSF; // reuse token types
	ASTLabelType=CommonTree; // $label will have type CommonTree
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

// Optional step: Disable automatic error recovery
@members {
	protected void mismatch(IntStream input, int ttype, BitSet follow)throws RecognitionException {
		throw new MismatchedTokenException(ttype, input);
	}
	
	public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException
	{
		throw e;
	}

	private PropertyDeclaration property = null;
	private ResourceSchema resource = null;
	private RSErrorReporter errorReporter = null;
    	
    	public void setErrorReporter(RSErrorReporter errorReporter) {
        	this.errorReporter = errorReporter;
    	}
    
    	public void emitErrorMessage(String msg) {
        	errorReporter.reportError(msg);
    	}

	private Object extractNumber(CommonTree numberToken) {
        	String numberBody = numberToken.getText();
        	return new Integer(numberBody);
    	}

    	private String extractString(CommonTree token) {
        	StringBuffer sb = new StringBuffer(token.getText());
        	return sb.toString();
    	}



}

@rulecatch {
	catch (RecognitionException e) {
		throw e;
	}
} 

osl returns [List<ResourceSchemaType> list] 
	@init{ list = new ArrayList<ResourceSchemaType>(); }
	: ^(DESCRIPTIONS (d=description {$list.add(d);})*);


description returns [ResourceSchemaType type]	:
	(^(PROPERTY p=property_dec{$type=p;})
	|^(RESOURCE r=resource_dec{$type=r;})
	)
	; 


property_dec 
	:	PROPERTY
		String
		('{'!
		(p_assertion '='! value(COMMA! p_assertion '='! value)*)?
		'}'!)?;
		
resource_dec 
	:	RESOURCE
		String
		'{'!
		(property_dec (',' property_dec)*)?
		'}'!;



p_assertion 
	:	TYPE
	|	MAX
	|	MIN
	|	REGEX
	|	CARDINALITY;
	

value returns [Object obj]

	: s=string {$obj = s;}
	| n=number {$obj = n;}
	| a=array  {$obj = a;}
	| TEXT     {$obj = ElementaryDataType.STRING}
	| BOOLEAN  {$obj = ElementaryDataType.BOOLEAN}
	| NUMBER   {$obj = ElementaryDataType.INTEGER}
	| TRUE     {$obj = Boolean.TRUE;}
	| FALSE	   {$obj = Boolean.FALSE;}
	| NULL 	   {$obj = null;}
	;

string returns [String result]
	: ^(STRING String)
	  { $result = extractString($String); }
	;

number	returns [Object result]
	: ^(NUMBER Number)
	  { $result = extractNumber($Number); }
	;

array	returns [List list]
@init{ list = new ArrayList(); }
	: ^(ARRAY (v=value {$list.add(v); })+ )
	;



