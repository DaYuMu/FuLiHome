package cn.ucai.fulihome;

import android.app.Application;

import cn.ucai.fulihome.bean.User;

/**
 * Created by Administrator on 2016/10/17 0017.
 * 单例设计模式
 */
public class FuLiHomeApplication extends Application {
    public static FuLiHomeApplication application;
    private static  FuLiHomeApplication instance;
    public static String username;
    public static User user;

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


    public static void setUsername(String username) {
        FuLiHomeApplication.username = username;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiHomeApplication.user = user;
    }
}
