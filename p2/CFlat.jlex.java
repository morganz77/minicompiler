import java_cup.runtime.*; // defines the Symbol class
// The generated scanner will return a Symbol for each token that it finds.
// A Symbol contains an Object field named value; that field will be of type
// TokenVal, defined below.
//
// A TokenVal object contains the line number on which the token occurs as
// well as the number of the character on that line that starts the token.
// Some tokens (literals and IDs) also include the value of the token.
class TokenVal {
  // fields
    int linenum;
    int charnum;
  // constructor
    TokenVal(int line, int ch) {
        linenum = line;
        charnum = ch;
    }
}
class IntLitTokenVal extends TokenVal {
  // new field: the value of the integer literal
    int intVal;
  // constructor
    IntLitTokenVal(int line, int ch, int val) {
        super(line, ch);
        intVal = val;
    }
}
class IdTokenVal extends TokenVal {
  // new field: the value of the identifier
    String idVal;
  // constructor
    IdTokenVal(int line, int ch, String val) {
        super(line, ch);
    idVal = val;
    }
}
class StrLitTokenVal extends TokenVal {
  // new field: the value of the string literal
    String strVal;
  // constructor
    StrLitTokenVal(int line, int ch, String val) {
        super(line, ch);
        strVal = val;
    }
}
// The following class is used to keep track of the character number at which
// the current token starts on its line.
class CharNum {
    static int num=1;
}
//maps names to sym constants
class Map {
    static java.util.HashMap<String, Integer> m_reserved = 
                                     new java.util.HashMap<String, Integer>();
    static java.util.HashMap<String, Integer> m_charsym = 
                                    new java.util.HashMap<String, Integer>();
    static{
        m_charsym.put("{", sym.LCURLY);
        m_charsym.put("}", sym.RCURLY);
        m_charsym.put("(", sym.LPAREN);
        m_charsym.put(")", sym.RPAREN);
        m_charsym.put(";", sym.SEMICOLON);
        m_charsym.put(",", sym.COMMA);
        m_charsym.put(".", sym.DOT);
        m_charsym.put("<<", sym.WRITE);
        m_charsym.put(">>", sym.READ);
        m_charsym.put("++", sym.PLUSPLUS);
        m_charsym.put("--", sym.MINUSMINUS);
        m_charsym.put("+", sym.PLUS);
        m_charsym.put("-", sym.MINUS);
        m_charsym.put("*", sym.TIMES);
        m_charsym.put("/", sym.DIVIDE);
        m_charsym.put("!", sym.NOT);
        m_charsym.put("&&", sym.AND);
        m_charsym.put("||", sym.OR);
        m_charsym.put("==", sym.EQUALS);
        m_charsym.put("!=", sym.NOTEQUALS);
        m_charsym.put("<", sym.LESS);
        m_charsym.put(">", sym.GREATER);
        m_charsym.put("<=", sym.LESSEQ);
        m_charsym.put(">=", sym.GREATEREQ);
        m_charsym.put("=", sym.ASSIGN);
        m_reserved.put("bool", sym.BOOL);
        m_reserved.put("int", sym.INT);
        m_reserved.put("void", sym.VOID);
        m_reserved.put("true", sym.TRUE);
        m_reserved.put("false", sym.FALSE);
        m_reserved.put("struct", sym.STRUCT);
        m_reserved.put("cin", sym.CIN);
        m_reserved.put("cout", sym.COUT);
        m_reserved.put("if", sym.IF);
        m_reserved.put("else", sym.ELSE);
        m_reserved.put("while", sym.WHILE);
        m_reserved.put("return", sym.RETURN);
    }
}


class Yylex implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NOT_ACCEPT,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NOT_ACCEPT,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NOT_ACCEPT,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"37:9,3,2,37:2,35,37:18,3,10,32,36,37:2,11,34,5:3,4,5,8,5,9,1:10,37,5,6,13,7" +
",34,37,31:26,37,33,37:2,31,37,26,14,28,21,24,25,31,30,17,31:2,16,31,18,15,3" +
"1:2,22,27,19,23,20,29,31:3,5,12,5,37:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,55,
"0,1,2,1,3,4,1,5,6,7,8,9,1,10,1,11,12,13,14,15,16,1,17,18,19,20,21,22,23,24," +
"25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,9,41,42,43,44,33,45,46,47")[0];

	private int yy_nxt[][] = unpackFromString(48,38,
"1,2,3,4,5,6,16,20,24,26,28,7,17,28,8,46:2,18,46,48,49,46,50,46,51,52,46,53," +
"31,54,46:2,9,21:2,-1,10,21,-1:39,2,-1:39,4,-1:38,6,-1:44,6,-1:27,46,-1:12,4" +
"6,33,46:16,-1:7,9,-1,9:29,12,15,9:4,-1,10,-1,10:32,-1,10:2,-1,46,-1:12,46:1" +
"8,-1:7,13,-1,13:29,14,19,13:4,-1,13:17,32:2,13:12,32:3,13:3,-1:6,6,-1:6,6,-" +
"1:36,6,-1:26,46,-1:12,46:4,22,46:6,11,46:6,-1:7,13:17,-1:2,13:12,-1:3,13:3," +
"-1:7,6,-1:5,6,-1:25,46,-1:12,46:5,11,46:12,-1:24,32:2,-1:12,32:3,-1:11,6,-1" +
":30,46,-1:12,46:4,11,46:13,-1:15,10,-1:29,46,-1:12,46:2,11,46:15,-1:19,6,-1" +
":25,46,-1:12,46:10,11,46:7,-1:7,46,-1:12,46:7,11,46:10,-1:7,46,-1:12,46,40," +
"46,25,46:14,-1:7,32,-1,32:29,12,23,32:4,-1,46,-1:12,46,27,46:16,-1:7,46,-1:" +
"12,46:9,29,46:8,-1:7,46,-1:12,46:3,30,46:14,-1:7,46,-1:12,46:5,42,46:12,-1:" +
"7,46,-1:12,46:13,29,46:4,-1:7,46,-1:12,46:2,37,46:15,-1:7,46,-1:12,46:8,47," +
"46:9,-1:7,46,-1:12,46:9,22,46:8,-1:7,46,-1:12,46:3,43,46:14,-1:7,46,-1:12,4" +
"6:9,44,46:8,-1:7,46,-1:12,46:2,29,46:15,-1:7,46,-1:12,46:8,25,46:9,-1:7,46," +
"-1:12,46:14,22,46:3,-1:7,46,-1:12,46:9,45,46:8,-1:7,46,-1:12,46:8,34,46:9,-" +
"1:7,46,-1:12,46,35,46:16,-1:7,46,-1:12,46:10,36,46:7,-1:7,46,-1:12,46:12,38" +
",46:5,-1:7,46,-1:12,46:5,39,46:12,-1:7,46,-1:12,46:16,41,46,-1:6");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

return new Symbol(sym.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ // NOTE: the following computation of the integer value does NOT
            //       check for overflow.  This must be changed.
            try{
                int val = (new Integer(yytext())).intValue();
                Symbol S = new Symbol(sym.INTLITERAL,
                                 new IntLitTokenVal(yyline+1, CharNum.num, val));
                CharNum.num += yytext().length();
                return S;
            } catch (NumberFormatException nfe){
                ErrMsg.warn(yyline+1, CharNum.num,
                                     "integer literal too large");
                Symbol S = new Symbol(sym.INTLITERAL,
                    new IntLitTokenVal(yyline+1, CharNum.num, Integer.MAX_VALUE));
                CharNum.num += yytext().length();
                return S;
            }
          }
					case -3:
						break;
					case 3:
						{ CharNum.num = 1; }
					case -4:
						break;
					case 4:
						{ CharNum.num += yytext().length(); }
					case -5:
						break;
					case 5:
						{ Symbol S = new Symbol(sym.PLUS, new TokenVal(yyline+1, CharNum.num));
            CharNum.num++;
            return S;
          }
					case -6:
						break;
					case 6:
						{ Symbol S = new Symbol(Map.m_charsym.get(yytext()), 
                new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -7:
						break;
					case 7:
						{ ErrMsg.fatal(yyline+1, CharNum.num,
                         "ignoring illegal character: " + yytext());
            CharNum.num++;
          }
					case -8:
						break;
					case 8:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -9:
						break;
					case 9:
						{  ErrMsg.fatal(yyline+1, CharNum.num, 
                        "unterminated string literal ignored");
                CharNum.num += yytext().length();
             }
					case -10:
						break;
					case 10:
						{ 
          }
					case -11:
						break;
					case 11:
						{ Symbol S = new Symbol(Map.m_reserved.get(yytext()), 
                new TokenVal(yyline+1, CharNum.num)); 
            CharNum.num += yytext().length();
            return S;
          }
					case -12:
						break;
					case 12:
						{ Symbol S = new Symbol(sym.STRINGLITERAL, 
                new StrLitTokenVal(yyline+1, CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -13:
						break;
					case 13:
						{  ErrMsg.fatal(yyline+1, CharNum.num, 
                        "unterminated string literal with bad escaped character ignored");
                CharNum.num += yytext().length();
             }
					case -14:
						break;
					case 14:
						{  ErrMsg.fatal(yyline+1, CharNum.num, 
                        "string literal with bad escaped character ignored");
                CharNum.num += yytext().length();
             }
					case -15:
						break;
					case 16:
						{ Symbol S = new Symbol(Map.m_charsym.get(yytext()), 
                new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -16:
						break;
					case 17:
						{ ErrMsg.fatal(yyline+1, CharNum.num,
                         "ignoring illegal character: " + yytext());
            CharNum.num++;
          }
					case -17:
						break;
					case 18:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -18:
						break;
					case 20:
						{ Symbol S = new Symbol(Map.m_charsym.get(yytext()), 
                new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -19:
						break;
					case 21:
						{ ErrMsg.fatal(yyline+1, CharNum.num,
                         "ignoring illegal character: " + yytext());
            CharNum.num++;
          }
					case -20:
						break;
					case 22:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -21:
						break;
					case 24:
						{ Symbol S = new Symbol(Map.m_charsym.get(yytext()), 
                new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -22:
						break;
					case 25:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -23:
						break;
					case 26:
						{ Symbol S = new Symbol(Map.m_charsym.get(yytext()), 
                new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -24:
						break;
					case 27:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -25:
						break;
					case 28:
						{ Symbol S = new Symbol(Map.m_charsym.get(yytext()), 
                new TokenVal(yyline+1, CharNum.num));
            CharNum.num += yytext().length();
            return S;
          }
					case -26:
						break;
					case 29:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -27:
						break;
					case 30:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -28:
						break;
					case 31:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -29:
						break;
					case 32:
						{  ErrMsg.fatal(yyline+1, CharNum.num, 
                        "unterminated string literal ignored");
                CharNum.num += yytext().length();
             }
					case -30:
						break;
					case 33:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -31:
						break;
					case 34:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -32:
						break;
					case 35:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -33:
						break;
					case 36:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -34:
						break;
					case 37:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -35:
						break;
					case 38:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -36:
						break;
					case 39:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -37:
						break;
					case 40:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -38:
						break;
					case 41:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -39:
						break;
					case 42:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -40:
						break;
					case 43:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -41:
						break;
					case 44:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -42:
						break;
					case 45:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -43:
						break;
					case 46:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -44:
						break;
					case 47:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -45:
						break;
					case 48:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -46:
						break;
					case 49:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -47:
						break;
					case 50:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -48:
						break;
					case 51:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -49:
						break;
					case 52:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -50:
						break;
					case 53:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -51:
						break;
					case 54:
						{ Symbol S = new Symbol(sym.ID, new IdTokenVal(yyline+1, 
                CharNum.num, yytext())); 
            CharNum.num += yytext().length();
            return S;
          }
					case -52:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
