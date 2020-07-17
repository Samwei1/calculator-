package com.example.calculator.ParserCode;

public class CosExp extends Exp {
    private Exp exp;

    public CosExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String show() {
        return "(" + "cos(" + exp.show() + "))";
    }

    @Override
    public double evaluate() {
        return (Math.cos(exp.evaluate()*Math.PI/180));
    }
}