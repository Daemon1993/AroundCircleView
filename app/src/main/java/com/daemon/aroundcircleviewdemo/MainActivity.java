package com.daemon.aroundcircleviewdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.daemon.aroundcircleview.AroundCircleView;
import com.daemon.aroundcircleview.BitmapBlurHelper;

public class MainActivity extends AppCompatActivity {

    private int progress;

    WeakHandler weakHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what == 1) {
                acvIcon.setProgress(progress);
            }

            return false;
        }
    });
    private boolean falg = true;
    private AroundCircleView acvIcon;
    private RelativeLayout rl_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acvIcon = (AroundCircleView) findViewById(R.id.acv_icon);
        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);


        ObjectAnimator animator = ObjectAnimator.ofFloat(acvIcon,"rotation",0,360);
        animator.setDuration(8000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

        Bitmap image = ((BitmapDrawable)acvIcon.getDrawable()).getBitmap();

        Bitmap bitmap = BitmapBlurHelper.doBlur(this, image, 6);

        BitmapDrawable drawable= new BitmapDrawable(getResources(),bitmap);

        rl_bg.setBackgroundDrawable(drawable);


        new Thread(new Runnable() {


            @Override
            public void run() {

                while (falg) {

                    if (progress > 100) {
                        progress = 0;
                    }
                    SystemClock.sleep(1000);
                    progress += 5;

                    weakHandler.sendEmptyMessage(1);

                }
            }
        }).start();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        falg = false;
    }


}
