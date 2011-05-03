// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g 2011-05-03 17:53:39

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
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


public class OSFTree extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "STRING", "NUMBER", "ARRAY", "BOOLEAN", "TEXT", "COMMA", "TYPE", "MAX", "MIN", "REGEX", "CARDINALITY", "TRUE", "FALSE", "NULL", "PROPERTY", "RESOURCE", "DESCRIPTIONS", "PROPERTY_ASSERTION", "PROPERTY_DEC", "String", "Number", "Digit", "WS", "UnicodeEscape", "EscapeSequence", "HexDigit", "'{'", "'='", "'}'", "'['", "']'"
    };
    public static final int PROPERTY_DEC=22;
    public static final int NULL=17;
    public static final int NUMBER=5;
    public static final int MAX=11;
    public static final int PROPERTY_ASSERTION=21;
    public static final int REGEX=13;
    public static final int MIN=12;
    public static final int DESCRIPTIONS=20;
    public static final int TEXT=8;
    public static final int Digit=25;
    public static final int EOF=-1;
    public static final int HexDigit=29;
    public static final int RESOURCE=19;
    public static final int TRUE=15;
    public static final int TYPE=10;
    public static final int CARDINALITY=14;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int BOOLEAN=7;
    public static final int T__33=33;
    public static final int WS=26;
    public static final int T__34=34;
    public static final int Number=24;
    public static final int PROPERTY=18;
    public static final int COMMA=9;
    public static final int UnicodeEscape=27;
    public static final int String=23;
    public static final int FALSE=16;
    public static final int EscapeSequence=28;
    public static final int ARRAY=6;
    public static final int STRING=4;

    // delegates
    // delegators


        public OSFTree(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public OSFTree(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return OSFTree.tokenNames; }
    public String getGrammarFileName() { return "de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g"; }



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
            	StringBuffer sb = new StringBuffer(token.getText());
            	return sb.toString();
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





    public static class osl_return extends TreeRuleReturnScope {
        public List<ResourceSchemaType> list;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "osl"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:95:1: osl returns [List<ResourceSchemaType> list] : ^( DESCRIPTIONS (d= description )* ) ;
    public final OSFTree.osl_return osl() throws RecognitionException {
        OSFTree.osl_return retval = new OSFTree.osl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree DESCRIPTIONS1=null;
        OSFTree.description_return d = null;


        CommonTree DESCRIPTIONS1_tree=null;

         retval.list = new ArrayList<ResourceSchemaType>(); 
        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:97:2: ( ^( DESCRIPTIONS (d= description )* ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:97:4: ^( DESCRIPTIONS (d= description )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            DESCRIPTIONS1=(CommonTree)match(input,DESCRIPTIONS,FOLLOW_DESCRIPTIONS_in_osl72); 
            DESCRIPTIONS1_tree = (CommonTree)adaptor.dupNode(DESCRIPTIONS1);

            root_1 = (CommonTree)adaptor.becomeRoot(DESCRIPTIONS1_tree, root_1);



            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:97:19: (d= description )*
                loop1:
                do {
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( ((LA1_0>=PROPERTY && LA1_0<=RESOURCE)) ) {
                        alt1=1;
                    }


                    switch (alt1) {
                	case 1 :
                	    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:97:20: d= description
                	    {
                	    _last = (CommonTree)input.LT(1);
                	    pushFollow(FOLLOW_description_in_osl77);
                	    d=description();

                	    state._fsp--;

                	    adaptor.addChild(root_1, d.getTree());
                	    retval.list.add(d.type);

                	    }
                	    break;

                	default :
                	    break loop1;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "osl"

    public static class description_return extends TreeRuleReturnScope {
        public ResourceSchemaType type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "description"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:100:1: description returns [ResourceSchemaType type] : ( ^( PROPERTY p= property_dec ) | ^( RESOURCE r= resource_dec ) ) ;
    public final OSFTree.description_return description() throws RecognitionException {
        OSFTree.description_return retval = new OSFTree.description_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PROPERTY2=null;
        CommonTree RESOURCE3=null;
        OSFTree.property_dec_return p = null;

        OSFTree.resource_dec_return r = null;


        CommonTree PROPERTY2_tree=null;
        CommonTree RESOURCE3_tree=null;

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:100:47: ( ( ^( PROPERTY p= property_dec ) | ^( RESOURCE r= resource_dec ) ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:101:2: ( ^( PROPERTY p= property_dec ) | ^( RESOURCE r= resource_dec ) )
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:101:2: ( ^( PROPERTY p= property_dec ) | ^( RESOURCE r= resource_dec ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==PROPERTY) ) {
                alt2=1;
            }
            else if ( (LA2_0==RESOURCE) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:101:3: ^( PROPERTY p= property_dec )
                    {
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    PROPERTY2=(CommonTree)match(input,PROPERTY,FOLLOW_PROPERTY_in_description98); 
                    PROPERTY2_tree = (CommonTree)adaptor.dupNode(PROPERTY2);

                    root_1 = (CommonTree)adaptor.becomeRoot(PROPERTY2_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_property_dec_in_description102);
                    p=property_dec();

                    state._fsp--;

                    adaptor.addChild(root_1, p.getTree());
                    retval.type =p.pDec;

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:102:3: ^( RESOURCE r= resource_dec )
                    {
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    RESOURCE3=(CommonTree)match(input,RESOURCE,FOLLOW_RESOURCE_in_description109); 
                    RESOURCE3_tree = (CommonTree)adaptor.dupNode(RESOURCE3);

                    root_1 = (CommonTree)adaptor.becomeRoot(RESOURCE3_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_resource_dec_in_description113);
                    r=resource_dec();

                    state._fsp--;

                    adaptor.addChild(root_1, r.getTree());
                    retval.type =r.rSchema;

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;

            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "description"

    public static class property_dec_return extends TreeRuleReturnScope {
        public PropertyDeclaration pDec;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "property_dec"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:107:1: property_dec returns [PropertyDeclaration pDec] : ^( STRING s= string PROPERTY_ASSERTION ( ^(p= p_assertion v= value ) )* ) ;
    public final OSFTree.property_dec_return property_dec() throws RecognitionException {
        OSFTree.property_dec_return retval = new OSFTree.property_dec_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree STRING4=null;
        CommonTree PROPERTY_ASSERTION5=null;
        OSFTree.string_return s = null;

        OSFTree.p_assertion_return p = null;

        OSFTree.value_return v = null;


        CommonTree STRING4_tree=null;
        CommonTree PROPERTY_ASSERTION5_tree=null;

         retval.pDec = new PropertyDeclarationImpl();
        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:109:2: ( ^( STRING s= string PROPERTY_ASSERTION ( ^(p= p_assertion v= value ) )* ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:109:4: ^( STRING s= string PROPERTY_ASSERTION ( ^(p= p_assertion v= value ) )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            STRING4=(CommonTree)match(input,STRING,FOLLOW_STRING_in_property_dec141); 
            STRING4_tree = (CommonTree)adaptor.dupNode(STRING4);

            root_1 = (CommonTree)adaptor.becomeRoot(STRING4_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_string_in_property_dec145);
            s=string();

            state._fsp--;

            adaptor.addChild(root_1, s.getTree());
            retval.pDec.setIdentifier(s.result);
            _last = (CommonTree)input.LT(1);
            PROPERTY_ASSERTION5=(CommonTree)match(input,PROPERTY_ASSERTION,FOLLOW_PROPERTY_ASSERTION_in_property_dec149); 
            PROPERTY_ASSERTION5_tree = (CommonTree)adaptor.dupNode(PROPERTY_ASSERTION5);

            adaptor.addChild(root_1, PROPERTY_ASSERTION5_tree);

            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:110:3: ( ^(p= p_assertion v= value ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=TYPE && LA3_0<=CARDINALITY)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:110:4: ^(p= p_assertion v= value )
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    {
            	    CommonTree _save_last_2 = _last;
            	    CommonTree _first_2 = null;
            	    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_p_assertion_in_property_dec157);
            	    p=p_assertion();

            	    state._fsp--;

            	    root_2 = (CommonTree)adaptor.becomeRoot(p.getTree(), root_2);


            	    match(input, Token.DOWN, null); 
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_value_in_property_dec161);
            	    v=value();

            	    state._fsp--;

            	    adaptor.addChild(root_2, v.getTree());
            	    OSFEvaluator.evaluateGlobalPropertyDec(retval.pDec,p.type,v.obj, this);

            	    match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "property_dec"

    public static class resource_dec_return extends TreeRuleReturnScope {
        public ResourceSchema rSchema;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "resource_dec"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:113:1: resource_dec returns [ResourceSchema rSchema] : ^( STRING s= string PROPERTY_DEC (a= assignment_dec )* ) ;
    public final OSFTree.resource_dec_return resource_dec() throws RecognitionException {
        OSFTree.resource_dec_return retval = new OSFTree.resource_dec_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree STRING6=null;
        CommonTree PROPERTY_DEC7=null;
        OSFTree.string_return s = null;

        OSFTree.assignment_dec_return a = null;


        CommonTree STRING6_tree=null;
        CommonTree PROPERTY_DEC7_tree=null;

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:114:2: ( ^( STRING s= string PROPERTY_DEC (a= assignment_dec )* ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:114:4: ^( STRING s= string PROPERTY_DEC (a= assignment_dec )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            STRING6=(CommonTree)match(input,STRING,FOLLOW_STRING_in_resource_dec185); 
            STRING6_tree = (CommonTree)adaptor.dupNode(STRING6);

            root_1 = (CommonTree)adaptor.becomeRoot(STRING6_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_string_in_resource_dec189);
            s=string();

            state._fsp--;

            adaptor.addChild(root_1, s.getTree());
            retval.rSchema = new ResourceSchemaImpl(s.result);
            _last = (CommonTree)input.LT(1);
            PROPERTY_DEC7=(CommonTree)match(input,PROPERTY_DEC,FOLLOW_PROPERTY_DEC_in_resource_dec193); 
            PROPERTY_DEC7_tree = (CommonTree)adaptor.dupNode(PROPERTY_DEC7);

            adaptor.addChild(root_1, PROPERTY_DEC7_tree);

            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:115:2: (a= assignment_dec )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==STRING) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:115:3: a= assignment_dec
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_assignment_dec_in_resource_dec199);
            	    a=assignment_dec();

            	    state._fsp--;

            	    adaptor.addChild(root_1, a.getTree());
            	    retval.rSchema.addPropertyAssertion(a.assertion);

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "resource_dec"

    public static class assignment_dec_return extends TreeRuleReturnScope {
        public PropertyAssertionImpl assertion;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignment_dec"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:118:1: assignment_dec returns [PropertyAssertionImpl assertion] : ^( STRING s= string PROPERTY_ASSERTION ( ^(p= p_assertion v= value ) )* ) ;
    public final OSFTree.assignment_dec_return assignment_dec() throws RecognitionException {
        OSFTree.assignment_dec_return retval = new OSFTree.assignment_dec_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree STRING8=null;
        CommonTree PROPERTY_ASSERTION9=null;
        OSFTree.string_return s = null;

        OSFTree.p_assertion_return p = null;

        OSFTree.value_return v = null;


        CommonTree STRING8_tree=null;
        CommonTree PROPERTY_ASSERTION9_tree=null;

         
        	PropertyDeclarationImpl pDec = new PropertyDeclarationImpl();
        	retval.assertion = new PropertyAssertionImpl(null,pDec);
        	
        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:123:2: ( ^( STRING s= string PROPERTY_ASSERTION ( ^(p= p_assertion v= value ) )* ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:123:4: ^( STRING s= string PROPERTY_ASSERTION ( ^(p= p_assertion v= value ) )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            STRING8=(CommonTree)match(input,STRING,FOLLOW_STRING_in_assignment_dec224); 
            STRING8_tree = (CommonTree)adaptor.dupNode(STRING8);

            root_1 = (CommonTree)adaptor.becomeRoot(STRING8_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_string_in_assignment_dec228);
            s=string();

            state._fsp--;

            adaptor.addChild(root_1, s.getTree());
            pDec.setIdentifier(s.result);
            _last = (CommonTree)input.LT(1);
            PROPERTY_ASSERTION9=(CommonTree)match(input,PROPERTY_ASSERTION,FOLLOW_PROPERTY_ASSERTION_in_assignment_dec232); 
            PROPERTY_ASSERTION9_tree = (CommonTree)adaptor.dupNode(PROPERTY_ASSERTION9);

            adaptor.addChild(root_1, PROPERTY_ASSERTION9_tree);

            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:123:73: ( ^(p= p_assertion v= value ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>=TYPE && LA5_0<=CARDINALITY)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:123:74: ^(p= p_assertion v= value )
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    {
            	    CommonTree _save_last_2 = _last;
            	    CommonTree _first_2 = null;
            	    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_p_assertion_in_assignment_dec238);
            	    p=p_assertion();

            	    state._fsp--;

            	    root_2 = (CommonTree)adaptor.becomeRoot(p.getTree(), root_2);


            	    match(input, Token.DOWN, null); 
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_value_in_assignment_dec242);
            	    v=value();

            	    state._fsp--;

            	    adaptor.addChild(root_2, v.getTree());
            	    OSFEvaluator.evaluateLocalPropertyDec(retval.assertion,p.type,v.obj,this);

            	    match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "assignment_dec"

    public static class p_assertion_return extends TreeRuleReturnScope {
        public String type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "p_assertion"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:126:1: p_assertion returns [String type] : ( TYPE | MAX | MIN | REGEX | CARDINALITY );
    public final OSFTree.p_assertion_return p_assertion() throws RecognitionException {
        OSFTree.p_assertion_return retval = new OSFTree.p_assertion_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree TYPE10=null;
        CommonTree MAX11=null;
        CommonTree MIN12=null;
        CommonTree REGEX13=null;
        CommonTree CARDINALITY14=null;

        CommonTree TYPE10_tree=null;
        CommonTree MAX11_tree=null;
        CommonTree MIN12_tree=null;
        CommonTree REGEX13_tree=null;
        CommonTree CARDINALITY14_tree=null;

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:127:2: ( TYPE | MAX | MIN | REGEX | CARDINALITY )
            int alt6=5;
            switch ( input.LA(1) ) {
            case TYPE:
                {
                alt6=1;
                }
                break;
            case MAX:
                {
                alt6=2;
                }
                break;
            case MIN:
                {
                alt6=3;
                }
                break;
            case REGEX:
                {
                alt6=4;
                }
                break;
            case CARDINALITY:
                {
                alt6=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:127:4: TYPE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    TYPE10=(CommonTree)match(input,TYPE,FOLLOW_TYPE_in_p_assertion264); 
                    TYPE10_tree = (CommonTree)adaptor.dupNode(TYPE10);

                    adaptor.addChild(root_0, TYPE10_tree);

                    retval.type = "type";

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:128:4: MAX
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    MAX11=(CommonTree)match(input,MAX,FOLLOW_MAX_in_p_assertion271); 
                    MAX11_tree = (CommonTree)adaptor.dupNode(MAX11);

                    adaptor.addChild(root_0, MAX11_tree);

                    retval.type = "max_cardinality";

                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:129:4: MIN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    MIN12=(CommonTree)match(input,MIN,FOLLOW_MIN_in_p_assertion279); 
                    MIN12_tree = (CommonTree)adaptor.dupNode(MIN12);

                    adaptor.addChild(root_0, MIN12_tree);

                    retval.type = "min_cardinality";

                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:130:4: REGEX
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    REGEX13=(CommonTree)match(input,REGEX,FOLLOW_REGEX_in_p_assertion287); 
                    REGEX13_tree = (CommonTree)adaptor.dupNode(REGEX13);

                    adaptor.addChild(root_0, REGEX13_tree);

                    retval.type = "regex";

                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:131:4: CARDINALITY
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    CARDINALITY14=(CommonTree)match(input,CARDINALITY,FOLLOW_CARDINALITY_in_p_assertion295); 
                    CARDINALITY14_tree = (CommonTree)adaptor.dupNode(CARDINALITY14);

                    adaptor.addChild(root_0, CARDINALITY14_tree);

                    retval.type = "cardinality";

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "p_assertion"

    public static class value_return extends TreeRuleReturnScope {
        public Object obj;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "value"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:135:1: value returns [Object obj] : (s= string | n= number | a= array | TEXT | BOOLEAN | NUMBER | TRUE | FALSE | NULL );
    public final OSFTree.value_return value() throws RecognitionException {
        OSFTree.value_return retval = new OSFTree.value_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree TEXT15=null;
        CommonTree BOOLEAN16=null;
        CommonTree NUMBER17=null;
        CommonTree TRUE18=null;
        CommonTree FALSE19=null;
        CommonTree NULL20=null;
        OSFTree.string_return s = null;

        OSFTree.number_return n = null;

        OSFTree.array_return a = null;


        CommonTree TEXT15_tree=null;
        CommonTree BOOLEAN16_tree=null;
        CommonTree NUMBER17_tree=null;
        CommonTree TRUE18_tree=null;
        CommonTree FALSE19_tree=null;
        CommonTree NULL20_tree=null;

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:137:2: (s= string | n= number | a= array | TEXT | BOOLEAN | NUMBER | TRUE | FALSE | NULL )
            int alt7=9;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:137:4: s= string
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_string_in_value318);
                    s=string();

                    state._fsp--;

                    adaptor.addChild(root_0, s.getTree());
                    retval.obj = s.result;

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:138:4: n= number
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_number_in_value327);
                    n=number();

                    state._fsp--;

                    adaptor.addChild(root_0, n.getTree());
                    retval.obj = n.result;

                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:139:4: a= array
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_array_in_value336);
                    a=array();

                    state._fsp--;

                    adaptor.addChild(root_0, a.getTree());
                    retval.obj = a.list;

                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:140:4: TEXT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    TEXT15=(CommonTree)match(input,TEXT,FOLLOW_TEXT_in_value344); 
                    TEXT15_tree = (CommonTree)adaptor.dupNode(TEXT15);

                    adaptor.addChild(root_0, TEXT15_tree);

                    retval.obj = ElementaryDataType.STRING;

                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:141:4: BOOLEAN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    BOOLEAN16=(CommonTree)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_value355); 
                    BOOLEAN16_tree = (CommonTree)adaptor.dupNode(BOOLEAN16);

                    adaptor.addChild(root_0, BOOLEAN16_tree);

                    retval.obj = ElementaryDataType.BOOLEAN;

                    }
                    break;
                case 6 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:142:4: NUMBER
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    NUMBER17=(CommonTree)match(input,NUMBER,FOLLOW_NUMBER_in_value363); 
                    NUMBER17_tree = (CommonTree)adaptor.dupNode(NUMBER17);

                    adaptor.addChild(root_0, NUMBER17_tree);

                    retval.obj = ElementaryDataType.INTEGER;

                    }
                    break;
                case 7 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:143:4: TRUE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    TRUE18=(CommonTree)match(input,TRUE,FOLLOW_TRUE_in_value372); 
                    TRUE18_tree = (CommonTree)adaptor.dupNode(TRUE18);

                    adaptor.addChild(root_0, TRUE18_tree);

                    retval.obj = Boolean.TRUE;

                    }
                    break;
                case 8 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:144:4: FALSE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    FALSE19=(CommonTree)match(input,FALSE,FOLLOW_FALSE_in_value383); 
                    FALSE19_tree = (CommonTree)adaptor.dupNode(FALSE19);

                    adaptor.addChild(root_0, FALSE19_tree);

                    retval.obj = Boolean.FALSE;

                    }
                    break;
                case 9 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:145:4: NULL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    NULL20=(CommonTree)match(input,NULL,FOLLOW_NULL_in_value393); 
                    NULL20_tree = (CommonTree)adaptor.dupNode(NULL20);

                    adaptor.addChild(root_0, NULL20_tree);

                    retval.obj = null;

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "value"

    public static class string_return extends TreeRuleReturnScope {
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "string"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:148:1: string returns [String result] : String ;
    public final OSFTree.string_return string() throws RecognitionException {
        OSFTree.string_return retval = new OSFTree.string_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree String21=null;

        CommonTree String21_tree=null;

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:149:2: ( String )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:149:4: String
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            String21=(CommonTree)match(input,String,FOLLOW_String_in_string414); 
            String21_tree = (CommonTree)adaptor.dupNode(String21);

            root_0 = (CommonTree)adaptor.becomeRoot(String21_tree, root_0);

             retval.result = extractString(String21); 

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "string"

    public static class number_return extends TreeRuleReturnScope {
        public Integer result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "number"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:153:1: number returns [Integer result] : ^( NUMBER Number ) ;
    public final OSFTree.number_return number() throws RecognitionException {
        OSFTree.number_return retval = new OSFTree.number_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree NUMBER22=null;
        CommonTree Number23=null;

        CommonTree NUMBER22_tree=null;
        CommonTree Number23_tree=null;

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:154:2: ( ^( NUMBER Number ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:154:4: ^( NUMBER Number )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            NUMBER22=(CommonTree)match(input,NUMBER,FOLLOW_NUMBER_in_number436); 
            NUMBER22_tree = (CommonTree)adaptor.dupNode(NUMBER22);

            root_1 = (CommonTree)adaptor.becomeRoot(NUMBER22_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            Number23=(CommonTree)match(input,Number,FOLLOW_Number_in_number438); 
            Number23_tree = (CommonTree)adaptor.dupNode(Number23);

            adaptor.addChild(root_1, Number23_tree);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = extractNumber(Number23); 

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "number"

    public static class array_return extends TreeRuleReturnScope {
        public List list;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "array"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:158:1: array returns [List list] : ^( ARRAY (v= value )+ ) ;
    public final OSFTree.array_return array() throws RecognitionException {
        OSFTree.array_return retval = new OSFTree.array_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ARRAY24=null;
        OSFTree.value_return v = null;


        CommonTree ARRAY24_tree=null;

         retval.list = new ArrayList(); 
        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:160:2: ( ^( ARRAY (v= value )+ ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:160:4: ^( ARRAY (v= value )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            ARRAY24=(CommonTree)match(input,ARRAY,FOLLOW_ARRAY_in_array464); 
            ARRAY24_tree = (CommonTree)adaptor.dupNode(ARRAY24);

            root_1 = (CommonTree)adaptor.becomeRoot(ARRAY24_tree, root_1);



            match(input, Token.DOWN, null); 
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:160:12: (v= value )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>=NUMBER && LA8_0<=TEXT)||(LA8_0>=TRUE && LA8_0<=NULL)||LA8_0==String) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/parser/impl/osf/OSFTree.g:160:13: v= value
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_value_in_array469);
            	    v=value();

            	    state._fsp--;

            	    adaptor.addChild(root_1, v.getTree());
            	    retval.list.add(v.obj); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }

        	catch (RecognitionException e) {
        		throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "array"

    // Delegated rules


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\13\uffff";
    static final String DFA7_eofS =
        "\13\uffff";
    static final String DFA7_minS =
        "\1\5\1\uffff\1\2\10\uffff";
    static final String DFA7_maxS =
        "\1\27\1\uffff\1\27\10\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\1\1\uffff\1\3\1\4\1\5\1\7\1\10\1\11\1\2\1\6";
    static final String DFA7_specialS =
        "\13\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\2\1\3\1\5\1\4\6\uffff\1\6\1\7\1\10\5\uffff\1\1",
            "",
            "\1\11\1\12\1\uffff\4\12\6\uffff\3\12\5\uffff\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "135:1: value returns [Object obj] : (s= string | n= number | a= array | TEXT | BOOLEAN | NUMBER | TRUE | FALSE | NULL );";
        }
    }
 

    public static final BitSet FOLLOW_DESCRIPTIONS_in_osl72 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_description_in_osl77 = new BitSet(new long[]{0x00000000000C0008L});
    public static final BitSet FOLLOW_PROPERTY_in_description98 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_property_dec_in_description102 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_RESOURCE_in_description109 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_resource_dec_in_description113 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STRING_in_property_dec141 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_string_in_property_dec145 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_PROPERTY_ASSERTION_in_property_dec149 = new BitSet(new long[]{0x0000000000007C08L});
    public static final BitSet FOLLOW_p_assertion_in_property_dec157 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_value_in_property_dec161 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STRING_in_resource_dec185 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_string_in_resource_dec189 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_PROPERTY_DEC_in_resource_dec193 = new BitSet(new long[]{0x0000000000000018L});
    public static final BitSet FOLLOW_assignment_dec_in_resource_dec199 = new BitSet(new long[]{0x0000000000000018L});
    public static final BitSet FOLLOW_STRING_in_assignment_dec224 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_string_in_assignment_dec228 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_PROPERTY_ASSERTION_in_assignment_dec232 = new BitSet(new long[]{0x0000000000007C08L});
    public static final BitSet FOLLOW_p_assertion_in_assignment_dec238 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_value_in_assignment_dec242 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_in_p_assertion264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MAX_in_p_assertion271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIN_in_p_assertion279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REGEX_in_p_assertion287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CARDINALITY_in_p_assertion295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_in_value318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_number_in_value327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_in_value336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TEXT_in_value344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOLEAN_in_value355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_value363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_value372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_value383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_value393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_String_in_string414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_number436 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Number_in_number438 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ARRAY_in_array464 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_value_in_array469 = new BitSet(new long[]{0x00000000008381E8L});

}