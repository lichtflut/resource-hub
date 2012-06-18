// $ANTLR 3.4 RSF.g 2012-06-11 14:26:53

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ASSIGMENT", "CARDINALITY", "CARDINALITY_DECL", "COLON", "CONSTRAINT", "CONSTRAINT_REFERENCE", "DATATYPE", "FIELD_LABEL", "INT_LABEL", "LABEL", "LABEL_RULE", "NAMESPACE", "NS", "PREFIX", "PROPERTY", "PROPERTY_DECL", "RESOURCE_CONSTRAINT", "SCHEMA", "SCHEMA_FOR", "STATEMENTS", "STRING", "WS", "'{'", "'}'"
    };

    public static final int EOF=-1;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int ASSIGMENT=4;
    public static final int CARDINALITY=5;
    public static final int CARDINALITY_DECL=6;
    public static final int COLON=7;
    public static final int CONSTRAINT=8;
    public static final int CONSTRAINT_REFERENCE=9;
    public static final int DATATYPE=10;
    public static final int FIELD_LABEL=11;
    public static final int INT_LABEL=12;
    public static final int LABEL=13;
    public static final int LABEL_RULE=14;
    public static final int NAMESPACE=15;
    public static final int NS=16;
    public static final int PREFIX=17;
    public static final int PROPERTY=18;
    public static final int PROPERTY_DECL=19;
    public static final int RESOURCE_CONSTRAINT=20;
    public static final int SCHEMA=21;
    public static final int SCHEMA_FOR=22;
    public static final int STATEMENTS=23;
    public static final int STRING=24;
    public static final int WS=25;

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
    // RSF.g:35:1: statements : ( statement )+ -> ^( STATEMENTS ( statement )+ ) ;
    public final RSFParser.statements_return statements() throws RecognitionException {
        RSFParser.statements_return retval = new RSFParser.statements_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        RSFParser.statement_return statement1 =null;


        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // RSF.g:35:12: ( ( statement )+ -> ^( STATEMENTS ( statement )+ ) )
            // RSF.g:35:14: ( statement )+
            {
            // RSF.g:35:14: ( statement )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==NS||LA1_0==SCHEMA_FOR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // RSF.g:35:14: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_statements79);
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
            // 35:26: -> ^( STATEMENTS ( statement )+ )
            {
                // RSF.g:35:29: ^( STATEMENTS ( statement )+ )
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
    // RSF.g:38:1: statement : ( namespace_decl | schema_decl );
    public final RSFParser.statement_return statement() throws RecognitionException {
        RSFParser.statement_return retval = new RSFParser.statement_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        RSFParser.namespace_decl_return namespace_decl2 =null;

        RSFParser.schema_decl_return schema_decl3 =null;



        try {
            // RSF.g:38:11: ( namespace_decl | schema_decl )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NS) ) {
                alt2=1;
            }
            else if ( (LA2_0==SCHEMA_FOR) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // RSF.g:38:14: namespace_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_namespace_decl_in_statement100);
                    namespace_decl2=namespace_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, namespace_decl2.getTree());

                    }
                    break;
                case 2 :
                    // RSF.g:39:7: schema_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_schema_decl_in_statement108);
                    schema_decl3=schema_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, schema_decl3.getTree());

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
    // RSF.g:43:1: namespace_decl : NS e= STRING PREFIX f= STRING -> ^( NAMESPACE $e $f) ;
    public final RSFParser.namespace_decl_return namespace_decl() throws RecognitionException {
        RSFParser.namespace_decl_return retval = new RSFParser.namespace_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token e=null;
        Token f=null;
        Token NS4=null;
        Token PREFIX5=null;

        CommonTree e_tree=null;
        CommonTree f_tree=null;
        CommonTree NS4_tree=null;
        CommonTree PREFIX5_tree=null;
        RewriteRuleTokenStream stream_PREFIX=new RewriteRuleTokenStream(adaptor,"token PREFIX");
        RewriteRuleTokenStream stream_NS=new RewriteRuleTokenStream(adaptor,"token NS");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // RSF.g:43:15: ( NS e= STRING PREFIX f= STRING -> ^( NAMESPACE $e $f) )
            // RSF.g:43:17: NS e= STRING PREFIX f= STRING
            {
            NS4=(Token)match(input,NS,FOLLOW_NS_in_namespace_decl121);  
            stream_NS.add(NS4);


            e=(Token)match(input,STRING,FOLLOW_STRING_in_namespace_decl125);  
            stream_STRING.add(e);


            PREFIX5=(Token)match(input,PREFIX,FOLLOW_PREFIX_in_namespace_decl127);  
            stream_PREFIX.add(PREFIX5);


            f=(Token)match(input,STRING,FOLLOW_STRING_in_namespace_decl131);  
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
            // 43:45: -> ^( NAMESPACE $e $f)
            {
                // RSF.g:43:48: ^( NAMESPACE $e $f)
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


    public static class schema_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "schema_decl"
    // RSF.g:46:1: schema_decl : SCHEMA_FOR s= STRING '{' ( decl )+ '}' -> ^( SCHEMA $s ( decl )+ ) ;
    public final RSFParser.schema_decl_return schema_decl() throws RecognitionException {
        RSFParser.schema_decl_return retval = new RSFParser.schema_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token s=null;
        Token SCHEMA_FOR6=null;
        Token char_literal7=null;
        Token char_literal9=null;
        RSFParser.decl_return decl8 =null;


        CommonTree s_tree=null;
        CommonTree SCHEMA_FOR6_tree=null;
        CommonTree char_literal7_tree=null;
        CommonTree char_literal9_tree=null;
        RewriteRuleTokenStream stream_SCHEMA_FOR=new RewriteRuleTokenStream(adaptor,"token SCHEMA_FOR");
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_decl=new RewriteRuleSubtreeStream(adaptor,"rule decl");
        try {
            // RSF.g:46:13: ( SCHEMA_FOR s= STRING '{' ( decl )+ '}' -> ^( SCHEMA $s ( decl )+ ) )
            // RSF.g:46:15: SCHEMA_FOR s= STRING '{' ( decl )+ '}'
            {
            SCHEMA_FOR6=(Token)match(input,SCHEMA_FOR,FOLLOW_SCHEMA_FOR_in_schema_decl153);  
            stream_SCHEMA_FOR.add(SCHEMA_FOR6);


            s=(Token)match(input,STRING,FOLLOW_STRING_in_schema_decl157);  
            stream_STRING.add(s);


            char_literal7=(Token)match(input,26,FOLLOW_26_in_schema_decl159);  
            stream_26.add(char_literal7);


            // RSF.g:47:6: ( decl )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==LABEL_RULE||LA3_0==PROPERTY_DECL) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // RSF.g:47:6: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_schema_decl166);
            	    decl8=decl();

            	    state._fsp--;

            	    stream_decl.add(decl8.getTree());

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


            char_literal9=(Token)match(input,27,FOLLOW_27_in_schema_decl172);  
            stream_27.add(char_literal9);


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
            // 49:4: -> ^( SCHEMA $s ( decl )+ )
            {
                // RSF.g:49:7: ^( SCHEMA $s ( decl )+ )
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
    // RSF.g:52:1: decl : ( label_decl | property_decl );
    public final RSFParser.decl_return decl() throws RecognitionException {
        RSFParser.decl_return retval = new RSFParser.decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        RSFParser.label_decl_return label_decl10 =null;

        RSFParser.property_decl_return property_decl11 =null;



        try {
            // RSF.g:52:6: ( label_decl | property_decl )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LABEL_RULE) ) {
                alt4=1;
            }
            else if ( (LA4_0==PROPERTY_DECL) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // RSF.g:52:10: label_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_label_decl_in_decl198);
                    label_decl10=label_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, label_decl10.getTree());

                    }
                    break;
                case 2 :
                    // RSF.g:53:5: property_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_property_decl_in_decl204);
                    property_decl11=property_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, property_decl11.getTree());

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
    // RSF.g:57:1: label_decl : LABEL_RULE COLON e= STRING -> ^( LABEL $e) ;
    public final RSFParser.label_decl_return label_decl() throws RecognitionException {
        RSFParser.label_decl_return retval = new RSFParser.label_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token e=null;
        Token LABEL_RULE12=null;
        Token COLON13=null;

        CommonTree e_tree=null;
        CommonTree LABEL_RULE12_tree=null;
        CommonTree COLON13_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_LABEL_RULE=new RewriteRuleTokenStream(adaptor,"token LABEL_RULE");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // RSF.g:57:11: ( LABEL_RULE COLON e= STRING -> ^( LABEL $e) )
            // RSF.g:57:13: LABEL_RULE COLON e= STRING
            {
            LABEL_RULE12=(Token)match(input,LABEL_RULE,FOLLOW_LABEL_RULE_in_label_decl216);  
            stream_LABEL_RULE.add(LABEL_RULE12);


            COLON13=(Token)match(input,COLON,FOLLOW_COLON_in_label_decl218);  
            stream_COLON.add(COLON13);


            e=(Token)match(input,STRING,FOLLOW_STRING_in_label_decl222);  
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
            // 57:39: -> ^( LABEL $e)
            {
                // RSF.g:57:42: ^( LABEL $e)
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
    // RSF.g:60:1: property_decl : PROPERTY_DECL id= STRING cardinal_decl '{' ( assigment )+ '}' -> ^( PROPERTY $id cardinal_decl ( assigment )+ ) ;
    public final RSFParser.property_decl_return property_decl() throws RecognitionException {
        RSFParser.property_decl_return retval = new RSFParser.property_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token id=null;
        Token PROPERTY_DECL14=null;
        Token char_literal16=null;
        Token char_literal18=null;
        RSFParser.cardinal_decl_return cardinal_decl15 =null;

        RSFParser.assigment_return assigment17 =null;


        CommonTree id_tree=null;
        CommonTree PROPERTY_DECL14_tree=null;
        CommonTree char_literal16_tree=null;
        CommonTree char_literal18_tree=null;
        RewriteRuleTokenStream stream_PROPERTY_DECL=new RewriteRuleTokenStream(adaptor,"token PROPERTY_DECL");
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_assigment=new RewriteRuleSubtreeStream(adaptor,"rule assigment");
        RewriteRuleSubtreeStream stream_cardinal_decl=new RewriteRuleSubtreeStream(adaptor,"rule cardinal_decl");
        try {
            // RSF.g:60:15: ( PROPERTY_DECL id= STRING cardinal_decl '{' ( assigment )+ '}' -> ^( PROPERTY $id cardinal_decl ( assigment )+ ) )
            // RSF.g:60:17: PROPERTY_DECL id= STRING cardinal_decl '{' ( assigment )+ '}'
            {
            PROPERTY_DECL14=(Token)match(input,PROPERTY_DECL,FOLLOW_PROPERTY_DECL_in_property_decl240);  
            stream_PROPERTY_DECL.add(PROPERTY_DECL14);


            id=(Token)match(input,STRING,FOLLOW_STRING_in_property_decl244);  
            stream_STRING.add(id);


            pushFollow(FOLLOW_cardinal_decl_in_property_decl246);
            cardinal_decl15=cardinal_decl();

            state._fsp--;

            stream_cardinal_decl.add(cardinal_decl15.getTree());

            char_literal16=(Token)match(input,26,FOLLOW_26_in_property_decl248);  
            stream_26.add(char_literal16);


            // RSF.g:61:6: ( assigment )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= CONSTRAINT_REFERENCE && LA5_0 <= INT_LABEL)||LA5_0==RESOURCE_CONSTRAINT) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // RSF.g:61:6: assigment
            	    {
            	    pushFollow(FOLLOW_assigment_in_property_decl255);
            	    assigment17=assigment();

            	    state._fsp--;

            	    stream_assigment.add(assigment17.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            char_literal18=(Token)match(input,27,FOLLOW_27_in_property_decl262);  
            stream_27.add(char_literal18);


            // AST REWRITE
            // elements: cardinal_decl, id, assigment
            // token labels: id
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_id=new RewriteRuleTokenStream(adaptor,"token id",id);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 63:5: -> ^( PROPERTY $id cardinal_decl ( assigment )+ )
            {
                // RSF.g:63:8: ^( PROPERTY $id cardinal_decl ( assigment )+ )
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
    // RSF.g:66:1: cardinal_decl : e= CARDINALITY_DECL -> ^( CARDINALITY $e) ;
    public final RSFParser.cardinal_decl_return cardinal_decl() throws RecognitionException {
        RSFParser.cardinal_decl_return retval = new RSFParser.cardinal_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token e=null;

        CommonTree e_tree=null;
        RewriteRuleTokenStream stream_CARDINALITY_DECL=new RewriteRuleTokenStream(adaptor,"token CARDINALITY_DECL");

        try {
            // RSF.g:66:15: (e= CARDINALITY_DECL -> ^( CARDINALITY $e) )
            // RSF.g:66:17: e= CARDINALITY_DECL
            {
            e=(Token)match(input,CARDINALITY_DECL,FOLLOW_CARDINALITY_DECL_in_cardinal_decl292);  
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
            // 66:36: -> ^( CARDINALITY $e)
            {
                // RSF.g:66:39: ^( CARDINALITY $e)
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
    // RSF.g:69:1: assigment : k= key COLON v= value -> ^( ASSIGMENT $k $v) ;
    public final RSFParser.assigment_return assigment() throws RecognitionException {
        RSFParser.assigment_return retval = new RSFParser.assigment_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token COLON19=null;
        RSFParser.key_return k =null;

        RSFParser.value_return v =null;


        CommonTree COLON19_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        RewriteRuleSubtreeStream stream_key=new RewriteRuleSubtreeStream(adaptor,"rule key");
        try {
            // RSF.g:69:11: (k= key COLON v= value -> ^( ASSIGMENT $k $v) )
            // RSF.g:69:13: k= key COLON v= value
            {
            pushFollow(FOLLOW_key_in_assigment312);
            k=key();

            state._fsp--;

            stream_key.add(k.getTree());

            COLON19=(Token)match(input,COLON,FOLLOW_COLON_in_assigment314);  
            stream_COLON.add(COLON19);


            pushFollow(FOLLOW_value_in_assigment318);
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
            // 69:33: -> ^( ASSIGMENT $k $v)
            {
                // RSF.g:69:36: ^( ASSIGMENT $k $v)
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
    // RSF.g:72:1: key : ( FIELD_LABEL | INT_LABEL | DATATYPE | RESOURCE_CONSTRAINT | CONSTRAINT_REFERENCE );
    public final RSFParser.key_return key() throws RecognitionException {
        RSFParser.key_return retval = new RSFParser.key_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set20=null;

        CommonTree set20_tree=null;

        try {
            // RSF.g:72:5: ( FIELD_LABEL | INT_LABEL | DATATYPE | RESOURCE_CONSTRAINT | CONSTRAINT_REFERENCE )
            // RSF.g:
            {
            root_0 = (CommonTree)adaptor.nil();


            set20=(Token)input.LT(1);

            if ( (input.LA(1) >= CONSTRAINT_REFERENCE && input.LA(1) <= INT_LABEL)||input.LA(1)==RESOURCE_CONSTRAINT ) {
                input.consume();
                adaptor.addChild(root_0, 
                (CommonTree)adaptor.create(set20)
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
    // RSF.g:79:1: value : STRING ;
    public final RSFParser.value_return value() throws RecognitionException {
        RSFParser.value_return retval = new RSFParser.value_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token STRING21=null;

        CommonTree STRING21_tree=null;

        try {
            // RSF.g:79:7: ( STRING )
            // RSF.g:79:9: STRING
            {
            root_0 = (CommonTree)adaptor.nil();


            STRING21=(Token)match(input,STRING,FOLLOW_STRING_in_value371); 
            STRING21_tree = 
            (CommonTree)adaptor.create(STRING21)
            ;
            adaptor.addChild(root_0, STRING21_tree);


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


 

    public static final BitSet FOLLOW_statement_in_statements79 = new BitSet(new long[]{0x0000000000410002L});
    public static final BitSet FOLLOW_namespace_decl_in_statement100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_schema_decl_in_statement108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NS_in_namespace_decl121 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_STRING_in_namespace_decl125 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_PREFIX_in_namespace_decl127 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_STRING_in_namespace_decl131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SCHEMA_FOR_in_schema_decl153 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_STRING_in_schema_decl157 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_schema_decl159 = new BitSet(new long[]{0x0000000000084000L});
    public static final BitSet FOLLOW_decl_in_schema_decl166 = new BitSet(new long[]{0x0000000008084000L});
    public static final BitSet FOLLOW_27_in_schema_decl172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_label_decl_in_decl198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_property_decl_in_decl204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LABEL_RULE_in_label_decl216 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_COLON_in_label_decl218 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_STRING_in_label_decl222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_DECL_in_property_decl240 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_STRING_in_property_decl244 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_cardinal_decl_in_property_decl246 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_property_decl248 = new BitSet(new long[]{0x0000000000101E00L});
    public static final BitSet FOLLOW_assigment_in_property_decl255 = new BitSet(new long[]{0x0000000008101E00L});
    public static final BitSet FOLLOW_27_in_property_decl262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CARDINALITY_DECL_in_cardinal_decl292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_key_in_assigment312 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_COLON_in_assigment314 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_value_in_assigment318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_value371 = new BitSet(new long[]{0x0000000000000002L});

}