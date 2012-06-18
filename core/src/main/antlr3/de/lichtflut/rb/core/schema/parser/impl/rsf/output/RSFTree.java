// $ANTLR 3.4 RSFTree.g 2012-03-29 13:54:55

/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.parser.impl.rsf;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.NamespaceHandle;

import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;


import java.util.Locale;
import java.util.HashMap;



import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class RSFTree extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ASSIGMENT", "CARDINALITY", "CARDINALITY_DECL", "COLON", "CONSTRAINT", "FIELD_LABEL", "INT_LABEL", "LABEL", "LABEL_RULE", "NAMESPACE", "NS", "PREFIX", "PROPERTY", "PROPERTY_DECL", "RESOURCE_CONSTRAINT", "SCHEMA", "SCHEMA_FOR", "STATEMENTS", "STRING", "TYPE_DEF", "WS", "'{'", "'}'"
    };

    public static final int EOF=-1;
    public static final int T__25=25;
    public static final int T__26=26;
    public static final int ASSIGMENT=4;
    public static final int CARDINALITY=5;
    public static final int CARDINALITY_DECL=6;
    public static final int COLON=7;
    public static final int CONSTRAINT=8;
    public static final int FIELD_LABEL=9;
    public static final int INT_LABEL=10;
    public static final int LABEL=11;
    public static final int LABEL_RULE=12;
    public static final int NAMESPACE=13;
    public static final int NS=14;
    public static final int PREFIX=15;
    public static final int PROPERTY=16;
    public static final int PROPERTY_DECL=17;
    public static final int RESOURCE_CONSTRAINT=18;
    public static final int SCHEMA=19;
    public static final int SCHEMA_FOR=20;
    public static final int STATEMENTS=21;
    public static final int STRING=22;
    public static final int TYPE_DEF=23;
    public static final int WS=24;

    // delegates
    public TreeParser[] getDelegates() {
        return new TreeParser[] {};
    }

    // delegators


    public RSFTree(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }
    public RSFTree(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return RSFTree.tokenNames; }
    public String getGrammarFileName() { return "RSFTree.g"; }



    	private static final String TYPE_DEF_CONST = "type-definition";
    	private static final String RESOURCE_CONSTRAINT_CONST = "resource-constraint";
    	private static final String FIELD_LABEL_CONST = "field-label";
    	private static final String FIELD_LABEL_INT_CONST = "field-label\\[..\\]";
    	
    	private List<ResourceSchemaImpl> schemaList = new ArrayList<ResourceSchemaImpl>();
    	private HashMap<String, NamespaceHandle> nsMap = new HashMap<String, NamespaceHandle>();
    	private RSErrorReporter errorReporter;

    	public void setErrorReporter(RSErrorReporter errorReporter) {
           	this.errorReporter = errorReporter;
        }
        
        public void emitErrorMessage(String msg) {
           	errorReporter.reportError(msg);
        }
        
    	private String removeAll(String s, String remove){
    		s = s.replaceAll(remove, "");
    		return s;
    	}
    	
    	private String extract(String s, String delim, int pos){
    		String[] array = s.split(delim);
    		return array[pos];
    	}
    	
    	private void buildTypeDef(String key, String value){
    		String ns = ((schema_decl_scope)schema_decl_stack.peek()).currentNS.getUri();
    		if(TYPE_DEF_CONST.equals(key)){
    			TypeDefinitionImpl def = ((property_decl_scope)property_decl_stack.peek()).def;
    			def.setName(ns+key);
    			def.setElementaryDataType(Datatype.valueOf(value.toUpperCase()));
    		}
    		if(RESOURCE_CONSTRAINT_CONST.equals(key)){
    			TypeDefinitionImpl def = ((property_decl_scope)property_decl_stack.peek()).def;
    			def.addConstraint(ConstraintBuilder.buildResourceConstraint(toResourceID(value)));
    		}
    		if(FIELD_LABEL_CONST.equals(key)){
    			PropertyDeclarationImpl pDec = ((property_decl_scope)property_decl_stack.peek()).pDec;
    			if(pDec.getFieldLabelDefinition() == null){
    				pDec.setFieldLabelDefinition(new FieldLabelDefinitionImpl(value));
    			}else{
    				pDec.getFieldLabelDefinition().setDefaultLabel(value);
    			}
    		}
    		if(key.matches(FIELD_LABEL_INT_CONST)){
    			PropertyDeclarationImpl pDec = ((property_decl_scope)property_decl_stack.peek()).pDec;
    			if(pDec.getFieldLabelDefinition() == null){
    				pDec.setFieldLabelDefinition(new FieldLabelDefinitionImpl(value));
    			}
    			String locale = key.substring(12, 14);
    			pDec.getFieldLabelDefinition().setLabel(new Locale(locale), value);
    		}
    	}
    	
    	public ResourceID toResourceID(final String name) {
    		if (QualifiedName.isUri(name)) {
    			return new SimpleResourceID(name); 
    		} else if (!QualifiedName.isQname(name)) {
    			throw new IllegalArgumentException("Name is neither URI nor QName: " + name);
    		}
    		final String prefix = QualifiedName.getPrefix(name);
    		final String simpleName = QualifiedName.getSimpleName(name);
    		if (nsMap.containsKey(prefix)) {
    			return new SimpleResourceID(nsMap.get(prefix).getUri(), simpleName);
    		} else {
    			final NamespaceHandle handle = new NamespaceHandle(null, prefix);
    			nsMap.put(prefix, handle);
    			return new SimpleResourceID(handle.getUri(), simpleName);	
    		}
    	}



    // $ANTLR start "statements"
    // RSFTree.g:113:1: statements returns [ List<ResourceSchemaImpl> list ] : ^( STATEMENTS ( statement )+ ) ;
    public final List<ResourceSchemaImpl> statements() throws RecognitionException {
        List<ResourceSchemaImpl> list = null;



            	list = new ArrayList<ResourceSchemaImpl>();
            
        try {
            // RSFTree.g:118:2: ( ^( STATEMENTS ( statement )+ ) )
            // RSFTree.g:118:6: ^( STATEMENTS ( statement )+ )
            {
            match(input,STATEMENTS,FOLLOW_STATEMENTS_in_statements66); 

            match(input, Token.DOWN, null); 
            // RSFTree.g:118:19: ( statement )+
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
            	    // RSFTree.g:118:19: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_statements68);
            	    statement();

            	    state._fsp--;


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


            match(input, Token.UP, null); 


            list.addAll(schemaList); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return list;
    }
    // $ANTLR end "statements"



    // $ANTLR start "statement"
    // RSFTree.g:121:1: statement : ( namespace_decl | schema_decl );
    public final void statement() throws RecognitionException {
        try {
            // RSFTree.g:121:11: ( namespace_decl | schema_decl )
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
                    // RSFTree.g:121:14: namespace_decl
                    {
                    pushFollow(FOLLOW_namespace_decl_in_statement84);
                    namespace_decl();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // RSFTree.g:122:7: schema_decl
                    {
                    pushFollow(FOLLOW_schema_decl_in_statement93);
                    schema_decl();

                    state._fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "statement"



    // $ANTLR start "namespace_decl"
    // RSFTree.g:126:1: namespace_decl : ^( NAMESPACE (ns= STRING qn= STRING ) ) ;
    public final void namespace_decl() throws RecognitionException {
        CommonTree ns=null;
        CommonTree qn=null;

        try {
            // RSFTree.g:126:15: ( ^( NAMESPACE (ns= STRING qn= STRING ) ) )
            // RSFTree.g:126:17: ^( NAMESPACE (ns= STRING qn= STRING ) )
            {
            match(input,NAMESPACE,FOLLOW_NAMESPACE_in_namespace_decl107); 

            match(input, Token.DOWN, null); 
            // RSFTree.g:126:29: (ns= STRING qn= STRING )
            // RSFTree.g:126:30: ns= STRING qn= STRING
            {
            ns=(CommonTree)match(input,STRING,FOLLOW_STRING_in_namespace_decl112); 

            qn=(CommonTree)match(input,STRING,FOLLOW_STRING_in_namespace_decl116); 


            		String prefix = removeAll((qn!=null?qn.getText():null), "\"");
            		String uri = removeAll((ns!=null?ns.getText():null), "\"");
            		nsMap.put(prefix, new NamespaceHandle(uri, prefix));
            		

            }


            match(input, Token.UP, null); 


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "namespace_decl"


    protected static class schema_decl_scope {
        ResourceSchemaImpl schema;
        NamespaceHandle currentNS;
    }
    protected Stack schema_decl_stack = new Stack();



    // $ANTLR start "schema_decl"
    // RSFTree.g:136:1: schema_decl : ^( SCHEMA (id= STRING ) ( decl )+ ) ;
    public final void schema_decl() throws RecognitionException {
        schema_decl_stack.push(new schema_decl_scope());
        CommonTree id=null;

        try {
            // RSFTree.g:140:5: ( ^( SCHEMA (id= STRING ) ( decl )+ ) )
            // RSFTree.g:140:8: ^( SCHEMA (id= STRING ) ( decl )+ )
            {
            match(input,SCHEMA,FOLLOW_SCHEMA_in_schema_decl144); 

            match(input, Token.DOWN, null); 
            // RSFTree.g:141:4: (id= STRING )
            // RSFTree.g:141:5: id= STRING
            {
            id=(CommonTree)match(input,STRING,FOLLOW_STRING_in_schema_decl153); 


            				String cleaned = removeAll((id!=null?id.getText():null), "\"");
            				((schema_decl_scope)schema_decl_stack.peek()).currentNS = nsMap.get(extract(cleaned, ":", 0));
            				String described = extract(cleaned, ":", 1);
            				((schema_decl_scope)schema_decl_stack.peek()).schema = new ResourceSchemaImpl(new SimpleResourceID(((schema_decl_scope)schema_decl_stack.peek()).currentNS.getUri() + described));
            				schemaList.add(((schema_decl_scope)schema_decl_stack.peek()).schema);
            			

            }


            // RSFTree.g:148:5: ( decl )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==LABEL||LA3_0==PROPERTY) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // RSFTree.g:148:5: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_schema_decl162);
            	    decl();

            	    state._fsp--;


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


            match(input, Token.UP, null); 


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
            schema_decl_stack.pop();
        }
        return ;
    }
    // $ANTLR end "schema_decl"



    // $ANTLR start "decl"
    // RSFTree.g:152:1: decl : ( label_decl | property_decl );
    public final void decl() throws RecognitionException {
        try {
            // RSFTree.g:152:6: ( label_decl | property_decl )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LABEL) ) {
                alt4=1;
            }
            else if ( (LA4_0==PROPERTY) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // RSFTree.g:152:10: label_decl
                    {
                    pushFollow(FOLLOW_label_decl_in_decl184);
                    label_decl();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // RSFTree.g:153:5: property_decl
                    {
                    pushFollow(FOLLOW_property_decl_in_decl190);
                    property_decl();

                    state._fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "decl"



    // $ANTLR start "label_decl"
    // RSFTree.g:157:1: label_decl : ^( LABEL (rule= STRING ) ) ;
    public final void label_decl() throws RecognitionException {
        CommonTree rule=null;

        try {
            // RSFTree.g:157:11: ( ^( LABEL (rule= STRING ) ) )
            // RSFTree.g:157:14: ^( LABEL (rule= STRING ) )
            {
            match(input,LABEL,FOLLOW_LABEL_in_label_decl203); 

            match(input, Token.DOWN, null); 
            // RSFTree.g:157:22: (rule= STRING )
            // RSFTree.g:157:23: rule= STRING
            {
            rule=(CommonTree)match(input,STRING,FOLLOW_STRING_in_label_decl208); 


            						String cleaned = removeAll((rule!=null?rule.getText():null), "\"");
            						try{
            						((schema_decl_scope)schema_decl_stack.peek()).schema.setLabelBuilder(new ExpressionBasedLabelBuilder(cleaned, nsMap));
            						}catch (LabelExpressionParseException e) {
            							emitErrorMessage(e.getMessage());
            						}


            }


            match(input, Token.UP, null); 


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "label_decl"


    protected static class property_decl_scope {
        TypeDefinitionImpl def;
        PropertyDeclarationImpl pDec;
    }
    protected Stack property_decl_stack = new Stack();



    // $ANTLR start "property_decl"
    // RSFTree.g:167:1: property_decl : ^( PROPERTY (s= STRING cardinal_decl ( ( assigment )+ ) ) ) ;
    public final void property_decl() throws RecognitionException {
        property_decl_stack.push(new property_decl_scope());
        CommonTree s=null;
        RSFTree.cardinal_decl_return cardinal_decl1 =null;



        	((property_decl_scope)property_decl_stack.peek()).def = new TypeDefinitionImpl(new SimpleResourceID(), true);
        	((property_decl_scope)property_decl_stack.peek()).pDec = new PropertyDeclarationImpl();


        try {
            // RSFTree.g:175:2: ( ^( PROPERTY (s= STRING cardinal_decl ( ( assigment )+ ) ) ) )
            // RSFTree.g:175:4: ^( PROPERTY (s= STRING cardinal_decl ( ( assigment )+ ) ) )
            {
            match(input,PROPERTY,FOLLOW_PROPERTY_in_property_decl228); 

            match(input, Token.DOWN, null); 
            // RSFTree.g:175:15: (s= STRING cardinal_decl ( ( assigment )+ ) )
            // RSFTree.g:175:16: s= STRING cardinal_decl ( ( assigment )+ )
            {
            s=(CommonTree)match(input,STRING,FOLLOW_STRING_in_property_decl233); 

            pushFollow(FOLLOW_cardinal_decl_in_property_decl235);
            cardinal_decl1=cardinal_decl();

            state._fsp--;


            // RSFTree.g:175:39: ( ( assigment )+ )
            // RSFTree.g:175:40: ( assigment )+
            {
            // RSFTree.g:175:40: ( assigment )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ASSIGMENT) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // RSFTree.g:175:40: assigment
            	    {
            	    pushFollow(FOLLOW_assigment_in_property_decl238);
            	    assigment();

            	    state._fsp--;


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


            }



            					String cleaned = removeAll((s!=null?s.getText():null), "\"");
            					ResourceID sid = toResourceID(cleaned);
            					((property_decl_scope)property_decl_stack.peek()).pDec.setPropertyDescriptor(sid);
            					((property_decl_scope)property_decl_stack.peek()).pDec.setTypeDefinition(((property_decl_scope)property_decl_stack.peek()).def);
            					((property_decl_scope)property_decl_stack.peek()).pDec.setCardinality(CardinalityBuilder.extractFromString((cardinal_decl1!=null?(input.getTokenStream().toString(input.getTreeAdaptor().getTokenStartIndex(cardinal_decl1.start),input.getTreeAdaptor().getTokenStopIndex(cardinal_decl1.start))):null)));
            					((schema_decl_scope)schema_decl_stack.peek()).schema.addPropertyDeclaration(((property_decl_scope)property_decl_stack.peek()).pDec);
            				

            }


            match(input, Token.UP, null); 


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
            property_decl_stack.pop();
        }
        return ;
    }
    // $ANTLR end "property_decl"


    public static class cardinal_decl_return extends TreeRuleReturnScope {
        public String s;
    };


    // $ANTLR start "cardinal_decl"
    // RSFTree.g:186:1: cardinal_decl returns [String s] : ^( CARDINALITY CARDINALITY_DECL ) ;
    public final RSFTree.cardinal_decl_return cardinal_decl() throws RecognitionException {
        RSFTree.cardinal_decl_return retval = new RSFTree.cardinal_decl_return();
        retval.start = input.LT(1);



        	retval.s = input.getTokenStream().toString(input.getTreeAdaptor().getTokenStartIndex(retval.start),input.getTreeAdaptor().getTokenStopIndex(retval.start));

        try {
            // RSFTree.g:189:3: ( ^( CARDINALITY CARDINALITY_DECL ) )
            // RSFTree.g:189:6: ^( CARDINALITY CARDINALITY_DECL )
            {
            match(input,CARDINALITY,FOLLOW_CARDINALITY_in_cardinal_decl269); 

            match(input, Token.DOWN, null); 
            match(input,CARDINALITY_DECL,FOLLOW_CARDINALITY_DECL_in_cardinal_decl271); 

            match(input, Token.UP, null); 


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "cardinal_decl"



    // $ANTLR start "assigment"
    // RSFTree.g:192:1: assigment : ^( ASSIGMENT ( key value ) ) ;
    public final void assigment() throws RecognitionException {
        RSFTree.key_return key2 =null;

        RSFTree.value_return value3 =null;


        try {
            // RSFTree.g:192:11: ( ^( ASSIGMENT ( key value ) ) )
            // RSFTree.g:193:6: ^( ASSIGMENT ( key value ) )
            {
            match(input,ASSIGMENT,FOLLOW_ASSIGMENT_in_assigment288); 

            match(input, Token.DOWN, null); 
            // RSFTree.g:193:18: ( key value )
            // RSFTree.g:193:19: key value
            {
            pushFollow(FOLLOW_key_in_assigment291);
            key2=key();

            state._fsp--;


            pushFollow(FOLLOW_value_in_assigment293);
            value3=value();

            state._fsp--;



            					buildTypeDef((key2!=null?(input.getTokenStream().toString(input.getTreeAdaptor().getTokenStartIndex(key2.start),input.getTreeAdaptor().getTokenStopIndex(key2.start))):null), removeAll((value3!=null?(input.getTokenStream().toString(input.getTreeAdaptor().getTokenStartIndex(value3.start),input.getTreeAdaptor().getTokenStopIndex(value3.start))):null), "\""));
            				

            }


            match(input, Token.UP, null); 


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "assigment"


    public static class key_return extends TreeRuleReturnScope {
        public String s;
    };


    // $ANTLR start "key"
    // RSFTree.g:198:1: key returns [String s] : ( FIELD_LABEL | INT_LABEL | TYPE_DEF | RESOURCE_CONSTRAINT );
    public final RSFTree.key_return key() throws RecognitionException {
        RSFTree.key_return retval = new RSFTree.key_return();
        retval.start = input.LT(1);



        	retval.s = input.getTokenStream().toString(input.getTreeAdaptor().getTokenStartIndex(retval.start),input.getTreeAdaptor().getTokenStopIndex(retval.start));

        try {
            // RSFTree.g:202:2: ( FIELD_LABEL | INT_LABEL | TYPE_DEF | RESOURCE_CONSTRAINT )
            // RSFTree.g:
            {
            if ( (input.LA(1) >= FIELD_LABEL && input.LA(1) <= INT_LABEL)||input.LA(1)==RESOURCE_CONSTRAINT||input.LA(1)==TYPE_DEF ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "key"


    public static class value_return extends TreeRuleReturnScope {
        public String s;
    };


    // $ANTLR start "value"
    // RSFTree.g:210:1: value returns [String s] : STRING ;
    public final RSFTree.value_return value() throws RecognitionException {
        RSFTree.value_return retval = new RSFTree.value_return();
        retval.start = input.LT(1);


        CommonTree STRING4=null;

        try {
            // RSFTree.g:210:26: ( STRING )
            // RSFTree.g:210:28: STRING
            {
            STRING4=(CommonTree)match(input,STRING,FOLLOW_STRING_in_value352); 

            retval.s = (STRING4!=null?STRING4.getText():null);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "value"

    // Delegated rules


 

    public static final BitSet FOLLOW_STATEMENTS_in_statements66 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_in_statements68 = new BitSet(new long[]{0x0000000000082008L});
    public static final BitSet FOLLOW_namespace_decl_in_statement84 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_schema_decl_in_statement93 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAMESPACE_in_namespace_decl107 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_namespace_decl112 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_STRING_in_namespace_decl116 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SCHEMA_in_schema_decl144 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_schema_decl153 = new BitSet(new long[]{0x0000000000010800L});
    public static final BitSet FOLLOW_decl_in_schema_decl162 = new BitSet(new long[]{0x0000000000010808L});
    public static final BitSet FOLLOW_label_decl_in_decl184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_property_decl_in_decl190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LABEL_in_label_decl203 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_label_decl208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_in_property_decl228 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_property_decl233 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_cardinal_decl_in_property_decl235 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_assigment_in_property_decl238 = new BitSet(new long[]{0x0000000000000018L});
    public static final BitSet FOLLOW_CARDINALITY_in_cardinal_decl269 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CARDINALITY_DECL_in_cardinal_decl271 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ASSIGMENT_in_assigment288 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_key_in_assigment291 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_value_in_assigment293 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STRING_in_value352 = new BitSet(new long[]{0x0000000000000002L});

}