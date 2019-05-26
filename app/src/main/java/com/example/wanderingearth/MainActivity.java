package com.example.wanderingearth;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM,5);
    public int music = soundPool.load(this,R.raw.disound,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        /*
        以下是用来改变activity切换效果的代码
         */
        getWindow().setEnterTransition(new Fade().setDuration(500));
        getWindow().setReenterTransition(new Fade().setDuration(500));

        setContentView(R.layout.start_screen);
        findViewById(R.id.StartGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                Intent intent = new Intent(MainActivity.this,StartGameFlashActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        findViewById(R.id.Setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);getWindow().setExitTransition(new Fade().setDuration(300).excludeChildren(R.drawable.background_paintstyle,true));
                startActivity(intent);//启动设置页面；
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        findViewById(R.id.Continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                Intent intent = new Intent(MainActivity.this,ChooseGameActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        findViewById(R.id.ExitGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                getWindow().setExitTransition(new Fade().setDuration(200));
                ActivityContainer.getInstance().finishAllActivity();
            }
        });
    }

    private void playMusic(){
        soundPool.play(music,1,1,0,0,1);
    }
}
