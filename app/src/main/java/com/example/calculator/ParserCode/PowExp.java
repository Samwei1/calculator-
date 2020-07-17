package com.example.calculator.ParserCode;

public class PowExp extends Exp {
    private Exp base;
    private Exp exp;

    public PowExp(Exp exp_1, Exp exp) {
        this.base = exp_1;
        this.exp = exp;
    }

    @Override
    public String show() {
        return "(" + "pow(" + base.show() + "," + exp.show() + "))";
    }

    @Override
    public double evaluate() {
        return (Math.pow(base.evaluate(),exp.evaluate()));
    }
}