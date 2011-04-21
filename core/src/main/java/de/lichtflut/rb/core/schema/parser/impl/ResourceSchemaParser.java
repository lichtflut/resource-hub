// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/ResourceSchema.g 2011-04-21 16:18:06

   /*
    * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
   */
    package de.lichtflut.rb.core.schema.parser.impl;
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

public class ResourceSchemaParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PROPERTY_DEC", "IDENT", "BRACKET_OPEN", "BRACKET_CLOSED", "TYPE_DEC", "REGEX_DEC", "RESOURCE_DEC", "CARDINALITY", "INT", "NUMERIC", "TEXT", "LOGICAL", "DELIM", "STRING", "WS", "'\"'", "'references'", "'AND'"
    };
    public static final int PROPERTY_DEC=4;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int INT=12;
    public static final int NUMERIC=13;
    public static final int TEXT=14;
    public static final int DELIM=16;
    public static final int EOF=-1;
    public static final int CARDINALITY=11;
    public static final int REGEX_DEC=9;
    public static final int RESOURCE_DEC=10;
    public static final int T__19=19;
    public static final int WS=18;
    public static final int BRACKET_CLOSED=7;
    public static final int IDENT=5;
    public static final int LOGICAL=15;
    public static final int TYPE_DEC=8;
    public static final int STRING=17;
    public static final int BRACKET_OPEN=6;

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
    	private ResourceSchema resource = null;
    	private RSErrorReporter errorReporter = null;
        public void setErrorReporter(RSErrorReporter errorReporter) {
            this.errorReporter = errorReporter;
        }
        public void emitErrorMessage(String msg) {
            errorReporter.reportError(msg);
        }
    	
    	


    public static class dsl_return extends ParserRuleReturnScope {
        public Set<ResourceSchemaType> types;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dsl"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:53:1: dsl returns [Set<ResourceSchemaType> types] : ( property | resource )+ ;
    public final ResourceSchemaParser.dsl_return dsl() throws RecognitionException {
        ResourceSchemaParser.dsl_return retval = new ResourceSchemaParser.dsl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        ResourceSchemaParser.property_return property1 = null;

        ResourceSchemaParser.resource_return resource2 = null;



        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:54:2: ( ( property | resource )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:54:4: ( property | resource )+
            {
            root_0 = (CommonTree)adaptor.nil();

            retval.types = new HashSet<ResourceSchemaType>();
            // de/lichtflut/rb/core/schema/ResourceSchema.g:55:3: ( property | resource )+
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
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:56:3: property
            	    {
            	    pushFollow(FOLLOW_property_in_dsl79);
            	    property1=property();

            	    state._fsp--;

            	    adaptor.addChild(root_0, property1.getTree());
            	    retval.types.add((property1!=null?property1.property:null));

            	    }
            	    break;
            	case 2 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:58:3: resource
            	    {
            	    pushFollow(FOLLOW_resource_in_dsl88);
            	    resource2=resource();

            	    state._fsp--;

            	    adaptor.addChild(root_0, resource2.getTree());
            	    retval.types.add((resource2!=null?resource2.resource:null));

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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:65:1: property returns [PropertyDeclaration property] : PROPERTY_DEC IDENT BRACKET_OPEN ( propertyDeclaration )* BRACKET_CLOSED ;
    public final ResourceSchemaParser.property_return property() throws RecognitionException {
        ResourceSchemaParser.property_return retval = new ResourceSchemaParser.property_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token PROPERTY_DEC3=null;
        Token IDENT4=null;
        Token BRACKET_OPEN5=null;
        Token BRACKET_CLOSED7=null;
        ResourceSchemaParser.propertyDeclaration_return propertyDeclaration6 = null;


        CommonTree PROPERTY_DEC3_tree=null;
        CommonTree IDENT4_tree=null;
        CommonTree BRACKET_OPEN5_tree=null;
        CommonTree BRACKET_CLOSED7_tree=null;

        this.property = new PropertyDeclarationImpl();
        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:68:3: ( PROPERTY_DEC IDENT BRACKET_OPEN ( propertyDeclaration )* BRACKET_CLOSED )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:68:6: PROPERTY_DEC IDENT BRACKET_OPEN ( propertyDeclaration )* BRACKET_CLOSED
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
            BRACKET_OPEN5=(Token)match(input,BRACKET_OPEN,FOLLOW_BRACKET_OPEN_in_property139); 
            BRACKET_OPEN5_tree = (CommonTree)adaptor.create(BRACKET_OPEN5);
            adaptor.addChild(root_0, BRACKET_OPEN5_tree);

            // de/lichtflut/rb/core/schema/ResourceSchema.g:70:17: ( propertyDeclaration )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=TYPE_DEC && LA2_0<=REGEX_DEC)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:70:18: propertyDeclaration
            	    {
            	    pushFollow(FOLLOW_propertyDeclaration_in_property142);
            	    propertyDeclaration6=propertyDeclaration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, propertyDeclaration6.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            BRACKET_CLOSED7=(Token)match(input,BRACKET_CLOSED,FOLLOW_BRACKET_CLOSED_in_property146); 
            BRACKET_CLOSED7_tree = (CommonTree)adaptor.create(BRACKET_CLOSED7);
            adaptor.addChild(root_0, BRACKET_CLOSED7_tree);


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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:74:1: propertyDeclaration returns [PropertyDeclaration property] : ( typeDeclaration | regexDeclaration ) ;
    public final ResourceSchemaParser.propertyDeclaration_return propertyDeclaration() throws RecognitionException {
        ResourceSchemaParser.propertyDeclaration_return retval = new ResourceSchemaParser.propertyDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        ResourceSchemaParser.typeDeclaration_return typeDeclaration8 = null;

        ResourceSchemaParser.regexDeclaration_return regexDeclaration9 = null;



        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:75:2: ( ( typeDeclaration | regexDeclaration ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:75:4: ( typeDeclaration | regexDeclaration )
            {
            root_0 = (CommonTree)adaptor.nil();

            retval.property = this.property;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:76:2: ( typeDeclaration | regexDeclaration )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==TYPE_DEC) ) {
                alt3=1;
            }
            else if ( (LA3_0==REGEX_DEC) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:77:2: typeDeclaration
                    {
                    pushFollow(FOLLOW_typeDeclaration_in_propertyDeclaration169);
                    typeDeclaration8=typeDeclaration();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDeclaration8.getTree());

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:79:2: regexDeclaration
                    {
                    pushFollow(FOLLOW_regexDeclaration_in_propertyDeclaration175);
                    regexDeclaration9=regexDeclaration();

                    state._fsp--;

                    adaptor.addChild(root_0, regexDeclaration9.getTree());

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
    // $ANTLR end "propertyDeclaration"

    public static class typeDeclaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:86:1: typeDeclaration : TYPE_DEC ( '\"' )? datatype ( '\"' )? ;
    public final ResourceSchemaParser.typeDeclaration_return typeDeclaration() throws RecognitionException {
        ResourceSchemaParser.typeDeclaration_return retval = new ResourceSchemaParser.typeDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token TYPE_DEC10=null;
        Token char_literal11=null;
        Token char_literal13=null;
        ResourceSchemaParser.datatype_return datatype12 = null;


        CommonTree TYPE_DEC10_tree=null;
        CommonTree char_literal11_tree=null;
        CommonTree char_literal13_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:86:17: ( TYPE_DEC ( '\"' )? datatype ( '\"' )? )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:86:20: TYPE_DEC ( '\"' )? datatype ( '\"' )?
            {
            root_0 = (CommonTree)adaptor.nil();

            TYPE_DEC10=(Token)match(input,TYPE_DEC,FOLLOW_TYPE_DEC_in_typeDeclaration193); 
            TYPE_DEC10_tree = (CommonTree)adaptor.create(TYPE_DEC10);
            adaptor.addChild(root_0, TYPE_DEC10_tree);

            // de/lichtflut/rb/core/schema/ResourceSchema.g:87:8: ( '\"' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==19) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:87:8: '\"'
                    {
                    char_literal11=(Token)match(input,19,FOLLOW_19_in_typeDeclaration202); 
                    char_literal11_tree = (CommonTree)adaptor.create(char_literal11);
                    adaptor.addChild(root_0, char_literal11_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_datatype_in_typeDeclaration212);
            datatype12=datatype();

            state._fsp--;

            adaptor.addChild(root_0, datatype12.getTree());
            this.property.setElementaryDataType((datatype12!=null?datatype12.type:null));
            // de/lichtflut/rb/core/schema/ResourceSchema.g:89:8: ( '\"' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==19) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:89:8: '\"'
                    {
                    char_literal13=(Token)match(input,19,FOLLOW_19_in_typeDeclaration223); 
                    char_literal13_tree = (CommonTree)adaptor.create(char_literal13);
                    adaptor.addChild(root_0, char_literal13_tree);


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
    // $ANTLR end "typeDeclaration"

    public static class regexDeclaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "regexDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:95:1: regexDeclaration : REGEX_DEC '\"' IDENT '\"' ;
    public final ResourceSchemaParser.regexDeclaration_return regexDeclaration() throws RecognitionException {
        ResourceSchemaParser.regexDeclaration_return retval = new ResourceSchemaParser.regexDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token REGEX_DEC14=null;
        Token char_literal15=null;
        Token IDENT16=null;
        Token char_literal17=null;

        CommonTree REGEX_DEC14_tree=null;
        CommonTree char_literal15_tree=null;
        CommonTree IDENT16_tree=null;
        CommonTree char_literal17_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:95:18: ( REGEX_DEC '\"' IDENT '\"' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:95:21: REGEX_DEC '\"' IDENT '\"'
            {
            root_0 = (CommonTree)adaptor.nil();

            REGEX_DEC14=(Token)match(input,REGEX_DEC,FOLLOW_REGEX_DEC_in_regexDeclaration245); 
            REGEX_DEC14_tree = (CommonTree)adaptor.create(REGEX_DEC14);
            adaptor.addChild(root_0, REGEX_DEC14_tree);

            char_literal15=(Token)match(input,19,FOLLOW_19_in_regexDeclaration252); 
            char_literal15_tree = (CommonTree)adaptor.create(char_literal15);
            adaptor.addChild(root_0, char_literal15_tree);

            IDENT16=(Token)match(input,IDENT,FOLLOW_IDENT_in_regexDeclaration260); 
            IDENT16_tree = (CommonTree)adaptor.create(IDENT16);
            adaptor.addChild(root_0, IDENT16_tree);

            this.property.addConstraint(ConstraintFactory.buildConstraint((IDENT16!=null?IDENT16.getText():null)));
            char_literal17=(Token)match(input,19,FOLLOW_19_in_regexDeclaration269); 
            char_literal17_tree = (CommonTree)adaptor.create(char_literal17);
            adaptor.addChild(root_0, char_literal17_tree);


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
        public ResourceSchema resource;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "resource"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:103:1: resource returns [ResourceSchema resource] : RESOURCE_DEC IDENT BRACKET_OPEN resourceDeclaration BRACKET_CLOSED ;
    public final ResourceSchemaParser.resource_return resource() throws RecognitionException {
        ResourceSchemaParser.resource_return retval = new ResourceSchemaParser.resource_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token RESOURCE_DEC18=null;
        Token IDENT19=null;
        Token BRACKET_OPEN20=null;
        Token BRACKET_CLOSED22=null;
        ResourceSchemaParser.resourceDeclaration_return resourceDeclaration21 = null;


        CommonTree RESOURCE_DEC18_tree=null;
        CommonTree IDENT19_tree=null;
        CommonTree BRACKET_OPEN20_tree=null;
        CommonTree BRACKET_CLOSED22_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:105:2: ( RESOURCE_DEC IDENT BRACKET_OPEN resourceDeclaration BRACKET_CLOSED )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:106:2: RESOURCE_DEC IDENT BRACKET_OPEN resourceDeclaration BRACKET_CLOSED
            {
            root_0 = (CommonTree)adaptor.nil();

            RESOURCE_DEC18=(Token)match(input,RESOURCE_DEC,FOLLOW_RESOURCE_DEC_in_resource298); 
            RESOURCE_DEC18_tree = (CommonTree)adaptor.create(RESOURCE_DEC18);
            adaptor.addChild(root_0, RESOURCE_DEC18_tree);

            IDENT19=(Token)match(input,IDENT,FOLLOW_IDENT_in_resource301); 
            IDENT19_tree = (CommonTree)adaptor.create(IDENT19);
            adaptor.addChild(root_0, IDENT19_tree);

            this.resource = new ResourceSchemaImpl(IDENT19.getText()); retval.resource = this.resource;
            BRACKET_OPEN20=(Token)match(input,BRACKET_OPEN,FOLLOW_BRACKET_OPEN_in_resource307); 
            BRACKET_OPEN20_tree = (CommonTree)adaptor.create(BRACKET_OPEN20);
            adaptor.addChild(root_0, BRACKET_OPEN20_tree);

            pushFollow(FOLLOW_resourceDeclaration_in_resource309);
            resourceDeclaration21=resourceDeclaration();

            state._fsp--;

            adaptor.addChild(root_0, resourceDeclaration21.getTree());
            BRACKET_CLOSED22=(Token)match(input,BRACKET_CLOSED,FOLLOW_BRACKET_CLOSED_in_resource311); 
            BRACKET_CLOSED22_tree = (CommonTree)adaptor.create(BRACKET_CLOSED22);
            adaptor.addChild(root_0, BRACKET_CLOSED22_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

            this.resource = null;
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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:112:1: resourceDeclaration : ( cardinality IDENT )* ;
    public final ResourceSchemaParser.resourceDeclaration_return resourceDeclaration() throws RecognitionException {
        ResourceSchemaParser.resourceDeclaration_return retval = new ResourceSchemaParser.resourceDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT24=null;
        ResourceSchemaParser.cardinality_return cardinality23 = null;


        CommonTree IDENT24_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:112:21: ( ( cardinality IDENT )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:112:23: ( cardinality IDENT )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:112:23: ( cardinality IDENT )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==CARDINALITY) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:112:24: cardinality IDENT
            	    {
            	    pushFollow(FOLLOW_cardinality_in_resourceDeclaration323);
            	    cardinality23=cardinality();

            	    state._fsp--;

            	    adaptor.addChild(root_0, cardinality23.getTree());
            	    Cardinality cardinality = (cardinality23!=null?cardinality23.cardinality:null);
            	    IDENT24=(Token)match(input,IDENT,FOLLOW_IDENT_in_resourceDeclaration333); 
            	    IDENT24_tree = (CommonTree)adaptor.create(IDENT24);
            	    adaptor.addChild(root_0, IDENT24_tree);

            	    this.resource.addPropertyAssertion(new PropertyAssertionImpl(IDENT24.getText(),cardinality));

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
    // $ANTLR end "resourceDeclaration"

    public static class referenceDeclaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "referenceDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:119:1: referenceDeclaration : 'references' IDENT ;
    public final ResourceSchemaParser.referenceDeclaration_return referenceDeclaration() throws RecognitionException {
        ResourceSchemaParser.referenceDeclaration_return retval = new ResourceSchemaParser.referenceDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal25=null;
        Token IDENT26=null;

        CommonTree string_literal25_tree=null;
        CommonTree IDENT26_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:119:22: ( 'references' IDENT )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:119:24: 'references' IDENT
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal25=(Token)match(input,20,FOLLOW_20_in_referenceDeclaration363); 
            string_literal25_tree = (CommonTree)adaptor.create(string_literal25);
            adaptor.addChild(root_0, string_literal25_tree);

            IDENT26=(Token)match(input,IDENT,FOLLOW_IDENT_in_referenceDeclaration365); 
            IDENT26_tree = (CommonTree)adaptor.create(IDENT26);
            adaptor.addChild(root_0, IDENT26_tree);


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
        public Cardinality cardinality;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "cardinality"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:123:1: cardinality returns [Cardinality cardinality] : c1= cardinalityDeclaration ( 'AND' cn= cardinalityDeclaration )* ;
    public final ResourceSchemaParser.cardinality_return cardinality() throws RecognitionException {
        ResourceSchemaParser.cardinality_return retval = new ResourceSchemaParser.cardinality_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal27=null;
        ResourceSchemaParser.cardinalityDeclaration_return c1 = null;

        ResourceSchemaParser.cardinalityDeclaration_return cn = null;


        CommonTree string_literal27_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:124:6: (c1= cardinalityDeclaration ( 'AND' cn= cardinalityDeclaration )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:124:8: c1= cardinalityDeclaration ( 'AND' cn= cardinalityDeclaration )*
            {
            root_0 = (CommonTree)adaptor.nil();

            RBEvaluator eval = null;
            pushFollow(FOLLOW_cardinalityDeclaration_in_cardinality394);
            c1=cardinalityDeclaration();

            state._fsp--;

            adaptor.addChild(root_0, c1.getTree());
            eval = (c1!=null?c1.evaluator:null);
            // de/lichtflut/rb/core/schema/ResourceSchema.g:126:6: ( 'AND' cn= cardinalityDeclaration )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==21) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:126:7: 'AND' cn= cardinalityDeclaration
            	    {
            	    string_literal27=(Token)match(input,21,FOLLOW_21_in_cardinality403); 
            	    pushFollow(FOLLOW_cardinalityDeclaration_in_cardinality409);
            	    cn=cardinalityDeclaration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, cn.getTree());
            	    eval.evaluate((cn!=null?cn.evaluator:null).getResult());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            retval.cardinality = (Cardinality) eval.getResult();

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
        public RBEvaluator<Cardinality> evaluator;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "cardinalityDeclaration"
    // de/lichtflut/rb/core/schema/ResourceSchema.g:132:1: cardinalityDeclaration returns [RBEvaluator<Cardinality> evaluator] : CARDINALITY INT ;
    public final ResourceSchemaParser.cardinalityDeclaration_return cardinalityDeclaration() throws RecognitionException {
        ResourceSchemaParser.cardinalityDeclaration_return retval = new ResourceSchemaParser.cardinalityDeclaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token CARDINALITY28=null;
        Token INT29=null;

        CommonTree CARDINALITY28_tree=null;
        CommonTree INT29_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:132:68: ( CARDINALITY INT )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:133:1: CARDINALITY INT
            {
            root_0 = (CommonTree)adaptor.nil();

            CARDINALITY28=(Token)match(input,CARDINALITY,FOLLOW_CARDINALITY_in_cardinalityDeclaration440); 
            CARDINALITY28_tree = (CommonTree)adaptor.create(CARDINALITY28);
            adaptor.addChild(root_0, CARDINALITY28_tree);

            INT29=(Token)match(input,INT,FOLLOW_INT_in_cardinalityDeclaration443); 
            INT29_tree = (CommonTree)adaptor.create(INT29);
            adaptor.addChild(root_0, INT29_tree);

            retval.evaluator = new RBCardinalityEvaluator(CARDINALITY28,Integer.parseInt(INT29.getText()));

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
    // de/lichtflut/rb/core/schema/ResourceSchema.g:137:1: datatype returns [ElementaryDataType type] : ( NUMERIC | TEXT | LOGICAL ) ;
    public final ResourceSchemaParser.datatype_return datatype() throws RecognitionException {
        ResourceSchemaParser.datatype_return retval = new ResourceSchemaParser.datatype_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NUMERIC30=null;
        Token TEXT31=null;
        Token LOGICAL32=null;

        CommonTree NUMERIC30_tree=null;
        CommonTree TEXT31_tree=null;
        CommonTree LOGICAL32_tree=null;

        try {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:138:2: ( ( NUMERIC | TEXT | LOGICAL ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:139:3: ( NUMERIC | TEXT | LOGICAL )
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/ResourceSchema.g:139:3: ( NUMERIC | TEXT | LOGICAL )
            int alt8=3;
            switch ( input.LA(1) ) {
            case NUMERIC:
                {
                alt8=1;
                }
                break;
            case TEXT:
                {
                alt8=2;
                }
                break;
            case LOGICAL:
                {
                alt8=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:140:3: NUMERIC
                    {
                    NUMERIC30=(Token)match(input,NUMERIC,FOLLOW_NUMERIC_in_datatype466); 
                    NUMERIC30_tree = (CommonTree)adaptor.create(NUMERIC30);
                    adaptor.addChild(root_0, NUMERIC30_tree);

                    retval.type = ElementaryDataType.INTEGER;

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:142:3: TEXT
                    {
                    TEXT31=(Token)match(input,TEXT,FOLLOW_TEXT_in_datatype476); 
                    TEXT31_tree = (CommonTree)adaptor.create(TEXT31);
                    adaptor.addChild(root_0, TEXT31_tree);

                    retval.type = ElementaryDataType.STRING;

                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:144:3: LOGICAL
                    {
                    LOGICAL32=(Token)match(input,LOGICAL,FOLLOW_LOGICAL_in_datatype486); 
                    LOGICAL32_tree = (CommonTree)adaptor.create(LOGICAL32);
                    adaptor.addChild(root_0, LOGICAL32_tree);

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


 

    public static final BitSet FOLLOW_property_in_dsl79 = new BitSet(new long[]{0x0000000000000412L});
    public static final BitSet FOLLOW_resource_in_dsl88 = new BitSet(new long[]{0x0000000000000412L});
    public static final BitSet FOLLOW_PROPERTY_DEC_in_property130 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_property132 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_BRACKET_OPEN_in_property139 = new BitSet(new long[]{0x0000000000000380L});
    public static final BitSet FOLLOW_propertyDeclaration_in_property142 = new BitSet(new long[]{0x0000000000000380L});
    public static final BitSet FOLLOW_BRACKET_CLOSED_in_property146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDeclaration_in_propertyDeclaration169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regexDeclaration_in_propertyDeclaration175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TYPE_DEC_in_typeDeclaration193 = new BitSet(new long[]{0x000000000008E000L});
    public static final BitSet FOLLOW_19_in_typeDeclaration202 = new BitSet(new long[]{0x000000000008E000L});
    public static final BitSet FOLLOW_datatype_in_typeDeclaration212 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_19_in_typeDeclaration223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REGEX_DEC_in_regexDeclaration245 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_regexDeclaration252 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_regexDeclaration260 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_regexDeclaration269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RESOURCE_DEC_in_resource298 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_resource301 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_BRACKET_OPEN_in_resource307 = new BitSet(new long[]{0x0000000000000880L});
    public static final BitSet FOLLOW_resourceDeclaration_in_resource309 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_BRACKET_CLOSED_in_resource311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cardinality_in_resourceDeclaration323 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_resourceDeclaration333 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_20_in_referenceDeclaration363 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_referenceDeclaration365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cardinalityDeclaration_in_cardinality394 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_21_in_cardinality403 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_cardinalityDeclaration_in_cardinality409 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_CARDINALITY_in_cardinalityDeclaration440 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_INT_in_cardinalityDeclaration443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_in_datatype466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TEXT_in_datatype476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LOGICAL_in_datatype486 = new BitSet(new long[]{0x0000000000000002L});

}