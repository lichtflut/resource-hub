grammar ResourceSchema;

tokens {
	PLUS 	= '+' ;
	MINUS	= '-' ;
	MULT	= '*' ;
	DIV	= '/' ;
	PROPERTY_DEC_UPPER = 'PROPERTY';
	PROPERTY_DEC_LOWER = 'property';
        RESOURCE_DEC_UPPER = 'RESOURCE';
	RESOURCE_DEC_LOWER = 'resource';
}

@members {
    public static void main(String[] args) throws Exception {
        ResourceSchemaLexer lex = new ResourceSchemaLexer(new ANTLRFileStream(args[0]));
       	CommonTokenStream tokens = new CommonTokenStream(lex);

        ResourceSchemaParser parser = new ResourceSchemaParser(tokens);

        try {
            parser.expr();
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

start : term*EOF;

term : (resource_definition | property_definition);
			 
resource_definition : RESOURCE_PREFIX_DELIM IDENTIFIER  RESOURCE_SUFFIX_DELIM;

property_definition : PROPERTY_PREFIX_DELIM IDENTIFIER RESOURCE_SUFFIX_DELIM;
	

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

NUMBER	: (DIGIT)+ ;


RESOURCE_PREFIX_DELIM : WHITESPACE* ('resource' | 'RESOURCE') WHITESPACE* ':' WHITESPACE* IDENTIFIER;

PROPERTY_PREFIX_DELIM : WHITESPACE* ('property' | 'PROPERTY') WHITESPACE* ':' WHITESPACE* IDENTIFIER;		

IDENTIFIER : URI ALPHANUMERIC;

URI : ALPHANUMERIC+ '://' ALPHANUMERIC+ '/';

ALPHANUMERIC : 'a..z' | 'A..Z' | '0..9';		

RESOURCE_SUFFIX_DELIM :	'd';

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ 	{ $channel = HIDDEN; } ;

fragment DIGIT	: '0'..'9' ;
