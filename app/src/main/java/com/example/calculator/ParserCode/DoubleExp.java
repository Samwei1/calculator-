package com.example.calculator.ParserCode;

public class DoubleExp extends Exp {
	private Double value;
	public DoubleExp(Double value) {
		this.value = value;
	}

	@Override
	public String show() {
		return value.toString();
	}

	@Override
	public double evaluate() {
		return value;
	}
}
