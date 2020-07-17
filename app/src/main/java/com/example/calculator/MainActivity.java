package com.example.calculator;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.calculator.ParserCode.Exp;
import com.example.calculator.ParserCode.MyTokenizer;
import com.example.calculator.ParserCode.Parser;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //establishing buttons.
        Button b0 = findViewById(R.id.c0);
        Button b1 = findViewById(R.id.c1);
        Button b2 = findViewById(R.id.c2);
        Button b3 = findViewById(R.id.c3);
        Button b4 = findViewById(R.id.c4);
        Button b5 = findViewById(R.id.c5);
        Button b6 = findViewById(R.id.c6);
        Button b7 = findViewById(R.id.c7);
        Button b8 = findViewById(R.id.c8);
        Button b9 = findViewById(R.id.c9);
        Button point = findViewById(R.id.point);
        Button add = findViewById(R.id.add);
        Button div = findViewById(R.id.div);
        Button mul = findViewById(R.id.mul);
        Button minus = findViewById(R.id.minus);
        Button equal = findViewById(R.id.equal);
        Button ac = findViewById(R.id.ac);
        Button per = findViewById(R.id.per);
        Button back = findViewById(R.id.back);
        Button lb = findViewById(R.id.lb);
        Button rb = findViewById(R.id.rb);
        final TextView textView = findViewById(R.id.textView);
        setNumber(b0,textView,"0");
        setNumber(b1,textView,"1");
        setNumber(b2,textView,"2");
        setNumber(b3,textView,"3");
        setNumber(b4,textView,"4");
        setNumber(b5,textView,"5");
        setNumber(b6,textView,"6");
        setNumber(b7,textView,"7");
        setNumber(b8,textView,"8");
        setNumber(b9,textView,"9");
        setNumber(lb,textView,"(");
        setSymbol(point,textView,".");
        setSymbol(mul,textView,"×");
        setSymbol(div,textView,"÷");
        setSymbol(add,textView,"+");
        setSymbol(per,textView,"%");
        setSymbol(rb,textView,")");
        setSymbol(minus,textView,"-");

        //Setting fonts.
        Typeface typeface = ResourcesCompat.getFont(this, R.font.dxl);
        textView.setTypeface(typeface);
        b0.setTypeface(typeface);
        b1.setTypeface(typeface);
        b2.setTypeface(typeface);
        b3.setTypeface(typeface);
        b4.setTypeface(typeface);
        b5.setTypeface(typeface);
        b6.setTypeface(typeface);
        b7.setTypeface(typeface);
        b8.setTypeface(typeface);
        b9.setTypeface(typeface);
        lb.setTypeface(typeface);
        point.setTypeface(typeface);
        mul.setTypeface(typeface);
        div.setTypeface(typeface);
        add.setTypeface(typeface);
        rb.setTypeface(typeface);
        minus.setTypeface(typeface);
        back.setTypeface(typeface);
        ac.setTypeface(typeface);
        equal.setTypeface(typeface);

        //delete one character from the expression
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textView.getText().toString();
                int length = text.length();
                if (length>1) {
                    textView.setText(text.substring(0, length-1));
                }else{
                    textView.setText("0");
                }
            }
        });

        //get the result of current expression
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String text = textView.getText().toString();
                    MyTokenizer math = new MyTokenizer(text);
                    Exp t1 = new Parser(math).parseExp();
                    String s=String.valueOf(t1.evaluate());
                    int sl=s.length()-1;
                    if (s.charAt(sl)=='0'&&s.charAt(sl-1)=='.'){
                        s=s.substring(0,sl-1);
                    }
                    textView.setText(s);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    textView.setText("Mathematical Error");
                }
            }
        });

        //clear the expression
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("0");
            }
        });
    }

    //add number to expression
    public void setNumber(Button button,final TextView textView, final String string){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textView.getText().toString();
                if(text.equals("0")||text.equals("Mathematical Error"))
                    textView.setText(string);
                else
                    textView.append(string);
            }
        });
    }

    //add symbol to expression.
    public void setSymbol(Button button, final TextView textView, final  String string){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.append(string);
            }
        });
    }

    //performing screen transition.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//landscape
            Toast.makeText(getApplicationContext(), "landscape", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Landscape.class);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
            Intent intent = new Intent(MainActivity.this,WelcomeInterface.class);
            startActivity(intent);

    }

}
