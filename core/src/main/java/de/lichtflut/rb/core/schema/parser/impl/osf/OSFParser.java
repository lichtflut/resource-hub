// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/OSF.g 2011-05-02 18:20:18

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


import org.antlr.runtime.tree.*;

public class OSFParser extends Parser {
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
    public static final int WS=24;
    public static final int BOOLEAN=7;
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


        public OSFParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public OSFParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return OSFParser.tokenNames; }
    public String getGrammarFileName() { return "de/lichtflut/rb/core/schema/OSF.g"; }


    protected void mismatch(IntStream input, int ttype, BitSet follow)
    throws RecognitionException
    {
    throw new MismatchedTokenException(ttype, input);
    }
    public Object recoverFromMismatchedSet(IntStream input,
    RecognitionException e,
    BitSet follow)
    throws RecognitionException
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



    public static class osl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "osl"
    // de/lichtflut/rb/core/schema/OSF.g:77:1: osl : descriptions -> ^( DESCRIPTIONS descriptions ) ;
    public final OSFParser.osl_return osl() throws RecognitionException {
        OSFParser.osl_return retval = new OSFParser.osl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        OSFParser.descriptions_return descriptions1 = null;


        RewriteRuleSubtreeStream stream_descriptions=new RewriteRuleSubtreeStream(adaptor,"rule descriptions");
        try {
            // de/lichtflut/rb/core/schema/OSF.g:77:6: ( descriptions -> ^( DESCRIPTIONS descriptions ) )
            // de/lichtflut/rb/core/schema/OSF.g:77:8: descriptions
            {
            pushFollow(FOLLOW_descriptions_in_osl119);
            descriptions1=descriptions();

            state._fsp--;

            stream_descriptions.add(descriptions1.getTree());


            // AST REWRITE
            // elements: descriptions
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 77:22: -> ^( DESCRIPTIONS descriptions )
            {
                // de/lichtflut/rb/core/schema/OSF.g:77:25: ^( DESCRIPTIONS descriptions )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DESCRIPTIONS, "DESCRIPTIONS"), root_1);

                adaptor.addChild(root_1, stream_descriptions.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "osl"

    public static class descriptions_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "descriptions"
    // de/lichtflut/rb/core/schema/OSF.g:80:1: descriptions : ( description ( COMMA description )* )? ;
    public final OSFParser.descriptions_return descriptions() throws RecognitionException {
        OSFParser.descriptions_return retval = new OSFParser.descriptions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA3=null;
        OSFParser.description_return description2 = null;

        OSFParser.description_return description4 = null;


        Object COMMA3_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSF.g:81:2: ( ( description ( COMMA description )* )? )
            // de/lichtflut/rb/core/schema/OSF.g:81:4: ( description ( COMMA description )* )?
            {
            root_0 = (Object)adaptor.nil();

            // de/lichtflut/rb/core/schema/OSF.g:81:4: ( description ( COMMA description )* )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=PROPERTY && LA2_0<=RESOURCE)) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSF.g:81:5: description ( COMMA description )*
                    {
                    pushFollow(FOLLOW_description_in_descriptions141);
                    description2=description();

                    state._fsp--;

                    adaptor.addChild(root_0, description2.getTree());
                    // de/lichtflut/rb/core/schema/OSF.g:81:17: ( COMMA description )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==COMMA) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // de/lichtflut/rb/core/schema/OSF.g:81:18: COMMA description
                    	    {
                    	    COMMA3=(Token)match(input,COMMA,FOLLOW_COMMA_in_descriptions144); 
                    	    pushFollow(FOLLOW_description_in_descriptions147);
                    	    description4=description();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, description4.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "descriptions"

    public static class description_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "description"
    // de/lichtflut/rb/core/schema/OSF.g:83:1: description : ( property_dec -> ^( PROPERTY property_dec ) | resource_dec -> ^( RESOURCE resource_dec ) ) ;
    public final OSFParser.description_return description() throws RecognitionException {
        OSFParser.description_return retval = new OSFParser.description_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        OSFParser.property_dec_return property_dec5 = null;

        OSFParser.resource_dec_return resource_dec6 = null;


        RewriteRuleSubtreeStream stream_property_dec=new RewriteRuleSubtreeStream(adaptor,"rule property_dec");
        RewriteRuleSubtreeStream stream_resource_dec=new RewriteRuleSubtreeStream(adaptor,"rule resource_dec");
        try {
            // de/lichtflut/rb/core/schema/OSF.g:83:14: ( ( property_dec -> ^( PROPERTY property_dec ) | resource_dec -> ^( RESOURCE resource_dec ) ) )
            // de/lichtflut/rb/core/schema/OSF.g:84:2: ( property_dec -> ^( PROPERTY property_dec ) | resource_dec -> ^( RESOURCE resource_dec ) )
            {
            // de/lichtflut/rb/core/schema/OSF.g:84:2: ( property_dec -> ^( PROPERTY property_dec ) | resource_dec -> ^( RESOURCE resource_dec ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==PROPERTY) ) {
                alt3=1;
            }
            else if ( (LA3_0==RESOURCE) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSF.g:84:4: property_dec
                    {
                    pushFollow(FOLLOW_property_dec_in_description163);
                    property_dec5=property_dec();

                    state._fsp--;

                    stream_property_dec.add(property_dec5.getTree());


                    // AST REWRITE
                    // elements: property_dec
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 84:17: -> ^( PROPERTY property_dec )
                    {
                        // de/lichtflut/rb/core/schema/OSF.g:84:20: ^( PROPERTY property_dec )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PROPERTY, "PROPERTY"), root_1);

                        adaptor.addChild(root_1, stream_property_dec.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/OSF.g:85:4: resource_dec
                    {
                    pushFollow(FOLLOW_resource_dec_in_description176);
                    resource_dec6=resource_dec();

                    state._fsp--;

                    stream_resource_dec.add(resource_dec6.getTree());


                    // AST REWRITE
                    // elements: resource_dec
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 85:17: -> ^( RESOURCE resource_dec )
                    {
                        // de/lichtflut/rb/core/schema/OSF.g:85:20: ^( RESOURCE resource_dec )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(RESOURCE, "RESOURCE"), root_1);

                        adaptor.addChild(root_1, stream_resource_dec.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "description"

    public static class property_dec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "property_dec"
    // de/lichtflut/rb/core/schema/OSF.g:90:1: property_dec : PROPERTY String ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )? ;
    public final OSFParser.property_dec_return property_dec() throws RecognitionException {
        OSFParser.property_dec_return retval = new OSFParser.property_dec_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PROPERTY7=null;
        Token String8=null;
        Token char_literal9=null;
        Token char_literal11=null;
        Token COMMA13=null;
        Token char_literal15=null;
        Token char_literal17=null;
        OSFParser.p_assertion_return p_assertion10 = null;

        OSFParser.value_return value12 = null;

        OSFParser.p_assertion_return p_assertion14 = null;

        OSFParser.value_return value16 = null;


        Object PROPERTY7_tree=null;
        Object String8_tree=null;
        Object char_literal9_tree=null;
        Object char_literal11_tree=null;
        Object COMMA13_tree=null;
        Object char_literal15_tree=null;
        Object char_literal17_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSF.g:91:2: ( PROPERTY String ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )? )
            // de/lichtflut/rb/core/schema/OSF.g:91:4: PROPERTY String ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )?
            {
            root_0 = (Object)adaptor.nil();

            PROPERTY7=(Token)match(input,PROPERTY,FOLLOW_PROPERTY_in_property_dec201); 
            PROPERTY7_tree = (Object)adaptor.create(PROPERTY7);
            adaptor.addChild(root_0, PROPERTY7_tree);

            String8=(Token)match(input,String,FOLLOW_String_in_property_dec205); 
            String8_tree = (Object)adaptor.create(String8);
            adaptor.addChild(root_0, String8_tree);

            // de/lichtflut/rb/core/schema/OSF.g:93:3: ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==28) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSF.g:93:4: '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}'
                    {
                    char_literal9=(Token)match(input,28,FOLLOW_28_in_property_dec210); 
                    // de/lichtflut/rb/core/schema/OSF.g:94:3: ( p_assertion '=' value ( COMMA p_assertion '=' value )* )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( ((LA5_0>=TYPE && LA5_0<=CARDINALITY)) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // de/lichtflut/rb/core/schema/OSF.g:94:4: p_assertion '=' value ( COMMA p_assertion '=' value )*
                            {
                            pushFollow(FOLLOW_p_assertion_in_property_dec216);
                            p_assertion10=p_assertion();

                            state._fsp--;

                            adaptor.addChild(root_0, p_assertion10.getTree());
                            char_literal11=(Token)match(input,29,FOLLOW_29_in_property_dec218); 
                            pushFollow(FOLLOW_value_in_property_dec221);
                            value12=value();

                            state._fsp--;

                            adaptor.addChild(root_0, value12.getTree());
                            // de/lichtflut/rb/core/schema/OSF.g:94:26: ( COMMA p_assertion '=' value )*
                            loop4:
                            do {
                                int alt4=2;
                                int LA4_0 = input.LA(1);

                                if ( (LA4_0==COMMA) ) {
                                    alt4=1;
                                }


                                switch (alt4) {
                            	case 1 :
                            	    // de/lichtflut/rb/core/schema/OSF.g:94:27: COMMA p_assertion '=' value
                            	    {
                            	    COMMA13=(Token)match(input,COMMA,FOLLOW_COMMA_in_property_dec223); 
                            	    pushFollow(FOLLOW_p_assertion_in_property_dec226);
                            	    p_assertion14=p_assertion();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, p_assertion14.getTree());
                            	    char_literal15=(Token)match(input,29,FOLLOW_29_in_property_dec228); 
                            	    pushFollow(FOLLOW_value_in_property_dec231);
                            	    value16=value();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, value16.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop4;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal17=(Token)match(input,30,FOLLOW_30_in_property_dec239); 

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "property_dec"

    public static class resource_dec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "resource_dec"
    // de/lichtflut/rb/core/schema/OSF.g:97:1: resource_dec : RESOURCE String '{' ( property_dec ( ',' property_dec )* )? '}' ;
    public final OSFParser.resource_dec_return resource_dec() throws RecognitionException {
        OSFParser.resource_dec_return retval = new OSFParser.resource_dec_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RESOURCE18=null;
        Token String19=null;
        Token char_literal20=null;
        Token char_literal22=null;
        Token char_literal24=null;
        OSFParser.property_dec_return property_dec21 = null;

        OSFParser.property_dec_return property_dec23 = null;


        Object RESOURCE18_tree=null;
        Object String19_tree=null;
        Object char_literal20_tree=null;
        Object char_literal22_tree=null;
        Object char_literal24_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSF.g:98:2: ( RESOURCE String '{' ( property_dec ( ',' property_dec )* )? '}' )
            // de/lichtflut/rb/core/schema/OSF.g:98:4: RESOURCE String '{' ( property_dec ( ',' property_dec )* )? '}'
            {
            root_0 = (Object)adaptor.nil();

            RESOURCE18=(Token)match(input,RESOURCE,FOLLOW_RESOURCE_in_resource_dec254); 
            RESOURCE18_tree = (Object)adaptor.create(RESOURCE18);
            adaptor.addChild(root_0, RESOURCE18_tree);

            String19=(Token)match(input,String,FOLLOW_String_in_resource_dec258); 
            String19_tree = (Object)adaptor.create(String19);
            adaptor.addChild(root_0, String19_tree);

            char_literal20=(Token)match(input,28,FOLLOW_28_in_resource_dec262); 
            // de/lichtflut/rb/core/schema/OSF.g:101:3: ( property_dec ( ',' property_dec )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==PROPERTY) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSF.g:101:4: property_dec ( ',' property_dec )*
                    {
                    pushFollow(FOLLOW_property_dec_in_resource_dec268);
                    property_dec21=property_dec();

                    state._fsp--;

                    adaptor.addChild(root_0, property_dec21.getTree());
                    // de/lichtflut/rb/core/schema/OSF.g:101:17: ( ',' property_dec )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==COMMA) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // de/lichtflut/rb/core/schema/OSF.g:101:18: ',' property_dec
                    	    {
                    	    char_literal22=(Token)match(input,COMMA,FOLLOW_COMMA_in_resource_dec271); 
                    	    char_literal22_tree = (Object)adaptor.create(char_literal22);
                    	    adaptor.addChild(root_0, char_literal22_tree);

                    	    pushFollow(FOLLOW_property_dec_in_resource_dec273);
                    	    property_dec23=property_dec();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, property_dec23.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    }
                    break;

            }

            char_literal24=(Token)match(input,30,FOLLOW_30_in_resource_dec281); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "resource_dec"

    public static class p_assertion_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "p_assertion"
    // de/lichtflut/rb/core/schema/OSF.g:106:1: p_assertion : ( TYPE -> TYPE | MAX -> MAX | MIN -> MIN | REGEX -> REGEX | CARDINALITY -> CARDINALITY );
    public final OSFParser.p_assertion_return p_assertion() throws RecognitionException {
        OSFParser.p_assertion_return retval = new OSFParser.p_assertion_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TYPE25=null;
        Token MAX26=null;
        Token MIN27=null;
        Token REGEX28=null;
        Token CARDINALITY29=null;

        Object TYPE25_tree=null;
        Object MAX26_tree=null;
        Object MIN27_tree=null;
        Object REGEX28_tree=null;
        Object CARDINALITY29_tree=null;
        RewriteRuleTokenStream stream_MAX=new RewriteRuleTokenStream(adaptor,"token MAX");
        RewriteRuleTokenStream stream_REGEX=new RewriteRuleTokenStream(adaptor,"token REGEX");
        RewriteRuleTokenStream stream_MIN=new RewriteRuleTokenStream(adaptor,"token MIN");
        RewriteRuleTokenStream stream_TYPE=new RewriteRuleTokenStream(adaptor,"token TYPE");
        RewriteRuleTokenStream stream_CARDINALITY=new RewriteRuleTokenStream(adaptor,"token CARDINALITY");

        try {
            // de/lichtflut/rb/core/schema/OSF.g:107:2: ( TYPE -> TYPE | MAX -> MAX | MIN -> MIN | REGEX -> REGEX | CARDINALITY -> CARDINALITY )
            int alt9=5;
            switch ( input.LA(1) ) {
            case TYPE:
                {
                alt9=1;
                }
                break;
            case MAX:
                {
                alt9=2;
                }
                break;
            case MIN:
                {
                alt9=3;
                }
                break;
            case REGEX:
                {
                alt9=4;
                }
                break;
            case CARDINALITY:
                {
                alt9=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSF.g:107:4: TYPE
                    {
                    TYPE25=(Token)match(input,TYPE,FOLLOW_TYPE_in_p_assertion294);  
                    stream_TYPE.add(TYPE25);



                    // AST REWRITE
                    // elements: TYPE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 107:9: -> TYPE
                    {
                        adaptor.addChild(root_0, stream_TYPE.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/OSF.g:108:4: MAX
                    {
                    MAX26=(Token)match(input,MAX,FOLLOW_MAX_in_p_assertion303);  
                    stream_MAX.add(MAX26);



                    // AST REWRITE
                    // elements: MAX
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 108:9: -> MAX
                    {
                        adaptor.addChild(root_0, stream_MAX.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/OSF.g:109:4: MIN
                    {
                    MIN27=(Token)match(input,MIN,FOLLOW_MIN_in_p_assertion313);  
                    stream_MIN.add(MIN27);



                    // AST REWRITE
                    // elements: MIN
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 109:9: -> MIN
                    {
                        adaptor.addChild(root_0, stream_MIN.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/OSF.g:110:4: REGEX
                    {
                    REGEX28=(Token)match(input,REGEX,FOLLOW_REGEX_in_p_assertion323);  
                    stream_REGEX.add(REGEX28);



                    // AST REWRITE
                    // elements: REGEX
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 110:10: -> REGEX
                    {
                        adaptor.addChild(root_0, stream_REGEX.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/OSF.g:111:4: CARDINALITY
                    {
                    CARDINALITY29=(Token)match(input,CARDINALITY,FOLLOW_CARDINALITY_in_p_assertion332);  
                    stream_CARDINALITY.add(CARDINALITY29);



                    // AST REWRITE
                    // elements: CARDINALITY
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 111:16: -> CARDINALITY
                    {
                        adaptor.addChild(root_0, stream_CARDINALITY.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "p_assertion"

    public static class value_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "value"
    // de/lichtflut/rb/core/schema/OSF.g:114:1: value : ( string | number | array | TEXT -> TEXT | BOOLEAN -> BOOLEAN | NUMBER -> NUMBER | TRUE -> TRUE | FALSE -> FALSE | NULL -> NULL );
    public final OSFParser.value_return value() throws RecognitionException {
        OSFParser.value_return retval = new OSFParser.value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token TEXT33=null;
        Token BOOLEAN34=null;
        Token NUMBER35=null;
        Token TRUE36=null;
        Token FALSE37=null;
        Token NULL38=null;
        OSFParser.string_return string30 = null;

        OSFParser.number_return number31 = null;

        OSFParser.array_return array32 = null;


        Object TEXT33_tree=null;
        Object BOOLEAN34_tree=null;
        Object NUMBER35_tree=null;
        Object TRUE36_tree=null;
        Object FALSE37_tree=null;
        Object NULL38_tree=null;
        RewriteRuleTokenStream stream_BOOLEAN=new RewriteRuleTokenStream(adaptor,"token BOOLEAN");
        RewriteRuleTokenStream stream_TEXT=new RewriteRuleTokenStream(adaptor,"token TEXT");
        RewriteRuleTokenStream stream_FALSE=new RewriteRuleTokenStream(adaptor,"token FALSE");
        RewriteRuleTokenStream stream_TRUE=new RewriteRuleTokenStream(adaptor,"token TRUE");
        RewriteRuleTokenStream stream_NULL=new RewriteRuleTokenStream(adaptor,"token NULL");
        RewriteRuleTokenStream stream_NUMBER=new RewriteRuleTokenStream(adaptor,"token NUMBER");

        try {
            // de/lichtflut/rb/core/schema/OSF.g:116:2: ( string | number | array | TEXT -> TEXT | BOOLEAN -> BOOLEAN | NUMBER -> NUMBER | TRUE -> TRUE | FALSE -> FALSE | NULL -> NULL )
            int alt10=9;
            switch ( input.LA(1) ) {
            case String:
                {
                alt10=1;
                }
                break;
            case Number:
                {
                alt10=2;
                }
                break;
            case 31:
                {
                alt10=3;
                }
                break;
            case TEXT:
                {
                alt10=4;
                }
                break;
            case BOOLEAN:
                {
                alt10=5;
                }
                break;
            case NUMBER:
                {
                alt10=6;
                }
                break;
            case TRUE:
                {
                alt10=7;
                }
                break;
            case FALSE:
                {
                alt10=8;
                }
                break;
            case NULL:
                {
                alt10=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSF.g:116:4: string
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_in_value348);
                    string30=string();

                    state._fsp--;

                    adaptor.addChild(root_0, string30.getTree());

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/OSF.g:117:4: number
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_number_in_value353);
                    number31=number();

                    state._fsp--;

                    adaptor.addChild(root_0, number31.getTree());

                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/OSF.g:118:4: array
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_array_in_value358);
                    array32=array();

                    state._fsp--;

                    adaptor.addChild(root_0, array32.getTree());

                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/OSF.g:119:4: TEXT
                    {
                    TEXT33=(Token)match(input,TEXT,FOLLOW_TEXT_in_value363);  
                    stream_TEXT.add(TEXT33);



                    // AST REWRITE
                    // elements: TEXT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 119:9: -> TEXT
                    {
                        adaptor.addChild(root_0, stream_TEXT.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/OSF.g:120:4: BOOLEAN
                    {
                    BOOLEAN34=(Token)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_value372);  
                    stream_BOOLEAN.add(BOOLEAN34);



                    // AST REWRITE
                    // elements: BOOLEAN
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 120:12: -> BOOLEAN
                    {
                        adaptor.addChild(root_0, stream_BOOLEAN.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // de/lichtflut/rb/core/schema/OSF.g:121:4: NUMBER
                    {
                    NUMBER35=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_value381);  
                    stream_NUMBER.add(NUMBER35);



                    // AST REWRITE
                    // elements: NUMBER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 121:11: -> NUMBER
                    {
                        adaptor.addChild(root_0, stream_NUMBER.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 7 :
                    // de/lichtflut/rb/core/schema/OSF.g:122:4: TRUE
                    {
                    TRUE36=(Token)match(input,TRUE,FOLLOW_TRUE_in_value390);  
                    stream_TRUE.add(TRUE36);



                    // AST REWRITE
                    // elements: TRUE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 122:9: -> TRUE
                    {
                        adaptor.addChild(root_0, stream_TRUE.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 8 :
                    // de/lichtflut/rb/core/schema/OSF.g:123:4: FALSE
                    {
                    FALSE37=(Token)match(input,FALSE,FOLLOW_FALSE_in_value399);  
                    stream_FALSE.add(FALSE37);



                    // AST REWRITE
                    // elements: FALSE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 123:10: -> FALSE
                    {
                        adaptor.addChild(root_0, stream_FALSE.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 9 :
                    // de/lichtflut/rb/core/schema/OSF.g:124:4: NULL
                    {
                    NULL38=(Token)match(input,NULL,FOLLOW_NULL_in_value408);  
                    stream_NULL.add(NULL38);



                    // AST REWRITE
                    // elements: NULL
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 124:9: -> NULL
                    {
                        adaptor.addChild(root_0, stream_NULL.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "value"

    public static class string_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "string"
    // de/lichtflut/rb/core/schema/OSF.g:128:1: string : String -> ^( STRING String ) ;
    public final OSFParser.string_return string() throws RecognitionException {
        OSFParser.string_return retval = new OSFParser.string_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token String39=null;

        Object String39_tree=null;
        RewriteRuleTokenStream stream_String=new RewriteRuleTokenStream(adaptor,"token String");

        try {
            // de/lichtflut/rb/core/schema/OSF.g:128:9: ( String -> ^( STRING String ) )
            // de/lichtflut/rb/core/schema/OSF.g:128:11: String
            {
            String39=(Token)match(input,String,FOLLOW_String_in_string424);  
            stream_String.add(String39);



            // AST REWRITE
            // elements: String
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 129:4: -> ^( STRING String )
            {
                // de/lichtflut/rb/core/schema/OSF.g:129:7: ^( STRING String )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STRING, "STRING"), root_1);

                adaptor.addChild(root_1, stream_String.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "string"

    public static class number_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "number"
    // de/lichtflut/rb/core/schema/OSF.g:132:1: number : Number -> ^( NUMBER Number ) ;
    public final OSFParser.number_return number() throws RecognitionException {
        OSFParser.number_return retval = new OSFParser.number_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token Number40=null;

        Object Number40_tree=null;
        RewriteRuleTokenStream stream_Number=new RewriteRuleTokenStream(adaptor,"token Number");

        try {
            // de/lichtflut/rb/core/schema/OSF.g:132:8: ( Number -> ^( NUMBER Number ) )
            // de/lichtflut/rb/core/schema/OSF.g:132:10: Number
            {
            Number40=(Token)match(input,Number,FOLLOW_Number_in_number445);  
            stream_Number.add(Number40);



            // AST REWRITE
            // elements: Number
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 132:17: -> ^( NUMBER Number )
            {
                // de/lichtflut/rb/core/schema/OSF.g:132:20: ^( NUMBER Number )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NUMBER, "NUMBER"), root_1);

                adaptor.addChild(root_1, stream_Number.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "number"

    public static class array_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "array"
    // de/lichtflut/rb/core/schema/OSF.g:136:1: array : '[' elements ']' -> ^( ARRAY elements ) ;
    public final OSFParser.array_return array() throws RecognitionException {
        OSFParser.array_return retval = new OSFParser.array_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal41=null;
        Token char_literal43=null;
        OSFParser.elements_return elements42 = null;


        Object char_literal41_tree=null;
        Object char_literal43_tree=null;
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleSubtreeStream stream_elements=new RewriteRuleSubtreeStream(adaptor,"rule elements");
        try {
            // de/lichtflut/rb/core/schema/OSF.g:136:7: ( '[' elements ']' -> ^( ARRAY elements ) )
            // de/lichtflut/rb/core/schema/OSF.g:136:9: '[' elements ']'
            {
            char_literal41=(Token)match(input,31,FOLLOW_31_in_array464);  
            stream_31.add(char_literal41);

            pushFollow(FOLLOW_elements_in_array466);
            elements42=elements();

            state._fsp--;

            stream_elements.add(elements42.getTree());
            char_literal43=(Token)match(input,32,FOLLOW_32_in_array468);  
            stream_32.add(char_literal43);



            // AST REWRITE
            // elements: elements
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 136:26: -> ^( ARRAY elements )
            {
                // de/lichtflut/rb/core/schema/OSF.g:136:29: ^( ARRAY elements )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ARRAY, "ARRAY"), root_1);

                adaptor.addChild(root_1, stream_elements.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "array"

    public static class elements_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "elements"
    // de/lichtflut/rb/core/schema/OSF.g:139:1: elements : value ( COMMA value )* ;
    public final OSFParser.elements_return elements() throws RecognitionException {
        OSFParser.elements_return retval = new OSFParser.elements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA45=null;
        OSFParser.value_return value44 = null;

        OSFParser.value_return value46 = null;


        Object COMMA45_tree=null;

        try {
            // de/lichtflut/rb/core/schema/OSF.g:139:9: ( value ( COMMA value )* )
            // de/lichtflut/rb/core/schema/OSF.g:139:11: value ( COMMA value )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_value_in_elements485);
            value44=value();

            state._fsp--;

            adaptor.addChild(root_0, value44.getTree());
            // de/lichtflut/rb/core/schema/OSF.g:139:17: ( COMMA value )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==COMMA) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/OSF.g:139:18: COMMA value
            	    {
            	    COMMA45=(Token)match(input,COMMA,FOLLOW_COMMA_in_elements488); 
            	    pushFollow(FOLLOW_value_in_elements491);
            	    value46=value();

            	    state._fsp--;

            	    adaptor.addChild(root_0, value46.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        catch (RecognitionException e) {
        throw e;
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "elements"

    // Delegated rules


 

    public static final BitSet FOLLOW_descriptions_in_osl119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_description_in_descriptions141 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_COMMA_in_descriptions144 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_description_in_descriptions147 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_property_dec_in_description163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_dec_in_description176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_in_property_dec201 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_String_in_property_dec205 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_property_dec210 = new BitSet(new long[]{0x0000000040007C00L});
    public static final BitSet FOLLOW_p_assertion_in_property_dec216 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_property_dec218 = new BitSet(new long[]{0x00000000806381A0L});
    public static final BitSet FOLLOW_value_in_property_dec221 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_COMMA_in_property_dec223 = new BitSet(new long[]{0x0000000000007C00L});
    public static final BitSet FOLLOW_p_assertion_in_property_dec226 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_property_dec228 = new BitSet(new long[]{0x00000000806381A0L});
    public static final BitSet FOLLOW_value_in_property_dec231 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_30_in_property_dec239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RESOURCE_in_resource_dec254 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_String_in_resource_dec258 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_resource_dec262 = new BitSet(new long[]{0x0000000040040000L});
    public static final BitSet FOLLOW_property_dec_in_resource_dec268 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_COMMA_in_resource_dec271 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_property_dec_in_resource_dec273 = new BitSet(new long[]{0x0000000040000200L});
    public static final BitSet FOLLOW_30_in_resource_dec281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TYPE_in_p_assertion294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MAX_in_p_assertion303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIN_in_p_assertion313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REGEX_in_p_assertion323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CARDINALITY_in_p_assertion332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_in_value348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_number_in_value353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_in_value358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TEXT_in_value363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOLEAN_in_value372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_value381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_value390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_value399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_value408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_String_in_string424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Number_in_number445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_array464 = new BitSet(new long[]{0x00000000806381A0L});
    public static final BitSet FOLLOW_elements_in_array466 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_array468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_elements485 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_COMMA_in_elements488 = new BitSet(new long[]{0x00000000806381A0L});
    public static final BitSet FOLLOW_value_in_elements491 = new BitSet(new long[]{0x0000000000000202L});

}