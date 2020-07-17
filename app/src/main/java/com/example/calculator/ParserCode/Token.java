package com.example.calculator.ParserCode;
public class Token {
    public enum Type {UNKNOWN, DOB, ADD, SUB, MUL, DIV, LBRA, RBRA, PER,POW,LOG,SIN,COS,TAN,COM,X};
    private String _token = "";
    private Type _type = Type.UNKNOWN;
    
    public Token(String token, Type type) {
        _token = token;
        _type = type;
    }
    
    public String token() {
        return _token;
    }
    
    public Type type() {
        return _type;
    }
}
