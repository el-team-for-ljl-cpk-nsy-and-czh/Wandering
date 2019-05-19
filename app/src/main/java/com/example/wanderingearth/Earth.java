package com.example.wanderingearth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class Earth extends View{
    public Earth(Context context){
        super(context);
    }

    private float[] pathXDots;//一系列的点用来绘制路径；
    private float[] pathYDots;
    //以下是一系列的getter和setter;
    public void setXDots(float[] XDots){
        this.pathXDots = XDots;
    }
    public void setYDots(float[] YDots){
        this.pathYDots = YDots;
    }
    public float[] getPathXDots(){
        return this.pathXDots;
    }
    public float[] getPathYDots(){
        return this.pathYDots;
    }
    //改变路径的代码；
    public float[] changeXDots(){return null;};
    public float[] changeYDots(){return null;}
    //绘制路径的代码
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        Paint mypaint = new Paint();
        Path path = new Path();
        mypaint.setColor(Color.BLACK);
        mypaint.setStyle(Paint.Style.STROKE);
        mypaint.setStrokeWidth(5);
        path.moveTo(this.pathXDots[0],this.pathYDots[0]);
        for(int i=1; i< this.pathXDots.length;i++){
            path.lineTo(this.pathXDots[i],this.pathYDots[i]);
        }
        canvas.drawPath(path,mypaint);
    }

}
