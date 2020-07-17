package com.example.calculator.ParserCode;
public abstract class Tokenizer {
	public abstract boolean hasNext();
	public abstract Token current();
	public abstract void next();
}
