package cn.ucai.fulihome.utils;

import android.app.Activity;
import android.content.Intent;

import cn.ucai.fulihome.R;
import cn.ucai.fulihome.activity.MainActivity;

/**
 * 本方法为活动中的跳转
 * 从某一个活动跳转到MainActivity。
 */
public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}
