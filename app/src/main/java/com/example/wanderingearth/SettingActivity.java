package com.example.wanderingearth;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer ;
    private MediaPlayer backgroundPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(this,R.raw.disound);
        int unlockedGames = getIntent().getExtras().getInt("UnlockedGame",1);
        int musicTime = getIntent().getExtras().getInt("musicTime",0);
        backgroundPlayer = MediaPlayer.create(this,R.raw.backgroundmusic);
        backgroundPlayer.seekTo(musicTime);
        backgroundPlayer.start();
        mediaPlayer.seekTo(0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        getWindow().setEnterTransition(new Fade().setDuration(300).excludeChildren(R.drawable.background_paintstyle,true));
        setContentView(R.layout.settings);
        ActivityContainer.getInstance().addActivity(this);
        /*
         * 按下“返回”按钮可以实现返回返主界面；
         */
        findViewById(R.id.ReturnInSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                backgroundPlayer.pause();
                Bundle bundle = new Bundle();
                bundle.putInt("UnlockedGame",unlockedGames);
                bundle.putInt("musicTime",backgroundPlayer.getCurrentPosition());
                backgroundPlayer.release();
                Intent intent = new Intent(SettingActivity.this,MainActivity.class).putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        /*
         * 以下是点击“了解更多”所显示的代码；
         */
        findViewById(R.id.LearnMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                Animation alpha=new AlphaAnimation(0,1);
                alpha.setDuration(300);
                findViewById(R.id.ChooseGame).setVisibility(View.INVISIBLE);
                findViewById(R.id.LearnMore).setVisibility(View.INVISIBLE);
                findViewById(R.id.ReturnInSetting).setVisibility(View.INVISIBLE);
                findViewById(R.id.DialogueView).setVisibility(View.VISIBLE);
                findViewById(R.id.DialogueView).startAnimation(alpha);
                findViewById(R.id.Text).setVisibility(View.VISIBLE);
                findViewById(R.id.Text).startAnimation(alpha);
                findViewById(R.id.cancelView).setVisibility(View.VISIBLE);
                findViewById(R.id.cancelView).startAnimation(alpha);
                findViewById(R.id.cancelInSeting).setVisibility(View.VISIBLE);
                findViewById(R.id.cancelInSeting).startAnimation(alpha);
                findViewById(R.id.cancelInSeting).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.ChooseGame).setVisibility(View.VISIBLE);
                        findViewById(R.id.LearnMore).setVisibility(View.VISIBLE);
                        findViewById(R.id.ReturnInSetting).setVisibility(View.VISIBLE);
                        findViewById(R.id.DialogueView).setVisibility(View.INVISIBLE);
                        findViewById(R.id.Text).setVisibility(View.INVISIBLE);
                        findViewById(R.id.cancelView).setVisibility(View.INVISIBLE);
                        findViewById(R.id.cancelInSeting).setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        /*
         * 以下是跳转到关于我们的代码
         */
        findViewById(R.id.ChooseGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
                Animation alpha = new AlphaAnimation(0, 1);
                alpha.setDuration(300);
                findViewById(R.id.ChooseGame).setVisibility(View.INVISIBLE);
                findViewById(R.id.LearnMore).setVisibility(View.INVISIBLE);
                findViewById(R.id.ReturnInSetting).setVisibility(View.INVISIBLE);
                findViewById(R.id.DialogueView).setVisibility(View.VISIBLE);
                findViewById(R.id.DialogueView).startAnimation(alpha);
                findViewById(R.id.TextAbout).setVisibility(View.VISIBLE);
                findViewById(R.id.TextAbout).startAnimation(alpha);
                findViewById(R.id.cancelView).setVisibility(View.VISIBLE);
                findViewById(R.id.cancelView).startAnimation(alpha);
                findViewById(R.id.cancelInSeting).setVisibility(View.VISIBLE);
                findViewById(R.id.cancelInSeting).startAnimation(alpha);
                findViewById(R.id.cancelInSeting).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.ChooseGame).setVisibility(View.VISIBLE);
                        findViewById(R.id.LearnMore).setVisibility(View.VISIBLE);
                        findViewById(R.id.ReturnInSetting).setVisibility(View.VISIBLE);
                        findViewById(R.id.DialogueView).setVisibility(View.INVISIBLE);
                        findViewById(R.id.TextAbout).setVisibility(View.INVISIBLE);
                        findViewById(R.id.cancelView).setVisibility(View.INVISIBLE);
                        findViewById(R.id.cancelInSeting).setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }
    private void playMusic(){
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
