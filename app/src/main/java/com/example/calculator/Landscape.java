package com.example.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.calculator.ParserCode.Exp;
import com.example.calculator.ParserCode.MyTokenizer;
import com.example.calculator.ParserCode.Parser;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Landscape extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private static final String TAG = "Landscape";
    public int pos = 1;
    String targeting = "0";
    String rest = "";
    String last_cal="";
    String title ="";
    String content = "";
    Boolean landscape=true;
    private SharedPreferences sp;
    public ArrayList<Function> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscape);
        databaseHelper = new DatabaseHelper(this);
        sp = this.getSharedPreferences("saveData", 0);
        //Create buttons
        Button b0 = findViewById(R.id.l0);
        Button b1 = findViewById(R.id.l1);
        Button b2 = findViewById(R.id.l2);
        Button b3 = findViewById(R.id.l3);
        Button b4 = findViewById(R.id.l4);
        Button b5 = findViewById(R.id.l5);
        Button b6 = findViewById(R.id.l6);
        Button b7 = findViewById(R.id.l7);
        Button b8 = findViewById(R.id.l8);
        Button b9 = findViewById(R.id.l9);
        Button point = findViewById(R.id.ldot);
        Button add = findViewById(R.id.ladd);
        Button div = findViewById(R.id.ldiv);
        Button mul = findViewById(R.id.lmul);
        Button minus = findViewById(R.id.lminus);
        Button equal = findViewById(R.id.lequal);
        Button ac = findViewById(R.id.lac);
        Button per = findViewById(R.id.lpercent);
        Button back = findViewById(R.id.lback);
        Button lb = findViewById(R.id.llb);
        Button rb = findViewById(R.id.lrb);
        Button la = findViewById(R.id.lla);
        Button ra = findViewById(R.id.lra);
        Button pow = findViewById(R.id.lpow);
        Button log = findViewById(R.id.llog);
        Button com = findViewById(R.id.lcomma);
        Button cos = findViewById(R.id.lcos);
        Button sin = findViewById(R.id.lsin);
        Button tan = findViewById(R.id.ltan);
        Button pi = findViewById(R.id.lpi);
        Button e = findViewById(R.id.le);
        Button x = findViewById(R.id.lx);
        Button y = findViewById(R.id.ly);
        Button z = findViewById(R.id.lz);
        Button g = findViewById(R.id.lg);
        Button result = findViewById(R.id.result);

        //Create text box.
        final TextView textView = findViewById(R.id.textView3);
        final TextView ltool = findViewById(R.id.textView6);
        final TextView rtool = findViewById(R.id.textView8);
        final TextView textView5 = findViewById(R.id.textView5);

        //Perform similar onclick actions.
        normal_b(b0,textView,"0");
        normal_b(b1,textView,"1");
        normal_b(b2,textView,"2");
        normal_b(b3,textView,"3");
        normal_b(b4,textView,"4");
        normal_b(b5,textView,"5");
        normal_b(b6,textView,"6");
        normal_b(b7,textView,"7");
        normal_b(b8,textView,"8");
        normal_b(b9,textView,"9");
        normal_b(lb,textView,"(");
        sp_b(point,textView,".");
        sp_b(mul,textView,"×");
        sp_b(div,textView,"÷");
        sp_b(add,textView,"+");
        sp_b(minus,textView,"-");
        sp_b(per,textView,"%");
        normal_b(rb,textView,")");
        normal_b(equal,textView,"=");
        normal_b(x,textView,"x");
        normal_b(y,textView,"y");
        normal_b(z,textView,"z");
        normal_b(g,textView,"g");
        normal_b(pi,textView,"π");
        normal_b(e,textView,"e");
        normal_b(com,textView,",");
        normal_b(sin,textView,"sin(");
        normal_b(cos,textView,"cos(");
        normal_b(tan,textView,"tan(");
        normal_b(pow,textView,"pow(");
        normal_b(log,textView,"log(");

        //set font
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
        add.setTypeface(typeface);
        minus.setTypeface(typeface);
        mul.setTypeface(typeface);
        div.setTypeface(typeface);
        rb.setTypeface(typeface);
        back.setTypeface(typeface);
        ac.setTypeface(typeface);
        equal.setTypeface(typeface);
        x.setTypeface(typeface);
        y.setTypeface(typeface);
        z.setTypeface(typeface);
        g.setTypeface(typeface);
        pi.setTypeface(typeface);
        e.setTypeface(typeface);
        com.setTypeface(typeface);
        tan.setTypeface(typeface);
        cos.setTypeface(typeface);
        sin.setTypeface(typeface);
        pow.setTypeface(typeface);
        log.setTypeface(typeface);
        la.setTypeface(typeface);
        ra.setTypeface(typeface);
        result.setTypeface(typeface);
        final TextView textView2 = findViewById(R.id.textView2);

        //Perform complex or unusual buttons.
        //Move current focusing to the left by one.
        la.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textView.getText().toString();
                int length = text.length();
                int len=targeting.length()-1;
                if (pos!=0)
                {
                    if (targeting.charAt(len)=='('&&len>=2)
                    {
                        char a = targeting.charAt(len-1);
                        char b = targeting.charAt(len-2);
                        if ((a=='g'&&b=='o')||a=='w'||a=='n'||a=='s')
                            pos-=3;
                    }
                    pos--;
                }
                targeting =text.substring(0,pos);
                rest=text.substring(pos,length);
                ltool.setText(targeting);
                rtool.setText(rest);
            }
        });
        //Move current focusing to the right by one.
        ra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textView.getText().toString();
                int length = text.length();
                if (pos!=length)
                {
                    if (rest.length()>3) {
                        if (rest.charAt(3) == '(') {
                            char a = rest.charAt(0);
                            if (a=='s'||a =='p'||a=='c'||a=='t'||a=='l')
                                pos += 3;
                        }
                    }
                    pos++;
                }
                targeting =text.substring(0,pos);
                rest=text.substring(pos,length);
                ltool.setText(targeting);
                rtool.setText(rest);
            }
        });
        //Delete one symbol or number from the current focusing
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length= targeting.length()-1;

                if (length>=0)
                {
                    if (targeting.charAt(length)=='(')
                    {
                        char a = targeting.charAt(length-1);
                        char b = targeting.charAt(length-2);
                        if ((a=='g'&&b=='o')||a=='w'||a=='n'||a=='s')
                        {
                            length-=3;
                            pos-=3;
                        }
                    }
                    targeting=targeting.substring(0, length);
                    if (rest.equals("")&&targeting.equals(""))
                        targeting="0";

                    String result=targeting+rest;
                    textView.setText(result);
                }
                if(pos>0)
                    pos--;
            }
        });
        //Get result of the expression. This is different from "=" in landscape mode. Because we will use "="
        //to perform equations and plotting.
        result.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String temp=textView.getText().toString();
                //check graph condition

                if (temp.equals("Mathematical Error"))
                    textView.setText(targeting+rest);
                else
                {
                    try //If the user made an unexpected (usually it is mathematically wrong) expression and press result
                    {   //We will show "Mathematical Error" on the screen. And if the user press "Res" Again,
                        //They will get the previous expression so that they will have the opportunity to fix it.
                        int len_1=temp.length();
                        for (int i=0; i<len_1;i++)
                        {
                            if (temp.charAt(i)=='f')
                            {
                                int lock=1;
                                int a=i+2;
                                int k=i+2;
                                for (; lock>=1; k++) {
                                    if (temp.charAt(k)=='(')
                                        lock++;
                                    if (temp.charAt(k)==')')
                                        lock--;
                                }
                                String tag = temp.substring(a,k);               //The content between f( and ).
                                String left = temp.substring(0,i);              //The content before f
                                String right = "";                              //The content after f()
                                if(temp.length()>k)
                                    right= temp.substring(k+1);
                                String func=content;
                                String y = "";                                  //Variable initialization. x is downwards.
                                String z = "";
                                String g = "";

                                i+=2;

                                //Subtract and calculate x,y,z,g
                                int lock_1=0;
                                int [] po={0,0,0};
                                int count=0;
                                for (int c=0; c<tag.length();c++)
                                {
                                    if (tag.charAt(c)=='(')
                                        lock_1++;
                                    else if (tag.charAt(c)==')')
                                        lock_1--;

                                    if (lock_1==0)
                                    {
                                        if (tag.charAt(c)==',')
                                        {
                                            po[count]=c;
                                            count++;
                                        }
                                    }
                                }

                                //Calculating x
                                if (count==0)
                                    po[0]=tag.length();
                                String x=tag.substring(0,po[0]);
                                MyTokenizer xm = new MyTokenizer(x);
                                Exp x_1 = new Parser(xm).parseExp();
                                double xr = new BigDecimal(x_1.evaluate()).setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
                                x=String.valueOf(xr);

                                //Calculating y, if there is any
                                if (count>0)
                                {
                                    if (count==1)
                                        po[1]=tag.length();
                                    y=tag.substring(po[0]+1,po[1]);
                                    MyTokenizer ym = new MyTokenizer(y);
                                    Exp y_1 = new Parser(ym).parseExp();
                                    double yr = new BigDecimal(y_1.evaluate()).setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    y=String.valueOf(yr);
                                }

                                //Calculating z, if there is any
                                if (count>1)
                                {
                                    if (count==2)
                                        po[2]=tag.length();
                                    z=tag.substring(po[1]+1,po[2]);
                                    MyTokenizer zm = new MyTokenizer(z);
                                    Exp z_1 = new Parser(zm).parseExp();
                                    double zr = new BigDecimal(z_1.evaluate()).setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    z=String.valueOf(zr);
                                }

                                //Calculating g, if there is any.
                                if (count>2)
                                {
                                    g=tag.substring(po[2]+1);
                                    MyTokenizer gm = new MyTokenizer(g);
                                    Exp g_1 = new Parser(gm).parseExp();
                                    double gr = new BigDecimal(g_1.evaluate()).setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    g=String.valueOf(gr);
                                }

                                //Substitute the result of x,y,z,g into func.
                                int lenn=func.length();
                                for (int p=0; p<lenn; p++)
                                {
                                    if (func.charAt(p)=='x')
                                        func=func.substring(0,p)+"("+x+")"+func.substring(p+1,lenn);
                                    else if (func.charAt(p)=='y')
                                        func=func.substring(0,p)+"("+y+")"+func.substring(p+1,lenn);
                                    else if (func.charAt(p)=='z')
                                        func=func.substring(0,p)+"("+z+")"+func.substring(p+1,lenn);
                                    else if (func.charAt(p)=='g')
                                        func=func.substring(0,p)+"("+g+")"+func.substring(p+1,lenn);

                                    lenn=func.length();
                                }
                                temp=left+func+right;
                            }
                        }

                        //Converting pi and e
                        int len_2=temp.length();
                        for (int i=0; i<len_2;i++)
                        {
                            if(temp.charAt(i)=='π')
                                temp=temp.substring(0,i)+"(3.141592653589)"+temp.substring(i+1);
                            else if (temp.charAt(i)=='e')
                                temp=temp.substring(0,i)+"(2.718281828459)"+temp.substring(i+1);
                            len_2=temp.length();
                        }

                        //Regulating expression, for example, converting "3(5)" to "3x(5)"
                        int len_3=temp.length();
                        for (int i=0; i<len_3; i++)
                        {
                            char cc=temp.charAt(i);
                            if(i!=0&&(cc=='('||cc=='x'||cc=='y'||cc=='z'||cc=='g'||cc=='s'||cc=='c'||cc=='t'||cc=='l'||cc=='p')&&Character.isDigit(temp.charAt(i-1)))
                                temp=temp.substring(0,i)+"×"+temp.substring(i);
                            len_3=temp.length();
                        }
                        if (temp.contains("=")){
                            Intent intent = new Intent(Landscape.this,GraphingInterface.class);
                            intent.putExtra("function",temp);
                            startActivity(intent);
                        }
                        //If no '=' equal sign detected, Calculating the entire expression
                        else
                        {
                            MyTokenizer math = new MyTokenizer(temp);
                            Exp t1 = new Parser(math).parseExp();
                            double fix_res = new BigDecimal(t1.evaluate()).setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
                            if (fix_res>1e15)
                                fix_res=temp.charAt(1000);
                            String s=String.valueOf(fix_res);
                            int sl=s.length()-1;
                            //Cancelling the ".0" in the end if the result is an integer
                            if (s.charAt(sl)=='0'&&s.charAt(sl-1)=='.') {
                                s = s.substring(0, sl - 1);
                            }
                            //Perform modifying expression functionality.
                            if (last_cal.equals(temp)) {
                                if(s.charAt(0)=='-')
                                    s='('+s+')';
                                pos = s.length();
                                textView.setText(s);
                                targeting = s.substring(0, pos);
                                rest = "";
                                textView5.setText("");
                                rtool.setText(rest);
                            }else
                                textView5.setText("Result: "+s);

                            last_cal=temp;
                        }
                    }
                    catch (Exception e)
                    {
                        //Show expression is wrong and give information to the terminal.
                        textView.setText("Mathematical Error");
                        e.printStackTrace();
                    }
                }
            }
        });
        //Clear the expression and the results.
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("0");
                targeting="0";
                rest="";
                pos=1;
                rtool.setText(rest);
                textView5.setText("");
                textView2.setText("");
                textView5.setText("");
            }
        });
    }

    //Non-special button pressed.
    public void normal_b(Button button,final TextView textView, final String s){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(targeting.equals("0")){
                    targeting=s;
                    textView.setText(targeting);
                    pos=s.length();
                }else {
                    targeting=targeting+s;
                    String result=targeting+rest;
                    textView.setText(result);
                    pos+=s.length();
                }
            }
        });
    }

    //Special buttons that would want the initial "0" to be exist after press the button.
    public void sp_b(Button button,final TextView textView, final String s){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targeting=targeting+s;
                String result=targeting+rest;
                textView.setText(result);
                pos+=s.length();
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//landscape
            Toast.makeText(getApplicationContext(), "landscape", Toast.LENGTH_SHORT).show();
            landscape =false;

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){//normal-vision
            Toast.makeText(getApplicationContext(), "portrait", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Landscape.this, MainActivity.class);
            startActivity(intent);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return  true;
    }
    // get event from menu
    public boolean onOptionsItemSelected(MenuItem item){
        TextView textView =findViewById(R.id.textView3);
        TextView textView2 = findViewById(R.id.textView2);
        switch (item.getItemId()) {
            case R.id.new_item:
                Toast.makeText(this, "new", Toast.LENGTH_SHORT).show();
                break;
            case R.id.save_item:
                Function function = new Function(textView2.getText().toString(),textView.getText().toString());
                arrayList.add(function);
                textView2.setText("");
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
                textView.setText("0");
                targeting="0";
                rest="";
                break;
            case R.id.new_one:
                textView2.setText("f(x)=");
                break;
            case R.id.new_two:
                textView2.setText("f(x,y)=");
                break;
            case R.id.new_three:
                textView2.setText("f(x,y,z)=");
                break;
            case R.id.new_four:
                textView2.setText("f(x,y,z,g)=");
                break;
            case R.id.load_item:
                for (Function f:arrayList){
                    addData(f.title+f.content);
                }
                Intent intent = new Intent(Landscape.this,LoadActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }
    public void addData(String newEntry){
        boolean insertData = databaseHelper.addData(newEntry);
        if (insertData){
            toastMessage("Data Successfully Inserted");
        }else {
            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        String l = targeting;
        String r = rest;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("data_1", l);
        editor.putString("data_2", r);
        editor.commit();
    }
    @Override
    protected void onResume() {
        TextView textView = findViewById(R.id.textView3);
        TextView textView5 = findViewById(R.id.textView5);
        super.onResume();
        Intent receiveIntent = getIntent();
        title = receiveIntent.getStringExtra("title");
        content = receiveIntent.getStringExtra("content");
        targeting=sp.getString("data_1", "0");
        rest=sp.getString("data_2","");
        String s=targeting+rest;

        if (s.equals("")){
            textView.setText("0");
        }else {
            if (content == null) {
                textView.setText(s);
            } else {
                if (targeting.equals("0")){
                    textView.setText("f("+rest);
                    targeting="f(";
                }else {
                    textView.setText(targeting + "f("+rest);
                    targeting=targeting+"f(";
                }
                textView5.setText(title+content);
            }
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
    @Override
    public void onBackPressed() {
        if (landscape){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }else {
            Intent intent = new Intent(Landscape.this, MainActivity.class);
            startActivity(intent);
        }
    }
}



