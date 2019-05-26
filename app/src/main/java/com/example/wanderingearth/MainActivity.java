package com.example.wanderingearth;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;
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
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int unlockedGames = getIntent().getIntExtra("UnlockedGame",1);
        ActivityContainer.getInstance().addActivity(this);
        mediaPlayer =MediaPlayer.create(this,R.raw.disound);
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
                Intent intent = new Intent(MainActivity.this,StartGameFlashActivity.class).putExtra("UnlockedGame",unlockedGames);
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
                Intent intent = new Intent(MainActivity.this,ChooseGameActivity.class).putExtra("UnlockedGame",unlockedGames);
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

    private void playMusic() {
        mediaPlayer.start();
        mediaPlayer.reset();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.release();
    }
}
