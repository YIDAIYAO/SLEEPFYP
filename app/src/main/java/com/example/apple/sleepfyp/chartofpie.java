package com.example.apple.sleepfyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class chartofpie extends AppCompatActivity {


    float[] yData1={23.3f,32.3f,26.4f,34.1f,123.1f,44.34f,34.2f};
    String[] xData1={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    float[] yData2={23.3f,32.3f,26.4f,34.1f};
    String[] xData2={"Week1","Week2","Week3","Week4"};
    float[] yData3={23.3f,32.3f,26.4f,34.1f,123.1f,23.3f,32.3f,26.4f,34.1f,123.1f,23.3f,32.3f};
    String[] xData3={"January","February","March","April","May","June","July","August","September","October","November","December"};
    float[] yData4={23.3f,32.3f,26.4f,34.1f};
    String[] xData4={"2018","2019","2020","2021"};
    //private String[] xData2={"January","February","March","April","May","June","July","August","September","October","November","December"};
    //private int xData3=2018;
    Button Return;
    final float[] AA = new float[15];
    final String[] BB = new String[15];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chartofpie);
        Return=findViewById(R.id.Return);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chartofpie.this,SleepQualityAnalysis.class));
            }
        });
        Intent intent=getIntent();
        String om=intent.getStringExtra("om");
        final String CFF=om;
        switch (CFF){
            case "day":
                for(int i=0;i<yData1.length;i++){
                    AA[i]=yData1[i];
                    BB[i]=xData1[i];
                }

                break;
            case "week":
                for(int i=0;i<yData2.length;i++){
                    AA[i]=yData2[i];
                    BB[i]=xData2[i];
                }
                break;
            case "month":
                for(int i=0;i<yData3.length;i++){
                    AA[i]=yData3[i];
                    BB[i]=xData3[i];
                }
                break;
            case "year":
                for(int i=0;i<yData4.length;i++){
                    AA[i]=yData4[i];
                    BB[i]=xData4[i];
                }
                break;
        }
        setupPieChart();

    }

    private void setupPieChart() {
        List<PieEntry>pieEntries=new ArrayList<>();

        for(int i=0;i<AA.length;i++){//add all obtained values into pie chart
            pieEntries.add(new PieEntry(AA[i],BB[i]));
        }
        PieDataSet dataSet=new PieDataSet(pieEntries,"Sleep Quality");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12);

        PieData data=new PieData(dataSet);
        //Get the chart
        PieChart chart=(PieChart)findViewById(R.id.pieChart);
        chart.animateY(2000);
        chart.setData(data);
        chart.invalidate();

    }
}
