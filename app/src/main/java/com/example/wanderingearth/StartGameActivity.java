package com.example.wanderingearth;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class StartGameActivity extends AppCompatActivity {
    int WINDOWWIDTH,WINDOWHEIGHT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        setContentView(R.layout.startgame);
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
