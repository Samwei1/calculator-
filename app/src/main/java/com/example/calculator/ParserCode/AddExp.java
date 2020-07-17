package com.example.calculator.ParserCode;

public class AddExp extends Exp {
	private Exp exp;
	private Exp term;

	public AddExp(Exp exp, Exp term) {
		this.exp = exp;
		this.term = term;
	}

	@Override
	public String show() {
		return "(" + exp.show() + "+" + term.show() + ")";
	}

	@Override
	public double evaluate() {
		return (exp.evaluate() + term.evaluate());
	}
}