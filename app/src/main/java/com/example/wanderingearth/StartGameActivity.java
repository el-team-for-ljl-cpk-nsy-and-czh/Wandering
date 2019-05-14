package com.example.wanderingearth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class StartGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_game_animation);
        /**
         * 以下是实现文本框渐变动画的代码
         */
        final AlphaAnimation myani = new AlphaAnimation(1,0.3f);
        myani.setDuration(4000);
        myani.setRepeatCount(0);
        myani.setFillAfter(true);
        findViewById(R.id.textInStartAnimation).setAnimation(myani);
        myani.start();
        myani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setContentView(R.layout.startgame);
            }//当动画结束自动切换至下一个画面

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
