package com.daemon.aroundcircleviewdemo;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.daemon.aroundcircleview.AroundCircleView;

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
    private ObjectAnimator animator1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acvIcon = (AroundCircleView) findViewById(R.id.acv_icon);

        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);

        Bitmap image = ((BitmapDrawable)acvIcon.getDrawable()).getBitmap();

        //背景图像虚化
        blurBG(image);

        progress=50;
        acvIcon.setProgress(progress);

        //更新进度条
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


    private void blurBG(Bitmap image) {


        //透明度动画
        animator1 = ObjectAnimator.ofFloat(rl_bg, "alpha", 0.2f, 1.0f);
        animator1.setDuration(2000);
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());


        Bitmap bitmap = BitmapBlurHelper.doBlurJniBitMap(image, 50, false);

        if (bitmap == null) {
            //获取背景图片失败  使用默认黑色背景 需要图片
            return;
        }
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);

        rl_bg.setBackgroundDrawable(drawable);

        animator1.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        falg = false;
    }


}
