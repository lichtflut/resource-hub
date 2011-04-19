// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/ResourceSchema.g 2011-04-19 12:34:10

    package de.lichtflut.rb.core.schema.parser.impl;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ResourceSchemaLexer extends Lexer {
    public static final int PROPERTY_DEC=4;
    public static final int T__20=20;
    public static final int INT=12;
    public static final int NUMERIC=13;
    public static final int TEXT=14;
    public static final int EOF=-1;
    public static final int CARDINALITY=11;
    public static final int REGEX_DEC=9;
    public static final int RESOURCE_DEC=10;
    public static final int T__19=19;
    public static final int WS=17;
    public static final int T__18=18;
    public static final int BRACKET_CLOSED=7;
    public static final int IDENT=5;
    public static final int LOGICAL=15;
    public static final int TYPE_DEC=8;
    public static final int STRING=16;
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

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
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
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
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
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
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
    // $ANTLR end "T__20"

    // $ANTLR start "TYPE_DEC"
    public final void mTYPE_DEC() throws RecognitionException {
        try {
            int _type = TYPE_DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:129:10: ( ( 'TYPE IS' | 'TYPE:' | 'TYPE' | 'TYPE IS:' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:129:12: ( 'TYPE IS' | 'TYPE:' | 'TYPE' | 'TYPE IS:' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:129:12: ( 'TYPE IS' | 'TYPE:' | 'TYPE' | 'TYPE IS:' )
            int alt1=4;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:129:13: 'TYPE IS'
                    {
                    match("TYPE IS"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:129:23: 'TYPE:'
                    {
                    match("TYPE:"); 


                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:129:32: 'TYPE'
                    {
                    match("TYPE"); 


                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:129:39: 'TYPE IS:'
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:130:11: ( ( 'LIKE:' | 'REGEX:' | 'LOOKS LIKE:' | 'LIKE' | 'REGEX' | 'LOOKS LIKE' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:130:13: ( 'LIKE:' | 'REGEX:' | 'LOOKS LIKE:' | 'LIKE' | 'REGEX' | 'LOOKS LIKE' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:130:13: ( 'LIKE:' | 'REGEX:' | 'LOOKS LIKE:' | 'LIKE' | 'REGEX' | 'LOOKS LIKE' )
            int alt2=6;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:130:14: 'LIKE:'
                    {
                    match("LIKE:"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:130:24: 'REGEX:'
                    {
                    match("REGEX:"); 


                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:130:35: 'LOOKS LIKE:'
                    {
                    match("LOOKS LIKE:"); 


                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:130:51: 'LIKE'
                    {
                    match("LIKE"); 


                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:130:60: 'REGEX'
                    {
                    match("REGEX"); 


                    }
                    break;
                case 6 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:130:70: 'LOOKS LIKE'
                    {
                    match("LOOKS LIKE"); 


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

    // $ANTLR start "PROPERTY_DEC"
    public final void mPROPERTY_DEC() throws RecognitionException {
        try {
            int _type = PROPERTY_DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:131:14: ( ( 'PROPERTY' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:131:16: ( 'PROPERTY' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:131:16: ( 'PROPERTY' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:131:17: 'PROPERTY'
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:132:14: ( ( 'RESOURCE' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:132:16: ( 'RESOURCE' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:132:16: ( 'RESOURCE' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:132:17: 'RESOURCE'
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:133:9: ( ( 'NUMERIC' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:133:11: ( 'NUMERIC' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:133:11: ( 'NUMERIC' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:133:12: 'NUMERIC'
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:134:6: ( ( 'TEXT' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:134:8: ( 'TEXT' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:134:8: ( 'TEXT' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:134:9: 'TEXT'
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:135:9: ( ( 'LOGICAL' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:135:11: ( 'LOGICAL' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:135:11: ( 'LOGICAL' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:135:12: 'LOGICAL'
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:136:14: ( '(' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:136:16: '('
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:137:16: ( ')' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:137:18: ')'
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:138:13: ( ( 'HAS' | 'HAS MIN' | 'HAS MAX' | 'HASMIN' | 'HASMAX' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:138:15: ( 'HAS' | 'HAS MIN' | 'HAS MAX' | 'HASMIN' | 'HASMAX' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:138:15: ( 'HAS' | 'HAS MIN' | 'HAS MAX' | 'HASMIN' | 'HASMAX' )
            int alt3=5;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:138:16: 'HAS'
                    {
                    match("HAS"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:138:24: 'HAS MIN'
                    {
                    match("HAS MIN"); 


                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:138:36: 'HAS MAX'
                    {
                    match("HAS MAX"); 


                    }
                    break;
                case 4 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:138:48: 'HASMIN'
                    {
                    match("HASMIN"); 


                    }
                    break;
                case 5 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:138:59: 'HASMAX'
                    {
                    match("HASMAX"); 


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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:140:5: ( ( '0' .. '9' )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:140:7: ( '0' .. '9' )+
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:140:7: ( '0' .. '9' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:140:8: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:141:7: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:141:9: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // de/lichtflut/rb/core/schema/ResourceSchema.g:141:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
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
            	    break loop5;
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:142:8: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+ '\"' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:142:10: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+ '\"'
            {
            match('\"'); 
            // de/lichtflut/rb/core/schema/ResourceSchema.g:142:13: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='*' && LA6_0<='+')||LA6_0=='-'||(LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='^'||(LA6_0>='a' && LA6_0<='z')) ) {
                    alt6=1;
                }


                switch (alt6) {
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
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:143:4: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:143:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:143:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='\t' && LA7_0<='\n')||(LA7_0>='\f' && LA7_0<='\r')||LA7_0==' ') ) {
                    alt7=1;
                }


                switch (alt7) {
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
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
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
        // de/lichtflut/rb/core/schema/ResourceSchema.g:1:8: ( T__18 | T__19 | T__20 | TYPE_DEC | REGEX_DEC | PROPERTY_DEC | RESOURCE_DEC | NUMERIC | TEXT | LOGICAL | BRACKET_OPEN | BRACKET_CLOSED | CARDINALITY | INT | IDENT | STRING | WS )
        int alt8=17;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:10: T__18
                {
                mT__18(); 

                }
                break;
            case 2 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:16: T__19
                {
                mT__19(); 

                }
                break;
            case 3 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:22: T__20
                {
                mT__20(); 

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
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:47: PROPERTY_DEC
                {
                mPROPERTY_DEC(); 

                }
                break;
            case 7 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:60: RESOURCE_DEC
                {
                mRESOURCE_DEC(); 

                }
                break;
            case 8 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:73: NUMERIC
                {
                mNUMERIC(); 

                }
                break;
            case 9 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:81: TEXT
                {
                mTEXT(); 

                }
                break;
            case 10 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:86: LOGICAL
                {
                mLOGICAL(); 

                }
                break;
            case 11 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:94: BRACKET_OPEN
                {
                mBRACKET_OPEN(); 

                }
                break;
            case 12 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:107: BRACKET_CLOSED
                {
                mBRACKET_CLOSED(); 

                }
                break;
            case 13 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:122: CARDINALITY
                {
                mCARDINALITY(); 

                }
                break;
            case 14 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:134: INT
                {
                mINT(); 

                }
                break;
            case 15 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:138: IDENT
                {
                mIDENT(); 

                }
                break;
            case 16 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:144: STRING
                {
                mSTRING(); 

                }
                break;
            case 17 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:151: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA2 dfa2 = new DFA2(this);
    protected DFA3 dfa3 = new DFA3(this);
    protected DFA8 dfa8 = new DFA8(this);
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
            return "129:12: ( 'TYPE IS' | 'TYPE:' | 'TYPE' | 'TYPE IS:' )";
        }
    }
    static final String DFA2_eotS =
        "\11\uffff\1\15\5\uffff\1\22\6\uffff\1\30\2\uffff";
    static final String DFA2_eofS =
        "\31\uffff";
    static final String DFA2_minS =
        "\1\114\1\111\1\105\1\113\1\117\1\107\1\105\1\113\1\105\1\72\1\123"+
        "\1\130\2\uffff\1\40\1\72\1\114\2\uffff\1\111\1\113\1\105\1\72\2"+
        "\uffff";
    static final String DFA2_maxS =
        "\1\122\1\117\1\105\1\113\1\117\1\107\1\105\1\113\1\105\1\72\1\123"+
        "\1\130\2\uffff\1\40\1\72\1\114\2\uffff\1\111\1\113\1\105\1\72\2"+
        "\uffff";
    static final String DFA2_acceptS =
        "\14\uffff\1\1\1\4\3\uffff\1\2\1\5\4\uffff\1\3\1\6";
    static final String DFA2_specialS =
        "\31\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1\5\uffff\1\2",
            "\1\3\5\uffff\1\4",
            "\1\5",
            "\1\6",
            "\1\7",
            "\1\10",
            "\1\11",
            "\1\12",
            "\1\13",
            "\1\14",
            "\1\16",
            "\1\17",
            "",
            "",
            "\1\20",
            "\1\21",
            "\1\23",
            "",
            "",
            "\1\24",
            "\1\25",
            "\1\26",
            "\1\27",
            "",
            ""
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "130:13: ( 'LIKE:' | 'REGEX:' | 'LOOKS LIKE:' | 'LIKE' | 'REGEX' | 'LOOKS LIKE' )";
        }
    }
    static final String DFA3_eotS =
        "\3\uffff\1\6\10\uffff";
    static final String DFA3_eofS =
        "\14\uffff";
    static final String DFA3_minS =
        "\1\110\1\101\1\123\1\40\1\115\1\101\1\uffff\1\101\4\uffff";
    static final String DFA3_maxS =
        "\1\110\1\101\1\123\2\115\1\111\1\uffff\1\111\4\uffff";
    static final String DFA3_acceptS =
        "\6\uffff\1\1\1\uffff\1\4\1\5\1\2\1\3";
    static final String DFA3_specialS =
        "\14\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1",
            "\1\2",
            "\1\3",
            "\1\4\54\uffff\1\5",
            "\1\7",
            "\1\11\7\uffff\1\10",
            "",
            "\1\13\7\uffff\1\12",
            "",
            "",
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
            return "138:15: ( 'HAS' | 'HAS MIN' | 'HAS MAX' | 'HASMIN' | 'HASMAX' )";
        }
    }
    static final String DFA8_eotS =
        "\1\uffff\1\20\7\15\2\uffff\1\15\5\uffff\13\15\1\50\11\15\1\62\1"+
        "\15\1\uffff\1\65\1\66\1\67\6\15\1\uffff\2\15\3\uffff\2\15\1\67\12"+
        "\15\2\62\1\15\1\115\2\15\1\120\1\15\1\uffff\1\122\1\123\1\uffff"+
        "\1\15\2\uffff\1\125\1\uffff";
    static final String DFA8_eofS =
        "\126\uffff";
    static final String DFA8_minS =
        "\1\11\1\52\1\145\1\116\1\105\1\111\1\105\1\122\1\125\2\uffff\1\101"+
        "\5\uffff\1\146\1\104\1\120\1\130\1\113\2\107\1\117\1\115\1\123\1"+
        "\145\1\60\1\105\1\124\1\105\1\113\1\111\1\105\1\117\1\120\1\105"+
        "\1\60\1\162\1\uffff\3\60\1\123\1\103\1\130\1\125\1\105\1\122\1\uffff"+
        "\1\101\1\145\3\uffff\1\40\1\101\1\60\2\122\1\111\1\116\1\130\1\156"+
        "\1\114\1\103\1\124\1\103\2\60\1\143\1\60\1\105\1\131\1\60\1\145"+
        "\1\uffff\2\60\1\uffff\1\163\2\uffff\1\60\1\uffff";
    static final String DFA8_maxS =
        "\2\172\1\145\1\116\1\131\1\117\1\105\1\122\1\125\2\uffff\1\101\5"+
        "\uffff\1\146\1\104\1\120\1\130\1\113\1\117\1\123\1\117\1\115\1\123"+
        "\1\145\1\172\1\105\1\124\1\105\1\113\1\111\1\105\1\117\1\120\1\105"+
        "\1\172\1\162\1\uffff\3\172\1\123\1\103\1\130\1\125\1\105\1\122\1"+
        "\uffff\1\111\1\145\3\uffff\1\40\1\101\1\172\2\122\1\111\1\116\1"+
        "\130\1\156\1\114\1\103\1\124\1\103\2\172\1\143\1\172\1\105\1\131"+
        "\1\172\1\145\1\uffff\2\172\1\uffff\1\163\2\uffff\1\172\1\uffff";
    static final String DFA8_acceptS =
        "\11\uffff\1\13\1\14\1\uffff\1\16\1\17\1\21\1\20\1\1\27\uffff\1\3"+
        "\11\uffff\1\15\2\uffff\1\4\1\11\1\5\25\uffff\1\12\2\uffff\1\10\1"+
        "\uffff\1\7\1\6\1\uffff\1\2";
    static final String DFA8_specialS =
        "\126\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\16\1\uffff\2\16\22\uffff\1\16\1\uffff\1\1\5\uffff\1\11\1"+
            "\12\6\uffff\12\14\7\uffff\1\3\6\15\1\13\3\15\1\5\1\15\1\10\1"+
            "\15\1\7\1\15\1\6\1\15\1\4\6\15\6\uffff\21\15\1\2\10\15",
            "\2\17\1\uffff\1\17\2\uffff\12\17\7\uffff\32\17\3\uffff\1\17"+
            "\2\uffff\32\17",
            "\1\21",
            "\1\22",
            "\1\24\23\uffff\1\23",
            "\1\25\5\uffff\1\26",
            "\1\27",
            "\1\30",
            "\1\31",
            "",
            "",
            "\1\32",
            "",
            "",
            "",
            "",
            "",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "\1\41\7\uffff\1\40",
            "\1\42\13\uffff\1\43",
            "\1\44",
            "\1\45",
            "\1\46",
            "\1\47",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\12\15\7\uffff\14\15\1\63\15\15\4\uffff\1\15\1\uffff\32\15",
            "\1\64",
            "",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\75",
            "",
            "\1\77\7\uffff\1\76",
            "\1\100",
            "",
            "",
            "",
            "\1\67",
            "\1\101",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\1\102",
            "\1\103",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\1\114",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\1\116",
            "\1\117",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\1\121",
            "",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
            "",
            "\1\124",
            "",
            "",
            "\12\15\7\uffff\32\15\4\uffff\1\15\1\uffff\32\15",
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
            return "1:1: Tokens : ( T__18 | T__19 | T__20 | TYPE_DEC | REGEX_DEC | PROPERTY_DEC | RESOURCE_DEC | NUMERIC | TEXT | LOGICAL | BRACKET_OPEN | BRACKET_CLOSED | CARDINALITY | INT | IDENT | STRING | WS );";
        }
    }
 

}