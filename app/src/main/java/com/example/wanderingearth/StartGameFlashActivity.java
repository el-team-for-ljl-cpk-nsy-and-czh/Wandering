package com.example.wanderingearth;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class StartGameFlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_game_animation);
        /**
         * 以下是实现文本框渐变动画的代码
         */
        final AlphaAnimation myani = new AlphaAnimation(1,0.3f);
        myani.setDuration(2500);
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
                Intent intent = new Intent(StartGameFlashActivity.this,StartGameActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }//当动画结束自动切换至下一个画面

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
