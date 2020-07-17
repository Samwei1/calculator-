package com.example.calculator.ParserCode;

public class TanExp extends Exp {
    private Exp exp;

    public TanExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String show() {
        return "(" + "tan(" + exp.show() + "))";
    }

    @Override
    public double evaluate() {
        return (Math.tan(exp.evaluate()*Math.PI/180));
    }
}