package com.example.calculator.ParserCode;

public class SinExp extends Exp {
    private Exp exp;

    public SinExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String show() {
        return "(" + "sin(" + exp.show() + "))";
    }

    @Override
    public double evaluate() {
        return (Math.sin(exp.evaluate()*Math.PI/180));
    }
}