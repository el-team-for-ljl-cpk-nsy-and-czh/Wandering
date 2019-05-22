package com.example.wanderingearth;

import android.app.ActivityOptions;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Canvas;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static java.lang.Math.floor;
import static java.lang.Math.sin;

public class StartGameActivity extends AppCompatActivity {
    //此处声明earth则在后续的所有方法中都可以使用earth；
    int WINDOWWIDTH,WINDOWHEIGHT;
    final double PI=3.1415926;
    Barrier jupiter=new Barrier();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        setContentView(R.layout.startgame);
        getWindow().setEnterTransition(new Fade().setDuration(500));
        getWindow().setReenterTransition(new Fade().setDuration(300)
                .excludeChildren(R.drawable.gamebackgound,true)
                .excludeChildren(R.drawable.cute_jupiter,true)
                .excludeChildren(R.id.goback,true)
                .excludeChildren(R.id.restart,true)
                .excludeChildren(R.id.start,true));
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        WINDOWWIDTH = dm.widthPixels;//横屏宽度
        WINDOWHEIGHT = dm.heightPixels;//横屏高度
        AnimationSet animation=new AnimationSet(true);
        RotateAnimation rotateAnimation;
        rotateAnimation = new RotateAnimation(0,7200, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(80000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        //让旋转动画一直转，不停顿的重点
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(-1);
        AlphaAnimation alphaAnimation;
        alphaAnimation=new AlphaAnimation(0.7f,0.95f);
        alphaAnimation.setDuration(800);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(-1);
        animation.addAnimation(rotateAnimation);
        animation.addAnimation(alphaAnimation);
        ImageView door=findViewById(R.id.door);
        door.startAnimation(animation);



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
                getWindow().setExitTransition(new Fade().setDuration(300)
                        .excludeChildren(R.drawable.gamebackgound,true)
                        .excludeChildren(R.drawable.cute_jupiter,true)
                        .excludeChildren(R.id.goback,true)
                        .excludeChildren(R.id.restart,true)
                        .excludeChildren(R.id.start,true));
                Intent intent = new Intent(StartGameActivity.this,StartGameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override

    public void onWindowFocusChanged(boolean hasFocus){

        super.onWindowFocusChanged(hasFocus);

        //此处可以正常获取width、height等
        jupiter.setMass(318);
        TextView mass_text=findViewById(R.id.Mass_text);
        mass_text.setText(String.valueOf(jupiter.getMass()));
        ImageView earth_iView=findViewById(R.id.earth);
        ImageView door_iView=findViewById(R.id.door);
        LinearLayout layout = findViewById(R.id.LayoutInStartGame);
        int left=earth_iView.getLeft()+earth_iView.getWidth()/2;
        int top=earth_iView.getTop()+earth_iView.getWidth()/2;
        Earth earth = new Earth(this);
        float[] XDots = new float[WINDOWWIDTH-left];
        float[] YDots = new float[WINDOWWIDTH-left];
        for(int i = 0;i< WINDOWWIDTH-left;i++){
            XDots[i] = i+left;
            YDots[i] =(float) (-(jupiter.getMass()*sin((PI/(WINDOWWIDTH-left)*i))))+top;
        }
        earth.setXDots(XDots);
        earth.setYDots(YDots);
        //
        layout.addView(earth);

    }
    public void propertyMove(View v) {
        AlertDialog.Builder boom=new AlertDialog.Builder(this);
        boom.setMessage("BOOM!");
        ImageView jupiter_iView=findViewById(R.id.jupiter);
        final int jupiter_radius=jupiter_iView.getHeight()/2;

        final ImageView earth = findViewById(R.id.earth);
        final int left = earth.getLeft();
        final int top = earth.getTop();
        final int earth_radius=earth.getHeight()/2;


        final ValueAnimator animator = ValueAnimator.ofInt(0, WINDOWWIDTH-earth_radius*2-left);//横屏宽度

        animator.setDuration(2000);

        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
        int xEarth=earth.getLeft()+earth_radius,xJupiter=jupiter_iView.getLeft()+jupiter_radius,yEarth=earth.getTop()+earth_radius,yJupiter=jupiter_iView.getTop()+jupiter_radius;
        double distence_e_j=Math.sqrt((xEarth-xJupiter)*(xEarth-xJupiter)+(yEarth-yJupiter)*(yEarth-yJupiter));
        int current = (int) animator.getAnimatedValue();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) earth.getLayoutParams();
        if(distence_e_j<jupiter_radius+earth_radius){
            animator.cancel();
            boom.show();
        }
        else {
            layoutParams.leftMargin = left + current;

            layoutParams.topMargin =(int) (top - ((jupiter.getMass()*sin((PI/(WINDOWWIDTH-left)*current)))));

            earth.setLayoutParams(layoutParams);
        }

    });

        animator.start();
    }
    public void plus(View v){
        jupiter.setMass(jupiter.getMass()+1);
        TextView mass=findViewById(R.id.Mass_text);
        mass.setText(String.valueOf(jupiter.getMass()));
        LinearLayout layout = findViewById(R.id.LayoutInStartGame);
        layout.removeAllViews();
        ImageView earth_iView=findViewById(R.id.earth);
        int left=earth_iView.getLeft()+earth_iView.getWidth()/2;
        int top=earth_iView.getTop()+earth_iView.getWidth()/2;
        /*
        以下是初始化earth轨迹的代码，仅供测试使用；
         */
        Earth earth = new Earth(this);
        float[] XDots = new float[WINDOWWIDTH-left];
        float[] YDots = new float[WINDOWWIDTH-left];
        for(int i = 0;i< WINDOWWIDTH-left;i++){
            XDots[i] = i+left;
            YDots[i] =(float) (-(jupiter.getMass()*sin((PI/(WINDOWWIDTH-left)*i))))+top;
        }
        earth.setXDots(XDots);
        earth.setYDots(YDots);
        //
        layout.addView(earth);
    }
    public void minus(View v){
        jupiter.setMass(jupiter.getMass()-1);
        TextView mass=findViewById(R.id.Mass_text);
        mass.setText(String.valueOf(jupiter.getMass()));
        LinearLayout layout = findViewById(R.id.LayoutInStartGame);
        layout.removeAllViews();
        ImageView earth_iView=findViewById(R.id.earth);
        int left=earth_iView.getLeft()+earth_iView.getWidth()/2;
        int top=earth_iView.getTop()+earth_iView.getWidth()/2;
        /*
        以下是初始化earth轨迹的代码，仅供测试使用；
         */
        Earth earth = new Earth(this);
        float[] XDots = new float[WINDOWWIDTH-left];
        float[] YDots = new float[WINDOWWIDTH-left];
        for(int i = 0;i< WINDOWWIDTH-left;i++){
            XDots[i] = i+left;
            YDots[i] =(float) (-(jupiter.getMass()*sin((PI/(WINDOWWIDTH-left)*i))))+top;
        }
        earth.setXDots(XDots);
        earth.setYDots(YDots);
        //
        layout.addView(earth);
    }
}
