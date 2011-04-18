// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 de/lichtflut/rb/core/schema/ResourceSchema.g 2011-04-18 22:23:29

    package de.lichtflut.rb.core.schema.parser.impl;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ResourceSchemaLexer extends Lexer {
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

    public ResourceSchemaLexer() {;} 
    public ResourceSchemaLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ResourceSchemaLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "de/lichtflut/rb/core/schema/ResourceSchema.g"; }

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
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
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
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
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
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
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
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
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
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
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
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
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
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
    // $ANTLR end "T__21"

    // $ANTLR start "PROPERTY_DEC"
    public final void mPROPERTY_DEC() throws RecognitionException {
        try {
            int _type = PROPERTY_DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:123:14: ( ( 'PROPERTY' | 'property' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:123:16: ( 'PROPERTY' | 'property' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:123:16: ( 'PROPERTY' | 'property' )
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
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:123:17: 'PROPERTY'
                    {
                    match("PROPERTY"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:123:30: 'property'
                    {
                    match("property"); 


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

    // $ANTLR start "RESOURCE_DEC"
    public final void mRESOURCE_DEC() throws RecognitionException {
        try {
            int _type = RESOURCE_DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:124:14: ( ( 'RESOURCE' | 'resource' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:124:16: ( 'RESOURCE' | 'resource' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:124:16: ( 'RESOURCE' | 'resource' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='R') ) {
                alt2=1;
            }
            else if ( (LA2_0=='r') ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:124:17: 'RESOURCE'
                    {
                    match("RESOURCE"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:124:30: 'resource'
                    {
                    match("resource"); 


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
    // $ANTLR end "RESOURCE_DEC"

    // $ANTLR start "NUMERIC"
    public final void mNUMERIC() throws RecognitionException {
        try {
            int _type = NUMERIC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:125:9: ( ( 'NUMERIC' | 'numeric' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:125:11: ( 'NUMERIC' | 'numeric' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:125:11: ( 'NUMERIC' | 'numeric' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='N') ) {
                alt3=1;
            }
            else if ( (LA3_0=='n') ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:125:12: 'NUMERIC'
                    {
                    match("NUMERIC"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:125:24: 'numeric'
                    {
                    match("numeric"); 


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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:126:6: ( ( 'TEXT' | 'text' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:126:8: ( 'TEXT' | 'text' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:126:8: ( 'TEXT' | 'text' )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='T') ) {
                alt4=1;
            }
            else if ( (LA4_0=='t') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:126:9: 'TEXT'
                    {
                    match("TEXT"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:126:18: 'text'
                    {
                    match("text"); 


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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:127:9: ( ( 'LOGICAL' | 'logical' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:127:11: ( 'LOGICAL' | 'logical' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:127:11: ( 'LOGICAL' | 'logical' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='L') ) {
                alt5=1;
            }
            else if ( (LA5_0=='l') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:127:12: 'LOGICAL'
                    {
                    match("LOGICAL"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:127:24: 'logical'
                    {
                    match("logical"); 


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

    // $ANTLR start "EOL"
    public final void mEOL() throws RecognitionException {
        try {
            int _type = EOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:128:6: ( '\\n' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:128:9: '\\n'
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:129:13: ( ( 'has' | 'hasMin' | 'hasMax' ) )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:129:15: ( 'has' | 'hasMin' | 'hasMax' )
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:129:15: ( 'has' | 'hasMin' | 'hasMax' )
            int alt6=3;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='h') ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1=='a') ) {
                    int LA6_2 = input.LA(3);

                    if ( (LA6_2=='s') ) {
                        int LA6_3 = input.LA(4);

                        if ( (LA6_3=='M') ) {
                            int LA6_4 = input.LA(5);

                            if ( (LA6_4=='i') ) {
                                alt6=2;
                            }
                            else if ( (LA6_4=='a') ) {
                                alt6=3;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 6, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            alt6=1;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:129:16: 'has'
                    {
                    match("has"); 


                    }
                    break;
                case 2 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:129:24: 'hasMin'
                    {
                    match("hasMin"); 


                    }
                    break;
                case 3 :
                    // de/lichtflut/rb/core/schema/ResourceSchema.g:129:35: 'hasMax'
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

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de/lichtflut/rb/core/schema/ResourceSchema.g:131:5: ( ( '0' .. '9' )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:131:7: ( '0' .. '9' )+
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:131:7: ( '0' .. '9' )+
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
            	    // de/lichtflut/rb/core/schema/ResourceSchema.g:131:8: '0' .. '9'
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:132:7: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:132:9: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // de/lichtflut/rb/core/schema/ResourceSchema.g:132:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:133:8: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+ '\"' )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:133:10: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+ '\"'
            {
            match('\"'); 
            // de/lichtflut/rb/core/schema/ResourceSchema.g:133:13: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '-' | '*' | '+' | '^' )+
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
            // de/lichtflut/rb/core/schema/ResourceSchema.g:134:4: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // de/lichtflut/rb/core/schema/ResourceSchema.g:134:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // de/lichtflut/rb/core/schema/ResourceSchema.g:134:6: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
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
        // de/lichtflut/rb/core/schema/ResourceSchema.g:1:8: ( T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | PROPERTY_DEC | RESOURCE_DEC | NUMERIC | TEXT | LOGICAL | EOL | CARDINALITY | INT | IDENT | STRING | WS )
        int alt11=18;
        alt11 = dfa11.predict(input);
        switch (alt11) {
            case 1 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:10: T__15
                {
                mT__15(); 

                }
                break;
            case 2 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:16: T__16
                {
                mT__16(); 

                }
                break;
            case 3 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:22: T__17
                {
                mT__17(); 

                }
                break;
            case 4 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:28: T__18
                {
                mT__18(); 

                }
                break;
            case 5 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:34: T__19
                {
                mT__19(); 

                }
                break;
            case 6 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:40: T__20
                {
                mT__20(); 

                }
                break;
            case 7 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:46: T__21
                {
                mT__21(); 

                }
                break;
            case 8 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:52: PROPERTY_DEC
                {
                mPROPERTY_DEC(); 

                }
                break;
            case 9 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:65: RESOURCE_DEC
                {
                mRESOURCE_DEC(); 

                }
                break;
            case 10 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:78: NUMERIC
                {
                mNUMERIC(); 

                }
                break;
            case 11 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:86: TEXT
                {
                mTEXT(); 

                }
                break;
            case 12 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:91: LOGICAL
                {
                mLOGICAL(); 

                }
                break;
            case 13 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:99: EOL
                {
                mEOL(); 

                }
                break;
            case 14 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:103: CARDINALITY
                {
                mCARDINALITY(); 

                }
                break;
            case 15 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:115: INT
                {
                mINT(); 

                }
                break;
            case 16 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:119: IDENT
                {
                mIDENT(); 

                }
                break;
            case 17 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:125: STRING
                {
                mSTRING(); 

                }
                break;
            case 18 :
                // de/lichtflut/rb/core/schema/ResourceSchema.g:1:132: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA11_eotS =
        "\3\uffff\2\22\1\30\11\22\1\42\1\22\3\uffff\3\22\2\uffff\11\22\1"+
        "\uffff\6\22\1\70\10\22\1\102\1\22\1\104\3\22\1\uffff\5\22\1\104"+
        "\3\22\3\uffff\1\121\13\22\1\uffff\11\22\2\102\5\22\2\153\2\154\1"+
        "\22\1\156\2\157\1\156\2\uffff\1\22\2\uffff\1\161\1\uffff";
    static final String DFA11_eofS =
        "\162\uffff";
    static final String DFA11_minS =
        "\1\11\2\uffff\2\145\1\52\1\156\1\122\1\162\1\105\1\125\1\165\1\105"+
        "\1\117\1\157\1\11\1\141\3\uffff\1\160\1\170\1\146\2\uffff\1\144"+
        "\1\117\1\157\1\123\1\115\1\155\1\130\1\107\1\147\1\uffff\1\163\1"+
        "\145\1\164\2\145\1\157\1\60\1\120\1\160\1\117\1\105\1\145\1\124"+
        "\1\111\1\151\1\60\1\40\1\60\1\170\1\162\1\165\1\uffff\1\105\1\145"+
        "\1\125\1\122\1\162\1\60\1\103\1\143\1\141\3\uffff\1\60\1\145\1\162"+
        "\1\122\1\162\1\122\1\111\1\151\1\101\1\141\1\156\1\170\1\uffff\1"+
        "\156\1\143\1\124\1\164\2\103\1\143\1\114\1\154\2\60\1\143\1\145"+
        "\1\131\1\171\1\105\4\60\1\145\4\60\2\uffff\1\163\2\uffff\1\60\1"+
        "\uffff";
    static final String DFA11_maxS =
        "\1\172\2\uffff\1\171\1\145\1\172\1\156\1\122\1\162\1\105\1\125\1"+
        "\165\1\105\1\117\1\157\1\40\1\141\3\uffff\1\160\1\170\1\163\2\uffff"+
        "\1\144\1\117\1\157\1\123\1\115\1\155\1\130\1\107\1\147\1\uffff\1"+
        "\163\1\145\1\164\2\145\1\157\1\172\1\120\1\160\1\117\1\105\1\145"+
        "\1\124\1\111\1\151\1\172\1\40\1\172\1\170\1\162\1\165\1\uffff\1"+
        "\105\1\145\1\125\1\122\1\162\1\172\1\103\1\143\1\151\3\uffff\1\172"+
        "\1\145\1\162\1\122\1\162\1\122\1\111\1\151\1\101\1\141\1\156\1\170"+
        "\1\uffff\1\156\1\143\1\124\1\164\2\103\1\143\1\114\1\154\2\172\1"+
        "\143\1\145\1\131\1\171\1\105\4\172\1\145\4\172\2\uffff\1\163\2\uffff"+
        "\1\172\1\uffff";
    static final String DFA11_acceptS =
        "\1\uffff\1\1\1\2\16\uffff\1\17\1\20\1\22\3\uffff\1\21\1\5\11\uffff"+
        "\1\15\25\uffff\1\7\11\uffff\1\16\1\3\1\13\14\uffff\1\4\31\uffff"+
        "\1\12\1\14\1\uffff\1\11\1\10\1\uffff\1\6";
    static final String DFA11_specialS =
        "\162\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\23\1\17\1\uffff\2\23\22\uffff\1\23\1\uffff\1\5\5\uffff\1"+
            "\1\1\2\6\uffff\12\21\7\uffff\13\22\1\15\1\22\1\12\1\22\1\7\1"+
            "\22\1\11\1\22\1\14\6\22\6\uffff\1\6\6\22\1\20\3\22\1\16\1\22"+
            "\1\13\1\22\1\10\1\22\1\4\1\22\1\3\6\22",
            "",
            "",
            "\1\25\23\uffff\1\24",
            "\1\26",
            "\2\27\1\uffff\1\27\2\uffff\12\27\7\uffff\32\27\3\uffff\1\27"+
            "\2\uffff\32\27",
            "\1\31",
            "\1\32",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "\1\40",
            "\1\41",
            "\2\23\1\uffff\2\23\22\uffff\1\23",
            "\1\43",
            "",
            "",
            "",
            "\1\44",
            "\1\45",
            "\1\47\1\46\13\uffff\1\50",
            "",
            "",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\71",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\12\22\7\uffff\14\22\1\101\15\22\4\uffff\1\22\1\uffff\32\22",
            "\1\103",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\105",
            "\1\106",
            "\1\107",
            "",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\115",
            "\1\116",
            "\1\120\7\uffff\1\117",
            "",
            "",
            "",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\143",
            "\1\144",
            "\1\145",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "\1\152",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\1\155",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "\12\22\7\uffff\32\22\4\uffff\1\22\1\uffff\32\22",
            "",
            "",
            "\1\160",
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
            return "1:1: Tokens : ( T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | PROPERTY_DEC | RESOURCE_DEC | NUMERIC | TEXT | LOGICAL | EOL | CARDINALITY | INT | IDENT | STRING | WS );";
        }
    }
 

}