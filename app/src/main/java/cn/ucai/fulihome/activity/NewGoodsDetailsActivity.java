package cn.ucai.fulihome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.Albums;
import cn.ucai.fulihome.bean.GoodsDetailsBean;
import cn.ucai.fulihome.bean.MessageBean;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.view.FlowIndicator;
import cn.ucai.fulihome.view.SlideAutoLoopView;


public class NewGoodsDetailsActivity extends BaseActivity {
    private static final String TAG = NewGoodsDetailsActivity.class.getSimpleName();

    @BindView(R.id.agoPrice)
    TextView agoPrice;
    @BindView(R.id.currentPrice)
    TextView currentPrice;
    @BindView(R.id.ivTitleBack)
    ImageView ivTitleBack;
    @BindView(R.id.EnglishName)
    TextView EnglishName;
    @BindView(R.id.ChinaName)
    TextView ChinaName;
    @BindView(R.id.SlideImage)
    SlideAutoLoopView SlideImage;
    @BindView(R.id.FlowImage)
    FlowIndicator FlowImage;
    @BindView(R.id.NewGoodsDetails)
    TextView NewGoodsDetails;

    int position;

    boolean isCollected = false;

    NewGoodsDetailsActivity mContext;
    @BindView(R.id.ivTitleCollect)
    ImageView ivTitleCollect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_goods_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mContext = this;
        position = intent.getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetails(this, position, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodsDateils(result);
                } else {
                    finish();
                }
            }

            private void showGoodsDateils(GoodsDetailsBean result) {
                agoPrice.setText(result.getPromotePrice());
                currentPrice.setText(result.getCurrencyPrice());
                EnglishName.setText(result.getGoodsEnglishName());
                ChinaName.setText(result.getGoodsName());
                NewGoodsDetails.setText(result.getGoodsBrief());
                L.e("main", "商品的详情加载完成");
                SlideImage.startPlayLoop(FlowImage, getAlbumImgUrl(result), getAlbumCount(result));

            }

            private int getAlbumCount(GoodsDetailsBean detailsBean) {
                if (detailsBean.getProperties() != null && detailsBean.getProperties().length > 0) {
                    return detailsBean.getProperties()[0].getAlbums().length;
                }
                return 0;
            }

            private String[] getAlbumImgUrl(GoodsDetailsBean detailsBean) {
                String[] url = new String[]{};
                if (detailsBean.getProperties() != null && detailsBean.getProperties().length > 0) {
                    Albums[] albums = detailsBean.getProperties()[0].getAlbums();
                    url = new String[albums.length];
                    for (int i = 0; i < albums.length; i++) {
                        url[i] = albums[i].getImgUrl();
                    }
                }
                return url;
            }

            @Override
            public void onError(String error) {
                finish();
                CommonUtils.showShortToast(error);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.ivTitleBack)
    public void onBackClick() {
        MFGT.finish(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCollect();
    }

    @OnClick(R.id.ivTitleCollect)
    public void onCollectChilk() {
        User user = FuLiHomeApplication.getUser();
        if (user == null) {
            MFGT.gotoLoginActivity(mContext);
        } else {
            if (isCollected) {
                NetDao.deleteCollects(mContext, user.getMuserName(), position, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollected = !isCollected;
                            updateGoodCollectStatus();
                            CommonUtils.showShortToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            } else {
                NetDao.addCollect(mContext, user.getMuserName(), position, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollected = !isCollected;
                            updateGoodCollectStatus();
                            CommonUtils.showShortToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }
    }


    public void isCollect() {
        final User user = FuLiHomeApplication.getUser();
        if (user != null) {
            NetDao.isCollect(mContext, user.getMuserName(), position, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    L.e(TAG + "isCollect.success.start" + result);
                    if (result != null && result.isSuccess()) {
                        isCollected = true;
                        L.e(TAG + "updateGoodCollectStatus.start");
                    }
                    updateGoodCollectStatus();
                }

                @Override
                public void onError(String error) {
                    L.e(TAG + "isCollect.onError.start");
                }

            });

        } else {
            finish();
        }
        updateGoodCollectStatus();
    }

    private void updateGoodCollectStatus() {
        if (isCollected) {
            ivTitleCollect.setImageResource(R.mipmap.bg_collect_out);
            L.e(TAG + "collect_out");
        } else {
            ivTitleCollect.setImageResource(R.mipmap.bg_collect_in);
            L.e(TAG + "collect_in");
        }
    }

    @OnClick(R.id.ivTitleShare)
    public void onClick() {
        showShare();
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
