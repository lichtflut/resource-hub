/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/ResourceSchema.g 2011-03-09 20:35:05

 package de.lichtflut.rb.core.schema.parser;


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class ResourceSchemaLexer extends Lexer {
    public static final int PROPERTY_DEC=6;
    public static final int LOGICAL_LOWER=14;
    public static final int TEXT_LOWER=12;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int LOGICAL_UPPER=13;
    public static final int NUMERIC_UPPER=9;
    public static final int PROPERTY_DEC_LOWER=5;
    public static final int INT=21;
    public static final int TEXT=18;
    public static final int NUMERIC=17;
    public static final int EOF=-1;
    public static final int TEXT_UPPER=11;
    public static final int RESOURCE_DEC_UPPER=7;
    public static final int NUMERIC_LOWER=10;
    public static final int CARDINALITY=16;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int EOL=15;
    public static final int WS=24;
    public static final int PROPERTY_DEC_UPPER=4;
    public static final int IDENT=22;
    public static final int DATATYPE=20;
    public static final int LOGICAL=19;
    public static final int RESOURCE_DEC_LOWER=8;
    public static final int STRING=23;

    // delegates
    // delegators

    public ResourceSchemaLexer() {;} 
    public ResourceSchemaLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ResourceSchemaLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "de/lichtflut/rb/core/schema/ResourceSchema.g"; }

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:11:7: ( '(' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:11:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:12:7: ( ')' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:12:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:13:7: ( 'type is' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:13:9: 'type is'
            {
            match("type is"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:14:7: ( 'regex' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:14:9: 'regex'
            {
            match("regex"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:15:7: ( '\"' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:15:9: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:16:7: ( 'references' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:16:9: 'references'
            {
            match("references"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:17:7: ( 'and' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:17:9: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "PROPERTY_DEC"
    public final void mPROPERTY_DEC() throws RecognitionException {
        try {
            int _type = PROPERTY_DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:40:14: ( ( PROPERTY_DEC_UPPER | PROPERTY_DEC_LOWER ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:40:16: ( PROPERTY_DEC_UPPER | PROPERTY_DEC_LOWER )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:40:16: ( PROPERTY_DEC_UPPER | PROPERTY_DEC_LOWER )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='P') ) {
                alt1=1;
            }
            else if ( (LA1_0=='p') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:40:17: PROPERTY_DEC_UPPER
                    {
                    mPROPERTY_DEC_UPPER(); 

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:40:38: PROPERTY_DEC_LOWER
                    {
                    mPROPERTY_DEC_LOWER(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROPERTY_DEC"

    // $ANTLR start "PROPERTY_DEC_UPPER"
    public final void mPROPERTY_DEC_UPPER() throws RecognitionException {
        try {
            int _type = PROPERTY_DEC_UPPER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:41:20: ( 'PROPERTY' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:41:22: 'PROPERTY'
            {
            match("PROPERTY"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROPERTY_DEC_UPPER"

    // $ANTLR start "PROPERTY_DEC_LOWER"
    public final void mPROPERTY_DEC_LOWER() throws RecognitionException {
        try {
            int _type = PROPERTY_DEC_LOWER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:42:20: ( 'property' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:42:22: 'property'
            {
            match("property"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROPERTY_DEC_LOWER"

    // $ANTLR start "RESOURCE_DEC_UPPER"
    public final void mRESOURCE_DEC_UPPER() throws RecognitionException {
        try {
            int _type = RESOURCE_DEC_UPPER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:43:20: ( 'RESOURCE' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:43:22: 'RESOURCE'
            {
            match("RESOURCE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RESOURCE_DEC_UPPER"

    // $ANTLR start "RESOURCE_DEC_LOWER"
    public final void mRESOURCE_DEC_LOWER() throws RecognitionException {
        try {
            int _type = RESOURCE_DEC_LOWER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:44:20: ( 'resource' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:44:22: 'resource'
            {
            match("resource"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RESOURCE_DEC_LOWER"

    // $ANTLR start "NUMERIC_UPPER"
    public final void mNUMERIC_UPPER() throws RecognitionException {
        try {
            int _type = NUMERIC_UPPER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:45:15: ( 'NUMERIC' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:45:17: 'NUMERIC'
            {
            match("NUMERIC"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUMERIC_UPPER"

    // $ANTLR start "NUMERIC_LOWER"
    public final void mNUMERIC_LOWER() throws RecognitionException {
        try {
            int _type = NUMERIC_LOWER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:46:15: ( 'numeric' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:46:17: 'numeric'
            {
            match("numeric"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUMERIC_LOWER"

    // $ANTLR start "TEXT_UPPER"
    public final void mTEXT_UPPER() throws RecognitionException {
        try {
            int _type = TEXT_UPPER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:47:12: ( 'TEXT' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:47:14: 'TEXT'
            {
            match("TEXT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TEXT_UPPER"

    // $ANTLR start "TEXT_LOWER"
    public final void mTEXT_LOWER() throws RecognitionException {
        try {
            int _type = TEXT_LOWER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:48:12: ( 'numeric' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:48:14: 'numeric'
            {
            match("numeric"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TEXT_LOWER"

    // $ANTLR start "LOGICAL_UPPER"
    public final void mLOGICAL_UPPER() throws RecognitionException {
        try {
            int _type = LOGICAL_UPPER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:49:15: ( 'LOGICAL' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:49:17: 'LOGICAL'
            {
            match("LOGICAL"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOGICAL_UPPER"

    // $ANTLR start "LOGICAL_LOWER"
    public final void mLOGICAL_LOWER() throws RecognitionException {
        try {
            int _type = LOGICAL_LOWER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:50:15: ( 'logical' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:50:17: 'logical'
            {
            match("logical"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOGICAL_LOWER"

    // $ANTLR start "EOL"
    public final void mEOL() throws RecognitionException {
        try {
            int _type = EOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:51:6: ( '\\n' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:51:9: '\\n'
            {
            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EOL"

    // $ANTLR start "CARDINALITY"
    public final void mCARDINALITY() throws RecognitionException {
        try {
            int _type = CARDINALITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:54:13: ( ( 'has' | 'hasMin' | 'hasMax' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:54:15: ( 'has' | 'hasMin' | 'hasMax' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:54:15: ( 'has' | 'hasMin' | 'hasMax' )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='h') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='a') ) {
                    int LA2_2 = input.LA(3);

                    if ( (LA2_2=='s') ) {
                        int LA2_3 = input.LA(4);

                        if ( (LA2_3=='M') ) {
                            int LA2_4 = input.LA(5);

                            if ( (LA2_4=='i') ) {
                                alt2=2;
                            }
                            else if ( (LA2_4=='a') ) {
                                alt2=3;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 2, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            alt2=1;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:54:16: 'has'
                    {
                    match("has"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:54:24: 'hasMin'
                    {
                    match("hasMin"); 


                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:54:35: 'hasMax'
                    {
                    match("hasMax"); 


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CARDINALITY"

    // $ANTLR start "DATATYPE"
    public final void mDATATYPE() throws RecognitionException {
        try {
            int _type = DATATYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:55:10: ( ( NUMERIC | TEXT | LOGICAL ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:55:12: ( NUMERIC | TEXT | LOGICAL )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:55:12: ( NUMERIC | TEXT | LOGICAL )
            int alt3=3;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:55:13: NUMERIC
                    {
                    mNUMERIC(); 

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:55:23: TEXT
                    {
                    mTEXT(); 

                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:55:30: LOGICAL
                    {
                    mLOGICAL(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DATATYPE"

    // $ANTLR start "NUMERIC"
    public final void mNUMERIC() throws RecognitionException {
        try {
            int _type = NUMERIC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:56:9: ( ( NUMERIC_UPPER | NUMERIC_LOWER ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:56:11: ( NUMERIC_UPPER | NUMERIC_LOWER )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:56:11: ( NUMERIC_UPPER | NUMERIC_LOWER )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='N') ) {
                alt4=1;
            }
            else if ( (LA4_0=='n') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:56:12: NUMERIC_UPPER
                    {
                    mNUMERIC_UPPER(); 

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:56:28: NUMERIC_LOWER
                    {
                    mNUMERIC_LOWER(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUMERIC"

    // $ANTLR start "TEXT"
    public final void mTEXT() throws RecognitionException {
        try {
            int _type = TEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:57:6: ( ( TEXT_UPPER | TEXT_LOWER ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:57:8: ( TEXT_UPPER | TEXT_LOWER )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:57:8: ( TEXT_UPPER | TEXT_LOWER )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='T') ) {
                alt5=1;
            }
            else if ( (LA5_0=='n') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:57:9: TEXT_UPPER
                    {
                    mTEXT_UPPER(); 

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:57:22: TEXT_LOWER
                    {
                    mTEXT_LOWER(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TEXT"

    // $ANTLR start "LOGICAL"
    public final void mLOGICAL() throws RecognitionException {
        try {
            int _type = LOGICAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:58:9: ( ( LOGICAL_UPPER | LOGICAL_LOWER ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:58:11: ( LOGICAL_UPPER | LOGICAL_LOWER )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:58:11: ( LOGICAL_UPPER | LOGICAL_LOWER )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='L') ) {
                alt6=1;
            }
            else if ( (LA6_0=='l') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:58:12: LOGICAL_UPPER
                    {
                    mLOGICAL_UPPER(); 

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:58:28: LOGICAL_LOWER
                    {
                    mLOGICAL_LOWER(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOGICAL"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:59:5: ( ( '0' .. '9' )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:59:7: ( '0' .. '9' )+
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:59:7: ( '0' .. '9' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:59:8: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:60:7: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:60:9: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // de/lichtflut/rb/core/schema/ResourceSchema.g:60:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')||(LA8_0>='A' && LA8_0<='Z')||LA8_0=='_'||(LA8_0>='a' && LA8_0<='z')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:61:8: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+ '\"' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:61:10: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+ '\"'
            {
            match('\"'); 
            // de/lichtflut/rb/core/schema/ResourceSchema.g:61:13: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='*' && LA9_0<='+')||LA9_0=='-'||(LA9_0>='0' && LA9_0<='9')||(LA9_0>='A' && LA9_0<='Z')||LA9_0=='^'||(LA9_0>='a' && LA9_0<='z')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:
            	    {
            	    if ( (input.LA(1)>='*' && input.LA(1)<='+')||input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='^'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:62:4: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:62:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:62:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\t' && LA10_0<='\n')||(LA10_0>='\f' && LA10_0<='\r')||LA10_0==' ') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // de/lichtflut/rb/core/schema/ResourceSchema.g:1:8: ( T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | PROPERTY_DEC | PROPERTY_DEC_UPPER | PROPERTY_DEC_LOWER | RESOURCE_DEC_UPPER | RESOURCE_DEC_LOWER | NUMERIC_UPPER | NUMERIC_LOWER | TEXT_UPPER | TEXT_LOWER | LOGICAL_UPPER | LOGICAL_LOWER | EOL | CARDINALITY | DATATYPE | NUMERIC | TEXT | LOGICAL | INT | IDENT | STRING | WS )
        int alt11=28;
        alt11 = dfa11.predict(input);
        switch (alt11) {
            case 1 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:10: T__25
                {
                mT__25(); 

                }
                break;
            case 2 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:16: T__26
                {
                mT__26(); 

                }
                break;
            case 3 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:22: T__27
                {
                mT__27(); 

                }
                break;
            case 4 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:28: T__28
                {
                mT__28(); 

                }
                break;
            case 5 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:34: T__29
                {
                mT__29(); 

                }
                break;
            case 6 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:40: T__30
                {
                mT__30(); 

                }
                break;
            case 7 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:46: T__31
                {
                mT__31(); 

                }
                break;
            case 8 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:52: PROPERTY_DEC
                {
                mPROPERTY_DEC(); 

                }
                break;
            case 9 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:65: PROPERTY_DEC_UPPER
                {
                mPROPERTY_DEC_UPPER(); 

                }
                break;
            case 10 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:84: PROPERTY_DEC_LOWER
                {
                mPROPERTY_DEC_LOWER(); 

                }
                break;
            case 11 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:103: RESOURCE_DEC_UPPER
                {
                mRESOURCE_DEC_UPPER(); 

                }
                break;
            case 12 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:122: RESOURCE_DEC_LOWER
                {
                mRESOURCE_DEC_LOWER(); 

                }
                break;
            case 13 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:141: NUMERIC_UPPER
                {
                mNUMERIC_UPPER(); 

                }
                break;
            case 14 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:155: NUMERIC_LOWER
                {
                mNUMERIC_LOWER(); 

                }
                break;
            case 15 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:169: TEXT_UPPER
                {
                mTEXT_UPPER(); 

                }
                break;
            case 16 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:180: TEXT_LOWER
                {
                mTEXT_LOWER(); 

                }
                break;
            case 17 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:191: LOGICAL_UPPER
                {
                mLOGICAL_UPPER(); 

                }
                break;
            case 18 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:205: LOGICAL_LOWER
                {
                mLOGICAL_LOWER(); 

                }
                break;
            case 19 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:219: EOL
                {
                mEOL(); 

                }
                break;
            case 20 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:223: CARDINALITY
                {
                mCARDINALITY(); 

                }
                break;
            case 21 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:235: DATATYPE
                {
                mDATATYPE(); 

                }
                break;
            case 22 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:244: NUMERIC
                {
                mNUMERIC(); 

                }
                break;
            case 23 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:252: TEXT
                {
                mTEXT(); 

                }
                break;
            case 24 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:257: LOGICAL
                {
                mLOGICAL(); 

                }
                break;
            case 25 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:265: INT
                {
                mINT(); 

                }
                break;
            case 26 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:269: IDENT
                {
                mIDENT(); 

                }
                break;
            case 27 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:275: STRING
                {
                mSTRING(); 

                }
                break;
            case 28 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:282: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA3_eotS =
        "\13\uffff";
    static final String DFA3_eofS =
        "\13\uffff";
    static final String DFA3_minS =
        "\1\114\1\uffff\1\165\2\uffff\1\155\1\145\1\162\1\151\1\143\1\uffff";
    static final String DFA3_maxS =
        "\1\156\1\uffff\1\165\2\uffff\1\155\1\145\1\162\1\151\1\143\1\uffff";
    static final String DFA3_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\1\3\5\uffff\1\1";
    static final String DFA3_specialS =
        "\13\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\4\1\uffff\1\1\5\uffff\1\3\27\uffff\1\4\1\uffff\1\2",
            "",
            "\1\5",
            "",
            "",
            "\1\6",
            "\1\7",
            "\1\10",
            "\1\11",
            "\1\12",
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
            return "55:12: ( NUMERIC | TEXT | LOGICAL )";
        }
    }
    static final String DFA11_eotS =
        "\3\uffff\2\22\1\27\11\22\1\41\1\22\3\uffff\2\22\2\uffff\11\22\1"+
        "\uffff\5\22\1\65\10\22\1\77\4\22\1\uffff\5\22\1\111\3\22\2\uffff"+
        "\1\116\7\22\1\uffff\4\22\1\uffff\11\22\2\77\5\22\1\150\1\151\1\152"+
        "\1\153\1\22\1\155\2\156\1\157\4\uffff\1\22\3\uffff\1\161\1\uffff";
    static final String DFA11_eofS =
        "\162\uffff";
    static final String DFA11_minS =
        "\1\11\2\uffff\1\171\1\145\1\52\1\156\1\122\1\162\1\105\1\125\1\165"+
        "\1\105\1\117\1\157\1\11\1\141\3\uffff\1\160\1\146\2\uffff\1\144"+
        "\1\117\1\157\1\123\1\115\1\155\1\130\1\107\1\147\1\uffff\1\163\3"+
        "\145\1\157\1\60\1\120\1\160\1\117\1\105\1\145\1\124\1\111\1\151"+
        "\1\60\1\40\1\170\1\162\1\165\1\uffff\1\105\1\145\1\125\1\122\1\162"+
        "\1\60\1\103\1\143\1\141\2\uffff\1\60\1\145\1\162\1\122\1\162\1\122"+
        "\1\111\1\151\1\uffff\1\101\1\141\1\156\1\170\1\uffff\1\156\1\143"+
        "\1\124\1\164\2\103\1\143\1\114\1\154\2\60\1\143\1\145\1\131\1\171"+
        "\1\105\4\60\1\145\4\60\4\uffff\1\163\3\uffff\1\60\1\uffff";
    static final String DFA11_maxS =
        "\1\172\2\uffff\1\171\1\145\1\172\1\156\1\122\1\162\1\105\1\125\1"+
        "\165\1\105\1\117\1\157\1\40\1\141\3\uffff\1\160\1\163\2\uffff\1"+
        "\144\1\117\1\157\1\123\1\115\1\155\1\130\1\107\1\147\1\uffff\1\163"+
        "\3\145\1\157\1\172\1\120\1\160\1\117\1\105\1\145\1\124\1\111\1\151"+
        "\1\172\1\40\1\170\1\162\1\165\1\uffff\1\105\1\145\1\125\1\122\1"+
        "\162\1\172\1\103\1\143\1\151\2\uffff\1\172\1\145\1\162\1\122\1\162"+
        "\1\122\1\111\1\151\1\uffff\1\101\1\141\1\156\1\170\1\uffff\1\156"+
        "\1\143\1\124\1\164\2\103\1\143\1\114\1\154\2\172\1\143\1\145\1\131"+
        "\1\171\1\105\4\172\1\145\4\172\4\uffff\1\163\3\uffff\1\172\1\uffff";
    static final String DFA11_acceptS =
        "\1\uffff\1\1\1\2\16\uffff\1\31\1\32\1\34\2\uffff\1\33\1\5\11\uffff"+
        "\1\23\23\uffff\1\7\11\uffff\1\24\1\3\10\uffff\1\17\4\uffff\1\4\31"+
        "\uffff\1\15\1\16\1\21\1\22\1\uffff\1\14\1\10\1\13\1\uffff\1\6";
    static final String DFA11_specialS =
        "\162\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\23\1\17\1\uffff\2\23\22\uffff\1\23\1\uffff\1\5\5\uffff\1"+
            "\1\1\2\6\uffff\12\21\7\uffff\13\22\1\15\1\22\1\12\1\22\1\7\1"+
            "\22\1\11\1\22\1\14\6\22\6\uffff\1\6\6\22\1\20\3\22\1\16\1\22"+
            "\1\13\1\22\1\10\1\22\1\4\1\22\1\3\6\22",
            "",
            "",
            "\1\24",
            "\1\25",
            "\2\26\1\uffff\1\26\2\uffff\12\26\7\uffff\32\26\3\uffff\1\26"+
            "\2\uffff\32\26",
            "\1\30",
            "\1\31",
            "\1\32",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "\1\40",
            "\2\23\1\uffff\2\23\22\uffff\1\23",
            "\1\42",
            "",
            "",
            "",
            "\1\43",
            "\1\45\1\44\13\uffff\1\46",
            "",
            "",
            "\1\47",
            "\1\50",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\75",
            "\12\22\7\uffff\14\22\1\76\15\22\4\uffff\1\22\1\uffff\32\22",
            "\1\100",
            "\1\101",
            "\1\102",
            "\1\103",
            "",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\112",
            "\1\113",
            "\1\115\7\uffff\1\114",
            "",
            "",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\117",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\143",
            "\1\144",
            "\1\145",
            "\1\146",
            "\1\147",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\154",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "",
            "",
            "",
            "",
            "\1\160",
            "",
            "",
            "",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | PROPERTY_DEC | PROPERTY_DEC_UPPER | PROPERTY_DEC_LOWER | RESOURCE_DEC_UPPER | RESOURCE_DEC_LOWER | NUMERIC_UPPER | NUMERIC_LOWER | TEXT_UPPER | TEXT_LOWER | LOGICAL_UPPER | LOGICAL_LOWER | EOL | CARDINALITY | DATATYPE | NUMERIC | TEXT | LOGICAL | INT | IDENT | STRING | WS );";
        }
    }
 

}