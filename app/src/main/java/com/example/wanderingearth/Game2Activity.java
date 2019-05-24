package com.example.wanderingearth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.WindowManager;

public class Game2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        getWindow().setEnterTransition(new Fade().setDuration(500)
                .excludeChildren(R.drawable.gamebackgound,true));
        setContentView(R.layout.activity_game2);
    }
}
