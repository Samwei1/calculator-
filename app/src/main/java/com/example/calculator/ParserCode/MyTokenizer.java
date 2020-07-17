package com.example.calculator.ParserCode;

public class MyTokenizer extends Tokenizer {
    private String _buffer;
    private Token currentToken;


    public MyTokenizer(String text) {
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
         
        char firstChar = _buffer.charAt(len);
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
        if (firstChar=='w'){
            currentToken = new Token("pow",Token.Type.POW);
        }
        if (firstChar=='g'){
            currentToken = new Token("log",Token.Type.LOG);
        }
        if (firstChar=='n'){
            if (_buffer.charAt(len-1)=='i')
                currentToken = new Token("sin",Token.Type.SIN);
            else
                currentToken = new Token("tan",Token.Type.TAN);
        }
        if (firstChar=='s'){
            currentToken = new Token("cos",Token.Type.COS);
        }
        if (firstChar==','){
            currentToken = new Token(",",Token.Type.COM);
        }
        if (firstChar=='x') {
            currentToken = new Token("x",Token.Type.X);
        }

        //If more symbols are introduced, they can be easily added here.
        if (Character.isDigit(firstChar)) {
            String s="";
            int index=0;
            while (Character.isDigit(_buffer.charAt(len-index))||_buffer.charAt(len-index)=='.'){
                s=_buffer.charAt(len-index)+s;
                index++;
                if (index==len+1){
                    break;
                }
            }
            currentToken = new Token(s,Token.Type.DOB);
        }


        // Remove the extracted token from buffer
        int tokenLen = currentToken.token().length()-1;
        _buffer = _buffer.substring(0,len-tokenLen);
    }

    public Token current() {
    	return currentToken;
    }

    public boolean hasNext() {
    	return currentToken != null;
    }
}