tree grammar RSFTree_Old;

options {
	output = AST;
	//tokenVocab=RSF; // reuse token types
	ASTLabelType=CommonTree; // $label will have type CommonTree
}


@header {
/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;

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

		
	public String getErrorMessage(RecognitionException e, String[] tokenNames) {
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
		} else {
			msg = super.getErrorMessage(e, this.tokenNames);
		}
		return stack+" "+msg+" context=..."+inputContext+"...";
	}
		
	public String getTokenErrorDisplay(Token t) {
		return t.toString();
	}

}


declarations returns [List list] 
	@init{ $list = new ArrayList(); }
	: ^(DECLARATION (d=decl {$list.add(d);})*);


decl returns [Object obj]	:
	(^(PROPERTY p=property_decl{$obj=p.pDec;})
	|^(SCHEMA r=schema_decl{$obj=r.rSchema;})
	)
	; 

	schema_decl returns [ResourceSchema rSchema]
		: ^(STRING s=string {$rSchema = new ResourceSchemaImpl(new SimpleResourceID(s.result));
		this.resourceSchema = (ResourceSchemaImpl) $rSchema;} PROPERTY_DECL)
		;

property_decl returns [PropertyDeclaration pDec]
	@init{ $pDec = new PropertyDeclarationImpl();}
	: ^(STRING s=string {$pDec.setPropertyDescriptor(new SimpleResourceID(s.result));})
	;

value returns [Object obj]
	: ^(STRING s=string {$obj = s.result;})
	;

string returns [String result]
	: String^
	  { $result = extractString($String); }
	;

number	returns [Integer result]
	: ^(NUMBER Number)
	  { $result = extractNumber($Number); }
	;



