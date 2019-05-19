package com.example.wanderingearth;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class StartGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        setContentView(R.layout.startgame);
        float[] XDots = new float[250];
        float[] YDots = new float[250];
        for(int i = 1;i<=250;i++){
            XDots[i] = i;
            YDots[i] = i*i + 2*i + 2;
        }
        Earth earth = new Earth(StartGameActivity.this,XDots,YDots);
    }
}
