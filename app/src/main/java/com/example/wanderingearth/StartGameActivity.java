package com.example.wanderingearth;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
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

public class StartGameActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayerAlert;
    private static boolean firstStart = true;
    private int numberNeededInGuide=0;
    private MediaPlayer gamebackPlayer;
    //此处声明earth则在后续的所有方法中都可以使用earth；
    int WINDOWWIDTH,WINDOWHEIGHT;
    final double PI=3.1415926;
    Barrier jupiter=new Barrier();
    int a=150;
    private int unlockedGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        mediaPlayerAlert = MediaPlayer.create(this,R.raw.alertvoice);
        mediaPlayerAlert.seekTo(0);
        unlockedGames = getIntent().getIntExtra("UnlockedGame",1);
        int musicTime = getIntent().getIntExtra("musicTime", 0);
        gamebackPlayer = MediaPlayer.create(this,R.raw.mtets);
        gamebackPlayer.setLooping(true);
        gamebackPlayer.seekTo(musicTime);
        gamebackPlayer.start();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        setContentView(R.layout.startgame);
        //第一次进行游戏出现指导
        if(firstStart){
            firstStart = false;
            int[] IDs = {R.id.Guide1,R.id.guide2,R.id.guide3,R.id.guide4,R.id.guide5,R.id.guide6,R.id.guide7};
            findViewById(R.id.guideView).setVisibility(View.VISIBLE);
            findViewById(R.id.goback).setVisibility(View.INVISIBLE);
            findViewById(R.id.restart).setVisibility(View.INVISIBLE);
            findViewById(R.id.start).setClickable(false);
            findViewById(R.id.plus).setClickable(false);
            findViewById(R.id.minus).setClickable(false);
            findViewById(R.id.goOnButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(numberNeededInGuide<6){
                        if(numberNeededInGuide==4){
                            findViewById(R.id.arrow).setVisibility(View.VISIBLE);
                        }
                        if(numberNeededInGuide==5){
                            findViewById(R.id.arrow).setVisibility(View.INVISIBLE);
                        }
                        findViewById(IDs[numberNeededInGuide]).setVisibility(View.INVISIBLE);
                        findViewById(IDs[numberNeededInGuide+1]).setVisibility(View.VISIBLE);
                    }
                    else{
                        findViewById(IDs[numberNeededInGuide]).setVisibility(View.INVISIBLE);
                        findViewById(IDs[0]).setVisibility(View.VISIBLE);
                        findViewById(R.id.guideView).setVisibility(View.GONE);
                        findViewById(R.id.goback).setVisibility(View.VISIBLE);
                        findViewById(R.id.restart).setVisibility(View.VISIBLE);
                        findViewById(R.id.start).setClickable(true);
                        findViewById(R.id.minus).setClickable(true);
                        findViewById(R.id.plus).setClickable(true);
                    }
                    numberNeededInGuide++;
                }
            });
        }
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
        以下是返回按钮的方法，按下按钮返回选关界面；
         */
        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamebackPlayer.pause();
                Bundle bundle = new Bundle();
                bundle.putInt("UnlockedGame",unlockedGames);
                Intent intent = new Intent(StartGameActivity.this,ChooseGameActivity.class).putExtras(bundle);
                startActivity(intent);
                gamebackPlayer.release();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        /*
        以下是重新开始游戏按钮方法，将路径设置为初始位置，将地球的位置放回原点
         */
        findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
            int k=1;
            @Override
            public void onClick(View v) {
                if(k==1) {
                    gamebackPlayer.pause();
                    Bundle bundle = new Bundle();
                    bundle.putInt("UnlockedGame", unlockedGames);
                    bundle.putInt("musicTime", gamebackPlayer.getCurrentPosition());
                    gamebackPlayer.release();
                    Intent intent = new Intent(StartGameActivity.this, StartGameActivity.class).putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    k=k+1;
                }
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
            jupiter.setMass(318);
            TextView mass_text = findViewById(R.id.Mass_text);
            mass_text.setText(String.valueOf(jupiter.getMass()));
            ImageView earth_iView = findViewById(R.id.earth);
            ImageView door_iView = findViewById(R.id.door);
            LinearLayout layout = findViewById(R.id.LayoutInStartGame1);
            int left = earth_iView.getLeft() + earth_iView.getWidth() / 2;
            int top = earth_iView.getTop() + earth_iView.getWidth() / 2;
            Earth earth = new Earth(this);
            float[] XDots = new float[door_iView.getLeft() - left+door_iView.getWidth()/2];
            float[] YDots = new float[door_iView.getLeft() - left+door_iView.getWidth()/2];
            for (int i = 0; i < door_iView.getLeft() - left+door_iView.getWidth()/2; i++) {
                XDots[i] = i + left;
                YDots[i] = (float) (-(Math.pow(1.03, jupiter.getMass() - a) * sin((PI / (door_iView.getLeft() - left+door_iView.getWidth()/2.0) * i)))) + top;
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
            layout.setVisibility(View.VISIBLE);
        }
        i=i+1;
    }

    /*
     *按开始按钮的代码
     */
    public void propertyMove(View v) {
        Animation alpha = new AlphaAnimation(0,1);
        alpha.setDuration(700);
        ImageView jupiter_iView=findViewById(R.id.jupiter);
        ImageView barrier_iView=findViewById(R.id.barrier_1);
        ImageView door_iView=findViewById(R.id.door);
        Button start=findViewById(R.id.start);
        Button plus=findViewById(R.id.plus);
        Button minus=findViewById(R.id.minus);
        TextView mass_text=findViewById(R.id.Mass_text);
        start.setVisibility(View.GONE);
        plus.setVisibility(View.GONE);
        minus.setVisibility(View.GONE);
        mass_text.setVisibility(View.GONE);
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
        int xDoor=door_iView.getLeft()+door_radius,yDoor=door_iView.getTop()+door_radius;
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
        //下面是距离小于洛希极限所显示的提示框
        /*
         *地球碰撞了星球
         */
        if(distence_e_j<=jupiter_radius*1.22+earth_radius||distence_e_b<=barrier_radius*1.22+earth_radius){
            animator.cancel();
            mediaPlayerAlert.start();
            mediaPlayerAlert.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            findViewById(R.id.LayoutInStartGame1).setVisibility(View.INVISIBLE);
            findViewById(R.id.jupiter).setVisibility(View.VISIBLE);
            findViewById(R.id.barrier_1).setVisibility(View.INVISIBLE);
            findViewById(R.id.earth).setVisibility(View.INVISIBLE);
            findViewById(R.id.plus).setVisibility(View.INVISIBLE);
            findViewById(R.id.minus).setVisibility(View.INVISIBLE);
            findViewById(R.id.Mass_text).setVisibility(View.INVISIBLE);
            findViewById(R.id.restart).setClickable(false);
            findViewById(R.id.start).setClickable(false);
            findViewById(R.id.goback).setClickable(false);
            findViewById(R.id.dialogueView).setVisibility(View.VISIBLE);
            findViewById(R.id.dialogueView).setAnimation(alpha);
            findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.dialogueView).setVisibility(View.GONE);
                    findViewById(R.id.jupiter).setVisibility(View.VISIBLE);
                    findViewById(R.id.barrier_1).setVisibility(View.VISIBLE);
                    findViewById(R.id.earth).setVisibility(View.VISIBLE);
                    findViewById(R.id.plus).setVisibility(View.VISIBLE);
                    findViewById(R.id.minus).setVisibility(View.VISIBLE);
                    findViewById(R.id.LayoutInStartGame1).setVisibility(View.VISIBLE);
                    findViewById(R.id.Mass_text).setVisibility(View.VISIBLE);
                    findViewById(R.id.restart).setClickable(true);
                    findViewById(R.id.start).setClickable(true);
                    findViewById(R.id.goback).setClickable(true);
                    gamebackPlayer.pause();
                    Bundle bundle = new Bundle();
                    bundle.putInt("musicTime",gamebackPlayer.getCurrentPosition());
                    startActivity(new Intent(StartGameActivity.this,StartGameActivity.class).putExtras(bundle));
                    gamebackPlayer.release();
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            });
            findViewById(R.id.R_limit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.R_limit).setVisibility(View.GONE);
                    findViewById(R.id.cancelButton).setVisibility(View.GONE);
                    findViewById(R.id.textView3).setVisibility(View.GONE);
                    findViewById(R.id.Rlimit_text).setVisibility(View.VISIBLE);
                    findViewById(R.id.Rlimit_text).startAnimation(alpha);
                    findViewById(R.id.gotIt).setVisibility(View.VISIBLE);
                    findViewById(R.id.gotIt).setAnimation(alpha);
                }
            });
            findViewById(R.id.gotIt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.R_limit).setVisibility(View.VISIBLE);
                    findViewById(R.id.R_limit).startAnimation(alpha);
                    findViewById(R.id.cancelButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.cancelButton).setAnimation(alpha);
                    findViewById(R.id.textView3).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView3).setAnimation(alpha);
                    findViewById(R.id.Rlimit_text).setVisibility(View.GONE);
                    findViewById(R.id.gotIt).setVisibility(View.GONE);
                }
            });

        }
        /*
         *任务完成
         */
        //下面是结束所显示的提示框
        else if(distence_e_door<=earth_radius+door_radius){
            animator.cancel();
            findViewById(R.id.barrier_1).setVisibility(View.INVISIBLE);
            findViewById(R.id.jupiter).setVisibility(View.INVISIBLE);
            findViewById(R.id.earth).setVisibility(View.INVISIBLE);
            findViewById(R.id.LayoutInStartGame1).setVisibility(View.INVISIBLE);

            findViewById(R.id.goback).setClickable(false);
            findViewById(R.id.restart).setClickable(false);
            findViewById(R.id.start).setClickable(false);
            findViewById(R.id.Mass_text).setVisibility(View.INVISIBLE);
            findViewById(R.id.minus).setVisibility(View.INVISIBLE);
            findViewById(R.id.plus).setVisibility(View.INVISIBLE);
            findViewById(R.id.congratulationView).setVisibility(View.VISIBLE);
            findViewById(R.id.congratulationView).setAnimation(alpha);
            findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.congratulationView).setVisibility(View.GONE);
                    findViewById(R.id.jupiter).setVisibility(View.VISIBLE);
                    findViewById(R.id.barrier_1).setVisibility(View.VISIBLE);
                    findViewById(R.id.earth).setVisibility(View.VISIBLE);
                    findViewById(R.id.plus).setVisibility(View.VISIBLE);
                    findViewById(R.id.minus).setVisibility(View.VISIBLE);
                    findViewById(R.id.LayoutInStartGame1).setVisibility(View.VISIBLE);
                    findViewById(R.id.Mass_text).setVisibility(View.VISIBLE);
                    findViewById(R.id.goback).setClickable(true);
                    findViewById(R.id.restart).setClickable(true);
                    findViewById(R.id.start).setClickable(true);
                    Bundle bundle = new Bundle();
                    bundle.putInt("UnlockedGame",2);
                    gamebackPlayer.pause();
                    gamebackPlayer.release();
                    startActivity(new Intent(StartGameActivity.this,ChooseGameActivity.class).putExtras(bundle));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            });//跳转到选关界面；
            findViewById(R.id.nextLevel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.congratulationView).setVisibility(View.GONE);
                    findViewById(R.id.earth).setVisibility(View.VISIBLE);
                    findViewById(R.id.goback).setClickable(true);
                    findViewById(R.id.restart).setClickable(true);
                    findViewById(R.id.start).setClickable(true);
                    findViewById(R.id.plus).setVisibility(View.VISIBLE);
                    findViewById(R.id.minus).setVisibility(View.VISIBLE);
                    findViewById(R.id.Mass_text).setVisibility(View.VISIBLE);
                    findViewById(R.id.jupiter).setVisibility(View.VISIBLE);
                    findViewById(R.id.barrier_1).setVisibility(View.VISIBLE);
                    gamebackPlayer.pause();
                    Bundle bundle = new Bundle();
                    bundle.putInt("musicTime",gamebackPlayer.getCurrentPosition());
                    startActivity(new Intent(StartGameActivity.this,Game2Activity.class).putExtras(bundle));
                    gamebackPlayer.release();
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            });

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

            layoutParams.topMargin =(int) (top - (Math.pow(1.03,jupiter.getMass()-a)*sin( PI / (door_iView.getLeft() - left+door_iView.getWidth()/2.0) * current)));

            earth.setLayoutParams(layoutParams);
        }
        });

        animator.start();
    }
    /*
     *加质量
     */
    public void plus(View v){
        if(jupiter.getMass()<=360) {
            jupiter.setMass(jupiter.getMass() + 1);
            TextView mass = findViewById(R.id.Mass_text);
            mass.setText(String.valueOf(jupiter.getMass()));
            LinearLayout layout = findViewById(R.id.LayoutInStartGame1);
            layout.removeAllViews();
            ImageView earth_iView = findViewById(R.id.earth);
            int left = earth_iView.getLeft() + earth_iView.getWidth() / 2;
            int top = earth_iView.getTop() + earth_iView.getWidth() / 2;
            ImageView door = findViewById(R.id.door);
            Earth earth = new Earth(this);
            float[] XDots = new float[door.getLeft() - left + door.getWidth() / 2];
            float[] YDots = new float[door.getLeft() - left + door.getWidth() / 2];
            for (int i = 0; i < door.getLeft() - left + door.getWidth() / 2; i++) {
                XDots[i] = i + left;
                YDots[i] = (float) (-(Math.pow(1.03, jupiter.getMass() - a) * sin((PI / (door.getLeft() - left + door.getWidth() / 2.0) * i)))) + top;
            }
            earth.setXDots(XDots);
            earth.setYDots(YDots);
            //
            layout.addView(earth);
            AlphaAnimation alphaAnimation_path = new AlphaAnimation(0.95f, 0.0f);
            alphaAnimation_path.setDuration(800);
            alphaAnimation_path.setRepeatMode(Animation.REVERSE);
            alphaAnimation_path.setInterpolator(new LinearInterpolator());
            alphaAnimation_path.setRepeatCount(-1);
            layout.startAnimation(alphaAnimation_path);
        }
    }
    /*
     *减质量
     */
    public void minus(View v){
        if(jupiter.getMass()<=360) {
            jupiter.setMass(jupiter.getMass() - 1);
            TextView mass = findViewById(R.id.Mass_text);
            mass.setText(String.valueOf(jupiter.getMass()));
            LinearLayout layout = findViewById(R.id.LayoutInStartGame1);
            layout.removeAllViews();
            ImageView earth_iView = findViewById(R.id.earth);
            int left = earth_iView.getLeft() + earth_iView.getWidth() / 2;
            int top = earth_iView.getTop() + earth_iView.getWidth() / 2;
            ImageView door = findViewById(R.id.door);
            Earth earth = new Earth(this);
            float[] XDots = new float[door.getLeft() - left + door.getWidth() / 2];
            float[] YDots = new float[door.getLeft() - left + door.getWidth() / 2];
            for (int i = 0; i < door.getLeft() - left + door.getWidth() / 2; i++) {
                XDots[i] = i + left;
                YDots[i] = (float) (-(Math.pow(1.03, jupiter.getMass() - a) * sin((PI / (door.getLeft() - left + door.getWidth() / 2.0) * i)))) + top;
            }
            earth.setXDots(XDots);
            earth.setYDots(YDots);
            //
            layout.addView(earth);
            AlphaAnimation alphaAnimation_path = new AlphaAnimation(0.95f, 0.0f);
            alphaAnimation_path.setDuration(800);
            alphaAnimation_path.setRepeatMode(Animation.REVERSE);
            alphaAnimation_path.setInterpolator(new LinearInterpolator());
            alphaAnimation_path.setRepeatCount(-1);
            layout.startAnimation(alphaAnimation_path);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayerAlert.release();
        gamebackPlayer.release();
    }
}
