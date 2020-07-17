package com.example.calculator;
/***************************************************************************************
 *    Reference:
 *    Title: GraphView
 *    Author: jjoe64
 *    Date: 2019
 *    Code version:2.0
 *    Availability: https://github.com/jjoe64/GraphView
 *
 ***************************************************************************************/
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.ParserCode.Exp;
import com.example.calculator.ParserCode.MyTokenizer;
import com.example.calculator.ParserCode.Parser;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.BigDecimal;
import java.util.ArrayList;

public class GraphingInterface extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;
    GraphView graphView;
    ArrayList<DataPoint> points = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //there is library used. to perform zoom in & out.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphing_interface);
        graphView = findViewById(R.id.graphing);
        series = new LineGraphSeries<DataPoint>();
        graphView.getGridLabelRenderer().setVerticalAxisTitle("x");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("f(x)");
        parserString();
        for (DataPoint data: points){
            series.appendData(data,true,100000);
        }

        graphView.addSeries(series);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(15);
        graphView.getViewport().setMinY(-15);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(15);
        graphView.getViewport().setMinX(-15);
    }
    private void parserString(){
        Intent receiveIntent = getIntent();
        String f = receiveIntent.getStringExtra("function");
        double x,y;
        x=-2500;            //x starts with -2500
        int index = f.indexOf("=");
        f=f.substring(index+1);
        //Basically we are calculating the graph between x=-2500 to x=2500, with the point density of 0.1
        //More accurate calculation can be perform, but it will be obviously slower.
        for (;x<2500;x+=0.1) {
            String k=f;
            x= x+0.1;
            k=k.replaceAll("x","("+x+")");
            MyTokenizer math = new MyTokenizer(k);
            Exp t1 = new Parser(math).parseExp();
            y = new BigDecimal(t1.evaluate()).setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
            points.add(new DataPoint(x,y));
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
