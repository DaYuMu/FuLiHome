package cn.ucai.fulihome;

import android.app.Application;

/**
 * Created by Administrator on 2016/10/17 0017.
 * 单例设计模式
 */
public class FuLiHomeApplication extends Application {
    public static FuLiHomeApplication application;
    private static  FuLiHomeApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        instance = this;
    }

    public static FuLiHomeApplication getInstance() {
        if (instance == null) {
            instance = new FuLiHomeApplication();
        }
        return instance;
    }
}
