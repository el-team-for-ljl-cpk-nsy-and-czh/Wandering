package com.example.wanderingearth;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.transition.Fade;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class SplashActivity extends Activity {
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(this,R.raw.splash3sec);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ImageView liulang=findViewById(R.id.imageView2);
        Animation alphaAnimation=new AlphaAnimation(1,0);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(1);
        liulang.startAnimation(alphaAnimation);
        Thread mythread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2500);
                    mediaPlayer.stop();
                    Intent it = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(it);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        mythread.start();

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.release();
    }
}
