// $ANTLR 3.4 RSF.g 2012-06-21 22:06:25

/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.parser.impl.rsf;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class RSFLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public RSFLexer() {} 
    public RSFLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public RSFLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "RSF.g"; }

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:10:7: ( '{' )
            // RSF.g:10:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:11:7: ( '}' )
            // RSF.g:11:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "NS"
    public final void mNS() throws RecognitionException {
        try {
            int _type = NS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:92:4: ( 'namespace' )
            // RSF.g:92:6: 'namespace'
            {
            match("namespace"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NS"

    // $ANTLR start "CONSTRAINT_FOR"
    public final void mCONSTRAINT_FOR() throws RecognitionException {
        try {
            int _type = CONSTRAINT_FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:94:16: ( 'constraint definition for' )
            // RSF.g:94:18: 'constraint definition for'
            {
            match("constraint definition for"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CONSTRAINT_FOR"

    // $ANTLR start "SCHEMA_FOR"
    public final void mSCHEMA_FOR() throws RecognitionException {
        try {
            int _type = SCHEMA_FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:96:12: ( 'schema for' )
            // RSF.g:96:14: 'schema for'
            {
            match("schema for"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SCHEMA_FOR"

    // $ANTLR start "LABEL_RULE"
    public final void mLABEL_RULE() throws RecognitionException {
        try {
            int _type = LABEL_RULE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:98:12: ( 'label-rule' )
            // RSF.g:98:14: 'label-rule'
            {
            match("label-rule"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LABEL_RULE"

    // $ANTLR start "PROPERTY_DECL"
    public final void mPROPERTY_DECL() throws RecognitionException {
        try {
            int _type = PROPERTY_DECL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:100:15: ( 'property' )
            // RSF.g:100:17: 'property'
            {
            match("property"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PROPERTY_DECL"

    // $ANTLR start "FIELD_LABEL"
    public final void mFIELD_LABEL() throws RecognitionException {
        try {
            int _type = FIELD_LABEL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:102:13: ( 'field-label' )
            // RSF.g:102:15: 'field-label'
            {
            match("field-label"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FIELD_LABEL"

    // $ANTLR start "DATATYPE"
    public final void mDATATYPE() throws RecognitionException {
        try {
            int _type = DATATYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:104:10: ( 'datatype' )
            // RSF.g:104:11: 'datatype'
            {
            match("datatype"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DATATYPE"

    // $ANTLR start "RESOURCE_CONSTRAINT"
    public final void mRESOURCE_CONSTRAINT() throws RecognitionException {
        try {
            int _type = RESOURCE_CONSTRAINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:106:21: ( 'resource-constraint' )
            // RSF.g:106:23: 'resource-constraint'
            {
            match("resource-constraint"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RESOURCE_CONSTRAINT"

    // $ANTLR start "CONSTRAINT_REFERENCE"
    public final void mCONSTRAINT_REFERENCE() throws RecognitionException {
        try {
            int _type = CONSTRAINT_REFERENCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:108:22: ( 'reference-constraint' )
            // RSF.g:108:24: 'reference-constraint'
            {
            match("reference-constraint"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CONSTRAINT_REFERENCE"

    // $ANTLR start "LITERAL_CONSTRAINT"
    public final void mLITERAL_CONSTRAINT() throws RecognitionException {
        try {
            int _type = LITERAL_CONSTRAINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:110:20: ( 'literal-constraint' )
            // RSF.g:110:22: 'literal-constraint'
            {
            match("literal-constraint"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LITERAL_CONSTRAINT"

    // $ANTLR start "APPLICABLE_DATATYPES"
    public final void mAPPLICABLE_DATATYPES() throws RecognitionException {
        try {
            int _type = APPLICABLE_DATATYPES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:112:22: ( 'applicable-datatypes' )
            // RSF.g:112:24: 'applicable-datatypes'
            {
            match("applicable-datatypes"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "APPLICABLE_DATATYPES"

    // $ANTLR start "PREFIX"
    public final void mPREFIX() throws RecognitionException {
        try {
            int _type = PREFIX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:114:8: ( 'prefix' )
            // RSF.g:114:10: 'prefix'
            {
            match("prefix"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PREFIX"

    // $ANTLR start "NAME"
    public final void mNAME() throws RecognitionException {
        try {
            int _type = NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:116:6: ( 'name' )
            // RSF.g:116:8: 'name'
            {
            match("name"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NAME"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:118:7: ( ':' )
            // RSF.g:118:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "INT_LABEL"
    public final void mINT_LABEL() throws RecognitionException {
        try {
            int _type = INT_LABEL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:120:11: ( 'field-label[' ( ( 'a' .. 'z' | 'A' .. 'Z' )+ ) ( ']' )* )
            // RSF.g:120:13: 'field-label[' ( ( 'a' .. 'z' | 'A' .. 'Z' )+ ) ( ']' )*
            {
            match("field-label["); 



            // RSF.g:120:28: ( ( 'a' .. 'z' | 'A' .. 'Z' )+ )
            // RSF.g:120:29: ( 'a' .. 'z' | 'A' .. 'Z' )+
            {
            // RSF.g:120:29: ( 'a' .. 'z' | 'A' .. 'Z' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= 'A' && LA1_0 <= 'Z')||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // RSF.g:
            	    {
            	    if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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


            // RSF.g:120:57: ( ']' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==']') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // RSF.g:120:57: ']'
            	    {
            	    match(']'); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT_LABEL"

    // $ANTLR start "CARDINALITY_DECL"
    public final void mCARDINALITY_DECL() throws RecognitionException {
        try {
            int _type = CARDINALITY_DECL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:122:19: ( '[' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ '..' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ ']' )
            // RSF.g:122:21: '[' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ '..' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ ']'
            {
            match('['); 

            // RSF.g:122:24: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '0' && LA3_0 <= '9')||(LA3_0 >= 'A' && LA3_0 <= 'Z')||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // RSF.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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


            match(".."); 



            // RSF.g:122:67: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '0' && LA4_0 <= '9')||(LA4_0 >= 'A' && LA4_0 <= 'Z')||(LA4_0 >= 'a' && LA4_0 <= 'z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // RSF.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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


            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CARDINALITY_DECL"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:124:9: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ' ' | '_' | '-' | '#' | '/' | ':' | '.' | '<' | '>' | ',' | '@' | '*' )+ '\"' )
            // RSF.g:124:11: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ' ' | '_' | '-' | '#' | '/' | ':' | '.' | '<' | '>' | ',' | '@' | '*' )+ '\"'
            {
            match('\"'); 

            // RSF.g:124:15: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ' ' | '_' | '-' | '#' | '/' | ':' | '.' | '<' | '>' | ',' | '@' | '*' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==' '||LA5_0=='#'||LA5_0=='*'||(LA5_0 >= ',' && LA5_0 <= ':')||LA5_0=='<'||LA5_0=='>'||(LA5_0 >= '@' && LA5_0 <= 'Z')||LA5_0=='_'||(LA5_0 >= 'a' && LA5_0 <= 'z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // RSF.g:
            	    {
            	    if ( input.LA(1)==' '||input.LA(1)=='#'||input.LA(1)=='*'||(input.LA(1) >= ',' && input.LA(1) <= ':')||input.LA(1)=='<'||input.LA(1)=='>'||(input.LA(1) >= '@' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:126:3: ( ( ' ' | '\\n' | '\\r' | '\\t' )+ )
            // RSF.g:126:5: ( ' ' | '\\n' | '\\r' | '\\t' )+
            {
            // RSF.g:126:5: ( ' ' | '\\n' | '\\r' | '\\t' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0 >= '\t' && LA6_0 <= '\n')||LA6_0=='\r'||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // RSF.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // RSF.g:1:8: ( T__30 | T__31 | NS | CONSTRAINT_FOR | SCHEMA_FOR | LABEL_RULE | PROPERTY_DECL | FIELD_LABEL | DATATYPE | RESOURCE_CONSTRAINT | CONSTRAINT_REFERENCE | LITERAL_CONSTRAINT | APPLICABLE_DATATYPES | PREFIX | NAME | COLON | INT_LABEL | CARDINALITY_DECL | STRING | WS )
        int alt7=20;
        switch ( input.LA(1) ) {
        case '{':
            {
            alt7=1;
            }
            break;
        case '}':
            {
            alt7=2;
            }
            break;
        case 'n':
            {
            int LA7_3 = input.LA(2);

            if ( (LA7_3=='a') ) {
                int LA7_16 = input.LA(3);

                if ( (LA7_16=='m') ) {
                    int LA7_22 = input.LA(4);

                    if ( (LA7_22=='e') ) {
                        int LA7_28 = input.LA(5);

                        if ( (LA7_28=='s') ) {
                            alt7=3;
                        }
                        else {
                            alt7=15;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 22, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 16, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 3, input);

                throw nvae;

            }
            }
            break;
        case 'c':
            {
            alt7=4;
            }
            break;
        case 's':
            {
            alt7=5;
            }
            break;
        case 'l':
            {
            int LA7_6 = input.LA(2);

            if ( (LA7_6=='a') ) {
                alt7=6;
            }
            else if ( (LA7_6=='i') ) {
                alt7=12;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 6, input);

                throw nvae;

            }
            }
            break;
        case 'p':
            {
            int LA7_7 = input.LA(2);

            if ( (LA7_7=='r') ) {
                int LA7_19 = input.LA(3);

                if ( (LA7_19=='o') ) {
                    alt7=7;
                }
                else if ( (LA7_19=='e') ) {
                    alt7=14;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 19, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 7, input);

                throw nvae;

            }
            }
            break;
        case 'f':
            {
            int LA7_8 = input.LA(2);

            if ( (LA7_8=='i') ) {
                int LA7_20 = input.LA(3);

                if ( (LA7_20=='e') ) {
                    int LA7_25 = input.LA(4);

                    if ( (LA7_25=='l') ) {
                        int LA7_29 = input.LA(5);

                        if ( (LA7_29=='d') ) {
                            int LA7_32 = input.LA(6);

                            if ( (LA7_32=='-') ) {
                                int LA7_33 = input.LA(7);

                                if ( (LA7_33=='l') ) {
                                    int LA7_34 = input.LA(8);

                                    if ( (LA7_34=='a') ) {
                                        int LA7_35 = input.LA(9);

                                        if ( (LA7_35=='b') ) {
                                            int LA7_36 = input.LA(10);

                                            if ( (LA7_36=='e') ) {
                                                int LA7_37 = input.LA(11);

                                                if ( (LA7_37=='l') ) {
                                                    int LA7_38 = input.LA(12);

                                                    if ( (LA7_38=='[') ) {
                                                        alt7=17;
                                                    }
                                                    else {
                                                        alt7=8;
                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 7, 37, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 7, 36, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 7, 35, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 7, 34, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 7, 33, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 7, 32, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 29, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 25, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 20, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 8, input);

                throw nvae;

            }
            }
            break;
        case 'd':
            {
            alt7=9;
            }
            break;
        case 'r':
            {
            int LA7_10 = input.LA(2);

            if ( (LA7_10=='e') ) {
                int LA7_21 = input.LA(3);

                if ( (LA7_21=='s') ) {
                    alt7=10;
                }
                else if ( (LA7_21=='f') ) {
                    alt7=11;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 21, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 10, input);

                throw nvae;

            }
            }
            break;
        case 'a':
            {
            alt7=13;
            }
            break;
        case ':':
            {
            alt7=16;
            }
            break;
        case '[':
            {
            alt7=18;
            }
            break;
        case '\"':
            {
            alt7=19;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt7=20;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 7, 0, input);

            throw nvae;

        }

        switch (alt7) {
            case 1 :
                // RSF.g:1:10: T__30
                {
                mT__30(); 


                }
                break;
            case 2 :
                // RSF.g:1:16: T__31
                {
                mT__31(); 


                }
                break;
            case 3 :
                // RSF.g:1:22: NS
                {
                mNS(); 


                }
                break;
            case 4 :
                // RSF.g:1:25: CONSTRAINT_FOR
                {
                mCONSTRAINT_FOR(); 


                }
                break;
            case 5 :
                // RSF.g:1:40: SCHEMA_FOR
                {
                mSCHEMA_FOR(); 


                }
                break;
            case 6 :
                // RSF.g:1:51: LABEL_RULE
                {
                mLABEL_RULE(); 


                }
                break;
            case 7 :
                // RSF.g:1:62: PROPERTY_DECL
                {
                mPROPERTY_DECL(); 


                }
                break;
            case 8 :
                // RSF.g:1:76: FIELD_LABEL
                {
                mFIELD_LABEL(); 


                }
                break;
            case 9 :
                // RSF.g:1:88: DATATYPE
                {
                mDATATYPE(); 


                }
                break;
            case 10 :
                // RSF.g:1:97: RESOURCE_CONSTRAINT
                {
                mRESOURCE_CONSTRAINT(); 


                }
                break;
            case 11 :
                // RSF.g:1:117: CONSTRAINT_REFERENCE
                {
                mCONSTRAINT_REFERENCE(); 


                }
                break;
            case 12 :
                // RSF.g:1:138: LITERAL_CONSTRAINT
                {
                mLITERAL_CONSTRAINT(); 


                }
                break;
            case 13 :
                // RSF.g:1:157: APPLICABLE_DATATYPES
                {
                mAPPLICABLE_DATATYPES(); 


                }
                break;
            case 14 :
                // RSF.g:1:178: PREFIX
                {
                mPREFIX(); 


                }
                break;
            case 15 :
                // RSF.g:1:185: NAME
                {
                mNAME(); 


                }
                break;
            case 16 :
                // RSF.g:1:190: COLON
                {
                mCOLON(); 


                }
                break;
            case 17 :
                // RSF.g:1:196: INT_LABEL
                {
                mINT_LABEL(); 


                }
                break;
            case 18 :
                // RSF.g:1:206: CARDINALITY_DECL
                {
                mCARDINALITY_DECL(); 


                }
                break;
            case 19 :
                // RSF.g:1:223: STRING
                {
                mSTRING(); 


                }
                break;
            case 20 :
                // RSF.g:1:230: WS
                {
                mWS(); 


                }
                break;

        }

    }


 

}