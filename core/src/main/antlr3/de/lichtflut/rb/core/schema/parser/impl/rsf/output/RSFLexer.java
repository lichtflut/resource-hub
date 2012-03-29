// $ANTLR 3.4 RSF.g 2012-03-29 10:09:46

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

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
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
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
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
    // $ANTLR end "T__26"

    // $ANTLR start "NS"
    public final void mNS() throws RecognitionException {
        try {
            int _type = NS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:80:4: ( 'namespace' )
            // RSF.g:80:6: 'namespace'
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

    // $ANTLR start "SCHEMA_FOR"
    public final void mSCHEMA_FOR() throws RecognitionException {
        try {
            int _type = SCHEMA_FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:82:12: ( 'schema for' )
            // RSF.g:82:14: 'schema for'
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
            // RSF.g:84:12: ( 'label-rule' )
            // RSF.g:84:14: 'label-rule'
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
            // RSF.g:86:15: ( 'property' )
            // RSF.g:86:17: 'property'
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
            // RSF.g:88:13: ( 'field-label' )
            // RSF.g:88:15: 'field-label'
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

    // $ANTLR start "TYPE_DEF"
    public final void mTYPE_DEF() throws RecognitionException {
        try {
            int _type = TYPE_DEF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:90:9: ( 'type-definition' )
            // RSF.g:90:10: 'type-definition'
            {
            match("type-definition"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TYPE_DEF"

    // $ANTLR start "RESOURCE_CONSTRAINT"
    public final void mRESOURCE_CONSTRAINT() throws RecognitionException {
        try {
            int _type = RESOURCE_CONSTRAINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:92:20: ( 'resource-constraint' )
            // RSF.g:92:21: 'resource-constraint'
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

    // $ANTLR start "CONSTRAINT"
    public final void mCONSTRAINT() throws RecognitionException {
        try {
            int _type = CONSTRAINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:94:11: ( 'literal-constraint' )
            // RSF.g:94:12: 'literal-constraint'
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
    // $ANTLR end "CONSTRAINT"

    // $ANTLR start "PREFIX"
    public final void mPREFIX() throws RecognitionException {
        try {
            int _type = PREFIX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:96:8: ( 'prefix' )
            // RSF.g:96:10: 'prefix'
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

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RSF.g:98:7: ( ':' )
            // RSF.g:98:9: ':'
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
            // RSF.g:100:11: ( 'field-label[' ( ( 'a' .. 'z' | 'A' .. 'Z' )+ ) ( ']' )* )
            // RSF.g:100:13: 'field-label[' ( ( 'a' .. 'z' | 'A' .. 'Z' )+ ) ( ']' )*
            {
            match("field-label["); 



            // RSF.g:100:28: ( ( 'a' .. 'z' | 'A' .. 'Z' )+ )
            // RSF.g:100:29: ( 'a' .. 'z' | 'A' .. 'Z' )+
            {
            // RSF.g:100:29: ( 'a' .. 'z' | 'A' .. 'Z' )+
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


            // RSF.g:100:57: ( ']' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==']') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // RSF.g:100:57: ']'
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
            // RSF.g:102:19: ( '[' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ '..' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ ']' )
            // RSF.g:102:21: '[' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ '..' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ ']'
            {
            match('['); 

            // RSF.g:102:24: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+
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



            // RSF.g:102:67: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+
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
            // RSF.g:104:9: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ' ' | '_' | '-' | '#' | '/' | ':' | '.' )+ '\"' )
            // RSF.g:104:11: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ' ' | '_' | '-' | '#' | '/' | ':' | '.' )+ '\"'
            {
            match('\"'); 

            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // RSF.g:104:53: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ' ' | '_' | '-' | '#' | '/' | ':' | '.' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==' '||LA5_0=='#'||(LA5_0 >= '-' && LA5_0 <= ':')||(LA5_0 >= 'A' && LA5_0 <= 'Z')||LA5_0=='_'||(LA5_0 >= 'a' && LA5_0 <= 'z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // RSF.g:
            	    {
            	    if ( input.LA(1)==' '||input.LA(1)=='#'||(input.LA(1) >= '-' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
            // RSF.g:106:3: ( ( ' ' | '\\n' | '\\r' | '\\t' )+ )
            // RSF.g:106:5: ( ' ' | '\\n' | '\\r' | '\\t' )+
            {
            // RSF.g:106:5: ( ' ' | '\\n' | '\\r' | '\\t' )+
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
        // RSF.g:1:8: ( T__25 | T__26 | NS | SCHEMA_FOR | LABEL_RULE | PROPERTY_DECL | FIELD_LABEL | TYPE_DEF | RESOURCE_CONSTRAINT | CONSTRAINT | PREFIX | COLON | INT_LABEL | CARDINALITY_DECL | STRING | WS )
        int alt7=16;
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
            alt7=3;
            }
            break;
        case 's':
            {
            alt7=4;
            }
            break;
        case 'l':
            {
            int LA7_5 = input.LA(2);

            if ( (LA7_5=='a') ) {
                alt7=5;
            }
            else if ( (LA7_5=='i') ) {
                alt7=10;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 5, input);

                throw nvae;

            }
            }
            break;
        case 'p':
            {
            int LA7_6 = input.LA(2);

            if ( (LA7_6=='r') ) {
                int LA7_16 = input.LA(3);

                if ( (LA7_16=='o') ) {
                    alt7=6;
                }
                else if ( (LA7_16=='e') ) {
                    alt7=11;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 16, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 6, input);

                throw nvae;

            }
            }
            break;
        case 'f':
            {
            int LA7_7 = input.LA(2);

            if ( (LA7_7=='i') ) {
                int LA7_17 = input.LA(3);

                if ( (LA7_17=='e') ) {
                    int LA7_20 = input.LA(4);

                    if ( (LA7_20=='l') ) {
                        int LA7_21 = input.LA(5);

                        if ( (LA7_21=='d') ) {
                            int LA7_22 = input.LA(6);

                            if ( (LA7_22=='-') ) {
                                int LA7_23 = input.LA(7);

                                if ( (LA7_23=='l') ) {
                                    int LA7_24 = input.LA(8);

                                    if ( (LA7_24=='a') ) {
                                        int LA7_25 = input.LA(9);

                                        if ( (LA7_25=='b') ) {
                                            int LA7_26 = input.LA(10);

                                            if ( (LA7_26=='e') ) {
                                                int LA7_27 = input.LA(11);

                                                if ( (LA7_27=='l') ) {
                                                    int LA7_28 = input.LA(12);

                                                    if ( (LA7_28=='[') ) {
                                                        alt7=13;
                                                    }
                                                    else {
                                                        alt7=7;
                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 7, 27, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 7, 26, input);

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
                                            new NoViableAltException("", 7, 24, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 7, 23, input);

                                    throw nvae;

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
                                new NoViableAltException("", 7, 21, input);

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
                        new NoViableAltException("", 7, 17, input);

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
        case 't':
            {
            alt7=8;
            }
            break;
        case 'r':
            {
            alt7=9;
            }
            break;
        case ':':
            {
            alt7=12;
            }
            break;
        case '[':
            {
            alt7=14;
            }
            break;
        case '\"':
            {
            alt7=15;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt7=16;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 7, 0, input);

            throw nvae;

        }

        switch (alt7) {
            case 1 :
                // RSF.g:1:10: T__25
                {
                mT__25(); 


                }
                break;
            case 2 :
                // RSF.g:1:16: T__26
                {
                mT__26(); 


                }
                break;
            case 3 :
                // RSF.g:1:22: NS
                {
                mNS(); 


                }
                break;
            case 4 :
                // RSF.g:1:25: SCHEMA_FOR
                {
                mSCHEMA_FOR(); 


                }
                break;
            case 5 :
                // RSF.g:1:36: LABEL_RULE
                {
                mLABEL_RULE(); 


                }
                break;
            case 6 :
                // RSF.g:1:47: PROPERTY_DECL
                {
                mPROPERTY_DECL(); 


                }
                break;
            case 7 :
                // RSF.g:1:61: FIELD_LABEL
                {
                mFIELD_LABEL(); 


                }
                break;
            case 8 :
                // RSF.g:1:73: TYPE_DEF
                {
                mTYPE_DEF(); 


                }
                break;
            case 9 :
                // RSF.g:1:82: RESOURCE_CONSTRAINT
                {
                mRESOURCE_CONSTRAINT(); 


                }
                break;
            case 10 :
                // RSF.g:1:102: CONSTRAINT
                {
                mCONSTRAINT(); 


                }
                break;
            case 11 :
                // RSF.g:1:113: PREFIX
                {
                mPREFIX(); 


                }
                break;
            case 12 :
                // RSF.g:1:120: COLON
                {
                mCOLON(); 


                }
                break;
            case 13 :
                // RSF.g:1:126: INT_LABEL
                {
                mINT_LABEL(); 


                }
                break;
            case 14 :
                // RSF.g:1:136: CARDINALITY_DECL
                {
                mCARDINALITY_DECL(); 


                }
                break;
            case 15 :
                // RSF.g:1:153: STRING
                {
                mSTRING(); 


                }
                break;
            case 16 :
                // RSF.g:1:160: WS
                {
                mWS(); 


                }
                break;

        }

    }


 

}