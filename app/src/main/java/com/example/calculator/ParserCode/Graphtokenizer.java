package com.example.calculator.ParserCode;

public class Graphtokenizer extends Tokenizer {
        private String _buffer;
        private Token currentToken;

        public Graphtokenizer(String text) {
            _buffer = text;
            next();
        }

        public void next() {
            _buffer = _buffer.trim();
            int len = _buffer.length()-1;
            if(_buffer.isEmpty()) {
                currentToken = null;
                return;
            }

            char firstChar = _buffer.charAt(0);
            if(firstChar=='+') {
                currentToken = new Token("+", Token.Type.ADD);
            }
            if(firstChar=='-') {
                currentToken = new Token("-", Token.Type.SUB);
            }
            if(firstChar=='×') {
                currentToken = new Token("×", Token.Type.MUL);
            }
            if (firstChar=='÷') {
                currentToken = new Token("÷", Token.Type.DIV);
            }
            if (firstChar=='(') {
                currentToken = new Token("(", Token.Type.LBRA);
            }
            if (firstChar==')') {
                currentToken = new Token(")", Token.Type.RBRA);
            }
            if (firstChar=='%'){
                currentToken = new Token("%", Token.Type.PER);
            }
            if (firstChar=='^'){
                currentToken = new Token("^",Token.Type.POW);
            }
            if (firstChar=='x'){
                currentToken = new Token("x",Token.Type.X);
            }
            if (Character.isDigit(firstChar)) {
                String s="";
                int index=0;
                while (Character.isDigit(_buffer.charAt(index))||_buffer.charAt(len-index)=='.'){
                    s=_buffer.charAt(index)+s;
                    index++;
                    if (index==len+1){
                        break;
                    }
                }
                currentToken = new Token(s,Token.Type.DOB);
            }


            // Remove the extracted token from buffer
            int tokenLen = currentToken.token().length();
            _buffer = _buffer.substring(tokenLen);
        }

        public Token current() {
            return currentToken;
        }

        public boolean hasNext() {
            return currentToken != null;
        }

    }
