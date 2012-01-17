// $ANTLR 3.4 /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g 2012-01-17 19:37:32

/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import de.lichtflut.rb.core.schema.model.*;
import de.lichtflut.rb.core.schema.model.impl.*;

import de.lichtflut.rb.core.schema.parser.RSErrorReporter;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class RSFParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BOOLEAN", "CARDINALITY", "CONSTRAINT", "DATE", "DECIMAL", "Digit", "EscapeSequence", "FIELD_LABEL", "FOR", "HexDigit", "INTEGER", "LABEL_RULE", "MAX", "MIN", "NAMESPACE", "NUMBER", "Number", "PREFIX", "PROPERTY", "RESOURCE", "SCHEMA", "STRING", "String", "TEXT", "TYPE", "TYPE_DEFINITION", "UnicodeEscape", "WS", "':'", "'['", "']'", "'{'", "'}'"
    };

    public static final int EOF=-1;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int BOOLEAN=4;
    public static final int CARDINALITY=5;
    public static final int CONSTRAINT=6;
    public static final int DATE=7;
    public static final int DECIMAL=8;
    public static final int Digit=9;
    public static final int EscapeSequence=10;
    public static final int FIELD_LABEL=11;
    public static final int FOR=12;
    public static final int HexDigit=13;
    public static final int INTEGER=14;
    public static final int LABEL_RULE=15;
    public static final int MAX=16;
    public static final int MIN=17;
    public static final int NAMESPACE=18;
    public static final int NUMBER=19;
    public static final int Number=20;
    public static final int PREFIX=21;
    public static final int PROPERTY=22;
    public static final int RESOURCE=23;
    public static final int SCHEMA=24;
    public static final int STRING=25;
    public static final int String=26;
    public static final int TEXT=27;
    public static final int TYPE=28;
    public static final int TYPE_DEFINITION=29;
    public static final int UnicodeEscape=30;
    public static final int WS=31;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public RSFParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public RSFParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return RSFParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g"; }



    	private RSErrorReporter errorReporter = null;
        public void setErrorReporter(RSErrorReporter errorReporter) {
            this.errorReporter = errorReporter;
        }
        public void emitErrorMessage(String msg) {
            errorReporter.reportError(msg);
        }



    public static class declarations_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "declarations"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:63:1: declarations : ( decl )+ EOF ;
    public final RSFParser.declarations_return declarations() throws RecognitionException {
        RSFParser.declarations_return retval = new RSFParser.declarations_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token EOF2=null;
        RSFParser.decl_return decl1 =null;


        CommonTree EOF2_tree=null;

        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:63:15: ( ( decl )+ EOF )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:63:17: ( decl )+ EOF
            {
            root_0 = (CommonTree)adaptor.nil();


            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:63:17: ( decl )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==NAMESPACE||LA1_0==SCHEMA) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:63:17: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_declarations129);
            	    decl1=decl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, decl1.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_declarations132); 
            EOF2_tree = 
            (CommonTree)adaptor.create(EOF2)
            ;
            adaptor.addChild(root_0, EOF2_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "declarations"


    public static class decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "decl"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:66:1: decl : ( namespace_decl -> ^( NAMESPACE namespace_decl ) | schema_decl -> ^( SCHEMA schema_decl ) );
    public final RSFParser.decl_return decl() throws RecognitionException {
        RSFParser.decl_return retval = new RSFParser.decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        RSFParser.namespace_decl_return namespace_decl3 =null;

        RSFParser.schema_decl_return schema_decl4 =null;


        RewriteRuleSubtreeStream stream_namespace_decl=new RewriteRuleSubtreeStream(adaptor,"rule namespace_decl");
        RewriteRuleSubtreeStream stream_schema_decl=new RewriteRuleSubtreeStream(adaptor,"rule schema_decl");
        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:66:7: ( namespace_decl -> ^( NAMESPACE namespace_decl ) | schema_decl -> ^( SCHEMA schema_decl ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NAMESPACE) ) {
                alt2=1;
            }
            else if ( (LA2_0==SCHEMA) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:67:2: namespace_decl
                    {
                    pushFollow(FOLLOW_namespace_decl_in_decl143);
                    namespace_decl3=namespace_decl();

                    state._fsp--;

                    stream_namespace_decl.add(namespace_decl3.getTree());

                    // AST REWRITE
                    // elements: namespace_decl
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 67:17: -> ^( NAMESPACE namespace_decl )
                    {
                        // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:67:20: ^( NAMESPACE namespace_decl )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(NAMESPACE, "NAMESPACE")
                        , root_1);

                        adaptor.addChild(root_1, stream_namespace_decl.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:68:4: schema_decl
                    {
                    pushFollow(FOLLOW_schema_decl_in_decl156);
                    schema_decl4=schema_decl();

                    state._fsp--;

                    stream_schema_decl.add(schema_decl4.getTree());

                    // AST REWRITE
                    // elements: schema_decl
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 68:16: -> ^( SCHEMA schema_decl )
                    {
                        // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:68:19: ^( SCHEMA schema_decl )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(SCHEMA, "SCHEMA")
                        , root_1);

                        adaptor.addChild(root_1, stream_schema_decl.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "decl"


    public static class namespace_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "namespace_decl"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:71:1: namespace_decl : NAMESPACE string PREFIX string ;
    public final RSFParser.namespace_decl_return namespace_decl() throws RecognitionException {
        RSFParser.namespace_decl_return retval = new RSFParser.namespace_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token NAMESPACE5=null;
        Token PREFIX7=null;
        RSFParser.string_return string6 =null;

        RSFParser.string_return string8 =null;


        CommonTree NAMESPACE5_tree=null;
        CommonTree PREFIX7_tree=null;

        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:72:4: ( NAMESPACE string PREFIX string )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:72:6: NAMESPACE string PREFIX string
            {
            root_0 = (CommonTree)adaptor.nil();


            NAMESPACE5=(Token)match(input,NAMESPACE,FOLLOW_NAMESPACE_in_namespace_decl180); 
            NAMESPACE5_tree = 
            (CommonTree)adaptor.create(NAMESPACE5)
            ;
            adaptor.addChild(root_0, NAMESPACE5_tree);


            pushFollow(FOLLOW_string_in_namespace_decl182);
            string6=string();

            state._fsp--;

            adaptor.addChild(root_0, string6.getTree());

            PREFIX7=(Token)match(input,PREFIX,FOLLOW_PREFIX_in_namespace_decl184); 
            PREFIX7_tree = 
            (CommonTree)adaptor.create(PREFIX7)
            ;
            adaptor.addChild(root_0, PREFIX7_tree);


            pushFollow(FOLLOW_string_in_namespace_decl186);
            string8=string();

            state._fsp--;

            adaptor.addChild(root_0, string8.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "namespace_decl"


    public static class schema_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "schema_decl"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:74:1: schema_decl : SCHEMA FOR string '{' ( property_decl )* '}' ;
    public final RSFParser.schema_decl_return schema_decl() throws RecognitionException {
        RSFParser.schema_decl_return retval = new RSFParser.schema_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token SCHEMA9=null;
        Token FOR10=null;
        Token char_literal12=null;
        Token char_literal14=null;
        RSFParser.string_return string11 =null;

        RSFParser.property_decl_return property_decl13 =null;


        CommonTree SCHEMA9_tree=null;
        CommonTree FOR10_tree=null;
        CommonTree char_literal12_tree=null;
        CommonTree char_literal14_tree=null;

        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:75:3: ( SCHEMA FOR string '{' ( property_decl )* '}' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:75:5: SCHEMA FOR string '{' ( property_decl )* '}'
            {
            root_0 = (CommonTree)adaptor.nil();


            SCHEMA9=(Token)match(input,SCHEMA,FOLLOW_SCHEMA_in_schema_decl197); 
            SCHEMA9_tree = 
            (CommonTree)adaptor.create(SCHEMA9)
            ;
            adaptor.addChild(root_0, SCHEMA9_tree);


            FOR10=(Token)match(input,FOR,FOLLOW_FOR_in_schema_decl199); 
            FOR10_tree = 
            (CommonTree)adaptor.create(FOR10)
            ;
            adaptor.addChild(root_0, FOR10_tree);


            pushFollow(FOLLOW_string_in_schema_decl201);
            string11=string();

            state._fsp--;

            adaptor.addChild(root_0, string11.getTree());

            char_literal12=(Token)match(input,35,FOLLOW_35_in_schema_decl206); 
            char_literal12_tree = 
            (CommonTree)adaptor.create(char_literal12)
            ;
            adaptor.addChild(root_0, char_literal12_tree);


            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:77:4: ( property_decl )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==PROPERTY) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:77:4: property_decl
            	    {
            	    pushFollow(FOLLOW_property_decl_in_schema_decl211);
            	    property_decl13=property_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, property_decl13.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            char_literal14=(Token)match(input,36,FOLLOW_36_in_schema_decl217); 
            char_literal14_tree = 
            (CommonTree)adaptor.create(char_literal14)
            ;
            adaptor.addChild(root_0, char_literal14_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "schema_decl"


    public static class property_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "property_decl"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:80:1: property_decl : PROPERTY string cardinality '{' assignment '}' ;
    public final RSFParser.property_decl_return property_decl() throws RecognitionException {
        RSFParser.property_decl_return retval = new RSFParser.property_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token PROPERTY15=null;
        Token char_literal18=null;
        Token char_literal20=null;
        RSFParser.string_return string16 =null;

        RSFParser.cardinality_return cardinality17 =null;

        RSFParser.assignment_return assignment19 =null;


        CommonTree PROPERTY15_tree=null;
        CommonTree char_literal18_tree=null;
        CommonTree char_literal20_tree=null;

        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:81:2: ( PROPERTY string cardinality '{' assignment '}' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:81:4: PROPERTY string cardinality '{' assignment '}'
            {
            root_0 = (CommonTree)adaptor.nil();


            PROPERTY15=(Token)match(input,PROPERTY,FOLLOW_PROPERTY_in_property_decl227); 
            PROPERTY15_tree = 
            (CommonTree)adaptor.create(PROPERTY15)
            ;
            adaptor.addChild(root_0, PROPERTY15_tree);


            pushFollow(FOLLOW_string_in_property_decl231);
            string16=string();

            state._fsp--;

            adaptor.addChild(root_0, string16.getTree());

            pushFollow(FOLLOW_cardinality_in_property_decl235);
            cardinality17=cardinality();

            state._fsp--;

            adaptor.addChild(root_0, cardinality17.getTree());

            char_literal18=(Token)match(input,35,FOLLOW_35_in_property_decl239); 
            char_literal18_tree = 
            (CommonTree)adaptor.create(char_literal18)
            ;
            adaptor.addChild(root_0, char_literal18_tree);


            pushFollow(FOLLOW_assignment_in_property_decl244);
            assignment19=assignment();

            state._fsp--;

            adaptor.addChild(root_0, assignment19.getTree());

            char_literal20=(Token)match(input,36,FOLLOW_36_in_property_decl248); 
            char_literal20_tree = 
            (CommonTree)adaptor.create(char_literal20)
            ;
            adaptor.addChild(root_0, char_literal20_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "property_decl"


    public static class cardinality_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cardinality"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:89:1: cardinality : '[' string ']' ;
    public final RSFParser.cardinality_return cardinality() throws RecognitionException {
        RSFParser.cardinality_return retval = new RSFParser.cardinality_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal21=null;
        Token char_literal23=null;
        RSFParser.string_return string22 =null;


        CommonTree char_literal21_tree=null;
        CommonTree char_literal23_tree=null;

        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:91:2: ( '[' string ']' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:91:4: '[' string ']'
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal21=(Token)match(input,33,FOLLOW_33_in_cardinality261); 
            char_literal21_tree = 
            (CommonTree)adaptor.create(char_literal21)
            ;
            adaptor.addChild(root_0, char_literal21_tree);


            pushFollow(FOLLOW_string_in_cardinality263);
            string22=string();

            state._fsp--;

            adaptor.addChild(root_0, string22.getTree());

            char_literal23=(Token)match(input,34,FOLLOW_34_in_cardinality265); 
            char_literal23_tree = 
            (CommonTree)adaptor.create(char_literal23)
            ;
            adaptor.addChild(root_0, char_literal23_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "cardinality"


    public static class assignment_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "assignment"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:93:1: assignment : ( key ':' value )* ;
    public final RSFParser.assignment_return assignment() throws RecognitionException {
        RSFParser.assignment_return retval = new RSFParser.assignment_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal25=null;
        RSFParser.key_return key24 =null;

        RSFParser.value_return value26 =null;


        CommonTree char_literal25_tree=null;

        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:94:2: ( ( key ':' value )* )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:94:4: ( key ':' value )*
            {
            root_0 = (CommonTree)adaptor.nil();


            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:94:4: ( key ':' value )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==CONSTRAINT||LA4_0==FIELD_LABEL||LA4_0==TYPE_DEFINITION) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:94:5: key ':' value
            	    {
            	    pushFollow(FOLLOW_key_in_assignment277);
            	    key24=key();

            	    state._fsp--;

            	    adaptor.addChild(root_0, key24.getTree());

            	    char_literal25=(Token)match(input,32,FOLLOW_32_in_assignment279); 
            	    char_literal25_tree = 
            	    (CommonTree)adaptor.create(char_literal25)
            	    ;
            	    adaptor.addChild(root_0, char_literal25_tree);


            	    pushFollow(FOLLOW_value_in_assignment281);
            	    value26=value();

            	    state._fsp--;

            	    adaptor.addChild(root_0, value26.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "assignment"


    public static class key_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "key"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:96:1: key : ( FIELD_LABEL -> FIELD_LABEL | TYPE_DEFINITION -> TYPE_DEFINITION | CONSTRAINT -> CONSTRAINT );
    public final RSFParser.key_return key() throws RecognitionException {
        RSFParser.key_return retval = new RSFParser.key_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token FIELD_LABEL27=null;
        Token TYPE_DEFINITION28=null;
        Token CONSTRAINT29=null;

        CommonTree FIELD_LABEL27_tree=null;
        CommonTree TYPE_DEFINITION28_tree=null;
        CommonTree CONSTRAINT29_tree=null;
        RewriteRuleTokenStream stream_TYPE_DEFINITION=new RewriteRuleTokenStream(adaptor,"token TYPE_DEFINITION");
        RewriteRuleTokenStream stream_FIELD_LABEL=new RewriteRuleTokenStream(adaptor,"token FIELD_LABEL");
        RewriteRuleTokenStream stream_CONSTRAINT=new RewriteRuleTokenStream(adaptor,"token CONSTRAINT");

        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:97:2: ( FIELD_LABEL -> FIELD_LABEL | TYPE_DEFINITION -> TYPE_DEFINITION | CONSTRAINT -> CONSTRAINT )
            int alt5=3;
            switch ( input.LA(1) ) {
            case FIELD_LABEL:
                {
                alt5=1;
                }
                break;
            case TYPE_DEFINITION:
                {
                alt5=2;
                }
                break;
            case CONSTRAINT:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }

            switch (alt5) {
                case 1 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:97:4: FIELD_LABEL
                    {
                    FIELD_LABEL27=(Token)match(input,FIELD_LABEL,FOLLOW_FIELD_LABEL_in_key294);  
                    stream_FIELD_LABEL.add(FIELD_LABEL27);


                    // AST REWRITE
                    // elements: FIELD_LABEL
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 97:16: -> FIELD_LABEL
                    {
                        adaptor.addChild(root_0, 
                        stream_FIELD_LABEL.nextNode()
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:98:4: TYPE_DEFINITION
                    {
                    TYPE_DEFINITION28=(Token)match(input,TYPE_DEFINITION,FOLLOW_TYPE_DEFINITION_in_key303);  
                    stream_TYPE_DEFINITION.add(TYPE_DEFINITION28);


                    // AST REWRITE
                    // elements: TYPE_DEFINITION
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 98:21: -> TYPE_DEFINITION
                    {
                        adaptor.addChild(root_0, 
                        stream_TYPE_DEFINITION.nextNode()
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:99:4: CONSTRAINT
                    {
                    CONSTRAINT29=(Token)match(input,CONSTRAINT,FOLLOW_CONSTRAINT_in_key313);  
                    stream_CONSTRAINT.add(CONSTRAINT29);


                    // AST REWRITE
                    // elements: CONSTRAINT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 99:15: -> CONSTRAINT
                    {
                        adaptor.addChild(root_0, 
                        stream_CONSTRAINT.nextNode()
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "key"


    public static class value_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "value"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:101:1: value : string ;
    public final RSFParser.value_return value() throws RecognitionException {
        RSFParser.value_return retval = new RSFParser.value_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        RSFParser.string_return string30 =null;



        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:103:2: ( string )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:103:4: string
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_string_in_value327);
            string30=string();

            state._fsp--;

            adaptor.addChild(root_0, string30.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "value"


    public static class string_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "string"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:106:1: string : String -> ^( STRING String ) ;
    public final RSFParser.string_return string() throws RecognitionException {
        RSFParser.string_return retval = new RSFParser.string_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token String31=null;

        CommonTree String31_tree=null;
        RewriteRuleTokenStream stream_String=new RewriteRuleTokenStream(adaptor,"token String");

        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:106:9: ( String -> ^( STRING String ) )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:106:11: String
            {
            String31=(Token)match(input,String,FOLLOW_String_in_string337);  
            stream_String.add(String31);


            // AST REWRITE
            // elements: String
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 107:4: -> ^( STRING String )
            {
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:107:7: ^( STRING String )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(STRING, "STRING")
                , root_1);

                adaptor.addChild(root_1, 
                stream_String.nextNode()
                );

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "string"


    public static class number_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "number"
    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:110:1: number : Number -> ^( NUMBER Number ) ;
    public final RSFParser.number_return number() throws RecognitionException {
        RSFParser.number_return retval = new RSFParser.number_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token Number32=null;

        CommonTree Number32_tree=null;
        RewriteRuleTokenStream stream_Number=new RewriteRuleTokenStream(adaptor,"token Number");

        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:110:8: ( Number -> ^( NUMBER Number ) )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:110:10: Number
            {
            Number32=(Token)match(input,Number,FOLLOW_Number_in_number358);  
            stream_Number.add(Number32);


            // AST REWRITE
            // elements: Number
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 110:17: -> ^( NUMBER Number )
            {
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:110:20: ^( NUMBER Number )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(NUMBER, "NUMBER")
                , root_1);

                adaptor.addChild(root_1, 
                stream_Number.nextNode()
                );

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "number"

    // Delegated rules


 

    public static final BitSet FOLLOW_decl_in_declarations129 = new BitSet(new long[]{0x0000000001040000L});
    public static final BitSet FOLLOW_EOF_in_declarations132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespace_decl_in_decl143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_schema_decl_in_decl156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAMESPACE_in_namespace_decl180 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_string_in_namespace_decl182 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_PREFIX_in_namespace_decl184 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_string_in_namespace_decl186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SCHEMA_in_schema_decl197 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_FOR_in_schema_decl199 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_string_in_schema_decl201 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_schema_decl206 = new BitSet(new long[]{0x0000001000400000L});
    public static final BitSet FOLLOW_property_decl_in_schema_decl211 = new BitSet(new long[]{0x0000001000400000L});
    public static final BitSet FOLLOW_36_in_schema_decl217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_in_property_decl227 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_string_in_property_decl231 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_cardinality_in_property_decl235 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_property_decl239 = new BitSet(new long[]{0x0000001020000840L});
    public static final BitSet FOLLOW_assignment_in_property_decl244 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_36_in_property_decl248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_cardinality261 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_string_in_cardinality263 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_cardinality265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_key_in_assignment277 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_assignment279 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_value_in_assignment281 = new BitSet(new long[]{0x0000000020000842L});
    public static final BitSet FOLLOW_FIELD_LABEL_in_key294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TYPE_DEFINITION_in_key303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTRAINT_in_key313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_in_value327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_String_in_string337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Number_in_number358 = new BitSet(new long[]{0x0000000000000002L});

}