package com.daemon.aroundcircleview;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acvIcon = (AroundCircleView) findViewById(R.id.acv_icon);

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
