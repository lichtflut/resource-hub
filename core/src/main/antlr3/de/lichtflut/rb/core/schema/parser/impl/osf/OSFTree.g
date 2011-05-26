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
package de.lichtflut.rb.core.schema.parser.impl.osf;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.ElementaryDataType;



}

// Optional step: Disable automatic error recovery
@members {
	private ResourceSchemaImpl resourceSchema = null;
	private RSErrorReporter errorReporter = null;
    	
    	public void setErrorReporter(RSErrorReporter errorReporter) {
        	this.errorReporter = errorReporter;
    	}
    
    	public void emitErrorMessage(String msg) {
        	errorReporter.reportError(msg);
    	}

	private Integer extractNumber(CommonTree numberToken) {
        	String numberBody = numberToken.getText();
        	return new Integer(numberBody);
    	}

    	private String extractString(CommonTree token) {
        	String extract = token.getText();
        	extract = extract.replaceAll("\"","");
        	return extract;
    	}

		
		public String getErrorMessage(RecognitionException e, String[] tokenNames)
		{
			List stack = getRuleInvocationStack(e, this.getClass().getName());
			String msg = null;
			String inputContext =
			input.LT(-3) == null ? "" : ((Tree)input.LT(-3)).getText()+" "+
			input.LT(-2) == null ? "" : ((Tree)input.LT(-2)).getText()+" "+
			input.LT(-1) == null ? "" : ((Tree)input.LT(-1)).getText()+" >>>"+
			((Tree)input.LT(1)).getText()+"<<< "+
			((Tree)input.LT(2)).getText()+" "+
			((Tree)input.LT(3)).getText();
			if ( e instanceof NoViableAltException ) {
				NoViableAltException nvae = (NoViableAltException)e;
				msg = " no viable alt; token="+e.token+
				" (decision="+nvae.decisionNumber+
				" state "+nvae.stateNumber+")"+
				" decision=<<"+nvae.grammarDecisionDescription+">>";
				}
				else {
					msg = super.getErrorMessage(e, this.tokenNames);
					}
					return stack+" "+msg+" context=..."+inputContext+"...";
		}
		
		public String getTokenErrorDisplay(Token t) {
			return t.toString();
		}



}


osl returns [List<ResourceSchemaType> list] 
	@init{ $list = new ArrayList<ResourceSchemaType>(); }
	: ^(DESCRIPTIONS (d=description {$list.add(d.type);})*);


description returns [ResourceSchemaType type]	:
	(^(PROPERTY p=property_dec{$type=p.pDec;})
	|^(RESOURCE r=resource_dec{$type=r.rSchema;})
	)
	; 


property_dec returns [PropertyDeclaration pDec]
	@init{ $pDec = new PropertyDeclarationImpl();}
	: ^(STRING s=string {$pDec.setIdentifier(s.result);} PROPERTY_ASSERTION
		(^(p=p_assertion v=value {OSFEvaluator.evaluateGlobalPropertyDec($pDec,p.type,v.obj, this);}))*)
	;
		
resource_dec returns [ResourceSchema rSchema]
	: ^(STRING s=string {$rSchema = new ResourceSchemaImpl(s.result);
	this.resourceSchema = (ResourceSchemaImpl) $rSchema;} PROPERTY_DEC
	(a=assignment_dec{$rSchema.addPropertyAssertion(a.assertion);})*)
	;

assignment_dec returns [PropertyAssertionImpl assertion]
	@init{ 
	PropertyDeclarationImpl pDec = new PropertyDeclarationImpl();
	$assertion = new PropertyAssertionImpl(null,pDec);
	}
	: ^(STRING s=string {pDec.setIdentifier(s.result); 
	$assertion.setPropertyIdentifier(s.result);
	QualifiedName qName = $assertion.getQualifiedPropertyIdentifier();
    if(!(qName.getSimpleName().startsWith("has"))){
          $assertion.setPropertyDescriptor(new SimpleResourceID(qName.getNamespace().getUri(),"has"+qName.getSimpleName()));
    }else{
    	$assertion.setPropertyDescriptor(new SimpleResourceID($assertion.getQualifiedPropertyIdentifier()));}
    }
	PROPERTY_ASSERTION (^(p=p_assertion v=value {OSFEvaluator.evaluateLocalPropertyDec($assertion,this.resourceSchema,p.type,v.obj,this);}))*);

p_assertion returns [String type] 
	:	TYPE {$type = "type";}
	|	MAX  {$type = "max_cardinality";}
	|	MIN  {$type = "min_cardinality";}
	|	REGEX  {$type = "regex";}
	|	CARDINALITY  {$type = "cardinality";}
	;
	

value returns [Object obj]

	: ^(STRING s=string {$obj = s.result;})
	| n=number {$obj = n.result;}
	| a=array  {$obj = a.list;}
	| TEXT     {$obj = ElementaryDataType.STRING;}
	| BOOLEAN  {$obj = ElementaryDataType.BOOLEAN;}
	| NUMBER   {$obj = ElementaryDataType.INTEGER;}
	| DATE 	   {$obj = ElementaryDataType.DATE;}
	| TRUE     {$obj = Boolean.TRUE;}
	| FALSE	   {$obj = Boolean.FALSE;}
	| NULL 	   {$obj = null;}
	;

string returns [String result]
	: String^
	  { $result = extractString($String); }
	;

number	returns [Integer result]
	: ^(NUMBER Number)
	  { $result = extractNumber($Number); }
	;

array	returns [List list]
@init{ $list = new ArrayList(); }
	: ^(ARRAY (v=value {$list.add(v.obj); })+ )
	;



