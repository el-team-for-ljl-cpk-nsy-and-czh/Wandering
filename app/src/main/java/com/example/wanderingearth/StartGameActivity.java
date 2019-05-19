package com.example.wanderingearth;

import android.drm.DrmStore;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class StartGameActivity extends AppCompatActivity {
    private Earth earth = new Earth(this);//此处声明earth则在后续的所有方法中都可以使用earth；
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        setContentView(R.layout.startgame);
        /*
        以下是初始化earth轨迹的代码，仅供测试使用；
         */
        float[] XDots = new float[250];
        float[] YDots = new float[250];
        for(int i = 1;i<= 250;i++){
            XDots[i] = i;
            YDots[i] = i*i + 2*i + 2;
        }
        earth.setXDots(XDots);
        earth.setYDots(YDots);
        //
        LinearLayout layout = findViewById(R.id.LayoutInStartGame);
        layout.addView(earth);
        findViewById(R.id.Move).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
