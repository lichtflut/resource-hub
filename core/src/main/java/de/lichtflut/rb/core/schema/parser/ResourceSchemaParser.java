// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/ResourceSchema.g 2011-04-14 16:28:34

    package de.lichtflut.rb.core.schema.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class ResourceSchemaParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PROPERTY_DEC", "IDENT", "EOL", "WS", "DATATYPE", "RESOURCE_DEC", "CARDINALITY", "INT", "PROPERTY_DEC_UPPER", "PROPERTY_DEC_LOWER", "RESOURCE_DEC_UPPER", "RESOURCE_DEC_LOWER", "NUMERIC_UPPER", "NUMERIC_LOWER", "TEXT_UPPER", "TEXT_LOWER", "LOGICAL_UPPER", "LOGICAL_LOWER", "NUMERIC", "TEXT", "LOGICAL", "STRING", "'('", "')'", "'type is'", "'regex'", "'\"'", "'references'", "'and'"
    };
    public static final int LOGICAL_LOWER=21;
    public static final int PROPERTY_DEC=4;
    public static final int TEXT_LOWER=19;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int LOGICAL_UPPER=20;
    public static final int NUMERIC_UPPER=16;
    public static final int PROPERTY_DEC_LOWER=13;
    public static final int INT=11;
    public static final int TEXT=23;
    public static final int NUMERIC=22;
    public static final int EOF=-1;
    public static final int TEXT_UPPER=18;
    public static final int RESOURCE_DEC_UPPER=14;
    public static final int NUMERIC_LOWER=17;
    public static final int CARDINALITY=10;
    public static final int RESOURCE_DEC=9;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int EOL=6;
    public static final int WS=7;
    public static final int PROPERTY_DEC_UPPER=12;
    public static final int IDENT=5;
    public static final int DATATYPE=8;
    public static final int LOGICAL=24;
    public static final int RESOURCE_DEC_LOWER=15;
    public static final int STRING=25;

    // delegates
    // delegators


        public ResourceSchemaParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ResourceSchemaParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ResourceSchemaParser.tokenNames; }
    public String getGrammarFileName() { return "de/lichtflut/rb/core/schema/ResourceSchema.g"; }


        public static void main(String[] args) throws Exception {
        }


    public static class dsl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dsl"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:30:1: dsl : ( property | resource )+ ;
    public final ResourceSchemaParser.dsl_return dsl() throws RecognitionException {
        ResourceSchemaParser.dsl_return retval = new ResourceSchemaParser.dsl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        ResourceSchemaParser.property_return property1 = null;

        ResourceSchemaParser.resource_return resource2 = null;



        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:30:4: ( ( property | resource )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:30:6: ( property | resource )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:30:6: ( property | resource )+
            int cnt1=0;
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==PROPERTY_DEC) ) {
                    alt1=1;
                }
                else if ( (LA1_0==RESOURCE_DEC) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:30:7: property
            	    {
            	    pushFollow(FOLLOW_property_in_dsl66);
            	    property1=property();

            	    state._fsp--;

            	    adaptor.addChild(root_0, property1.getTree());

            	    }
            	    break;
            	case 2 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:30:18: resource
            	    {
            	    pushFollow(FOLLOW_resource_in_dsl70);
            	    resource2=resource();

            	    state._fsp--;

            	    adaptor.addChild(root_0, resource2.getTree());

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
        }
        return retval;
    }
    // $ANTLR end "dsl"

    public static class property_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "property"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:32:1: property : PROPERTY_DEC IDENT '(' ( propertyDeclaration )* ')' ;
    public final ResourceSchemaParser.property_return property() throws RecognitionException {
        ResourceSchemaParser.property_return retval = new ResourceSchemaParser.property_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token PROPERTY_DEC3=null;
        Token IDENT4=null;
        Token char_literal5=null;
        Token char_literal7=null;
        ResourceSchemaParser.propertyDeclaration_return propertyDeclaration6 = null;


        CommonTree PROPERTY_DEC3_tree=null;
        CommonTree IDENT4_tree=null;
        CommonTree char_literal5_tree=null;
        CommonTree char_literal7_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:32:10: ( PROPERTY_DEC IDENT '(' ( propertyDeclaration )* ')' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:32:12: PROPERTY_DEC IDENT '(' ( propertyDeclaration )* ')'
            {
            root_0 = (CommonTree)adaptor.nil();

            PROPERTY_DEC3=(Token)match(input,PROPERTY_DEC,FOLLOW_PROPERTY_DEC_in_property80); 
            PROPERTY_DEC3_tree = (CommonTree)adaptor.create(PROPERTY_DEC3);
            adaptor.addChild(root_0, PROPERTY_DEC3_tree);

            IDENT4=(Token)match(input,IDENT,FOLLOW_IDENT_in_property82); 
            IDENT4_tree = (CommonTree)adaptor.create(IDENT4);
            adaptor.addChild(root_0, IDENT4_tree);

            char_literal5=(Token)match(input,26,FOLLOW_26_in_property84); 
            // de/lichtflut/rb/core/schema/ResourceSchema.g:32:36: ( propertyDeclaration )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==WS||(LA2_0>=28 && LA2_0<=29)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:32:36: propertyDeclaration
            	    {
            	    pushFollow(FOLLOW_propertyDeclaration_in_property87);
            	    propertyDeclaration6=propertyDeclaration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, propertyDeclaration6.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            char_literal7=(Token)match(input,27,FOLLOW_27_in_property90); 

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
        }
        return retval;
    }
    // $ANTLR end "property"

    public static class propertyDeclaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertyDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:34:1: propertyDeclaration : ( typeDeclaration | regexDeclaration ) EOL ;
    public final ResourceSchemaParser.propertyDeclaration_return propertyDeclaration() throws RecognitionException {
        ResourceSchemaParser.propertyDeclaration_return retval = new ResourceSchemaParser.propertyDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token EOL10=null;
        ResourceSchemaParser.typeDeclaration_return typeDeclaration8 = null;

        ResourceSchemaParser.regexDeclaration_return regexDeclaration9 = null;


        CommonTree EOL10_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:34:21: ( ( typeDeclaration | regexDeclaration ) EOL )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:34:24: ( typeDeclaration | regexDeclaration ) EOL
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:34:24: ( typeDeclaration | regexDeclaration )
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:34:26: typeDeclaration
                    {
                    pushFollow(FOLLOW_typeDeclaration_in_propertyDeclaration102);
                    typeDeclaration8=typeDeclaration();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDeclaration8.getTree());

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:34:44: regexDeclaration
                    {
                    pushFollow(FOLLOW_regexDeclaration_in_propertyDeclaration106);
                    regexDeclaration9=regexDeclaration();

                    state._fsp--;

                    adaptor.addChild(root_0, regexDeclaration9.getTree());

                    }
                    break;

            }

            EOL10=(Token)match(input,EOL,FOLLOW_EOL_in_propertyDeclaration109); 
            EOL10_tree = (CommonTree)adaptor.create(EOL10);
            adaptor.addChild(root_0, EOL10_tree);


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
        }
        return retval;
    }
    // $ANTLR end "propertyDeclaration"

    public static class typeDeclaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:36:1: typeDeclaration : ( WS )* 'type is' ( WS )* DATATYPE ( WS )* ;
    public final ResourceSchemaParser.typeDeclaration_return typeDeclaration() throws RecognitionException {
        ResourceSchemaParser.typeDeclaration_return retval = new ResourceSchemaParser.typeDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token WS11=null;
        Token string_literal12=null;
        Token WS13=null;
        Token DATATYPE14=null;
        Token WS15=null;

        CommonTree WS11_tree=null;
        CommonTree string_literal12_tree=null;
        CommonTree WS13_tree=null;
        CommonTree DATATYPE14_tree=null;
        CommonTree WS15_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:36:17: ( ( WS )* 'type is' ( WS )* DATATYPE ( WS )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:36:20: ( WS )* 'type is' ( WS )* DATATYPE ( WS )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:36:20: ( WS )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==WS) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:36:21: WS
            	    {
            	    WS11=(Token)match(input,WS,FOLLOW_WS_in_typeDeclaration119); 
            	    WS11_tree = (CommonTree)adaptor.create(WS11);
            	    adaptor.addChild(root_0, WS11_tree);


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            string_literal12=(Token)match(input,28,FOLLOW_28_in_typeDeclaration123); 
            string_literal12_tree = (CommonTree)adaptor.create(string_literal12);
            adaptor.addChild(root_0, string_literal12_tree);

            // de/lichtflut/rb/core/schema/ResourceSchema.g:36:37: ( WS )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==WS) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:36:38: WS
            	    {
            	    WS13=(Token)match(input,WS,FOLLOW_WS_in_typeDeclaration127); 
            	    WS13_tree = (CommonTree)adaptor.create(WS13);
            	    adaptor.addChild(root_0, WS13_tree);


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            DATATYPE14=(Token)match(input,DATATYPE,FOLLOW_DATATYPE_in_typeDeclaration131); 
            DATATYPE14_tree = (CommonTree)adaptor.create(DATATYPE14);
            adaptor.addChild(root_0, DATATYPE14_tree);

            // de/lichtflut/rb/core/schema/ResourceSchema.g:36:53: ( WS )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==WS) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:36:54: WS
            	    {
            	    WS15=(Token)match(input,WS,FOLLOW_WS_in_typeDeclaration135); 
            	    WS15_tree = (CommonTree)adaptor.create(WS15);
            	    adaptor.addChild(root_0, WS15_tree);


            	    }
            	    break;

            	default :
            	    break loop6;
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
        }
        return retval;
    }
    // $ANTLR end "typeDeclaration"

    public static class regexDeclaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "regexDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:38:1: regexDeclaration : ( WS )* 'regex' ( WS )* '\"' IDENT '\"' ( WS )* ;
    public final ResourceSchemaParser.regexDeclaration_return regexDeclaration() throws RecognitionException {
        ResourceSchemaParser.regexDeclaration_return retval = new ResourceSchemaParser.regexDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token WS16=null;
        Token string_literal17=null;
        Token WS18=null;
        Token char_literal19=null;
        Token IDENT20=null;
        Token char_literal21=null;
        Token WS22=null;

        CommonTree WS16_tree=null;
        CommonTree string_literal17_tree=null;
        CommonTree WS18_tree=null;
        CommonTree char_literal19_tree=null;
        CommonTree IDENT20_tree=null;
        CommonTree char_literal21_tree=null;
        CommonTree WS22_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:38:18: ( ( WS )* 'regex' ( WS )* '\"' IDENT '\"' ( WS )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:38:20: ( WS )* 'regex' ( WS )* '\"' IDENT '\"' ( WS )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:38:20: ( WS )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==WS) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:38:21: WS
            	    {
            	    WS16=(Token)match(input,WS,FOLLOW_WS_in_regexDeclaration147); 
            	    WS16_tree = (CommonTree)adaptor.create(WS16);
            	    adaptor.addChild(root_0, WS16_tree);


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            string_literal17=(Token)match(input,29,FOLLOW_29_in_regexDeclaration151); 
            string_literal17_tree = (CommonTree)adaptor.create(string_literal17);
            adaptor.addChild(root_0, string_literal17_tree);

            // de/lichtflut/rb/core/schema/ResourceSchema.g:38:35: ( WS )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==WS) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:38:36: WS
            	    {
            	    WS18=(Token)match(input,WS,FOLLOW_WS_in_regexDeclaration155); 
            	    WS18_tree = (CommonTree)adaptor.create(WS18);
            	    adaptor.addChild(root_0, WS18_tree);


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            char_literal19=(Token)match(input,30,FOLLOW_30_in_regexDeclaration159); 
            char_literal19_tree = (CommonTree)adaptor.create(char_literal19);
            adaptor.addChild(root_0, char_literal19_tree);

            IDENT20=(Token)match(input,IDENT,FOLLOW_IDENT_in_regexDeclaration161); 
            IDENT20_tree = (CommonTree)adaptor.create(IDENT20);
            adaptor.addChild(root_0, IDENT20_tree);

            char_literal21=(Token)match(input,30,FOLLOW_30_in_regexDeclaration163); 
            char_literal21_tree = (CommonTree)adaptor.create(char_literal21);
            adaptor.addChild(root_0, char_literal21_tree);

            // de/lichtflut/rb/core/schema/ResourceSchema.g:38:55: ( WS )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==WS) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:38:56: WS
            	    {
            	    WS22=(Token)match(input,WS,FOLLOW_WS_in_regexDeclaration166); 
            	    WS22_tree = (CommonTree)adaptor.create(WS22);
            	    adaptor.addChild(root_0, WS22_tree);


            	    }
            	    break;

            	default :
            	    break loop9;
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
        }
        return retval;
    }
    // $ANTLR end "regexDeclaration"

    public static class resource_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "resource"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:40:1: resource : RESOURCE_DEC IDENT '(' ( resourceDeclaration )+ ')' ;
    public final ResourceSchemaParser.resource_return resource() throws RecognitionException {
        ResourceSchemaParser.resource_return retval = new ResourceSchemaParser.resource_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token RESOURCE_DEC23=null;
        Token IDENT24=null;
        Token char_literal25=null;
        Token char_literal27=null;
        ResourceSchemaParser.resourceDeclaration_return resourceDeclaration26 = null;


        CommonTree RESOURCE_DEC23_tree=null;
        CommonTree IDENT24_tree=null;
        CommonTree char_literal25_tree=null;
        CommonTree char_literal27_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:40:10: ( RESOURCE_DEC IDENT '(' ( resourceDeclaration )+ ')' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:40:12: RESOURCE_DEC IDENT '(' ( resourceDeclaration )+ ')'
            {
            root_0 = (CommonTree)adaptor.nil();

            RESOURCE_DEC23=(Token)match(input,RESOURCE_DEC,FOLLOW_RESOURCE_DEC_in_resource177); 
            RESOURCE_DEC23_tree = (CommonTree)adaptor.create(RESOURCE_DEC23);
            adaptor.addChild(root_0, RESOURCE_DEC23_tree);

            IDENT24=(Token)match(input,IDENT,FOLLOW_IDENT_in_resource179); 
            IDENT24_tree = (CommonTree)adaptor.create(IDENT24);
            adaptor.addChild(root_0, IDENT24_tree);

            char_literal25=(Token)match(input,26,FOLLOW_26_in_resource181); 
            // de/lichtflut/rb/core/schema/ResourceSchema.g:40:36: ( resourceDeclaration )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==CARDINALITY) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:40:36: resourceDeclaration
            	    {
            	    pushFollow(FOLLOW_resourceDeclaration_in_resource184);
            	    resourceDeclaration26=resourceDeclaration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, resourceDeclaration26.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);

            char_literal27=(Token)match(input,27,FOLLOW_27_in_resource187); 

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
        }
        return retval;
    }
    // $ANTLR end "resource"

    public static class resourceDeclaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "resourceDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:42:1: resourceDeclaration : cardinality ( IDENT ( referenceDeclaration )? | property ) ;
    public final ResourceSchemaParser.resourceDeclaration_return resourceDeclaration() throws RecognitionException {
        ResourceSchemaParser.resourceDeclaration_return retval = new ResourceSchemaParser.resourceDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT29=null;
        ResourceSchemaParser.cardinality_return cardinality28 = null;

        ResourceSchemaParser.referenceDeclaration_return referenceDeclaration30 = null;

        ResourceSchemaParser.property_return property31 = null;


        CommonTree IDENT29_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:42:21: ( cardinality ( IDENT ( referenceDeclaration )? | property ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:42:23: cardinality ( IDENT ( referenceDeclaration )? | property )
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_cardinality_in_resourceDeclaration196);
            cardinality28=cardinality();

            state._fsp--;

            adaptor.addChild(root_0, cardinality28.getTree());
            // de/lichtflut/rb/core/schema/ResourceSchema.g:42:35: ( IDENT ( referenceDeclaration )? | property )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==IDENT) ) {
                alt12=1;
            }
            else if ( (LA12_0==PROPERTY_DEC) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:42:36: IDENT ( referenceDeclaration )?
                    {
                    IDENT29=(Token)match(input,IDENT,FOLLOW_IDENT_in_resourceDeclaration199); 
                    IDENT29_tree = (CommonTree)adaptor.create(IDENT29);
                    adaptor.addChild(root_0, IDENT29_tree);

                    // de/lichtflut/rb/core/schema/ResourceSchema.g:42:42: ( referenceDeclaration )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==31) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // de/lichtflut/rb/core/schema/ResourceSchema.g:42:42: referenceDeclaration
                            {
                            pushFollow(FOLLOW_referenceDeclaration_in_resourceDeclaration201);
                            referenceDeclaration30=referenceDeclaration();

                            state._fsp--;

                            adaptor.addChild(root_0, referenceDeclaration30.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:42:66: property
                    {
                    pushFollow(FOLLOW_property_in_resourceDeclaration206);
                    property31=property();

                    state._fsp--;

                    adaptor.addChild(root_0, property31.getTree());

                    }
                    break;

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
        }
        return retval;
    }
    // $ANTLR end "resourceDeclaration"

    public static class referenceDeclaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "referenceDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:44:1: referenceDeclaration : 'references' IDENT ;
    public final ResourceSchemaParser.referenceDeclaration_return referenceDeclaration() throws RecognitionException {
        ResourceSchemaParser.referenceDeclaration_return retval = new ResourceSchemaParser.referenceDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal32=null;
        Token IDENT33=null;

        CommonTree string_literal32_tree=null;
        CommonTree IDENT33_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:44:22: ( 'references' IDENT )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:44:24: 'references' IDENT
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal32=(Token)match(input,31,FOLLOW_31_in_referenceDeclaration215); 
            string_literal32_tree = (CommonTree)adaptor.create(string_literal32);
            adaptor.addChild(root_0, string_literal32_tree);

            IDENT33=(Token)match(input,IDENT,FOLLOW_IDENT_in_referenceDeclaration217); 
            IDENT33_tree = (CommonTree)adaptor.create(IDENT33);
            adaptor.addChild(root_0, IDENT33_tree);


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
        }
        return retval;
    }
    // $ANTLR end "referenceDeclaration"

    public static class cardinality_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "cardinality"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:46:1: cardinality : cardinalityDeclaration ( 'and' cardinalityDeclaration )* ;
    public final ResourceSchemaParser.cardinality_return cardinality() throws RecognitionException {
        ResourceSchemaParser.cardinality_return retval = new ResourceSchemaParser.cardinality_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal35=null;
        ResourceSchemaParser.cardinalityDeclaration_return cardinalityDeclaration34 = null;

        ResourceSchemaParser.cardinalityDeclaration_return cardinalityDeclaration36 = null;


        CommonTree string_literal35_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:46:13: ( cardinalityDeclaration ( 'and' cardinalityDeclaration )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:46:15: cardinalityDeclaration ( 'and' cardinalityDeclaration )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_cardinalityDeclaration_in_cardinality225);
            cardinalityDeclaration34=cardinalityDeclaration();

            state._fsp--;

            adaptor.addChild(root_0, cardinalityDeclaration34.getTree());
            // de/lichtflut/rb/core/schema/ResourceSchema.g:46:38: ( 'and' cardinalityDeclaration )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==32) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:46:39: 'and' cardinalityDeclaration
            	    {
            	    string_literal35=(Token)match(input,32,FOLLOW_32_in_cardinality228); 
            	    pushFollow(FOLLOW_cardinalityDeclaration_in_cardinality231);
            	    cardinalityDeclaration36=cardinalityDeclaration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, cardinalityDeclaration36.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
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
        }
        return retval;
    }
    // $ANTLR end "cardinality"

    public static class cardinalityDeclaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "cardinalityDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:48:1: cardinalityDeclaration : CARDINALITY INT ;
    public final ResourceSchemaParser.cardinalityDeclaration_return cardinalityDeclaration() throws RecognitionException {
        ResourceSchemaParser.cardinalityDeclaration_return retval = new ResourceSchemaParser.cardinalityDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token CARDINALITY37=null;
        Token INT38=null;

        CommonTree CARDINALITY37_tree=null;
        CommonTree INT38_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:48:24: ( CARDINALITY INT )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:48:26: CARDINALITY INT
            {
            root_0 = (CommonTree)adaptor.nil();

            CARDINALITY37=(Token)match(input,CARDINALITY,FOLLOW_CARDINALITY_in_cardinalityDeclaration241); 
            CARDINALITY37_tree = (CommonTree)adaptor.create(CARDINALITY37);
            adaptor.addChild(root_0, CARDINALITY37_tree);

            INT38=(Token)match(input,INT,FOLLOW_INT_in_cardinalityDeclaration243); 
            INT38_tree = (CommonTree)adaptor.create(INT38);
            adaptor.addChild(root_0, INT38_tree);


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
        }
        return retval;
    }
    // $ANTLR end "cardinalityDeclaration"

    // Delegated rules


    protected DFA3 dfa3 = new DFA3(this);
    static final String DFA3_eotS =
        "\4\uffff";
    static final String DFA3_eofS =
        "\4\uffff";
    static final String DFA3_minS =
        "\2\7\2\uffff";
    static final String DFA3_maxS =
        "\2\35\2\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA3_specialS =
        "\4\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\24\uffff\1\2\1\3",
            "\1\1\24\uffff\1\2\1\3",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "34:24: ( typeDeclaration | regexDeclaration )";
        }
    }
 

    public static final BitSet FOLLOW_property_in_dsl66 = new BitSet(new long[]{0x0000000000000212L});
    public static final BitSet FOLLOW_resource_in_dsl70 = new BitSet(new long[]{0x0000000000000212L});
    public static final BitSet FOLLOW_PROPERTY_DEC_in_property80 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_property82 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_property84 = new BitSet(new long[]{0x0000000038000080L});
    public static final BitSet FOLLOW_propertyDeclaration_in_property87 = new BitSet(new long[]{0x0000000038000080L});
    public static final BitSet FOLLOW_27_in_property90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDeclaration_in_propertyDeclaration102 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_regexDeclaration_in_propertyDeclaration106 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EOL_in_propertyDeclaration109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_typeDeclaration119 = new BitSet(new long[]{0x0000000010000080L});
    public static final BitSet FOLLOW_28_in_typeDeclaration123 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_WS_in_typeDeclaration127 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_DATATYPE_in_typeDeclaration131 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_WS_in_typeDeclaration135 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_WS_in_regexDeclaration147 = new BitSet(new long[]{0x0000000020000080L});
    public static final BitSet FOLLOW_29_in_regexDeclaration151 = new BitSet(new long[]{0x0000000040000080L});
    public static final BitSet FOLLOW_WS_in_regexDeclaration155 = new BitSet(new long[]{0x0000000040000080L});
    public static final BitSet FOLLOW_30_in_regexDeclaration159 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_regexDeclaration161 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_regexDeclaration163 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_WS_in_regexDeclaration166 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_RESOURCE_DEC_in_resource177 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_resource179 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_resource181 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceDeclaration_in_resource184 = new BitSet(new long[]{0x0000000008000400L});
    public static final BitSet FOLLOW_27_in_resource187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cardinality_in_resourceDeclaration196 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_IDENT_in_resourceDeclaration199 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_referenceDeclaration_in_resourceDeclaration201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_property_in_resourceDeclaration206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_referenceDeclaration215 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_referenceDeclaration217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cardinalityDeclaration_in_cardinality225 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_32_in_cardinality228 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_cardinalityDeclaration_in_cardinality231 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_CARDINALITY_in_cardinalityDeclaration241 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_INT_in_cardinalityDeclaration243 = new BitSet(new long[]{0x0000000000000002L});

}