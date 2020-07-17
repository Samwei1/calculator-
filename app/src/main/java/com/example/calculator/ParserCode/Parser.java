package com.example.calculator.ParserCode;
public class Parser {
    //This tokenizer class is originated from the lab tasks.
    //But it is modified in a deep extend so that is serves well for this calculator.
    MyTokenizer _tokenizer;
    
    public Parser(MyTokenizer tokenizer) {
        _tokenizer = tokenizer;
    }

    public Exp parseExp() {
        Exp term = parseTerm();
        if (_tokenizer.hasNext() && _tokenizer.current().token().equals("+")){
            _tokenizer.next();
            Exp exp = parseExp();
            return new AddExp(exp,term);
        }
        if (_tokenizer.hasNext() && _tokenizer.current().token().equals("-")){
            _tokenizer.next();
            Exp exp = parseExp();
            //If there is nothing in front of a "-", the exp will be a 0
            //This will make the SubExp become the negative value of the term.
            return new SubExp(exp,term);
        }
        return term;
    }

    public Exp parseTerm() {
        Exp factor = parseFactor();
        if (_tokenizer.hasNext() && _tokenizer.current().token().equals("×")){
            _tokenizer.next();
            Exp term = parseTerm();
            return new MultExp(term,factor);
        }
        if (_tokenizer.hasNext()&&_tokenizer.current().token().equals("÷")){
            _tokenizer.next();
            Exp term = parseTerm();
            return new DivExp(term,factor);
        }
        return factor;
    }

    public Exp parseFactor() {
        if (_tokenizer.hasNext()&& _tokenizer.current().token().equals(")")){
            //Here we do the parser differently (from right hand side to left hand side), so we are detecting right brackets.
            _tokenizer.next();
            Exp exp = parseExp();
            if (_tokenizer.hasNext()&& _tokenizer.current().token().equals(",")) {
                _tokenizer.next();
                //This is the base of log or pow.
                Exp base = parseExp();
                _tokenizer.next();
                if (_tokenizer.hasNext()&& _tokenizer.current().token().equals("pow"))
                {
                    _tokenizer.next();
                    return new PowExp(base,exp);
                }
                if (_tokenizer.hasNext()&& _tokenizer.current().token().equals("log"))
                {
                    _tokenizer.next();
                    return new LogExp(base,exp);
                }
            }
            _tokenizer.next();
            if (_tokenizer.hasNext()&& _tokenizer.current().token().equals("sin"))
            {
                _tokenizer.next();
                return new SinExp(exp);
            }
            else if (_tokenizer.hasNext()&& _tokenizer.current().token().equals("cos"))
            {
                _tokenizer.next();
                return new CosExp(exp);
            }
            else if (_tokenizer.hasNext()&& _tokenizer.current().token().equals("tan"))
            {
                _tokenizer.next();
                return new TanExp(exp);
            }
            return exp;
        }
        if (_tokenizer.hasNext()&& _tokenizer.current().token().equals("%")){
            //This is the minor reason why we are doing parser backwards.
            //To handle the symbol that only affects the number before it.
            _tokenizer.next();
            Exp p_factor = parseFactor();
            return new PercentExp(p_factor);
        }

        if (_tokenizer.hasNext()&& _tokenizer.current().token().equals("(")){
            //There is only one way to let the current token be "(", that is, there is a symbol right after a "("
            return new DoubleExp(0.0);
        }
        Exp num= new DoubleExp(Double.parseDouble(_tokenizer.current().token()));
        _tokenizer.next();
        return num;
    }
}
