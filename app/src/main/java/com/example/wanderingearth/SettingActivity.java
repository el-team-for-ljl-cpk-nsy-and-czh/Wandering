package com.example.wanderingearth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//不显示状态栏的指令；
        ActivityContainer.getInstance().addActivity(this);
        /**
         * 按下“返回”按钮可以实现返回返主界面；
         */
        findViewById(R.id.ReturnInSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
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
                builder.setTitle("关于我们");
                builder.setMessage(R.string.LearnMoreMessage);
                builder.setCancelable(true);//用户按下手机上的返回键即可返回
                final AlertDialog dialog = builder.show();
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });//用户按下“OK”按钮即可消除对话框；
            }
        });
        /**
         * 以下是跳转到选关界面的代码
         */
        findViewById(R.id.ChooseGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ChooseGameActivity.class);
                startActivity(intent);
            }
        });
    }

}
