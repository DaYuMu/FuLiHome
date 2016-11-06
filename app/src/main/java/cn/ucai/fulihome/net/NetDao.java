package cn.ucai.fulihome.net;

import android.content.Context;
import android.support.v7.view.menu.MenuWrapperFactory;

import cn.ucai.fulihome.I;
import cn.ucai.fulihome.bean.BoutiqueBean;
import cn.ucai.fulihome.bean.CartBean;
import cn.ucai.fulihome.bean.CategoryChildBean;
import cn.ucai.fulihome.bean.CategoryGroupBean;
import cn.ucai.fulihome.bean.CollectBean;
import cn.ucai.fulihome.bean.GoodsDetailsBean;
import cn.ucai.fulihome.bean.MessageBean;
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
    public static void downloadGoodsDetails(Context context, int position,
                                            OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener) {
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
     * 下载购物车数据
     *http://101.251.196.90:8000/FuLiCenterServerV2.0/findCarts?userName=a95270011
     * @param context
     * @param listener
     */
    public static void downloadCort(Context context, String username ,OkHttpUtils.OnCompleteListener<CartBean[]> listener) {
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME, username)
                .targetClass(CartBean[].class)
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
    public static void downloadChild(Context context, int parentId,
                                     OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener) {
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

    public static void login(Context context, String name, String password,
                             OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,name)
                .addParam(I.User.PASSWORD,MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * i下载更新昵称后的数据
     * @param context
     * @param name
     * @param nick
     * @param listener
     */
    public static void updatanick(Context context, String name, String nick,
                             OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,name)
                .addParam(I.User.NICK,nick)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 下载收藏商品的数量
     * @param context
     * @param userName
     * @param listener
     */
    public static void syncUserInfo(Context context, String userName,
                               OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME,userName)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 得到收藏商品的数量
     * @param context
     * @param username
     * @param listener
     */
    public static void getCollectCount(Context context, String username,
                                       OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    /**
     * 下载收藏的商品的详情
     * @param context
     * @param username
     * @param pageId
     * @param listener
     */
    public static void downloadCollects(Context context, String username, int pageId,
                                        OkHttpUtils.OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);

    }

    /**
     * 下载删除已收藏的商品信息的方法
     * @param context
     * @param username
     * @param goodsId
     * @param listener
     */
    public static void deleteCollects(Context context, String username, int goodsId,
                                      OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,String.valueOf(goodsId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    /**
     * 下载商品收藏的图标的变化信息
     * @param context
     * @param username
     * @param goodsId
     * @param listener
     */
    public static void isCollect(Context context, String username, int goodsId,
                                      OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,String.valueOf(goodsId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    //  添加收藏
    public static void addCollect(Context context, String username, int goodsId,
                                  OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,String.valueOf(goodsId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    //  是否选中购物车中的商品
    public static void isChecked(Context context, int cartId, int cartCount,
                                 OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,String.valueOf(cartId))
                .addParam(I.Cart.COUNT,String.valueOf(cartCount))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(I.CART_CHECKED_DEFAULT))
                .targetClass(MessageBean.class)
                .execute(listener);

    }


    //  删除购物车中的商品
    public static void deleteCart(Context context, int cartId,
                                  OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,String.valueOf(cartId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    //  添加购物车中的商品
    public static void addCart(Context context, String username, int goodId,
                               OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.USER_NAME, username)
                .addParam(I.Cart.GOODS_ID,String.valueOf(goodId))
                .addParam(I.Cart.COUNT,String.valueOf(1))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(I.CART_CHECKED_DEFAULT))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void updataCart(Context context, int cartId, int count,
                                  OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,String.valueOf(cartId))
                .addParam(I.Cart.COUNT,String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(I.CART_CHECKED_DEFAULT))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

}
