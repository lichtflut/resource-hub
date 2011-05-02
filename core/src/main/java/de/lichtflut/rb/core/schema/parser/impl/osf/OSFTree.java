// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/OSFTree.g 2011-05-02 18:22:15

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "STRING", "NUMBER", "ARRAY", "BOOLEAN", "TEXT", "COMMA", "TYPE", "MAX", "MIN", "REGEX", "CARDINALITY", "TRUE", "FALSE", "NULL", "PROPERTY", "RESOURCE", "DESCRIPTIONS", "String", "Number", "Digit", "WS", "UnicodeEscape", "EscapeSequence", "HexDigit", "'{'", "'='", "'}'", "'['", "']'"
    };
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int NULL=17;
    public static final int NUMBER=5;
    public static final int MAX=11;
    public static final int REGEX=13;
    public static final int MIN=12;
    public static final int DESCRIPTIONS=20;
    public static final int TEXT=8;
    public static final int Digit=23;
    public static final int EOF=-1;
    public static final int HexDigit=27;
    public static final int RESOURCE=19;
    public static final int TRUE=15;
    public static final int TYPE=10;
    public static final int CARDINALITY=14;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int BOOLEAN=7;
    public static final int WS=24;
    public static final int Number=22;
    public static final int PROPERTY=18;
    public static final int COMMA=9;
    public static final int UnicodeEscape=25;
    public static final int String=21;
    public static final int FALSE=16;
    public static final int EscapeSequence=26;
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
    public String getGrammarFileName() { return "de/lichtflut/rb/core/schema/OSFTree.g"; }


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





    public static class osl_return extends TreeRuleReturnScope {
        public List<ResourceSchemaType> list;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "osl"
    // de/lichtflut/rb/core/schema/OSFTree.g:73:1: osl returns [List<ResourceSchemaType> list] : ^( DESCRIPTIONS (d= description )* ) ;
    public final OSFTree.osl_return osl() throws RecognitionException {
        OSFTree.osl_return retval = new OSFTree.osl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree DESCRIPTIONS1=null;
        OSFTree.description_return d = null;


        CommonTree DESCRIPTIONS1_tree=null;

         list = new ArrayList<ResourceSchemaType>(); 
        try {
            // de/lichtflut/rb/core/schema/OSFTree.g:75:2: ( ^( DESCRIPTIONS (d= description )* ) )
            // de/lichtflut/rb/core/schema/OSFTree.g:75:4: ^( DESCRIPTIONS (d= description )* )
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
                // de/lichtflut/rb/core/schema/OSFTree.g:75:19: (d= description )*
                loop1:
                do {
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( ((LA1_0>=PROPERTY && LA1_0<=RESOURCE)) ) {
                        alt1=1;
                    }


                    switch (alt1) {
                	case 1 :
                	    // de/lichtflut/rb/core/schema/OSFTree.g:75:20: d= description
                	    {
                	    _last = (CommonTree)input.LT(1);
                	    pushFollow(FOLLOW_description_in_osl77);
                	    d=description();

                	    state._fsp--;

                	    adaptor.addChild(root_1, d.getTree());
                	    retval.list.add(d);

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
    // de/lichtflut/rb/core/schema/OSFTree.g:78:1: description returns [ResourceSchemaType type] : ( ^( PROPERTY p= property_dec ) | ^( RESOURCE r= resource_dec ) ) ;
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
            // de/lichtflut/rb/core/schema/OSFTree.g:78:47: ( ( ^( PROPERTY p= property_dec ) | ^( RESOURCE r= resource_dec ) ) )
            // de/lichtflut/rb/core/schema/OSFTree.g:79:2: ( ^( PROPERTY p= property_dec ) | ^( RESOURCE r= resource_dec ) )
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/OSFTree.g:79:2: ( ^( PROPERTY p= property_dec ) | ^( RESOURCE r= resource_dec ) )
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
                    // de/lichtflut/rb/core/schema/OSFTree.g:79:3: ^( PROPERTY p= property_dec )
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
                    retval.type =p;

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:80:3: ^( RESOURCE r= resource_dec )
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
                    retval.type =r;

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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "property_dec"
    // de/lichtflut/rb/core/schema/OSFTree.g:85:1: property_dec : PROPERTY String ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )? ;
    public final OSFTree.property_dec_return property_dec() throws RecognitionException {
        OSFTree.property_dec_return retval = new OSFTree.property_dec_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PROPERTY4=null;
        CommonTree String5=null;
        CommonTree char_literal6=null;
        CommonTree char_literal8=null;
        CommonTree COMMA10=null;
        CommonTree char_literal12=null;
        CommonTree char_literal14=null;
        OSFTree.p_assertion_return p_assertion7 = null;

        OSFTree.value_return value9 = null;

        OSFTree.p_assertion_return p_assertion11 = null;

        OSFTree.value_return value13 = null;


        CommonTree PROPERTY4_tree=null;
        CommonTree String5_tree=null;
        CommonTree char_literal6_tree=null;
        CommonTree char_literal8_tree=null;
        CommonTree COMMA10_tree=null;
        CommonTree char_literal12_tree=null;
        CommonTree char_literal14_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSFTree.g:86:2: ( PROPERTY String ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )? )
            // de/lichtflut/rb/core/schema/OSFTree.g:86:4: PROPERTY String ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )?
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            PROPERTY4=(CommonTree)match(input,PROPERTY,FOLLOW_PROPERTY_in_property_dec132); 
            PROPERTY4_tree = (CommonTree)adaptor.dupNode(PROPERTY4);

            adaptor.addChild(root_0, PROPERTY4_tree);

            _last = (CommonTree)input.LT(1);
            String5=(CommonTree)match(input,String,FOLLOW_String_in_property_dec136); 
            String5_tree = (CommonTree)adaptor.dupNode(String5);

            adaptor.addChild(root_0, String5_tree);

            // de/lichtflut/rb/core/schema/OSFTree.g:88:3: ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==28) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:88:4: '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}'
                    {
                    _last = (CommonTree)input.LT(1);
                    char_literal6=(CommonTree)match(input,28,FOLLOW_28_in_property_dec141); 
                    // de/lichtflut/rb/core/schema/OSFTree.g:89:3: ( p_assertion '=' value ( COMMA p_assertion '=' value )* )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>=TYPE && LA4_0<=CARDINALITY)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // de/lichtflut/rb/core/schema/OSFTree.g:89:4: p_assertion '=' value ( COMMA p_assertion '=' value )*
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_p_assertion_in_property_dec147);
                            p_assertion7=p_assertion();

                            state._fsp--;

                            adaptor.addChild(root_0, p_assertion7.getTree());
                            _last = (CommonTree)input.LT(1);
                            char_literal8=(CommonTree)match(input,29,FOLLOW_29_in_property_dec149); 
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_value_in_property_dec152);
                            value9=value();

                            state._fsp--;

                            adaptor.addChild(root_0, value9.getTree());
                            // de/lichtflut/rb/core/schema/OSFTree.g:89:26: ( COMMA p_assertion '=' value )*
                            loop3:
                            do {
                                int alt3=2;
                                int LA3_0 = input.LA(1);

                                if ( (LA3_0==COMMA) ) {
                                    alt3=1;
                                }


                                switch (alt3) {
                            	case 1 :
                            	    // de/lichtflut/rb/core/schema/OSFTree.g:89:27: COMMA p_assertion '=' value
                            	    {
                            	    _last = (CommonTree)input.LT(1);
                            	    COMMA10=(CommonTree)match(input,COMMA,FOLLOW_COMMA_in_property_dec154); 
                            	    _last = (CommonTree)input.LT(1);
                            	    pushFollow(FOLLOW_p_assertion_in_property_dec157);
                            	    p_assertion11=p_assertion();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, p_assertion11.getTree());
                            	    _last = (CommonTree)input.LT(1);
                            	    char_literal12=(CommonTree)match(input,29,FOLLOW_29_in_property_dec159); 
                            	    _last = (CommonTree)input.LT(1);
                            	    pushFollow(FOLLOW_value_in_property_dec162);
                            	    value13=value();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, value13.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop3;
                                }
                            } while (true);


                            }
                            break;

                    }

                    _last = (CommonTree)input.LT(1);
                    char_literal14=(CommonTree)match(input,30,FOLLOW_30_in_property_dec170); 

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
    // $ANTLR end "property_dec"

    public static class resource_dec_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "resource_dec"
    // de/lichtflut/rb/core/schema/OSFTree.g:92:1: resource_dec : RESOURCE String '{' ( property_dec ( ',' property_dec )* )? '}' ;
    public final OSFTree.resource_dec_return resource_dec() throws RecognitionException {
        OSFTree.resource_dec_return retval = new OSFTree.resource_dec_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree RESOURCE15=null;
        CommonTree String16=null;
        CommonTree char_literal17=null;
        CommonTree char_literal19=null;
        CommonTree char_literal21=null;
        OSFTree.property_dec_return property_dec18 = null;

        OSFTree.property_dec_return property_dec20 = null;


        CommonTree RESOURCE15_tree=null;
        CommonTree String16_tree=null;
        CommonTree char_literal17_tree=null;
        CommonTree char_literal19_tree=null;
        CommonTree char_literal21_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSFTree.g:93:2: ( RESOURCE String '{' ( property_dec ( ',' property_dec )* )? '}' )
            // de/lichtflut/rb/core/schema/OSFTree.g:93:4: RESOURCE String '{' ( property_dec ( ',' property_dec )* )? '}'
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            RESOURCE15=(CommonTree)match(input,RESOURCE,FOLLOW_RESOURCE_in_resource_dec185); 
            RESOURCE15_tree = (CommonTree)adaptor.dupNode(RESOURCE15);

            adaptor.addChild(root_0, RESOURCE15_tree);

            _last = (CommonTree)input.LT(1);
            String16=(CommonTree)match(input,String,FOLLOW_String_in_resource_dec189); 
            String16_tree = (CommonTree)adaptor.dupNode(String16);

            adaptor.addChild(root_0, String16_tree);

            _last = (CommonTree)input.LT(1);
            char_literal17=(CommonTree)match(input,28,FOLLOW_28_in_resource_dec193); 
            // de/lichtflut/rb/core/schema/OSFTree.g:96:3: ( property_dec ( ',' property_dec )* )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==PROPERTY) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:96:4: property_dec ( ',' property_dec )*
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_property_dec_in_resource_dec199);
                    property_dec18=property_dec();

                    state._fsp--;

                    adaptor.addChild(root_0, property_dec18.getTree());
                    // de/lichtflut/rb/core/schema/OSFTree.g:96:17: ( ',' property_dec )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==COMMA) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // de/lichtflut/rb/core/schema/OSFTree.g:96:18: ',' property_dec
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    char_literal19=(CommonTree)match(input,COMMA,FOLLOW_COMMA_in_resource_dec202); 
                    	    char_literal19_tree = (CommonTree)adaptor.dupNode(char_literal19);

                    	    adaptor.addChild(root_0, char_literal19_tree);

                    	    _last = (CommonTree)input.LT(1);
                    	    pushFollow(FOLLOW_property_dec_in_resource_dec204);
                    	    property_dec20=property_dec();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, property_dec20.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            char_literal21=(CommonTree)match(input,30,FOLLOW_30_in_resource_dec212); 

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

    public static class p_assertion_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "p_assertion"
    // de/lichtflut/rb/core/schema/OSFTree.g:101:1: p_assertion : ( TYPE | MAX | MIN | REGEX | CARDINALITY );
    public final OSFTree.p_assertion_return p_assertion() throws RecognitionException {
        OSFTree.p_assertion_return retval = new OSFTree.p_assertion_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set22=null;

        CommonTree set22_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSFTree.g:102:2: ( TYPE | MAX | MIN | REGEX | CARDINALITY )
            // de/lichtflut/rb/core/schema/OSFTree.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set22=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=TYPE && input.LA(1)<=CARDINALITY) ) {
                input.consume();

                set22_tree = (CommonTree)adaptor.dupNode(set22);

                adaptor.addChild(root_0, set22_tree);

                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
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
    // $ANTLR end "p_assertion"

    public static class value_return extends TreeRuleReturnScope {
        public Object obj;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "value"
    // de/lichtflut/rb/core/schema/OSFTree.g:109:1: value returns [Object obj] : (s= string | n= number | a= array | TEXT | BOOLEAN | NUMBER | TRUE | FALSE | NULL );
    public final OSFTree.value_return value() throws RecognitionException {
        OSFTree.value_return retval = new OSFTree.value_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree TEXT23=null;
        CommonTree BOOLEAN24=null;
        CommonTree NUMBER25=null;
        CommonTree TRUE26=null;
        CommonTree FALSE27=null;
        CommonTree NULL28=null;
        OSFTree.string_return s = null;

        OSFTree.number_return n = null;

        OSFTree.array_return a = null;


        CommonTree TEXT23_tree=null;
        CommonTree BOOLEAN24_tree=null;
        CommonTree NUMBER25_tree=null;
        CommonTree TRUE26_tree=null;
        CommonTree FALSE27_tree=null;
        CommonTree NULL28_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSFTree.g:111:2: (s= string | n= number | a= array | TEXT | BOOLEAN | NUMBER | TRUE | FALSE | NULL )
            int alt8=9;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:111:4: s= string
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_string_in_value263);
                    s=string();

                    state._fsp--;

                    adaptor.addChild(root_0, s.getTree());
                    retval.obj = s;

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:112:4: n= number
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_number_in_value272);
                    n=number();

                    state._fsp--;

                    adaptor.addChild(root_0, n.getTree());
                    retval.obj = n;

                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:113:4: a= array
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_array_in_value281);
                    a=array();

                    state._fsp--;

                    adaptor.addChild(root_0, a.getTree());
                    retval.obj = a;

                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:114:4: TEXT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    TEXT23=(CommonTree)match(input,TEXT,FOLLOW_TEXT_in_value289); 
                    TEXT23_tree = (CommonTree)adaptor.dupNode(TEXT23);

                    adaptor.addChild(root_0, TEXT23_tree);

                    retval.obj = ElementaryDataType.STRING;

                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:115:4: BOOLEAN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    BOOLEAN24=(CommonTree)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_value300); 
                    BOOLEAN24_tree = (CommonTree)adaptor.dupNode(BOOLEAN24);

                    adaptor.addChild(root_0, BOOLEAN24_tree);

                    retval.obj = ElementaryDataType.BOOLEAN;

                    }
                    break;
                case 6 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:116:4: NUMBER
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    NUMBER25=(CommonTree)match(input,NUMBER,FOLLOW_NUMBER_in_value308); 
                    NUMBER25_tree = (CommonTree)adaptor.dupNode(NUMBER25);

                    adaptor.addChild(root_0, NUMBER25_tree);

                    retval.obj = ElementaryDataType.INTEGER;

                    }
                    break;
                case 7 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:117:4: TRUE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    TRUE26=(CommonTree)match(input,TRUE,FOLLOW_TRUE_in_value317); 
                    TRUE26_tree = (CommonTree)adaptor.dupNode(TRUE26);

                    adaptor.addChild(root_0, TRUE26_tree);

                    retval.obj = Boolean.TRUE;

                    }
                    break;
                case 8 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:118:4: FALSE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    FALSE27=(CommonTree)match(input,FALSE,FOLLOW_FALSE_in_value328); 
                    FALSE27_tree = (CommonTree)adaptor.dupNode(FALSE27);

                    adaptor.addChild(root_0, FALSE27_tree);

                    retval.obj = Boolean.FALSE;

                    }
                    break;
                case 9 :
                    // de/lichtflut/rb/core/schema/OSFTree.g:119:4: NULL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    NULL28=(CommonTree)match(input,NULL,FOLLOW_NULL_in_value338); 
                    NULL28_tree = (CommonTree)adaptor.dupNode(NULL28);

                    adaptor.addChild(root_0, NULL28_tree);

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
    // de/lichtflut/rb/core/schema/OSFTree.g:122:1: string returns [String result] : ^( STRING String ) ;
    public final OSFTree.string_return string() throws RecognitionException {
        OSFTree.string_return retval = new OSFTree.string_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree STRING29=null;
        CommonTree String30=null;

        CommonTree STRING29_tree=null;
        CommonTree String30_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSFTree.g:123:2: ( ^( STRING String ) )
            // de/lichtflut/rb/core/schema/OSFTree.g:123:4: ^( STRING String )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            STRING29=(CommonTree)match(input,STRING,FOLLOW_STRING_in_string360); 
            STRING29_tree = (CommonTree)adaptor.dupNode(STRING29);

            root_1 = (CommonTree)adaptor.becomeRoot(STRING29_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            String30=(CommonTree)match(input,String,FOLLOW_String_in_string362); 
            String30_tree = (CommonTree)adaptor.dupNode(String30);

            adaptor.addChild(root_1, String30_tree);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = extractString(String30); 

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
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "number"
    // de/lichtflut/rb/core/schema/OSFTree.g:127:1: number returns [Object result] : ^( NUMBER Number ) ;
    public final OSFTree.number_return number() throws RecognitionException {
        OSFTree.number_return retval = new OSFTree.number_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree NUMBER31=null;
        CommonTree Number32=null;

        CommonTree NUMBER31_tree=null;
        CommonTree Number32_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSFTree.g:128:2: ( ^( NUMBER Number ) )
            // de/lichtflut/rb/core/schema/OSFTree.g:128:4: ^( NUMBER Number )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            NUMBER31=(CommonTree)match(input,NUMBER,FOLLOW_NUMBER_in_number384); 
            NUMBER31_tree = (CommonTree)adaptor.dupNode(NUMBER31);

            root_1 = (CommonTree)adaptor.becomeRoot(NUMBER31_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            Number32=(CommonTree)match(input,Number,FOLLOW_Number_in_number386); 
            Number32_tree = (CommonTree)adaptor.dupNode(Number32);

            adaptor.addChild(root_1, Number32_tree);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = extractNumber(Number32); 

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
    // de/lichtflut/rb/core/schema/OSFTree.g:132:1: array returns [List list] : ^( ARRAY (v= value )+ ) ;
    public final OSFTree.array_return array() throws RecognitionException {
        OSFTree.array_return retval = new OSFTree.array_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ARRAY33=null;
        OSFTree.value_return v = null;


        CommonTree ARRAY33_tree=null;

         list = new ArrayList(); 
        try {
            // de/lichtflut/rb/core/schema/OSFTree.g:134:2: ( ^( ARRAY (v= value )+ ) )
            // de/lichtflut/rb/core/schema/OSFTree.g:134:4: ^( ARRAY (v= value )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            ARRAY33=(CommonTree)match(input,ARRAY,FOLLOW_ARRAY_in_array412); 
            ARRAY33_tree = (CommonTree)adaptor.dupNode(ARRAY33);

            root_1 = (CommonTree)adaptor.becomeRoot(ARRAY33_tree, root_1);



            match(input, Token.DOWN, null); 
            // de/lichtflut/rb/core/schema/OSFTree.g:134:12: (v= value )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>=STRING && LA9_0<=TEXT)||(LA9_0>=TRUE && LA9_0<=NULL)) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/OSFTree.g:134:13: v= value
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_value_in_array417);
            	    v=value();

            	    state._fsp--;

            	    adaptor.addChild(root_1, v.getTree());
            	    retval.list.add(v); 

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
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


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\13\uffff";
    static final String DFA8_eofS =
        "\13\uffff";
    static final String DFA8_minS =
        "\1\4\1\uffff\1\2\10\uffff";
    static final String DFA8_maxS =
        "\1\21\1\uffff\1\36\10\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\1\uffff\1\3\1\4\1\5\1\7\1\10\1\11\1\2\1\6";
    static final String DFA8_specialS =
        "\13\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\1\1\2\1\3\1\5\1\4\6\uffff\1\6\1\7\1\10",
            "",
            "\1\11\7\12\5\uffff\3\12\14\uffff\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "109:1: value returns [Object obj] : (s= string | n= number | a= array | TEXT | BOOLEAN | NUMBER | TRUE | FALSE | NULL );";
        }
    }
 

    public static final BitSet FOLLOW_DESCRIPTIONS_in_osl72 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_description_in_osl77 = new BitSet(new long[]{0x00000000000C0008L});
    public static final BitSet FOLLOW_PROPERTY_in_description98 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_property_dec_in_description102 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_RESOURCE_in_description109 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_resource_dec_in_description113 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_in_property_dec132 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_String_in_property_dec136 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_property_dec141 = new BitSet(new long[]{0x0000000040007C00L});
    public static final BitSet FOLLOW_p_assertion_in_property_dec147 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_property_dec149 = new BitSet(new long[]{0x00000000000381F0L});
    public static final BitSet FOLLOW_value_in_property_dec152 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_COMMA_in_property_dec154 = new BitSet(new long[]{0x0000000000007C00L});
    public static final BitSet FOLLOW_p_assertion_in_property_dec157 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_property_dec159 = new BitSet(new long[]{0x00000000000381F0L});
    public static final BitSet FOLLOW_value_in_property_dec162 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_30_in_property_dec170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RESOURCE_in_resource_dec185 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_String_in_resource_dec189 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_resource_dec193 = new BitSet(new long[]{0x0000000040040000L});
    public static final BitSet FOLLOW_property_dec_in_resource_dec199 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_COMMA_in_resource_dec202 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_property_dec_in_resource_dec204 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_30_in_resource_dec212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_p_assertion0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_in_value263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_number_in_value272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_in_value281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TEXT_in_value289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOLEAN_in_value300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_value308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_value317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_value328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_value338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_string360 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_String_in_string362 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMBER_in_number384 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Number_in_number386 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ARRAY_in_array412 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_value_in_array417 = new BitSet(new long[]{0x00000000000381F8L});

}