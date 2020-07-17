package com.example.calculator.ParserCode;

public class DivExp extends Exp {
	private Exp term;
	private Exp factor;

	public DivExp(Exp term, Exp factor) {
		this.term = term;
		this.factor = factor;
	}

	@Override
	public String show() {
		return "(" + term.show() + "÷" + factor.show() + ")";
	}

	@Override
	public double evaluate() {
		return (term.evaluate() / factor.evaluate());
	}
}