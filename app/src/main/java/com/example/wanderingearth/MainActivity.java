package com.example.wanderingearth;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        /*
        以下是用来改变activity切换效果的代码
         */
        getWindow().setEnterTransition(new Slide().setDuration(300));
        getWindow().setExitTransition(new Explode().setDuration(300));
        getWindow().setReenterTransition(new Slide().setDuration(300));

        setContentView(R.layout.start_screen);
        findViewById(R.id.StartGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StartGameFlashActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        findViewById(R.id.Setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());//启动设置页面；
            }
        });
        findViewById(R.id.Continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChooseGameActivity.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        findViewById(R.id.ExitGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityContainer.getInstance().finishAllActivity();
            }
        });
    }
}
