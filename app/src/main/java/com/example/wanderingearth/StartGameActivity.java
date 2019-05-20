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
        int[] oriPosition = new int[]{findViewById(R.id.earth).getLeft(),findViewById(R.id.earth).getTop(),findViewById(R.id.earth).getRight(),findViewById(R.id.earth).getBottom()};//得到初始位置，用于重新开始游戏
        super.onCreate(savedInstanceState);
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
        //不会用
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        WINDOWWIDTH = (int) (width / density);  // 屏幕高度(dp)横屏
        WINDOWHEIGHT = (int) (height / density);// 屏幕宽度(dp)横屏
        //不会用


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
                earth.setXDots(XDots);
                earth.setYDots(YDots);
                earth.invalidate();
                ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(findViewById(R.id.earth).getLayoutParams());
                margin.setMargins(oriPosition[0],oriPosition[1],oriPosition[2],oriPosition[3]);
                RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(margin);
                findViewById(R.id.earth).setLayoutParams(relativeLayout);
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

        animator.setDuration(1000);

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

            layoutParams.topMargin = top;

            earth.setLayoutParams(layoutParams);
        }

    });

        animator.start();
    }
}
