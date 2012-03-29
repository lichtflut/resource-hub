// $ANTLR 3.4 /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g 2012-01-17 19:37:32

/*
  * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
*/
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import de.lichtflut.rb.core.schema.parser.RSErrorReporter;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class RSFLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int BOOLEAN=4;
    public static final int CARDINALITY=5;
    public static final int CONSTRAINT=6;
    public static final int DATE=7;
    public static final int DECIMAL=8;
    public static final int Digit=9;
    public static final int EscapeSequence=10;
    public static final int FIELD_LABEL=11;
    public static final int FOR=12;
    public static final int HexDigit=13;
    public static final int INTEGER=14;
    public static final int LABEL_RULE=15;
    public static final int MAX=16;
    public static final int MIN=17;
    public static final int NAMESPACE=18;
    public static final int NUMBER=19;
    public static final int Number=20;
    public static final int PREFIX=21;
    public static final int PROPERTY=22;
    public static final int RESOURCE=23;
    public static final int SCHEMA=24;
    public static final int STRING=25;
    public static final int String=26;
    public static final int TEXT=27;
    public static final int TYPE=28;
    public static final int TYPE_DEFINITION=29;
    public static final int UnicodeEscape=30;
    public static final int WS=31;

     private RSErrorReporter errorReporter = null;
     public void setErrorReporter(RSErrorReporter errorReporter) {
         this.errorReporter = errorReporter;
     }
     public void emitErrorMessage(String msg) {
         errorReporter.reportError(msg);
     }


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
    public String getGrammarFileName() { return "/Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g"; }

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:26:7: ( ':' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:26:9: ':'
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
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:27:7: ( '[' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:27:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:28:7: ( ']' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:28:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:29:7: ( '{' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:29:9: '{'
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
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:30:7: ( '}' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:30:9: '}'
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
    // $ANTLR end "T__36"

    // $ANTLR start "Number"
    public final void mNumber() throws RecognitionException {
        try {
            int _type = Number;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:114:8: ( ( '-' )? ( Digit )+ ( '.' ( Digit )+ )? )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:114:10: ( '-' )? ( Digit )+ ( '.' ( Digit )+ )?
            {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:114:10: ( '-' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='-') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:114:10: '-'
                    {
                    match('-'); 

                    }
                    break;

            }


            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:114:15: ( Digit )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
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
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:114:22: ( '.' ( Digit )+ )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='.') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:114:24: '.' ( Digit )+
                    {
                    match('.'); 

                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:114:28: ( Digit )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
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


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Number"

    // $ANTLR start "PROPERTY"
    public final void mPROPERTY() throws RecognitionException {
        try {
            int _type = PROPERTY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:117:9: ( 'PROPERTY' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:117:11: 'PROPERTY'
            {
            match("PROPERTY"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PROPERTY"

    // $ANTLR start "RESOURCE"
    public final void mRESOURCE() throws RecognitionException {
        try {
            int _type = RESOURCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:118:9: ( 'RESOURCE' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:118:11: 'RESOURCE'
            {
            match("RESOURCE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RESOURCE"

    // $ANTLR start "SCHEMA"
    public final void mSCHEMA() throws RecognitionException {
        try {
            int _type = SCHEMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:119:9: ( 'SCHEMA' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:119:13: 'SCHEMA'
            {
            match("SCHEMA"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SCHEMA"

    // $ANTLR start "TEXT"
    public final void mTEXT() throws RecognitionException {
        try {
            int _type = TEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:120:7: ( 'TEXT' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:120:9: 'TEXT'
            {
            match("TEXT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TEXT"

    // $ANTLR start "DATE"
    public final void mDATE() throws RecognitionException {
        try {
            int _type = DATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:121:7: ( 'DATE' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:121:11: 'DATE'
            {
            match("DATE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DATE"

    // $ANTLR start "BOOLEAN"
    public final void mBOOLEAN() throws RecognitionException {
        try {
            int _type = BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:122:9: ( 'BOOLEAN' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:122:11: 'BOOLEAN'
            {
            match("BOOLEAN"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BOOLEAN"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:123:9: ( 'NUMBER' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:123:11: 'NUMBER'
            {
            match("NUMBER"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "TYPE"
    public final void mTYPE() throws RecognitionException {
        try {
            int _type = TYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:124:7: ( 'TYPE' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:124:9: 'TYPE'
            {
            match("TYPE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TYPE"

    // $ANTLR start "MIN"
    public final void mMIN() throws RecognitionException {
        try {
            int _type = MIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:125:6: ( 'MIN' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:125:8: 'MIN'
            {
            match("MIN"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MIN"

    // $ANTLR start "MAX"
    public final void mMAX() throws RecognitionException {
        try {
            int _type = MAX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:126:6: ( 'MAX' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:126:8: 'MAX'
            {
            match("MAX"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MAX"

    // $ANTLR start "CARDINALITY"
    public final void mCARDINALITY() throws RecognitionException {
        try {
            int _type = CARDINALITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:127:13: ( 'CARDINALITY' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:127:15: 'CARDINALITY'
            {
            match("CARDINALITY"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CARDINALITY"

    // $ANTLR start "TYPE_DEFINITION"
    public final void mTYPE_DEFINITION() throws RecognitionException {
        try {
            int _type = TYPE_DEFINITION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:128:17: ( 'TYPE-DEFINITION' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:128:19: 'TYPE-DEFINITION'
            {
            match("TYPE-DEFINITION"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TYPE_DEFINITION"

    // $ANTLR start "CONSTRAINT"
    public final void mCONSTRAINT() throws RecognitionException {
        try {
            int _type = CONSTRAINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:129:12: ( 'CONSTRAINT' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:129:14: 'CONSTRAINT'
            {
            match("CONSTRAINT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CONSTRAINT"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:130:9: ( 'FOR' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:130:13: 'FOR'
            {
            match("FOR"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "NAMESPACE"
    public final void mNAMESPACE() throws RecognitionException {
        try {
            int _type = NAMESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:131:11: ( 'NAMESPACE' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:131:13: 'NAMESPACE'
            {
            match("NAMESPACE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NAMESPACE"

    // $ANTLR start "PREFIX"
    public final void mPREFIX() throws RecognitionException {
        try {
            int _type = PREFIX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:132:9: ( 'PREFIX' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:132:13: 'PREFIX'
            {
            match("PREFIX"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PREFIX"

    // $ANTLR start "FIELD_LABEL"
    public final void mFIELD_LABEL() throws RecognitionException {
        try {
            int _type = FIELD_LABEL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:133:12: ( 'FIELD-LABEL' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:133:14: 'FIELD-LABEL'
            {
            match("FIELD-LABEL"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FIELD_LABEL"

    // $ANTLR start "String"
    public final void mString() throws RecognitionException {
        try {
            int _type = String;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:136:8: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | '#' | '/' | ':' | '.' )+ '\"' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:136:10: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | '#' | '/' | ':' | '.' )+ '\"'
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


            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:136:52: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | '#' | '/' | ':' | '.' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='#'||(LA5_0 >= '-' && LA5_0 <= ':')||(LA5_0 >= 'A' && LA5_0 <= 'Z')||LA5_0=='_'||(LA5_0 >= 'a' && LA5_0 <= 'z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:
            	    {
            	    if ( input.LA(1)=='#'||(input.LA(1) >= '-' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
    // $ANTLR end "String"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:139:3: ( ( ' ' | '\\n' | '\\r' | '\\t' )+ )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:139:5: ( ' ' | '\\n' | '\\r' | '\\t' )+
            {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:139:5: ( ' ' | '\\n' | '\\r' | '\\t' )+
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
            	    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:
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

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:6: ( '\\\\' ( UnicodeEscape | 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:10: '\\\\' ( UnicodeEscape | 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
            {
            match('\\'); 

            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:15: ( UnicodeEscape | 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
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
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:16: UnicodeEscape
                    {
                    mUnicodeEscape(); 


                    }
                    break;
                case 2 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:31: 'b'
                    {
                    match('b'); 

                    }
                    break;
                case 3 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:35: 't'
                    {
                    match('t'); 

                    }
                    break;
                case 4 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:39: 'n'
                    {
                    match('n'); 

                    }
                    break;
                case 5 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:43: 'f'
                    {
                    match('f'); 

                    }
                    break;
                case 6 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:47: 'r'
                    {
                    match('r'); 

                    }
                    break;
                case 7 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:51: '\\\"'
                    {
                    match('\"'); 

                    }
                    break;
                case 8 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:56: '\\''
                    {
                    match('\''); 

                    }
                    break;
                case 9 :
                    // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:142:61: '\\\\'
                    {
                    match('\\'); 

                    }
                    break;

            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "UnicodeEscape"
    public final void mUnicodeEscape() throws RecognitionException {
        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:146:2: ( 'u' HexDigit HexDigit HexDigit HexDigit )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:146:4: 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('u'); 

            mHexDigit(); 


            mHexDigit(); 


            mHexDigit(); 


            mHexDigit(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:150:2: ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HexDigit"

    // $ANTLR start "Digit"
    public final void mDigit() throws RecognitionException {
        try {
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:154:2: ( '0' .. '9' )
            // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Digit"

    public void mTokens() throws RecognitionException {
        // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:8: ( T__32 | T__33 | T__34 | T__35 | T__36 | Number | PROPERTY | RESOURCE | SCHEMA | TEXT | DATE | BOOLEAN | NUMBER | TYPE | MIN | MAX | CARDINALITY | TYPE_DEFINITION | CONSTRAINT | FOR | NAMESPACE | PREFIX | FIELD_LABEL | String | WS )
        int alt8=25;
        switch ( input.LA(1) ) {
        case ':':
            {
            alt8=1;
            }
            break;
        case '[':
            {
            alt8=2;
            }
            break;
        case ']':
            {
            alt8=3;
            }
            break;
        case '{':
            {
            alt8=4;
            }
            break;
        case '}':
            {
            alt8=5;
            }
            break;
        case '-':
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt8=6;
            }
            break;
        case 'P':
            {
            int LA8_7 = input.LA(2);

            if ( (LA8_7=='R') ) {
                int LA8_19 = input.LA(3);

                if ( (LA8_19=='O') ) {
                    alt8=7;
                }
                else if ( (LA8_19=='E') ) {
                    alt8=22;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 19, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 7, input);

                throw nvae;

            }
            }
            break;
        case 'R':
            {
            alt8=8;
            }
            break;
        case 'S':
            {
            alt8=9;
            }
            break;
        case 'T':
            {
            int LA8_10 = input.LA(2);

            if ( (LA8_10=='E') ) {
                alt8=10;
            }
            else if ( (LA8_10=='Y') ) {
                int LA8_21 = input.LA(3);

                if ( (LA8_21=='P') ) {
                    int LA8_32 = input.LA(4);

                    if ( (LA8_32=='E') ) {
                        int LA8_33 = input.LA(5);

                        if ( (LA8_33=='-') ) {
                            alt8=18;
                        }
                        else {
                            alt8=14;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 32, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 21, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 10, input);

                throw nvae;

            }
            }
            break;
        case 'D':
            {
            alt8=11;
            }
            break;
        case 'B':
            {
            alt8=12;
            }
            break;
        case 'N':
            {
            int LA8_13 = input.LA(2);

            if ( (LA8_13=='U') ) {
                alt8=13;
            }
            else if ( (LA8_13=='A') ) {
                alt8=21;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 13, input);

                throw nvae;

            }
            }
            break;
        case 'M':
            {
            int LA8_14 = input.LA(2);

            if ( (LA8_14=='I') ) {
                alt8=15;
            }
            else if ( (LA8_14=='A') ) {
                alt8=16;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 14, input);

                throw nvae;

            }
            }
            break;
        case 'C':
            {
            int LA8_15 = input.LA(2);

            if ( (LA8_15=='A') ) {
                alt8=17;
            }
            else if ( (LA8_15=='O') ) {
                alt8=19;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 15, input);

                throw nvae;

            }
            }
            break;
        case 'F':
            {
            int LA8_16 = input.LA(2);

            if ( (LA8_16=='O') ) {
                alt8=20;
            }
            else if ( (LA8_16=='I') ) {
                alt8=23;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 16, input);

                throw nvae;

            }
            }
            break;
        case '\"':
            {
            alt8=24;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt8=25;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 8, 0, input);

            throw nvae;

        }

        switch (alt8) {
            case 1 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:10: T__32
                {
                mT__32(); 


                }
                break;
            case 2 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:16: T__33
                {
                mT__33(); 


                }
                break;
            case 3 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:22: T__34
                {
                mT__34(); 


                }
                break;
            case 4 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:28: T__35
                {
                mT__35(); 


                }
                break;
            case 5 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:34: T__36
                {
                mT__36(); 


                }
                break;
            case 6 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:40: Number
                {
                mNumber(); 


                }
                break;
            case 7 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:47: PROPERTY
                {
                mPROPERTY(); 


                }
                break;
            case 8 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:56: RESOURCE
                {
                mRESOURCE(); 


                }
                break;
            case 9 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:65: SCHEMA
                {
                mSCHEMA(); 


                }
                break;
            case 10 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:72: TEXT
                {
                mTEXT(); 


                }
                break;
            case 11 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:77: DATE
                {
                mDATE(); 


                }
                break;
            case 12 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:82: BOOLEAN
                {
                mBOOLEAN(); 


                }
                break;
            case 13 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:90: NUMBER
                {
                mNUMBER(); 


                }
                break;
            case 14 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:97: TYPE
                {
                mTYPE(); 


                }
                break;
            case 15 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:102: MIN
                {
                mMIN(); 


                }
                break;
            case 16 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:106: MAX
                {
                mMAX(); 


                }
                break;
            case 17 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:110: CARDINALITY
                {
                mCARDINALITY(); 


                }
                break;
            case 18 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:122: TYPE_DEFINITION
                {
                mTYPE_DEFINITION(); 


                }
                break;
            case 19 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:138: CONSTRAINT
                {
                mCONSTRAINT(); 


                }
                break;
            case 20 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:149: FOR
                {
                mFOR(); 


                }
                break;
            case 21 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:153: NAMESPACE
                {
                mNAMESPACE(); 


                }
                break;
            case 22 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:163: PREFIX
                {
                mPREFIX(); 


                }
                break;
            case 23 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:170: FIELD_LABEL
                {
                mFIELD_LABEL(); 


                }
                break;
            case 24 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:182: String
                {
                mString(); 


                }
                break;
            case 25 :
                // /Users/otigges/projects/rb/core/src/main/antlr3/de/lichtflut/rb/core/schema/parser/impl/rsf/RSF.g:1:189: WS
                {
                mWS(); 


                }
                break;

        }

    }


 

}