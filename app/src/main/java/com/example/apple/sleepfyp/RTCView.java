package com.example.apple.sleepfyp;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RTCView extends View {

    //图形当前的坐标
    private Context mContext;
    private int bWidth,bHeight;//left length-margin
    private int totalValue,pJValue;//total value of y, y axis
    private String xStr="date", yStr;//x,y
    private HashMap<Double,Double>map=new HashMap<>();
    private boolean isShow;
    private int marginB;
    private List<Double> dlk=new ArrayList<>();
    private List<Integer> xList=new ArrayList<>();//view x  axis
    private Point [] mpoints;


    public RTCView(Context context,AttributeSet attrs){

        super(context,attrs);
        mContext= context;

    }
    @Override
    protected void onMeasure (int widthMeasureSpec,int heigthMeasureSpec){
        super.onMeasure(widthMeasureSpec,heigthMeasureSpec);
    }

    public  void setView(HashMap<Double,Double>map, int totalValue,int pJValue, boolean isShow,int marginB,  String yStr){

        this.map=map;
        this.totalValue=totalValue;
        this.pJValue=pJValue;
        this.marginB=marginB;
        this.yStr=yStr;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        //实例化画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画笔颜色
        paint.setColor(Color.RED);
        super.onDraw(canvas);
        int height=getHeight();
        int width=getWidth();
        bWidth=50;
        bHeight=height-marginB;
        int jSize=totalValue/pJValue; //numbers of y axis
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        //1.draw the y axis(within in a week)
        for(int i=0;i<jSize+1;i++){

            canvas.drawLine(bWidth-0,bHeight-(bHeight/jSize)*i+20,width-0,bHeight+0,paint);//startX=bwidth;endX=width;//startY=bheight-bheight/jsize*i;endY=BHEITGH
            //Y 刻度
            drawText(pJValue*i+yStr,bWidth/2,bHeight-(bHeight/jSize)*i+20,canvas);



        }
        //x axis

        for(int i=0;i<dlk.size();i++){
            xList.add(bWidth+((int)((width-bWidth)/dlk.size()*dlk.get(i))));
            if(isShow) {
                canvas.drawLine(bWidth + (width - bWidth) / dlk.size() * i, 20, bWidth + (width - bWidth) / dlk.size() * i, bHeight + 0, paint);
            }
            drawText(i+xStr,bWidth + (width - bWidth) / dlk.size() * i+20,bHeight-20,canvas);
        }

        //get all the points together
        mpoints=getPoints(dlk,map,totalValue,canvas,xList);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        drawPoints(mpoints,canvas,paint);

        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        for(int i=0;i<mpoints.length;i++){

            canvas.drawRect(paintRect(mpoints[i]),paint);
        }


    }

    private RectF paintRect(Point point) {

        return new RectF(point.x-3,point.y-3,point.x+3,point.y+3);
    }

    private void drawPoints(Point[] mpoints, Canvas canvas, Paint paint) {
        Point point=new Point();
        Point point1=new Point();
        for(int i=0;i<mpoints.length-1;i++){
            point=mpoints[i];
            point1=mpoints[i+1];
            canvas.drawLine(point.x,point.y,point1.x,point1.y,paint);
        }
    }

    private Point[] getPoints(List<Double> dlk, HashMap<Double, Double> map, int totalValue, Canvas canvas, List<Integer> xList) {
        Point[] points=new Point[dlk.size()];
        for (int i=0;i<points.length;i++){
            int ph=bHeight-(int)(bHeight*(map.get(dlk.get(i))/totalValue));//yaxis
            points[i]=new Point(xList.get(i),ph+20);
        }

        return points;
    }

    //yx刻度
    private void drawText (String string, int i, int j, Canvas canvas){
        Paint paint =new Paint();
        paint.setAlpha(0x0000ff);
        paint.setTextSize(20);
        paint.setColor(Color.GRAY);
        Typeface typeface=Typeface.create("Times New Roman",Typeface.ITALIC);
        paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(string,i,j,paint);

    }
}