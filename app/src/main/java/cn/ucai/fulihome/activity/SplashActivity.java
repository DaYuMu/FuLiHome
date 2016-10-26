package cn.ucai.fulihome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.dao.SharePreferenceUtils;
import cn.ucai.fulihome.dao.UserDao;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private final int splash = 2000;
    SplashActivity mContext;
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long timeMillis1 = System.currentTimeMillis();//  一开始时候的时间L.e(TAG+"username="+username);
                // 如果内存中有User数据直接读取
                User user = FuLiHomeApplication.getUser();
                L.e(TAG, "user=" + user);
                // 判断首选项是否读取到用户名
                String username = SharePreferenceUtils.getInstance(mContext).getUser();
                L.e(TAG, "username=" + username);
                if (user == null && username != null) {
                    //  数据库中读取信息
                    UserDao userDao = new UserDao(mContext);
                    user = userDao.getUser(username);
                    L.e(TAG , "user=" + user);
                    if (user != null) {
                        FuLiHomeApplication.setUser(user);
                    }
                }
                //  某些在中间进行的加载数据等需要时间的操作。
                long timeMillis2 = System.currentTimeMillis();//  加载数据等耗时操作完成后的时间

                //  用两次时间的差与定的事件进行比较
                if (timeMillis2 - timeMillis1 < splash) {
                    try {
                        //   判断闪屏还要延续多少时间
                        Thread.sleep(splash - (timeMillis2 - timeMillis1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MFGT.gotoMainActivity(SplashActivity.this);
                    MFGT.finish(SplashActivity.this);
                }
            }
        }).start();
    }
}
