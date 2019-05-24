package com.example.wanderingearth;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;

public class ChooseGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        getWindow().setEnterTransition(new Fade().setDuration(500));
        getWindow().setExitTransition(new Fade().setDuration(500));
        setContentView(R.layout.activity_choose);
        findViewById(R.id.Level1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setExitTransition(new Fade().setDuration(500));
                Intent intent = new Intent(ChooseGameActivity.this,StartGameActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ChooseGameActivity.this).toBundle());
                finish();
            }
        });
        /*
        刚开始时其他关卡的按钮是invisible的，在通过前面一关的时候将其set为visible并且更换图片
         */
        findViewById(R.id.Level2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setExitTransition(new Fade().setDuration(500));
                startActivity(new Intent(ChooseGameActivity.this,Game2Activity.class),ActivityOptions.makeSceneTransitionAnimation(ChooseGameActivity.this).toBundle());
                finish();
            }
        });
        findViewById(R.id.ReturnInChoose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setExitTransition(new Fade().setDuration(500));
                Intent intent = new Intent(ChooseGameActivity.this,MainActivity.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(ChooseGameActivity.this).toBundle());
                finish();
            }
        });
    }
}
