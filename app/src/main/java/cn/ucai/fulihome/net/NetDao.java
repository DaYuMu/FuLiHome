package cn.ucai.fulihome.net;

import android.content.Context;

import cn.ucai.fulihome.I;
import cn.ucai.fulihome.bean.NewGoodsBean;

/**
 * Created by Administrator on 2016/10/17 0017.
 * 商品新品的网络请求
 */
public class NetDao {
    public static void downloadNewGoods(Context context, int pageId,
                                        OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)//  请求发现新品精品的商品
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                //  默认的每页的尺寸
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}
