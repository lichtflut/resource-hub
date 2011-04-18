// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/ResourceSchema.g 2011-04-18 22:23:29

 package de.lichtflut.rb.core.schema.parser.impl;
 import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;
import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.rb.core.schema.model.*;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;


public class ResourceSchemaParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PROPERTY_DEC", "IDENT", "EOL", "WS", "RESOURCE_DEC", "CARDINALITY", "INT", "NUMERIC", "TEXT", "LOGICAL", "STRING", "'('", "')'", "'type is'", "'regex'", "'\"'", "'references'", "'and'"
    };
    public static final int PROPERTY_DEC=4;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int INT=10;
    public static final int NUMERIC=11;
    public static final int TEXT=12;
    public static final int EOF=-1;
    public static final int CARDINALITY=9;
    public static final int RESOURCE_DEC=8;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int EOL=6;
    public static final int WS=7;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int IDENT=5;
    public static final int LOGICAL=13;
    public static final int STRING=14;

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


    	private PropertyDeclaration property = null;


    public static class dsl_return extends ParserRuleReturnScope {
        public Set<ResourceSchemaType> types;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dsl"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:33:1: dsl returns [Set<ResourceSchemaType> types] : ( property | resource )+ ;
    public final ResourceSchemaParser.dsl_return dsl() throws RecognitionException {
        ResourceSchemaParser.dsl_return retval = new ResourceSchemaParser.dsl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        ResourceSchemaParser.property_return property1 = null;

        ResourceSchemaParser.resource_return resource2 = null;



        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:34:2: ( ( property | resource )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:34:4: ( property | resource )+
            {
            root_0 = (CommonTree)adaptor.nil();

            retval.types = new HashSet<ResourceSchemaType>();
            // de/lichtflut/rb/core/schema/ResourceSchema.g:35:3: ( property | resource )+
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
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:36:3: property
            	    {
            	    pushFollow(FOLLOW_property_in_dsl79);
            	    property1=property();

            	    state._fsp--;

            	    adaptor.addChild(root_0, property1.getTree());
            	    retval.types.add((property1!=null?property1.property:null));

            	    }
            	    break;
            	case 2 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:38:3: resource
            	    {
            	    pushFollow(FOLLOW_resource_in_dsl88);
            	    resource2=resource();

            	    state._fsp--;

            	    adaptor.addChild(root_0, resource2.getTree());
            	    retval.types.add(null);

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
        public PropertyDeclaration property;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "property"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:45:1: property returns [PropertyDeclaration property] : PROPERTY_DEC IDENT '(' ( propertyDeclaration )* ')' ;
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

        this.property = new PropertyDeclarationImpl();
        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:48:3: ( PROPERTY_DEC IDENT '(' ( propertyDeclaration )* ')' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:48:6: PROPERTY_DEC IDENT '(' ( propertyDeclaration )* ')'
            {
            root_0 = (CommonTree)adaptor.nil();

            retval.property = this.property;
            PROPERTY_DEC3=(Token)match(input,PROPERTY_DEC,FOLLOW_PROPERTY_DEC_in_property130); 
            PROPERTY_DEC3_tree = (CommonTree)adaptor.create(PROPERTY_DEC3);
            adaptor.addChild(root_0, PROPERTY_DEC3_tree);

            IDENT4=(Token)match(input,IDENT,FOLLOW_IDENT_in_property132); 
            IDENT4_tree = (CommonTree)adaptor.create(IDENT4);
            adaptor.addChild(root_0, IDENT4_tree);

            retval.property.setName((IDENT4!=null?IDENT4.getText():null));
            char_literal5=(Token)match(input,15,FOLLOW_15_in_property139); 
            // de/lichtflut/rb/core/schema/ResourceSchema.g:50:9: ( propertyDeclaration )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==WS||(LA2_0>=17 && LA2_0<=18)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:50:10: propertyDeclaration
            	    {
            	    pushFollow(FOLLOW_propertyDeclaration_in_property143);
            	    propertyDeclaration6=propertyDeclaration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, propertyDeclaration6.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            char_literal7=(Token)match(input,16,FOLLOW_16_in_property147); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

            this.property = null;
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
        public PropertyDeclaration property;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertyDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:54:1: propertyDeclaration returns [PropertyDeclaration property] : ( typeDeclaration | regexDeclaration ) EOL ;
    public final ResourceSchemaParser.propertyDeclaration_return propertyDeclaration() throws RecognitionException {
        ResourceSchemaParser.propertyDeclaration_return retval = new ResourceSchemaParser.propertyDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token EOL10=null;
        ResourceSchemaParser.typeDeclaration_return typeDeclaration8 = null;

        ResourceSchemaParser.regexDeclaration_return regexDeclaration9 = null;


        CommonTree EOL10_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:55:2: ( ( typeDeclaration | regexDeclaration ) EOL )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:56:2: ( typeDeclaration | regexDeclaration ) EOL
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:56:2: ( typeDeclaration | regexDeclaration )
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:57:2: typeDeclaration
                    {
                    pushFollow(FOLLOW_typeDeclaration_in_propertyDeclaration169);
                    typeDeclaration8=typeDeclaration();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDeclaration8.getTree());

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:59:2: regexDeclaration
                    {
                    pushFollow(FOLLOW_regexDeclaration_in_propertyDeclaration175);
                    regexDeclaration9=regexDeclaration();

                    state._fsp--;

                    adaptor.addChild(root_0, regexDeclaration9.getTree());

                    }
                    break;

            }

            EOL10=(Token)match(input,EOL,FOLLOW_EOL_in_propertyDeclaration181); 
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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:66:1: typeDeclaration : ( WS )* 'type is' ( WS )* datatype ( WS )* ;
    public final ResourceSchemaParser.typeDeclaration_return typeDeclaration() throws RecognitionException {
        ResourceSchemaParser.typeDeclaration_return retval = new ResourceSchemaParser.typeDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token WS11=null;
        Token string_literal12=null;
        Token WS13=null;
        Token WS15=null;
        ResourceSchemaParser.datatype_return datatype14 = null;


        CommonTree WS11_tree=null;
        CommonTree string_literal12_tree=null;
        CommonTree WS13_tree=null;
        CommonTree WS15_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:66:17: ( ( WS )* 'type is' ( WS )* datatype ( WS )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:66:20: ( WS )* 'type is' ( WS )* datatype ( WS )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:66:20: ( WS )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==WS) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:66:21: WS
            	    {
            	    WS11=(Token)match(input,WS,FOLLOW_WS_in_typeDeclaration195); 
            	    WS11_tree = (CommonTree)adaptor.create(WS11);
            	    adaptor.addChild(root_0, WS11_tree);


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            string_literal12=(Token)match(input,17,FOLLOW_17_in_typeDeclaration206); 
            string_literal12_tree = (CommonTree)adaptor.create(string_literal12);
            adaptor.addChild(root_0, string_literal12_tree);

            // de/lichtflut/rb/core/schema/ResourceSchema.g:68:8: ( WS )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==WS) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:68:9: WS
            	    {
            	    WS13=(Token)match(input,WS,FOLLOW_WS_in_typeDeclaration216); 
            	    WS13_tree = (CommonTree)adaptor.create(WS13);
            	    adaptor.addChild(root_0, WS13_tree);


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            pushFollow(FOLLOW_datatype_in_typeDeclaration227);
            datatype14=datatype();

            state._fsp--;

            adaptor.addChild(root_0, datatype14.getTree());
            this.property.setElementaryDataType((datatype14!=null?datatype14.type:null));
            // de/lichtflut/rb/core/schema/ResourceSchema.g:70:8: ( WS )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==WS) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:70:9: WS
            	    {
            	    WS15=(Token)match(input,WS,FOLLOW_WS_in_typeDeclaration239); 
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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:75:1: regexDeclaration : ( WS )* 'regex' ( WS )* '\"' IDENT '\"' ( WS )* ;
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:75:18: ( ( WS )* 'regex' ( WS )* '\"' IDENT '\"' ( WS )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:75:21: ( WS )* 'regex' ( WS )* '\"' IDENT '\"' ( WS )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:75:21: ( WS )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==WS) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:75:22: WS
            	    {
            	    WS16=(Token)match(input,WS,FOLLOW_WS_in_regexDeclaration256); 
            	    WS16_tree = (CommonTree)adaptor.create(WS16);
            	    adaptor.addChild(root_0, WS16_tree);


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            string_literal17=(Token)match(input,18,FOLLOW_18_in_regexDeclaration266); 
            string_literal17_tree = (CommonTree)adaptor.create(string_literal17);
            adaptor.addChild(root_0, string_literal17_tree);

            // de/lichtflut/rb/core/schema/ResourceSchema.g:77:6: ( WS )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==WS) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:77:7: WS
            	    {
            	    WS18=(Token)match(input,WS,FOLLOW_WS_in_regexDeclaration274); 
            	    WS18_tree = (CommonTree)adaptor.create(WS18);
            	    adaptor.addChild(root_0, WS18_tree);


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            char_literal19=(Token)match(input,19,FOLLOW_19_in_regexDeclaration283); 
            char_literal19_tree = (CommonTree)adaptor.create(char_literal19);
            adaptor.addChild(root_0, char_literal19_tree);

            IDENT20=(Token)match(input,IDENT,FOLLOW_IDENT_in_regexDeclaration290); 
            IDENT20_tree = (CommonTree)adaptor.create(IDENT20);
            adaptor.addChild(root_0, IDENT20_tree);

            this.property.addConstraint(ConstraintFactory.buildConstraint((IDENT20!=null?IDENT20.getText():null)));
            char_literal21=(Token)match(input,19,FOLLOW_19_in_regexDeclaration299); 
            char_literal21_tree = (CommonTree)adaptor.create(char_literal21);
            adaptor.addChild(root_0, char_literal21_tree);

            // de/lichtflut/rb/core/schema/ResourceSchema.g:81:6: ( WS )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==WS) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:81:7: WS
            	    {
            	    WS22=(Token)match(input,WS,FOLLOW_WS_in_regexDeclaration307); 
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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:86:1: resource : RESOURCE_DEC IDENT '(' ( resourceDeclaration )+ ')' ;
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:86:10: ( RESOURCE_DEC IDENT '(' ( resourceDeclaration )+ ')' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:86:12: RESOURCE_DEC IDENT '(' ( resourceDeclaration )+ ')'
            {
            root_0 = (CommonTree)adaptor.nil();

            RESOURCE_DEC23=(Token)match(input,RESOURCE_DEC,FOLLOW_RESOURCE_DEC_in_resource326); 
            RESOURCE_DEC23_tree = (CommonTree)adaptor.create(RESOURCE_DEC23);
            adaptor.addChild(root_0, RESOURCE_DEC23_tree);

            IDENT24=(Token)match(input,IDENT,FOLLOW_IDENT_in_resource328); 
            IDENT24_tree = (CommonTree)adaptor.create(IDENT24);
            adaptor.addChild(root_0, IDENT24_tree);

            char_literal25=(Token)match(input,15,FOLLOW_15_in_resource330); 
            // de/lichtflut/rb/core/schema/ResourceSchema.g:86:36: ( resourceDeclaration )+
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
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:86:36: resourceDeclaration
            	    {
            	    pushFollow(FOLLOW_resourceDeclaration_in_resource333);
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

            char_literal27=(Token)match(input,16,FOLLOW_16_in_resource336); 

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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:90:1: resourceDeclaration : cardinality ( IDENT ( referenceDeclaration )? | property ) EOL ;
    public final ResourceSchemaParser.resourceDeclaration_return resourceDeclaration() throws RecognitionException {
        ResourceSchemaParser.resourceDeclaration_return retval = new ResourceSchemaParser.resourceDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT29=null;
        Token EOL32=null;
        ResourceSchemaParser.cardinality_return cardinality28 = null;

        ResourceSchemaParser.referenceDeclaration_return referenceDeclaration30 = null;

        ResourceSchemaParser.property_return property31 = null;


        CommonTree IDENT29_tree=null;
        CommonTree EOL32_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:90:21: ( cardinality ( IDENT ( referenceDeclaration )? | property ) EOL )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:90:23: cardinality ( IDENT ( referenceDeclaration )? | property ) EOL
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_cardinality_in_resourceDeclaration348);
            cardinality28=cardinality();

            state._fsp--;

            adaptor.addChild(root_0, cardinality28.getTree());
            // de/lichtflut/rb/core/schema/ResourceSchema.g:90:35: ( IDENT ( referenceDeclaration )? | property )
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
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:90:36: IDENT ( referenceDeclaration )?
                    {
                    IDENT29=(Token)match(input,IDENT,FOLLOW_IDENT_in_resourceDeclaration351); 
                    IDENT29_tree = (CommonTree)adaptor.create(IDENT29);
                    adaptor.addChild(root_0, IDENT29_tree);

                    // de/lichtflut/rb/core/schema/ResourceSchema.g:90:42: ( referenceDeclaration )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==20) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // de/lichtflut/rb/core/schema/ResourceSchema.g:90:42: referenceDeclaration
                            {
                            pushFollow(FOLLOW_referenceDeclaration_in_resourceDeclaration353);
                            referenceDeclaration30=referenceDeclaration();

                            state._fsp--;

                            adaptor.addChild(root_0, referenceDeclaration30.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:90:66: property
                    {
                    pushFollow(FOLLOW_property_in_resourceDeclaration358);
                    property31=property();

                    state._fsp--;

                    adaptor.addChild(root_0, property31.getTree());

                    }
                    break;

            }

            EOL32=(Token)match(input,EOL,FOLLOW_EOL_in_resourceDeclaration361); 
            EOL32_tree = (CommonTree)adaptor.create(EOL32);
            adaptor.addChild(root_0, EOL32_tree);


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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:94:1: referenceDeclaration : 'references' IDENT ;
    public final ResourceSchemaParser.referenceDeclaration_return referenceDeclaration() throws RecognitionException {
        ResourceSchemaParser.referenceDeclaration_return retval = new ResourceSchemaParser.referenceDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal33=null;
        Token IDENT34=null;

        CommonTree string_literal33_tree=null;
        CommonTree IDENT34_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:94:22: ( 'references' IDENT )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:94:24: 'references' IDENT
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal33=(Token)match(input,20,FOLLOW_20_in_referenceDeclaration372); 
            string_literal33_tree = (CommonTree)adaptor.create(string_literal33);
            adaptor.addChild(root_0, string_literal33_tree);

            IDENT34=(Token)match(input,IDENT,FOLLOW_IDENT_in_referenceDeclaration374); 
            IDENT34_tree = (CommonTree)adaptor.create(IDENT34);
            adaptor.addChild(root_0, IDENT34_tree);


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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:98:1: cardinality : cardinalityDeclaration ( 'and' cardinalityDeclaration )* ;
    public final ResourceSchemaParser.cardinality_return cardinality() throws RecognitionException {
        ResourceSchemaParser.cardinality_return retval = new ResourceSchemaParser.cardinality_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal36=null;
        ResourceSchemaParser.cardinalityDeclaration_return cardinalityDeclaration35 = null;

        ResourceSchemaParser.cardinalityDeclaration_return cardinalityDeclaration37 = null;


        CommonTree string_literal36_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:98:13: ( cardinalityDeclaration ( 'and' cardinalityDeclaration )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:98:15: cardinalityDeclaration ( 'and' cardinalityDeclaration )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_cardinalityDeclaration_in_cardinality385);
            cardinalityDeclaration35=cardinalityDeclaration();

            state._fsp--;

            adaptor.addChild(root_0, cardinalityDeclaration35.getTree());
            // de/lichtflut/rb/core/schema/ResourceSchema.g:98:38: ( 'and' cardinalityDeclaration )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==21) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:98:39: 'and' cardinalityDeclaration
            	    {
            	    string_literal36=(Token)match(input,21,FOLLOW_21_in_cardinality388); 
            	    pushFollow(FOLLOW_cardinalityDeclaration_in_cardinality391);
            	    cardinalityDeclaration37=cardinalityDeclaration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, cardinalityDeclaration37.getTree());

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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:102:1: cardinalityDeclaration : CARDINALITY INT ;
    public final ResourceSchemaParser.cardinalityDeclaration_return cardinalityDeclaration() throws RecognitionException {
        ResourceSchemaParser.cardinalityDeclaration_return retval = new ResourceSchemaParser.cardinalityDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token CARDINALITY38=null;
        Token INT39=null;

        CommonTree CARDINALITY38_tree=null;
        CommonTree INT39_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:102:24: ( CARDINALITY INT )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:102:26: CARDINALITY INT
            {
            root_0 = (CommonTree)adaptor.nil();

            CARDINALITY38=(Token)match(input,CARDINALITY,FOLLOW_CARDINALITY_in_cardinalityDeclaration404); 
            CARDINALITY38_tree = (CommonTree)adaptor.create(CARDINALITY38);
            adaptor.addChild(root_0, CARDINALITY38_tree);

            INT39=(Token)match(input,INT,FOLLOW_INT_in_cardinalityDeclaration406); 
            INT39_tree = (CommonTree)adaptor.create(INT39);
            adaptor.addChild(root_0, INT39_tree);


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

    public static class datatype_return extends ParserRuleReturnScope {
        public ElementaryDataType type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "datatype"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:106:1: datatype returns [ElementaryDataType type] : ( NUMERIC | TEXT | LOGICAL ) ;
    public final ResourceSchemaParser.datatype_return datatype() throws RecognitionException {
        ResourceSchemaParser.datatype_return retval = new ResourceSchemaParser.datatype_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NUMERIC40=null;
        Token TEXT41=null;
        Token LOGICAL42=null;

        CommonTree NUMERIC40_tree=null;
        CommonTree TEXT41_tree=null;
        CommonTree LOGICAL42_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:107:2: ( ( NUMERIC | TEXT | LOGICAL ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:108:3: ( NUMERIC | TEXT | LOGICAL )
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:108:3: ( NUMERIC | TEXT | LOGICAL )
            int alt14=3;
            switch ( input.LA(1) ) {
            case NUMERIC:
                {
                alt14=1;
                }
                break;
            case TEXT:
                {
                alt14=2;
                }
                break;
            case LOGICAL:
                {
                alt14=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:109:3: NUMERIC
                    {
                    NUMERIC40=(Token)match(input,NUMERIC,FOLLOW_NUMERIC_in_datatype428); 
                    NUMERIC40_tree = (CommonTree)adaptor.create(NUMERIC40);
                    adaptor.addChild(root_0, NUMERIC40_tree);

                    retval.type = ElementaryDataType.INTEGER;

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:111:3: TEXT
                    {
                    TEXT41=(Token)match(input,TEXT,FOLLOW_TEXT_in_datatype438); 
                    TEXT41_tree = (CommonTree)adaptor.create(TEXT41);
                    adaptor.addChild(root_0, TEXT41_tree);

                    retval.type = ElementaryDataType.STRING;

                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:113:3: LOGICAL
                    {
                    LOGICAL42=(Token)match(input,LOGICAL,FOLLOW_LOGICAL_in_datatype448); 
                    LOGICAL42_tree = (CommonTree)adaptor.create(LOGICAL42);
                    adaptor.addChild(root_0, LOGICAL42_tree);

                    retval.type = ElementaryDataType.BOOLEAN;

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
    // $ANTLR end "datatype"

    // Delegated rules


    protected DFA3 dfa3 = new DFA3(this);
    static final String DFA3_eotS =
        "\4\uffff";
    static final String DFA3_eofS =
        "\4\uffff";
    static final String DFA3_minS =
        "\2\7\2\uffff";
    static final String DFA3_maxS =
        "\2\22\2\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA3_specialS =
        "\4\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\11\uffff\1\2\1\3",
            "\1\1\11\uffff\1\2\1\3",
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
            return "56:2: ( typeDeclaration | regexDeclaration )";
        }
    }
 

    public static final BitSet FOLLOW_property_in_dsl79 = new BitSet(new long[]{0x0000000000000112L});
    public static final BitSet FOLLOW_resource_in_dsl88 = new BitSet(new long[]{0x0000000000000112L});
    public static final BitSet FOLLOW_PROPERTY_DEC_in_property130 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_property132 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_property139 = new BitSet(new long[]{0x0000000000070080L});
    public static final BitSet FOLLOW_propertyDeclaration_in_property143 = new BitSet(new long[]{0x0000000000070080L});
    public static final BitSet FOLLOW_16_in_property147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDeclaration_in_propertyDeclaration169 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_regexDeclaration_in_propertyDeclaration175 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EOL_in_propertyDeclaration181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_typeDeclaration195 = new BitSet(new long[]{0x0000000000020080L});
    public static final BitSet FOLLOW_17_in_typeDeclaration206 = new BitSet(new long[]{0x0000000000003880L});
    public static final BitSet FOLLOW_WS_in_typeDeclaration216 = new BitSet(new long[]{0x0000000000003880L});
    public static final BitSet FOLLOW_datatype_in_typeDeclaration227 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_WS_in_typeDeclaration239 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_WS_in_regexDeclaration256 = new BitSet(new long[]{0x0000000000040080L});
    public static final BitSet FOLLOW_18_in_regexDeclaration266 = new BitSet(new long[]{0x0000000000080080L});
    public static final BitSet FOLLOW_WS_in_regexDeclaration274 = new BitSet(new long[]{0x0000000000080080L});
    public static final BitSet FOLLOW_19_in_regexDeclaration283 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_regexDeclaration290 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_regexDeclaration299 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_WS_in_regexDeclaration307 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_RESOURCE_DEC_in_resource326 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_resource328 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_resource330 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_resourceDeclaration_in_resource333 = new BitSet(new long[]{0x0000000000010200L});
    public static final BitSet FOLLOW_16_in_resource336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cardinality_in_resourceDeclaration348 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_IDENT_in_resourceDeclaration351 = new BitSet(new long[]{0x0000000000100040L});
    public static final BitSet FOLLOW_referenceDeclaration_in_resourceDeclaration353 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_property_in_resourceDeclaration358 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EOL_in_resourceDeclaration361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_referenceDeclaration372 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_referenceDeclaration374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cardinalityDeclaration_in_cardinality385 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_21_in_cardinality388 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_cardinalityDeclaration_in_cardinality391 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_CARDINALITY_in_cardinalityDeclaration404 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_INT_in_cardinalityDeclaration406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_in_datatype428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TEXT_in_datatype438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LOGICAL_in_datatype448 = new BitSet(new long[]{0x0000000000000002L});

}