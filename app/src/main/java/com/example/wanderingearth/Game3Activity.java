package com.example.wanderingearth;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Game3Activity extends AppCompatActivity {
    int WINDOWWIDTH,WINDOWHEIGHT;
    private MediaPlayer gamebackPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        setContentView(R.layout.activity_game3);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int musicTime = getIntent().getIntExtra("musicTime",0);
        gamebackPlayer = MediaPlayer.create(this,R.raw.mtets);
        gamebackPlayer.seekTo(musicTime);
        gamebackPlayer.setLooping(true);
        gamebackPlayer.start();;
        WINDOWWIDTH = dm.widthPixels;//横屏宽度
        WINDOWHEIGHT = dm.heightPixels;//横屏高度
        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamebackPlayer.pause();
                Bundle bundle = new Bundle();
                bundle.putInt("UnlockedGame",3);
                startActivity(new Intent(Game3Activity.this,ChooseGameActivity.class).putExtras(bundle));
                gamebackPlayer.release();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamebackPlayer.pause();
                Bundle bundle = new Bundle();
                bundle.putInt("UnlockedGame",3);
                startActivity(new Intent(Game3Activity.this,MainActivity.class).putExtras(bundle));
                gamebackPlayer.release();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
    }
    int i=1;
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
        if(i==1){
            ImageView earth=findViewById(R.id.earth);
            Animation translate=new TranslateAnimation(-earth.getWidth()-100,WINDOWWIDTH,0,0);
            translate.setDuration(3000);
            translate.setRepeatCount(-1);
            translate.setInterpolator(new LinearInterpolator());
            earth.startAnimation(translate);
        }

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        gamebackPlayer.release();
    }
}
