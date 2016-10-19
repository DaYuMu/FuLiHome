package cn.ucai.fulihome.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.io.Serializable;

import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.activity.BoutiqueChildActivity;
import cn.ucai.fulihome.activity.MainActivity;
import cn.ucai.fulihome.activity.NewGoodsDetailsActivity;
import cn.ucai.fulihome.bean.BoutiqueBean;

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
        startActivity(context,intent);
    }
    //  去往NewGoodsDetailsActivity的方法，要传入一个商品的id
    public static void gotoNewGoodsDetailsActivity(Context context, int goodsid){
        Intent intent = new Intent();
        intent.setClass(context, NewGoodsDetailsActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsid);
        startActivity(context,intent);
    }
    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean boutiqueBean){
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,boutiqueBean );
        startActivity(context,intent);
    }
}

