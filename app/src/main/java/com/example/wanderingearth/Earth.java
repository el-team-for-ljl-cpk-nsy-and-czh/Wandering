package com.example.wanderingearth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class Earth extends View{
    public Earth(Context context){
        super(context);
    }
    public Earth(Context context,AttributeSet attrs){
        super(context,attrs);
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
        mypaint.setColor(Color.WHITE);
        mypaint.setStyle(Paint.Style.STROKE);
        mypaint.setStrokeWidth(8);
        path.moveTo(this.pathXDots[0],this.pathYDots[0]);
        int j=0;
        for(int i=1; i< this.pathXDots.length;i++){
            if(i%16!=0&&j%2==0) {
                path.lineTo(this.pathXDots[i], this.pathYDots[i]);
            }
            else if(i%16==0&&j%2==0){
                path.lineTo(this.pathXDots[i], this.pathYDots[i]);
                j=j+1;
            }
            else if(((i%16)==0)&&(j%2!=0)){
                path.moveTo(this.pathXDots[i],this.pathYDots[i]);
                j=j+1;
            }
        }
        canvas.drawPath(path,mypaint);
    }

}
