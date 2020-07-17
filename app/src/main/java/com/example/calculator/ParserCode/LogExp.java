package com.example.calculator.ParserCode;

public class LogExp extends Exp {
    private Exp base;
    private Exp exp;

    public LogExp(Exp base, Exp exp) {
        this.base = base;
        this.exp = exp;
    }

    @Override
    public String show() {
        return "(" + "log(" + base.show() + "," + exp.show() + "))";
    }

    @Override
    public double evaluate() {
        return (Math.log(exp.evaluate())/Math.log(base.evaluate()));
    }
}