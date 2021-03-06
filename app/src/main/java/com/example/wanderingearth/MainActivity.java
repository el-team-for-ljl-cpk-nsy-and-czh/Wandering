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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private MediaPlayer backgroundPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int unlockedGames = getIntent().getIntExtra("UnlockedGame",1);
        int musicTime = getIntent().getIntExtra("musicTime",0);
        ActivityContainer.getInstance().addActivity(this);
        backgroundPlayer = MediaPlayer.create(this,R.raw.backgroundmusic);
        backgroundPlayer.seekTo(musicTime);
        backgroundPlayer.setLooping(true);
        backgroundPlayer.start();
        mediaPlayer =MediaPlayer.create(this,R.raw.disound);
        mediaPlayer.seekTo(0);
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
                Intent intent;
                switch (unlockedGames){
                    case 1:{intent = new Intent(MainActivity.this,StartGameFlashActivity.class) ;break;}
                    case 2:{intent = new Intent(MainActivity.this,Game2Activity.class);break;}
                    case 3:{intent = new Intent(MainActivity.this,Game3Activity.class);break;}
                    default:{intent = new Intent(MainActivity.this,StartGameFlashActivity.class);}
                }
                backgroundPlayer.pause();
                startActivity(intent.putExtra("UnlockedGame",unlockedGames));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        findViewById(R.id.Setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                backgroundPlayer.pause();
                Bundle bundle = new Bundle();
                bundle.putInt("UnlockedGame",unlockedGames);
                bundle.putInt("musicTime",backgroundPlayer.getCurrentPosition());
                backgroundPlayer.release();
                Intent intent = new Intent(MainActivity.this,SettingActivity.class).putExtras(bundle);
                startActivity(intent);//启动设置页面；
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        findViewById(R.id.Continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                backgroundPlayer.pause();
                Bundle bundle = new Bundle();
                bundle.putInt("UnlockedGame",unlockedGames);
                bundle.putInt("musicTime",backgroundPlayer.getCurrentPosition());
                backgroundPlayer.release();
                Intent intent = new Intent(MainActivity.this,ChooseGameActivity.class).putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        findViewById(R.id.ExitGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                mediaPlayer.release();
                getWindow().setExitTransition(new Fade().setDuration(200));
                ActivityContainer.getInstance().finishAllActivity();
            }
        });
    }

    private void playMusic() {
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.release();
        backgroundPlayer.release();
    }
}
