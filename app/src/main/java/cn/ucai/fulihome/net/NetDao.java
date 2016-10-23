package cn.ucai.fulihome.net;

import android.content.Context;

import cn.ucai.fulihome.I;
import cn.ucai.fulihome.bean.BoutiqueBean;
import cn.ucai.fulihome.bean.CategoryChildBean;
import cn.ucai.fulihome.bean.CategoryGroupBean;
import cn.ucai.fulihome.bean.GoodsDetailsBean;
import cn.ucai.fulihome.bean.NewGoodsBean;
import cn.ucai.fulihome.bean.Result;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MD5;

/**
 * Created by Administrator on 2016/10/17 0017.
 * 商品新品的网络请求
 */
public class NetDao {
    /**
     * 加载新品的首页数据
     * @param context
     * @param pageId
     * @param listener
     */
    public static void downloadNewGoods(Context context, int catId,int pageId,
                                        OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)//  请求发现新品精品的商品
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                //  默认的每页的尺寸
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    /**
     * 加载新品页的商品详情的数据
     * @param context
     * @param position
     * @param listener
     */
    public static void downloadGoodsDetails(Context context, int position, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener) {
        L.e("main","NetDao.downloadGoodsDetails()方法开始运行");
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(position))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
        L.e("main","NetDao.downloadGoodsDetails()方法运行结束");
    }

    /**
     * 加载精选首页的数据
     * @param context
     * @param listener
     */
    public static void downloadBoutique(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }

    /**
     * 加载分类页面大类的数据
     * @param context
     * @param listener
     */
    public static void downloadGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    /**
     * 加载分类页面小类的数据
     * @param context
     * @param listener
     */
    public static void downloadChild(Context context, int parentId,OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(parentId))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    /**
     * 加载分类页面小类点击事件后跳转到CategoryChildActivity的数据
     * @param context
     * @param pageId
     * @param listener
     */
    public static void downloadCategoryGoods(Context context, int catId,int pageId,
                                        OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)//  请求发现新品精品的商品
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                //  默认的每页的尺寸
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    /**
     * 下载注册需要的数据。
     * @param context
     * @param username
     * @param usernick
     * @param userpassword
     * @param listener
     */
    public static void register(Context context, String username, String usernick, String userpassword, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,usernick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(userpassword))
                .post()
                .targetClass(Result.class)
                .execute(listener);
    }

    public static void login(Context context, String name, String password, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,name)
                .addParam(I.User.PASSWORD,MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .execute(listener);
    }
}
