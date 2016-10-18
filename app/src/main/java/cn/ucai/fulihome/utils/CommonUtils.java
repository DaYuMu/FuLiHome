package cn.ucai.fulihome.utils;

import android.widget.Toast;

import cn.ucai.fulihome.FuLiHomeApplication;

public class CommonUtils {
    public static void showLongToast(String msg){
        Toast.makeText(FuLiHomeApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(String msg){
        Toast.makeText(FuLiHomeApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(int rId){
        showLongToast(FuLiHomeApplication.getInstance().getString(rId));
    }
    public static void showShortToast(int rId){
        showShortToast(FuLiHomeApplication.getInstance().getString(rId));
    }
}
