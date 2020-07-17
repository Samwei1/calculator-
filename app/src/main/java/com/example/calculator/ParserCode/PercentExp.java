package com.example.calculator.ParserCode;

public class PercentExp extends Exp {
        private Exp factor;

        public PercentExp(Exp factor) {
            this.factor = factor;

        }
    @Override
    public String show() {
        return "(" + factor.show() + "%" +")";
    }

    @Override
    public double evaluate() {
        Double res = factor.evaluate();
        String result = res.toString();
        int len = result.length()-1;

        int i = 1;
        for (; i<len; i++)
            if (result.charAt(i)=='.')
                break;
        if (i==1)
            result="0.0"+result.charAt(0)+result.substring(2);
        else if (i==2)
            result="0."+result.substring(0,2)+result.substring(3);
        else {
            if (i==len) {
                result=result.substring(0,len-1)+"."+result.substring(len-1);
            }
            else {
                result=result.substring(0,i-2)+"."+result.substring(i-2,i)+result.substring(i+1);
            }
        }
        return Double.parseDouble(result);
    }
}
