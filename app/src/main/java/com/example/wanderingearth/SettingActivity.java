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
                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setCancelable(true);//用户按下手机上的返回键即可返回
                final AlertDialog dialog = builder.show();
                dialog.setContentView(R.layout.dailogue);
                TextView mytext = findViewById(R.id.alertText);
                mytext.setText(R.string.LearnMoreMessage);
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mytext.setText("");
                        dialog.dismiss();
                    }
                });//按下返回即可退出此对话框
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
