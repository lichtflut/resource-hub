// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/OSF.g 2011-05-02 18:20:19

/*
  * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
*/
package de.lichtflut.rb.core.schema.parser.impl.osf;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class OSFLexer extends Lexer {
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int NULL=17;
    public static final int NUMBER=5;
    public static final int MAX=11;
    public static final int REGEX=13;
    public static final int MIN=12;
    public static final int DESCRIPTIONS=20;
    public static final int TEXT=8;
    public static final int Digit=23;
    public static final int EOF=-1;
    public static final int HexDigit=27;
    public static final int RESOURCE=19;
    public static final int TRUE=15;
    public static final int TYPE=10;
    public static final int CARDINALITY=14;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int BOOLEAN=7;
    public static final int WS=24;
    public static final int Number=22;
    public static final int PROPERTY=18;
    public static final int COMMA=9;
    public static final int UnicodeEscape=25;
    public static final int String=21;
    public static final int FALSE=16;
    public static final int EscapeSequence=26;
    public static final int ARRAY=6;
    public static final int STRING=4;

    // delegates
    // delegators

    public OSFLexer() {;} 
    public OSFLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public OSFLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "de/lichtflut/rb/core/schema/OSF.g"; }

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:10:7: ( ',' )
            // de/lichtflut/rb/core/schema/OSF.g:10:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:11:7: ( '{' )
            // de/lichtflut/rb/core/schema/OSF.g:11:9: '{'
            {
            match('{'); 

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
            // de/lichtflut/rb/core/schema/OSF.g:12:7: ( '=' )
            // de/lichtflut/rb/core/schema/OSF.g:12:9: '='
            {
            match('='); 

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
            // de/lichtflut/rb/core/schema/OSF.g:13:7: ( '}' )
            // de/lichtflut/rb/core/schema/OSF.g:13:9: '}'
            {
            match('}'); 

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
            // de/lichtflut/rb/core/schema/OSF.g:14:7: ( '[' )
            // de/lichtflut/rb/core/schema/OSF.g:14:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:15:7: ( ']' )
            // de/lichtflut/rb/core/schema/OSF.g:15:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "Number"
    public final void mNumber() throws RecognitionException {
        try {
            int _type = Number;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:144:8: ( ( '-' )? ( Digit )+ ( '.' ( Digit )+ )? )
            // de/lichtflut/rb/core/schema/OSF.g:144:10: ( '-' )? ( Digit )+ ( '.' ( Digit )+ )?
            {
            // de/lichtflut/rb/core/schema/OSF.g:144:10: ( '-' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='-') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSF.g:144:10: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // de/lichtflut/rb/core/schema/OSF.g:144:15: ( Digit )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/OSF.g:144:15: Digit
            	    {
            	    mDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            // de/lichtflut/rb/core/schema/OSF.g:144:22: ( '.' ( Digit )+ )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='.') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSF.g:144:24: '.' ( Digit )+
                    {
                    match('.'); 
                    // de/lichtflut/rb/core/schema/OSF.g:144:28: ( Digit )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // de/lichtflut/rb/core/schema/OSF.g:144:28: Digit
                    	    {
                    	    mDigit(); 

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
    // $ANTLR end "Number"

    // $ANTLR start "PROPERTY"
    public final void mPROPERTY() throws RecognitionException {
        try {
            int _type = PROPERTY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:147:9: ( 'PROPERTY' )
            // de/lichtflut/rb/core/schema/OSF.g:147:11: 'PROPERTY'
            {
            match("PROPERTY"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROPERTY"

    // $ANTLR start "RESOURCE"
    public final void mRESOURCE() throws RecognitionException {
        try {
            int _type = RESOURCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:148:9: ( 'RESOURCE' )
            // de/lichtflut/rb/core/schema/OSF.g:148:11: 'RESOURCE'
            {
            match("RESOURCE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RESOURCE"

    // $ANTLR start "TEXT"
    public final void mTEXT() throws RecognitionException {
        try {
            int _type = TEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:149:7: ( 'TEXT' )
            // de/lichtflut/rb/core/schema/OSF.g:149:9: 'TEXT'
            {
            match("TEXT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TEXT"

    // $ANTLR start "BOOLEAN"
    public final void mBOOLEAN() throws RecognitionException {
        try {
            int _type = BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:150:9: ( 'BOOLEAN' )
            // de/lichtflut/rb/core/schema/OSF.g:150:11: 'BOOLEAN'
            {
            match("BOOLEAN"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOOLEAN"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:151:9: ( 'NUMBER' )
            // de/lichtflut/rb/core/schema/OSF.g:151:11: 'NUMBER'
            {
            match("NUMBER"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:152:7: ( 'TRUE' )
            // de/lichtflut/rb/core/schema/OSF.g:152:9: 'TRUE'
            {
            match("TRUE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:153:8: ( 'FALSE' )
            // de/lichtflut/rb/core/schema/OSF.g:153:10: 'FALSE'
            {
            match("FALSE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "NULL"
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:154:7: ( 'NULL' )
            // de/lichtflut/rb/core/schema/OSF.g:154:9: 'NULL'
            {
            match("NULL"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NULL"

    // $ANTLR start "TYPE"
    public final void mTYPE() throws RecognitionException {
        try {
            int _type = TYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:155:7: ( 'TYPE' )
            // de/lichtflut/rb/core/schema/OSF.g:155:9: 'TYPE'
            {
            match("TYPE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TYPE"

    // $ANTLR start "MIN"
    public final void mMIN() throws RecognitionException {
        try {
            int _type = MIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:156:6: ( 'MIN' )
            // de/lichtflut/rb/core/schema/OSF.g:156:8: 'MIN'
            {
            match("MIN"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MIN"

    // $ANTLR start "MAX"
    public final void mMAX() throws RecognitionException {
        try {
            int _type = MAX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:157:6: ( 'MAX' )
            // de/lichtflut/rb/core/schema/OSF.g:157:8: 'MAX'
            {
            match("MAX"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MAX"

    // $ANTLR start "CARDINALITY"
    public final void mCARDINALITY() throws RecognitionException {
        try {
            int _type = CARDINALITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:158:13: ( 'CARDINALITY' )
            // de/lichtflut/rb/core/schema/OSF.g:158:15: 'CARDINALITY'
            {
            match("CARDINALITY"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CARDINALITY"

    // $ANTLR start "REGEX"
    public final void mREGEX() throws RecognitionException {
        try {
            int _type = REGEX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:159:13: ( 'REGEX' )
            // de/lichtflut/rb/core/schema/OSF.g:159:15: 'REGEX'
            {
            match("REGEX"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REGEX"

    // $ANTLR start "String"
    public final void mString() throws RecognitionException {
        try {
            int _type = String;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:162:8: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | '#' | '/' | ':' | '.' )+ '\"' )
            // de/lichtflut/rb/core/schema/OSF.g:162:10: '\"' ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | '#' | '/' | ':' | '.' )+ '\"'
            {
            match('\"'); 
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // de/lichtflut/rb/core/schema/OSF.g:162:39: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | '#' | '/' | ':' | '.' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='#'||(LA5_0>='-' && LA5_0<=':')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/OSF.g:
            	    {
            	    if ( input.LA(1)=='#'||(input.LA(1)>='-' && input.LA(1)<=':')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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
        }
    }
    // $ANTLR end "String"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/OSF.g:168:3: ( ( ' ' | '\\n' | '\\r' | '\\t' )+ )
            // de/lichtflut/rb/core/schema/OSF.g:168:5: ( ' ' | '\\n' | '\\r' | '\\t' )+
            {
            // de/lichtflut/rb/core/schema/OSF.g:168:5: ( ' ' | '\\n' | '\\r' | '\\t' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='\t' && LA6_0<='\n')||LA6_0=='\r'||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/OSF.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // de/lichtflut/rb/core/schema/OSF.g:171:6: ( '\\\\' ( UnicodeEscape | 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) )
            // de/lichtflut/rb/core/schema/OSF.g:171:10: '\\\\' ( UnicodeEscape | 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
            {
            match('\\'); 
            // de/lichtflut/rb/core/schema/OSF.g:171:15: ( UnicodeEscape | 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
            int alt7=9;
            switch ( input.LA(1) ) {
            case 'u':
                {
                alt7=1;
                }
                break;
            case 'b':
                {
                alt7=2;
                }
                break;
            case 't':
                {
                alt7=3;
                }
                break;
            case 'n':
                {
                alt7=4;
                }
                break;
            case 'f':
                {
                alt7=5;
                }
                break;
            case 'r':
                {
                alt7=6;
                }
                break;
            case '\"':
                {
                alt7=7;
                }
                break;
            case '\'':
                {
                alt7=8;
                }
                break;
            case '\\':
                {
                alt7=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // de/lichtflut/rb/core/schema/OSF.g:171:16: UnicodeEscape
                    {
                    mUnicodeEscape(); 

                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/OSF.g:171:31: 'b'
                    {
                    match('b'); 

                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/OSF.g:171:35: 't'
                    {
                    match('t'); 

                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/OSF.g:171:39: 'n'
                    {
                    match('n'); 

                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/OSF.g:171:43: 'f'
                    {
                    match('f'); 

                    }
                    break;
                case 6 :
                    // de/lichtflut/rb/core/schema/OSF.g:171:47: 'r'
                    {
                    match('r'); 

                    }
                    break;
                case 7 :
                    // de/lichtflut/rb/core/schema/OSF.g:171:51: '\\\"'
                    {
                    match('\"'); 

                    }
                    break;
                case 8 :
                    // de/lichtflut/rb/core/schema/OSF.g:171:56: '\\''
                    {
                    match('\''); 

                    }
                    break;
                case 9 :
                    // de/lichtflut/rb/core/schema/OSF.g:171:61: '\\\\'
                    {
                    match('\\'); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "UnicodeEscape"
    public final void mUnicodeEscape() throws RecognitionException {
        try {
            // de/lichtflut/rb/core/schema/OSF.g:175:2: ( 'u' HexDigit HexDigit HexDigit HexDigit )
            // de/lichtflut/rb/core/schema/OSF.g:175:4: 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('u'); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // de/lichtflut/rb/core/schema/OSF.g:179:2: ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' )
            // de/lichtflut/rb/core/schema/OSF.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexDigit"

    // $ANTLR start "Digit"
    public final void mDigit() throws RecognitionException {
        try {
            // de/lichtflut/rb/core/schema/OSF.g:183:2: ( '0' .. '9' )
            // de/lichtflut/rb/core/schema/OSF.g:183:4: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "Digit"

    public void mTokens() throws RecognitionException {
        // de/lichtflut/rb/core/schema/OSF.g:1:8: ( COMMA | T__28 | T__29 | T__30 | T__31 | T__32 | Number | PROPERTY | RESOURCE | TEXT | BOOLEAN | NUMBER | TRUE | FALSE | NULL | TYPE | MIN | MAX | CARDINALITY | REGEX | String | WS )
        int alt8=22;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // de/lichtflut/rb/core/schema/OSF.g:1:10: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 2 :
                // de/lichtflut/rb/core/schema/OSF.g:1:16: T__28
                {
                mT__28(); 

                }
                break;
            case 3 :
                // de/lichtflut/rb/core/schema/OSF.g:1:22: T__29
                {
                mT__29(); 

                }
                break;
            case 4 :
                // de/lichtflut/rb/core/schema/OSF.g:1:28: T__30
                {
                mT__30(); 

                }
                break;
            case 5 :
                // de/lichtflut/rb/core/schema/OSF.g:1:34: T__31
                {
                mT__31(); 

                }
                break;
            case 6 :
                // de/lichtflut/rb/core/schema/OSF.g:1:40: T__32
                {
                mT__32(); 

                }
                break;
            case 7 :
                // de/lichtflut/rb/core/schema/OSF.g:1:46: Number
                {
                mNumber(); 

                }
                break;
            case 8 :
                // de/lichtflut/rb/core/schema/OSF.g:1:53: PROPERTY
                {
                mPROPERTY(); 

                }
                break;
            case 9 :
                // de/lichtflut/rb/core/schema/OSF.g:1:62: RESOURCE
                {
                mRESOURCE(); 

                }
                break;
            case 10 :
                // de/lichtflut/rb/core/schema/OSF.g:1:71: TEXT
                {
                mTEXT(); 

                }
                break;
            case 11 :
                // de/lichtflut/rb/core/schema/OSF.g:1:76: BOOLEAN
                {
                mBOOLEAN(); 

                }
                break;
            case 12 :
                // de/lichtflut/rb/core/schema/OSF.g:1:84: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 13 :
                // de/lichtflut/rb/core/schema/OSF.g:1:91: TRUE
                {
                mTRUE(); 

                }
                break;
            case 14 :
                // de/lichtflut/rb/core/schema/OSF.g:1:96: FALSE
                {
                mFALSE(); 

                }
                break;
            case 15 :
                // de/lichtflut/rb/core/schema/OSF.g:1:102: NULL
                {
                mNULL(); 

                }
                break;
            case 16 :
                // de/lichtflut/rb/core/schema/OSF.g:1:107: TYPE
                {
                mTYPE(); 

                }
                break;
            case 17 :
                // de/lichtflut/rb/core/schema/OSF.g:1:112: MIN
                {
                mMIN(); 

                }
                break;
            case 18 :
                // de/lichtflut/rb/core/schema/OSF.g:1:116: MAX
                {
                mMAX(); 

                }
                break;
            case 19 :
                // de/lichtflut/rb/core/schema/OSF.g:1:120: CARDINALITY
                {
                mCARDINALITY(); 

                }
                break;
            case 20 :
                // de/lichtflut/rb/core/schema/OSF.g:1:132: REGEX
                {
                mREGEX(); 

                }
                break;
            case 21 :
                // de/lichtflut/rb/core/schema/OSF.g:1:138: String
                {
                mString(); 

                }
                break;
            case 22 :
                // de/lichtflut/rb/core/schema/OSF.g:1:145: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\35\uffff";
    static final String DFA8_eofS =
        "\35\uffff";
    static final String DFA8_minS =
        "\1\11\10\uffff\2\105\1\uffff\1\125\1\uffff\1\101\3\uffff\1\107\3"+
        "\uffff\1\114\6\uffff";
    static final String DFA8_maxS =
        "\1\175\10\uffff\1\105\1\131\1\uffff\1\125\1\uffff\1\111\3\uffff"+
        "\1\123\3\uffff\1\115\6\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\2\uffff\1\13\1\uffff\1"+
        "\16\1\uffff\1\23\1\25\1\26\1\uffff\1\12\1\15\1\20\1\uffff\1\21\1"+
        "\22\1\11\1\24\1\14\1\17";
    static final String DFA8_specialS =
        "\35\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\21\2\uffff\1\21\22\uffff\1\21\1\uffff\1\20\11\uffff\1\1\1"+
            "\7\2\uffff\12\7\3\uffff\1\3\4\uffff\1\13\1\17\2\uffff\1\15\6"+
            "\uffff\1\16\1\14\1\uffff\1\10\1\uffff\1\11\1\uffff\1\12\6\uffff"+
            "\1\5\1\uffff\1\6\35\uffff\1\2\1\uffff\1\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\22",
            "\1\23\14\uffff\1\24\6\uffff\1\25",
            "",
            "\1\26",
            "",
            "\1\30\7\uffff\1\27",
            "",
            "",
            "",
            "\1\32\13\uffff\1\31",
            "",
            "",
            "",
            "\1\34\1\33",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( COMMA | T__28 | T__29 | T__30 | T__31 | T__32 | Number | PROPERTY | RESOURCE | TEXT | BOOLEAN | NUMBER | TRUE | FALSE | NULL | TYPE | MIN | MAX | CARDINALITY | REGEX | String | WS );";
        }
    }
 

}