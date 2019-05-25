package com.example.wanderingearth;


import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static java.lang.Math.sin;

public class Game2Activity extends AppCompatActivity
        implements DialogInterface.OnClickListener {
        int WINDOWWIDTH,WINDOWHEIGHT;
        final double PI=3.1415926;
        Barrier barrier_1=new Barrier();
        Barrier barrier_2=new Barrier();
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        setContentView(R.layout.startgame);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        WINDOWWIDTH = dm.widthPixels;//横屏宽度
        WINDOWHEIGHT = dm.heightPixels;//横屏高度
        /*
         *让door一直转而且透明度改变
         */
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
        Intent intent = new Intent(Game2Activity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
        }
        });
        /*
        以下是重新开始游戏按钮方法，将路径设置为初始位置，将地球的位置放回原点
         */
        findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent = new Intent(Game2Activity.this,Game2Activity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
        }
        });
        }
        /*
         *画出初始路径 且令其在屏幕改变焦点时不执行该方法
         */
        int i=1;
@Override
public void onWindowFocusChanged(boolean hasFocus){

        super.onWindowFocusChanged(hasFocus);
        if (i == 1) {

        //此处可以正常获取width、height等
        barrier_2.setMass(350);
        TextView mass_text = findViewById(R.id.Mass_text_2);
        mass_text.setText(String.valueOf(barrier_2.getMass()));
        ImageView earth_iView = findViewById(R.id.earth);
        ImageView door_iView = findViewById(R.id.door);
        LinearLayout layout = findViewById(R.id.LayoutInStartGame);
        int left = earth_iView.getLeft() + earth_iView.getWidth() / 2;
        int top = earth_iView.getTop() + earth_iView.getWidth() / 2;
        Earth earth = new Earth(this);
        float[] XDots = new float[door_iView.getLeft() - left+door_iView.getWidth()/2];
        float[] YDots = new float[door_iView.getLeft() - left+door_iView.getWidth()/2];
        for (int i = 0; i < door_iView.getLeft() - left+door_iView.getWidth()/2; i++) {
                XDots[i] = i + left;
                YDots[i] = (float) (-(Math.pow(1.02, barrier_2.getMass() - 70) * sin((PI / (WINDOWWIDTH - left) * i)))) + top;
        }
        earth.setXDots(XDots);
        earth.setYDots(YDots);
        //
        layout.addView(earth);
        AlphaAnimation alphaAnimation_path=new AlphaAnimation(0.95f,0.0f);
        alphaAnimation_path.setDuration(800);
        alphaAnimation_path.setRepeatMode(Animation.REVERSE);
        alphaAnimation_path.setInterpolator(new LinearInterpolator());
        alphaAnimation_path.setRepeatCount(-1);
        layout.startAnimation(alphaAnimation_path);
        }
        i=i+1;
        }

/*
 *按开始按钮的代码
 */
public void propertyMove(View v) {
        //以下是一种新的提示框的实现，代码移至184行，即boom.show之后
        Button plus_1=findViewById(R.id.plus_2);
        Button minus_1=findViewById(R.id.minus_2);
        Button start=findViewById(R.id.start);
        plus_1.setVisibility(View.GONE);
        minus_1.setVisibility(View.GONE);
        start.setVisibility(View.GONE);
        TextView mass_text=findViewById(R.id.Mass_text_2);
        mass_text.setVisibility(View.GONE);
        AlertDialog.Builder boom=new AlertDialog.Builder(this);
        AlertDialog.Builder complete=new AlertDialog.Builder(this);
        AlertDialog.Builder out=new AlertDialog.Builder(this);
        complete.setTitle("Congratulation!");
        complete.setMessage("Complete!!!");
        complete.setCancelable(false);
        complete.setPositiveButton("Go on！",this);
        complete.setNegativeButton("Select",this);
        ImageView jupiter_iView=findViewById(R.id.barrier_2);
        ImageView barrier_iView=findViewById(R.id.barrier_1);
        ImageView door_iView=findViewById(R.id.door);
final int jupiter_radius=jupiter_iView.getHeight()/2,barrier_radius=barrier_iView.getHeight()/2,door_radius=door_iView.getHeight()/2;
final ImageView earth = findViewById(R.id.earth);
final int left = earth.getLeft();
final int top = earth.getTop();
final int earth_radius=earth.getHeight()/2;


final ValueAnimator animator = ValueAnimator.ofInt(0, WINDOWWIDTH-earth_radius*2-left);//横屏宽度
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
        /*
         *获取坐标
         */
        int xEarth=earth.getLeft()+earth_radius,xJupiter=jupiter_iView.getLeft()+jupiter_radius,yEarth=earth.getTop()+earth_radius,yJupiter=jupiter_iView.getTop()+jupiter_radius;
        int xBarrier=barrier_iView.getLeft()+barrier_radius,yBarrier=barrier_iView.getTop()+barrier_radius;
        int xDoor=door_iView.getLeft()+door_radius,yDoor=barrier_iView.getTop()+door_radius;
        /*
         *获取距离
         */
        double distence_e_j=Math.sqrt((xEarth-xJupiter)*(xEarth-xJupiter)+(yEarth-yJupiter)*(yEarth-yJupiter));
        double distence_e_b=Math.sqrt((xEarth-xBarrier)*(xEarth-xBarrier)+(yEarth-yBarrier)*(yEarth-yBarrier));
        double distence_e_door=Math.sqrt((xEarth-xDoor)*(xEarth-xDoor)+(yEarth-yDoor)*(yEarth-yDoor));
        /*
         *实现地球移动的代码的一部分
         */
        int current = (int) animator.getAnimatedValue();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) earth.getLayoutParams();
        /*
         *地球碰撞了星球
         */
        if(distence_e_j<=jupiter_radius+earth_radius||distence_e_b<=barrier_radius+earth_radius){
        animator.cancel();
        AlertDialog dialog = boom.show();
        dialog.setContentView(R.layout.dailogue);
        TextView textWhenBomm = findViewById(R.id.alertText);
        textWhenBomm.setText(R.string.MessageWhenBoom);
        boom.setCancelable(true);
        boom.setOnCancelListener(new DialogInterface.OnCancelListener() {
@Override
public void onCancel(DialogInterface dialog) {
        textWhenBomm.setText("");
        dialog.dismiss();
        getWindow().setExitTransition(new Fade().setDuration(500).
        excludeChildren(R.drawable.gamebackgound,true)
        .excludeChildren(R.drawable.cute_jupiter,true)
        .excludeChildren(R.drawable.door,true));
        startActivity(new Intent(Game2Activity.this,Game2Activity.class)
        , ActivityOptions.makeSceneTransitionAnimation(Game2Activity.this).toBundle());
        finish();
        }
        });
        }
        /*
         *任务完成
         */
        else if(distence_e_door<=earth_radius+door_radius){
        animator.cancel();
        complete.show();

        }
        /*
         *地球到达屏幕边界
         */
        else if(earth.getTop()>=WINDOWHEIGHT-earth_radius*2||earth.getLeft()>=WINDOWWIDTH-earth_radius*2||earth.getTop()==0||earth.getLeft()==0){
        animator.cancel();

        }
        /*
         *地球正常移动
         */
        else {
        layoutParams.leftMargin = left + current;

        layoutParams.topMargin =(int) (top - ((Math.pow(1.02,barrier_2.getMass()-90)*sin((PI/(WINDOWWIDTH-left)*current)))));

        earth.setLayoutParams(layoutParams);
        }
        });

        animator.start();
        }
/*
 *加质量
 */
public void plus(View v){
        barrier_2.setMass(barrier_2.getMass()+1);
        TextView mass=findViewById(R.id.Mass_text_2);
        mass.setText(String.valueOf(barrier_2.getMass()));
        LinearLayout layout = findViewById(R.id.LayoutInStartGame);
        layout.removeAllViews();
        ImageView earth_iView=findViewById(R.id.earth);
        int left=earth_iView.getLeft()+earth_iView.getWidth()/2;
        int top=earth_iView.getTop()+earth_iView.getWidth()/2;
        ImageView door=findViewById(R.id.door);
        Earth earth = new Earth(this);
        float[] XDots = new float[door.getLeft()-left+door.getWidth()/2];
        float[] YDots = new float[door.getLeft()-left+door.getWidth()/2];
        for(int i = 0;i< door.getLeft()-left+door.getWidth()/2;i++){
        XDots[i] = i+left;
        YDots[i] =(float) (-(Math.pow(1.02,barrier_2.getMass()-70)*sin((PI/(WINDOWWIDTH-left)*i))))+top;
        }
        earth.setXDots(XDots);
        earth.setYDots(YDots);
        //
        layout.addView(earth);
        AlphaAnimation alphaAnimation_path=new AlphaAnimation(0.95f,0.0f);
        alphaAnimation_path.setDuration(800);
        alphaAnimation_path.setRepeatMode(Animation.REVERSE);
        alphaAnimation_path.setInterpolator(new LinearInterpolator());
        alphaAnimation_path.setRepeatCount(-1);
        layout.startAnimation(alphaAnimation_path);
        }
/*
 *减质量
 */
public void minus(View v){
        barrier_2.setMass(barrier_2.getMass()-1);
        TextView mass=findViewById(R.id.Mass_text_2);
        mass.setText(String.valueOf(barrier_2.getMass()));
        LinearLayout layout = findViewById(R.id.LayoutInStartGame);
        layout.removeAllViews();
        ImageView earth_iView=findViewById(R.id.earth);
        int left=earth_iView.getLeft()+earth_iView.getWidth()/2;
        int top=earth_iView.getTop()+earth_iView.getWidth()/2;
        ImageView door=findViewById(R.id.door);
        Earth earth = new Earth(this);
        float[] XDots = new float[door.getLeft()-left+door.getWidth()/2];
        float[] YDots = new float[door.getLeft()-left+door.getWidth()/2];
        for(int i = 0;i< door.getLeft()-left+door.getWidth()/2;i++){
        XDots[i] = i+left;
        YDots[i] =(float) (-(Math.pow(1.02,barrier_2.getMass()-70)*sin((PI/(WINDOWWIDTH-left)*i))))+top;
        }
        earth.setXDots(XDots);
        earth.setYDots(YDots);
        //
        layout.addView(earth);
        AlphaAnimation alphaAnimation_path=new AlphaAnimation(0.95f,0.0f);
        alphaAnimation_path.setDuration(800);
        alphaAnimation_path.setRepeatMode(Animation.REVERSE);
        alphaAnimation_path.setInterpolator(new LinearInterpolator());
        alphaAnimation_path.setRepeatCount(-1);
        layout.startAnimation(alphaAnimation_path);
        }

@Override
public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE) {
        //改变选关界面
        findViewById(R.id.Level2).setVisibility(View.VISIBLE);
        findViewById(R.id.Level2ViewLocked).setVisibility(View.GONE);
        findViewById(R.id.Level2ViewUnlocked).setVisibility(View.VISIBLE);
        //写跳转至下一关的代码;
        startActivity(new Intent(Game2Activity.this,Game3Activity.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
        }
        else if(which==DialogInterface.BUTTON_NEGATIVE){
        //写返回到选关界面的代码。
        startActivity(new Intent(Game2Activity.this,ChooseGameActivity.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
        }

        }
        }
