package com.example.wanderingearth;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        getWindow().setEnterTransition(new Fade().setDuration(300).excludeChildren(R.drawable.background_paintstyle,true));
        setContentView(R.layout.settings);
        ActivityContainer.getInstance().addActivity(this);
        /**
         * 按下“返回”按钮可以实现返回返主界面；
         */
        findViewById(R.id.ReturnInSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setExitTransition(new Fade().setDuration(500).excludeChildren(R.drawable.background_paintstyle,true));
                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SettingActivity.this).toBundle());
                finish();
            }
        });
        /**
         * 以下是点击“了解更多”所显示的代码；
         */
        findViewById(R.id.LearnMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.ChooseGame).setVisibility(View.INVISIBLE);
                findViewById(R.id.LearnMore).setVisibility(View.INVISIBLE);
                findViewById(R.id.ReturnInSetting).setVisibility(View.INVISIBLE);
                findViewById(R.id.DialogueView).setVisibility(View.VISIBLE);
                findViewById(R.id.Text).setVisibility(View.VISIBLE);
                findViewById(R.id.cancelView).setVisibility(View.VISIBLE);
                findViewById(R.id.cancelInSeting).setVisibility(View.VISIBLE);
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
        /**
         * 以下是跳转到选关界面的代码
         */
        findViewById(R.id.ChooseGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ChooseGameActivity.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(SettingActivity.this).toBundle());
                finish();
            }
        });
    }

}
