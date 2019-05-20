package com.example.wanderingearth;

import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Canvas;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class StartGameActivity extends AppCompatActivity {
    //此处声明earth则在后续的所有方法中都可以使用earth；
    int WINDOWWIDTH,WINDOWHEIGHT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        setContentView(R.layout.startgame);
        /*
        以下是初始化earth轨迹的代码，仅供测试使用；
         */
        Earth earth = new Earth(this);
        float[] XDots = new float[250];
        float[] YDots = new float[250];
        for(int i = 0;i< 250;i++){
            XDots[i] = i;
            YDots[i] = i*i + 2*i + 2;
        }
        earth.setXDots(XDots);
        earth.setYDots(YDots);
        //
        LinearLayout layout = findViewById(R.id.LayoutInStartGame);
        layout.addView(earth);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        WINDOWWIDTH = dm.widthPixels;//横屏宽度
        WINDOWHEIGHT = dm.heightPixels;//横屏高度


        /*
        以下是返回按钮的方法，目前还缺少保存数据的代码
         */
        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGameActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /*
        以下是重新开始游戏按钮方法，将路径设置为初始位置，将地球的位置放回原点
         */
        findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGameActivity.this,StartGameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void propertyMove(View v) {
        AlertDialog.Builder boom=new AlertDialog.Builder(this);
        boom.setMessage("BOOM!");
        ImageView jupiter=findViewById(R.id.jupiter);
        final int jupiter_radius=jupiter.getHeight()/2;

        final ImageView earth = findViewById(R.id.earth);
        final int left = earth.getLeft();
        final int top = earth.getTop();
        final int earth_radius=earth.getHeight()/2;


        final ValueAnimator animator = ValueAnimator.ofInt(0, 100);//横屏宽度

        animator.setDuration(2000);

        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
        int xEarth=earth.getLeft()+earth_radius,xJupiter=jupiter.getLeft()+jupiter_radius,yEarth=earth.getTop()+earth_radius,yJupiter=jupiter.getTop()+jupiter_radius;
        double distence_e_j=Math.sqrt((xEarth-xJupiter)*(xEarth-xJupiter)+(yEarth-yJupiter)*(yEarth-yJupiter));
        int current = (int) animator.getAnimatedValue();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) earth.getLayoutParams();
        if(distence_e_j<jupiter_radius+earth_radius){
            animator.cancel();
            boom.show();
        }
        else {
            layoutParams.leftMargin = left + current;

            layoutParams.topMargin =(int) top - (current/10);

            earth.setLayoutParams(layoutParams);
        }

    });

        animator.start();
    }
}
