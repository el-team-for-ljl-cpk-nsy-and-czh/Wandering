package com.example.wanderingearth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;

public class ChooseGameActivity extends AppCompatActivity {
    private int[] listOfButtons = {R.id.Level2,R.id.Level3,R.id.Level4,R.id.Level5};
    private int[] listOfLockedViews = {R.id.Level2ViewLocked,R.id.Level3ViewLocked,R.id.Level4ViewLocked,R.id.Level5ViewLocked};
    private int[] listOfUnlockedViews = {R.id.Level2ViewUnlocked,R.id.Level3ViewUnlocked,R.id.Level4ViewUnlocked,R.id.Level5ViewUnlocked};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        setContentView(R.layout.activity_choose);
        int unlockedGames = getIntent().getIntExtra("UnlockedGame",1);
        if(unlockedGames>1){
            for(int i=0;i<unlockedGames-1;i++){
                findViewById(listOfButtons[i]).setVisibility(View.VISIBLE);
                findViewById(listOfLockedViews[i]).setVisibility(View.INVISIBLE);
                findViewById(listOfUnlockedViews[i]).setVisibility(View.VISIBLE);
            }
        }
        findViewById(R.id.Level1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setExitTransition(new Fade().setDuration(500));
                Intent intent = new Intent(ChooseGameActivity.this,StartGameActivity.class)
                        .putExtra("UnlockedGame",unlockedGames);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        /*
        刚开始时其他关卡的按钮是invisible的，在通过前面一关的时候将其set为visible并且更换图片
         */
        findViewById(R.id.Level2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseGameActivity.this,Game2Activity.class)
                        .putExtra("UnlockedGame",unlockedGames));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        findViewById(R.id.Level3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseGameActivity.this,Game3Activity.class)
                        .putExtra("UnlockedGame",unlockedGames));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        findViewById(R.id.ReturnInChoose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseGameActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
    }
}
