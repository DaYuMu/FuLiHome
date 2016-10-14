package cn.ucai.fulihome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulihome.R;

public class SplashActivity extends AppCompatActivity {
    private final int splash = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long timeMillis1 = System.currentTimeMillis();//  一开始时候的时间
                //  某些在中间进行的加载数据等需要时间的操作。
                long timeMillis2 = System.currentTimeMillis();//  加载数据等耗时操作完成后的时间

                //  用两次时间的差与定的事件进行比较
                if (timeMillis2 - timeMillis1< splash) {
                    try {
                        //   判断闪屏还要延续多少时间
                        Thread.sleep(splash-(timeMillis2-timeMillis1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        }).start();
    }
}
