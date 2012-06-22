// $ANTLR 3.4 RSF.g 2012-06-21 22:06:24

/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.parser.impl.rsf;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class RSFParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "APPLICABLE_DATATYPES", "ASSIGMENT", "CARDINALITY", "CARDINALITY_DECL", "COLON", "CONSTRAINT_FOR", "CONSTRAINT_REFERENCE", "DATATYPE", "FIELD_LABEL", "INT_LABEL", "LABEL", "LABEL_RULE", "LITERAL_CONSTRAINT", "NAME", "NAMESPACE", "NS", "PREFIX", "PROPERTY", "PROPERTY_DECL", "PUBLIC_CONSTRAINT", "RESOURCE_CONSTRAINT", "SCHEMA", "SCHEMA_FOR", "STATEMENTS", "STRING", "WS", "'{'", "'}'"
    };

    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int APPLICABLE_DATATYPES=4;
    public static final int ASSIGMENT=5;
    public static final int CARDINALITY=6;
    public static final int CARDINALITY_DECL=7;
    public static final int COLON=8;
    public static final int CONSTRAINT_FOR=9;
    public static final int CONSTRAINT_REFERENCE=10;
    public static final int DATATYPE=11;
    public static final int FIELD_LABEL=12;
    public static final int INT_LABEL=13;
    public static final int LABEL=14;
    public static final int LABEL_RULE=15;
    public static final int LITERAL_CONSTRAINT=16;
    public static final int NAME=17;
    public static final int NAMESPACE=18;
    public static final int NS=19;
    public static final int PREFIX=20;
    public static final int PROPERTY=21;
    public static final int PROPERTY_DECL=22;
    public static final int PUBLIC_CONSTRAINT=23;
    public static final int RESOURCE_CONSTRAINT=24;
    public static final int SCHEMA=25;
    public static final int SCHEMA_FOR=26;
    public static final int STATEMENTS=27;
    public static final int STRING=28;
    public static final int WS=29;

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
    public String getGrammarFileName() { return "RSF.g"; }


    public static class statements_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "statements"
    // RSF.g:36:1: statements : ( statement )+ -> ^( STATEMENTS ( statement )+ ) ;
    public final RSFParser.statements_return statements() throws RecognitionException {
        RSFParser.statements_return retval = new RSFParser.statements_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        RSFParser.statement_return statement1 =null;


        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // RSF.g:36:12: ( ( statement )+ -> ^( STATEMENTS ( statement )+ ) )
            // RSF.g:36:14: ( statement )+
            {
            // RSF.g:36:14: ( statement )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==CONSTRAINT_FOR||LA1_0==NS||LA1_0==SCHEMA_FOR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // RSF.g:36:14: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_statements83);
            	    statement1=statement();

            	    state._fsp--;

            	    stream_statement.add(statement1.getTree());

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


            // AST REWRITE
            // elements: statement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 36:26: -> ^( STATEMENTS ( statement )+ )
            {
                // RSF.g:36:29: ^( STATEMENTS ( statement )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(STATEMENTS, "STATEMENTS")
                , root_1);

                if ( !(stream_statement.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_statement.hasNext() ) {
                    adaptor.addChild(root_1, stream_statement.nextTree());

                }
                stream_statement.reset();

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
    // $ANTLR end "statements"


    public static class statement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "statement"
    // RSF.g:39:1: statement : ( namespace_decl | public_constraint | schema_decl );
    public final RSFParser.statement_return statement() throws RecognitionException {
        RSFParser.statement_return retval = new RSFParser.statement_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        RSFParser.namespace_decl_return namespace_decl2 =null;

        RSFParser.public_constraint_return public_constraint3 =null;

        RSFParser.schema_decl_return schema_decl4 =null;



        try {
            // RSF.g:39:11: ( namespace_decl | public_constraint | schema_decl )
            int alt2=3;
            switch ( input.LA(1) ) {
            case NS:
                {
                alt2=1;
                }
                break;
            case CONSTRAINT_FOR:
                {
                alt2=2;
                }
                break;
            case SCHEMA_FOR:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }

            switch (alt2) {
                case 1 :
                    // RSF.g:39:14: namespace_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_namespace_decl_in_statement104);
                    namespace_decl2=namespace_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, namespace_decl2.getTree());

                    }
                    break;
                case 2 :
                    // RSF.g:40:6: public_constraint
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_public_constraint_in_statement111);
                    public_constraint3=public_constraint();

                    state._fsp--;

                    adaptor.addChild(root_0, public_constraint3.getTree());

                    }
                    break;
                case 3 :
                    // RSF.g:41:7: schema_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_schema_decl_in_statement119);
                    schema_decl4=schema_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, schema_decl4.getTree());

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
    // $ANTLR end "statement"


    public static class namespace_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "namespace_decl"
    // RSF.g:45:1: namespace_decl : NS e= STRING PREFIX f= STRING -> ^( NAMESPACE $e $f) ;
    public final RSFParser.namespace_decl_return namespace_decl() throws RecognitionException {
        RSFParser.namespace_decl_return retval = new RSFParser.namespace_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token e=null;
        Token f=null;
        Token NS5=null;
        Token PREFIX6=null;

        CommonTree e_tree=null;
        CommonTree f_tree=null;
        CommonTree NS5_tree=null;
        CommonTree PREFIX6_tree=null;
        RewriteRuleTokenStream stream_PREFIX=new RewriteRuleTokenStream(adaptor,"token PREFIX");
        RewriteRuleTokenStream stream_NS=new RewriteRuleTokenStream(adaptor,"token NS");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // RSF.g:45:15: ( NS e= STRING PREFIX f= STRING -> ^( NAMESPACE $e $f) )
            // RSF.g:45:17: NS e= STRING PREFIX f= STRING
            {
            NS5=(Token)match(input,NS,FOLLOW_NS_in_namespace_decl132);  
            stream_NS.add(NS5);


            e=(Token)match(input,STRING,FOLLOW_STRING_in_namespace_decl136);  
            stream_STRING.add(e);


            PREFIX6=(Token)match(input,PREFIX,FOLLOW_PREFIX_in_namespace_decl138);  
            stream_PREFIX.add(PREFIX6);


            f=(Token)match(input,STRING,FOLLOW_STRING_in_namespace_decl142);  
            stream_STRING.add(f);


            // AST REWRITE
            // elements: e, f
            // token labels: f, e
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_f=new RewriteRuleTokenStream(adaptor,"token f",f);
            RewriteRuleTokenStream stream_e=new RewriteRuleTokenStream(adaptor,"token e",e);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 45:45: -> ^( NAMESPACE $e $f)
            {
                // RSF.g:45:48: ^( NAMESPACE $e $f)
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(NAMESPACE, "NAMESPACE")
                , root_1);

                adaptor.addChild(root_1, stream_e.nextNode());

                adaptor.addChild(root_1, stream_f.nextNode());

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
    // $ANTLR end "namespace_decl"


    public static class public_constraint_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "public_constraint"
    // RSF.g:48:1: public_constraint : CONSTRAINT_FOR s= STRING '{' ( assigment )+ '}' -> ^( PUBLIC_CONSTRAINT $s ( assigment )+ ) ;
    public final RSFParser.public_constraint_return public_constraint() throws RecognitionException {
        RSFParser.public_constraint_return retval = new RSFParser.public_constraint_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token s=null;
        Token CONSTRAINT_FOR7=null;
        Token char_literal8=null;
        Token char_literal10=null;
        RSFParser.assigment_return assigment9 =null;


        CommonTree s_tree=null;
        CommonTree CONSTRAINT_FOR7_tree=null;
        CommonTree char_literal8_tree=null;
        CommonTree char_literal10_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleTokenStream stream_CONSTRAINT_FOR=new RewriteRuleTokenStream(adaptor,"token CONSTRAINT_FOR");
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_assigment=new RewriteRuleSubtreeStream(adaptor,"rule assigment");
        try {
            // RSF.g:48:19: ( CONSTRAINT_FOR s= STRING '{' ( assigment )+ '}' -> ^( PUBLIC_CONSTRAINT $s ( assigment )+ ) )
            // RSF.g:48:21: CONSTRAINT_FOR s= STRING '{' ( assigment )+ '}'
            {
            CONSTRAINT_FOR7=(Token)match(input,CONSTRAINT_FOR,FOLLOW_CONSTRAINT_FOR_in_public_constraint164);  
            stream_CONSTRAINT_FOR.add(CONSTRAINT_FOR7);


            s=(Token)match(input,STRING,FOLLOW_STRING_in_public_constraint168);  
            stream_STRING.add(s);


            char_literal8=(Token)match(input,30,FOLLOW_30_in_public_constraint170);  
            stream_30.add(char_literal8);


            // RSF.g:49:7: ( assigment )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==APPLICABLE_DATATYPES||(LA3_0 >= CONSTRAINT_REFERENCE && LA3_0 <= INT_LABEL)||(LA3_0 >= LITERAL_CONSTRAINT && LA3_0 <= NAME)||LA3_0==RESOURCE_CONSTRAINT) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // RSF.g:49:7: assigment
            	    {
            	    pushFollow(FOLLOW_assigment_in_public_constraint178);
            	    assigment9=assigment();

            	    state._fsp--;

            	    stream_assigment.add(assigment9.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            char_literal10=(Token)match(input,31,FOLLOW_31_in_public_constraint187);  
            stream_31.add(char_literal10);


            // AST REWRITE
            // elements: s, assigment
            // token labels: s
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_s=new RewriteRuleTokenStream(adaptor,"token s",s);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 51:6: -> ^( PUBLIC_CONSTRAINT $s ( assigment )+ )
            {
                // RSF.g:51:9: ^( PUBLIC_CONSTRAINT $s ( assigment )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(PUBLIC_CONSTRAINT, "PUBLIC_CONSTRAINT")
                , root_1);

                adaptor.addChild(root_1, stream_s.nextNode());

                if ( !(stream_assigment.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_assigment.hasNext() ) {
                    adaptor.addChild(root_1, stream_assigment.nextTree());

                }
                stream_assigment.reset();

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
    // $ANTLR end "public_constraint"


    public static class schema_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "schema_decl"
    // RSF.g:54:1: schema_decl : SCHEMA_FOR s= STRING '{' ( decl )+ '}' -> ^( SCHEMA $s ( decl )+ ) ;
    public final RSFParser.schema_decl_return schema_decl() throws RecognitionException {
        RSFParser.schema_decl_return retval = new RSFParser.schema_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token s=null;
        Token SCHEMA_FOR11=null;
        Token char_literal12=null;
        Token char_literal14=null;
        RSFParser.decl_return decl13 =null;


        CommonTree s_tree=null;
        CommonTree SCHEMA_FOR11_tree=null;
        CommonTree char_literal12_tree=null;
        CommonTree char_literal14_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_SCHEMA_FOR=new RewriteRuleTokenStream(adaptor,"token SCHEMA_FOR");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_decl=new RewriteRuleSubtreeStream(adaptor,"rule decl");
        try {
            // RSF.g:54:13: ( SCHEMA_FOR s= STRING '{' ( decl )+ '}' -> ^( SCHEMA $s ( decl )+ ) )
            // RSF.g:54:15: SCHEMA_FOR s= STRING '{' ( decl )+ '}'
            {
            SCHEMA_FOR11=(Token)match(input,SCHEMA_FOR,FOLLOW_SCHEMA_FOR_in_schema_decl213);  
            stream_SCHEMA_FOR.add(SCHEMA_FOR11);


            s=(Token)match(input,STRING,FOLLOW_STRING_in_schema_decl217);  
            stream_STRING.add(s);


            char_literal12=(Token)match(input,30,FOLLOW_30_in_schema_decl219);  
            stream_30.add(char_literal12);


            // RSF.g:55:6: ( decl )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==LABEL_RULE||LA4_0==PROPERTY_DECL) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // RSF.g:55:6: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_schema_decl226);
            	    decl13=decl();

            	    state._fsp--;

            	    stream_decl.add(decl13.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            char_literal14=(Token)match(input,31,FOLLOW_31_in_schema_decl232);  
            stream_31.add(char_literal14);


            // AST REWRITE
            // elements: s, decl
            // token labels: s
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_s=new RewriteRuleTokenStream(adaptor,"token s",s);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 57:4: -> ^( SCHEMA $s ( decl )+ )
            {
                // RSF.g:57:7: ^( SCHEMA $s ( decl )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(SCHEMA, "SCHEMA")
                , root_1);

                adaptor.addChild(root_1, stream_s.nextNode());

                if ( !(stream_decl.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_decl.hasNext() ) {
                    adaptor.addChild(root_1, stream_decl.nextTree());

                }
                stream_decl.reset();

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
    // $ANTLR end "schema_decl"


    public static class decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "decl"
    // RSF.g:60:1: decl : ( label_decl | property_decl );
    public final RSFParser.decl_return decl() throws RecognitionException {
        RSFParser.decl_return retval = new RSFParser.decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        RSFParser.label_decl_return label_decl15 =null;

        RSFParser.property_decl_return property_decl16 =null;



        try {
            // RSF.g:60:6: ( label_decl | property_decl )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==LABEL_RULE) ) {
                alt5=1;
            }
            else if ( (LA5_0==PROPERTY_DECL) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // RSF.g:60:10: label_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_label_decl_in_decl258);
                    label_decl15=label_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, label_decl15.getTree());

                    }
                    break;
                case 2 :
                    // RSF.g:61:5: property_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_property_decl_in_decl264);
                    property_decl16=property_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, property_decl16.getTree());

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


    public static class label_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "label_decl"
    // RSF.g:65:1: label_decl : LABEL_RULE COLON e= STRING -> ^( LABEL $e) ;
    public final RSFParser.label_decl_return label_decl() throws RecognitionException {
        RSFParser.label_decl_return retval = new RSFParser.label_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token e=null;
        Token LABEL_RULE17=null;
        Token COLON18=null;

        CommonTree e_tree=null;
        CommonTree LABEL_RULE17_tree=null;
        CommonTree COLON18_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_LABEL_RULE=new RewriteRuleTokenStream(adaptor,"token LABEL_RULE");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // RSF.g:65:11: ( LABEL_RULE COLON e= STRING -> ^( LABEL $e) )
            // RSF.g:65:13: LABEL_RULE COLON e= STRING
            {
            LABEL_RULE17=(Token)match(input,LABEL_RULE,FOLLOW_LABEL_RULE_in_label_decl276);  
            stream_LABEL_RULE.add(LABEL_RULE17);


            COLON18=(Token)match(input,COLON,FOLLOW_COLON_in_label_decl278);  
            stream_COLON.add(COLON18);


            e=(Token)match(input,STRING,FOLLOW_STRING_in_label_decl282);  
            stream_STRING.add(e);


            // AST REWRITE
            // elements: e
            // token labels: e
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_e=new RewriteRuleTokenStream(adaptor,"token e",e);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 65:39: -> ^( LABEL $e)
            {
                // RSF.g:65:42: ^( LABEL $e)
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(LABEL, "LABEL")
                , root_1);

                adaptor.addChild(root_1, stream_e.nextNode());

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
    // $ANTLR end "label_decl"


    public static class property_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "property_decl"
    // RSF.g:68:1: property_decl : PROPERTY_DECL id= STRING cardinal_decl '{' ( assigment )+ '}' -> ^( PROPERTY $id cardinal_decl ( assigment )+ ) ;
    public final RSFParser.property_decl_return property_decl() throws RecognitionException {
        RSFParser.property_decl_return retval = new RSFParser.property_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token id=null;
        Token PROPERTY_DECL19=null;
        Token char_literal21=null;
        Token char_literal23=null;
        RSFParser.cardinal_decl_return cardinal_decl20 =null;

        RSFParser.assigment_return assigment22 =null;


        CommonTree id_tree=null;
        CommonTree PROPERTY_DECL19_tree=null;
        CommonTree char_literal21_tree=null;
        CommonTree char_literal23_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_PROPERTY_DECL=new RewriteRuleTokenStream(adaptor,"token PROPERTY_DECL");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_assigment=new RewriteRuleSubtreeStream(adaptor,"rule assigment");
        RewriteRuleSubtreeStream stream_cardinal_decl=new RewriteRuleSubtreeStream(adaptor,"rule cardinal_decl");
        try {
            // RSF.g:68:15: ( PROPERTY_DECL id= STRING cardinal_decl '{' ( assigment )+ '}' -> ^( PROPERTY $id cardinal_decl ( assigment )+ ) )
            // RSF.g:68:17: PROPERTY_DECL id= STRING cardinal_decl '{' ( assigment )+ '}'
            {
            PROPERTY_DECL19=(Token)match(input,PROPERTY_DECL,FOLLOW_PROPERTY_DECL_in_property_decl300);  
            stream_PROPERTY_DECL.add(PROPERTY_DECL19);


            id=(Token)match(input,STRING,FOLLOW_STRING_in_property_decl304);  
            stream_STRING.add(id);


            pushFollow(FOLLOW_cardinal_decl_in_property_decl306);
            cardinal_decl20=cardinal_decl();

            state._fsp--;

            stream_cardinal_decl.add(cardinal_decl20.getTree());

            char_literal21=(Token)match(input,30,FOLLOW_30_in_property_decl308);  
            stream_30.add(char_literal21);


            // RSF.g:69:6: ( assigment )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==APPLICABLE_DATATYPES||(LA6_0 >= CONSTRAINT_REFERENCE && LA6_0 <= INT_LABEL)||(LA6_0 >= LITERAL_CONSTRAINT && LA6_0 <= NAME)||LA6_0==RESOURCE_CONSTRAINT) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // RSF.g:69:6: assigment
            	    {
            	    pushFollow(FOLLOW_assigment_in_property_decl315);
            	    assigment22=assigment();

            	    state._fsp--;

            	    stream_assigment.add(assigment22.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            char_literal23=(Token)match(input,31,FOLLOW_31_in_property_decl322);  
            stream_31.add(char_literal23);


            // AST REWRITE
            // elements: id, assigment, cardinal_decl
            // token labels: id
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_id=new RewriteRuleTokenStream(adaptor,"token id",id);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 71:5: -> ^( PROPERTY $id cardinal_decl ( assigment )+ )
            {
                // RSF.g:71:8: ^( PROPERTY $id cardinal_decl ( assigment )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(PROPERTY, "PROPERTY")
                , root_1);

                adaptor.addChild(root_1, stream_id.nextNode());

                adaptor.addChild(root_1, stream_cardinal_decl.nextTree());

                if ( !(stream_assigment.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_assigment.hasNext() ) {
                    adaptor.addChild(root_1, stream_assigment.nextTree());

                }
                stream_assigment.reset();

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
    // $ANTLR end "property_decl"


    public static class cardinal_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cardinal_decl"
    // RSF.g:74:1: cardinal_decl : e= CARDINALITY_DECL -> ^( CARDINALITY $e) ;
    public final RSFParser.cardinal_decl_return cardinal_decl() throws RecognitionException {
        RSFParser.cardinal_decl_return retval = new RSFParser.cardinal_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token e=null;

        CommonTree e_tree=null;
        RewriteRuleTokenStream stream_CARDINALITY_DECL=new RewriteRuleTokenStream(adaptor,"token CARDINALITY_DECL");

        try {
            // RSF.g:74:15: (e= CARDINALITY_DECL -> ^( CARDINALITY $e) )
            // RSF.g:74:17: e= CARDINALITY_DECL
            {
            e=(Token)match(input,CARDINALITY_DECL,FOLLOW_CARDINALITY_DECL_in_cardinal_decl352);  
            stream_CARDINALITY_DECL.add(e);


            // AST REWRITE
            // elements: e
            // token labels: e
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_e=new RewriteRuleTokenStream(adaptor,"token e",e);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 74:36: -> ^( CARDINALITY $e)
            {
                // RSF.g:74:39: ^( CARDINALITY $e)
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(CARDINALITY, "CARDINALITY")
                , root_1);

                adaptor.addChild(root_1, stream_e.nextNode());

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
    // $ANTLR end "cardinal_decl"


    public static class assigment_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "assigment"
    // RSF.g:77:1: assigment : k= key COLON v= value -> ^( ASSIGMENT $k $v) ;
    public final RSFParser.assigment_return assigment() throws RecognitionException {
        RSFParser.assigment_return retval = new RSFParser.assigment_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token COLON24=null;
        RSFParser.key_return k =null;

        RSFParser.value_return v =null;


        CommonTree COLON24_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        RewriteRuleSubtreeStream stream_key=new RewriteRuleSubtreeStream(adaptor,"rule key");
        try {
            // RSF.g:77:11: (k= key COLON v= value -> ^( ASSIGMENT $k $v) )
            // RSF.g:77:13: k= key COLON v= value
            {
            pushFollow(FOLLOW_key_in_assigment372);
            k=key();

            state._fsp--;

            stream_key.add(k.getTree());

            COLON24=(Token)match(input,COLON,FOLLOW_COLON_in_assigment374);  
            stream_COLON.add(COLON24);


            pushFollow(FOLLOW_value_in_assigment378);
            v=value();

            state._fsp--;

            stream_value.add(v.getTree());

            // AST REWRITE
            // elements: v, k
            // token labels: 
            // rule labels: v, retval, k
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_v=new RewriteRuleSubtreeStream(adaptor,"rule v",v!=null?v.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_k=new RewriteRuleSubtreeStream(adaptor,"rule k",k!=null?k.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 77:33: -> ^( ASSIGMENT $k $v)
            {
                // RSF.g:77:36: ^( ASSIGMENT $k $v)
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(ASSIGMENT, "ASSIGMENT")
                , root_1);

                adaptor.addChild(root_1, stream_k.nextTree());

                adaptor.addChild(root_1, stream_v.nextTree());

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
    // $ANTLR end "assigment"


    public static class key_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "key"
    // RSF.g:80:1: key : ( FIELD_LABEL | INT_LABEL | DATATYPE | RESOURCE_CONSTRAINT | CONSTRAINT_REFERENCE | LITERAL_CONSTRAINT | APPLICABLE_DATATYPES | NAME );
    public final RSFParser.key_return key() throws RecognitionException {
        RSFParser.key_return retval = new RSFParser.key_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set25=null;

        CommonTree set25_tree=null;

        try {
            // RSF.g:80:5: ( FIELD_LABEL | INT_LABEL | DATATYPE | RESOURCE_CONSTRAINT | CONSTRAINT_REFERENCE | LITERAL_CONSTRAINT | APPLICABLE_DATATYPES | NAME )
            // RSF.g:
            {
            root_0 = (CommonTree)adaptor.nil();


            set25=(Token)input.LT(1);

            if ( input.LA(1)==APPLICABLE_DATATYPES||(input.LA(1) >= CONSTRAINT_REFERENCE && input.LA(1) <= INT_LABEL)||(input.LA(1) >= LITERAL_CONSTRAINT && input.LA(1) <= NAME)||input.LA(1)==RESOURCE_CONSTRAINT ) {
                input.consume();
                adaptor.addChild(root_0, 
                (CommonTree)adaptor.create(set25)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


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
    // RSF.g:90:1: value : STRING ;
    public final RSFParser.value_return value() throws RecognitionException {
        RSFParser.value_return retval = new RSFParser.value_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token STRING26=null;

        CommonTree STRING26_tree=null;

        try {
            // RSF.g:90:7: ( STRING )
            // RSF.g:90:9: STRING
            {
            root_0 = (CommonTree)adaptor.nil();


            STRING26=(Token)match(input,STRING,FOLLOW_STRING_in_value446); 
            STRING26_tree = 
            (CommonTree)adaptor.create(STRING26)
            ;
            adaptor.addChild(root_0, STRING26_tree);


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

    // Delegated rules


 

    public static final BitSet FOLLOW_statement_in_statements83 = new BitSet(new long[]{0x0000000004080202L});
    public static final BitSet FOLLOW_namespace_decl_in_statement104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_public_constraint_in_statement111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_schema_decl_in_statement119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NS_in_namespace_decl132 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STRING_in_namespace_decl136 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_PREFIX_in_namespace_decl138 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STRING_in_namespace_decl142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTRAINT_FOR_in_public_constraint164 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STRING_in_public_constraint168 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_public_constraint170 = new BitSet(new long[]{0x0000000001033C10L});
    public static final BitSet FOLLOW_assigment_in_public_constraint178 = new BitSet(new long[]{0x0000000081033C10L});
    public static final BitSet FOLLOW_31_in_public_constraint187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SCHEMA_FOR_in_schema_decl213 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STRING_in_schema_decl217 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_schema_decl219 = new BitSet(new long[]{0x0000000000408000L});
    public static final BitSet FOLLOW_decl_in_schema_decl226 = new BitSet(new long[]{0x0000000080408000L});
    public static final BitSet FOLLOW_31_in_schema_decl232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_label_decl_in_decl258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_property_decl_in_decl264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LABEL_RULE_in_label_decl276 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COLON_in_label_decl278 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STRING_in_label_decl282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_DECL_in_property_decl300 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STRING_in_property_decl304 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_cardinal_decl_in_property_decl306 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_property_decl308 = new BitSet(new long[]{0x0000000001033C10L});
    public static final BitSet FOLLOW_assigment_in_property_decl315 = new BitSet(new long[]{0x0000000081033C10L});
    public static final BitSet FOLLOW_31_in_property_decl322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CARDINALITY_DECL_in_cardinal_decl352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_key_in_assigment372 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COLON_in_assigment374 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_value_in_assigment378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_value446 = new BitSet(new long[]{0x0000000000000002L});

}