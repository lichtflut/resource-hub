// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/ResourceSchema.g 2011-04-27 16:42:19

    package de.lichtflut.rb.core.schema.parser.impl.simplersf;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ResourceSchemaLexer extends Lexer {
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

    public ResourceSchemaLexer() {;} 
    public ResourceSchemaLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ResourceSchemaLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "de/lichtflut/rb/core/schema/ResourceSchema.g"; }

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:11:7: ( '\"' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:11:9: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:12:7: ( 'references' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:12:9: 'references'
            {
            match("references"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:13:7: ( 'AND' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:13:9: 'AND'
            {
            match("AND"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "TYPE_DEC"
    public final void mTYPE_DEC() throws RecognitionException {
        try {
            int _type = TYPE_DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:154:10: ( ( 'TYPE IS' | 'TYPE:' | 'TYPE' | 'TYPE IS:' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:154:12: ( 'TYPE IS' | 'TYPE:' | 'TYPE' | 'TYPE IS:' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:154:12: ( 'TYPE IS' | 'TYPE:' | 'TYPE' | 'TYPE IS:' )
            int alt1=4;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:154:13: 'TYPE IS'
                    {
                    match("TYPE IS"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:154:23: 'TYPE:'
                    {
                    match("TYPE:"); 


                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:154:32: 'TYPE'
                    {
                    match("TYPE"); 


                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:154:39: 'TYPE IS:'
                    {
                    match("TYPE IS:"); 


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
    // $ANTLR end "TYPE_DEC"

    // $ANTLR start "REGEX_DEC"
    public final void mREGEX_DEC() throws RecognitionException {
        try {
            int _type = REGEX_DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:155:11: ( ( 'LIKE' | 'REGEX' | 'LOOKS LIKE' ) ( DELIM )? )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:155:13: ( 'LIKE' | 'REGEX' | 'LOOKS LIKE' ) ( DELIM )?
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:155:13: ( 'LIKE' | 'REGEX' | 'LOOKS LIKE' )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='L') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='I') ) {
                    alt2=1;
                }
                else if ( (LA2_1=='O') ) {
                    alt2=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA2_0=='R') ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:155:14: 'LIKE'
                    {
                    match("LIKE"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:155:23: 'REGEX'
                    {
                    match("REGEX"); 


                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:155:33: 'LOOKS LIKE'
                    {
                    match("LOOKS LIKE"); 


                    }
                    break;

            }

            // de/lichtflut/rb/core/schema/ResourceSchema.g:155:46: ( DELIM )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==':') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:155:46: DELIM
                    {
                    mDELIM(); 

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
    // $ANTLR end "REGEX_DEC"

    // $ANTLR start "DELIM"
    public final void mDELIM() throws RecognitionException {
        try {
            int _type = DELIM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:156:7: ( ( ':' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:156:9: ( ':' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:156:9: ( ':' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:156:10: ':'
            {
            match(':'); 

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DELIM"

    // $ANTLR start "PROPERTY_DEC"
    public final void mPROPERTY_DEC() throws RecognitionException {
        try {
            int _type = PROPERTY_DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:157:14: ( ( 'PROPERTY' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:157:16: ( 'PROPERTY' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:157:16: ( 'PROPERTY' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:157:17: 'PROPERTY'
            {
            match("PROPERTY"); 


            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROPERTY_DEC"

    // $ANTLR start "RESOURCE_DEC"
    public final void mRESOURCE_DEC() throws RecognitionException {
        try {
            int _type = RESOURCE_DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:158:14: ( ( 'RESOURCE' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:158:16: ( 'RESOURCE' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:158:16: ( 'RESOURCE' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:158:17: 'RESOURCE'
            {
            match("RESOURCE"); 


            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RESOURCE_DEC"

    // $ANTLR start "NUMERIC"
    public final void mNUMERIC() throws RecognitionException {
        try {
            int _type = NUMERIC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:159:9: ( ( 'NUMERIC' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:159:11: ( 'NUMERIC' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:159:11: ( 'NUMERIC' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:159:12: 'NUMERIC'
            {
            match("NUMERIC"); 


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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:160:6: ( ( 'TEXT' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:160:8: ( 'TEXT' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:160:8: ( 'TEXT' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:160:9: 'TEXT'
            {
            match("TEXT"); 


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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:161:9: ( ( 'LOGICAL' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:161:11: ( 'LOGICAL' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:161:11: ( 'LOGICAL' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:161:12: 'LOGICAL'
            {
            match("LOGICAL"); 


            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOGICAL"

    // $ANTLR start "BRACKET_OPEN"
    public final void mBRACKET_OPEN() throws RecognitionException {
        try {
            int _type = BRACKET_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:162:14: ( '(' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:162:16: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BRACKET_OPEN"

    // $ANTLR start "BRACKET_CLOSED"
    public final void mBRACKET_CLOSED() throws RecognitionException {
        try {
            int _type = BRACKET_CLOSED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:163:16: ( ')' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:163:18: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BRACKET_CLOSED"

    // $ANTLR start "CARDINALITY"
    public final void mCARDINALITY() throws RecognitionException {
        try {
            int _type = CARDINALITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:164:13: ( ( 'HAS' | 'HAS_MIN' | 'HAS_MAX' | 'HASMIN' | 'HASMAX' ) ( DELIM )? )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:164:15: ( 'HAS' | 'HAS_MIN' | 'HAS_MAX' | 'HASMIN' | 'HASMAX' ) ( DELIM )?
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:164:15: ( 'HAS' | 'HAS_MIN' | 'HAS_MAX' | 'HASMIN' | 'HASMAX' )
            int alt4=5;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:164:16: 'HAS'
                    {
                    match("HAS"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:164:24: 'HAS_MIN'
                    {
                    match("HAS_MIN"); 


                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:164:36: 'HAS_MAX'
                    {
                    match("HAS_MAX"); 


                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:164:48: 'HASMIN'
                    {
                    match("HASMIN"); 


                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:164:59: 'HASMAX'
                    {
                    match("HASMAX"); 


                    }
                    break;

            }

            // de/lichtflut/rb/core/schema/ResourceSchema.g:164:68: ( DELIM )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==':') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:164:68: DELIM
                    {
                    mDELIM(); 

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

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:166:5: ( ( '0' .. '9' )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:166:7: ( '0' .. '9' )+
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:166:7: ( '0' .. '9' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:166:8: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:167:7: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:167:10: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // de/lichtflut/rb/core/schema/ResourceSchema.g:167:35: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='Z')||LA7_0=='_'||(LA7_0>='a' && LA7_0<='z')) ) {
                    alt7=1;
                }


                switch (alt7) {
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
            	    break loop7;
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:168:8: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+ '\"' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:168:10: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+ '\"'
            {
            match('\"'); 
            // de/lichtflut/rb/core/schema/ResourceSchema.g:168:13: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='*' && LA8_0<='+')||LA8_0=='-'||(LA8_0>='0' && LA8_0<='9')||(LA8_0>='A' && LA8_0<='Z')||LA8_0=='^'||(LA8_0>='a' && LA8_0<='z')) ) {
                    alt8=1;
                }


                switch (alt8) {
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
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:169:4: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:169:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:169:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='\t' && LA9_0<='\n')||(LA9_0>='\f' && LA9_0<='\r')||LA9_0==' ') ) {
                    alt9=1;
                }


                switch (alt9) {
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
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);

             skip();  _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // de/lichtflut/rb/core/schema/ResourceSchema.g:1:8: ( T__19 | T__20 | T__21 | TYPE_DEC | REGEX_DEC | DELIM | PROPERTY_DEC | RESOURCE_DEC | NUMERIC | TEXT | LOGICAL | BRACKET_OPEN | BRACKET_CLOSED | CARDINALITY | INT | IDENT | STRING | WS )
        int alt10=18;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:10: T__19
                {
                mT__19(); 

                }
                break;
            case 2 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:16: T__20
                {
                mT__20(); 

                }
                break;
            case 3 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:22: T__21
                {
                mT__21(); 

                }
                break;
            case 4 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:28: TYPE_DEC
                {
                mTYPE_DEC(); 

                }
                break;
            case 5 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:37: REGEX_DEC
                {
                mREGEX_DEC(); 

                }
                break;
            case 6 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:47: DELIM
                {
                mDELIM(); 

                }
                break;
            case 7 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:53: PROPERTY_DEC
                {
                mPROPERTY_DEC(); 

                }
                break;
            case 8 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:66: RESOURCE_DEC
                {
                mRESOURCE_DEC(); 

                }
                break;
            case 9 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:79: NUMERIC
                {
                mNUMERIC(); 

                }
                break;
            case 10 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:87: TEXT
                {
                mTEXT(); 

                }
                break;
            case 11 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:92: LOGICAL
                {
                mLOGICAL(); 

                }
                break;
            case 12 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:100: BRACKET_OPEN
                {
                mBRACKET_OPEN(); 

                }
                break;
            case 13 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:113: BRACKET_CLOSED
                {
                mBRACKET_CLOSED(); 

                }
                break;
            case 14 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:128: CARDINALITY
                {
                mCARDINALITY(); 

                }
                break;
            case 15 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:140: INT
                {
                mINT(); 

                }
                break;
            case 16 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:144: IDENT
                {
                mIDENT(); 

                }
                break;
            case 17 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:150: STRING
                {
                mSTRING(); 

                }
                break;
            case 18 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:157: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA1_eotS =
        "\4\uffff\1\7\4\uffff\1\13\2\uffff";
    static final String DFA1_eofS =
        "\14\uffff";
    static final String DFA1_minS =
        "\1\124\1\131\1\120\1\105\1\40\1\111\2\uffff\1\123\1\72\2\uffff";
    static final String DFA1_maxS =
        "\1\124\1\131\1\120\1\105\1\72\1\111\2\uffff\1\123\1\72\2\uffff";
    static final String DFA1_acceptS =
        "\6\uffff\1\2\1\3\2\uffff\1\4\1\1";
    static final String DFA1_specialS =
        "\14\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\1",
            "\1\2",
            "\1\3",
            "\1\4",
            "\1\5\31\uffff\1\6",
            "\1\10",
            "",
            "",
            "\1\11",
            "\1\12",
            "",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "154:12: ( 'TYPE IS' | 'TYPE:' | 'TYPE' | 'TYPE IS:' )";
        }
    }
    static final String DFA4_eotS =
        "\3\uffff\1\6\10\uffff";
    static final String DFA4_eofS =
        "\14\uffff";
    static final String DFA4_minS =
        "\1\110\1\101\1\123\2\115\1\101\1\uffff\1\101\4\uffff";
    static final String DFA4_maxS =
        "\1\110\1\101\1\123\1\137\1\115\1\111\1\uffff\1\111\4\uffff";
    static final String DFA4_acceptS =
        "\6\uffff\1\1\1\uffff\1\4\1\5\1\2\1\3";
    static final String DFA4_specialS =
        "\14\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1",
            "\1\2",
            "\1\3",
            "\1\5\21\uffff\1\4",
            "\1\7",
            "\1\11\7\uffff\1\10",
            "",
            "\1\13\7\uffff\1\12",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "164:15: ( 'HAS' | 'HAS_MIN' | 'HAS_MAX' | 'HASMIN' | 'HASMAX' )";
        }
    }
    static final String DFA10_eotS =
        "\1\uffff\1\21\5\16\1\uffff\2\16\2\uffff\1\16\5\uffff\13\16\1\51"+
        "\11\16\1\65\1\16\1\uffff\1\67\1\70\1\71\10\16\1\uffff\1\16\3\uffff"+
        "\2\16\1\71\15\16\2\65\1\16\1\124\2\16\1\127\2\65\1\16\1\uffff\1"+
        "\131\1\132\1\uffff\1\16\2\uffff\1\134\1\uffff";
    static final String DFA10_eofS =
        "\135\uffff";
    static final String DFA10_minS =
        "\1\11\1\52\1\145\1\116\1\105\1\111\1\105\1\uffff\1\122\1\125\2\uffff"+
        "\1\101\5\uffff\1\146\1\104\1\120\1\130\1\113\2\107\1\117\1\115\1"+
        "\123\1\145\1\60\1\105\1\124\1\105\1\113\1\111\1\105\1\117\1\120"+
        "\1\105\1\60\1\162\1\uffff\3\60\1\123\1\103\1\130\1\125\1\105\1\122"+
        "\1\115\1\101\1\uffff\1\145\3\uffff\1\40\1\101\1\60\2\122\1\111\1"+
        "\101\1\116\1\130\1\156\1\114\1\103\1\124\1\103\1\116\1\130\2\60"+
        "\1\143\1\60\1\105\1\131\3\60\1\145\1\uffff\2\60\1\uffff\1\163\2"+
        "\uffff\1\60\1\uffff";
    static final String DFA10_maxS =
        "\2\172\1\145\1\116\1\131\1\117\1\105\1\uffff\1\122\1\125\2\uffff"+
        "\1\101\5\uffff\1\146\1\104\1\120\1\130\1\113\1\117\1\123\1\117\1"+
        "\115\1\123\1\145\1\172\1\105\1\124\1\105\1\113\1\111\1\105\1\117"+
        "\1\120\1\105\1\172\1\162\1\uffff\3\172\1\123\1\103\1\130\1\125\1"+
        "\105\1\122\1\115\1\111\1\uffff\1\145\3\uffff\1\40\1\101\1\172\2"+
        "\122\2\111\1\116\1\130\1\156\1\114\1\103\1\124\1\103\1\116\1\130"+
        "\2\172\1\143\1\172\1\105\1\131\3\172\1\145\1\uffff\2\172\1\uffff"+
        "\1\163\2\uffff\1\172\1\uffff";
    static final String DFA10_acceptS =
        "\7\uffff\1\6\2\uffff\1\14\1\15\1\uffff\1\17\1\20\1\22\1\21\1\1\27"+
        "\uffff\1\3\13\uffff\1\16\1\uffff\1\4\1\12\1\5\32\uffff\1\13\2\uffff"+
        "\1\11\1\uffff\1\10\1\7\1\uffff\1\2";
    static final String DFA10_specialS =
        "\135\uffff}>";
    static final String[] DFA10_transitionS = {
            "\2\17\1\uffff\2\17\22\uffff\1\17\1\uffff\1\1\5\uffff\1\12\1"+
            "\13\6\uffff\12\15\1\7\6\uffff\1\3\6\16\1\14\3\16\1\5\1\16\1"+
            "\11\1\16\1\10\1\16\1\6\1\16\1\4\6\16\6\uffff\21\16\1\2\10\16",
            "\2\20\1\uffff\1\20\2\uffff\12\20\7\uffff\32\20\3\uffff\1\20"+
            "\2\uffff\32\20",
            "\1\22",
            "\1\23",
            "\1\25\23\uffff\1\24",
            "\1\26\5\uffff\1\27",
            "\1\30",
            "",
            "\1\31",
            "\1\32",
            "",
            "",
            "\1\33",
            "",
            "",
            "",
            "",
            "",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "\1\40",
            "\1\42\7\uffff\1\41",
            "\1\43\13\uffff\1\44",
            "\1\45",
            "\1\46",
            "\1\47",
            "\1\50",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\12\16\7\uffff\14\16\1\64\15\16\4\uffff\1\63\1\uffff\32\16",
            "\1\66",
            "",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\102\7\uffff\1\101",
            "",
            "\1\103",
            "",
            "",
            "",
            "\1\71",
            "\1\104",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\111\7\uffff\1\110",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\117",
            "\1\120",
            "\1\121",
            "\1\122",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\1\123",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\1\125",
            "\1\126",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\1\130",
            "",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            "",
            "\1\133",
            "",
            "",
            "\12\16\7\uffff\32\16\4\uffff\1\16\1\uffff\32\16",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__19 | T__20 | T__21 | TYPE_DEC | REGEX_DEC | DELIM | PROPERTY_DEC | RESOURCE_DEC | NUMERIC | TEXT | LOGICAL | BRACKET_OPEN | BRACKET_CLOSED | CARDINALITY | INT | IDENT | STRING | WS );";
        }
    }
 

}