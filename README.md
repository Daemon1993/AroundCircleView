圆形图片 周围进度条 类似于音乐播放器的进度

圆角显示图片 来自CircleImageView 在基础上修改 让其可以周边动态显示进度

     app:textBgColor 外围进度的原始背景颜色 
     app:textColor 外围进度条的颜色 
    app:textSize="10dp" 宽度0时不显示
    
使用方法

    <com.daemon.aroundcircleview.AroundCircleView
        android:id="@+id/acv_icon"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_centerInParent="true"
        android:layout_gravity="center"
        android:src="@mipmap/head"
        app:textColor="@color/colorAccent"
        app:textBgColor="#000000"
        app:textSize="10dp" />

 
- 更新进度条 利用Hanlder更新即可
       //更新进度条
        progress=50;
        acvIcon.setProgress(progress);

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
        @Overridepublic void onDestroy() {    
              super.onDestroy();    
              falg = false;
        }

        WeakHandler weakHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what == 1) {
                acvIcon.setProgress(progress);
            }

            return false;
        }
    });


效果如图

背景虚化也写过相关的blog 
[android 图片 高斯模糊 Blur Android Studio JNI NDK 生成 so 问题汇总](http://www.jianshu.com/p/d3ab6de52712)

![Paste_Image.png](http://upload-images.jianshu.io/upload_images/831873-b0ece0afb3cea8bc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![GIF.gif](https://github.com/Daemon1993/AroundCircleView/blob/master/GIF.gif)

[github地址](https://github.com/Daemon1993/AroundCircleView)

相关音乐项目
[MVP模式 项目练习 Pas --新闻 音乐 图片 三个模块](http://www.jianshu.com/p/f959c5cf8218)
