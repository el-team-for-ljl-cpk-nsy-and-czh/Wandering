package com.example.wanderingearth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MyAlertDialogue extends Dialog  {
    private TextView message;
    private Button rebutton;
    private Context context;
    public MyAlertDialogue(Context context){
        super(context);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getWindow().setGravity(Gravity.CENTER);
        setContentView(R.layout.dailogue);
        WindowManager windowManager = ((Activity)context).getWindowManager();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        Display display = windowManager.getDefaultDisplay();
        lp.width = display.getWidth()*4/5;
        lp.height = display.getDisplayId()*5/6;
        getWindow().setAttributes(lp);
        message = findViewById(R.id.alertText);
        setCanceledOnTouchOutside(false);
        rebutton = findViewById(R.id.buttonInDialogue);
    }

    public void setText(int id){
        message.setText(id);
    }
    public void setOnClickListener(View.OnClickListener ls){
        rebutton.setOnClickListener(ls);
    }
}
