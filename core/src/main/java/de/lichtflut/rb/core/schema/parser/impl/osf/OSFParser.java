// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g 2011-05-03 22:46:51

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



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class OSFParser extends Parser {
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
    public static final int WS=26;
    public static final int T__33=33;
    public static final int BOOLEAN=7;
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
    public String getGrammarFileName() { return "de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g"; }



    	private RSErrorReporter errorReporter = null;
        public void setErrorReporter(RSErrorReporter errorReporter) {
            this.errorReporter = errorReporter;
        }
        public void emitErrorMessage(String msg) {
            errorReporter.reportError(msg);
        }



    public static class osl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "osl"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:77:1: osl : ( descriptions EOF ) -> ^( DESCRIPTIONS ( descriptions )? ) ;
    public final OSFParser.osl_return osl() throws RecognitionException {
        OSFParser.osl_return retval = new OSFParser.osl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token EOF2=null;
        OSFParser.descriptions_return descriptions1 = null;


        CommonTree EOF2_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_descriptions=new RewriteRuleSubtreeStream(adaptor,"rule descriptions");
        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:77:6: ( ( descriptions EOF ) -> ^( DESCRIPTIONS ( descriptions )? ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:77:8: ( descriptions EOF )
            {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:77:8: ( descriptions EOF )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:77:9: descriptions EOF
            {
            pushFollow(FOLLOW_descriptions_in_osl146);
            descriptions1=descriptions();

            state._fsp--;

            stream_descriptions.add(descriptions1.getTree());
            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_osl148);  
            stream_EOF.add(EOF2);


            }



            // AST REWRITE
            // elements: descriptions
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 77:29: -> ^( DESCRIPTIONS ( descriptions )? )
            {
                // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:77:32: ^( DESCRIPTIONS ( descriptions )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(DESCRIPTIONS, "DESCRIPTIONS"), root_1);

                // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:77:47: ( descriptions )?
                if ( stream_descriptions.hasNext() ) {
                    adaptor.addChild(root_1, stream_descriptions.nextTree());

                }
                stream_descriptions.reset();

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
        }
        return retval;
    }
    // $ANTLR end "osl"

    public static class descriptions_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "descriptions"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:80:1: descriptions : ( description ( COMMA description )* )? ;
    public final OSFParser.descriptions_return descriptions() throws RecognitionException {
        OSFParser.descriptions_return retval = new OSFParser.descriptions_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token COMMA4=null;
        OSFParser.description_return description3 = null;

        OSFParser.description_return description5 = null;


        CommonTree COMMA4_tree=null;

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:81:2: ( ( description ( COMMA description )* )? )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:81:4: ( description ( COMMA description )* )?
            {
            root_0 = (CommonTree)adaptor.nil();

            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:81:4: ( description ( COMMA description )* )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=PROPERTY && LA2_0<=RESOURCE)) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:81:5: description ( COMMA description )*
                    {
                    pushFollow(FOLLOW_description_in_descriptions172);
                    description3=description();

                    state._fsp--;

                    adaptor.addChild(root_0, description3.getTree());
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:81:17: ( COMMA description )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==COMMA) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:81:18: COMMA description
                    	    {
                    	    COMMA4=(Token)match(input,COMMA,FOLLOW_COMMA_in_descriptions175); 
                    	    pushFollow(FOLLOW_description_in_descriptions178);
                    	    description5=description();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, description5.getTree());

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
    // $ANTLR end "descriptions"

    public static class description_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "description"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:83:1: description : ( property_dec -> ^( PROPERTY property_dec ) | resource_dec -> ^( RESOURCE resource_dec ) ) ;
    public final OSFParser.description_return description() throws RecognitionException {
        OSFParser.description_return retval = new OSFParser.description_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        OSFParser.property_dec_return property_dec6 = null;

        OSFParser.resource_dec_return resource_dec7 = null;


        RewriteRuleSubtreeStream stream_property_dec=new RewriteRuleSubtreeStream(adaptor,"rule property_dec");
        RewriteRuleSubtreeStream stream_resource_dec=new RewriteRuleSubtreeStream(adaptor,"rule resource_dec");
        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:83:14: ( ( property_dec -> ^( PROPERTY property_dec ) | resource_dec -> ^( RESOURCE resource_dec ) ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:84:2: ( property_dec -> ^( PROPERTY property_dec ) | resource_dec -> ^( RESOURCE resource_dec ) )
            {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:84:2: ( property_dec -> ^( PROPERTY property_dec ) | resource_dec -> ^( RESOURCE resource_dec ) )
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
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:84:4: property_dec
                    {
                    pushFollow(FOLLOW_property_dec_in_description195);
                    property_dec6=property_dec();

                    state._fsp--;

                    stream_property_dec.add(property_dec6.getTree());


                    // AST REWRITE
                    // elements: property_dec
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 84:17: -> ^( PROPERTY property_dec )
                    {
                        // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:84:20: ^( PROPERTY property_dec )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROPERTY, "PROPERTY"), root_1);

                        adaptor.addChild(root_1, stream_property_dec.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:85:4: resource_dec
                    {
                    pushFollow(FOLLOW_resource_dec_in_description208);
                    resource_dec7=resource_dec();

                    state._fsp--;

                    stream_resource_dec.add(resource_dec7.getTree());


                    // AST REWRITE
                    // elements: resource_dec
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 85:17: -> ^( RESOURCE resource_dec )
                    {
                        // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:85:20: ^( RESOURCE resource_dec )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(RESOURCE, "RESOURCE"), root_1);

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
    // $ANTLR end "description"

    public static class property_dec_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "property_dec"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:90:1: property_dec : PROPERTY string ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )? -> ^( string PROPERTY_ASSERTION ( ^( p_assertion value ) )* ) ;
    public final OSFParser.property_dec_return property_dec() throws RecognitionException {
        OSFParser.property_dec_return retval = new OSFParser.property_dec_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token PROPERTY8=null;
        Token char_literal10=null;
        Token char_literal12=null;
        Token COMMA14=null;
        Token char_literal16=null;
        Token char_literal18=null;
        OSFParser.string_return string9 = null;

        OSFParser.p_assertion_return p_assertion11 = null;

        OSFParser.value_return value13 = null;

        OSFParser.p_assertion_return p_assertion15 = null;

        OSFParser.value_return value17 = null;


        CommonTree PROPERTY8_tree=null;
        CommonTree char_literal10_tree=null;
        CommonTree char_literal12_tree=null;
        CommonTree COMMA14_tree=null;
        CommonTree char_literal16_tree=null;
        CommonTree char_literal18_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_PROPERTY=new RewriteRuleTokenStream(adaptor,"token PROPERTY");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_p_assertion=new RewriteRuleSubtreeStream(adaptor,"rule p_assertion");
        RewriteRuleSubtreeStream stream_string=new RewriteRuleSubtreeStream(adaptor,"rule string");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:91:2: ( PROPERTY string ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )? -> ^( string PROPERTY_ASSERTION ( ^( p_assertion value ) )* ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:91:4: PROPERTY string ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )?
            {
            PROPERTY8=(Token)match(input,PROPERTY,FOLLOW_PROPERTY_in_property_dec233);  
            stream_PROPERTY.add(PROPERTY8);

            pushFollow(FOLLOW_string_in_property_dec237);
            string9=string();

            state._fsp--;

            stream_string.add(string9.getTree());
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:93:3: ( '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==30) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:93:4: '{' ( p_assertion '=' value ( COMMA p_assertion '=' value )* )? '}'
                    {
                    char_literal10=(Token)match(input,30,FOLLOW_30_in_property_dec242);  
                    stream_30.add(char_literal10);

                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:94:3: ( p_assertion '=' value ( COMMA p_assertion '=' value )* )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( ((LA5_0>=TYPE && LA5_0<=CARDINALITY)) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:94:4: p_assertion '=' value ( COMMA p_assertion '=' value )*
                            {
                            pushFollow(FOLLOW_p_assertion_in_property_dec247);
                            p_assertion11=p_assertion();

                            state._fsp--;

                            stream_p_assertion.add(p_assertion11.getTree());
                            char_literal12=(Token)match(input,31,FOLLOW_31_in_property_dec249);  
                            stream_31.add(char_literal12);

                            pushFollow(FOLLOW_value_in_property_dec251);
                            value13=value();

                            state._fsp--;

                            stream_value.add(value13.getTree());
                            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:94:25: ( COMMA p_assertion '=' value )*
                            loop4:
                            do {
                                int alt4=2;
                                int LA4_0 = input.LA(1);

                                if ( (LA4_0==COMMA) ) {
                                    alt4=1;
                                }


                                switch (alt4) {
                            	case 1 :
                            	    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:94:26: COMMA p_assertion '=' value
                            	    {
                            	    COMMA14=(Token)match(input,COMMA,FOLLOW_COMMA_in_property_dec253);  
                            	    stream_COMMA.add(COMMA14);

                            	    pushFollow(FOLLOW_p_assertion_in_property_dec255);
                            	    p_assertion15=p_assertion();

                            	    state._fsp--;

                            	    stream_p_assertion.add(p_assertion15.getTree());
                            	    char_literal16=(Token)match(input,31,FOLLOW_31_in_property_dec257);  
                            	    stream_31.add(char_literal16);

                            	    pushFollow(FOLLOW_value_in_property_dec259);
                            	    value17=value();

                            	    state._fsp--;

                            	    stream_value.add(value17.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop4;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal18=(Token)match(input,32,FOLLOW_32_in_property_dec268);  
                    stream_32.add(char_literal18);


                    }
                    break;

            }



            // AST REWRITE
            // elements: p_assertion, value, string
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 95:9: -> ^( string PROPERTY_ASSERTION ( ^( p_assertion value ) )* )
            {
                // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:95:12: ^( string PROPERTY_ASSERTION ( ^( p_assertion value ) )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(stream_string.nextNode(), root_1);

                adaptor.addChild(root_1, (CommonTree)adaptor.create(PROPERTY_ASSERTION, "PROPERTY_ASSERTION"));
                // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:95:40: ( ^( p_assertion value ) )*
                while ( stream_p_assertion.hasNext()||stream_value.hasNext() ) {
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:95:41: ^( p_assertion value )
                    {
                    CommonTree root_2 = (CommonTree)adaptor.nil();
                    root_2 = (CommonTree)adaptor.becomeRoot(stream_p_assertion.nextNode(), root_2);

                    adaptor.addChild(root_2, stream_value.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_p_assertion.reset();
                stream_value.reset();

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
        }
        return retval;
    }
    // $ANTLR end "property_dec"

    public static class resource_dec_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "resource_dec"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:97:1: resource_dec : RESOURCE string '{' ( property_dec ( ',' property_dec )* )? '}' -> ^( string PROPERTY_DEC ( property_dec )* ) ;
    public final OSFParser.resource_dec_return resource_dec() throws RecognitionException {
        OSFParser.resource_dec_return retval = new OSFParser.resource_dec_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token RESOURCE19=null;
        Token char_literal21=null;
        Token char_literal23=null;
        Token char_literal25=null;
        OSFParser.string_return string20 = null;

        OSFParser.property_dec_return property_dec22 = null;

        OSFParser.property_dec_return property_dec24 = null;


        CommonTree RESOURCE19_tree=null;
        CommonTree char_literal21_tree=null;
        CommonTree char_literal23_tree=null;
        CommonTree char_literal25_tree=null;
        RewriteRuleTokenStream stream_30=new RewriteRuleTokenStream(adaptor,"token 30");
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_RESOURCE=new RewriteRuleTokenStream(adaptor,"token RESOURCE");
        RewriteRuleSubtreeStream stream_property_dec=new RewriteRuleSubtreeStream(adaptor,"rule property_dec");
        RewriteRuleSubtreeStream stream_string=new RewriteRuleSubtreeStream(adaptor,"rule string");
        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:98:2: ( RESOURCE string '{' ( property_dec ( ',' property_dec )* )? '}' -> ^( string PROPERTY_DEC ( property_dec )* ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:98:4: RESOURCE string '{' ( property_dec ( ',' property_dec )* )? '}'
            {
            RESOURCE19=(Token)match(input,RESOURCE,FOLLOW_RESOURCE_in_resource_dec299);  
            stream_RESOURCE.add(RESOURCE19);

            pushFollow(FOLLOW_string_in_resource_dec303);
            string20=string();

            state._fsp--;

            stream_string.add(string20.getTree());
            char_literal21=(Token)match(input,30,FOLLOW_30_in_resource_dec307);  
            stream_30.add(char_literal21);

            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:101:3: ( property_dec ( ',' property_dec )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==PROPERTY) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:101:4: property_dec ( ',' property_dec )*
                    {
                    pushFollow(FOLLOW_property_dec_in_resource_dec312);
                    property_dec22=property_dec();

                    state._fsp--;

                    stream_property_dec.add(property_dec22.getTree());
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:101:17: ( ',' property_dec )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==COMMA) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:101:18: ',' property_dec
                    	    {
                    	    char_literal23=(Token)match(input,COMMA,FOLLOW_COMMA_in_resource_dec315);  
                    	    stream_COMMA.add(char_literal23);

                    	    pushFollow(FOLLOW_property_dec_in_resource_dec317);
                    	    property_dec24=property_dec();

                    	    state._fsp--;

                    	    stream_property_dec.add(property_dec24.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    }
                    break;

            }

            char_literal25=(Token)match(input,32,FOLLOW_32_in_resource_dec326);  
            stream_32.add(char_literal25);



            // AST REWRITE
            // elements: string, property_dec
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 102:7: -> ^( string PROPERTY_DEC ( property_dec )* )
            {
                // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:102:10: ^( string PROPERTY_DEC ( property_dec )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(stream_string.nextNode(), root_1);

                adaptor.addChild(root_1, (CommonTree)adaptor.create(PROPERTY_DEC, "PROPERTY_DEC"));
                // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:102:32: ( property_dec )*
                while ( stream_property_dec.hasNext() ) {
                    adaptor.addChild(root_1, stream_property_dec.nextTree());

                }
                stream_property_dec.reset();

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
        }
        return retval;
    }
    // $ANTLR end "resource_dec"

    public static class p_assertion_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "p_assertion"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:106:1: p_assertion : ( TYPE -> TYPE | MAX -> MAX | MIN -> MIN | REGEX -> REGEX | CARDINALITY -> CARDINALITY );
    public final OSFParser.p_assertion_return p_assertion() throws RecognitionException {
        OSFParser.p_assertion_return retval = new OSFParser.p_assertion_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token TYPE26=null;
        Token MAX27=null;
        Token MIN28=null;
        Token REGEX29=null;
        Token CARDINALITY30=null;

        CommonTree TYPE26_tree=null;
        CommonTree MAX27_tree=null;
        CommonTree MIN28_tree=null;
        CommonTree REGEX29_tree=null;
        CommonTree CARDINALITY30_tree=null;
        RewriteRuleTokenStream stream_MAX=new RewriteRuleTokenStream(adaptor,"token MAX");
        RewriteRuleTokenStream stream_REGEX=new RewriteRuleTokenStream(adaptor,"token REGEX");
        RewriteRuleTokenStream stream_MIN=new RewriteRuleTokenStream(adaptor,"token MIN");
        RewriteRuleTokenStream stream_TYPE=new RewriteRuleTokenStream(adaptor,"token TYPE");
        RewriteRuleTokenStream stream_CARDINALITY=new RewriteRuleTokenStream(adaptor,"token CARDINALITY");

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:107:2: ( TYPE -> TYPE | MAX -> MAX | MIN -> MIN | REGEX -> REGEX | CARDINALITY -> CARDINALITY )
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
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:107:4: TYPE
                    {
                    TYPE26=(Token)match(input,TYPE,FOLLOW_TYPE_in_p_assertion351);  
                    stream_TYPE.add(TYPE26);



                    // AST REWRITE
                    // elements: TYPE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 107:9: -> TYPE
                    {
                        adaptor.addChild(root_0, stream_TYPE.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:108:4: MAX
                    {
                    MAX27=(Token)match(input,MAX,FOLLOW_MAX_in_p_assertion360);  
                    stream_MAX.add(MAX27);



                    // AST REWRITE
                    // elements: MAX
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 108:9: -> MAX
                    {
                        adaptor.addChild(root_0, stream_MAX.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:109:4: MIN
                    {
                    MIN28=(Token)match(input,MIN,FOLLOW_MIN_in_p_assertion370);  
                    stream_MIN.add(MIN28);



                    // AST REWRITE
                    // elements: MIN
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 109:9: -> MIN
                    {
                        adaptor.addChild(root_0, stream_MIN.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:110:4: REGEX
                    {
                    REGEX29=(Token)match(input,REGEX,FOLLOW_REGEX_in_p_assertion380);  
                    stream_REGEX.add(REGEX29);



                    // AST REWRITE
                    // elements: REGEX
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 110:10: -> REGEX
                    {
                        adaptor.addChild(root_0, stream_REGEX.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:111:4: CARDINALITY
                    {
                    CARDINALITY30=(Token)match(input,CARDINALITY,FOLLOW_CARDINALITY_in_p_assertion389);  
                    stream_CARDINALITY.add(CARDINALITY30);



                    // AST REWRITE
                    // elements: CARDINALITY
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 111:16: -> CARDINALITY
                    {
                        adaptor.addChild(root_0, stream_CARDINALITY.nextNode());

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
        }
        return retval;
    }
    // $ANTLR end "p_assertion"

    public static class value_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "value"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:114:1: value : ( string | number | array | TEXT -> TEXT | BOOLEAN -> BOOLEAN | NUMBER -> NUMBER | TRUE -> TRUE | FALSE -> FALSE | NULL -> NULL );
    public final OSFParser.value_return value() throws RecognitionException {
        OSFParser.value_return retval = new OSFParser.value_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token TEXT34=null;
        Token BOOLEAN35=null;
        Token NUMBER36=null;
        Token TRUE37=null;
        Token FALSE38=null;
        Token NULL39=null;
        OSFParser.string_return string31 = null;

        OSFParser.number_return number32 = null;

        OSFParser.array_return array33 = null;


        CommonTree TEXT34_tree=null;
        CommonTree BOOLEAN35_tree=null;
        CommonTree NUMBER36_tree=null;
        CommonTree TRUE37_tree=null;
        CommonTree FALSE38_tree=null;
        CommonTree NULL39_tree=null;
        RewriteRuleTokenStream stream_BOOLEAN=new RewriteRuleTokenStream(adaptor,"token BOOLEAN");
        RewriteRuleTokenStream stream_TEXT=new RewriteRuleTokenStream(adaptor,"token TEXT");
        RewriteRuleTokenStream stream_FALSE=new RewriteRuleTokenStream(adaptor,"token FALSE");
        RewriteRuleTokenStream stream_TRUE=new RewriteRuleTokenStream(adaptor,"token TRUE");
        RewriteRuleTokenStream stream_NULL=new RewriteRuleTokenStream(adaptor,"token NULL");
        RewriteRuleTokenStream stream_NUMBER=new RewriteRuleTokenStream(adaptor,"token NUMBER");

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:116:2: ( string | number | array | TEXT -> TEXT | BOOLEAN -> BOOLEAN | NUMBER -> NUMBER | TRUE -> TRUE | FALSE -> FALSE | NULL -> NULL )
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
            case 33:
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
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:116:4: string
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_string_in_value405);
                    string31=string();

                    state._fsp--;

                    adaptor.addChild(root_0, string31.getTree());

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:117:4: number
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_number_in_value410);
                    number32=number();

                    state._fsp--;

                    adaptor.addChild(root_0, number32.getTree());

                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:118:4: array
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_array_in_value415);
                    array33=array();

                    state._fsp--;

                    adaptor.addChild(root_0, array33.getTree());

                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:119:4: TEXT
                    {
                    TEXT34=(Token)match(input,TEXT,FOLLOW_TEXT_in_value420);  
                    stream_TEXT.add(TEXT34);



                    // AST REWRITE
                    // elements: TEXT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 119:9: -> TEXT
                    {
                        adaptor.addChild(root_0, stream_TEXT.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:120:4: BOOLEAN
                    {
                    BOOLEAN35=(Token)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_value429);  
                    stream_BOOLEAN.add(BOOLEAN35);



                    // AST REWRITE
                    // elements: BOOLEAN
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 120:12: -> BOOLEAN
                    {
                        adaptor.addChild(root_0, stream_BOOLEAN.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:121:4: NUMBER
                    {
                    NUMBER36=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_value438);  
                    stream_NUMBER.add(NUMBER36);



                    // AST REWRITE
                    // elements: NUMBER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 121:11: -> NUMBER
                    {
                        adaptor.addChild(root_0, stream_NUMBER.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 7 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:122:4: TRUE
                    {
                    TRUE37=(Token)match(input,TRUE,FOLLOW_TRUE_in_value447);  
                    stream_TRUE.add(TRUE37);



                    // AST REWRITE
                    // elements: TRUE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 122:9: -> TRUE
                    {
                        adaptor.addChild(root_0, stream_TRUE.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 8 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:123:4: FALSE
                    {
                    FALSE38=(Token)match(input,FALSE,FOLLOW_FALSE_in_value456);  
                    stream_FALSE.add(FALSE38);



                    // AST REWRITE
                    // elements: FALSE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 123:10: -> FALSE
                    {
                        adaptor.addChild(root_0, stream_FALSE.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 9 :
                    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:124:4: NULL
                    {
                    NULL39=(Token)match(input,NULL,FOLLOW_NULL_in_value465);  
                    stream_NULL.add(NULL39);



                    // AST REWRITE
                    // elements: NULL
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 124:9: -> NULL
                    {
                        adaptor.addChild(root_0, stream_NULL.nextNode());

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
        }
        return retval;
    }
    // $ANTLR end "value"

    public static class string_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "string"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:128:1: string : String -> ^( STRING String ) ;
    public final OSFParser.string_return string() throws RecognitionException {
        OSFParser.string_return retval = new OSFParser.string_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token String40=null;

        CommonTree String40_tree=null;
        RewriteRuleTokenStream stream_String=new RewriteRuleTokenStream(adaptor,"token String");

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:128:9: ( String -> ^( STRING String ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:128:11: String
            {
            String40=(Token)match(input,String,FOLLOW_String_in_string481);  
            stream_String.add(String40);



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
            // 129:4: -> ^( STRING String )
            {
                // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:129:7: ^( STRING String )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(STRING, "STRING"), root_1);

                adaptor.addChild(root_1, stream_String.nextNode());

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
        }
        return retval;
    }
    // $ANTLR end "string"

    public static class number_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "number"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:132:1: number : Number -> ^( NUMBER Number ) ;
    public final OSFParser.number_return number() throws RecognitionException {
        OSFParser.number_return retval = new OSFParser.number_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token Number41=null;

        CommonTree Number41_tree=null;
        RewriteRuleTokenStream stream_Number=new RewriteRuleTokenStream(adaptor,"token Number");

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:132:8: ( Number -> ^( NUMBER Number ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:132:10: Number
            {
            Number41=(Token)match(input,Number,FOLLOW_Number_in_number502);  
            stream_Number.add(Number41);



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
            // 132:17: -> ^( NUMBER Number )
            {
                // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:132:20: ^( NUMBER Number )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(NUMBER, "NUMBER"), root_1);

                adaptor.addChild(root_1, stream_Number.nextNode());

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
        }
        return retval;
    }
    // $ANTLR end "number"

    public static class array_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "array"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:136:1: array : '[' elements ']' -> ^( ARRAY elements ) ;
    public final OSFParser.array_return array() throws RecognitionException {
        OSFParser.array_return retval = new OSFParser.array_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal42=null;
        Token char_literal44=null;
        OSFParser.elements_return elements43 = null;


        CommonTree char_literal42_tree=null;
        CommonTree char_literal44_tree=null;
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
        RewriteRuleSubtreeStream stream_elements=new RewriteRuleSubtreeStream(adaptor,"rule elements");
        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:136:7: ( '[' elements ']' -> ^( ARRAY elements ) )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:136:9: '[' elements ']'
            {
            char_literal42=(Token)match(input,33,FOLLOW_33_in_array521);  
            stream_33.add(char_literal42);

            pushFollow(FOLLOW_elements_in_array523);
            elements43=elements();

            state._fsp--;

            stream_elements.add(elements43.getTree());
            char_literal44=(Token)match(input,34,FOLLOW_34_in_array525);  
            stream_34.add(char_literal44);



            // AST REWRITE
            // elements: elements
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 136:26: -> ^( ARRAY elements )
            {
                // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:136:29: ^( ARRAY elements )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ARRAY, "ARRAY"), root_1);

                adaptor.addChild(root_1, stream_elements.nextTree());

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
        }
        return retval;
    }
    // $ANTLR end "array"

    public static class elements_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "elements"
    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:139:1: elements : value ( COMMA value )* ;
    public final OSFParser.elements_return elements() throws RecognitionException {
        OSFParser.elements_return retval = new OSFParser.elements_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token COMMA46=null;
        OSFParser.value_return value45 = null;

        OSFParser.value_return value47 = null;


        CommonTree COMMA46_tree=null;

        try {
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:139:9: ( value ( COMMA value )* )
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:139:11: value ( COMMA value )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_value_in_elements542);
            value45=value();

            state._fsp--;

            adaptor.addChild(root_0, value45.getTree());
            // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:139:17: ( COMMA value )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==COMMA) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/parser/impl/osf/OSF.g:139:18: COMMA value
            	    {
            	    COMMA46=(Token)match(input,COMMA,FOLLOW_COMMA_in_elements545); 
            	    pushFollow(FOLLOW_value_in_elements548);
            	    value47=value();

            	    state._fsp--;

            	    adaptor.addChild(root_0, value47.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
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
    // $ANTLR end "elements"

    // Delegated rules


 

    public static final BitSet FOLLOW_descriptions_in_osl146 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_osl148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_description_in_descriptions172 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_COMMA_in_descriptions175 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_description_in_descriptions178 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_property_dec_in_description195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_dec_in_description208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_in_property_dec233 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_string_in_property_dec237 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_30_in_property_dec242 = new BitSet(new long[]{0x0000000100007C00L});
    public static final BitSet FOLLOW_p_assertion_in_property_dec247 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_property_dec249 = new BitSet(new long[]{0x00000002018381A0L});
    public static final BitSet FOLLOW_value_in_property_dec251 = new BitSet(new long[]{0x0000000100000200L});
    public static final BitSet FOLLOW_COMMA_in_property_dec253 = new BitSet(new long[]{0x0000000000007C00L});
    public static final BitSet FOLLOW_p_assertion_in_property_dec255 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_property_dec257 = new BitSet(new long[]{0x00000002018381A0L});
    public static final BitSet FOLLOW_value_in_property_dec259 = new BitSet(new long[]{0x0000000100000200L});
    public static final BitSet FOLLOW_32_in_property_dec268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RESOURCE_in_resource_dec299 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_string_in_resource_dec303 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_resource_dec307 = new BitSet(new long[]{0x0000000100040000L});
    public static final BitSet FOLLOW_property_dec_in_resource_dec312 = new BitSet(new long[]{0x0000000100000200L});
    public static final BitSet FOLLOW_COMMA_in_resource_dec315 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_property_dec_in_resource_dec317 = new BitSet(new long[]{0x0000000100000200L});
    public static final BitSet FOLLOW_32_in_resource_dec326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TYPE_in_p_assertion351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MAX_in_p_assertion360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIN_in_p_assertion370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REGEX_in_p_assertion380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CARDINALITY_in_p_assertion389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_in_value405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_number_in_value410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_in_value415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TEXT_in_value420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOLEAN_in_value429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_value438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_value447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_value456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_value465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_String_in_string481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Number_in_number502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_array521 = new BitSet(new long[]{0x00000002018381A0L});
    public static final BitSet FOLLOW_elements_in_array523 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_array525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_elements542 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_COMMA_in_elements545 = new BitSet(new long[]{0x00000002018381A0L});
    public static final BitSet FOLLOW_value_in_elements548 = new BitSet(new long[]{0x0000000000000202L});

}